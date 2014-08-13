/*
 * FXSkinFactory.java
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
import vrl.workflow.skin.ConnectionSkin;
import vrl.workflow.VFlow;
import vrl.workflow.VNode;
import vrl.workflow.skin.VNodeSkin;
import vrl.workflow.skin.Skin;
import vrl.workflow.skin.SkinFactory;
import javafx.scene.Parent;

/**
 *
 * @author Michael Hoffer  &lt;info@michaelhoffer.de&gt;
 */
public class FXSkinFactory implements SkinFactory<FXConnectionSkin, FXFlowNodeSkin> {

    private final Parent fxParent;
    private final FXSkinFactory parentFactory;
//    private Window clipboard;
    

    public FXSkinFactory(Parent parent) {
        this.fxParent = parent;
//        this.clipboard = clipboard;
        this.parentFactory = null;
    }

    protected FXSkinFactory(Parent parent, FXSkinFactory parentFactory) {
        this.fxParent = parent;
//        this.clipboard = clipboard;
        this.parentFactory = parentFactory;
    }

    @Override
    public VNodeSkin createSkin(VNode n, VFlow flow) {
        return new FXFlowNodeSkin(this, getFxParent(), n, flow);
    }

    @Override
    public ConnectionSkin createSkin(Connection c, VFlow flow, String type) {
        return new FXConnectionSkin(this, getFxParent(), c, flow, type/*, clipboard*/);
    }

    @Override
    public SkinFactory<FXConnectionSkin, FXFlowNodeSkin> createChild(Skin parent) {
        
//        System.out.println("parent: " + parent);

        FXSkinFactory result = new FXSkinFactory(((FXSkin) parent).getContentNode(), this);

        return result;
    }
    
    public FXSkinFactory newInstance(Parent parent, FXSkinFactory parentFactory) {

        FXSkinFactory result = new FXSkinFactory(parent, parentFactory);

        return result;
    }

    @Override
    public SkinFactory<FXConnectionSkin, FXFlowNodeSkin> getParent() {
        return this.parentFactory;
    }

    /**
     * @return the fxParent
     */
    public Parent getFxParent() {
        return fxParent;
    }
    
}
