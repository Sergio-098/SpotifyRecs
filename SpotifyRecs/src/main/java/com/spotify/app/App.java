package com.spotify.app;

import com.spotify.api.SpotifyClient;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting Spotify Playlist Generator...");
        String clientID = "a54ea954b9fe41408a55d3a577126fa1";
        String redirect = "http://localhost:8080/callback";


        SpotifyClient spotifyClient = new SpotifyClient(clientID, redirect);

        if (spotifyClient.authenticate()) {
            System.out.println("User successfully authenticated!");
            // Now you can use spotifyClient to make authorized API calls
        } else {
            System.err.println("Authentication failed. Check credentials or authorization code.");
        }
    }
}