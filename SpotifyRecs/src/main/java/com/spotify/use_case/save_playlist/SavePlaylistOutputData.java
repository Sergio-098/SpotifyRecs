package com.spotify.use_case.save_playlist;

public class SavePlaylistOutputData {

    private final String name;

    private final boolean useCaseFailed;

    public SavePlaylistOutputData(String username, boolean useCaseFailed) {
        this.name = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getName() {
        return name;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
