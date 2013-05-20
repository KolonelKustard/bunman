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
package com.totalchange.bunman;

import java.io.IOException;
import java.io.InputStream;

public interface Song extends Comparable<Song> {
    enum Format {
        Mp3("mp3"), Wav("wav");

        private final String extension;

        private Format(String extension) {
            this.extension = extension;
        }

        public String getExtension() {
            return extension;
        }
    }

    Format getFormat();
    String getArtist();
    String getAlbum();
    String getGenre();
    int getYear();
    int getTrack();
    String getTitle();

    InputStream getInputStream() throws IOException;
}
