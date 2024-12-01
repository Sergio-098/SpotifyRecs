package com.spotify.use_case.add_song;

public interface AddSongOutputBoundary {
    void prepareSuccessView(AddSongOutputData outputdata);

    void prepareFailView(AddSongOutputData outputdata);
}
