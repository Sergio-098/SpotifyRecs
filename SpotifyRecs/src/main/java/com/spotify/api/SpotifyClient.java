package com.spotify.api;

import com.spotify.models.Playlist;
import com.spotify.models.RecommendationCriteria;
import com.spotify.models.Song;
import com.spotify.models.User;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Class to handle interactions with Spotify Web API
public class SpotifyClient implements SpotifyAPIClient {
    private final SpotifyAuthenticator authenticator;
    private String accessToken;

    public SpotifyClient(String clientId, String redirectUri) {
        // Initialize SpotifyAuthenticator without clientSecret for PKCE
        this.authenticator = new SpotifyAuthenticator(clientId, redirectUri);
    }

    @Override
    public boolean authenticate() throws IOException {
        System.out.println("Authorize your account by visiting the following URL:");
        System.out.println(authenticator.getAuthorizationUrl());

        System.out.print("Enter the authorization code: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();

        // Use PKCE method to exchange the code for tokens
        boolean success = authenticator.exchangeCodeForTokens(code);
        if (success) {
            this.accessToken = authenticator.getAccessToken();
            System.out.println("Authentication successful!");
        } else {
            System.err.println("Failed to authenticate.");
        }

        return success;
    }

    @Override
    public Playlist createPlaylist(User user, String name, String description, boolean isPublic) throws IOException {
        // Logic for creating a playlist using Spotify's API
        // Example: Make an HTTP POST request to /v1/users/{user_id}/playlists
        // Use accessToken in the header for authorization
        return new Playlist(); // Placeholder
    }

    @Override
    public void addSongsToPlaylist(Playlist playlist, List<Song> songs) throws IOException {
        // Logic for adding songs to the playlist
    }

    @Override
    public List<Song> getRecommendations(RecommendationCriteria criteria) throws IOException {
        // Logic for retrieving recommendations based on criteria
        return List.of(); // Placeholder
    }

    public User getCurrentUser() throws IOException {
        //grab user information
        User user = new User();
        return user;
    }
}
