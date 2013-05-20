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

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;

public class IdnFileAlbumCache implements Closeable {
    private static final String CACHE_FILENAME = "IdnFileAlbumCache.bin";

    private static final class IdnFileKey implements Serializable {
        private static final long serialVersionUID = -5572602102338232684L;

        private String id;
        private String directoryName;

        private IdnFileKey(String id, String directoryName) {
            this.id = id;
            this.directoryName = directoryName;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((directoryName == null) ? 0 : directoryName.hashCode());
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof IdnFileKey)) {
                return false;
            }
            IdnFileKey other = (IdnFileKey) obj;
            if (directoryName == null) {
                if (other.directoryName != null) {
                    return false;
                }
            } else if (!directoryName.equals(other.directoryName)) {
                return false;
            }
            if (id == null) {
                if (other.id != null) {
                    return false;
                }
            } else if (!id.equals(other.id)) {
                return false;
            }
            return true;
        }
    }

    private RecordManager db;
    private Map<IdnFileKey, IdnFileAlbum> map;

    public IdnFileAlbumCache(File cacheDir) throws IOException {
        if (!cacheDir.exists()) {
            throw new FileNotFoundException("Cache directory " + cacheDir
                    + " not found");
        }

        if (!cacheDir.isDirectory()) {
            throw new IOException("Cache directory " + cacheDir
                    + " is not a directory");
        }

        db = RecordManagerFactory.createRecordManager(new File(cacheDir,
                CACHE_FILENAME).getCanonicalPath());
        map = db.hashMap(CACHE_FILENAME);
    }

    void putFileIntoCache(String id, String directoryName, IdnFileAlbum file) {
        IdnFileKey key = new IdnFileKey(id, directoryName);
        map.put(key, file);
        try {
            db.commit();
        } catch (IOException ioEx) {
            throw new RuntimeException(ioEx);
        }
    }

    IdnFileAlbum getFileFromCache(String id, String directoryName) {
        IdnFileKey key = new IdnFileKey(id, directoryName);
        return map.get(key);
    }

    public void close() throws IOException {
        db.close();
    }
}
