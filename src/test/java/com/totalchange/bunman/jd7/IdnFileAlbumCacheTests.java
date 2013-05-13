package com.totalchange.bunman.jd7;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.totalchange.bunman.cddb.CddbResult;

public class IdnFileAlbumCacheTests {
    @Test
    public void testPutFileIntoCache() throws IOException {
        File tmpDir = new File("target/test/IdnFileAlbumCacheTests");
        tmpDir.mkdirs();
        tmpDir.deleteOnExit();

        IdnFileAlbum test1 = new IdnFileAlbum(new File("test1"),
                new CddbResult() {
                    public int getYear() {
                        return 1991;
                    }

                    public String[] getTrackNames() {
                        return new String[] { "track1", "track2" };
                    }

                    public String getTitle() {
                        return "test1";
                    }

                    public String getGenre() {
                        return "test1";
                    }

                    public String[] getExtendedTrackData() {
                        return null;
                    }

                    public String getExtendedData() {
                        return null;
                    }

                    public String getDiscId() {
                        return "test1";
                    }

                    public String getCategory() {
                        return "test1";
                    }
                });

        IdnFileAlbumCache cache = new IdnFileAlbumCache(tmpDir);

        assertNull("Not in cache yet", cache.getFileFromCache("test1", "test1"));
        cache.putFileIntoCache("test1", "test1", test1);

        IdnFileAlbum result1 = cache.getFileFromCache("test1", "test1");
        assertEquals("test1", result1.getArtist());

        // Close and re-open cache and check get result again
        cache.close();
        cache = new IdnFileAlbumCache(tmpDir);

        result1 = cache.getFileFromCache("test1", "test1");
        assertEquals("test1", result1.getArtist());

        cache.close();

        for (File contents : tmpDir.listFiles()) {
            contents.delete();
        }
        tmpDir.delete();
    }
}
