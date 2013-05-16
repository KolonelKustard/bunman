package com.totalchange.bunman.ui;

import java.util.List;

import com.totalchange.bunman.CatalogueFactory;
import com.totalchange.bunman.Song;

public interface BunmanView {
    void setPresenter(BunmanPresenter presenter);

    void setVisible(boolean visible);

    void showLocationChooser(
            List<CatalogueFactory.Location> defaultBackupLocations,
            List<CatalogueFactory.Location> defaultLibraryLocations);

    void showInProgress();
    void hideInProgress();
    void setInProgress(int percentComplete, String msg);

    void addBackupSong(Song song);
    void addLibrarySong(Song song);
    void warn(String msg);

    void showListToSync(List<Song> toSync);
}
