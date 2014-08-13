package vrl.dialogs;

///*
// * LoadTutorialSessionDialog.java
// *
// * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
// *
// * Copyright (C) 2009 Michael Hoffer <info@michaelhoffer.de>
// *
// * Supported by the Goethe Center for Scientific Computing of Prof. Wittum
// * (http://gcsc.uni-frankfurt.de)
// *
// * This file is part of Visual Reflection Library (VRL).
// *
// * VRL is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License version 3
// * as published by the Free Software Foundation.
// *
// * VRL is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// *
// * Linking this library statically or dynamically with other modules is
// * making a combined work based on this library.  Thus, the terms and
// * conditions of the GNU General Public License cover the whole
// * combination.
// *
// * As a special exception, the copyright holders of this library give you
// * permission to link this library with independent modules to produce an
// * executable, regardless of the license terms of these independent
// * modules, and to copy and distribute the resulting executable under
// * terms of your choice, provided that you also meet, for each linked
// * independent module, the terms and conditions of the license of that
// * module.  An independent module is a module which is not derived from
// * or based on this library.  If you modify this library, you may extend
// * this exception to your version of the library, but you are not
// * obligated to do so.  If you do not wish to do so, delete this
// * exception statement from your version.
// */
//package eu.mihosoft.vrl.dialogs;
//
//import eu.mihosoft.vrl.io.TutorialSessionFilter;
//import eu.mihosoft.vrl.io.TutorialSessionLoader;
//import java.awt.Component;
//
///**
// * A dialog for loading XML tutorial sessions.
// */
//public class LoadTutorialSessionDialog {
//
//    /**
//     * Opens a load file dialog and loads a XML tutorial session which
//     * is returned as tutorial session object.
//     * @param parent the parent component of the dialog
//     * @return the loaded session as tutorial session object or
//     *         <code>null</code> if the session couldn't be loaded
//     */
//    public static Object showDialog(Component parent) {
//        FileDialogManager dialogManager = new FileDialogManager();
//        return dialogManager.loadFile(parent, new TutorialSessionLoader(),
//                new TutorialSessionFilter());
//    }
//}
