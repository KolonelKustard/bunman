package com.totalchange.bunman.util;

import java.util.Comparator;

import com.totalchange.bunman.Song;

public final class SongComparator implements Comparator<Song> {
    private static final int compareStrings(String thisString, String thatString) {
        if (thisString == null) {
            if (thatString == null) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (thatString != null) {
                return thisString.compareToIgnoreCase(thatString);
            } else {
                return 1;
            }
        }
    }

    static final int staticCompare(Song thisSong, Song thatSong) {
        if (thisSong == thatSong) {
            return 0;
        }

        int comparison = 0;

        comparison = compareStrings(thisSong.getArtist(), thatSong.getArtist());
        if (comparison != 0) {
            return comparison;
        }

        comparison = compareStrings(thisSong.getAlbum(), thatSong.getAlbum());
        if (comparison != 0) {
            return comparison;
        }

        comparison = compareStrings(thisSong.getGenre(), thatSong.getGenre());
        if (comparison != 0) {
            return comparison;
        }

        comparison = ((Integer) thisSong.getYear()).compareTo(thatSong
                .getYear());
        if (comparison != 0) {
            return comparison;
        }

        comparison = ((Integer) thisSong.getTrack()).compareTo(thatSong
                .getTrack());
        if (comparison != 0) {
            return comparison;
        }

        comparison = compareStrings(thisSong.getArtist(), thatSong.getArtist());
        if (comparison != 0) {
            return comparison;
        }

        return 0;
    }

    public int compare(Song o1, Song o2) {
        return staticCompare(o1, o2);
    }
}
