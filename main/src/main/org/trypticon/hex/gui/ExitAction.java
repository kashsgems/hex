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

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;

/**
 * Action to exit the application.
 */
class ExitAction extends AbstractAction {
    ExitAction() {
        putValue(NAME, "Exit");
        putValue(MNEMONIC_KEY, (int) 'x');
    }

    public void actionPerformed(ActionEvent event) {
        List<Frame> closeLater = new ArrayList<Frame>(2);
        for (Frame frame : Frame.getFrames()) {
            if (frame instanceof HexFrame) {
                if (((HexFrame) frame).prepareForClose()) {
                    closeLater.add(frame);
                } else {
                    // User decided it wasn't OK to close after all.
                    return;
                }
            } else {
                frame.dispose();
            }
        }

        for (Frame frame : closeLater) {
            frame.dispose();
        }
    }
}
