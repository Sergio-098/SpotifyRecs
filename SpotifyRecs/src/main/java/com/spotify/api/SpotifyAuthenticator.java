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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

public class SpotifyAuthenticator {
    private final String clientId;
    private final String redirectUri;
    private String codeVerifier;
    private String accessToken;
    private String refreshToken;

    public SpotifyAuthenticator(String clientId, String redirectUri) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.codeVerifier = generateCodeVerifier();
    }

    public String getAuthorizationUrl() {
        try {
            String codeChallenge = generateCodeChallenge(codeVerifier);
            return new URIBuilder("https://accounts.spotify.com/authorize")
                    .addParameter("client_id", clientId)
                    .addParameter("response_type", "code")
                    .addParameter("redirect_uri", redirectUri)
                    .addParameter("scope", "playlist-modify-public playlist-modify-private")
                    .addParameter("code_challenge_method", "S256")
                    .addParameter("code_challenge", codeChallenge)
                    .toString();
        } catch (Exception e) {
            throw new RuntimeException("Error building authorization URL", e);
        }
    }

    public boolean exchangeCodeForTokens(String code) throws IOException {
        URI tokenUri = URI.create("https://accounts.spotify.com/api/token");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(tokenUri);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            List<BasicNameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "authorization_code"));
            params.add(new BasicNameValuePair("code", code));
            params.add(new BasicNameValuePair("redirect_uri", redirectUri));
            params.add(new BasicNameValuePair("client_id", clientId));
            params.add(new BasicNameValuePair("code_verifier", codeVerifier));

            post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(post);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {

                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }

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
            return false;
        }
        String url = "https://accounts.spotify.com/api/token";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            List<BasicNameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "refresh_token"));
            params.add(new BasicNameValuePair("refresh_token", refreshToken));
            params.add(new BasicNameValuePair("client_id", clientId));

            post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(post);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {

                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
                JSONObject json = new JSONObject(responseBody.toString());

                if (json.has("access_token")) {
                    accessToken = json.getString("access_token");
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String generateCodeVerifier() {
        byte[] randomBytes = new byte[32];
        new Random().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private String generateCodeChallenge(String verifier) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(verifier.getBytes(StandardCharsets.US_ASCII));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public String getAccessToken() {
        return accessToken;
    }
}
