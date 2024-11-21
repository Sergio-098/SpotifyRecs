package com.spotify.use_case.authorize;

import com.spotify.api.SpotifyClient;

import java.io.IOException;

public class AuthorizeInteractor implements AuthorizeInputBoundary {
    private final SpotifyClient spotifyClient;

    public AuthorizeInteractor(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    @Override
    public boolean execute(String code) throws IOException {
        return spotifyClient.authorize(code);
    }
}
