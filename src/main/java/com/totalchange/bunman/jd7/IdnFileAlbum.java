package com.totalchange.bunman.jd7;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.totalchange.bunman.cddb.CddbResult;

final class IdnFileAlbum implements Album, Serializable {
    private static final long serialVersionUID = 3817575634261453776L;

    private static final String ARTIST_ALBUM_SPLITTER = "/";

    private transient File idnFile;

    private String artist;
    private String album;
    private String genre;
    private int year;
    private List<String> tracks;

    IdnFileAlbum(File idnFile, CddbResult cddb) {
        this.idnFile = idnFile;

        String[] split = Jd7Utils.splitArtistAlbumBits(cddb.getTitle(),
                ARTIST_ALBUM_SPLITTER);
        this.artist = split[0];
        this.album = split[1];
        this.genre = cddb.getGenre();
        this.year = cddb.getYear();
        this.tracks = new ArrayList<String>(Arrays.asList(cddb.getTrackNames()));
    }

    File getIdnFile() {
        return idnFile;
    }

    void setIdnFile(File idnFile) {
        this.idnFile = idnFile;
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
