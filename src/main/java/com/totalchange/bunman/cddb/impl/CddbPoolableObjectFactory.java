package com.totalchange.bunman.cddb.impl;

import org.apache.commons.pool.BasePoolableObjectFactory;

final class CddbPoolableObjectFactory extends
        BasePoolableObjectFactory<CddbWithLscat> {
    private String hostname;
    private int port;

    CddbPoolableObjectFactory(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public CddbWithLscat makeObject() throws Exception {
        CddbWithLscat cddb = new CddbWithLscat();
        cddb.connect(hostname, port);
        return cddb;
    }

    @Override
    public void destroyObject(CddbWithLscat cddb) throws Exception {
        cddb.close();
    }
}
