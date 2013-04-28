package com.totalchange.bunman.cddb.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool.ObjectPool;

import com.samskivert.net.cddb.CDDB;
import com.totalchange.bunman.cddb.CddbQuerier;
import com.totalchange.bunman.cddb.CddbQuerier.Listener;
import com.totalchange.bunman.cddb.CddbResult;

final class CddbRunnable implements Runnable {
    private ObjectPool<CDDB> cddbPool;
    private String id;
    private CddbQuerier.Listener listener;

    CddbRunnable(ObjectPool<CDDB> cddbPool, String id, Listener listener) {
        super();
        this.cddbPool = cddbPool;
        this.id = id;
        this.listener = listener;
    }

    public void run() {
        List<CddbResult> results = new ArrayList<CddbResult>();
        try {
            CDDB cddb = cddbPool.borrowObject();
            try {
                cddb.query(id, null, -1);
                CddbResultImpl result = new CddbResultImpl();
                results.add(result);
            } catch (IOException ioEx) {
                cddbPool.invalidateObject(cddb);
                throw ioEx;
            } finally {
                cddbPool.returnObject(cddb);
            }
        } catch (IOException ioEx) {
            listener.error(ioEx);
        } catch (Throwable th) {
            listener.error(new IOException(th));
        }
    }
}
