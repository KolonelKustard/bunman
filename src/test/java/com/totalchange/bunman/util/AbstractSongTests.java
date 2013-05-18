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
