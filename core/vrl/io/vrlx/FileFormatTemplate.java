/* 
 * FileFormatTemplate.java
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

import vrl.visual.Canvas;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
final class FileFormatTemplate implements DynamicFileFormat {

    private HashMap<String, SessionTask> sessionTasks =
            new HashMap<String, SessionTask>();
    private ArrayList<String> paths = new ArrayList<String>();
    private IOModel model;

    /**
     * Constructor.
     * @param model the model to use
     */
    public FileFormatTemplate(IOModel model) {
        this.model = model;
    }

    @Override
    public void addTask(String entryPath, SessionTask t) {
        sessionTasks.put(entryPath, t);
        paths.add(entryPath);
    }

    @Override
    public void addTaskAt(int i, String entryPath, SessionTask t) {
        sessionTasks.put(entryPath, t);
        paths.add(i, entryPath);
    }

    @Override
    public Integer getTaskIndex(String path) {
        Integer result = null;

        for (int i = 0; i < paths.size(); i++) {
            if (paths.get(i).equals(path)) {
                result = i;
                break;
            }
        }

        return result;
    }

//    @Override
//    public void addTaskAfter(String index, String entryPath, SessionTask t) {
//        sessionTasks.put(entryPath, t);
//        paths.add(getTaskIndex(index)+1,entryPath);
//    }
//    @Override
//    public void addTaskBefore(String index, String entryPath, SessionTask t) {
//        sessionTasks.put(entryPath, t);
//        paths.add(getTaskIndex(index),entryPath);
//    }
    @Override
    public SessionTask getTask(String entryName) {
        return sessionTasks.get(entryName);
    }

    @Override
    public boolean performLoadTasks(
            Canvas canvas, SessionFile file) {
        boolean result = true;

        for (String sE : paths) {
            SessionTask t = getTask(sE);
            if (t != null
                    && (t.getType() == TaskType.LOAD
                    || t.getType() == TaskType.LOAD_AND_SAVE)) {
                boolean v = false;
                try {
                    v = t.load(canvas, model.getEntry(file, sE));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FileFormatTemplate.class.getName()).
                            log(Level.SEVERE, null, ex);
                }

                System.out.println(
                        ">> Loading \"" + sE + "\": [" + v + "]");

                if (result==true) {
                    result = v;
                }
            }
        }

        return result;
    }

    @Override
    public boolean performSaveTasks(
            Canvas canvas, SessionFile file) {
        boolean result = true;
        for (String sE : paths) {
            SessionTask t = getTask(sE);
            if (t != null
                    && (t.getType() == TaskType.SAVE
                    || t.getType() == TaskType.LOAD_AND_SAVE)) {
                boolean v = false;
                try {
                    v = t.save(canvas, model.createFile(file, sE));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FileFormatTemplate.class.getName()).
                            log(Level.SEVERE, null, ex);
                }

                System.out.println(
                        ">> Saving \"" + sE + "\": [" + v + "]");

                if (result==true) {
                    result = v;
                }
            }
        }

        return result;
    }

    @Override
    public Collection<?> getEntryPaths() {
        return paths;
    }

    @Override
    public SessionTask getTaskAt(int i) {
        return getTask(paths.get(i));
    }

    @Override
    public IOModel getModel() {
        return model;
    }
}
