package com.spotify.use_case.remove_song;

public interface RemoveSongOutputBoundary {
    void prepareSuccessView(RemoveSongOutputData outputData);

    void prepareFailView(String errorMessage);
}
