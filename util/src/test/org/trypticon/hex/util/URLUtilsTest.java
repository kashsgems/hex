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

package org.trypticon.hex.util;

import java.io.File;
import java.net.URL;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link URLUtils}.
 *
 * @author trejkaz
 */
public class URLUtilsTest {

    @Test
    public void testToFile() throws Exception {
        File file = new File(System.getProperty("user.home"));
        assertEquals("Round trip gives the wrong result", file, URLUtils.toFile(file.toURI().toURL()));
    }

    @Test
    public void testToFileForInvalidURL() throws Exception {
        assertEquals("Wrong result for invalid URL case", new File("/path/with spaces"),
                     URLUtils.toFile(new URL("file:/path/with spaces")));
    }
}
