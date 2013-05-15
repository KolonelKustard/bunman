package com.totalchange.bunman.ui.impl;

import java.beans.Beans;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

final class Messages {
    private static final String BUNDLE_NAME = "com.totalchange.bunman.ui.impl.messages"; //$NON-NLS-1$
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
