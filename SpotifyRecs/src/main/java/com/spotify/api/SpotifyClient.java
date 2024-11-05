package com.spotify.api;

import com.spotify.factory.SongFactory;
import com.spotify.models.Playlist;
import com.spotify.models.RecommendationCriteria;
import com.spotify.models.Song;
import com.spotify.models.User;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Class to handle interactions with Spotify Web API
//We use APACHE http interactor version 5.3.1 to make api requests. ChatGPT can
//help you get a good idea of how that will work specifically with the spotify api
//since it is familiar with both.
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
        // Placeholder until API request logic is written
        //api response is the placeholder for the output from the api call
        JSONObject json = new JSONObject(apiResponse);
        String id = json.getString("id");
        return new Playlist(id, name, description, isPublic);
    }

    @Override
    public void addSongsToPlaylist(Playlist playlist, List<Song> songs) throws IOException {
        // Logic for adding songs to the playlist using the api but also storing
        //the songs into a playlist object as needed.
        String PlaylistID = playlist.getPlaylistId();
        //ADD SONGS WITH API AS WELL BY LOOPING OVER LIST OF SONGS AND ACCESSING
        //THEIR FIELD song.songId WHILE ADDING THEM TO PlaylistID
        playlist.addSongs(songs);
    }

    @Override
    public List<Song> getRecommendations(RecommendationCriteria criteria) throws IOException {
        // Logic for retrieving recommendations based on criteria
        // Placeholder until implemented. To use SongFactory to create the list
        //of songs returned
        SongFactory sf = new SongFactory();
        //Variable apiResponse below is the json return from Get Recommendations call
        //to the Spotify API
        JSONObject recommendations = new JSONObject(apiResponse);
        List<Song> allSongs = sf.createSongs(recommendations);
        return allSongs;
    }

    @Override
    public User getCurrentUser() throws IOException {
        //grabs the current user's user object in case we need its ID or username,
        //(like when we use the api to create and alter a playlist).
        //Placeholders currently
        User user = new User();
        return user;
    }
}
