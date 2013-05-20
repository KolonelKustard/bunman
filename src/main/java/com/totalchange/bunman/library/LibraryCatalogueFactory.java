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
package com.totalchange.bunman.library;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.totalchange.bunman.Catalogue;
import com.totalchange.bunman.CatalogueFactory;

public final class LibraryCatalogueFactory implements CatalogueFactory {
    public List<Location> findDefaultLocations() {
        List<Location> locations = new ArrayList<Location>();

        String usersDirStr = System.getProperty("user.home");
        if (usersDirStr != null) {
            File usersDir = new File(usersDirStr);

            // iTunes - Windows XP
            File dir = new File(usersDir, "My Documents");
            dir = new File(dir, "My Music");
            dir = new File(dir, "iTunes");
            dir = new File(dir, "iTunes Media");
            dir = new File(dir, "Music");
            if (dir.exists() && dir.isDirectory()) {
                final File itunesLibrary = new File(dir.getAbsolutePath());
                locations.add(new Location() {
                    public String getName() {
                        return "iTunes Music Library";
                    }

                    public File getRoot() {
                        return itunesLibrary;
                    }
                });
            }

            // iTunes - Windows 7 & 8
            dir = new File(usersDir, "My Music");
            dir = new File(dir, "iTunes");
            dir = new File(dir, "iTunes Media");
            dir = new File(dir, "Music");
            if (dir.exists() && dir.isDirectory()) {
                final File itunesLibrary = new File(dir.getAbsolutePath());
                locations.add(new Location() {
                    public String getName() {
                        return "iTunes Music Library";
                    }

                    public File getRoot() {
                        return itunesLibrary;
                    }
                });
            }

            // iTunes - Mac and Windows Vista
            dir = new File(usersDir, "Music");
            dir = new File(dir, "iTunes");
            dir = new File(dir, "iTunes Media");
            dir = new File(dir, "Music");
            if (dir.exists() && dir.isDirectory()) {
                final File itunesLibrary = new File(dir.getAbsolutePath());
                locations.add(new Location() {
                    public String getName() {
                        return "iTunes Music Library";
                    }

                    public File getRoot() {
                        return itunesLibrary;
                    }
                });
            }
        }

        return locations;
    }

    public Catalogue createCatalogue(File root) {
        return new LibraryCatalogue(root);
    }
}
