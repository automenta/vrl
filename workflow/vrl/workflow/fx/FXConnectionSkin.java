/*
 * FXConnectionSkin.java
 * 
 * Copyright 2012-2013 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 * 
 * Please cite the following publication(s):
 *
 * M. Hoffer, C.Poliwoda, G.Wittum. Visual Reflection Library -
 * A Framework for Declarative GUI Programming on the Java Platform.
 * Computing and Visualization in Science, 2011, in press.
 *
 * THIS SOFTWARE IS PROVIDED BY Michael Hoffer <info@michaelhoffer.de> "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Michael Hoffer <info@michaelhoffer.de> OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of Michael Hoffer <info@michaelhoffer.de>.
 */
package vrl.workflow.fx;

import vrl.workflow.Connection;
import vrl.workflow.ConnectionResult;
import vrl.workflow.skin.ConnectionSkin;
import vrl.workflow.Connector;
import vrl.workflow.VFlow;
import vrl.workflow.VNode;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import jfxtras.labs.scene.control.window.Window;
import jfxtras.labs.util.event.MouseControlUtil;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class FXConnectionSkin implements ConnectionSkin<Connection>, FXSkin<Connection, Path> {

    private final ObjectProperty<Connector> senderProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Connector> receiverProperty = new SimpleObjectProperty<>();
    private final Path connectionPath;
    private final LineTo lineTo;
    private final MoveTo moveTo;
    private final CubicCurveTo curveTo;
//    private Shape startConnector;
    private final Circle receiverConnectorUI;
    private Window receiverWindow;
    private VFlow controller;
    private final Connection connection;
    private final ObjectProperty<Connection> modelProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Parent> parentProperty = new SimpleObjectProperty<>();
    private final String type;
    private Node lastNode;
    private boolean valid = true;
//    private Window clipboard;
    private Window prevWindow;
    private final FXSkinFactory skinFactory;
    private Shape senderNode;
    private Shape receiverNode;
    private ConnectionListener connectionListener;

    private boolean receiverDraggingStarted = false;

    public FXConnectionSkin(FXSkinFactory skinFactory, Parent parent, Connection connection, VFlow flow, String type) {
        setParent(parent);
        this.skinFactory = skinFactory;
        this.connection = connection;
        this.setModel(connection);
        this.controller = flow;
        this.type = type;

//        this.clipboard = clipboard;
//        startConnector = new Circle(20);
        receiverConnectorUI = new Circle(20);

        moveTo = new MoveTo();
        lineTo = new LineTo();
        curveTo = new CubicCurveTo();
        connectionPath = new Path(moveTo, curveTo);

        init();
    }

    private void init() {

        connectionPath.getStyleClass().setAll("vnode-connection", "vnode-connection-" + type);
        receiverConnectorUI.getStyleClass().setAll("vnode-connection-receiver", "vnode-connection-receiver-" + type);

//        connectionPath.setFill(new Color(120.0 / 255.0, 140.0 / 255.0, 1, 0.2));
//        connectionPath.setStroke(new Color(120 / 255.0, 140 / 255.0, 1, 0.42));
//        connectionPath.setStrokeWidth(5);
//        connectionPath.setStrokeLineCap(StrokeLineCap.ROUND);
//        receiverConnector.setFill(new Color(120.0 / 255.0, 140.0 / 255.0, 1, 0.2));
//        receiverConnector.setStroke(new Color(120 / 255.0, 140 / 255.0, 1, 0.42));
//        receiverConnector.setStrokeWidth(3);
        getReceiverUI().setFill(new Color(0, 1.0, 0, 0.0));
        getReceiverUI().setStroke(new Color(0, 1.0, 0, 0.0));

//        if (type.equals("control")) {
//            getReceiverUI().setFill(new Color(1.0, 1.0, 0.0, 0.75));
//            getReceiverUI().setStroke(new Color(120 / 255.0, 140 / 255.0, 1, 0.42));
//        } else if (type.equals("data")) {
//            getReceiverUI().setFill(new Color(0.1, 0.1, 0.1, 0.5));
//            getReceiverUI().setStroke(new Color(120 / 255.0, 140 / 255.0, 1, 0.42));
//        } else if (type.equals("event")) {
//            getReceiverUI().setFill(new Color(255.0 / 255.0, 100.0 / 255.0, 1, 0.5));
//            getReceiverUI().setStroke(new Color(120 / 255.0, 140 / 255.0, 1, 0.42));
//        }
        getReceiverUI().setStrokeWidth(3);

//        connectionPath.setStyle("-fx-background-color: rgba(120,140,255,0.2);-fx-border-color: rgba(120,140,255,0.42);-fx-border-width: 2;");
//        receiverConnector.setStyle("-fx-background-color: rgba(120,140,255,0.2);-fx-border-color: rgba(120,140,255,0.42);-fx-border-width: 2;");
//        final FlowNode sender = getController().getSender(connection);
//        final FlowNode receiver = getController().getReceiver(connection);
        final FXFlowNodeSkin senderSkin = (FXFlowNodeSkin) getController().getNodeSkinLookup().getById(skinFactory, connection.getSender().getId());
        final Window senderWindow = senderSkin.getNode();
        senderNode = (Shape) senderSkin.getConnectorNodeByReference(connection.getSender());

        FXFlowNodeSkin receiverSkin = (FXFlowNodeSkin) getController().getNodeSkinLookup().getById(skinFactory, connection.getReceiver().getId());
        receiverWindow = receiverSkin.getNode();
        receiverNode = (Shape) receiverSkin.getConnectorNodeByReference(connection.getReceiver());

        if (receiverNode.getParent() != senderNode.getParent()) {
            createIntermediateConnection(senderNode, receiverNode, connection);
        }

        addToClipboard();

        setSender(getController().getNodeLookup().getConnectorById(connection.getSender().getId()));
        setReceiver(getController().getNodeLookup().getConnectorById(connection.getReceiver().getId()));

        if (getReceiverNode() instanceof ConnectorCircle) {
            ConnectorCircle recConnNode = (ConnectorCircle) getReceiverNode();

            if (getReceiverUI() instanceof Circle) {
                ((Circle) getReceiverUI()).radiusProperty().
                        bind(recConnNode.radiusProperty());
            }
        }

        DoubleBinding startXBinding = new DoubleBinding() {
            {
                super.bind(getSenderNode().layoutXProperty());
            }

            @Override
            protected double computeValue() {

                return getSenderNode().getLayoutX();

            }
        };

        DoubleBinding startYBinding = new DoubleBinding() {
            {
                super.bind(getSenderNode().layoutYProperty());
            }

            @Override
            protected double computeValue() {
                return getSenderNode().getLayoutY();
            }
        };

        final DoubleBinding receiveXBinding = new DoubleBinding() {
            {
                // super.bind(receiverWindow.boundsInParentProperty());
                super.bind(getReceiverNode().layoutXProperty());
            }

            @Override
            protected double computeValue() {

//                Point2D location = NodeUtil.transformCoordinates(
//                        receiverWindow.getBoundsInParent().getMinX(),
//                        receiverWindow.getBoundsInParent().getMinY(), receiverWindow.getParent(), getParent());
//
//                return location.getX();
                return getReceiverNode().layoutXProperty().get();
            }
        };

        final DoubleBinding receiveYBinding = new DoubleBinding() {
            {
//                super.bind(
//                        receiverWindow.boundsInParentProperty(),
//                        receiverWindow.heightProperty());
                super.bind(getReceiverNode().layoutYProperty());
            }

            @Override
            protected double computeValue() {

                return getReceiverNode().layoutYProperty().get();
            }
        };

        DoubleBinding controlX1Binding = new DoubleBinding() {
            {
                super.bind(senderNode.boundsInLocalProperty(),
                        senderNode.layoutXProperty(), receiverConnectorUI.layoutXProperty());
            }

            @Override
            protected double computeValue() {

                return senderNode.getLayoutX()
                        + (receiverConnectorUI.getLayoutX() - senderNode.getLayoutX()) / 2;

            }
        };

        DoubleBinding controlY1Binding = new DoubleBinding() {
            {
                super.bind(senderNode.boundsInLocalProperty(),
                        senderNode.layoutYProperty());
            }

            @Override
            protected double computeValue() {

                return senderNode.getLayoutY();

            }
        };

        DoubleBinding controlX2Binding = new DoubleBinding() {
            {
                super.bind(senderNode.boundsInLocalProperty(),
                        senderNode.layoutXProperty(), receiverConnectorUI.layoutXProperty());
            }

            @Override
            protected double computeValue() {

                return receiverConnectorUI.getLayoutX()
                        - (receiverConnectorUI.getLayoutX() - senderNode.getLayoutX()) / 2;

            }
        };

        DoubleBinding controlY2Binding = new DoubleBinding() {
            {
                super.bind(receiverConnectorUI.boundsInLocalProperty(),
                        receiverConnectorUI.layoutYProperty());
            }

            @Override
            protected double computeValue() {

                return receiverConnectorUI.getLayoutY();

            }
        };

        moveTo.xProperty().bind(startXBinding);
        moveTo.yProperty().bind(startYBinding);

        curveTo.controlX1Property().bind(controlX1Binding);
        curveTo.controlY1Property().bind(controlY1Binding);
        curveTo.controlX2Property().bind(controlX2Binding);
        curveTo.controlY2Property().bind(controlY2Binding);
        curveTo.xProperty().bind(receiverConnectorUI.layoutXProperty());
        curveTo.yProperty().bind(receiverConnectorUI.layoutYProperty());

        makeDraggable(receiveXBinding, receiveYBinding);

//        receiverConnectorUI.setLayoutX(senderNode.getLayoutX());
//        receiverConnectorUI.setLayoutY(senderNode.getLayoutY());
        connectionListener
                = new ConnectionListenerImpl(
                        skinFactory, controller, receiverConnectorUI);

//        getReceiverUI().layoutXProperty().bind(receiveXBinding);
//        getReceiverUI().layoutYProperty().bind(receiveYBinding);
//
//        moveTo.xProperty().bind(startXBinding);
//        moveTo.yProperty().bind(startYBinding);
//
//        lineTo.xProperty().bind(getReceiverUI().layoutXProperty());
//        lineTo.yProperty().bind(getReceiverUI().layoutYProperty());
//
//        getReceiverUI().onMouseEnteredProperty().set(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent t) {
//                getReceiverUI().toFront();
//            }
//        });
//
//        getReceiverUI().onMouseExitedProperty().set(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent t) {
//                if (!t.isPrimaryButtonDown()) {
//                    getReceiverUI().toFront();
//                }
//            }
//        });
//
//        makeDraggable(receiveXBinding, receiveYBinding);
//
//        connectionListener =
//                new ConnectionListenerImpl(
//                skinFactory, controller, receiverConnectorUI);
    } // end init

    private void makeDraggable(
            final DoubleBinding receiveXBinding,
            final DoubleBinding receiveYBinding) {

        connectionPath.toFront();
        getReceiverUI().toFront();

        MouseControlUtil.makeDraggable(getReceiverUI(), new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {

                receiverDraggingStarted = true;

                if (lastNode != null) {
//                    lastNode.setEffect(null);
                    lastNode = null;
                }

                SelectedConnector selConnector
                        = FXConnectorUtil.getSelectedInputConnector(
                                getSender().getNode(),
                                getParent().getScene().getRoot(), type, t);

                valid = true;

                // reject connection if no main input defined for current node
                if (selConnector != null
                        && selConnector.getNode() != null
                        && selConnector.getConnector() == null) {
//                    DropShadow shadow = new DropShadow(20, Color.RED);
//                    Glow effect = new Glow(0.8);
//                    effect.setInput(shadow);
//                    selConnector.getNode().setEffect(effect);
                    connectionListener.onNoConnection(selConnector.getNode());
                    valid = false;
                    lastNode = selConnector.getNode();
                }

                if (selConnector != null
                        && selConnector.getNode() != null
                        && selConnector.getConnector() != null) {

                    Node n = selConnector.getNode();
                    n.toFront();
                    Connector receiver = selConnector.getConnector();

//                    prevWindow = w;
                    ConnectionResult connResult
                            = getSender().getNode().getFlow().tryConnect(
                                    getSender(), receiver);

                    if (connResult.getStatus().isCompatible()) {

//                        DropShadow shadow = new DropShadow(20, Color.WHITE);
//                        Glow effect = new Glow(0.5);
//                        shadow.setInput(effect);
//                        n.setEffect(shadow);
                        getReceiverUI().toFront();

                        if (lastNode != n) {
                            receiverConnectorUI.radiusProperty().unbind();
                            connectionListener.onConnectionCompatible(n);
                        }

                        valid = true;
                    } else {

//                        DropShadow shadow = new DropShadow(20, Color.RED);
//                        Glow effect = new Glow(0.8);
//                        effect.setInput(shadow);
//                        n.setEffect(effect);
                        connectionListener.onConnectionIncompatible();
                        valid = false;
                    }

                    getReceiverUI().toFront();

                    lastNode = n;

                } else {

                    if (lastNode == null) {
                        receiverConnectorUI.radiusProperty().unbind();
                        connectionListener.onNoConnection(receiverConnectorUI);
                    }

                }
            }
        }, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
                    getReceiverUI().layoutXProperty().unbind();
                    getReceiverUI().layoutYProperty().unbind();
                    receiverConnectorUI.radiusProperty().unbind();
                }
                connection.getReceiver().click(NodeUtil.mouseBtnFromEvent(event), event);
                receiverDraggingStarted = false;
            }
        });

        getReceiverUI().layoutXProperty().bind(receiveXBinding);
        getReceiverUI().layoutYProperty().bind(receiveYBinding);

        getReceiverUI().onMouseReleasedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {

                if (!receiverDraggingStarted) {
                    return;
                }

                if (lastNode != null) {
//                    lastNode.setEffect(null);
                    lastNode = null;
                }

//                if (!valid) {
//                    init();
//                    return;
//                }
                getReceiverUI().toFront();
                connectionPath.toBack();

                getReceiverUI().layoutXProperty().bind(receiveXBinding);
                getReceiverUI().layoutYProperty().bind(receiveYBinding);

//                receiverConnector.onMousePressedProperty().set(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent t) {
//                        makeDraggable(receiveXBinding, receiveYBinding);
//                    }
//                });
                SelectedConnector selConnector
                        = FXConnectorUtil.getSelectedInputConnector(
                                getSender().getNode(), getParent().getScene().getRoot(), type, t);

                if (selConnector != null
                        && selConnector.getNode() != null
                        && selConnector.getConnector() != null) {

                    Node n = selConnector.getNode();
                    n.toFront();
                    Connector receiverConnector = selConnector.getConnector();

                    ConnectionResult connResult = controller.connect(
                            getSender(), receiverConnector);

                    if (connResult.getStatus().isCompatible()) {
                        connectionListener.onCreateNewConnectionReleased(connResult);
                    }

                    if (connResult.getStatus().isCompatible()) {
                        //
                    } else {
                        connectionListener.onConnectionIncompatibleReleased(n);
                    }

                } else {
                    //
                }

                // remove error notification etc.
                if (controller.getConnections(type).contains(connection.getSender(),
                        connection.getReceiver())) {
                    connectionListener.onNoConnection(receiverConnectorUI);
                }

                remove();
                connection.getConnections().remove(connection);

            }
        });

    }

    @Override
    public Connector getSender() {
        return senderProperty.get();
    }

    @Override
    public final void setSender(Connector n) {
        senderProperty.set(n);
    }

    @Override
    public ObjectProperty<Connector> senderProperty() {
        return senderProperty;
    }

    @Override
    public Connector getReceiver() {
        return receiverProperty.get();
    }

    @Override
    public void setReceiver(Connector n) {
        receiverProperty.set(n);
    }

    @Override
    public ObjectProperty<Connector> receiverProperty() {
        return receiverProperty;
    }

    @Override
    public Path getNode() {
        return connectionPath;
    }

    @Override
    public Parent getContentNode() {
        return getParent();
    }

    @Override
    public final void setModel(Connection model) {
        modelProperty.set(model);
    }

    @Override
    public Connection getModel() {
        return modelProperty.get();
    }

    @Override
    public ObjectProperty<Connection> modelProperty() {
        return modelProperty;
    }

    final void setParent(Parent parent) {
        parentProperty.set(parent);
    }

    Parent getParent() {
        return parentProperty.get();
    }

    ObjectProperty<Parent> parentProperty() {
        return parentProperty;
    }

    @Override
    public void add() {
        NodeUtil.addToParent(getParent(), connectionPath);
//        VFXNodeUtils.addToParent(getParent(), startConnector);
        NodeUtil.addToParent(getParent(), getReceiverUI());

//        startConnector.toBack();
        getReceiverUI().toFront();
        connectionPath.toBack();
    }

    @Override
    public void remove() {
        if (connectionPath.getParent() == null || getReceiverUI().getParent() == null) {
            return;
        }
        try {
            NodeUtil.removeFromParent(connectionPath);
            NodeUtil.removeFromParent(getReceiverUI());
//            connection.getConnections().remove(connection);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    /**
     * @return the controller
     */
    @Override
    public VFlow getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    @Override
    public void setController(VFlow controller) {
        this.controller = controller;
    }

    private void addToClipboard() {
//        if (!valid) {
//            clipboard.setVisible(true);
//            if (prevWindow != null) {
//                clipboard.toFront();
//                clipboard.setLayoutX(prevWindow.getLayoutX());
//                clipboard.setLayoutY(prevWindow.getLayoutY());
//
//                Timeline timeLine = new Timeline();
//
//                KeyValue vx1 = new KeyValue(clipboard.layoutXProperty(), clipboard.getLayoutX());
//                KeyValue vy1 = new KeyValue(clipboard.layoutYProperty(), clipboard.getLayoutY());
//                KeyValue vx2 = new KeyValue(clipboard.layoutXProperty(), prevWindow.getLayoutX());
//                KeyValue vy2 = new KeyValue(clipboard.layoutYProperty(), prevWindow.getLayoutY() - 100);
//
//                timeLine.getKeyFrames().add(new KeyFrame(Duration.ZERO, vx1, vy1));
//                timeLine.getKeyFrames().add(new KeyFrame(Duration.millis(300), vx2, vy2));
//
//                timeLine.play();
//
//                timeLine.statusProperty().addListener(new ChangeListener<Animation.Status>() {
//                    @Override
//                    public void changed(ObservableValue<? extends Animation.Status> ov, Animation.Status t, Animation.Status t1) {
//                        if (t1 == Animation.Status.STOPPED) {
//
//                            DoubleBinding clipboardYBinding = new DoubleBinding() {
//                                {
//                                    super.bind(prevWindow.layoutYProperty());
//                                }
//
//                                @Override
//                                protected double computeValue() {
//
//                                    return prevWindow.getLayoutY() - 100;
//                                }
//                            };
//
////                            clipboard.layoutXProperty().unbind();
////                            clipboard.layoutYProperty().unbind();
////
////                            clipboard.layoutXProperty().bind(prevWindow.layoutXProperty());
////                            clipboard.layoutYProperty().bind(clipboardYBinding);
//                        }
//                    }
//                });
//
//
//            }
//
//            receiverWindow = clipboard;
//        } else {
//            clipboard.setVisible(false);
//        }
    }

    /**
     * @return the skinFatory
     */
    @Override
    public FXSkinFactory getSkinFactory() {
        return skinFactory;
    }

    public void toFront() {
        getReceiverUI().toFront();
    }

    /**
     * @return the receiverConnector
     */
    public Shape getReceiverUI() {
        return receiverConnectorUI;
    }

    /**
     * @return the senderNode
     */
    public Shape getSenderNode() {
        return senderNode;
    }

    /**
     * @return the receiverNode
     */
    public Shape getReceiverNode() {
        return receiverNode;
    }

    private void createIntermediateConnection(Shape senderNode, Shape receiverNode, Connection connection) {
        VNode sender = connection.getSender().getNode();
        VNode receiver = connection.getReceiver().getNode();
        
        throw new UnsupportedOperationException("Cannot visualize connection with different parent flows!");
    }
}
