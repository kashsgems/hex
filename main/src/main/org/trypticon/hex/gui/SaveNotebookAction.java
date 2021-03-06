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

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.trypticon.hex.gui.notebook.Notebook;
import org.trypticon.hex.gui.notebook.NotebookFileFilter;
import org.trypticon.hex.gui.notebook.NotebookStorage;
import org.trypticon.hex.util.swingsupport.ActionException;
import org.trypticon.hex.util.swingsupport.BaseAction;
import org.trypticon.hex.util.swingsupport.ImprovedFileChooser;

/**
 * Action to save the notebook.
 *
 * @author trejkaz
 */
public class SaveNotebookAction extends BaseAction {
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
        doSave();
    }

    private void doSave() throws Exception {
        HexFrame frame = HexFrame.findActiveFrame();
        if (frame == null || frame.getNotebook() == null) {
            throw new ActionException("Focus is not on a hex viewer window");
        }

        Notebook notebook = frame.getNotebook();

        URL location;
        if (!alwaysAsk && notebook.getNotebookLocation() != null) {
            location = notebook.getNotebookLocation();
        } else {
            JFileChooser chooser = new ImprovedFileChooser();
            chooser.setFileFilter(new NotebookFileFilter());

            while (true) {
                File chosenFile;
                if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    chosenFile = chooser.getSelectedFile();
                    if (chosenFile.exists()) {
                        if (JOptionPane.showConfirmDialog(frame, "The file already exists.  Is it OK to overwrite it?",
                                                         "File Exists",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION) {
                            continue;
                        }
                    }
                    location = chosenFile.toURI().toURL();
                    break;
                } else {
                    return;
                }
            }
        }

        new NotebookStorage().write(notebook, location);

        notebook.setNotebookLocation(location);
    }

    /**
     * Saves the document programmatically, showing user interface as required.
     * If errors occur, UI is shown for these and {@code false} is returned.
     *
     * @param owner the owner to use in the event that dialogs need to be displayed.
     * @return {@code true} if the save was successful.
     */
    public boolean save(Component owner) {
        try {
            doSave();
            return true;
        } catch (Throwable t) {
            handleError(owner, t);
            return false;
        }
    }
}
