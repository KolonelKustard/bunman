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
package com.totalchange.bunman.util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.totalchange.bunman.Song;

public class AbstractSongTests {
    @Test
    public void testEquals() {
        TestAbstractSong song1 = new TestAbstractSong();
        TestAbstractSong song2 = new TestAbstractSong();

        assertEquals(song1, song2);

        song2.artist = "A.N.Other artist";
        assertNotEquals(song1, song2);
    }

    @Test
    public void testCompareTo() {
        TestAbstractSong song1 = new TestAbstractSong();
        TestAbstractSong song2 = new TestAbstractSong();

        assertEquals(0, song1.compareTo(song2));

        song1.artist = "A";
        song2.artist = "B";
        assertEquals(-1, song1.compareTo(song2));

        song2.artist = "A";
        song1.album = "A";
        song2.album = "B";
        assertEquals(-1, song1.compareTo(song2));

        song2.album = "A";
        song1.title = "A";
        song2.title = "B";
        assertEquals(-1, song1.compareTo(song2));

        song1.title = "B";
        song2.title = "A";
        song1.track = 1;
        song2.track = 2;
        assertEquals(-1, song1.compareTo(song2));
    }

    @Test
    public void testWorkOutFormat() {
        TestAbstractSong song = new TestAbstractSong();
        assertEquals(Song.Format.Mp3, song.workOutFormat(new File("test.mp3")));
        assertEquals(Song.Format.Mp3, song.workOutFormat(new File("test.MP3")));
        assertEquals(Song.Format.Wav, song.workOutFormat(new File("test.wav")));
        assertEquals(Song.Format.Wav, song.workOutFormat(new File("test.WAV")));
        assertNull(song.workOutFormat(new File("test.m4a")));
    }
}
