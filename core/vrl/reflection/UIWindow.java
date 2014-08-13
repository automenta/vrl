/* 
 * UIWindow.java
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

package vrl.reflection;

import vrl.annotation.MethodInfo;
import vrl.io.vrlx.AbstractUI;
import java.util.ArrayList;
import javax.swing.Box;
import vrl.visual.Canvas;
import vrl.visual.CanvasWindow;
import vrl.visual.MessageBox;
import vrl.visual.MessageType;
import vrl.visual.Selectable;
import vrl.visual.VContainer;

/**
 * This class provides custom interface functionality. It is a special
 * canvas window that is able to group selected type representations from
 * different objects in one window.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UIWindow extends CanvasWindow {

    private static final long serialVersionUID = 6777936778003285322L;
//    VContainer container;


    /**
     * Constructor.
     * @param title the title of the window
     * @param mainCanvas the main canvas object
     */
    public UIWindow(String title, Canvas mainCanvas) {
        super(title, mainCanvas);
    }

    /**
     * Constructor.
     * @param title the title of the window
     * @param mainCanvas the main canvas object
     */
    public static UIWindow createFromClipboard(String title, Canvas mainCanvas) {
        UIWindow result = new UIWindow(title, mainCanvas);

        ArrayList<Selectable> selectables = new ArrayList<Selectable>();

        ArrayList<TypeRepresentationBase> typeRepresentations =
                new ArrayList<TypeRepresentationBase>();

        boolean onlyTypeRepresentationsSelected = true;

        for (Selectable s : mainCanvas.getClipBoard()) {
            selectables.add(s);
//            if (s instanceof TypeRepresentationBase) {
//                final TypeRepresentationBase t = (TypeRepresentationBase) s;
//
////                this.add(new VContainer(t));
//
//                t.setAlignmentX(0.5f);
//                this.add(t);
//
//                MethodDescription mDesc = t.getParentMethod().getDescription();
//                MethodInfo mInfo = mDesc.getMethodInfo();
//
//                if (mInfo == null || mInfo.interactive()) {
//                    this.add(new VContainer(
//                            t.getParentMethod().createInvokeButton()));
//                }
//
//                selectables.add(t);
//            }
            if (s instanceof TypeRepresentationBase) {
                typeRepresentations.add((TypeRepresentationBase) s);
            } else {
                onlyTypeRepresentationsSelected = false;
            }
        }


        if (onlyTypeRepresentationsSelected) {
            if (typeRepresentations.size() > 0) {
                for (TypeRepresentationBase t : typeRepresentations) {


//                this.add(new VContainer(t));

                    t.setAlignmentX(0.5f);
                    result.add(t);

                    MethodDescription mDesc =
                            t.getParentMethod().getDescription();
                    MethodInfo mInfo = mDesc.getMethodInfo();

                    if (mInfo == null || mInfo.interactive()) {
                        result.add(new VContainer(
                                t.getParentMethod().createInvokeButton()));
                    }

                    selectables.add(t);

                }

                result.add(Box.createVerticalStrut(5));
                result.revalidate();
                result.getRemoveTasks().add(AbstractUI.getRemoveTask(result));
                result.setContentProvider(new AbstractUI(result));
                
            } else {
                MessageBox mBox = mainCanvas.getMessageBox();
                mBox.addMessage("Can't create Custom UI:",
                        ">> Please select at least one type representation.",
                        MessageType.ERROR);
            }
        } else {
            MessageBox mBox = mainCanvas.getMessageBox();
            mBox.addMessage("Can't create Custom UI:",
                    ">> Custom UI generation only supports type representations!" +
                    " Thus only type representations may be selected.",
                    MessageType.ERROR);

        }

        for (Selectable s : selectables) {
            result.getMainCanvas().getClipBoard().unselect(s);
        }

        return result;
    }
}


