package com.totalchange.bunman.ui;

import java.util.List;

import com.totalchange.bunman.CatalogueFactory;
import com.totalchange.bunman.Song;

public interface BunmanView {
    void setPresenter(BunmanPresenter presenter);

    void show();

    void showLocationChooser(
            List<CatalogueFactory.Location> defaultBackupLocations,
            List<CatalogueFactory.Location> defaultLibraryLocations);

    void addBackupSong(Song song);
    void addLibrarySong(Song song);
}
