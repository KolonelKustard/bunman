package com.totalchange.bunman.jd7;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.mapdb.DB;
import org.mapdb.DBMaker;

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

    private DB db;
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

        db = DBMaker.newFileDB(new File(cacheDir, CACHE_FILENAME)).make();
        map = db.getTreeMap(CACHE_FILENAME);
    }

    void putFileIntoCache(String id, String directoryName, IdnFileAlbum file) {
        IdnFileKey key = new IdnFileKey(id, directoryName);
        map.put(key, file);
        db.commit();
    }

    IdnFileAlbum getFileFromCache(String id, String directoryName) {
        IdnFileKey key = new IdnFileKey(id, directoryName);
        return map.get(key);
    }

    public void close() throws IOException {
        db.close();
    }
}
