/* 
 * VDialogContent.java
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

package vrl.visual;

import vrl.system.VParamUtil;
import vrl.visual.VDialog.AnswerType;
import javax.swing.JComponent;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */


public class VDialogContent implements DialogContent{
    private JComponent content;
    private AnswerType answer;
    private VDialogWindow dialog;
    private int index;
    private DialogAnswerListener listener;

    public VDialogContent(JComponent content) {
        
        VParamUtil.throwIfNull(content);
        
        this.content = content;
    }
    
     public VDialogContent(JComponent content, DialogAnswerListener listener) {
        
        VParamUtil.throwIfNull(content);
        
        this.content = content;
        this.listener = listener;
    }

    @Override
    public JComponent getContent() {
        return content;
    }

    @Override
    public boolean answered(AnswerType answer, int index) {
        this.answer = answer;
        this.index = index;
        
        if (listener!=null) {
            return listener.answered(answer, index);
        }
        
        return true;
    }

    /**
     * @return the answer
     */
    @Override
    public AnswerType getAnswer() {
        return answer;
    }

    /**
     * @param dialog the dialog to set
     */
    @Override
    public void setDialog(VDialogWindow dialog) {
        this.dialog = dialog;
    }

    /**
     * @return the dialog
     */
    public VDialogWindow getDialog() {
        return dialog;
    }

    @Override
    public int getAnswerIndex() {
        return this.index;
    }
    
}
