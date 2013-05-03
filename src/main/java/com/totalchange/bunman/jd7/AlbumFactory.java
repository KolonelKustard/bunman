package com.totalchange.bunman.jd7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.totalchange.bunman.cddb.CddbQuerier;
import com.totalchange.bunman.cddb.CddbResult;

final class AlbumFactory {
    private static final String CDDB_DATA_CATEGORY = "data";
    private static final String SPLITTER_CDDB = "/";
    private static final String SPLITTER_DIRNAME = "  ";

    private static final Logger logger = LoggerFactory
            .getLogger(AlbumFactory.class);

    private Queue<IdnFileAlbum> queue = new LinkedList<IdnFileAlbum>();
    private Queue<String> problems = new LinkedList<String>();
    private IdnFileAlbumCache cache;
    private CddbQuerier querier;

    @Inject
    public AlbumFactory(IdnFileAlbumCache cache, CddbQuerier querier) {
        this.cache = cache;
        this.querier = querier;
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

    private void addItemToQueueAndCacheIt(File file, String id, IdnFileAlbum idnf) {
        synchronized (queue) {
            queue.offer(idnf);
        }
        cache.putFileIntoCache(id, file.getParentFile().getName(), idnf);
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

    private void processQueryResults(File file, String id,
            List<CddbResult> results) {
        if (logger.isTraceEnabled()) {
            logger.trace("Got CDDB results for file " + file + ", id " + id
                    + ": " + results);
        }

        if (results.size() <= 0) {
            logger.trace("No results - need to report as a problem");
            synchronized (problems) {
                problems.offer("Failed to find any CDDB info for id " + id
                        + " in directory " + file.getParent());
            }
            return;
        }

        if (results.size() == 1) {
            logger.trace("Only one result, adding it to the queue");
            addItemToQueueAndCacheIt(file, id,
                    new IdnFileAlbum(file, results.get(0)));
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
            addItemToQueueAndCacheIt(file, id,
                    new IdnFileAlbum(file, matches.get(0)));
            return;
        }

        // Bums
        // TODO: sort out a way of flagging multiple possibilities to the user
        synchronized (problems) {
            problems.offer("Couldn't find any suitable CDDB info for id " + id
                    + " in directory " + file.getParent()
                    + " out of possible matches " + results);
        }
    }

    void processIdnFile(final File file) throws IOException {
        logger.trace("Processing idn file {}", file);

        final String id = readIdValue(file);
        if (id == null) {
            throw new NullPointerException(file
                    + " does not contain an ID value");
        }

        logger.trace("Looking up from cache based on id {}", id);
        IdnFileAlbum idnf = cache.getFileFromCache(id, file.getParentFile()
                .getName());
        if (idnf != null) {
            logger.trace("Got result {} from cache", idnf);
            idnf.setIdnFile(file);
            synchronized (queue) {
                queue.offer(idnf);
            }
        }

        logger.trace("Querying for CDDB results for id {}", id);
        querier.query(id, new CddbQuerier.Listener() {
            public void response(List<CddbResult> results) {
                processQueryResults(file, id, results);
            }

            public void error(IOException exception) {
                synchronized (problems) {
                    problems.add(exception.getMessage());
                }
            }
        });
    }

    IdnFileAlbum getNextAlbum() {
        logger.trace("Polling for next album");
        synchronized (queue) {
            return queue.poll();
        }
    }

    String getNextProblem() {
        logger.trace("Polling for next problem");
        synchronized (problems) {
            return problems.poll();
        }
    }

    void waitUntilFinished() {
        try {
            logger.trace("Telling querier to shut down");
            querier.close();
            logger.trace("Querier has shut down");
        } catch (IOException ioEx) {
            logger.warn("Querier failed to shut down nicely", ioEx);
            problems.add(ioEx.getMessage());
        }
    }
}
