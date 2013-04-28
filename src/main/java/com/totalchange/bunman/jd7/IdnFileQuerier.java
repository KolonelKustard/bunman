package com.totalchange.bunman.jd7;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class IdnFileQuerier {
    private static final Logger logger = LoggerFactory
            .getLogger(IdnFileQuerier.class);

    private static final int KEEP_ALIVE = 10;

    interface Listener {
        void responses(List<IdnFile> files);

        void failure(IOException ioEx);
    }

    private class Query {
        private String id;
        private String artist;
        private String album;
        private Listener listener;
    }

    private class QueryingThread extends Thread {
        private CddbQuerier querier;

        QueryingThread(ThreadGroup group, String hostname, int port)
                throws IOException {
            super(group, hostname + ":" + port);
            querier = new CddbQuerier(hostname, port);
        }

        @Override
        public void run() {
            try {
                Query query;
                while ((query = queue.poll(KEEP_ALIVE, TimeUnit.SECONDS)) != null) {
                    try {
                        gotResponse(query.listener, querier.queryForAlbum(
                                query.id, query.artist, query.album));
                    } catch (IOException ioEx) {
                        logger.warn("Failed querying CDDB", ioEx);
                        query.listener.failure(ioEx);
                    }
                }
            } catch (InterruptedException intEx) {
                // Just stop...
            } finally {
                try {
                    querier.close();
                } catch (IOException ioEx) {
                    logger.warn("Failed closing querier", ioEx);
                }

                synchronized (threadGroup) {
                    numActiveThreads--;
                }
            }
        }
    }

    private int inProgress = 0;
    private BlockingQueue<Query> queue = new LinkedBlockingQueue<Query>();
    private ThreadGroup threadGroup = new ThreadGroup("CDDB-Queriers");
    private int numActiveThreads = 0;

    private synchronized void gotResponse(Listener listener, List<IdnFile> files) {
        listener.responses(files);
        inProgress--;
    }

    synchronized void queryForFile(String id, String artist, String album,
            Listener listener) {
        inProgress++;
        
        

        Query query = new Query();
        query.id = id;
        query.artist = artist;
        query.album = album;
        query.listener = listener;

        queue.offer(query);
    }

    synchronized boolean isInProgress() {
        return inProgress > 0;
    }
}
