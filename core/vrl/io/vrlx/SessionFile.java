/* 
 * SessionFile.java
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

import vrl.system.AbstractPluginDependency;
import vrl.system.PluginDependency;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Session file.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface SessionFile extends SessionEntryFolder{
    
    /**
     * Returns the path of this session file.
     * @return the path of this session file
     */
    String getPath();
    
    /**
     * Defines the path of this session file.
     * @param path path to set
     */
    void setPath(String path);

    @Override
    boolean addEntry(SessionEntry entry);

    @Override
    SessionEntry getEntry(String name);

    /**
     * Returns the file version info.
     * @return the file version info
     */
    FileVersionInfo getVersionInfo();

    @Override
    boolean removeEntry(String name);

    /**
     * Defines the file version info
     * @param versionInfo the file version info to set
     */
    void setVersionInfo(FileVersionInfo versionInfo);

    /**
     * Defines the plugins this file depends on.
     * @param plugins the plugin this file depends on
     */
    void setPluginDependencies(Collection<AbstractPluginDependency> plugins);

    /**
     * Returns the plugin this file depends on.
     * @return the plugin this file depends on
     */
    Collection<AbstractPluginDependency> getPluginDependencies();

}
