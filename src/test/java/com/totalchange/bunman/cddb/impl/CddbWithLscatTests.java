package com.totalchange.bunman.cddb.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.samskivert.net.cddb.CDDBException;
import com.totalchange.bunman.cddb.impl.CddbWithLscat;

public class CddbWithLscatTests {
    @Test
    public void testLscat() throws IOException, CDDBException {
        CddbWithLscat cddb = new CddbWithLscat();
        cddb.connect("freedb.freedb.org", 8880);
        try {
            List<String> expected = new ArrayList<String>();
            expected.add("data");
            expected.add("folk");
            expected.add("jazz");
            expected.add("misc");
            expected.add("rock");
            expected.add("country");
            expected.add("blues");
            expected.add("newage");
            expected.add("reggae");
            expected.add("classical");
            expected.add("soundtrack");

            assertEquals(expected, cddb.lscat());
        } finally {
            cddb.close();
        }
    }
}
