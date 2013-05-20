/*
 * Copyright 2013 Ralph Jones
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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

    private static final int compareFormats(Song.Format thisFormat,
            Song.Format thatFormat) {
        if (thisFormat == null) {
            if (thatFormat == null) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (thatFormat != null) {
                return thisFormat.compareTo(thatFormat);
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

        comparison = compareStrings(thisSong.getTitle(), thatSong.getTitle());
        if (comparison != 0) {
            return comparison;
        }

        comparison = compareFormats(thisSong.getFormat(), thatSong.getFormat());
        if (comparison != 0) {
            return comparison;
        }

        return 0;
    }

    public int compare(Song o1, Song o2) {
        return staticCompare(o1, o2);
    }
}
