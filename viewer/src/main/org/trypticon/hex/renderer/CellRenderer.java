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

package org.trypticon.hex.renderer;

import java.awt.Component;

import org.trypticon.hex.HexViewer;

/**
 * Interface for rendering cells in the viewer.
 */
public interface CellRenderer
{
    // XXX: We should still consider allowing the user to customise the columns more freely than this.
    public static final int ROW_OFFSET = 0;
    public static final int HEX = 1;
    public static final int ASCII = 2;

    Component getRendererComponent(HexViewer viewer, boolean selected, boolean onCursorRow, boolean atCursor,
                                   long position, int valueDisplayMode);
}
