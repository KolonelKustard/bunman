package com.totalchange.bunman.jd7;

import java.io.Serializable;

class IdnFileAlbumCache {
    private static final class IdnFileKey implements Serializable {
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

    void putFileIntoCache(String id, String directoryName, IdnFileAlbum file) {
        // TODO: Write me
        IdnFileKey key = new IdnFileKey(id, directoryName);
    }

    IdnFileAlbum getFileFromCache(String id, String directoryName) {
        // TODO: Write me
        IdnFileKey key = new IdnFileKey(id, directoryName);
        return null;
    }
}
