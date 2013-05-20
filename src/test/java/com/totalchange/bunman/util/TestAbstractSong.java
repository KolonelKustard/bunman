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

import java.io.IOException;
import java.io.InputStream;

class TestAbstractSong extends AbstractSong {
    Format format = Format.Mp3;
    String artist = "Test artist";
    String album = "Test album";
    String genre = "Test genre";
    int year = 2000;
    int track = -1;
    String title = "Test title";
    InputStream inputStream = null;

    @Override
    public Format getFormat() {
        return format;
    }

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public String getAlbum() {
        return album;
    }

    @Override
    public String getGenre() {
        return genre;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getTrack() {
        return track;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }
}