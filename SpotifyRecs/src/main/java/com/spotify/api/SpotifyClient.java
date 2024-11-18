package com.spotify.api;

import com.spotify.factory.PlaylistFactory;
import com.spotify.factory.SongFactory;
import com.spotify.factory.UserFactory;
import com.spotify.models.Playlist;
import com.spotify.models.RecommendationCriteria;
import com.spotify.models.Song;
import com.spotify.models.User;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Class to handle interactions with Spotify Web API
public class SpotifyClient implements SpotifyAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(SpotifyClient.class);
    private final SpotifyAuthenticator authenticator;
    private String accessToken;
    private String refreshToken;
    String auth = "Authorization";
    String bearer = "Bearer ";
    String ct = "Content-Type";
    String app = "application/json";


    public SpotifyClient(String clientId, String redirectUri) {
        // Initialize SpotifyAuthenticator without clientSecret for PKCE
        this.authenticator = new SpotifyAuthenticator(clientId, redirectUri);
    }

    //Uses SpotifyAuthenticator to allow us to access the user's info
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
            this.refreshToken = authenticator.getRefreshToken(); // Store refresh token
            System.out.println("Authentication successful!");
        } else {
            System.err.println("Failed to authenticate.");
        }

        return success;
    }

    // Method to send HTTP GET requests to Spotify API. See other methods as
    // example of how to use.
    private JSONObject makeGetRequest(String url) throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(url);
            get.setHeader(auth, bearer + accessToken);
            get.setHeader(ct, app);

            try (CloseableHttpResponse response = httpClient.execute(get)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                return new JSONObject(jsonResponse);
            }
        }
    }

    // Method to send HTTP POST requests to Spotify API. See other methods as
    // examples of how to use.
    private JSONObject makePostRequest(String url, JSONObject requestBody) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setHeader(auth, bearer + accessToken);
            post.setHeader(ct, app);
            post.setEntity(new StringEntity(requestBody.toString()));

            try (CloseableHttpResponse response = httpClient.execute(post)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                return new JSONObject(jsonResponse);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Method to ask Spotify to create a playlist for the User in question.
    @Override
    public Playlist createPlaylist(User user, String name, String description, boolean isPublic) throws IOException {
        String url = "https://api.spotify.com/v1/users/" + user.getUserId() + "/playlists";

        // Create JSON body for the request
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", name);
        requestBody.put("description", description);
        requestBody.put("public", isPublic);

        // Send the request
        JSONObject jsonResponse = makePostRequest(url, requestBody);

        // Parse the JSON response to get the playlist ID
        String id = jsonResponse.getString("id");

        List<Song> songs = new ArrayList<>();
        PlaylistFactory pf = new PlaylistFactory();
        // Return a new Playlist object with the obtained ID
        return pf.createPlaylist(id, name, description, isPublic, songs, user);
    }

    //Method to add the generated songs (or any song for that matter as long
    //as it's in a list) to a specific playlist in our collection.
    @Override
    public void addSongsToPlaylist(Playlist playlist, List<Song> songs) throws IOException {
        String playlistId = playlist.getPlaylistId();
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";

        // Collect all song URIs into a list
        List<String> uris = songs.stream()
                .map(song -> "spotify:track:" + song.getSongId())
                .toList();

        // Create JSON body for the request
        JSONObject requestBody = new JSONObject();
        requestBody.put("uris", uris); // Add all song URIs in one request

        // Send the request
        JSONObject jsonResponse = makePostRequest(url, requestBody);

        // Check for successful response (status code 201 for successful addition)
        if (jsonResponse.has("error")) {
            if (logger.isErrorEnabled()) {
                logger.error("Failed to add songs: {}", jsonResponse.
                        getJSONObject("error").getString("message"));
            }
            throw new IOException("Failed to add songs to playlist");
        }

        // Add songs to the local playlist object as well
        playlist.addSongs(songs);
    }

    //Method to get the generated list of songs to put in the playlist.
    @Override
    public List<Song> getRecommendations(RecommendationCriteria criteria, Integer num) throws IOException, ParseException {
        String genreIds = String.join(",", criteria.getGenreIds());
        String artistIds = String.join(",", criteria.getArtistIds());
        String trackIds = String.join(",", criteria.getTrackIds());

        String recUrl = "https://api.spotify.com/v1/recommendations?"
                + "seed_genres=" + genreIds
                + "&seed_artists=" + artistIds
                + "&seed_tracks=" + trackIds
                + "&limit=" + num.toString()
                + "&min_popularity=50"
                + "&max_popularity=70";

        // Send the request
        JSONObject jsonResponse = makeGetRequest(recUrl);
        System.out.println(jsonResponse.toString());
        // Parse the JSON response and create a list of Song objects
        SongFactory sf = new SongFactory();
        return sf.createSongs(jsonResponse);
    }

    //Get the info of the current user and return it as a user object
    @Override
    public User getCurrentUser() throws IOException, ParseException {
        String userUrl = "https://api.spotify.com/v1/me";

        // Send the request
        JSONObject jsonResponse = makeGetRequest(userUrl);

        // Parse the JSON response and create a User object
        UserFactory uf = new UserFactory();
        return uf.createUser(jsonResponse);
    }

    public String getSearchArtist(String artist) throws IOException, ParseException {
        String baseArtist = "https://api.spotify.com/v1/search";
        String[] a = artist.split("\\s+");
        String aa = "";
        for (int i = 0; i < a.length; i++) {
            aa = aa.concat(a[i]);
            if (1 < a.length - i) {
                aa = aa.concat("+");
            }
        }
        String endpoint = baseArtist +
                "?q=artist%3A" + aa + "&type=artist&market=CA&limit=1";
        JSONObject jsonResponse = makeGetRequest(endpoint);
        return jsonResponse.getJSONObject("artists").getJSONArray("items").getJSONObject(0).getString("id");
    }

    public String getSearchSong(String artist) throws IOException, ParseException {
        String baseSong = "https://api.spotify.com/v1/search";
        String[] a = artist.split("\\s+");
        String aa = "";
        for (int i = 0; i < a.length; i++) {
            aa = aa.concat(a[i]);
            if (1 < a.length - i) {
                aa = aa.concat("+");
            }
        }
        String endpoint = baseSong +
                "?q=track%3a" + aa + "&type=track&market=CA&limit=1";
        JSONObject jsonResponse = makeGetRequest(endpoint);
        return jsonResponse.getJSONObject("tracks").getJSONArray("items").getJSONObject(0).getString("id");
    }

    public List<String> getGenres() throws IOException, ParseException {
        String baseGenre = "https://api.spotify.com/v1/recommendations/available-genre-seeds";
        JSONObject jsonResponse = makeGetRequest(baseGenre);
        JSONArray genresArray = jsonResponse.getJSONArray("genres");
        List<String> genresList = new ArrayList<>();
        for (int i = 0; i < genresArray.length(); i++) {
            genresList.add(genresArray.getString(i));
        }
        return genresList;
    }

    // Handle token expiration by refreshing the access token
    private void refreshAccessToken() throws IOException {
        String url = "https://accounts.spotify.com/api/token";
        JSONObject requestBody = new JSONObject();
        requestBody.put("grant_type", "refresh_token");
        requestBody.put("refresh_token", refreshToken);

        // Send POST request to refresh the token
        JSONObject jsonResponse = makePostRequest(url, requestBody);

        // Update access token
        this.accessToken = jsonResponse.getString("access_token");
    }
}
