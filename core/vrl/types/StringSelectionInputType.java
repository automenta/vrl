/* 
 * StringSelectionInputType.java
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
import groovy.lang.Script;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * Selection Type for strings. To define possible selections set the value
 * property of the param info.
 * </p>
 * <p><b>Example (Groovy code):</b><?p>
 * <code>
 * <pre>
 * &#64;ComponentInfo(name="String Selection")
 * class StringSelection implements Serializable {
 *
 *   private static final long serialVersionUID=1;
 *
 *   public String select(&#64;ParamInfo(style="selection", options="value=[\"value a\",\"value b\"]") String s){
 *     return s
 *   }
 * }
 * </pre>
 * </code>
 * 
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @see eu.mihosoft.vrl.annotation.ParamInfo
 */
@TypeInfo(type=String.class, input = true, output = false, style="selection")
public class StringSelectionInputType extends SelectionInputType {

    public StringSelectionInputType() {
        VBoxLayout layout = new VBoxLayout(this, VBoxLayout.PAGE_AXIS);
        setLayout(layout);

        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        getSelectionView().setAlignmentX(Component.LEFT_ALIGNMENT);

        add(nameLabel);
        add(getSelectionView());

        setValueName("String:");

        setHideConnector(true);
    }

    @Override
    public Object getViewValue() {
        
        if (!getMainCanvas().isSavingSession()) {
            if (getSelectionView().getSelectedItem() != null) {
                return getSelectionView().getSelectedItem().toString();
            }
        } else {
            return super.getViewValue();
        }
        return null;
    }

    @Override
    protected void evaluationRequest(Script script) {
        
        Object property = null;

        if (getValueOptions() != null) {

            if (getValueOptions().contains("value")) {
                property = script.getProperty("value");
            }

            // TODO 18.07.2012:
            //
            // validate that this still works. it might be broken
            // as we have changed the method order:
            //
            // evaluationRequest() is now called before setValue()!
            //
            if (property != null) {
                if (getViewValueWithoutValidation() == null) {
                    super.setViewValue(new Selection((Collection<?>) property));
                }
            }
        }
    }
    
    @Override()
    public String getValueAsCode() {
        String result = "null";

        Object o = getValue();

        if (o != null) {
            result = "\"" + o.toString() + "\"";
        }

        return result;
    }
}
