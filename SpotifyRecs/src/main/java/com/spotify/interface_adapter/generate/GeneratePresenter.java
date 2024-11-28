package com.spotify.interface_adapter.generate;

import com.spotify.interface_adapter.ViewManagerModel;
import com.spotify.interface_adapter.save_playlist.PlaylistViewModel;
import com.spotify.use_case.generate.GenerateOutputBoundary;

public class GeneratePresenter implements GenerateOutputBoundary {
    public GeneratePresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel, PlaylistViewModel playlistViewModel) {
    }
}
