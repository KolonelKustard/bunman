package com.totalchange.bunman.jd7;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import com.samskivert.net.cddb.CDDB;
import com.samskivert.net.cddb.CDDBException;

final class CddbQuerier implements Closeable {
    private CDDB cddb = new CDDB();

    CddbQuerier(String hostname, int port) throws IOException {
        try {
            cddb.connect(hostname, port);
        } catch (CDDBException cddbEx) {
            throw new IOException(cddbEx);
        }
    }

    List<IdnFile> queryForAlbum(String id, String artist, String album)
            throws IOException {
        return null;
    }

    public void close() throws IOException {
        cddb.close();
    }
}
