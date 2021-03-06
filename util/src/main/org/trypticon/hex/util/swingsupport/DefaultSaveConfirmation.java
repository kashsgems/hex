/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009  Trejkaz, Hex Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.trypticon.hex.util.swingsupport;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Default save confirmation support, using the option pane's built-in dialog support.
 */
public class DefaultSaveConfirmation extends SaveConfirmation {
    protected Object showOptionPane(Component owner, JOptionPane optionPane) {
        JDialog dialog = optionPane.createDialog(owner, "Save Confirmation");
        dialog.setVisible(true);

        return optionPane.getValue();
    }
}
