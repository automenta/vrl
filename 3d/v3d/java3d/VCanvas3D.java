package eu.mihosoft.vrl.types.java3d;

///* 
// * VCanvas3D.java
// * 
// * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
// *
// * Copyright (c) 2009–2012 Steinbeis Forschungszentrum (STZ Ölbronn),
// * Copyright (c) 2006–2012 by Michael Hoffer
// * 
// * This file is part of Visual Reflection Library (VRL).
// *
// * VRL is free software: you can redistribute it and/or modify
// * it under the terms of the GNU Lesser General Public License version 3
// * as published by the Free Software Foundation.
// * 
// * see: http://opensource.org/licenses/LGPL-3.0
// *      file://path/to/VRL/src/eu/mihosoft/vrl/resources/license/lgplv3.txt
// *
// * VRL is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU Lesser General Public License for more details.
// *
// * This version of VRL includes copyright notice and attribution requirements.
// * According to the LGPL this information must be displayed even if you modify
// * the source code of VRL. Neither the VRL Canvas attribution icon nor any
// * copyright statement/attribution may be removed.
// *
// * Attribution Requirements:
// *
// * If you create derived work you must do three things regarding copyright
// * notice and author attribution.
// *
// * First, the following text must be displayed on the Canvas:
// * "based on VRL source code". In this case the VRL canvas icon must be removed.
// * 
// * Second, the copyright notice must remain. It must be reproduced in any
// * program that uses VRL.
// *
// * Third, add an additional notice, stating that you modified VRL. In addition
// * you must cite the publications listed below. A suitable notice might read
// * "VRL source code modified by YourName 2012".
// * 
// * Note, that these requirements are in full accordance with the LGPL v3
// * (see 7. Additional Terms, b).
// *
// * Publications:
// *
// * M. Hoffer, C.Poliwoda, G.Wittum. Visual Reflection Library -
// * A Framework for Declarative GUI Programming on the Java Platform.
// * Computing and Visualization in Science, 2011, in press.
// */
//
//package eu.mihosoft.vrl.types;
//
//import eu.mihosoft.vrl.visual.Ruler;
//import com.sun.j3d.exp.swing.JCanvas3D;
//import eu.mihosoft.vrl.animation.Animation;
//import eu.mihosoft.vrl.animation.AnimationManager;
//import eu.mihosoft.vrl.animation.AnimationTask;
//import eu.mihosoft.vrl.animation.FrameListener;
//import eu.mihosoft.vrl.animation.RepaintThread;
//import eu.mihosoft.vrl.animation.TaskAnimation;
//import eu.mihosoft.vrl.visual.ImageUtils;
//import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
//import eu.mihosoft.vrl.visual.BufferedPainter;
//import eu.mihosoft.vrl.visual.Canvas;
//import eu.mihosoft.vrl.visual.CanvasChild;
//import eu.mihosoft.vrl.visual.CanvasLabel;
//import eu.mihosoft.vrl.visual.CanvasWindow;
//import eu.mihosoft.vrl.visual.Connection;
//import eu.mihosoft.vrl.visual.Style;
//import eu.mihosoft.vrl.visual.TransparentPanel;
//import eu.mihosoft.vrl.visual.VBoxLayout;
//import eu.mihosoft.vrl.visual.VSwingUtil;
//import java.awt.AlphaComposite;
//import java.awt.BasicStroke;
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Composite;
//import java.awt.Dimension;
//import java.awt.GradientPaint;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Point;
//import java.awt.Rectangle;
//import java.awt.RenderingHints;
//import java.awt.Shape;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ComponentEvent;
//import java.awt.event.ComponentListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.awt.event.MouseWheelEvent;
//import java.awt.event.MouseWheelListener;
//import java.awt.geom.Area;
//import java.awt.geom.Rectangle2D;
//import java.awt.geom.RoundRectangle2D;
//import java.awt.image.BufferedImage;
//import java.awt.image.ConvolveOp;
//import java.awt.image.Kernel;
//import java.util.ArrayList;
//import javax.media.j3d.ImageComponent;
//import javax.media.j3d.ImageComponent2D;
//import javax.swing.Box;
//import javax.swing.JMenu;
//import javax.swing.JMenuItem;
//import javax.swing.JPanel;
//import javax.swing.JPopupMenu;
//import javax.swing.RepaintManager;
//import javax.swing.SwingUtilities;
//
///**
// * Extended version of <code>JCanvas3D</code>. In addition it provides
// * functionality for post render tasks, i.e., render optimization. Also its
// * appearance is different. It is designed for use within type representations.
// * @author Michael Hoffer <info@michaelhoffer.de>
// */
//public class VCanvas3D extends JCanvas3D implements MouseListener,
//        MouseMotionListener,
//        BufferedPainter,
//        MouseWheelListener, ComponentListener, CanvasChild {
//
//    private static final long serialVersionUID = -1672983251248240258L;
//    /**
//     * the type representation this component belongs to
//     */
//    private TypeRepresentationBase typeRepresentation;
//    /**
//     * the shape of this component
//     */
//    private Shape shape;
//    /**
//     * the buffer for the render optimization
//     */
//    private BufferedImage renderBuffer;
////    private BufferedImage buffer;
//    private Style style;
//    /**
//     * defines if currently in post render mode (used by post render animation)
//     */
//    private boolean postRendering;
//    /**
//     * defines whether the image is to be optimized after rendering
//     */
//    private boolean renderOptimizationEnabled;
//    /**
//     * defines whether render optimization is used as realtime effect
//     * (if enabled)
//     */
//    private boolean realTimeRenderOptimization;
//    /**
//     * defines how much the blurring affects the image (defines the smoothness)
//     */
//    private float blurValue = 0.5f;
//    /**
//     *
//     */
//    private boolean initialized = false;
//    private TransparentPanel toolPane;
//    private JPopupMenu menu;
//
//    public static final String UPPER_COLOR_KEY = "VCanvas3D:upperColor";
//    public static final String LOWER_COLOR_KEY = "VCanvas3D:lowerColor";
//    public static final String CONTENT_TRANSPARENCY_KEY =
//            "VCanvas3D:Content:transparency";
//
//    /**
//     * Constructor.
//     * @param typeRepresentation the type representation that uses this canvas
//     */
//    public VCanvas3D(TypeRepresentationBase typeRepresentation) {
//        setTypeRepresentation(typeRepresentation);
//
////        VBoxLayout layout = new VBoxLayout(this, VBoxLayout.X_AXIS);
////        setLayout(layout);
//
//        setLayout(new BorderLayout());
//
////        this.add(Box.createGlue());
//        Box box = new Box(VBoxLayout.Y_AXIS);
//
//        box.add(Box.createVerticalGlue());
//        box.add(new Ruler(this));
//
//        this.add(box, BorderLayout.EAST);
//
//        addMouseListener(this);
//        addMouseMotionListener(this);
//        addMouseWheelListener(this);
//
//        setRenderOptimizationEnabled(false);
//
//        addComponentListener(this);
//
//        setOpaque(true);
//        setMinimumSize(new Dimension(160, 120));
//        setPreferredSize(new Dimension(160, 120));
//        setSize(new Dimension(160, 120));
//
//        toolPane = new TransparentPanel();
//        VBoxLayout toolLayout = new VBoxLayout(toolPane, VBoxLayout.Y_AXIS);
//        toolPane.setLayout(toolLayout);
//        add(toolPane, BorderLayout.WEST);
//
//        menu = new JPopupMenu("");
//    }
//
//    @Override
//    public void setBounds(int x, int y, int w, int h) {
//        int xChecked = Math.max(0, x);
//        int yChecked = Math.max(0, y);
//        int wChecked = Math.max(1, w);
//        int hChecked = Math.max(1, h);
//        super.setBounds(xChecked, yChecked, wChecked, hChecked);
//    }
//
//    /**
//     * Applies blur filter after rendering.
//     * @param g2 the graphics context that is to be used for post render
//     * operations
//     */
//    protected void postRender(Graphics2D g2) {
//        if (isPostRender()) {
//
//            boolean newImageNeeded = renderBuffer == null
//                    || renderBuffer.getWidth() != getWidth()
//                    || renderBuffer.getHeight() != getHeight();
//
//            if (newImageNeeded || isRealTimeRenderOptimization()) {
////                System.out.println("BLUR" + getWidth() + " " + renderBuffer);
//
//                if (newImageNeeded) {
//                    renderBuffer =
//                            ImageUtils.createCompatibleImage(
//                            getWidth(), getHeight());
//                } else {
//                    ImageUtils.clearImage(renderBuffer);
//                }
//
//                Graphics2D g2Buffer = renderBuffer.createGraphics();
//
//                super.paintComponent(g2Buffer);
//
//                g2Buffer.dispose();
//
//                float middle = blurValue;
//                float value = (1.f - middle) / 8f;
//
//                float[] BLUR = new float[]{
//                    value, value, value,
//                    value, middle, value,
//                    value, value, value
//                };
//
//                ConvolveOp vBlurOp = new ConvolveOp(new Kernel(3, 3, BLUR));
//
//                renderBuffer = vBlurOp.filter(renderBuffer, null);
//            } // end if image changed
//            g2.drawImage(renderBuffer, 0, 0, null);
//
//        } else {
//            super.paintComponent(g2);
//        }
//    }
//
////    @Override
////    public void paint(Graphics g){
////        paintComponent(g);
////        for (Component c : getComponents()){
////            c.paint(g);
////        }
////    }
//    @Override
//    public void paintComponent(Graphics g) {
//
////        boolean imageHasChanged = buffer == null ||
////                buffer.getWidth() != getWidth() ||
////                buffer.getHeight() != getHeight();
////
////        CanvasStyle newStyle = typeRepresentation.getMainCanvas().getStyle();
////
////        boolean styleHasChanged = style != newStyle;
////
////        if (imageHasChanged || styleHasChanged) {
////            style = newStyle;
////
////            buffer = ImageEffects.createCompatibleImage(getWidth(), getHeight());
////
////            Graphics2D g2 = buffer.createGraphics();
//
//        // this is necessary to restore size when loaded from file
//        if (!initialized) {
//            revalidate();
//            typeRepresentation.updateLayout();
//            initialized = true;
//        }
//
//
//        style = typeRepresentation.getStyle();
//
//        Graphics2D g2 = (Graphics2D) g;
//
//        // paint background shape (round rectangle with gradient background)
//
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
//
//        setShape(new RoundRectangle2D.Double(0, 0,
//                getWidth(), getHeight(), 20, 20));
//
//        Color upperColor = style.getBaseValues().getColor(UPPER_COLOR_KEY);
//        Color lowerColor = style.getBaseValues().getColor(LOWER_COLOR_KEY);
//
//        GradientPaint paint = new GradientPaint(0, 0,
//                upperColor,
//                0, this.getHeight(),
//                lowerColor,
//                false);
//
//        g2.setPaint(paint);
//
//        g2.fill(getShape());
//
//        // define alpha composite for 3d content
//        Composite originalComposite = g2.getComposite();
//
//        AlphaComposite ac1 =
//                AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
//               style.getBaseValues().getFloat(CONTENT_TRANSPARENCY_KEY));
//        g2.setComposite(ac1);
//
//        Shape originalClip = g.getClip();
//
//        int width = getWidth() - 1;
//        int height = getHeight() - 1;
//
//        // define new clip, otherwise round corner drawing bugs will be visible
//        // compute reduced clip by intersection realclip and round rectangle
//        // clip for scrollbars (otherwise 3d was drawn above, occured for other
//        // components as well)
//
//        RoundRectangle2D roundClip = new RoundRectangle2D.Double(0, 0,
//                width,
//                height, 20, 20);
//
//        Area roundClipArea = new Area(roundClip);
//        Area originalClipArea = new Area(originalClip);
//        originalClipArea.intersect(roundClipArea);
//        
//        g2.setClip(originalClipArea);
//
//        postRender(g2);
//
//        g2.setComposite(originalComposite);
//        g2.setClip(originalClip);
//        g2.setColor(style.getBaseValues().getColor(
//                CanvasWindow.BORDER_COLOR_KEY));
//        g2.setStroke(new BasicStroke(1));
//
//        g2.draw(new RoundRectangle2D.Double(0, 0,
//                getWidth() - 1, getHeight() - 1, 20, 20));
//
////    } // end if (imageHasChanged || styleHasChanged)
//
////        g.drawImage(buffer, 0, 0, null);
//    }
//
//    /**
//     * Returns the shape of the canvas.
//     * @return the shape
//     */
//    public Shape getShape() {
//        return shape;
//    }
//
//    /**
//     * Defines the shape of the canvas.
//     * @param shape the shape to set
//     */
//    public void setShape(Shape shape) {
//        this.shape = shape;
//    }
//
//    /**
//     * Returns the type representation that uses this canvas.
//     * @return the typeRepresentation that uses this canvas
//     */
//    public TypeRepresentationBase getTypeRepresentation() {
//        return typeRepresentation;
//    }
//
//    /**
//     * Defines the type representation that uses this canvas.
//     * @param typeRepresentation the type representation to set
//     */
//    protected void setTypeRepresentation(
//            TypeRepresentationBase typeRepresentation) {
//        this.typeRepresentation = typeRepresentation;
//    }
//
////    @Override
//    @Override
//    public void contentChanged() {
//        renderBuffer = null;
//        SwingUtilities.updateComponentTreeUI(getMenu());
////        System.out.println("CONTENT-CHANGED" + System.nanoTime());
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
////        System.out.println("CLICKED" + System.nanoTime());
//        postRenderTask();
//        if (e.getButton() == MouseEvent.BUTTON3) {
//            getMenu().show(this, e.getX(), e.getY());
//        }
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
////        System.out.println("PRESSED" + System.nanoTime());
//        postRenderTask();
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
////        System.out.println("RELEASED" + System.nanoTime());
//        postRenderTask();
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
////        System.out.println("ENTERED" + System.nanoTime());
////        postRenderTask();
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
////        System.out.println("EXITED" + System.nanoTime());
////        postRenderTask();
//    }
//
//    @Override
//    public void mouseDragged(MouseEvent e) {
////        System.out.println("DRAGGED" + System.nanoTime());
//        postRenderTask();
//        getTypeRepresentation().setDataOutdated();
//    }
//
//    @Override
//    public void mouseMoved(MouseEvent e) {
////        postRenderTask();
////        System.out.println("MOVED" + System.nanoTime());
//    }
//
//    @Override
//    public void mouseWheelMoved(MouseWheelEvent e) {
////        System.out.println("WHEEL" + System.nanoTime());
//        postRenderTask();
//        getTypeRepresentation().setDataOutdated();
//    }
//
//    /**
//     * Adds a post render animation to the animation manager.
//     */
//    void postRenderTask() {
//
////        if (getTypeRepresentation() != null
////                && getTypeRepresentation().getMainCanvas() != null) {
////            getTypeRepresentation().getMainCanvas().repaint();
////        }
//
//        if (isRenderOptimizationEnabled()) {
//
//            if (isRealTimeRenderOptimization()) {
//                setPostRender(true);
//            } else {
//                AnimationManager animationManager =
//                        getTypeRepresentation().
//                        getMainCanvas().getAnimationManager();
//
//                Animation a = new PostRenderAnimation(this);
//
//                a.setDuration(0.5);
//
//                animationManager.addUniqueAnimation(a, 1);
//            }
//        }
//    }
//
//    /**
//     * Indicates whether post render tasks are currently active.
//     * @return <code>true</code> if post render tasks are currently active;
//     *         <code>false</code> otherwise
//     */
//    boolean isPostRender() {
//        return postRendering;
//    }
//
//    /**Defines if post render tasks are currently active.
//     * @param postRender the state to set
//     */
//    void setPostRender(boolean postRender) {
//        this.postRendering = postRender;
//    }
//
//    /**
//     * Indicates whether renderOptimization is enabled.
//     * @return <code>true</code> if render optimization is enabled;
//     *         <code>false</code> otherwise
//     */
//    public boolean isRenderOptimizationEnabled() {
//        return renderOptimizationEnabled;
//    }
//
//    /**
//     * Defines if render optimization is to be enabled.
//     * @param renderOptimizationEnabled the state to set
//     */
//    public void setRenderOptimizationEnabled(boolean renderOptimizationEnabled) {
//        this.renderOptimizationEnabled = renderOptimizationEnabled;
//    }
//
//    /**
//     * Indicates whether realtime optimization is enabled.
//     * @return <code>true</code> if realtime optimization is enabled;
//     *         <code>false</code> otherwise
//     */
//    public boolean isRealTimeRenderOptimization() {
//        return realTimeRenderOptimization;
//    }
//
//    /**
//     * Defines whether to use realtime optimization.
//     * @param realTimeRenderOptimization the state to set
//     */
//    public void setRealTimeRenderOptimization(boolean realTimeRenderOptimization) {
//        this.realTimeRenderOptimization = realTimeRenderOptimization;
//    }
//
//    /**
//     * Returns the blur value of the image.
//     * @return the blur value of the image
//     */
//    public float getBlurValue() {
//        return blurValue;
//    }
//
//    /**
//     * Defines the blur value of the image. Values less than <code>1.0</code>
//     * result in smoother images. Values greater than <code>1.0</code> and less
//     * than <code>0</code> result in sharper images.
//     * @param blurValue the blur value of the image
//     */
//    public void setBlurValue(float blurValue) {
//        this.blurValue = blurValue;
//    }
//
//    @Override
//    public void componentResized(ComponentEvent arg0) {
//        postRenderTask();
//        typeRepresentation.setValueOptions("width=" + getWidth() + ";"
//                + "height=" + getHeight());
//    }
//
//    @Override
//    public void componentMoved(ComponentEvent arg0) {
//        postRenderTask();
//    }
//
//    @Override
//    public void componentShown(ComponentEvent arg0) {
//        postRenderTask();
//    }
//
//    @Override
//    public void componentHidden(ComponentEvent arg0) {
//        postRenderTask();
//    }
//
//    /**
//     * Returns a snapshot of this canvas.
//     * @return the renderBuffer
//     */
//    public BufferedImage getSnapShot() {
//        BufferedImage result =
//                ImageUtils.createCompatibleImage(getWidth(), getHeight());
//        Graphics g = result.createGraphics();
//        super.paintComponent(g);
//        return result;
//    }
//
//    /**
//     * @return the tool pane
//     */
//    public TransparentPanel getToolPane() {
//        return toolPane;
//    }
//
//    @Override
//    public Canvas getMainCanvas() {
//        return getTypeRepresentation().getMainCanvas();
//    }
//
//    /**
//     * <b>Warning: </b> this method does nothing. The maincanvas of the
//     * specified type representation is used instead.
//     * @param mainCanvas canvas to set
//     */
//    @Override
//    public void setMainCanvas(Canvas mainCanvas) {
//        //
//    }
//
//    /**
//     * @return the menu
//     */
//    public JPopupMenu getMenu() {
//        return menu;
//    }
//}
//
///**
// * Enables and disables render optimization.
// * @author Michael Hoffer <info@michaelhoffer.de>
// */
//class PostRenderAnimation extends Animation {
//
//    /**
//     * the main canvas
//     */
//    private VCanvas3D canvas;
//
//    /**
//     * Constructor.
//     * @param canvas the canvas that is to be controlled by this animation.
//     */
//    public PostRenderAnimation(final VCanvas3D canvas) {
//        this.canvas = canvas;
//        addFrameListener(new AnimationTask() {
//
//            @Override
//            public void firstFrameStarted() {
//                canvas.setPostRender(false);
//            }
//
//            @Override
//            public void frameStarted(double time) {
////                canvas.contentChanged();
//            }
//
//            @Override
//            public void lastFrameStarted() {
//                canvas.setPostRender(true);
//                canvas.contentChanged();
//                canvas.repaint();
//
//                // prevents memory leaks
//                if (getRepeats() >= getNumberOfRepeats()) {
//                    PostRenderAnimation.this.canvas = null;
//                }
//            }
//        });
//    }
//}
