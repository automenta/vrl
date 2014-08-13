/* 
 * IntDebugType.java
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

package vrl.types;

import vrl.annotation.TypeInfo;
import vrl.visual.VBoxLayout;
import java.awt.Color;
import java.awt.Dimension;
import vrl.reflection.RepresentationType;
import vrl.reflection.TypeRepresentationBase;
import vrl.visual.VTextField;

/**
 * TypeRepresentation for <code>Java.lang.Integer</code>.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@TypeInfo(type=Integer.class, input = true, output = true, style="debug")
public class IntDebugType extends TypeRepresentationBase {
    private static final long serialVersionUID = -4451324400119578036L;

    private VTextField input; 

    /**
     * Constructor.
     */
    public IntDebugType() {

        VBoxLayout layout = new VBoxLayout(this, VBoxLayout.Y_AXIS);

        setLayout(layout);

        nameLabel.setText("Integer:");
        nameLabel.setAlignmentX(0.0f);

        this.add(nameLabel);
        
        input= new VTextField(this,"");

        int height = (int) this.input.getMinimumSize().getHeight();
        this.input.setMinimumSize(new Dimension(50, height));

        setInputDocument(input, input.getDocument());

        input.setAlignmentY(0.5f);
        input.setAlignmentX(0.0f);

        this.add(input);
    }

    @Override
    public void setValueName(String name) {
        this.nameLabel.setText(name);
    }

    @Override
    public String getValueName() {
        return this.nameLabel.getText();
    }

    @Override
    public void setCurrentRepresentationType(
            RepresentationType representationType) {
        if (representationType == RepresentationType.OUTPUT) {
            input.setEditable(false);
        }
        super.setCurrentRepresentationType(representationType);
    }

    @Override
    protected void valueInvalidated() {
        if (isInput()) {
            input.setBackground(Color.RED);
        }
    }

    @Override
    protected void valueValidated() {
        if (isInput()) {
            input.setBackground(Color.WHITE);
        }
    }

    @Override
    public void emptyView() {
        input.setText("");
    }

    @Override
    public void setViewValue(Object o) {
        input.setText(o.toString());
    }

    @Override
    public Object getViewValue() {
        Object o = null;
        try {
            o = new Integer(input.getText());
        } catch (Exception e) {
        }
        return o;
    }
}
