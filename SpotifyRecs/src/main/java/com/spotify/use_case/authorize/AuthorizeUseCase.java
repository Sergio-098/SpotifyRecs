package com.spotify.use_case.authorize;

import com.spotify.api.SpotifyClient;

import java.io.IOException;

public class AuthorizeUseCase {
    private SpotifyClient spotifyClient;

    public AuthorizeUseCase(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }
    public boolean execute() throws IOException {
        return spotifyClient.authenticate();
    }
}
