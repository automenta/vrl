/* 
 * ControlFlowValueObject.java
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

package vrl.reflection;

import vrl.visual.CanvasWindow;
import vrl.visual.ConnectionResult;
import vrl.visual.ConnectionStatus;
import vrl.visual.Connections;
import vrl.visual.Connector;
import vrl.visual.Message;
import vrl.visual.MessageType;
import vrl.visual.ValueObject;
import java.awt.Component;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class ControlFlowValueObject implements ValueObject {

    private DefaultObjectRepresentation oRep;
    private ControlFlowConnector connector;
    private Connections connections;

    public ControlFlowValueObject(
            Connections connections,
            DefaultObjectRepresentation oRep,
            ControlFlowConnector c) {
        this.oRep = oRep;
        this.connector = c;
        this.connections = connections;
    }

    @Override
    public Class<?> getType() {
        return getClass();
    }

    @Override
    public CanvasWindow getParentWindow() {
        return oRep.getParentWindow();
    }

    @Override
    public void setValue(Object o) {
        //
    }

    @Override
    public Object getValue() {
        //
        return null;
    }

    @Override
    public void setOutdated() {
        //
    }

    @Override
    public ConnectionResult compatible(ValueObject obj) {
        boolean sameType = obj.getType().equals(getType());

        if (!sameType) {
            return new ConnectionResult(
                    null, ConnectionStatus.ERROR_VALUE_TYPE_MISSMATCH);
        }

        boolean isInput = getConnector().isInput();

        ConnectionResult result = new ConnectionResult(
                null, ConnectionStatus.VALID);

        if (!isInput) {
            // the connector we drag from is an output

            // if this connector is already connected we do not allow another
            // connection
            boolean alreadyConnected =
                    connections.alreadyConnected(connector);

            if (alreadyConnected) {
                result = new ConnectionResult(
                        new Message("Cannot establish connection:",
                        ">> this control-flow output connector is already"
                        + " connected! Only one connection is allowed.",
                        MessageType.ERROR),
                        ConnectionStatus.ERROR_INVALID);
            }
        } else {
            // the connector we drag from is an input


            // if the output is already connected we do not allow another
            // connection
            boolean outputAlreadyConnected =
                    connections.alreadyConnected(obj.getConnector());

            if (outputAlreadyConnected) {
                result = new ConnectionResult(
                        new Message("Cannot establish connection:",
                        ">> the control-flow output connector is already"
                        + " connected! Only one connection is allowed",
                        MessageType.ERROR),
                        ConnectionStatus.ERROR_INVALID);
            }
        }

        return result;
    }

    public DefaultObjectRepresentation getObject() {
        return oRep;
    }

    @Override
    public Component getParent() {
        return oRep;
    }

    @Override
    public Connector getConnector() {
        return connector;
    }
}
