/* 
 * VSplashScreen.java
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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * A splash screen.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class VSplashScreen extends JFrame {

	private BufferedImage img;
	private int x, y, w, h;

	private Timer timer;

    /**
     * Constructor.
     * @param pic the picture to display
     * @param splashBounds the splash bounds
     * @throws java.io.IOException
     */
	public VSplashScreen(File pic, Rectangle splashBounds) throws IOException {

		this.x = splashBounds.x;
        this.y = splashBounds.y;

        this.w = splashBounds.width;
        this.h = splashBounds.height;

		setSize(w, h);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		setLocation(x,y);

		this.setUndecorated(true);

		MediaTracker mt = new MediaTracker(this);
		try {

//			img = (BufferedImage) ImageIO.read(pic).getScaledInstance(w, h, Image.SCALE_SMOOTH);
            img = ImageIO.read(pic);
            img = ImageUtils.convertToCompatibleImage(img);

		} catch (IOException e) {
			//e.printStackTrace();
		}

		if (img == null) {
            img = (BufferedImage) this.createImage(w, h);
        }


		mt.addImage(img, 0);

		try {
			mt.waitForAll();
		} catch (InterruptedException e1) {
//			e1.printStackTrace();
		}
	}

    @Override
	public void paint(Graphics g) {
//		super.paint(g);
		if (img != null) {
            g.drawImage(img, 0, 0, this);
        }
	}
}
