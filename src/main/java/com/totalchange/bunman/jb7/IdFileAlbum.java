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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class IdFileAlbum implements Album {
    private static final String ARTIST_ALBUM_SPLITTER = "/";

    private String artist;
    private String album;
    private String genre;
    private int year;
    private List<String> tracks;

    IdFileAlbum(File idFile) throws IOException {
        readIdFile(idFile);
    }

    private void splitArtistAlbumBits(String artistAlbumBits) {
        String[] split = Jb7Utils.splitArtistAlbumBits(artistAlbumBits,
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
