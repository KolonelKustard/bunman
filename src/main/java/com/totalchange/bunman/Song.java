package com.totalchange.bunman;

import java.io.IOException;
import java.io.InputStream;

public interface Song {
    String getArtist();
    String getAlbum();
    String getTitle();
    String getGenre();
    int getYear();

    InputStream getInputStream() throws IOException;
}
