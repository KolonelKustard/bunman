package com.totalchange.bunman.jd7;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class Jd7FileFinderTests {
    @Test
    public void testFindTrackFile() {
        File file1 = new File("test track 1.mp3");
        File file2 = new File("test track 2.wav");
        File file3 = new File("test track 3.mpeg");
        File file4 = new File("test track 4.mpg");

        Jd7FileFinder ff = new Jd7FileFinder(new File[] { file1, file2, file3,
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
        Jd7FileFinder ff = new Jd7FileFinder(new File[] { testFile });
        assertEquals(".mp3", ff.findTrackFile(".mp3").getName());
    }

    @Test
    public void testLongExtension() {
        File testFile = new File("not.an.exte.nsion");
        Jd7FileFinder ff = new Jd7FileFinder(new File[] { testFile });
        assertEquals("not.an.exte.nsion", ff.findTrackFile("not.an.exte.nsion")
                .getName());
    }
}
