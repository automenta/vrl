/*
 * ConnectionListener.java
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

import vrl.workflow.ConnectionResult;
import vrl.workflow.VFlow;
import javafx.scene.Node;

/**
 *
 * @author Michael Hoffer  &lt;info@michaelhoffer.de&gt;
 */
public interface ConnectionListener {

    void onConnectionCompatible(Node n);

    void onConnectionCompatibleReleased(Node n);

    void onConnectionIncompatible();

    void onConnectionIncompatibleReleased(Node n);

    void onCreateNewConnectionReleased(ConnectionResult connResult);

    void onCreateNewConnectionReverseReleased(ConnectionResult connResult);

    void onNoConnection(Node n);

    void onRemoveConnectionReleased();
    
}

class ConnectionListenerImpl implements ConnectionListener {
    
    private Node receiverConnectorUI;
    private FXSkinFactory skinFactory;
    private VFlow flowController;

    public ConnectionListenerImpl(FXSkinFactory skinFactory, VFlow vflow, Node receiverConnectorUI) {
        this.skinFactory = skinFactory;
        this.flowController = vflow;
        this.receiverConnectorUI = receiverConnectorUI;
    }
    

    @Override
    public void onConnectionCompatible(Node n) {       
        FXConnectorUtil.connectAnim(receiverConnectorUI, n);
    }
    
    @Override
    public void onConnectionCompatibleReleased(Node n) {      
        FXConnectorUtil.connectAnim(receiverConnectorUI, n);
    }
    
    @Override
    public void onConnectionIncompatible() {        
        FXConnectorUtil.incompatibleAnim(receiverConnectorUI);
    }
    
    @Override
    public void onNoConnection(Node n) {

        FXConnectorUtil.unconnectAnim(receiverConnectorUI);
    }
    
    @Override
    public void onConnectionIncompatibleReleased(Node n) {

        FXConnectorUtil.connnectionIncompatibleAnim(n);
    }
    
    @Override
    public void onCreateNewConnectionReleased(ConnectionResult connResult) {
        newConnectionAnim(connResult);
    }
    
    @Override
    public void onCreateNewConnectionReverseReleased(ConnectionResult connResult) {
        newConnectionReverseAnim(connResult);
    }
    
    @Override
    public void onRemoveConnectionReleased() {
        
        FXConnectorUtil.unconnectAnim(receiverConnectorUI);
    }
    
    private void newConnectionAnim(ConnectionResult connResult) {
        if (connResult.getConnection() != null) {
            FXConnectionSkin connectionSkin =
                    (FXConnectionSkin) flowController.getNodeSkinLookup().getById(
                    skinFactory, connResult.getConnection());
            FXConnectorUtil.connnectionEstablishedAnim(connectionSkin.getReceiverUI());
        }
    }

    private void newConnectionReverseAnim(ConnectionResult connResult) {
        // System.out.println("new-connection anim (reverse)");
        if (connResult.getConnection() != null) {
            FXConnectionSkin connectionSkin =
                    (FXConnectionSkin) flowController.getNodeSkinLookup().getById(
                    skinFactory, connResult.getConnection());
  
            FXConnectorUtil.connnectionEstablishedAnim(connectionSkin.getSenderNode());
        }
    }
    
}
