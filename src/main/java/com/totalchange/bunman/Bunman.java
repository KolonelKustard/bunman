package com.totalchange.bunman;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.totalchange.bunman.guice.BunmanModule;
import com.totalchange.bunman.ui.BunmanPresenter;

/**
 * <p>
 * The <code>main</code> class for starting up the Bunman application.
 * </p>
 * 
 * @author Ralph Jones
 */
public final class Bunman {
    private static final Logger logger = LoggerFactory.getLogger(Bunman.class);

    private Bunman() {
        // To prevent instantiation of utility class
    }

    /**
     * <p>
     * Starts up the Bunman application. No arguments are expected.
     * </p>
     * 
     * @param args
     *            currently ignored
     */
    public static void main(String[] args) {
        logger.info("Starting up");

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());

                    Injector injector = Guice
                            .createInjector(new BunmanModule());
                    BunmanPresenter presenter = injector
                            .getInstance(BunmanPresenter.class);
                    presenter.go();
                } catch (Throwable th) {
                    logger.error("Error starting up", th);
                    th.printStackTrace();
                }
            }
        });
    }
}
