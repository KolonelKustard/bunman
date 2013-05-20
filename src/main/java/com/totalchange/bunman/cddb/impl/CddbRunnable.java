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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool.ObjectPool;

import com.samskivert.net.cddb.CDDB;
import com.samskivert.net.cddb.CDDBException;
import com.samskivert.net.cddb.CDDB.Detail;
import com.totalchange.bunman.cddb.CddbQuerier;
import com.totalchange.bunman.cddb.CddbQuerier.Listener;
import com.totalchange.bunman.cddb.CddbResult;

final class CddbRunnable implements Runnable {
    private ObjectPool<CDDB> cddbPool;
    private String id;
    private CddbQuerier.Listener listener;

    CddbRunnable(ObjectPool<CDDB> cddbPool, String id, Listener listener) {
        this.cddbPool = cddbPool;
        this.id = id;
        this.listener = listener;
    }

    public void run() {
        List<CddbResult> results = new ArrayList<CddbResult>();
        try {
            CDDB cddb = cddbPool.borrowObject();
            try {
                String[] categories = cddb.lscat();
                for (String category : categories) {
                    try {
                        Detail detail = cddb.read(category, id);
                        results.add(new CddbResultImpl(detail));
                    } catch (CDDBException cddbEx) {
                        // 401 indicates not found which is ok - we'll go onto
                        // the next category. Anything else and we'll fail.
                        if (cddbEx.getCode() != 401) {
                            throw cddbEx;
                        }
                    }
                }

                listener.response(results);
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
