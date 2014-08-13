/* 
 * ComponentMenuItem.java
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 * Component menue items can be used to add components to the VRL popup menu.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class ComponentMenuItem extends JMenuItem {

//    private VisualCanvas mainCanvas;
    private VCanvasPopupMenu menu;
    boolean component = false;
    private Class<?> componentClass;
    private boolean allowRemoval = true;

    /**
     * Constructor.
     * @param c the component class
     * @param menu the VRL popup menu this item shall be added to
     */
    public ComponentMenuItem(final Class<?> c, final VCanvasPopupMenu menu) {
        this.menu = menu;
//        mainCanvas = menu.getMainCanvas();

        componentClass = c;

        component = ComponentUtil.isComponent(c);
        allowRemoval = ComponentUtil.allowsRemoval(c);
        setText(ComponentUtil.getComponentName(c));
    }

    /**
     * Creates an action that removes the component from the menu and the
     * canvas.
     */
    public void createRemoveComponentAction() {
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(getText())) {
                    ComponentUtil.requestRemoval(
                            componentClass, menu.getMainCanvas(),
                            menu.getMainCanvas().getComponentController());
                }
            }
        });
    }

    /**
     * Creates an action that adds the component to the menu.
     */
    public void createAddComponentAction() {
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(getText())) {
                    ComponentUtil.addObject(
                            componentClass, menu.getMainCanvas(),
                            menu.getCurrentLocation(),false);
                }
            }
        });
    }

    /**
     * Indicates whether the associated code defines a VRL component.
     * @return <code>true</code> if the code defines a component;
     *         <code>false</code> otherwise
     */
    public boolean isComponent() {
        return component;
    }


    /**
     * Returns the class object of the component.
     * @return the class object of the component
     */
    public Class getComponentClass() {
        return componentClass;
    }

    /**
     * @return the allowRemoval
     */
    public boolean isAllowRemoval() {
        return allowRemoval;
    }
}
