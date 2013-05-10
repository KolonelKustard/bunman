package com.totalchange.bunman.jd7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.totalchange.bunman.Catalogue;
import com.totalchange.bunman.CatalogueSongListener;
import com.totalchange.bunman.cddb.CddbQuerier;
import com.totalchange.bunman.cddb.CddbResult;

public final class Jd7Catalogue implements Catalogue {
    private static final String CDDB_DATA_CATEGORY = "data";
    private static final String SPLITTER_CDDB = "/";
    private static final String SPLITTER_DIRNAME = "  ";

    private static final Logger logger = LoggerFactory
            .getLogger(Jd7Catalogue.class);

    private IdnFileAlbumCache cache;
    private CddbQuerier querier;
    private File root;

    @Inject
    public Jd7Catalogue(IdnFileAlbumCache cache, CddbQuerier querier, File root) {
        this.cache = cache;
        this.querier = querier;
        this.root = root;
    }

    private void processAlbumData(CatalogueSongListener listener, Album album,
            File dir, File... ignored) {
        FileFinder fileFinder = new FileFinder(dir.listFiles(), ignored);
        for (int num = 0; num < album.getTracks().size(); num++) {
            String track = album.getTracks().get(num);

            File file = fileFinder.findTrackFile(track);
            if (file != null) {
                listener.yetAnotherSong(new Jd7Song(album, num + 1, track, file));
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
            IdFileAlbum idf = new IdFileAlbum(idFile);
            processAlbumData(listener, idf, dir, idFile);
        } catch (IOException ioEx) {
            listener.skippedSomething("Couldn''t read ID file " + idFile + ": "
                    + ioEx.getLocalizedMessage());
        }
    }

    private String readIdValue(File file) throws IOException {
        logger.trace("Reading id string from file {}", file);

        BufferedReader in = new BufferedReader(new FileReader(file));
        try {
            String id = in.readLine();
            logger.trace("Raw id value is {}", id);

            if (id != null) {
                id = id.trim();
            }

            if (id != null && id.length() <= 0) {
                id = null;
            }

            logger.trace("Parsed id value is {}", id);
            return id;
        } finally {
            in.close();
        }
    }

    private void addItemToQueueAndCacheIt(CatalogueSongListener listener,
            File file, String id, IdnFileAlbum idnf) {
        cache.putFileIntoCache(id, file.getParentFile().getName(), idnf);
        processAlbumData(listener, idnf, file.getParentFile(), file);
    }

    private boolean equalsIgnoreCase(String[] arr1, String[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }

        for (int num = 0; num < arr1.length; num++) {
            if (!arr1[num].equalsIgnoreCase(arr2[num])) {
                return false;
            }
        }

        return true;
    }

    private void processQueryResults(CatalogueSongListener listener, File file,
            String id, List<CddbResult> results) {
        if (logger.isTraceEnabled()) {
            logger.trace("Got CDDB results for file " + file + ", id " + id
                    + ": " + results);
        }

        if (results.size() <= 0) {
            logger.trace("No results - need to report as a problem");
            // TODO: Internationalise
            listener.skippedSomething("Failed to find any CDDB info for id "
                    + id + " in directory " + file.getParent());
            return;
        }

        if (results.size() == 1) {
            logger.trace("Only one result, adding it to the queue");
            addItemToQueueAndCacheIt(listener, file, id, new IdnFileAlbum(file,
                    results.get(0)));
            return;
        }

        // Got more than 1 result - remove any that don't match on artist and
        // title (and skip any in the "data" category).
        String[] dirSplit = Jd7Utils.splitArtistAlbumBits(file.getParentFile()
                .getName(), SPLITTER_DIRNAME);
        List<CddbResult> matches = new ArrayList<CddbResult>();
        for (CddbResult cddb : results) {
            String[] resSplit = Jd7Utils.splitArtistAlbumBits(cddb.getTitle(),
                    SPLITTER_CDDB);
            if (!cddb.getCategory().equalsIgnoreCase(CDDB_DATA_CATEGORY)
                    && equalsIgnoreCase(dirSplit, resSplit)) {
                matches.add(cddb);
            }
        }

        if (matches.size() >= 1) {
            logger.trace("Found a match for {}: {}", (Object[]) dirSplit,
                    matches);
            addItemToQueueAndCacheIt(listener, file, id, new IdnFileAlbum(file,
                    matches.get(0)));
            return;
        }

        // Bums
        // TODO: sort out a way of flagging multiple possibilities to the user
        listener.skippedSomething("Couldn't find any suitable CDDB info "
                + "for id " + id + " in directory " + file.getParent()
                + " out of possible matches " + results);
    }

    private void processIdnFile(final File file,
            final CatalogueSongListener listener) {
        logger.trace("Processing idn file {}", file);

        final String id;
        try {
            id = readIdValue(file);
        } catch (IOException ioEx) {
            // TODO: Internationalise
            listener.skippedSomething("Failed to read an ID value from " + file
                    + " with error " + ioEx.getMessage());
            return;
        }

        if (id == null) {
            // TODO: Internationalise
            listener.skippedSomething(file + " does not contain an ID value");
            return;
        }

        logger.trace("Looking up from cache based on id {}", id);
        IdnFileAlbum idnf = cache.getFileFromCache(id, file.getParentFile()
                .getName());
        if (idnf != null) {
            logger.trace("Got result {} from cache", idnf);
            idnf.setIdnFile(file);
            processAlbumData(listener, idnf, file.getParentFile(), file);
        }

        logger.trace("Querying for CDDB results for id {}", id);
        querier.query(id, new CddbQuerier.Listener() {
            public void response(List<CddbResult> results) {
                processQueryResults(listener, file, id, results);
            }

            public void error(IOException exception) {
                listener.skippedSomething(exception.getMessage());
            }
        });
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

        for (File subDir : dir.listFiles()) {
            if (subDir.isDirectory()) {
                recurseForIdFiles(subDir, listener);
            }
        }
    }

    public void listAllSongs(CatalogueSongListener listener) {
        recurseForIdFiles(root, listener);

        // Wait for IDN factory to finish any background processing - shouldn't
        // really throw an error...
        try {
            querier.close();
        } catch (IOException ioEx) {
            listener.skippedSomething(ioEx.getMessage());
            logger.warn("A problem occurred waiting for the CDDB querier to "
                    + "close", ioEx);
        }
    }
}
