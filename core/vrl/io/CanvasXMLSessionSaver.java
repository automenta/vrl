/* 
 * CanvasXMLSessionSaver.java
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

package vrl.io;

import vrl.io.vrlx.VRLXSessionController;
import vrl.io.vrlx.VRLXReflection;
import vrl.reflection.VisualCanvas;
import vrl.system.VParamUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * XML session saver. This is the recommended way to save VRL sessions. It
 * is well supported and replaces the binary session management.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class CanvasXMLSessionSaver implements FileSaver {

    @Override
    public void saveFile(Object o, File file, String ext)
            throws FileNotFoundException {
        
        // ensure that o is an instance of VisualCanvas
        VParamUtil.throwIfNotValid(
                VParamUtil.VALIDATOR_INSTANCEOF, VisualCanvas.class, o);

        VisualCanvas canvas = (VisualCanvas) o;
        
        // name of backup file
        String fileName = file.getPath()+"~";
        
        // if a previous version of the file already exists make a backup
        // note: existing backup files will be silently overwritten
        if (file.exists() && file.isFile()) {
            try {
                 IOUtil.copyFile(file, new File(fileName));
            } catch (IOException ex) {
                Logger.getLogger(CanvasXMLSessionSaver.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }

        // create session controller with XML format
        VRLXSessionController sessionController =
                                            new VRLXSessionController(
                                            VRLXReflection.getVRLXFormat());
        
        // save the session
        sessionController.saveSession(canvas,file);
    }

    @Override
    public String getDefaultExtension() {
        return "vrlx";
    }
}
