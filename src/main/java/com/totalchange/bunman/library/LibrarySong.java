package com.totalchange.bunman.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import com.totalchange.bunman.util.AbstractSong;

final class LibrarySong extends AbstractSong {
    private File file;
    private Format format;
    private String artist;
    private String album;
    private String genre;
    private int year;
    private int track;
    private String title;

    LibrarySong(File file, Tag tag) {
        this.file = file;

        this.format = workOutFormat(file);
        this.artist = tag.getFirst(FieldKey.ARTIST);
        this.album = tag.getFirst(FieldKey.ALBUM);
        this.genre = tag.getFirst(FieldKey.GENRE);

        try {
            this.year = Integer.parseInt(tag.getFirst(FieldKey.YEAR));
        } catch (NumberFormatException nfEx) {
            this.year = -1;
        }

        try {
            this.track = Integer.parseInt(tag.getFirst(FieldKey.TRACK));
        } catch (NumberFormatException nfEx) {
            this.track = -1;
        }

        this.title = tag.getFirst(FieldKey.TITLE);
    }

    public Format getFormat() {
        return format;
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

    public int getTrack() {
        return track;
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }
}
