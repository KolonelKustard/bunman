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
package com.totalchange.bunman.guice;

import java.io.File;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.totalchange.bunman.CatalogueFactory;
import com.totalchange.bunman.cddb.CddbQuerier;
import com.totalchange.bunman.cddb.impl.CddbQuerierImpl;
import com.totalchange.bunman.jb7.IdnFileAlbumCache;
import com.totalchange.bunman.jb7.Jb7CatalogueFactory;
import com.totalchange.bunman.library.LibraryCatalogueFactory;
import com.totalchange.bunman.ui.BunmanPresenter;
import com.totalchange.bunman.ui.BunmanPresenterImpl;
import com.totalchange.bunman.ui.BunmanView;
import com.totalchange.bunman.ui.swing.BunmanFrame;

public class BunmanModule extends AbstractModule {
    private File findAppRoot() {
        String usersDirStr = System.getProperty("user.home");
        if (usersDirStr != null) {
            File usersDir = new File(usersDirStr);
            if (usersDir.exists() && usersDir.isDirectory()) {
                return new File(usersDir, ".bunman");
            }
        }

        return new File(".bunman");
    }

    @Override
    protected void configure() {
        bind(CatalogueFactory.class).annotatedWith(Jb7.class).to(
                Jb7CatalogueFactory.class);
        bind(CatalogueFactory.class).annotatedWith(Library.class).to(
                LibraryCatalogueFactory.class);

        bind(BunmanView.class).to(BunmanFrame.class);
        bind(BunmanPresenter.class).to(BunmanPresenterImpl.class);
    }

    @Provides
    public CddbQuerier provideCddbQuerier() {
        return new CddbQuerierImpl("freedb.freedb.org", 8880, 2, 4000);
    }

    @Provides
    public IdnFileAlbumCache provideIdnFileAlbumCache() {
        File cacheDir = new File(findAppRoot(), "IdnFileAlbumCache");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        try {
            return new IdnFileAlbumCache(cacheDir);
        } catch (Throwable th) {
            // Shouldn't happen and if it does it's fatal...
            throw new RuntimeException(th);
        }
    }
}
