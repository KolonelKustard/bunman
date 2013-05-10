package com.totalchange.bunman.util;

import com.totalchange.bunman.Song;

public abstract class AbstractSong implements Song, Comparable<Song> {
    public int compareTo(Song o) {
        return SongComparator.staticCompare(this, o);
    }

    @Override
    public String toString() {
        return "artist=" + getArtist() + ", album=" + getAlbum() + ", genre="
                + getGenre() + ", year=" + getYear() + ", track=" + getTrack()
                + ", title=" + getTitle();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
