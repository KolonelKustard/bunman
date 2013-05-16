package com.totalchange.bunman.ui.swing;

import java.beans.Beans;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

final class Messages {
    private static final String BUNDLE_NAME = Messages.class.getName();
    private static final ResourceBundle RESOURCE_BUNDLE = loadBundle();

    private Messages() {
        // do not instantiate
    }

    private static ResourceBundle loadBundle() {
        return ResourceBundle.getBundle(BUNDLE_NAME);
    }

    static String getString(String key) {
        try {
            ResourceBundle bundle = Beans.isDesignTime() ? loadBundle()
                    : RESOURCE_BUNDLE;
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "!" + key + "!";
        }
    }
}
