package com.totalchange.bunman;

import java.io.IOException;

public interface WritableCatalogue extends Catalogue {
    Song copySong(Song song) throws IOException;
}
