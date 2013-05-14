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
