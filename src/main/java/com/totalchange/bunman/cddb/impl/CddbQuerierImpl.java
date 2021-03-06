/*
 * Copyright 2013 Ralph Jones
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.totalchange.bunman.cddb.impl;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samskivert.net.cddb.CDDB;
import com.totalchange.bunman.cddb.CddbQuerier;

public class CddbQuerierImpl implements CddbQuerier {
    private static final long TIME_BETWEEN_EVICTION_RUNS = 1000;

    private static final Logger logger = LoggerFactory
            .getLogger(CddbQuerierImpl.class);

    private ObjectPool<CDDB> cddbPool;
    private ExecutorService executor;

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
        executor.execute(new CddbRunnable(cddbPool, id, listener));
    }

    public void close() throws IOException {
        executor.shutdown();
        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException intEx) {
            logger.warn("Timed out waiting for threads to finish", intEx);
        }

        try {
            cddbPool.close();
        } catch (Exception ex) {
            logger.warn("A problem occurred closing CDDB connections", ex);
            throw new IOException(ex);
        }
    }
}
