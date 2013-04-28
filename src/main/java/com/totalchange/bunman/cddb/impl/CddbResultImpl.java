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
