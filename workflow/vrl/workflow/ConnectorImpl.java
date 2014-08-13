/*
 * ConnectorImpl.java
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
package vrl.workflow;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;

/**
 * This class provides the default implementation of a {@code Connector} in VWorkflows
 * 
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
class ConnectorImpl implements Connector {

    private VNode node;
    private String type;
    private String localId;
    private VisualizationRequest vRequest;
    private boolean input;
    private boolean output;
    private final ObjectProperty<ValueObject> valueObjectProperty = new SimpleObjectProperty<>();
    private transient List<EventHandler<ConnectionEvent>> connectionEventHandlers;
    private transient List<EventHandler<ClickEvent>> clickEventHandlers;

    public ConnectorImpl(VNode node, String type, String localId, boolean input) {
        this.type = type;
        this.localId = localId;
        this.node = node;
        this.input = input;
        this.output = !input;
        setValueObject(new DefaultConnectorValueObject(this));
    }

    public ConnectorImpl(VNode node, Connector c) {
        this(node, c.getType(), c.getLocalId(), c.isInput());
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getId() {
        return this.node.getId() + ":" + this.localId;
    }

    @Override
    public String getLocalId() {
        return this.localId;
    }

    @Override
    public void setLocalId(String id) {
        this.localId = id;
    }

    @Override
    public VNode getNode() {
        return this.node;
    }

    @Override
    public VisualizationRequest getVisualizationRequest() {
        return this.vRequest;
    }

    @Override
    public void setVisualizationRequest(VisualizationRequest vReq) {
        this.vRequest = vReq;
    }

    /**
     * @return the input
     */
    @Override
    public boolean isInput() {
        return input;
    }

    /**
     * @return the output
     */
    @Override
    public boolean isOutput() {
        return output;
    }

    @Override
    public final void setValueObject(ValueObject vObj) {
        valueObjectProperty().set(vObj);
    }

    @Override
    public ValueObject getValueObject() {
        return valueObjectProperty().get();
    }

    @Override
    public ObjectProperty<ValueObject> valueObjectProperty() {
        return this.valueObjectProperty;
    }

    private List<EventHandler<ConnectionEvent>> getEventHandlers() {
        if (this.getConnectionEventHandlers() == null) {
            this.connectionEventHandlers = new ArrayList<>();
        }

        return this.getConnectionEventHandlers();
    }

    @Override
    public void addConnectionEventListener(EventHandler<ConnectionEvent> handler) {
        getEventHandlers().add(handler);
    }

    @Override
    public void removeConnectionEventListener(EventHandler<ConnectionEvent> handler) {
        getEventHandlers().remove(handler);

        // we throw unused lists away since this can lead to serious memory 
        // overhead for large flows
        if (getEventHandlers().isEmpty()) {
            this.connectionEventHandlers = null;
        }
    }

    /**
     * @return the connectionEventHandlers
     */
    public List<EventHandler<ConnectionEvent>> getConnectionEventHandlers() {
        return connectionEventHandlers;
    }

    @Override
    public void addClickEventListener(EventHandler<ClickEvent> handler) {
        getClickEventHandlers().add(handler);
    }

    @Override
    public void removeClickEventListener(EventHandler<ClickEvent> handler) {
        getClickEventHandlers().remove(handler);

        // we throw unused lists away since this can lead to serious memory 
        // overhead for large flows
        if (getClickEventHandlers().isEmpty()) {
            this.clickEventHandlers = null;
        }
    }

    /**
     * @return the clickEventHandlers
     */
    public List<EventHandler<ClickEvent>> getClickEventHandlers() {
        if (clickEventHandlers == null) {
            this.clickEventHandlers = new ArrayList<>();
        }
        return clickEventHandlers;
    }

    @Override
    public void click(MouseButton btn, Object event) {

        if (clickEventHandlers == null) {
            return;
        }

        ClickEvent evt = new ClickEvent(ClickEvent.ANY, this, btn, event);

        for (EventHandler<ClickEvent> evth : clickEventHandlers) {
            evth.handle(evt);
        }
    }
}
