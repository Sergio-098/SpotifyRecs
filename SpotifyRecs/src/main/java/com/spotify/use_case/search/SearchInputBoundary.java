package com.spotify.use_case.search;

import com.spotify.use_case.save_playlist.SavePlaylistInputData;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public interface SearchInputBoundary {

    /**
     * Executes the search use case.
     * @param searchInputData the input data
     */
    void execute(SearchInputData searchInputData) throws IOException, ParseException;
}
