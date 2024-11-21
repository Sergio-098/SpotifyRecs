package com.spotify.interface_adapter.save_playlist;

import com.spotify.use_case.save_playlist.SavePlaylistInputBoundary;
import com.spotify.use_case.save_playlist.SavePlaylistInputData;

import java.io.IOException;

public class SavePlaylistController {

    private final SavePlaylistInputBoundary savePlaylistInputBoundary;

    public SavePlaylistController(SavePlaylistInputBoundary savePlaylistInteractor) {
        this.savePlaylistInputBoundary = savePlaylistInteractor;
    }

    /**
     * Executes the Signup Use Case.
     * @param name the name of the playlist
     * @param description the description
     * @param isPublic the public status of the playlist
     */
    public void execute(String name, String description, boolean isPublic) throws IOException {
        final SavePlaylistInputData signupInputData = new SavePlaylistInputData(
                name, description, isPublic);

        savePlaylistInputBoundary.execute(signupInputData);
    }

    /**
     * Executes the "switch to LoggedInView" Use Case.
     */
    public void switchToLoggedInView() {
        savePlaylistInputBoundary.switchToLoggedInView();
    }
}
