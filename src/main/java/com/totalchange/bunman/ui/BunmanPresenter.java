package com.totalchange.bunman.ui;

import java.io.File;
import java.util.List;

import com.totalchange.bunman.Song;

public interface BunmanPresenter {
    void go();
    void scanLocations(File backupRoot, File libraryRoot);
    void copyFromBackupToLibrary(List<Song> songs);
    void cancelInProgress();
}
