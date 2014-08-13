/* 
 * GroovyCodeWindow.java
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

package vrl.lang.groovy;


import vrl.dialogs.RecompileClassDialog;
import vrl.annotation.ComponentInfo;
import vrl.io.vrlx.AbstractCode;
import vrl.lang.CompilerNotFoundException;
import vrl.lang.CompilerProvider;
import vrl.lang.visual.EditorProvider;
import vrl.lang.InstanceCreator;
import vrl.lang.VCompiler;
import vrl.reflection.TypeRepresentationBase;
import vrl.reflection.VisualCanvas;
import vrl.reflection.VisualObject;
import vrl.visual.CanvasActionListener;
import vrl.visual.CanvasWindow;
import vrl.visual.VButton;
import vrl.visual.VCodeEditor;
import vrl.visual.VContainer;
import vrl.visual.VDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EmptyBorder;
import vrl.lang.VLangUtils;

/**
 * Defines a code window for editing and compiling groovy code.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class GroovyCodeWindow extends CanvasWindow {

    private static final long serialVersionUID = -9207398420942890168L;
    private VCodeEditor editor;

    /**
     * Constructor.
     * @param mainCanvas the main canvas object
     */
    public GroovyCodeWindow(final VisualCanvas mainCanvas) {
        super("Groovy Code", mainCanvas);

        String code = "";
        init(mainCanvas);
        setCode(code);
    }

    /**
     * Constructor.
     * @param mainCanvas the main canvas object
     * @param code the code to show
     */
    public GroovyCodeWindow(final VisualCanvas mainCanvas, String code) {
        super("Groovy Code", mainCanvas);
        init(mainCanvas);
        setCode(code);
    }

    /**
     * Defines the code to show.
     * @param code the code to show
     */
    public void setCode(String code) {
        getEditor().getEditor().setText(code);
    }

    /**
     * Compiles the specified code and adds an instance to the canvas if
     * possible.
     */
    private void compileAndAddToCanvas() {

        final VisualCanvas visualCanvas = (VisualCanvas) getMainCanvas();

        AbstractCode code = new AbstractCode();

        code.setCode(getEditor().getEditor().getText());
        code.setLanguage(CompilerProvider.LANG_GROOVY);

        final VCompiler compiler;
        try {
            compiler = visualCanvas.getCompilerProvider().
                    getCompiler(code.getLanguage());
        } catch (CompilerNotFoundException ex) {
            Logger.getLogger(GroovyCodeWindow.class.getName()).
                    log(Level.SEVERE, null, ex);
            return;
        }

        Class<?> checkIfAlreadyExistentClass =
                compiler.compile(code.getCode(), getEditor());
        //
        // check whether the previous version of this class is already in use,
        // i.e., if instances of it exist
        @SuppressWarnings("unchecked")
        Collection<Object> objects =
                visualCanvas.getInspector().
                getObjectsByClassName(checkIfAlreadyExistentClass.getName());

        System.out.println("Instances: " + objects.size());
        
        boolean codeDefinedInSession = visualCanvas.getCodes().getByName(                
                VLangUtils.classNameFromCode(code))!=null;
        
        boolean classFileExists = visualCanvas.getProjectController().
                getProject().getClassFileByEntryName(VLangUtils.
                classNameFromCode(code))!=null;
        
        if (classFileExists && !codeDefinedInSession) {
            VDialog.showMessageDialog(visualCanvas, "Cannot compile:",
                    "A class with the same name is "
                    + "already defined in another session!");
            return;
        }

        boolean compile = checkIfAlreadyExistentClass != null;

        // if class already in use ask whether to recompile
        if (objects.size() > 0) {
            compile =
                    RecompileClassDialog.show(visualCanvas,
                    checkIfAlreadyExistentClass.getName());
        }

        if (compile) {

            code.setRecompile(true);

            final Class<?> c = code.addToClassPath(visualCanvas, getEditor());

            code.setName(c.getName());
            visualCanvas.getCodes().add(code);

            boolean instantiate = true;

            // analyse annotations and try to find component info
            for (Annotation a : c.getAnnotations()) {
                if (a.annotationType().equals(ComponentInfo.class)) {
                    ComponentInfo cInfo = (ComponentInfo) a;
                    instantiate = cInfo.instantiate();
                }
            }

            instantiate = instantiate && !c.isInterface();

            Object object = null;

            if (instantiate) {
                InstanceCreator creator = new InstanceCreator();
                object = creator.newInstance(c);
            }

            if (object != null && object instanceof TypeRepresentationBase) {
                
                @SuppressWarnings("unchecked")
                Class<? extends TypeRepresentationBase> typeClass =
                        (Class<? extends TypeRepresentationBase>) c;
                visualCanvas.getTypeFactory().addType(typeClass);

                visualCanvas.getWindows().removeObject(
                        GroovyCodeWindow.this);
            } else if (!instantiate) {
                visualCanvas.getWindows().removeObject(
                        GroovyCodeWindow.this);
            } else if (object != null) {

                // try to replace instances
                try {
                    visualCanvas.getInspector().replaceAllObjects(c,
                            new InstanceCreator(visualCanvas));
                    visualCanvas.getWindows().removeObject(
                            GroovyCodeWindow.this);

                    return;
                } catch (Exception ex) {
                    // interface changed or instanciation failed
                }

                VisualObject window = visualCanvas.addObject(
                        object);

                window.addSourceIcon();


                int winPosX = GroovyCodeWindow.this.getX()
                        - (window.getWidth()
                        - GroovyCodeWindow.this.getWidth()) / 2;

                int winPosY = GroovyCodeWindow.this.getY();

                // the window will be centered relative
                // to the code window
                window.setLocation(winPosX, winPosY);

                visualCanvas.getWindows().removeObject(
                        GroovyCodeWindow.this);

                // delete old instances
                for (Object obj : objects) {
                    for (CanvasWindow vObj :
                            visualCanvas.getInspector().getCanvasWindows(obj)) {
                        vObj.close();
                    }
                }
            } // end if object !=null

            if (visualCanvas.getPopupMenu() != null) {
                visualCanvas.getComponentController().addComponent(c);
            }
        } // end if compile
    }

    /**
     * Initializes the window.
     * @param mainCanvas the main canvas object
     */
    private void init(final VisualCanvas mainCanvas) {

        editor = EditorProvider.getEditor(CompilerProvider.LANG_GROOVY, this);

        getEditor().getEditor().addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent arg0) {
                //throw new UnsupportedOperationException("Not supported yet.");
                if (arg0.isShiftDown()
                        && arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                    compileAndAddToCanvas();
                }
            }
        });

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent arg0) {
                if (arg0.isShiftDown()
                        && arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                    compileAndAddToCanvas();
                }
            }
        });

        this.addActionListener(new CanvasActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(CanvasWindow.ACTIVE_ACTION)) {
                    getEditor().getEditor().requestFocus();
                }
                if (e.getActionCommand().equals(CanvasWindow.MAXIMIZE_ACTION)) {
                    getEditor().getEditor().requestFocus();
                }
            }
        });

        EmptyBorder border = new EmptyBorder(5, 5, 5, 5);

        VContainer container = new VContainer(getEditor());

        container.setBorder(border);

        add(container);

        VButton button = new VButton("Compile");

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                compileAndAddToCanvas();
            }
        });

        button.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent arg0) {
                if (arg0.isShiftDown()
                        && arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                    compileAndAddToCanvas();
                }
            }
        });

        VContainer buttonContainer = new VContainer(button);

        buttonContainer.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(buttonContainer);
    }

    /**
     * @return the editor
     */
    public VCodeEditor getEditor() {
        return editor;
    }

    /**
     * @return the text
     */
    public String getCode() {
        return editor.getEditor().getText();
    }
}
