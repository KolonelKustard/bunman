package com.totalchange.bunman.jd7;

import java.io.File;
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
        // TODO Auto-generated method stub
        return null;
    }

    public Catalogue createCatalogue(File root) {
        return new Jd7Catalogue(cache, querier, root);
    }
}
