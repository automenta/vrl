/* 
 * PluginConfiguratorDialog.java
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

package vrl.dialogs;

import vrl.annotation.MethodInfo;
import vrl.annotation.ObjectInfo;
import vrl.annotation.ParamInfo;
import vrl.lang.visual.ClassInfoObject;
import vrl.reflection.VisualCanvas;
import vrl.system.PluginInfo;


/**
 * Shows a dialog that allows to specify plugin information.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class PluginConfiguratorDialog {

    /**
     * Shows the dialog.
     * @param parent the parent component of the dialog
     * @param className the name of the class to recompile
     * @return <code>true</code> if the "yes" button has been clicked;
     *         <code>false</code> otherwise
     */
    public static PluginInfo show(VisualCanvas parent, String title) {

        final NewPluginDialogInfo dialogObject = new NewPluginDialogInfo();

        boolean result = RDialog.showConfirmDialog(
                parent, title, dialogObject, "Create").isValid();

        PluginInfo clsInfoObj = null;

        if (result) {
            clsInfoObj = new PluginInfo(
                    dialogObject.getName(),
                    dialogObject.getVersion(),
                    dialogObject.getDescription());
        }

        return clsInfoObj;
    }

    /**
     * Shows the dialog.
     * @param parent the parent component of the dialog
     * @param className the name of the class to recompile
     * @return <code>true</code> if the "yes" button has been clicked;
     *         <code>false</code> otherwise
     */
    public static PluginInfo show(VisualCanvas parent) {

        return show(parent, "New Plugin");
    }
}
@ObjectInfo(serialize = false, controlFlowIn = false, controlFlowOut = false)
class NewPluginDialogInfo extends DialogUIClass {

    private String pluginName;
    private String pluginVersion;
//    private String methodName;
    private String description;
//    private String componentInfo;
//    private String objectInfo;
//    private String methodInfo;

    public NewPluginDialogInfo() {
        super(1);
    }

    @MethodInfo(name = "Plugin Properties:",
    interactive = false, hide = false)
    public void setValues(
            @ParamInfo(name = "Name:",
            style = "plugin-name") String name,
             @ParamInfo(name = "Version:",
            style = "plugin-version") String version,
            @ParamInfo(name = "Description:") String description) {
        this.pluginName = name;
        this.pluginVersion = version;
        this.description = description;
        validCall();
    }

//    @MethodInfo(name = "Additional Component Properties:",
//    interactive = false, hide = true)
//    public void setAdditionalValues(
//            @ParamInfo(name = "ComponentInfo") String componentInfo,
//            @ParamInfo(name = "ObjectInfo") String objectInfo,
//            @ParamInfo(name = "Method Name:", options = "value=\"run\"",
//            style = "method-name") String name,
//            @ParamInfo(name = "MethodInfo") String methodInfo) {
//        this.methodName = name;
//        this.componentInfo = componentInfo;
//        this.objectInfo = objectInfo;
//        this.methodInfo = methodInfo;
//    }

    /**
     * @return the name
     */
    @MethodInfo(noGUI = true)
    public String getName() {
        return pluginName;
    }

    /**
     * @return the description
     */
    @MethodInfo(noGUI = true)
    public String getDescription() {
        return description;
    }

//    /**
//     * @return the methodName
//     */
//    @MethodInfo(noGUI = true)
//    public String getMethodName() {
//        return methodName;
//    }
//
//    /**
//     * @return the componentInfo
//     */
//    @MethodInfo(noGUI = true)
//    public String getComponentInfo() {
//        return componentInfo;
//    }
//
//    /**
//     * @return the objectInfo
//     */
//    @MethodInfo(noGUI = true)
//    public String getObjectInfo() {
//        return objectInfo;
//    }
//
//    /**
//     * @return the methodInfo
//     */
//    @MethodInfo(noGUI = true)
//    public String getMethodInfo() {
//        return methodInfo;
//    }

    /**
     * @return the pluginVersion
     */
    @MethodInfo(noGUI = true)
    public String getVersion() {
        return pluginVersion;
    }
}
