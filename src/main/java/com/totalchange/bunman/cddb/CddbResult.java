package com.totalchange.bunman.cddb;

public interface CddbResult {
    String getDiscId();
    String getCategory();
    String getTitle();
    int getYear();
    String getGenre();
    String[] getTrackNames();
    String getExtendedData();
    String[] getExtendedTrackData();
}
