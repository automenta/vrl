/* 
 * OutputTextType.java
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
import vrl.reflection.TypeRepresentationBase;
import vrl.visual.VBoxLayout;
import vrl.visual.VCodeEditor;
import vrl.visual.VContainer;
import java.awt.Dimension;
import javax.swing.Box;


/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@TypeInfo(type=String.class, input = false, output = true, style="editor")
public class OutputTextType extends TypeRepresentationBase {

    private VCodeEditor editor;
    private int minimumEditorWidth = 80;
    private VContainer editorContainer = new VContainer();

    public OutputTextType() {

        VBoxLayout layout = new VBoxLayout(this, VBoxLayout.Y_AXIS);

        setLayout(layout);

        nameLabel.setText("Text:");
        nameLabel.setAlignmentX(0.5f);
        this.add(nameLabel);

        editor = new VCodeEditor(this);
        editor.getEditor().setEditable(false);

        editorContainer.add(editor);

        add(Box.createGlue());
        add(editorContainer);
        add(Box.createGlue());

        // add minimum editor width functionality
        editorContainer.setMinPreferredWidth(minimumEditorWidth);

        // set max size to prevent the editor to have arbitrary height
        editorContainer.setMaximumSize(
                new Dimension(Short.MAX_VALUE,
                editor.getPreferredSize().height));
    }

    @Override
    public void emptyView() {
        editor.getEditor().setText("");

        // set max size to prevent the editor to have arbitrary height
        editorContainer.setMaximumSize(
                new Dimension(Short.MAX_VALUE,
                editor.getPreferredSize().height));
    }

    @Override
    public void setViewValue(Object o) {
        emptyView();

        editor.getEditor().setText(o.toString());

        // set max size to prevent the editor to have arbitrary height
        editorContainer.setMaximumSize(
                new Dimension(Short.MAX_VALUE,
                editor.getPreferredSize().height));
    }

    @Override
    public Object getViewValue() {
        Object o = null;
        try {
            o = editor.getEditor().getText();
        } catch (Exception ex) {
        }
        return o;
    }

    /**
     * @return the editor
     */
    public VCodeEditor getEditor() {
        return editor;
    }

    /**
     * @param editor the editor to set
     */
    public void setEditor(VCodeEditor editor) {
        this.editor = editor;
    }

    /**
     * @return the minimumEditorWidth
     */
    public Integer getMinimumEditorWidth() {
        return minimumEditorWidth;
    }

    /**
     * @param minimumEditorWidth the minimumEditorWidth to set
     */
    public void setMinimumEditorWidth(Integer minimumEditorWidth) {
        this.minimumEditorWidth = minimumEditorWidth;
        editorContainer.setMinPreferredWidth(minimumEditorWidth);
        editor.revalidate();
    }
}
