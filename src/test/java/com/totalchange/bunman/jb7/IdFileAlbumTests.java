/*
 * Copyright 2013 Ralph Jones
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.totalchange.bunman.jb7;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.totalchange.bunman.jb7.IdFileAlbum;

public class IdFileAlbumTests {
    @Test
    public void testIdFile() throws IOException {
        IdFileAlbum idf = new IdFileAlbum(new File(
                "src/test/jb7/idtests/10cc   The Very Best Of 10cc/id"));
        assertEquals("10cc", idf.getArtist());
        assertEquals("The Very Best Of 10cc", idf.getAlbum());
        assertEquals(1997, idf.getYear());
        assertEquals("Rock", idf.getGenre());

        List<String> expected = new ArrayList<String>();
        expected.add("Donna");
        expected.add("Rubber Bullets");
        expected.add("The Dean And I");
        expected.add("The Wall Street Shuffle");
        expected.add("Silly Love");
        expected.add("Life Is A Minestrone");
        expected.add("Une Nuit A Paris, Pt. 1: One Night In Paris\\Pt. 2: The Same N");
        expected.add("I'm Not In Love ");
        expected.add("Art For Art's Sake");
        expected.add("I'm Mandy, Fly Me");
        expected.add("The Things We Do For Love");
        expected.add("Good Morning Judge");
        expected.add("Dreadlock Holiday");
        expected.add("People In Love");
        expected.add("Under Your Thumb");
        expected.add("Wedding Bells");
        expected.add("Cry");
        expected.add("Neanderthal Man");

        assertEquals(expected, idf.getTracks());
    }
}
