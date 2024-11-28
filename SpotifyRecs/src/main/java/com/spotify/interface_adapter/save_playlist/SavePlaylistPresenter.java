package com.spotify.interface_adapter.save_playlist;

import com.spotify.interface_adapter.ViewManagerModel;
import com.spotify.interface_adapter.generate.LoggedInViewModel;
import com.spotify.use_case.save_playlist.SavePlaylistOutputBoundary;

public class SavePlaylistPresenter implements SavePlaylistOutputBoundary {
    public SavePlaylistPresenter(ViewManagerModel viewManagerModel, PlaylistViewModel playlistViewModel, LoggedInViewModel loggedInViewModel) {
    }
}
