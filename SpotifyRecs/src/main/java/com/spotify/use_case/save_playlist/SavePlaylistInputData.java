package com.spotify.use_case.save_playlist;

public class SavePlaylistInputData {
    private final String name;
    private final String description;
    private final boolean isPublic;

    public SavePlaylistInputData(String name, String description, boolean isPublic) {
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

    public boolean getIsPublic() {
        return isPublic;
    }
}
