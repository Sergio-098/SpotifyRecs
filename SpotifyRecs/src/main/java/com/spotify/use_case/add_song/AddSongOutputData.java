package com.spotify.use_case.add_song;

public class AddSongOutputData {

    private final Integer number;

    private final boolean useCaseFailed;


    public AddSongOutputData(Integer number, boolean useCaseFailed) {
        this.number = number;
        this.useCaseFailed = useCaseFailed;
    }

    public Integer getNumber() {return number;}

    public boolean isUseCaseFailed() {return useCaseFailed;}
}
