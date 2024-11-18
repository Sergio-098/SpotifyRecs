package com.spotify.use_case.remove_song;

public class RemoveSongInputData {

    private final String songID;


    public RemoveSongInputData(String songID) {
        this.songID = songID;
    }

    String getSongID() {return songID;}

}
