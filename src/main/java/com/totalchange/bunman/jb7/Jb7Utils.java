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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class Jb7Utils {
    private static final Logger logger = LoggerFactory
            .getLogger(Jb7Utils.class);

    private Jb7Utils() {
        // Not to be instantiated
    }

    static String[] splitArtistAlbumBits(String artistAlbumBits,
            String splitOnMe) {
        String[] split = new String[2];

        if (artistAlbumBits == null || artistAlbumBits.length() <= 0) {
            split[0] = null;
            split[1] = null;
        }

        int splitter = artistAlbumBits.indexOf(splitOnMe);
        if (splitter > -1) {
            split[0] = artistAlbumBits.substring(0, splitter).trim();
            if (splitter < (artistAlbumBits.length() - 1)) {
                split[1] = artistAlbumBits.substring(
                        splitter + splitOnMe.length(),
                        artistAlbumBits.length()).trim();
            } else {
                split[1] = "";
            }
        } else {
            split[0] = artistAlbumBits.trim();
            split[1] = split[0];
        }

        return split;
    }

    static String removeExtension(String fileName) {
        logger.trace("Stripping extension from filename {}", fileName);

        int ext = fileName.lastIndexOf('.');
        if (ext > 0 && (fileName.length() - ext) <= 5) {
            // It's intentionally > 0 as I'm not considering it an extension if
            // the filename has no name part. Also it's not considered an
            // extension if it's more than 4 characters long.
            logger.trace("Removing from index point {} onward", ext);
            return fileName.substring(0, ext);
        } else {
            logger.trace("No extension to strip");
            return fileName;
        }
    }
}
