package com.totalchange.bunman.jd7;

import java.util.List;

interface AlbumData {
    String getArtist();
    String getAlbum();
    String getGenre();
    int getYear();
    List<String> getTracks();
}
