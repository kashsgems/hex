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

package org.trypticon.hex.anno.primitive;

import org.trypticon.hex.anno.Value;

/**
 * An unsigned short value.
 *
 * @author trejkaz
 */
public class UShort implements Value {
    private final short value;

    public UShort(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }

    public int length() {
        return 2;
    }

    public String toString() {
        return String.valueOf(value & 0xFFFF);
    }
}
