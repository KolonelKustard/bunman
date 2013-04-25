package com.totalchange.bunman.jd7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import com.totalchange.bunman.Catalogue;
import com.totalchange.bunman.CatalogueSongListener;

public final class Jd7Catalogue implements Catalogue {
    private void tagFile(File dir, String artist, String album, String genre,
            String year, String track, int trackNum) throws IOException,
            InvalidAudioFrameException, CannotReadException, TagException,
            ReadOnlyFileException, CannotWriteException {
        try {
            File songFile = null;
            MP3File f = (MP3File) AudioFileIO.read(songFile);

            Tag tag = f.getTagOrCreateAndSetDefault();
            tag.setField(FieldKey.ARTIST, artist);
            tag.setField(FieldKey.ALBUM, album);
            tag.setField(FieldKey.GENRE, genre);
            tag.setField(FieldKey.YEAR, year);
            tag.setField(FieldKey.TITLE, track);
            tag.setField(FieldKey.TRACK, String.valueOf(trackNum));

            f.commit();
        } catch (FileNotFoundException fnfEx) {
        }
    }

    private void processIdDir(File dir, File idFile,
            CatalogueSongListener listener) {
        try {
            IdFile idf = new IdFile(idFile);
            Jd7FileFinder fileFinder = new Jd7FileFinder(dir.listFiles(),
                    idFile);
            for (String track : idf.getTracks()) {
                File file = fileFinder.findTrackFile(track);
                if (file != null) {
                    listener.yetAnotherSong(new Jd7Song(idf.getArtist(), idf
                            .getAlbum(), track, idf.getGenre(), idf.getYear(),
                            file));
                } else {
                    listener.skippedSomething("Couldn't find a file for "
                            + "track '" + track + "' from album '"
                            + idf.getAlbum() + "' by artist '"
                            + idf.getArtist() + "' in directory "
                            + dir.getCanonicalPath());
                }
            }
        } catch (IOException ioEx) {
            listener.skippedSomething("Couldn''t read ID file " + idFile + ": "
                    + ioEx.getLocalizedMessage());
        }
    }

    private void processIdnDir(File dir, File idFile,
            CatalogueSongListener listener) {
        // TODO: Write me
    }

    private void rebuildNonIdDir(File dir) throws IOException {
        String[] artistAndAlbumBits = dir.getName().split("  ");
        String artist = artistAndAlbumBits[0].trim();

        String album = "";
        if (artistAndAlbumBits.length > 1) {
            album = artistAndAlbumBits[1].trim();
        }

        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(".mp3")) {
                String title = file.getName().substring(0,
                        file.getName().length() - 4);

                try {
                    tagFile(dir, artist, album, "", "", title, 0);
                } catch (Throwable th) {
                    throw new IOException(th);
                }
            }
        }
    }

    private File root;

    private void recurseForIdFiles(File dir, CatalogueSongListener listener) {
        File idFile = new File(dir, "id");
        if (idFile.exists()) {
            processIdDir(dir, idFile, listener);
        } else {
            File idnFile = new File(dir, "idn");
            if (idnFile.exists()) {
                processIdnDir(dir, idnFile, listener);
            }
        }

        for (File subDir : dir.listFiles()) {
            if (subDir.isDirectory()) {
                recurseForIdFiles(subDir, listener);
            }
        }
    }

    public void listAllSongs(CatalogueSongListener listener) {
        recurseForIdFiles(root, listener);
    }
}
