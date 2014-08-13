/* 
 * MainWindowController.java
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
package vrl.codevisualization;

import vrl.lang.workflow.WorkflowUtil;
import com.thoughtworks.xstream.XStream;
import vrl.lang.model.Scope2Code;
import vrl.lang.model.ScopeInvocation;
import vrl.lang.model.UIBinding;
import vrl.lang.model.Variable;
import vrl.lang.model.CodeEntity;
import vrl.lang.model.CodeEventType;
import vrl.lang.model.Comment;
import vrl.lang.model.CommentType;
import vrl.lang.model.CompilationUnitDeclaration;
import vrl.lang.model.ForDeclaration;
import vrl.lang.model.Invocation;
import vrl.lang.model.Scope;
import vrl.lang.model.WhileDeclaration;
import vrl.workflow.Connection;
import vrl.workflow.Connections;
import vrl.workflow.Connector;
import vrl.workflow.FlowFactory;
import vrl.workflow.VFlow;
import vrl.workflow.VFlowModel;
import vrl.workflow.VNode;
import vrl.workflow.fx.FXSkinFactory;
import vrl.workflow.fx.ScalableContentPane;
import groovy.lang.GroovyClassLoader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooserBuilder;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class MainWindowController implements Initializable {

    private File currentDocument;
    @FXML
    private TextArea editor;
    @FXML
    private Pane view;
    private Pane rootPane;
    private VFlow flow;

    private final Map<String, LayoutData> layoutData = new HashMap<>();

    private final Set<String> loadLayoutIds = new HashSet<>();

    private FileAlterationMonitor fileMonitor;
    private FileAlterationObserver observer;

    private Stage mainWindow;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("Init");

        ScalableContentPane canvas = new ScalableContentPane();
        canvas.setStyle("-fx-background-color: rgb(0,0, 0)");

        canvas.setMaxScaleX(1);
        canvas.setMaxScaleY(1);

        view.getChildren().add(canvas);

        Pane root = new Pane();
        canvas.setContentPane(root);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(10,32,60), rgb(42,52,120));");

        rootPane = root;

        flow = FlowFactory.newFlow();
        flow.setVisible(true);
        UIBinding.setRootFlow(flow);

        FXSkinFactory skinFactory = new FXSkinFactory(rootPane);
        flow.setSkinFactories(skinFactory);

        fileMonitor = new FileAlterationMonitor(3000);

        try {
            fileMonitor.start();
        } catch (Exception ex) {
            Logger.getLogger(MainWindowController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        flow.getConnections(WorkflowUtil.CONTROL_FLOW).getConnections().addListener(
                (ListChangeListener.Change<? extends Connection> c) -> {
                    updateCode((Scope) UIBinding.getRootFlow().getModel().getValueObject().getValue());
                    System.out.println("changed cflow: " + c);
                });

        flow.getConnections(WorkflowUtil.DATA_FLOW).getConnections().addListener(
                (ListChangeListener.Change<? extends Connection> c) -> {
                    updateCode((Scope) UIBinding.getRootFlow().getModel().getValueObject().getValue());
                    System.out.println("changed dflow: " + c);
                });

//        VCodeEditor vEditor = new VCodeEditor(" the code ");
//        
//        canvas.getContentPane().getChildren().add(vEditor.getNode());
    }

    @FXML
    public void onKeyTyped(KeyEvent evt) {
//        String output = editor.getText();
//
//        output = MultiMarkdown.convert(output);
//
//        System.out.println(output);
//
//        
//
//        URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
//
//            @Override
//            public URLStreamHandler createURLStreamHandler(String protocol) {
//                
//            }
//        });
//        
//        
//        outputView.getEngine().s
    }

    @FXML
    public void onLoadAction(ActionEvent e) {
        loadTextFile(null);
    }

    @FXML
    public void onSaveAction(ActionEvent e) {
        updateCode(UIBinding.scopes.values().iterator().next().get(0));

        updateView();

        saveDocument(false);
    }

    private void saveDocument(boolean askForLocationIfAlreadyOpened) {

        if (askForLocationIfAlreadyOpened || currentDocument == null) {
            FileChooser.ExtensionFilter mdFilter
                    = new FileChooser.ExtensionFilter(
                            "Text Files (*.groovy, *.java, *.txt)",
                            "*.groovy", "*.txt", "*.java");

            FileChooser.ExtensionFilter allFilesfilter
                    = new FileChooser.ExtensionFilter("All Files (*.*)", "*.*");

            currentDocument
                    = FileChooserBuilder.create().title("Save Groovy File").
                    extensionFilters(mdFilter, allFilesfilter).build().
                    showSaveDialog(null).getAbsoluteFile();
        }

        try (FileWriter fileWriter = new FileWriter(currentDocument)) {
            fileWriter.write(editor.getText());
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

    }

    private void insertStringAtCurrentPosition(String s) {
        editor.insertText(editor.getCaretPosition(), s);
    }

    @FXML
    public void onSaveAsAction(ActionEvent e) {
        saveDocument(true);
//        updateView();
    }

    @FXML
    public void onCloseAction(ActionEvent e) {
    }

    void loadTextFile(File f) {

        try {
            if (f == null) {
                FileChooser.ExtensionFilter mdFilter
                        = new FileChooser.ExtensionFilter(
                                "Text Files (*.groovy, *.java, *.txt)",
                                "*.groovy", "*.txt", "*.java");

                FileChooser.ExtensionFilter allFilesfilter
                        = new FileChooser.ExtensionFilter(
                                "All Files (*.*)", "*.*");

                currentDocument
                        = FileChooserBuilder.create().title("Open Groovy File").
                        extensionFilters(mdFilter, allFilesfilter).build().
                        showOpenDialog(null).getAbsoluteFile();
            } else {
                currentDocument = f;
            }

            editor.setText(new String(Files.readAllBytes(
                    Paths.get(currentDocument.getAbsolutePath())), "UTF-8"));

            updateView();

        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        if (observer != null) {
            fileMonitor.removeObserver(observer);
        }

        observer = new FileAlterationObserver(
                currentDocument.getAbsoluteFile().getParentFile());
        observer.addListener(new FileAlterationListenerAdaptor() {
            @Override
            public void onFileChange(File file) {

                if (file.equals(currentDocument.getAbsoluteFile())) {

                    Platform.runLater(() -> {
                        try {
                            editor.setText(new String(
                                    Files.readAllBytes(
                                            Paths.get(currentDocument.getAbsolutePath())),
                                    "UTF-8"));
                            updateView();
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(MainWindowController.class.getName()).
                                    log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MainWindowController.class.getName()).
                                    log(Level.SEVERE, null, ex);
                        }
                    });
                }
            }
        });

        fileMonitor.addObserver(observer);

    }

    private void updateView() {

        savePositions();

        if (rootPane == null) {
            System.err.println("UI NOT READY");
            return;
        }

        UIBinding.scopes.clear();
        UIBinding.getRootFlow().clear();

        GroovyClassLoader gcl = new GroovyClassLoader();
        gcl.parseClass(
                "@eu.mihosoft.vrl.instrumentation.VRLVisualization\n"
                + editor.getText(), "Script");

        loadUIData();

        if (UIBinding.scopes == null) {
            System.err.println("NO SCOPES");
            return;
        }

        for (List<Scope> sList : UIBinding.scopes.values()) {
            for (Scope scope : sList) {
                scope.generateDataFlow();
                scope.addEventHandler(CodeEventType.CHANGE, evt -> {
                    updateCode((CompilationUnitDeclaration) UIBinding.scopes.values().
                            iterator().next().get(0));
                });
            }
        }

        applyPositions();

        saveUIData();

    }

    private void savePositions() {

        layoutData.clear();

//        nodeToScopes.keySet().
//                forEach(id -> savePosition(flow.getNodeLookup().getById(id)));
//        nodeInvocations.keySet().
//                forEach(id -> savePosition(flow.getNodeLookup().getById(id)));
        if (UIBinding.scopes.values().
                iterator().hasNext()) {
            CompilationUnitDeclaration cud
                    = (CompilationUnitDeclaration) UIBinding.scopes.values().
                    iterator().next().get(0);

            cud.visitScopeAndAllSubElements(s -> {
                savePosition(s);
            });
        }

    }

    private void applyPositions() {
//        nodeToScopes.keySet().
//                forEach(id -> applyPosition(flow.getNodeLookup().getById(id)));
//        nodeInvocations.keySet().
//                forEach(id -> applyPosition(flow.getNodeLookup().getById(id)));

        if (UIBinding.scopes.values().
                iterator().hasNext()) {
            CompilationUnitDeclaration cud
                    = (CompilationUnitDeclaration) UIBinding.scopes.values().
                    iterator().next().get(0);

            cud.visitScopeAndAllSubElements(s -> {
                applyPosition(s);
            });
        }
    }

    private boolean applyPosition(CodeEntity cE) {
        Objects.requireNonNull(cE);

        if (cE.getNode() == null) {
            return false;
        }

        String id = codeId(cE, true);

        LayoutData d = layoutData.get(id);

        if (d == null) {
            System.out.println("cannot load pos for [" + cE.getId() + ", " + id + "]");
            return false;
        }

        d.apply(cE.getNode());

        return true;
    }

    private void savePosition(CodeEntity cE) {

        Objects.requireNonNull(cE);

        if (cE.getNode() == null) {
            return;
        }

        layoutData.put(
                codeId(cE, false),
                new LayoutData(cE.getNode()));

    }

    private void saveUIData() {
        XStream xstream = new XStream();
        xstream.alias("layout", LayoutData.class);
        String data = xstream.toXML(layoutData);

        CompilationUnitDeclaration cud
                = (CompilationUnitDeclaration) UIBinding.scopes.values().
                iterator().next().get(0);

        Predicate<Comment> vrlLayoutType = (Comment c) -> {
            return c.getType() == CommentType.VRL_MULTI_LINE
                    && c.getComment().contains("<!VRL!><Type:VRL-Layout>");
        };

        Predicate<Comment> editorFoldType = (Comment c) -> {
            return c.getComment().contains("<editor-fold")
                    || c.getComment().contains("</editor-fold");
        };

        List<Comment> toBeRemoved = cud.getComments().stream().
                filter(vrlLayoutType.or(editorFoldType)).
                collect(Collectors.toList());

        toBeRemoved.forEach(c -> System.out.println("out: " + c.getType()));

        cud.getComments().removeAll(toBeRemoved);

        updateCode(cud);
        editor.setText(editor.getText()
                + "// <editor-fold defaultstate=\"collapsed\" desc=\"VRL-Data\">\n"
                + "/*<!VRL!><Type:VRL-Layout>\n" + data + "\n*/\n"
                + "// </editor-fold>");
    }

    private void loadUIData() {
        try {
            CompilationUnitDeclaration cud
                    = (CompilationUnitDeclaration) UIBinding.scopes.values().
                    iterator().next().get(0);

            Predicate<Comment> vrlLayoutType = (Comment c) -> {
                return c.getType()
                        == CommentType.VRL_MULTI_LINE
                        && c.getComment().contains("<!VRL!><Type:VRL-Layout>");
            };

            Optional<Comment> vrlC = cud.getComments().stream().
                    filter(vrlLayoutType).findFirst();

            if (vrlC.isPresent()) {
                String vrlComment = vrlC.get().getComment();
                vrlComment = vrlComment.substring(26, vrlComment.length() - 2);
                XStream xstream = new XStream();
                xstream.alias("layout", LayoutData.class);
                layoutData.clear();
                loadLayoutIds.clear();
                layoutData.putAll(
                        (Map<String, LayoutData>) xstream.fromXML(vrlComment));
            } else {
                System.err.println("-> cannot load layout - not present!");
            }
        } catch (Exception ex) {
            System.err.println(
                    "-> cannot load layout - exception while loading!");
        }
    }

    private String codeId(CodeEntity cE, boolean load) {

        List<String> parts = new ArrayList<>();

        parts.add(entityId(cE));

        while (cE.getParent() != null) {
            cE = cE.getParent();
            parts.add(entityId(cE));
        }

        Collections.reverse(parts);

        String str = String.join(":", parts);

        Set<String> set;

        if (load) {
            set = loadLayoutIds;
        } else {
            set = layoutData.keySet();
        }

        str = incCodeId(set, str);

        if (load) {
            set.add(str);
        }

//        System.out.println("id " + cE.getId() + " -> " + str);
        return str;
    }

    private String entityId(CodeEntity cE) {
        if (cE instanceof Invocation) {
            if (cE instanceof ScopeInvocation) {
                ScopeInvocation scopeInv = (ScopeInvocation) cE;

                entityId(scopeInv.getScope());
            }

            return "inv:" + ((Invocation) cE).getMethodName();
        } else if (cE instanceof ForDeclaration) {
            return "for:var=" + ((ForDeclaration) cE).getVarName();
        } else if (cE instanceof WhileDeclaration) {
            return "while";
        } else if (cE instanceof Scope) {
            return ((Scope) cE).getName();
        }

        return "";
    }

    public String incCodeId(Set<String> ids, String id) {

        if (!ids.contains(id)) {

            return id;
        }

        int counter = 0;
        String result = id + ":" + counter;

        while (ids.contains(result)) {
            counter++;
            result = id + ":" + counter;
        }

        return result;
    }

    private boolean isRoot(VNode node, String connectionType) {

        Predicate<Connector> notConnected = (Connector c) -> {
            return c.getType().equals(connectionType)
                    && !c.getNode().getFlow().
                    getConnections(connectionType).
                    getAllWith(c).isEmpty();
        };

        Predicate<VNode> rootNode = (VNode n) -> {
            return n.getInputs().filtered(notConnected).isEmpty();
        };

        return rootNode.test(node);
    }

    private void updateCode(Scope rootScope) {
        System.out.println("Scope: UpdateCode");
        String code = Scope2Code.getCode(
                (CompilationUnitDeclaration) getRootScope(rootScope));
        editor.setText(code);
    }

    private Scope getRootScope(Scope s) {
        Scope root = s;

        while (s.getParent() != null) {
            root = s.getParent();
            s = root;
        }

        return root;
    }

    private List<VNode> getPath(VNode sender, String connectionType) {

        List<VNode> result = new ArrayList<>();

        if (!isRoot(sender, connectionType)) {
            System.err.println("sender is no root!");
            return result;
        }

        result.add(sender);

        Connections connections = sender.getFlow().getConnections(connectionType);
        Collection<Connection> connectionsWithSender
                = connections.getAllWith(sender.getMainOutput(connectionType));

        while (!connectionsWithSender.isEmpty()) {

            VNode newSender = null;

            for (Connection c : connectionsWithSender) {

                if (newSender == c.getReceiver().getNode()) {
                    System.err.println("circular flow!");
                    return result;
                }

                newSender = c.getReceiver().getNode();

                result.add(newSender);
                break; // we only support one connection per controlflow conector
            }

            if (newSender != null) {
                connectionsWithSender
                        = connections.getAllWith(
                                newSender.getMainOutput(connectionType));
            } else {
                connectionsWithSender.clear();
            }
        }

        return result;
    }

    public static String getVariableId(VNode n, Variable v) {
        String id = n.getId() + ":" + v.getName();

        System.out.println("id: " + id);

        return id;
    }

    public static String getVariableId(VNode n, String varName) {
        String id = n.getId() + ":" + varName;

        System.out.println("id: " + id);

        return id;
    }

    /**
     * @return the mainWindow
     */
    public Stage getMainWindow() {
        return mainWindow;
    }

    /**
     * @param mainWindow the mainWindow to set
     */
    public void setMainWindow(Stage mainWindow) {
        this.mainWindow = mainWindow;

        mainWindow.setOnCloseRequest((WindowEvent event) -> {
            try {
                fileMonitor.stop();
            } catch (Exception ex) {
                Logger.getLogger(MainWindowController.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        });
    }
}

class LayoutData {

    private double x;
    private double y;
    private double width;
    private double height;
    private boolean contentVisible;

    public LayoutData() {
    }

    public LayoutData(VNode n) {

        Objects.requireNonNull(n);

        this.x = n.getX();
        this.y = n.getY();
        this.width = n.getWidth();
        this.height = n.getHeight();

        if (n instanceof VFlowModel) {
            this.contentVisible = ((VFlowModel) n).isVisible();
        } else {
            this.contentVisible = true;
        }
    }

    public LayoutData(double x, double y, double width, double height, boolean contentVisible) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.contentVisible = contentVisible;
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(double height) {
        this.height = height;
    }

    public void apply(VNode n) {
        n.setX(x);
        n.setY(y);
        n.setWidth(width);
        n.setHeight(height);

        if (n instanceof VFlowModel) {
            ((VFlowModel) n).setVisible(contentVisible);
        }
    }
}
