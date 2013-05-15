package com.totalchange.bunman.jd7;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.totalchange.bunman.Catalogue;
import com.totalchange.bunman.CatalogueFactory;
import com.totalchange.bunman.cddb.CddbQuerier;

public class Jd7CatalogueFactory implements CatalogueFactory {
    private IdnFileAlbumCache cache;
    private CddbQuerier querier;

    @Inject
    public Jd7CatalogueFactory(IdnFileAlbumCache cache, CddbQuerier querier) {
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
                        return "Brennan JD7 Backup";
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
        return new Jd7Catalogue(cache, querier, root);
    }
}
