package com.spotify.app;

import com.spotify.api.SpotifyClient;
import com.spotify.view.WelcomeView;

import javax.swing.*;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("SpotifyRecs");
            WelcomeView welcomeView = new WelcomeView();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 800);
            frame.add(welcomeView);
            frame.setVisible(true);
        });

    }
}
