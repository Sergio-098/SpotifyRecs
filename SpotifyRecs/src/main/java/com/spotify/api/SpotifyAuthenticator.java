package com.spotify.api;

import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SpotifyAuthenticator {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private String accessToken;
    private String refreshToken;

    public SpotifyAuthenticator(String clientId, String clientSecret, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public String getAuthorizationUrl() {
        try {
            return new URIBuilder("https://accounts.spotify.com/authorize")
                    .addParameter("client_id", clientId)
                    .addParameter("response_type", "code")
                    .addParameter("redirect_uri", redirectUri)
                    .addParameter("scope", "playlist-modify-public playlist-modify-private")
                    .toString();
        } catch (Exception e) {
            throw new RuntimeException("Error building authorization URL", e);
        }
    }

    public boolean exchangeCodeForTokens(String code) throws IOException {
        URI tokenUri = URI.create("https://accounts.spotify.com/api/token");

        // Create an HttpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create an HttpPost request
            HttpPost post = new HttpPost(tokenUri);

            // Add headers
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            // Set parameters for the request body
            List<BasicNameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "authorization_code"));
            params.add(new BasicNameValuePair("code", code));
            params.add(new BasicNameValuePair("redirect_uri", redirectUri));
            params.add(new BasicNameValuePair("client_id", clientId));
            params.add(new BasicNameValuePair("client_secret", clientSecret));

            // Set the entity with the parameters
            post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            // Execute the POST request and get the response
            try (CloseableHttpResponse response = httpClient.execute(post);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {

                // Read the response and parse the JSON
                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }

                // Parse the tokens from the response JSON
                return parseTokens(responseBody.toString());
            }
        }
    }


    private boolean parseTokens(String jsonResponse) {
        JSONObject json = new JSONObject(jsonResponse);
        if (json.has("access_token")) {
            accessToken = json.getString("access_token");
        }
        if (json.has("refresh_token")) {
            refreshToken = json.getString("refresh_token");
        }
        return accessToken != null;
    }

    public boolean refreshToken() {
        if (refreshToken == null) {
            System.out.println("Refresh token is not available. Cannot refresh access token.");
            return false; // Exit the method if there's no refresh token
        }
        String url = "https://accounts.spotify.com/api/token";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);

            // Add headers
            String auth = clientId + ":" + clientSecret;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            post.setHeader("Authorization", "Basic " + encodedAuth);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            // Set parameters for refresh token request
            List<BasicNameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "refresh_token"));
            params.add(new BasicNameValuePair("refresh_token", refreshToken));
            post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            // Execute the POST request
            try (CloseableHttpResponse response = httpClient.execute(post);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {

                // Read response and parse JSON
                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
                JSONObject json = new JSONObject(responseBody.toString());

                // Get new access token
                if (json.has("access_token")) {
                    accessToken = json.getString("access_token");
                    return true;  // Successful refresh
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  // Refresh failed
    }

    public String getAccessToken() {
        return accessToken;
    }
}
