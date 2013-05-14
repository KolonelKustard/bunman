package com.totalchange.bunman.util;

import java.io.File;

import com.totalchange.bunman.Song;

public abstract class AbstractSong implements Song, Comparable<Song> {
    public int compareTo(Song o) {
        return SongComparator.staticCompare(this, o);
    }

    protected Format workOutFormat(File file) {
        for (Format format : Format.values()) {
            if (file.getName().endsWith("." + format.getExtension())) {
                return format;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "format=" + getFormat() + ", artist=" + getArtist() + ", album="
                + getAlbum() + ", genre=" + getGenre() + ", year=" + getYear()
                + ", track=" + getTrack() + ", title=" + getTitle();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((getFormat() == null) ? 0 : getFormat().hashCode());
        result = prime * result
                + ((getAlbum() == null) ? 0 : getAlbum().hashCode());
        result = prime * result
                + ((getArtist() == null) ? 0 : getArtist().hashCode());
        result = prime * result
                + ((getGenre() == null) ? 0 : getGenre().hashCode());
        result = prime * result + getYear();
        result = prime * result + getTrack();
        result = prime * result
                + ((getTitle() == null) ? 0 : getTitle().hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Song)) {
            return false;
        }

        Song other = (Song) obj;
        return SongComparator.staticCompare(this, other) == 0;
    }
}
