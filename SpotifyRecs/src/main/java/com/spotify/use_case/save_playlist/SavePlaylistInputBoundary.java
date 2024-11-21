package com.spotify.use_case.save_playlist;

import java.io.IOException;

public interface SavePlaylistInputBoundary {

    /**
     * Executes the save playlist use case.
     * @param savePlaylistInputData the input data
     */
    void execute(SavePlaylistInputData savePlaylistInputData) throws IOException;

    /**
     * Executes the switch to login view use case.
     */
    void switchToLoggedInView();
}
