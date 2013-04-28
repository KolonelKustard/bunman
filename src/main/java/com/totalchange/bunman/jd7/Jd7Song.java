package com.totalchange.bunman.jd7;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.totalchange.bunman.Song;

final class Jd7Song implements Song {
    private AlbumData albumData;
    private String title;
    private File file;

    Jd7Song(AlbumData albumData, String title, File file) {
        this.albumData = albumData;
        this.title = title;
        this.file = file;
    }

    public String getArtist() {
        return albumData.getArtist();
    }

    public String getAlbum() {
        return albumData.getAlbum();
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return albumData.getGenre();
    }

    public int getYear() {
        return albumData.getYear();
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }
}
