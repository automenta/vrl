/* 
 * VMenuController.java
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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Menu controller.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class VMenuController implements MenuController {

    private Collection<Action> actions = new ArrayList<Action>();
    private boolean disableIfEmpty = true;

    @Override
    public void addAction(Action a) {
        if (!actions.contains(a)) {
            actions.add(a);
        }
    }

    @Override
    public boolean removeAction(ActionViewAdapter menu, Action a) {

        if (menu == null) {
            return false;
        }

        boolean removed = actions.remove(a);


        menu.remove(a);

        if (disableIfEmpty && menu.isEmpty()) {
            menu.setEnabled(false);
        }

        return removed;
    }

    /**
     * Returns all actions of this menu controller.
     *
     * @return all actions of this menu controller
     */
    public Collection<Action> getActions() {
        return actions;
    }

    @Override
    public void buildMenu(ActionViewAdapter menu, Object owner) {

        if (menu == null) {
            return;
        }

        for (final Action a : actions) {

            menu.add(a, owner);
        }

        if (disableIfEmpty && menu.isEmpty()) {
            menu.setEnabled(false);
        } else {
            menu.setEnabled(true);
        }
    }

    @Override
    public void clear(ActionViewAdapter menu, boolean onlyMenu) {

        Collection<Action> oldActions = new ArrayList<Action>(actions);

        if (menu != null) {
            for (final Action a : oldActions) {

                menu.remove(a);
            }

            if (disableIfEmpty && menu.isEmpty()) {
                menu.setEnabled(false);
            }
        }

        if (!onlyMenu) {
            actions.clear();
        }
    }

    @Override
    public void setDisabledIfEmpty(boolean v) {
        disableIfEmpty = v;
    }
}
