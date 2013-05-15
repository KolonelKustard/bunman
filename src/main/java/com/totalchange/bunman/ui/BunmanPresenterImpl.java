package com.totalchange.bunman.ui;

import javax.inject.Inject;

import com.totalchange.bunman.CatalogueFactory;
import com.totalchange.bunman.guice.Jd7;
import com.totalchange.bunman.guice.Library;

public class BunmanPresenterImpl implements BunmanPresenter {
    private CatalogueFactory backupCatalogueFactory;
    private CatalogueFactory libraryCatalogueFactory;
    private BunmanView view;

    @Inject
    public BunmanPresenterImpl(@Jd7 CatalogueFactory backupCatalogueFactory,
            @Library CatalogueFactory libraryCatalogueFactory, BunmanView view) {
        this.backupCatalogueFactory = backupCatalogueFactory;
        this.libraryCatalogueFactory = libraryCatalogueFactory;
        this.view = view;

        this.view.setPresenter(this);
    }

    public void go() {
        view.show();
    }
}
