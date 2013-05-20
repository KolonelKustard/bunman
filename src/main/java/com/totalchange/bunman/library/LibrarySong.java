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
package com.totalchange.bunman.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import com.totalchange.bunman.util.AbstractSong;

final class LibrarySong extends AbstractSong {
    private File file;
    private Format format;
    private String artist;
    private String album;
    private String genre;
    private int year;
    private int track;
    private String title;

    LibrarySong(File file, Tag tag) {
        this.file = file;

        this.format = workOutFormat(file);
        this.artist = getTagString(tag, FieldKey.ARTIST);
        this.album = getTagString(tag, FieldKey.ALBUM);
        this.genre = getTagString(tag, FieldKey.GENRE);
        this.year = getTagInt(tag, FieldKey.YEAR);
        this.track = getTagInt(tag, FieldKey.TRACK);
        this.title = getTagString(tag, FieldKey.TITLE);
    }

    private String getTagString(Tag tag, FieldKey key) {
        String str = tag.getFirst(key);
        if (str == null) {
            return null;
        } else {
            str = str.trim();
            if (str.length() <= 0) {
                return null;
            } else {
                return str;
            }
        }
    }

    private int getTagInt(Tag tag, FieldKey key) {
        String str = getTagString(tag, key);
        if (str == null) {
            return -1;
        } else {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException nfEx) {
                return -1;
            }
        }
    }

    public Format getFormat() {
        return format;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public int getTrack() {
        return track;
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public String toString() {
        return "LibrarySong [" + super.toString() + ", file=" + file + "]";
    }
}
