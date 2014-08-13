/*
 * VFlow.java
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

import vrl.workflow.skin.SkinFactory;
import vrl.workflow.skin.FlowNodeSkinLookup;
import vrl.workflow.skin.VNodeSkin;
import vrl.workflow.skin.ConnectionSkin;
import java.util.Collection;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 * The {@code VFlow} interface describes a workflow. A workflow is a network of
 * {@code VNode}s. {@code VNode}s are connected using {@code Connector}s.
 * 
 * @author Michael Hoffer  &lt;info@michaelhoffer.de&gt;
 */
public interface VFlow {

    public void setModel(VFlowModel flow);

    public void setNodeLookup(NodeLookup nodeLookup);

    public NodeLookup getNodeLookup();

    public VFlowModel getModel();

    public ObjectProperty modelProperty();

    public ConnectionResult tryConnect(Connector s, Connector r);

    public ConnectionResult connect(Connector s, Connector r);

    public ConnectionResult tryConnect(VNode s, VNode r, String flowType);

    public ConnectionResult connect(VNode s, VNode r, String flowType);

    public ConnectionResult tryConnect(VFlow s, VNode r, String flowType);

    public ConnectionResult tryConnect(VNode s, VFlow r, String flowType);

    public ConnectionResult tryConnect(VFlow s, VFlow r, String flowType);

    public ConnectionResult connect(VFlow s, VNode r, String flowType);

    public ConnectionResult connect(VNode s, VFlow r, String flowType);

    public ConnectionResult connect(VFlow s, VFlow r, String flowType);

    public VNode remove(VNode n);

    public ObservableList<VNode> getNodes();

    public void clear();

    public VNode getSender(Connection c);

    public VNode getReceiver(Connection c);

    public void addConnections(Connections connections, String flowType);

    public Connections getConnections(String flowType);

    public ObservableMap<String, Connections> getAllConnections();

    public void setFlowNodeClass(Class<? extends VNode> cls);

    public Class<? extends VNode> getFlowNodeClass();

    public VNode newNode(ValueObject obj);

    public VNode newNode();

    public VFlow newSubFlow(ValueObject obj);

    public VFlow newSubFlow();

    public Collection<VFlow> getSubControllers();

    // Doesn't use Generics because generic arrays are not supported. GENERICS ARE CRAPPY!
    // see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6227971
    public void setSkinFactories(SkinFactory... skinFactory);

    public void setSkinFactories(Collection<SkinFactory<? extends ConnectionSkin, ? extends VNodeSkin>> skinFactories);

    public Collection<SkinFactory<? extends ConnectionSkin, ? extends VNodeSkin>> getSkinFactories();

    // Doesn't use Generics because generic arrays are not supported. GENERICS ARE CRAPPY!
    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6227971
    public void addSkinFactories(SkinFactory... skinFactory);

    public void addSkinFactories(Collection<SkinFactory<? extends ConnectionSkin, ? extends VNodeSkin>> skinFactories);

    public void removeSkinFactories(SkinFactory<? extends ConnectionSkin, ? extends VNodeSkin>... skinFactory);

    public void removeSkinFactories(Collection<SkinFactory<? extends ConnectionSkin, ? extends VNodeSkin>> skinFactories);

    public void setIdGenerator(IdGenerator generator);

    public IdGenerator getIdGenerator();

    public List<VNodeSkin> getNodeSkinsById(String id);

    public FlowNodeSkinLookup getNodeSkinLookup();

    public void setNodeSkinLookup(FlowNodeSkinLookup skinLookup);

    public void setVisible(boolean state);

    public boolean isVisible();

    public BooleanProperty visibleState();

//    boolean isInputOfType(String type);
//
//    boolean isOutputOfType(String type);
//    boolean isInput();
//
//    boolean isOutput();
    Connector addInput(String type);

    Connector addOutput(String type);

//    ObservableList<String> getInputTypes();
//
//    ObservableList<String> getOutputTypes();
    /**
     * Returns child flow by id.
     *
     * @param id the id that specifies the requested flow
     * @return the requested child flow or <code>null</code> if no such flow
     * exists
     */
    public VFlow getFlowById(String id);
}
