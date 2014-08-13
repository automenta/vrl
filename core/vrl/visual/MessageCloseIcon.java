/* 
 * MessageCloseIcon.java
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

package vrl.visual;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import javax.swing.JPanel;

/**
 * This component is used by the message box to display a close icon.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class MessageCloseIcon extends VComponent implements Serializable,
        MouseListener, MouseMotionListener {

    private static final long serialVersionUID = -8087553085386807425L;
    private CanvasActionListener actionListener;
    private Color currentColor;
    private boolean isActive = false;

    /**
     * Constructor.
     * @param mainCanvas the main canvas object
     */
    public MessageCloseIcon(Canvas mainCanvas) {
        setMainCanvas(mainCanvas);
        setPreferredSize(new Dimension(28, 28));
//        setMinimumSize(new Dimension(100,100));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Color color = getStyle().getBaseValues().getColor(
                MessageBox.TEXT_COLOR_KEY);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Composite original = g2.getComposite();

        float alpha = getStyle().getBaseValues().getFloat(
                CanvasWindow.TRANSPARENCY_KEY);
        AlphaComposite ac1 = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);

        g2.setComposite(ac1);

        g2.setColor(getDefinedColor());

        float circleThickness = getWidth() / 8;

        BasicStroke stroke = new BasicStroke(circleThickness,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        g2.setStroke(stroke);

        int topLeftSpacing = (int) circleThickness;
        int bottomRightSpacing = (int) circleThickness + topLeftSpacing;

        g2.drawOval(topLeftSpacing, topLeftSpacing,
                getWidth() - bottomRightSpacing,
                getHeight() - bottomRightSpacing);

        float crossThickness = getWidth() / 8;

        stroke = new BasicStroke(crossThickness,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        g2.setStroke(stroke);

        int spacing = (int) (getWidth() / 2.8);

        g2.drawLine(spacing, spacing, getWidth() - spacing, getHeight() - spacing);
        g2.drawLine(getWidth() - spacing, spacing, spacing, getHeight() - spacing);

        g2.setComposite(original);
    }

    @Override
    public void mouseDragged(MouseEvent ev) {
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        fireAction(new ActionEvent(this, 0, "clicked"));
    }

    @Override
    public void mousePressed(MouseEvent ev) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        isActive = true;
        stateChanged();
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        isActive = false;
        repaint();
    }

    /**
     * Returns the action listener of this icon.
     * @return the action listener of this icon
     */
    public CanvasActionListener getActionListener() {
        return actionListener;
    }

    /**
     * Defines the action listener of this icon.
     * @param actionListener the action listener of this icon
     */
    public void setActionListener(CanvasActionListener actionListener) {
        this.actionListener = actionListener;
    }

    /**
     * Fires an action.
     * @param event the event
     */
    private void fireAction(ActionEvent event) {
        if (actionListener != null) {
            actionListener.actionPerformed(event);
        }
    }

    /**
     * Returns the defined color.
     * @return the defined color
     */
    public Color getDefinedColor() {
        if (isActive) {
            currentColor =
                    getStyle().getBaseValues().getColor(
                    MessageBox.ACTIVE_ICON_COLOR_KEY);
        } else {
            currentColor = getStyle().getBaseValues().getColor(
                    MessageBox.ICON_COLOR_KEY);
        }
        return currentColor;
    }

    /**
     * Is called whenever the message icon state has changed.
     */
    public void stateChanged() {
        //
    }
}
