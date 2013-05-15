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
