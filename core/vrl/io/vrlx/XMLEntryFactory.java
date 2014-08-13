/* 
 * XMLEntryFactory.java
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

package vrl.io.vrlx;

import vrl.system.VParamUtil;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * Entry factory for XML based session files.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class XMLEntryFactory implements SessionEntryFactory {

    @Override
    public SessionFile newFile(FileVersionInfo version, String path) {
        XMLFile f =  new XMLFile(version);
        f.setPath(path);
        return f;
    }

    @Override
    public SessionFile newFile(String path) {
        XMLFile f = new XMLFile();
        f.setPath(path);
        return f;
    }

    @Override
    public SessionEntryFile newEntryFile(String name) {
        return new XMLEntry(name);
    }

    @Override
    public SessionEntryFile newEntryFile(String name, Object data) {
        return new XMLEntry(name, data);
    }

    @Override
    public SessionEntryFolder newEntryFolder(String name) {
        return new XMLFolder(name);
    }

    @Override
    public SessionEntryFolder newEntryFolder(String name,
            Collection<SessionEntry> data) {
        return new XMLFolder(name, data);
    }

    @Override
    public SessionFile loadFile(File f) throws IOException {
        
        VParamUtil.throwIfNull(f);

        XMLDecoder d = new XMLDecoder(new BufferedInputStream(
                new FileInputStream(f)));

        XMLFile sessionFile = (XMLFile) d.readObject();
        
        sessionFile.setPath(f.getAbsolutePath());
        
        return sessionFile;
    }

    @Override
    public void saveFile(SessionFile file, File f)
            throws IOException {
        
        VParamUtil.throwIfNull(file, f);

        XMLEncoder e = new XMLEncoder(new BufferedOutputStream(
                new FileOutputStream(f)));
        e.writeObject(file);

        e.close();
    }
}
