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
package com.totalchange.bunman.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.totalchange.bunman.Catalogue;
import com.totalchange.bunman.CatalogueFactory;
import com.totalchange.bunman.CatalogueSongListener;
import com.totalchange.bunman.Song;
import com.totalchange.bunman.WritableCatalogue;
import com.totalchange.bunman.guice.Jb7;
import com.totalchange.bunman.guice.Library;

public class BunmanPresenterImpl implements BunmanPresenter {
    private static final Logger logger = LoggerFactory
            .getLogger(BunmanPresenterImpl.class);

    private CatalogueFactory backupCatalogueFactory;
    private CatalogueFactory libraryCatalogueFactory;
    private BunmanView view;
    private ExecutorService executor;

    private Catalogue backupCatalogue = null;
    private Catalogue libraryCatalogue = null;

    private int scansInProgress = 0;
    private int percentComplete = 0;
    private Set<Song> backupSongs = null;
    private Set<Song> librarySongs = null;

    @Inject
    public BunmanPresenterImpl(@Jb7 CatalogueFactory backupCatalogueFactory,
            @Library CatalogueFactory libraryCatalogueFactory, BunmanView view) {
        this.backupCatalogueFactory = backupCatalogueFactory;
        this.libraryCatalogueFactory = libraryCatalogueFactory;
        this.view = view;
        this.executor = Executors.newCachedThreadPool();

        this.view.setPresenter(this);
    }

    private int percenter(double soFar, double total) {
        if (total != 0) {
            return (int) ((soFar / total) * 100);
        } else {
            return 0;
        }
    }

    private List<Song> identifyWhatToSync() {
        List<Song> toSync = new ArrayList<Song>();

        view.setInProgress(0, "Comparing backup to library");
        int total = backupSongs.size();
        int counter = 0;
        for (Song song : backupSongs) {
            if (!librarySongs.contains(song)) {
                toSync.add(song);
            }

            counter++;
            view.setInProgress(percenter(counter, total), null);
        }

        Collections.sort(toSync);
        return toSync;
    }

    private synchronized void scanStarted() {
        scansInProgress++;
        view.showInProgress();
    }

    private synchronized void scanComplete() {
        scansInProgress--;
        if (scansInProgress <= 0) {
            List<Song> toSync = identifyWhatToSync();
            view.hideInProgress();
            view.showListToSync(toSync);
        }
    }

    private synchronized void addBackupSong(Song song) {
        backupSongs.add(song);
        view.addBackupSong(song);

        percentComplete++;
        if (percentComplete > 100) {
            percentComplete = 0;
        }
        view.setInProgress(percentComplete, "Added backup song: " + song);
    }

    private synchronized void backupWarn(String msg) {
        view.warn(msg);
    }

    private synchronized void addLibrarySong(Song song) {
        librarySongs.add(song);
        view.addLibrarySong(song);

        percentComplete++;
        if (percentComplete > 100) {
            percentComplete = 0;
        }
        view.setInProgress(percentComplete, "Added library song: " + song);
    }

    private synchronized void libraryWarn(String msg) {
        view.warn(msg);
    }

    public void go() {
        logger.trace("Showing view");
        view.setVisible(true);

        logger.trace("Looking for default backup and library locations");
        List<CatalogueFactory.Location> backupLocations = backupCatalogueFactory
                .findDefaultLocations();
        List<CatalogueFactory.Location> libraryLocations = libraryCatalogueFactory
                .findDefaultLocations();

        if (backupLocations.size() == 1 && libraryLocations.size() == 1) {
            logger.trace("Got 1 of both backup and library locations - going"
                    + "straight to scan");
            scanLocations(backupLocations.get(0).getRoot(), libraryLocations
                    .get(0).getRoot());
        } else {
            logger.trace("Got {} backup locations and {} library locations, "
                    + "asking view to show location chooser", backupLocations,
                    libraryLocations);
            view.showLocationChooser(backupLocations, libraryLocations);
        }
    }

    public void scanLocations(File backupRoot, File libraryRoot) {
        backupCatalogue = backupCatalogueFactory.createCatalogue(backupRoot);
        libraryCatalogue = libraryCatalogueFactory.createCatalogue(libraryRoot);

        backupSongs = new HashSet<Song>();
        librarySongs = new HashSet<Song>();

        executor.execute(new Runnable() {
            public void run() {
                scanStarted();
                // TODO: Internationalise
                backupWarn("Started scanning backup location");
                backupCatalogue.listAllSongs(new CatalogueSongListener() {
                    public void yetAnotherSong(Song song) {
                        addBackupSong(song);
                    }

                    public void warn(String msg) {
                        backupWarn(msg);
                    }
                });
                // TODO: Internationalise
                backupWarn("Completed scanning backup location");
                scanComplete();
            }
        });

        executor.execute(new Runnable() {
            public void run() {
                scanStarted();
                // TODO: Internationalise
                libraryWarn("Started scanning library location");
                libraryCatalogue.listAllSongs(new CatalogueSongListener() {
                    public void yetAnotherSong(Song song) {
                        addLibrarySong(song);
                    }

                    public void warn(String msg) {
                        libraryWarn(msg);
                    }
                });
                // TODO: Internationalise
                libraryWarn("Completed scanning library location");
                scanComplete();
            }
        });
    }

    public void copyFromBackupToLibrary(final List<Song> songs) {
        executor.execute(new Runnable() {
            public void run() {
                view.showInProgress();
                view.setInProgress(0, "Copying " + songs.size()
                        + " from backup to library");

                int total = backupSongs.size();
                int counter = 0;
                for (Song song : songs) {
                    try {
                        Song copiedSong = ((WritableCatalogue) libraryCatalogue)
                                .copySong(song);

                        addLibrarySong(copiedSong);
                    } catch (IOException ioEx) {
                        libraryWarn(ioEx.getMessage());
                        logger.error("Error copying file from "
                                + backupCatalogue + " to " + libraryCatalogue,
                                ioEx);
                    }

                    counter++;
                    view.setInProgress(percenter(counter, total), null);
                }

                view.hideInProgress();
            }
        });
    }

    public void cancelInProgress() {
        logger.trace("Shutting down executor and creating a new one");
        executor.shutdownNow();
        executor = Executors.newCachedThreadPool();

        logger.trace("Hiding in progress from view");
        view.hideInProgress();

        scansInProgress = 0;
        percentComplete = 0;
    }
}
