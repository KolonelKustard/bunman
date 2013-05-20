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
package com.totalchange.bunman.jb7;

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
