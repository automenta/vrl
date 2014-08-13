/*
 * Connector.java
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

import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;

/**
 * This interface describes a connector. A connector is used to link nodes together.
 * It serves as the input or output for the node. Methods allow you to detect
 * whether the connector has been clicked or if it is used in a connection.
 * 
 * @author Michael Hoffer  &lt;info@michaelhoffer.de&gt;
 */
public interface Connector extends Model {

    public String getType();

    public boolean isInput();

    public boolean isOutput();

    public String getId();

    public String getLocalId();

    public void setLocalId(String id);

    public VNode getNode();

    public void setValueObject(ValueObject obj);

    public ValueObject getValueObject();

    public ObjectProperty<ValueObject> valueObjectProperty();
    
    public void addConnectionEventListener(EventHandler<ConnectionEvent> handler);
    public void removeConnectionEventListener(EventHandler<ConnectionEvent> handler);
    
    public void addClickEventListener(EventHandler<ClickEvent> handler);
    public void removeClickEventListener(EventHandler<ClickEvent> handler);
    
    public void click(MouseButton btn, Object event);
}
