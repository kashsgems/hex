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

package org.trypticon.hex.anno;

/**
 * Interface marking an annotation within the binary.
 *
 * @author trejkaz
 */
public interface Annotation {

    /**
     * Gets the position in the binary of the first byte of the annotation.
     *
     * @return the position in the binary.
     */
    long getPosition();

    /**
     * Gets the length of the annotation.  This is purely for convenience, the
     * authoritative length should be obtained via the interpretor.
     *
     * @return the length of the annotation.
     */
    int getLength();

    /**
     * Gets the interpretor used to interpret the value at the given position.
     *
     * @return the interpretor.  TODO: Decide on nullable vs. non-null.
     */
    Interpretor getInterpretor();

    /**
     * A note added by the user.
     *
     * @return the note added by the user, {@code null} if there is no note.
     */
    String getNote();

}
