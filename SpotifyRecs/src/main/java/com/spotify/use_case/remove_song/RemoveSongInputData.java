package com.spotify.use_case.remove_song;

public class RemoveSongInputData {
    private final String songname;

    public RemoveSongInputData(String songName) {
        this.songname = songName;
    }

    String getSongName() {return songname;}
}
