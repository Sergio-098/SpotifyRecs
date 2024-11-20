package com.spotify.use_case.authorize;

import com.spotify.api.SpotifyClient;
import java.io.IOException;

public class AuthorizeUseCase {
    private SpotifyClient spotifyClient;

    public AuthorizeUseCase(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }
    public boolean execute(String code) throws IOException {
        return spotifyClient.authorize(code);
    }
    public boolean execute2() throws IOException {
        return spotifyClient.authenticate();
    }
}
