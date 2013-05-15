package com.totalchange.bunman.ui;

import java.io.File;

public interface BunmanPresenter {
    void go();
    void scanLocations(File backupRoot, File libraryRoot);
}
