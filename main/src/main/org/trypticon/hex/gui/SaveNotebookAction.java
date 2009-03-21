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

package org.trypticon.hex.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.SwingUtilities;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

import org.trypticon.hex.swingsupport.BaseAction;
import org.trypticon.hex.gui.notebook.NotebookFileFilter;
import org.trypticon.hex.gui.notebook.Notebook;
import org.trypticon.hex.gui.notebook.NotebookStorage;

/**
 * Action to save the notebook.
 *
 * @author trejkaz
 */
class SaveNotebookAction extends BaseAction {
    private final boolean alwaysAsk;

    SaveNotebookAction(boolean alwaysAsk) {
        this.alwaysAsk = alwaysAsk;

        int baseMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        if (alwaysAsk) {
            putValue(NAME, "Save As...");
            putValue(MNEMONIC_KEY, (int) 'a');
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, baseMask | InputEvent.SHIFT_DOWN_MASK));
        } else {
            putValue(NAME, "Save");
            putValue(MNEMONIC_KEY, (int) 's');
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, baseMask));
        }
    }

    protected void doAction(ActionEvent event) throws Exception {
        Component owner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
        HexFrame frame = (HexFrame) SwingUtilities.getWindowAncestor(owner);
        Notebook notebook = frame.getNotebook();

        URL location;
        if (!alwaysAsk && notebook.getNotebookLocation() != null) {
            location = notebook.getNotebookLocation();
        } else {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new NotebookFileFilter());

            if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                location = chooser.getSelectedFile().toURI().toURL();

                // TODO: Should correct extension to .xml
            } else {
                return;
            }
        }

        new NotebookStorage().write(notebook, location);
    }
}