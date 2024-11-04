package com.spotify.app;

import com.spotify.ui.PlaylistGeneratorUI;
import com.spotify.api.SpotifyClient;
import com.spotify.models.User;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting Spotify Playlist Generator...");

        // Initialize Spotify API client
        SpotifyClient spotifyClient = new SpotifyClient();

        // Authenticate the user and get user details
        User user = spotifyClient.authenticateUser();  // This could be a login flow

        // Initialize the UI with necessary components
        PlaylistGeneratorUI playlistUI = new PlaylistGeneratorUI(spotifyClient, user);

        // Start the user interface
        playlistUI.start();
    }
}
