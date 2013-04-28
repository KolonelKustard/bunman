package com.totalchange.bunman.jd7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.inject.Inject;

final class IdnFileFactory {
    private int inProgress = 0;
    private Queue<IdnFile> queue = new LinkedList<IdnFile>();
    private IdnFileCache cache;
    private IdnFileQuerier querier;

    @Inject
    public IdnFileFactory(IdnFileCache cache, IdnFileQuerier querier) {
        this.cache = cache;
        this.querier = querier;
    }

    private String readIdValue(File file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        try {
            String id = in.readLine();

            if (id != null) {
                id = id.trim();
            }

            if (id != null && id.length() <= 0) {
                id = null;
            }

            return id;
        } finally {
            in.close();
        }
    }

    void processIdnFile(File file) throws IOException {
        String id = readIdValue(file);
        if (id == null) {
            throw new NullPointerException(file
                    + " does not contain an ID value");
        }

        IdnFile idnf = cache.getFileFromCache(id, file.getParentFile()
                .getName());
        if (idnf != null) {
            synchronized (queue) {
                queue.offer(idnf);
            }
        }

    }

    IdnFile getNext() {
        synchronized (queue) {
            return queue.poll();
        }
    }

    void waitUntilFinished() {
        while (inProgress > 0) {
            try {
                synchronized (queue) {
                    queue.wait(1000);
                }
            } catch (InterruptedException iEx) {
                throw new RuntimeException(iEx);
            }
        }
    }
}
