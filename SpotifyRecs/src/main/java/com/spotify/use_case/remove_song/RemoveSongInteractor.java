package com.spotify.use_case.remove_song;

public class RemoveSongInteractor implements RemoveSongInputBoundary{
    private final RemoveSongUserDataAccessInterface userDataAccessInterface;
    private final RemoveSongOutputBoundary removeSongPreesenter;

    public RemoveSongInteractor(RemoveSongUserDataAccessInterface userDataAccessInterface,
                                RemoveSongOutputBoundary removeSongOutputBoundary) {
        this.userDataAccessInterface = userDataAccessInterface;
        this.removeSongPreesenter = removeSongOutputBoundary;
    }

    @Override
    public void execute(RemoveSongInputData removeSongInputData) {
        final String songID = removeSongInputData.getSongID();




    }
}
