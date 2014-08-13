/* 
 * BooleanOutputType.java
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

import java.awt.Dimension;
import vrl.annotation.TypeInfo;
import vrl.reflection.TypeRepresentationBase;
import vrl.visual.VBoxLayout;
import vrl.visual.VTextField;



/**
 * TypeRepresentation for <code>Java.lang.Boolean</code>.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@TypeInfo(type=Boolean.class, input = false, output = true, style="default")
public class BooleanOutputType extends TypeRepresentationBase {

    private static final long serialVersionUID = -6069239332706380245L;
    private VTextField input;

    /**
     * Constructor.
     */
    public BooleanOutputType() {

	VBoxLayout layout = new VBoxLayout(this, VBoxLayout.Y_AXIS);

	setLayout(layout);

	nameLabel.setText("Boolean:");
	nameLabel.setAlignmentX(0.0f);
	this.add(nameLabel);

	input = new VTextField(this, "");

	int height = (int) this.input.getMinimumSize().getHeight();
	this.input.setMinimumSize(new Dimension(50, height));
//        this.input.setPreferredSize(new Dimension(50, height));
//        this.input.setMaximumSize(new Dimension(50, height));

	input.setEditable(false);

	setInputDocument(input, input.getDocument());

	input.setAlignmentY(0.5f);
	input.setAlignmentX(0.0f);
	this.add(input);
    }

    @Override
    protected void valueInvalidated() {
	if (isInput()) {
	    input.setInvalidState(true);
	}
    }

    @Override
    protected void valueValidated() {
	if (isInput()) {
	    input.setInvalidState(false);
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
    public String getValueAsCode() {
        return value.toString();
    }

//    @Override
//    public Object getViewValue() {
//	Object o = null;
//	try {
//	    o = input.getText();
//	} catch (Exception e) {
//	}
//	return o;
//    }
}
