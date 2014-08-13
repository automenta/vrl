/* 
 * VBoxLayout.java
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

import java.awt.Container;
import java.io.PrintStream;

/**
 * <p>
 * The purpose of this class is to prevent BoxLayout to cause
 * NullPointerExceptions. This bug is known since 2001! No clean workaround
 * exists. Mostly this error occurs if too many method invokations in another
 * thread happen without calling <code>SwingUtilities.invokeLater()</code>.
 * </p>
 * <p>
 * Swing does not support multithreading, some methods are synchronized however.
 * </p>
 * <p>
 * If this layout error occurs while using <code>CodeBlock</code> objects it
 * may be sufficient to put the code blocks invoke method inside of a
 * <code>SwingUtilities.invokeLater()</code> call. But doing this for every call
 * will slow down the gui. So it may be better to do it every 100 calls or
 * so.
 * </p>
 * <p>
 * <b>Example:</b>
 * <blockquote><pre>
 *  for (int i = 0; i < repeats; i++) {
 *    if (i%100==0) {
 *       SwingUtilities.invokeLater{codeBlock.invoke()}
 *    } else {
 *       codeBlock.invoke();
 *    }
 *  }
 * </blockquote></pre>
 * </p>
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @see {@link http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4294758}
 */
public class VBoxLayout extends javax.swing.BoxLayout {

    private int MAX_RETRIES = 10;

    public VBoxLayout(Container target, int axis) {
        super(target, axis);
    }

    @Override
    public void layoutContainer(Container target) {

        int retryCount = 0;
        try {

            super.layoutContainer(target);

        } catch (NullPointerException ex) {

            retryCount++;
            //System.out.println("VVBoxLayout.retryCount: " + retryCount);
            if (retryCount < MAX_RETRIES) {
                layoutContainer(target);
            } else {
                throw ex;
            }

        }
    }
}
