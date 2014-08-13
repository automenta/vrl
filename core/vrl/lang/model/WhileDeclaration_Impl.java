/* 
 * WhileDeclaration_Impl.java
 *
 * Copyright (c) 2009–2014 Steinbeis Forschungszentrum (STZ Ölbronn),
 * Copyright (c) 2006–2014 by Michael Hoffer
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
 * First, the following text must be displayed on the Canvas or an equivalent location:
 * "based on VRL source code".
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
 * Computing and Visualization in Science, in press.
 */
package vrl.lang.model;

import vrl.lang.model.Scope;
import vrl.lang.model.WhileDeclaration;
import vrl.lang.model.Invocation;
import javafx.collections.ListChangeListener;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
class WhileDeclaration_Impl extends ScopeImpl implements WhileDeclaration {

    private WhileDeclarationMetaData metadata;

    public WhileDeclaration_Impl(String id, Scope parent, IArgument argument) {
        super(id, parent, ScopeType.WHILE, ScopeType.WHILE.name(), new WhileDeclarationMetaData(argument));

    }

    @Override
    public IArgument getCheck() {

        return getMetaData().getCheck();
    }
    
    private WhileDeclarationMetaData getMetaData() {
          if (metadata == null) {
            metadata = (WhileDeclarationMetaData) getScopeArgs()[0];
        }
          
          return metadata;
    }

    @Override
    public void defineParameters(Invocation i) {
        i.getArguments().setAll(getCheck());

        i.getArguments().addListener((ListChangeListener.Change<? extends IArgument> c) -> {
            while (c.next()) {
                if (i.getArguments().isEmpty()) {
                    getMetaData().setCheck(Argument.NULL);
                } else {
                    getMetaData().setCheck(i.getArguments().get(0));
                }
            }
        });
    }
}

class WhileDeclarationMetaData {

    private IArgument check;

    public WhileDeclarationMetaData(IArgument check) {
        this.check = check;
    }

    /**
     * @return the check
     */
    public IArgument getCheck() {
        return check;
    }

    public void setCheck(IArgument check) {
        this.check = check;
    }

}
