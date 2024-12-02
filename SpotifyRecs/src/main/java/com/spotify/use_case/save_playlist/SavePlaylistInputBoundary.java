package com.spotify.use_case.save_playlist;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public interface SavePlaylistInputBoundary {

    /**
     * Executes the save playlist use case.
     * @param savePlaylistInputData the input data
     */
    void execute(SavePlaylistInputData savePlaylistInputData) throws IOException, ParseException;

    /**
     * Executes the switch to login view use case.
     */
    void switchToLoggedInView();
}
