package com.totalchange.bunman.jd7;

import java.util.List;

interface Album {
    String getArtist();
    String getAlbum();
    String getGenre();
    int getYear();
    List<String> getTracks();
}
