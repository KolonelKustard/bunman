package com.totalchange.bunman.jd7;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

final class NoFile implements AlbumData {
    private static final String SPLITTER_DIRNAME = "  ";

    private String artist;
    private String album;
    private List<String> tracks;

    NoFile(File dir) {
        String[] split = Jd7Utils.splitArtistAlbumBits(dir.getName(),
                SPLITTER_DIRNAME);
        artist = split[0];
        album = split[1];

        tracks = new ArrayList<String>();
        for (File file : dir.listFiles()) {
            String lower = file.getName().toLowerCase();
            if (lower.endsWith(".mp3") || lower.endsWith(".wav")) {
                tracks.add(Jd7Utils.removeExtension(file.getName()));
            }
        }
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return null;
    }

    public int getYear() {
        return -1;
    }

    public List<String> getTracks() {
        return tracks;
    }

}
