package com.totalchange.bunman;

import java.io.File;
import java.util.List;

public interface CatalogueFactory {
    public interface Location {
        String getName();
        File getRoot();
    }

    List<Location> findDefaultLocations();
    Catalogue createCatalogue(File root);
}
