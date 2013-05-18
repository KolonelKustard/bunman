package com.totalchange.bunman.util;

import java.io.IOException;
import java.io.InputStream;

class TestAbstractSong extends AbstractSong {
    Format format = Format.Mp3;
    String artist = "Test artist";
    String album = "Test album";
    String genre = "Test genre";
    int year = 2000;
    int track = -1;
    String title = "Test title";
    InputStream inputStream = null;

    @Override
    public Format getFormat() {
        return format;
    }

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public String getAlbum() {
        return album;
    }

    @Override
    public String getGenre() {
        return genre;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getTrack() {
        return track;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }
}