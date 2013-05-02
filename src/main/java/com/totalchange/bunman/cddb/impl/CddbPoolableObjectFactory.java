package com.totalchange.bunman.cddb.impl;

import org.apache.commons.pool.BasePoolableObjectFactory;

import com.samskivert.net.cddb.CDDB;

final class CddbPoolableObjectFactory extends BasePoolableObjectFactory<CDDB> {
    private static final String DEFAULT_CLIENT_NAME = "Bunman";
    private static final String DEFAULT_CLIENT_VERSION = "1.0";

    private String hostname;
    private int port;
    private String clientName;
    private String clientVersion;

    CddbPoolableObjectFactory(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;

        this.clientName = this.getClass().getPackage().getImplementationTitle();
        if (this.clientName == null || this.clientName.length() <= 0) {
            this.clientName = DEFAULT_CLIENT_NAME;
        }

        this.clientVersion = this.getClass().getPackage()
                .getImplementationVersion();
        if (this.clientVersion == null || this.clientVersion.length() <= 0) {
            this.clientVersion = DEFAULT_CLIENT_VERSION;
        }
    }

    @Override
    public CDDB makeObject() throws Exception {
        CDDB cddb = new CDDB();
        cddb.connect(hostname, port, clientName, clientVersion);
        return cddb;
    }

    @Override
    public void destroyObject(CDDB cddb) throws Exception {
        cddb.close();
    }
}
