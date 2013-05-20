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
package com.totalchange.bunman.cddb.impl;

import com.samskivert.net.cddb.CDDB.Detail;
import com.totalchange.bunman.cddb.CddbResult;

final class CddbResultImpl implements CddbResult {
    private Detail detail;

    CddbResultImpl(Detail detail) {
        this.detail = detail;
    }

    public String getDiscId() {
        return detail.discid;
    }

    public String getCategory() {
        return detail.category;
    }

    public String getTitle() {
        return detail.title;
    }

    public int getYear() {
        return detail.year;
    }

    public String getGenre() {
        return detail.genre;
    }

    public String[] getTrackNames() {
        return detail.trackNames;
    }

    public String getExtendedData() {
        return detail.extendedData;
    }

    public String[] getExtendedTrackData() {
        return detail.extendedTrackData;
    }
}
