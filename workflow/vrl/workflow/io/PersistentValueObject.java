/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vrl.workflow.io;

import vrl.workflow.CompatibilityResult;
import vrl.workflow.VNode;
import vrl.workflow.VisualizationRequest;
import javafx.beans.property.ObjectProperty;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class PersistentValueObject {
    
    private String parentId;
    private Object value;
    private VisualizationRequest vReq;

    public PersistentValueObject(String parentId, Object value, VisualizationRequest vReq) {
        this.parentId = parentId;
        this.value = value;
        this.vReq = vReq;
    }

    public PersistentValueObject() {
    }
    
    
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String id) {
        this.parentId = id;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public VisualizationRequest getVisualizationRequest() {
        return this.vReq;
    }
    
    public void setVisualizationRequest(VisualizationRequest vReq) {
        this.vReq = vReq;
    }
}