package com.totalchange.bunman;

import java.io.IOException;
import java.io.InputStream;

public interface Song extends Comparable<Song> {
    String getArtist();
    String getAlbum();
    String getGenre();
    int getYear();
    int getTrack();
    String getTitle();

    InputStream getInputStream() throws IOException;
}
