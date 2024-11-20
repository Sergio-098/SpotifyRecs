package com.spotify.view;

import com.spotify.api.SpotifyClient;

import javax.swing.*;
import java.awt.*;

public class LoggedInView extends JPanel {
    private SpotifyClient spotify;

    public LoggedInView(SpotifyClient spotify) {
        this.spotify = spotify;
        this.setLayout(new BorderLayout());
        JLabel label = new JLabel("Logged in now", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        this.add(label, BorderLayout.CENTER);

        if(spotify.getAccessToken() != null) {
            JLabel newLabel = new JLabel("Authorization Worked!!!!", SwingConstants.CENTER);
            newLabel.setFont(new Font("Arial", Font.BOLD, 30));
            this.add(newLabel, BorderLayout.SOUTH);
        }
    }
}
