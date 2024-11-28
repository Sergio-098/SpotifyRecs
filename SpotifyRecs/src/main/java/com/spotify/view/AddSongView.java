package com.spotify.view;

import com.spotify.interface_adapter.save_playlist.AddSongViewModel;

import javax.swing.*;

public class AddSongView extends JPanel {
    private final String viewName = "add song";

    public AddSongView(AddSongViewModel addSongViewModel) {
    }

    public String getViewName() {
        return viewName;
    }
}
