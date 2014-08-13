/* 
 * MethodUtil.java
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

package vrl.reflection;

import vrl.annotation.MethodInfo;
import vrl.annotation.ParamGroupInfo;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */


public class MethodUtil {
    
     // no instanciation allowed
    private MethodUtil() {
        throw new AssertionError(); // not in this class either!
    }
    
//    /**
//     * Returns the group name of the specified method. If the group name
//     * defined by the param info is
//     * <code>null</code> or empty the default name will be used instead.
//     *
//     * @param info param info annotation
//     * @return the group name of the specified parameter
//     */
//    public static String getGroupName(MethodInfo info) {
//
//        try {
//            if (info == null) {
//                return "default";
//            }
//
//            if (info.group() == null) {
//                return "default";
//            }
//
//            if (info.group().isEmpty()) {
//                return "default";
//            }
//
//            return info.group();
//
//        } catch (Throwable tr) {
//            // to ensure file compatibility for files created before 20.10.2011
//            // try/catch block is not necessary if old files don't need support
//            // anymore
//            // ignore java.lang.AbstractMethodError etc.
//            System.out.println(
//                    ">> warning: deprecated file format (before 20.10.2011)");
//        }
//
//        return "default";
//    }
    
}
