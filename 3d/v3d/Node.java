/* 
 * Node.java
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

package v3d;

import java.io.Serializable;
import java.util.ArrayList;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

/**
 * A node.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class Node implements Serializable {

    private static final long serialVersionUID = 1L;
    private int index;
    private Point3f location;
    private Color3f color;
    private ArrayList<LocationChangedListener> listeners =
            new ArrayList<LocationChangedListener>();

    /**
     * Constructor.
     */
    public Node() {
    }

    /**
     * Constructor.
     * @param location the location
     */
    public Node(Point3f location) {
        this.index = 0;
        this.location = location;
    }

    /**
     * Constructor.
     * @param location the location
     */
    public Node(Point3f location, Color3f c) {
        this.index = 0;
        this.location = location;
        this.color = c;
    }

    /**
     * Constructor.
     * @param index the node index
     * @param location the location
     */
    public Node(int index, Point3f location) {
        this.index = index;
        this.location = location;
    }

    /**
     * Constructor.
     * @param index the node index
     * @param location the location
     */
    public Node(int index, Point3f location, Color3f c) {
        this.index = index;
        this.location = location;
        this.color = c;
    }


    /**
     * Constructor.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Node(float x, float y, float z) {
        this.index = 0;
        this.location = new Point3f(x,y,z);
    }

    /**
     * Constructor.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Node(float x, float y, float z, Color3f c) {
        this.index = 0;
        this.location = new Point3f(x,y,z);
        this.color = c;
    }

    /**
     * Constructor.
     * @param index the node index
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Node(int index, float x, float y, float z) {
        this.index = index;
        this.location = new Point3f(x,y,z);
    }

    /**
     * Constructor.
     * @param index the node index
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Node(int index, float x, float y, float z, Color3f c) {
        this.index = index;
        this.location = new Point3f(x,y,z);
        this.color = c;
    }

    /**
     * Adds a location changed listener to this node.
     * @param l the listener to add
     */
    public void addLocationChangedListener(LocationChangedListener l) {
        listeners.add(l);
    }

    /**
     * Removes a location changed listener from this node.
     * @param l the listener to remove
     * @return <code>true</code> if the node clould be removed;
     *         <code>false</code> otherwise
     */
    public boolean removeLocationChangedListener(LocationChangedListener l) {
        return listeners.remove(l);
    }

    /**
     * Notifies the listeners.
     */
    private void notifyListeners() {
        for (LocationChangedListener l : listeners) {
            l.locationChanged(this);
        }
    }

    /**
     * Returns the node index.
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Defines the node index.
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Returns the location of the node.
     * @return the location of the node
     */
    public Point3f getLocation() {
        return location;
    }

    /**
     * Defines the location of the node.
     * @param location the location to set
     */
    public void setLocation(Point3f location) {
        this.location = location;
        notifyListeners();
    }

    @Override
    public String toString() {
        return "Node[" + getIndex() + "]: " + getLocation();
    }

    /**
     * @return the color
     */
    public Color3f getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color3f color) {
        this.color = color;
    }
}
