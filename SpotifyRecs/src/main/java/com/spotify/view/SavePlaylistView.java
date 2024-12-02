package com.spotify.view;

import com.spotify.interface_adapter.save_playlist.SavePlaylistViewModel;

import javax.swing.*;

public class SavePlaylistView extends JPanel {

    public SavePlaylistView(SavePlaylistViewModel savePlaylistViewModel) {
    }

    public String getViewName() {
        return "save playlist";
    }
}
