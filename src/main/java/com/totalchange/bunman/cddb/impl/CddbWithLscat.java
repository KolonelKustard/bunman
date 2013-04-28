package com.totalchange.bunman.cddb.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.samskivert.net.cddb.CDDB;
import com.samskivert.net.cddb.CDDBException;
import com.samskivert.net.cddb.CDDBProtocol;

/**
 * <p>
 * Extends the {@link CDDB} class created by Sam Skivert to add the
 * <code>cddb lscat</code> query.
 * </p>
 * 
 * @author kolonel
 */
final class CddbWithLscat extends CDDB {
    /**
     * <p>
     * Fetches the list of categories discs can be categorised by.
     * </p>
     * 
     * @return the categories
     * @throws IOException
     *             if a problem occurs chatting to the server
     * @throws CDDBException
     *             if the server responds with an error
     */
    public List<String> lscat() throws IOException, CDDBException {
        // Sanity check
        if (_sock == null) {
            throw new CDDBException(500, "Not connected");
        }

        // Make the request
        Response rsp = request("cddb lscat");

        // Anything other than an OK response earns an exception
        if (rsp.code != 210) {
            throw new CDDBException(rsp.code, rsp.message);
        }

        List<String> categories = new ArrayList<String>();
        String input;
        while (!(input = _in.readLine()).equals(CDDBProtocol.TERMINATOR)) {
            categories.add(input);
        }

        return categories;
    }
}
