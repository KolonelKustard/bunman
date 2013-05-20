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

import java.io.File;

import com.totalchange.bunman.Song;

public abstract class AbstractSong implements Song, Comparable<Song> {
    public int compareTo(Song o) {
        return SongComparator.staticCompare(this, o);
    }

    protected Format workOutFormat(File file) {
        String filename = file.getName();
        if (filename == null) {
            return null;
        }

        filename = filename.toLowerCase();
        for (Format format : Format.values()) {
            if (filename.endsWith("." + format.getExtension())) {
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
