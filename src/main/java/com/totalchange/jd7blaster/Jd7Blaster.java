package com.totalchange.jd7blaster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class Jd7Blaster {
    private PrintWriter log;
    private PrintWriter err;

    private File findSongFile(File dir, String title)
            throws FileNotFoundException {
        String songFileName = title.replace("\\", " ");
        songFileName = songFileName.replace("/", " ");
        songFileName = songFileName.replace("?", " ");
        songFileName = songFileName.replace(":", " ");
        songFileName = songFileName.trim();

        String shortener = new String(songFileName);
        while (shortener.length() > 0) {
            File songFile = new File(dir, shortener + ".mp3");
            if (songFile.exists()) {
                return songFile;
            } else {
                shortener = shortener.substring(0, shortener.length() - 1);
            }
        }

        throw new FileNotFoundException("Couldn't find '" + songFileName
                + "' in " + dir.getAbsolutePath());
    }

    private void tagFile(File dir, String artist, String album, String genre,
            String year, String track, int trackNum) throws IOException,
            InvalidAudioFrameException, CannotReadException, TagException,
            ReadOnlyFileException, CannotWriteException {
        try {
            File songFile = findSongFile(dir, track);
            MP3File f = (MP3File) AudioFileIO.read(songFile);

            Tag tag = f.getTagOrCreateAndSetDefault();
            tag.setField(FieldKey.ARTIST, artist);
            tag.setField(FieldKey.ALBUM, album);
            tag.setField(FieldKey.GENRE, genre);
            tag.setField(FieldKey.YEAR, year);
            tag.setField(FieldKey.TITLE, track);
            tag.setField(FieldKey.TRACK, String.valueOf(trackNum));

            f.commit();

            log.println("Set " + songFile + "'s ID3 data to " + artist + ", "
                    + album + ", " + genre + ", " + year + ", " + track + ", "
                    + trackNum);
        } catch (FileNotFoundException fnfEx) {
            err.println(fnfEx.getMessage());
        }
    }

    private void rebuildIdDir(File dir, File idFile) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(idFile));
        try {
            String artistAlbum = in.readLine().trim();
            String year = in.readLine().trim();
            String genre = in.readLine().trim();

            String[] artistAlbumBits = artistAlbum.split(" / ");
            String artist = artistAlbumBits[0];
            String album = artistAlbumBits[1];

            String track;
            int count = 1;
            while ((track = in.readLine()) != null) {
                track = track.trim();

                if (track.length() > 0) {
                    try {
                        tagFile(dir, artist, album, genre, year, track, count);
                    } catch (Throwable th) {
                        throw new IOException(th);
                    }

                    count++;
                }
            }
        } finally {
            in.close();
        }
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

    private void rebuildDir(File dir) throws IOException {
        log.println("Rebuilding " + dir);

        File idFile = new File(dir, "id");
        if (idFile.exists()) {
            rebuildIdDir(dir, idFile);
        } else {
            err.println("'" + dir + "' has has no id file");
            rebuildNonIdDir(dir);
        }
    }

    public void rebuild(File root) throws IOException {
        log = new PrintWriter(new File(root, "jb7blaster.log"));
        err = new PrintWriter(new File(root, "jb7blaster.err"));
        try {
            for (File dir : root.listFiles()) {
                if (dir.isDirectory()) {
                    rebuildDir(dir);
                }
            }
        } finally {
            log.close();
            err.close();
        }
    }

    public static void main(String[] args) {
        File root = new File(".");
        if (args.length > 0) {
            root = new File(args[0]);
        }

        System.out.println("JD7 Blasting " + root.getAbsolutePath());
        Jd7Blaster blaster = new Jd7Blaster();
        try {
            blaster.rebuild(root);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}
