package com.spotify.use_case.remove_song;

public class RemoveSongOutputData {

    private final String songID;
    private final boolean useCaseFailed;

    public RemoveSongOutputData(String songID, boolean useCaseFailed) {
        this.songID = songID;
        this.useCaseFailed = useCaseFailed;
    }

    public String getSongID() {return songID;}
}
