package com.totalchange.bunman.cddb.impl;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.samskivert.net.cddb.CDDB;
import com.totalchange.bunman.cddb.CddbQuerier;

public class CddbQuerierImpl implements CddbQuerier {
    private static final long TIME_BETWEEN_EVICTION_RUNS = 1000;

    private ObjectPool<CDDB> cddbPool;
    private Executor executor;

    public CddbQuerierImpl(String hostname, int port, int maxConnections,
            long idleTimeout) {
        GenericObjectPool<CDDB> pool = new GenericObjectPool<CDDB>(
                new CddbPoolableObjectFactory(hostname, port));
        pool.setMaxActive(maxConnections);
        pool.setMinEvictableIdleTimeMillis(idleTimeout);
        pool.setTimeBetweenEvictionRunsMillis(TIME_BETWEEN_EVICTION_RUNS);

        this.cddbPool = pool;
        this.executor = Executors.newFixedThreadPool(maxConnections);
    }

    public void query(String id, Listener listener) {
        // TODO Auto-generated method stub
    }

    public void close() throws IOException {
        // TODO Auto-generated method stub
    }
}
