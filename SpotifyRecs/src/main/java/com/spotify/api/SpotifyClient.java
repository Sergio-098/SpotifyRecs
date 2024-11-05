package com.spotify.api;

import com.spotify.models.Playlist;
import com.spotify.models.RecommendationCriteria;
import com.spotify.models.Song;
import com.spotify.models.User;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SpotifyClient implements SpotifyAPIClient {
    private final SpotifyAuthenticator authenticator;
    private String accessToken;

    public SpotifyClient(String clientId, String clientSecret, String redirectUri) {
        this.authenticator = new SpotifyAuthenticator(clientId, clientSecret, redirectUri);
    }

    @Override
    public boolean authenticate() throws IOException {
        System.out.println("Authorize your account by visiting the following URL:");
        System.out.println(authenticator.getAuthorizationUrl());

        System.out.print("Enter the authorization code: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();

        boolean success = authenticator.exchangeCodeForTokens(code);
        if (success) {
            this.accessToken = authenticator.getAccessToken();
            System.out.println("Authentication successful!");
        } else {
            System.err.println("Failed to authenticate.");
        }

        return success;
    }

    public User getCurrentUser() throws IOException {
        String url = "https://api.spotify.com/v1/me";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet getRequest = new HttpGet(url);
            getRequest.setHeader("Authorization", "Bearer " + accessToken);

            try (CloseableHttpResponse response = httpClient.execute(getRequest)) {
                if (response.getCode() == 200) {
                    String jsonResponse = EntityUtils.toString(response.getEntity());
                    return parseUser(jsonResponse);
                } else {
                    throw new IOException("Failed to get current user, status code: " + response.getCode());
                }
            }
        } catch (ParseException e) {
            throw new IOException("Error parsing response", e);
        }
    }

    private User parseUser(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        String id = jsonObject.getString("id");
        String displayName = jsonObject.optString("display_name", "No display name");

        // Create and return a User object
        return new User(id, displayName); // Adjust based on your User class constructor
    }

    @Override
    public Playlist createPlaylist(User user, String name, String description, boolean isPublic) throws IOException {
        // Logic for creating a playlist using Spotify's API
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
}

