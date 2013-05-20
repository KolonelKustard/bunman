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
import java.util.ArrayList;
import java.util.List;

final class NoFileAlbum implements Album {
    private static final String SPLITTER_DIRNAME = "  ";

    private String artist;
    private String album;
    private List<String> tracks;

    NoFileAlbum(File dir) {
        String[] split = Jb7Utils.splitArtistAlbumBits(dir.getName(),
                SPLITTER_DIRNAME);
        artist = split[0];
        album = split[1];

        tracks = new ArrayList<String>();
        for (File file : dir.listFiles()) {
            String lower = file.getName().toLowerCase();
            if (lower.endsWith(".mp3") || lower.endsWith(".wav")) {
                tracks.add(Jb7Utils.removeExtension(file.getName()));
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
