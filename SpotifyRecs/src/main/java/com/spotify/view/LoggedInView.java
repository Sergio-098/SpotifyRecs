package com.spotify.view;

import com.spotify.api.SpotifyClient;
import com.spotify.interface_adapter.generate.GenerateController;
import com.spotify.interface_adapter.generate.LoggedInViewModel;

import javax.swing.*;
import java.awt.*;

public class LoggedInView extends JPanel {
    private final LoggedInViewModel viewModel;
    private SpotifyClient spotify;

    public LoggedInView(LoggedInViewModel viewModel) {
        this.viewModel = viewModel;
        this.setLayout(new BorderLayout());
        JLabel label = new JLabel("Logged in now", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        this.add(label, BorderLayout.CENTER);
        spotify = new SpotifyClient("a54ea954b9fe41408a55d3a577126fa1", "http://localhost:8080/callback");
        if(spotify.getAccessToken() != null) {
            JLabel newLabel = new JLabel("Authorization Worked!!!!", SwingConstants.CENTER);
            newLabel.setFont(new Font("Arial", Font.BOLD, 30));
            this.add(newLabel, BorderLayout.SOUTH);
        }
    }

    public void setPlaylistController(GenerateController controller) {
    }

    public String getViewName() {
        return "logged in";
    }
}
