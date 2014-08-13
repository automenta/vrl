/* 
 * XMLFolder.java
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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Entry folder for XML based session files.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public final class XMLFolder implements SessionEntry, SessionEntryFolder{

    private String name;

    private Collection<SessionEntry> content =
            new ArrayList<SessionEntry>();

    public XMLFolder() {
        //
    }

    public XMLFolder(String name) {
        this.name = name;
    }

    public XMLFolder(String name, Collection<SessionEntry> content) {
        this.name = name;
        setContent(content);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isFolder() {
        return true;
    }

    @Override
    public boolean addEntry(SessionEntry sE) {
        return getContent().add(sE);
    }

    @Override
    public boolean removeEntry(String name) {
        SessionEntry del = null;
        for (SessionEntry sE : getContent()) {
            if (name.equals(sE.getName())) {
                del = sE;
                break;
            }
        }

        return getContent().remove(del);
    }

    @Override
    public SessionEntry getEntry(String name) {
        SessionEntry result = null;
        for (SessionEntry sE : getContent()) {
            if (name.equals(sE.getName())) {
                result = sE;
                break;
            }
        }

        return result;
    }

    @Override
    public void setContent(Collection<SessionEntry> content) {
        this.content = content;
    }

    @Override
    public Collection<SessionEntry> getContent() {
        return content;
    }

}
