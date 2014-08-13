/* 
 * ColorUtils.java
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

import java.awt.Color;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class ColorUtils {

//    public static Color complementaryColor(Color c) {
//        float[] hsb =
//                Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
//
//        float hue = hsb[0];
//        float saturation = hsb[1];
//        float brightness = hsb[2];
//
////        System.out.println("HUE:" + hue);
////
////        if (hue>=180) {
////            hue -=180;
////            System.out.println("HUE>=180:" + hue);
////        } else {
////            hue+=180;
////            System.out.println("HUE<180:" + hue);
////        }
//
//        System.out.println("B:" + brightness);
//
//        brightness = 360 - brightness;
//
//        System.out.println("BA:" + brightness);
//
//
//        Color rgb = new Color(Color.HSBtoRGB(hue, saturation, brightness));
//
//
//        Color result = new Color(
//                rgb.getRed(),
//                rgb.getGreen(),
//                rgb.getBlue(),
//                c.getAlpha());
//
//        return result;
//    }
//    

    public static Color invertColor(Color c) {


        return new Color(
                255 - c.getRed(),
                255 - c.getGreen(),
                255 - c.getBlue(),
                c.getAlpha());

//        return result;
    }
}
