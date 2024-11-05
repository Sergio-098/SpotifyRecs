package com.spotify.app;

import com.spotify.api.SpotifyClient;
import com.spotify.ui.PlaylistGeneratorUI;
import com.spotify.models.User;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting Spotify Playlist Generator...");

        // Replace with your actual client ID, client secret, and redirect URI
        String clientId = "your_client_id";
        String clientSecret = "your_client_secret";
        String redirectUri = "your_redirect_uri";

        SpotifyClient spotifyClient = new SpotifyClient(clientId, clientSecret, redirectUri);

        if (spotifyClient.authenticate()) {
            System.out.println("User successfully authenticated!");

            // Get current user information
            try {
                User user = spotifyClient.getCurrentUser();
                System.out.println("Welcome, " + user.getUserId());
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to retrieve user information.");
            }

            // Initialize the UI with necessary components
            PlaylistGeneratorUI playlistUI = new PlaylistGeneratorUI(); //to be changed
            // Start the user interface. Placeholder
            playlistUI.start();

        } else {
            System.err.println("Authentication failed. Check credentials or authorization code.");
        }
    }
}
