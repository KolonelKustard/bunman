package com.totalchange.bunman.cddb;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public interface CddbQuerier extends Closeable {
    public interface Listener {
        void response(List<CddbResult> results);
        void error(IOException exception);
    }

    void query(String id, Listener listener);
}
