package com.totalchange.bunman.cddb;

public interface CddbResult {
    String getDiscId();
    String getCategory();
    String getTitle();
    String[] getTrackNames();
    String getExtendedData();
    String[] getExtendedTrackData();
}
