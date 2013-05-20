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
package com.totalchange.bunman.jb7;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.totalchange.bunman.Catalogue;
import com.totalchange.bunman.CatalogueFactory;
import com.totalchange.bunman.cddb.CddbQuerier;

public class Jb7CatalogueFactory implements CatalogueFactory {
    private IdnFileAlbumCache cache;
    private CddbQuerier querier;

    @Inject
    public Jb7CatalogueFactory(IdnFileAlbumCache cache, CddbQuerier querier) {
        this.cache = cache;
        this.querier = querier;
    }

    public List<Location> findDefaultLocations() {
        List<Location> locations = new ArrayList<Location>();

        File[] roots = File.listRoots();
        for (File root : roots) {
            File dir = new File(root, "hardfi");
            dir = new File(dir, "music");

            if (dir.exists() && dir.isDirectory()) {
                final File backupDir = new File(dir.getAbsolutePath());
                locations.add(new Location() {
                    public String getName() {
                        return "Brennan JB7 Backup";
                    }

                    public File getRoot() {
                        return backupDir;
                    }
                });
            }
        }

        return locations;
    }

    public Catalogue createCatalogue(File root) {
        return new Jb7Catalogue(cache, querier, root);
    }
}
