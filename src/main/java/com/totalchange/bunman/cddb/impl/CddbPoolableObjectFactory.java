package com.totalchange.bunman.cddb.impl;

import org.apache.commons.pool.BasePoolableObjectFactory;

import com.samskivert.net.cddb.CDDB;

final class CddbPoolableObjectFactory extends
        BasePoolableObjectFactory<CDDB> {
    private String hostname;
    private int port;

    CddbPoolableObjectFactory(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public CDDB makeObject() throws Exception {
        CDDB cddb = new CDDB();
        cddb.connect(hostname, port, "Bunman", "1.0");
        return cddb;
    }

    @Override
    public void destroyObject(CDDB cddb) throws Exception {
        cddb.close();
    }
}
