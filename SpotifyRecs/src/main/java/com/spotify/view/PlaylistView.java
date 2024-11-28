package com.spotify.view;

import com.spotify.interface_adapter.save_playlist.PlaylistViewModel;
import com.spotify.interface_adapter.save_playlist.SavePlaylistController;

import javax.swing.*;

public class PlaylistView extends JPanel {
    public PlaylistView(PlaylistViewModel playlistViewModel) {
    }

    public void setController(SavePlaylistController controller) {

    }

    public String getViewName() {
        return "playlist";
    }
}
