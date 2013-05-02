package com.totalchange.bunman.jd7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class IdFile implements AlbumData {
    private static final String ARTIST_ALBUM_SPLITTER = "/";

    private String artist;
    private String album;
    private String genre;
    private int year;
    private List<String> tracks;

    IdFile(File idFile) throws IOException {
        readIdFile(idFile);
    }

    private void splitArtistAlbumBits(String artistAlbumBits) {
        String[] split = Jd7Utils.splitArtistAlbumBits(artistAlbumBits,
                ARTIST_ALBUM_SPLITTER);
        this.artist = split[0];
        this.album = split[1];
    }

    private void readIdFile(File idFile) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(idFile));
        try {
            splitArtistAlbumBits(in.readLine());
            String yearStr = in.readLine();
            yearStr = yearStr.trim();
            try {
                this.year = Integer.parseInt(yearStr);
            } catch (NumberFormatException nfEx) {
                this.year = -1;
            }
            this.genre = in.readLine().trim();

            this.tracks = new ArrayList<String>();
            String track;
            while ((track = in.readLine()) != null) {
                track = track.trim();
                if (track.length() > 0) {
                    this.tracks.add(track);
                }
            }
        } finally {
            in.close();
        }
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public List<String> getTracks() {
        return tracks;
    }
}
