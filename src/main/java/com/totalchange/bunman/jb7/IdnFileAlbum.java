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
package com.totalchange.bunman.jb7;

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

        String[] split = Jb7Utils.splitArtistAlbumBits(cddb.getTitle(),
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
