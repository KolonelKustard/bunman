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

import org.junit.Test;

import com.totalchange.bunman.cddb.CddbResult;
import com.totalchange.bunman.jb7.IdnFileAlbum;
import com.totalchange.bunman.jb7.IdnFileAlbumCache;

public class IdnFileAlbumCacheTests {
    @Test
    public void testPutFileIntoCache() throws IOException {
        File tmpDir = new File("target/IdnFileAlbumCacheTests");
        if (tmpDir.exists()) {
            for (File contents : tmpDir.listFiles()) {
                contents.delete();
            }
            tmpDir.delete();
        }
        tmpDir.mkdirs();

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

        assertNull("Shouldn't be cached yet",
                cache.getFileFromCache("test1", "test1"));
        cache.putFileIntoCache("test1", "test1", test1);

        IdnFileAlbum result1 = cache.getFileFromCache("test1", "test1");
        assertEquals("test1", result1.getArtist());

        // Close and re-open cache and check get result again
        cache.close();
        cache = new IdnFileAlbumCache(tmpDir);

        result1 = cache.getFileFromCache("test1", "test1");
        assertEquals("test1", result1.getArtist());

        cache.close();
    }
}
