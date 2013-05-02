package com.totalchange.bunman.jd7;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import com.totalchange.bunman.Catalogue;
import com.totalchange.bunman.CatalogueSongListener;

public final class Jd7Catalogue implements Catalogue {
    private IdnFileFactory idnFileFactory;
    private File root;

    @Inject
    public Jd7Catalogue(IdnFileFactory idnFileFactory, File root) {
        this.idnFileFactory = idnFileFactory;
        this.root = root;
    }

    private void processAlbumData(CatalogueSongListener listener,
            AlbumData album, File dir, File... ignored) {
        Jd7FileFinder fileFinder = new Jd7FileFinder(dir.listFiles(), ignored);
        for (String track : album.getTracks()) {
            File file = fileFinder.findTrackFile(track);
            if (file != null) {
                listener.yetAnotherSong(new Jd7Song(album, track, file));
            } else {
                // TODO: Internationalise
                listener.skippedSomething("Couldn't find a file for "
                        + "track '" + track + "' from album '"
                        + album.getAlbum() + "' by artist '"
                        + album.getArtist() + "' in directory "
                        + dir.getAbsolutePath());
            }
        }
    }

    private void processIdDir(File dir, File idFile,
            CatalogueSongListener listener) {
        try {
            IdFile idf = new IdFile(idFile);
            processAlbumData(listener, idf, dir, idFile);
        } catch (IOException ioEx) {
            listener.skippedSomething("Couldn''t read ID file " + idFile + ": "
                    + ioEx.getLocalizedMessage());
        }
    }

    private void processIdnFile(File idnFile, CatalogueSongListener listener) {
        try {
            idnFileFactory.processIdnFile(idnFile);
        } catch (IOException ioEx) {
            listener.skippedSomething("Couldn''t read IDN file " + idnFile
                    + ": " + ioEx.getLocalizedMessage());
        }
    }

    private void processIdnResults(CatalogueSongListener listener) {
        IdnFile album;
        while ((album = idnFileFactory.getNextAlbum()) != null) {
            processAlbumData(listener, album, album.getIdnFile()
                    .getParentFile(), album.getIdnFile());
        }

        String problem;
        while ((problem = idnFileFactory.getNextProblem()) != null) {
            listener.skippedSomething(problem);
        }
    }

    private void recurseForIdFiles(File dir, CatalogueSongListener listener) {
        File idFile = new File(dir, "id");
        if (idFile.exists()) {
            processIdDir(dir, idFile, listener);
        } else {
            File idnFile = new File(dir, "idn");
            if (idnFile.exists()) {
                processIdnFile(idnFile, listener);
            }
        }

        processIdnResults(listener);

        for (File subDir : dir.listFiles()) {
            if (subDir.isDirectory()) {
                recurseForIdFiles(subDir, listener);
            }
        }
    }

    public void listAllSongs(CatalogueSongListener listener) {
        recurseForIdFiles(root, listener);

        // Wait for IDN factory to finish any background processing
        idnFileFactory.waitUntilFinished();
        processIdnResults(listener);
    }
}
