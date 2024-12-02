package com.spotify.use_case.remove_song;

public class RemoveSongOutputData {
    private final String name;
    private final boolean useCaseFailed;

    public RemoveSongOutputData(String name, boolean useCaseFailed) {
        this.name = name;
        this.useCaseFailed = useCaseFailed;
    }

    public String getName() {return name;}
    public boolean isUseCaseFailed() {return useCaseFailed;}


}
