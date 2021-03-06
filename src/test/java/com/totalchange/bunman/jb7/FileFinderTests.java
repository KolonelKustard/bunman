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

import org.junit.Test;

import com.totalchange.bunman.jb7.FileFinder;

public class FileFinderTests {
    @Test
    public void testFindTrackFile() {
        File file1 = new File("test track 1.mp3");
        File file2 = new File("test track 2.wav");
        File file3 = new File("test track 3.mpeg");
        File file4 = new File("test track 4.mpg");

        FileFinder ff = new FileFinder(new File[] { file1, file2, file3,
                file4 }, file4);

        assertEquals(file1, ff.findTrackFile("test track 1"));
        assertEquals(file2, ff.findTrackFile("test track 2"));
        assertEquals(file3, ff.findTrackFile("test track 3"));

        assertEquals(file1, ff.findTrackFile("test track / 1"));
        assertEquals(file2, ff.findTrackFile("test track 2   "));
        assertEquals(file3, ff.findTrackFile("Test Track: 3."));

        assertFalse(ff.findTrackFile("test track 4").equals(file4));
    }

    @Test
    public void testJustExtension() {
        File testFile = new File(".mp3");
        FileFinder ff = new FileFinder(new File[] { testFile });
        assertEquals(".mp3", ff.findTrackFile(".mp3").getName());
    }

    @Test
    public void testLongExtension() {
        File testFile = new File("not.an.exte.nsion");
        FileFinder ff = new FileFinder(new File[] { testFile });
        assertEquals("not.an.exte.nsion", ff.findTrackFile("not.an.exte.nsion")
                .getName());
    }
}
