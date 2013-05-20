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

import java.util.List;

import com.totalchange.bunman.CatalogueFactory;
import com.totalchange.bunman.Song;

public interface BunmanView {
    void setPresenter(BunmanPresenter presenter);

    void setVisible(boolean visible);

    void showLocationChooser(
            List<CatalogueFactory.Location> defaultBackupLocations,
            List<CatalogueFactory.Location> defaultLibraryLocations);

    void showInProgress();
    void hideInProgress();
    void setInProgress(int percentComplete, String msg);

    void addBackupSong(Song song);
    void addLibrarySong(Song song);
    void warn(String msg);

    void showListToSync(List<Song> toSync);
}
