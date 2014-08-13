/* 
 * DataRelationImpl.java
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

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
class DataRelationImpl implements DataRelation {
    
    final private Invocation sender;
    final private Invocation receiver;
    final private IArgument receiverArg;
    final private int receiverArgIndex;

    public DataRelationImpl(Invocation sender, Invocation receiver, IArgument receiverArg, int receiverArgIndex) {
        this.sender = sender;
        this.receiver = receiver;
        this.receiverArg = receiverArg;
        this.receiverArgIndex = receiverArgIndex;
    }



//    public void setSender(Invocation invocation) {
//        this.sender = invocation;
//    }

    @Override
    public Invocation getSender() {
        return sender;
    }


//    public void setReceiver(Invocation invocation) {
//        this.receiver = invocation;
//    }

    @Override
    public Invocation getReceiver() {
        return receiver;
    }

//    @Override
//    public String getInputVariable() {
//        return 
//    }
//
//    @Override
//    public void setInputVariable(String name) {
//        throw new UnsupportedOperationException("Not supported yet."); // TODO NB-AUTOGEN
//    }

    @Override
    public IArgument getReceiverArg() {
        return this.receiverArg;
    }

    @Override
    public int getReceiverArgIndex() {
       return this.receiverArgIndex;
    }
    
    @Override
    public String toString() {
        return "[" + sender +  " -> " + receiver + "]";
    }
    
}
