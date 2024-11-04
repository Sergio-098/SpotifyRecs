package com.spotify.api;

import com.spotify.models.Playlist;
import com.spotify.models.RecommendationCriteria;
import com.spotify.models.Song;
import com.spotify.models.User;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONArray;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;



public class SpotifyClient implements SpotifyAPIClient {
    private String accessToken;
    private final String BASE_URL = "https://api.spotify.com/v1";

    // Constructor
    public SpotifyClient(String accessToken) {
        this.accessToken = accessToken;
    }

    // Method to authenticate and obtain an access token (if needed)
    public void authenticate() {
        // Implement your OAuth 2.0 authentication logic here
        // Obtain the access token and set it to this.accessToken
    }

    // Method to create a playlist for a user
    @Override
    public Playlist createPlaylist(User user, String name, String description, boolean isPublic) throws IOException {
        // Create a new playlist by sending a POST request to the Spotify API
        String url = BASE_URL + "/users/" + user.getUserId() + "/playlists";
        // Prepare request body and headers
        // Use an HTTP client to send the request and get the response
        // Parse the response to create a Playlist object
        return new Playlist(/* Extracted playlist details */);
    }

    // Method to add songs to a playlist
    @Override
    public void addSongsToPlaylist(Playlist playlist, List<Song> songs) throws IOException {
        // Prepare the PATCH request to add songs to the playlist
        String url = BASE_URL + "/playlists/" + playlist.getPlaylistId() + "/tracks";
        // Prepare the request body with song IDs
        // Use an HTTP client to send the request
    }

    // Method to get song recommendations
    @Override
    public List<Song> getRecommendations(RecommendationCriteria criteria) throws IOException {
        // Prepare the GET request to retrieve recommendations
        String url = BASE_URL + "/recommendations?seed_artists=" + String.join(",", criteria.getArtistIds()) +
                "&seed_genres=" + String.join(",", criteria.getGenreIds()) +
                "&seed_tracks=" + String.join(",", criteria.getTrackIds());
        // Use an HTTP client to send the request and get the response
        // Parse the response to extract Song objects
        return List.of(/* Extracted song objects */);
    }

    // Additional helper methods for making requests, handling responses, etc.
    private String makeGetRequest(String url) throws IOException {
        // Implement the logic for making GET requests using your HTTP client
        return ""; // Return the response as a string
    }

    private String makePostRequest(String url, String jsonBody) throws IOException {
        // Implement the logic for making POST requests using your HTTP client
        return ""; // Return the response as a string
    }

    private String makePatchRequest(String url, String jsonBody) throws IOException {
        // Implement the logic for making PATCH requests using your HTTP client
        return ""; // Return the response as a string
    }

    @Override
    public void authenticateUser() throws IOException, AuthenticationException {
        // Step 1: Generate the authorization URL
        String clientId = "a54ea954b9fe41408a55d3a577126fa1";
        String clientSecret = "15746031dbb140a8867fa1c03e4ec7fd";
        String redirectUri = "http://localhost:8080"; // Placeholder for now
        String scope = "user-read-private user-read-email"; // Adjust scopes as necessary
        String authUrl = "https://accounts.spotify.com/authorize?" +
                "response_type=code" +
                "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) +
                "&scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8);

        // Step 2: Open the URL in the user's browser
        System.out.println("Please open the following URL in your browser:");
        System.out.println(authUrl);

        // Step 3: Wait for the user to authenticate and return with a code
        System.out.println("Enter the code from the URL you were redirected to:");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();

        // Step 4: Exchange the code for an access token
        String tokenUrl = "https://accounts.spotify.com/api/token";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(tokenUrl);

            // Set headers
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            post.setHeader("Authorization", "Basic " +
                    java.util.Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()));

            // Set request body
            String body = "grant_type=authorization_code" +
                    "&code=" + code +
                    "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
            post.setEntity(new StringEntity(body));

            // Execute request
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = new JSONObject(jsonResponse);

                // Parse and store access token
                this.accessToken = jsonObject.getString("access_token");
                System.out.println("Access Token: " + accessToken);
            }
        }
    }

    // Getter for accessToken if needed by other methods
    public String getAccessToken() {
        return accessToken;
    }
}
        // Use your preferred HTTP client to send a POST request to tokenUrl
        // Include client_id, client_secret, code, redirect_uri, and grant_type in the request body

        // Handle the response to extract and store the access token
        // Handle exceptions and errors appropriately
    }

}
