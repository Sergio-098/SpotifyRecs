package com.spotify.use_case.remove_song;

public interface RemoveSongInputBoundary {

    /**
     * Executes the  use case.
     * @param removeSongInputData the input data
     */

    void execute(RemoveSongInputData removeSongInputData);
}
