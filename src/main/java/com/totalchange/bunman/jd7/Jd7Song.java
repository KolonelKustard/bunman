package com.totalchange.bunman.jd7;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.totalchange.bunman.Song;

final class Jd7Song implements Song {
    private String artist;
    private String album;
    private String title;
    private String genre;
    private int year;
    private File file;

    Jd7Song(String artist, String album, String title, String genre, int year,
            File file) {
        this.artist = artist;
        this.album = album;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.file = file;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }
}
