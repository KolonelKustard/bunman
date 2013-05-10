package com.totalchange.bunman.jd7;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.totalchange.bunman.util.AbstractSong;

final class Jd7Song extends AbstractSong {
    private Album albumData;
    private int track;
    private String title;
    private File file;

    Jd7Song(Album albumData, int track, String title, File file) {
        this.albumData = albumData;
        this.track = track;
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

    public int getTrack() {
        return track;
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public String toString() {
        return "Jd7Song [" + super.toString() + ", file=" + file + "]";
    }
}
