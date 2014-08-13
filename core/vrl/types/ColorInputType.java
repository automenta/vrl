/* 
 * ColorInputType.java
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

package vrl.types;

import vrl.animation.Animation;
import vrl.animation.FrameListener;
import vrl.animation.LinearInterpolation;
import vrl.annotation.TypeInfo;
import vrl.visual.CanvasWindow;
import vrl.visual.VSwingUtil;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * TypeRepresentation for
 * <code>java.awt.Color</code>.
 *
 * <p>Sample:</p> <br/> <img src="doc-files/color-chooser-01.png"/> <br/>
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@TypeInfo(type = Color.class, input = true, output = false, style = "color-chooser")
public class ColorInputType extends ColorType {

    private JColorChooser colorChooser;
    private CanvasWindow window;

    public ColorInputType() {
        colorChooser = new JColorChooser();
        colorChooser.setPreviewPanel(new JPanel());
        AbstractColorChooserPanel panel = colorChooser.getChooserPanels()[1];
        panel.setBackground(new Color(0.f, 0.f, 0.f, 0.f));

        colorChooser.setChooserPanels(new AbstractColorChooserPanel[]{panel});
        colorChooser.setBackground(new Color(0.f, 0.f, 0.f, 0.f));

//        this.add(colorChooser);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1) {
                    if (e.getClickCount() == 2) {
                        showWindow();
                    }
                    setWindowLocation(VSwingUtil.getAbsPos(getConnector()));
                }
            }
        });
        
        setToolTipText("double-click to change the color");

        ColorSelectionModel model = colorChooser.getSelectionModel();

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                ColorInputType.super.setViewValue(colorChooser.getColor());
                setReturnTypeOutdated();
            }
        };

        model.addChangeListener(changeListener);
    }

    private void showWindow() {

        Point connectorLocation = VSwingUtil.getAbsPos(getConnector());

        if (window == null || window.isDisposed()) {
            window = new CanvasWindow(
                    "Choose Color", getMainCanvas());
            window.add(colorChooser);
            getMainCanvas().addWindow(window);
            window.setWindowLocation(connectorLocation.x, connectorLocation.y);
        }
    }

    @Override
    public void setViewValue(Object o) {

        super.setViewValue(o);

        Color c = (Color) o;
        if (c != null) {
            colorChooser.setColor(c);
        }
    }

    @Override
    public Object getViewValue() {
        return colorChooser.getColor();
    }

    private void setWindowLocation(Point connectorLocation) {

        if (window == null || window.isDisposed()) {
            return;
        }


        Animation a = new Animation();

        final LinearInterpolation iX =
                new LinearInterpolation(window.getX(), connectorLocation.x);
        final LinearInterpolation iY =
                new LinearInterpolation(window.getY(), connectorLocation.y);

        a.addFrameListener(new FrameListener() {
            @Override
            public void frameStarted(double time) {

                iX.step(time);
                iY.step(time);

                window.setWindowLocation((int) iX.getValue(), (int) iY.getValue());
            }
        });

        a.setDuration(0.15);

        getMainCanvas().getAnimationManager().addAnimation(a);
    }
}
