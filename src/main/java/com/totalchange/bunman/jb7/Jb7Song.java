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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.totalchange.bunman.util.AbstractSong;

final class Jb7Song extends AbstractSong {
    private Format format;
    private Album albumData;
    private int track;
    private String title;
    private File file;

    Jb7Song(Album albumData, int track, String title, File file) {
        this.format = workOutFormat(file);
        this.albumData = albumData;
        this.track = track;
        this.title = title;
        this.file = file;
    }

    public Format getFormat() {
        return format;
    }

    public String getArtist() {
        return albumData.getArtist();
    }

    public String getAlbum() {
        return albumData.getAlbum();
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return albumData.getGenre();
    }

    public int getYear() {
        return albumData.getYear();
    }

    public int getTrack() {
        return track;
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public String toString() {
        return "Jb7Song [" + super.toString() + ", file=" + file + "]";
    }
}
