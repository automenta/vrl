/* 
 * AbstractUI.java
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2009–2012 Steinbeis Forschungszentrum (STZ Ölbronn),
 * Copyright (c) 2006–2012 by Michael Hoffer
 * 
 * This file is part of Visual Reflection Library (VRL).
 *
 * VRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * see: http://opensource.org/licenses/LGPL-3.0
 *      file://path/to/VRL/src/eu/mihosoft/vrl/resources/license/lgplv3.txt
 *
 * VRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * This version of VRL includes copyright notice and attribution requirements.
 * According to the LGPL this information must be displayed even if you modify
 * the source code of VRL. Neither the VRL Canvas attribution icon nor any
 * copyright statement/attribution may be removed.
 *
 * Attribution Requirements:
 *
 * If you create derived work you must do three things regarding copyright
 * notice and author attribution.
 *
 * First, the following text must be displayed on the Canvas:
 * "based on VRL source code". In this case the VRL canvas icon must be removed.
 * 
 * Second, the copyright notice must remain. It must be reproduced in any
 * program that uses VRL.
 *
 * Third, add an additional notice, stating that you modified VRL. In addition
 * you must cite the publications listed below. A suitable notice might read
 * "VRL source code modified by YourName 2012".
 * 
 * Note, that these requirements are in full accordance with the LGPL v3
 * (see 7. Additional Terms, b).
 *
 * Publications:
 *
 * M. Hoffer, C.Poliwoda, G.Wittum. Visual Reflection Library -
 * A Framework for Declarative GUI Programming on the Java Platform.
 * Computing and Visualization in Science, 2011, in press.
 */

package vrl.io.vrlx;

import vrl.animation.AnimationTask;
import vrl.reflection.MethodDescription;
import vrl.annotation.MethodInfo;
import vrl.reflection.ReferenceTask;
import vrl.reflection.TypeRepresentationBase;
import vrl.reflection.TypeRepresentationContainer;
import vrl.reflection.UIWindow;
import vrl.reflection.VisualCanvas;
import vrl.visual.Canvas;
import vrl.visual.CanvasWindow;
import vrl.visual.Task;
import vrl.visual.WindowContentProvider;
import vrl.visual.VButton;
import vrl.visual.VContainer;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.Box;

/**
 * An abstract ui is a content provider for UIWindow objects. It is also used
 * for XML serialization.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class AbstractUI extends ArrayList<AbstractTypeRepresentationReference>
        implements WindowContentProvider {

    private static final long serialVersionUID = -8883661487840137957L;

    /**
     * Constructor.
     */
    public AbstractUI() {
    }

    /**
     * Constructor.
     * @param ui the ui window
     */
    public AbstractUI(UIWindow ui) {
//        System.out.println(ui.getTitle() + " " + ui.getID() + " BEGIN");
        for (Component c : ui.getComponents()) {
//            System.out.println("PIP");

            // if vcontainer is used

//            TypeRepresentationBase t = null;
//            if (c instanceof Container) {
//                // TODO: remove direct dependency to VContainer and component 0
//                Container container = (Container) c;
//                try {
//                    t = (TypeRepresentationBase) container.getComponent(0);
//                } catch (Exception ex) {
//                }
//            }

            TypeRepresentationBase t = null;

            try {
                t = (TypeRepresentationBase) c;
            } catch (Exception ex) {
                //
            }

            if (t != null) {
                add(new AbstractTypeRepresentationReference(t));
            }
        }
//        System.out.println("END");
    }

    @Override
    public ArrayList<Component> getContent(Canvas mainCanvas) {

        // TODO: remove copy&paste code in UIWindow

        ArrayList<Component> components =
                new ArrayList<Component>();

        if (mainCanvas instanceof VisualCanvas) {
            VisualCanvas v = (VisualCanvas) mainCanvas;

            ArrayList<Object> delList =
                    new ArrayList<Object>();

            for (AbstractTypeRepresentationReference t : this) {

                final TypeRepresentationBase tRep = t.getReference(v);

                // if reference is correct
                if (tRep != null) {

                    tRep.setAlignmentX(0.5f);

                    components.add(tRep);

                    MethodDescription mDesc =
                            tRep.getParentMethod().getDescription();
                    MethodInfo mInfo = mDesc.getMethodInfo();

                    if (mInfo == null || mInfo.interactive()) {
                        VButton button =
                                tRep.getParentMethod().createInvokeButton();

                        components.add(new VContainer(button));
                    }
                } else {
                    // if reference is incorrect it has to be deleted
                    delList.add(t);
                }
            } // end for

            // delete incorrect references
            for (Object t : delList) {
                this.remove(t);
            }

            components.add(Box.createVerticalStrut(5));
        }

        return components;
    }

    /**
     * <p>
     * Returns a remove task for a given canvas window. This is used to generate
     * the remove task for ui windows.
     * </p>
     * <p>
     * The remove task is necessary to put the type representations back to
     * their parent method representations after the window has been closed.
     * </p>
     *
     * @param window
     * @return the remove task
     */
    public static Task getRemoveTask(final CanvasWindow window) {
        return new Task() {

            @Override
            public void run() {
                for (Component c : window.getComponents()) {
                    if (c instanceof TypeRepresentationBase) {

                        final TypeRepresentationBase t =
                                (TypeRepresentationBase) c;

                        // Only add remove task if parent method exists.
                        // This is not the case if the methods parent window
                        // has been closed.
                        if (t.getParentMethod() != null) {

                            final CanvasWindow window =
                                    t.getParentMethod().getParentObject().
                                    getParentWindow();

                            window.maximize(new AnimationTask() {

                                @Override
                                public void firstFrameStarted() {
                                    //
                                }

                                @Override
                                public void frameStarted(double time) {
                                    //
                                }

                                @Override
                                public void lastFrameStarted() {
                                    window.setMinimumSize(null);
                                    window.setMaximumSize(null);
                                    window.setPreferredSize(null);
                                    t.setVisible(true);
                                    TypeRepresentationContainer tCont =
                                            (TypeRepresentationContainer) t.getConnector().getValueObject();
                                    tCont.add(t);
                                }
                            });
                        }
                    }
                }
            }
        };
    }

    @Override
    public CanvasWindow newWindow(Canvas canvas, AbstractWindow abstractWindow) {
        final VisualCanvas mainCanvas = (VisualCanvas) canvas;
       CanvasWindow window = new UIWindow(abstractWindow.getTitle(), mainCanvas);
            mainCanvas.getWindows().addWithID(window,abstractWindow.getObjID());
//            window.setTitle(getTitle() + "(" + window.getID() + ")");
            window.setLocation(abstractWindow.getLocation());
            window.getRemoveTasks().add(AbstractUI.getRemoveTask(window));
            window.setContentProvider(abstractWindow.getContentProvider());
            final CanvasWindow resolveWindow = window;

            // this is a very important part!
            // ui references can only be resolved after all components
            // have been loaded
//            if (abstractWindow.getContentProvider() != null) {
                mainCanvas.getReferenceTasks().add(new ReferenceTask() {

                    @Override
                    public void resolve() {
                        for (Component c : AbstractUI.this.getContent(mainCanvas)) {
                            resolveWindow.add(c);
                        }
                    }
                });
//            }
                return window;
    }
}
