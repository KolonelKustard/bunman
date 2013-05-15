package com.totalchange.bunman.ui;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import com.totalchange.bunman.Catalogue;
import com.totalchange.bunman.CatalogueFactory;
import com.totalchange.bunman.CatalogueSongListener;
import com.totalchange.bunman.Song;
import com.totalchange.bunman.guice.Jd7;
import com.totalchange.bunman.guice.Library;

public class BunmanPresenterImpl implements BunmanPresenter {
    private CatalogueFactory backupCatalogueFactory;
    private CatalogueFactory libraryCatalogueFactory;
    private BunmanView view;
    private Executor executor;

    private int scansInProgress = 0;
    private List<Song> backupSongs = null;
    private List<Song> librarySongs = null;

    @Inject
    public BunmanPresenterImpl(@Jd7 CatalogueFactory backupCatalogueFactory,
            @Library CatalogueFactory libraryCatalogueFactory, BunmanView view) {
        this.backupCatalogueFactory = backupCatalogueFactory;
        this.libraryCatalogueFactory = libraryCatalogueFactory;
        this.view = view;
        this.executor = Executors.newCachedThreadPool();

        this.view.setPresenter(this);
    }

    private synchronized void scanStarted() {
        scansInProgress++;
        view.showInProgress();
    }

    private synchronized void scanComplete() {
        scansInProgress--;
        if (scansInProgress <= 0) {
            view.hideInProgress();
        }
    }

    private synchronized void addBackupSong(Song song) {
        backupSongs.add(song);
        view.addBackupSong(song);
    }

    private synchronized void backupWarn(String msg) {
        view.warn(msg);
    }

    private synchronized void addLibrarySong(Song song) {
        librarySongs.add(song);
        view.addLibrarySong(song);
    }

    private synchronized void libraryWarn(String msg) {
        view.warn(msg);
    }

    public void go() {
        view.show();
        view.showLocationChooser(backupCatalogueFactory.findDefaultLocations(),
                libraryCatalogueFactory.findDefaultLocations());
    }

    public void scanLocations(File backupRoot, File libraryRoot) {
        final Catalogue backupCatalogue = backupCatalogueFactory
                .createCatalogue(backupRoot);
        final Catalogue libraryCatalogue = libraryCatalogueFactory
                .createCatalogue(libraryRoot);

        executor.execute(new Runnable() {
            public void run() {
                scanStarted();
                backupCatalogue.listAllSongs(new CatalogueSongListener() {
                    public void yetAnotherSong(Song song) {
                        addBackupSong(song);
                    }

                    public void warn(String msg) {
                        backupWarn(msg);
                    }
                });
                scanComplete();
            }
        });

        executor.execute(new Runnable() {
            public void run() {
                scanStarted();
                libraryCatalogue.listAllSongs(new CatalogueSongListener() {
                    public void yetAnotherSong(Song song) {
                        addLibrarySong(song);
                    }

                    public void warn(String msg) {
                        libraryWarn(msg);
                    }
                });
                scanComplete();
            }
        });
    }
}
