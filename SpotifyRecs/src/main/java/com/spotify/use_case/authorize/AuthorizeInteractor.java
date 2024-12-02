package com.spotify.use_case.authorize;

import com.spotify.api.SpotifyClient;
import com.spotify.data_access.FileUserDataAccessObject;
import com.spotify.view.AuthorizationView;

import java.io.IOException;

public class AuthorizeInteractor implements AuthorizeInputBoundary {
    private final FileUserDataAccessObject userDAO;
    private final AuthorizeOutputBoundary authorizePresenter;
    private final SpotifyClient spotifyClient;

;

    public AuthorizeInteractor(SpotifyClient spotifyClient, AuthorizeOutputBoundary authorizeOutputBoundary, FileUserDataAccessObject userDAO) {
        this.userDAO = userDAO;
        this.authorizePresenter = authorizeOutputBoundary;
        this.spotifyClient = spotifyClient;

    }

    @Override
    public boolean execute(AuthorizeInputData authorizeInputData) throws IOException {

        return spotifyClient.authorize(authorizeInputData.getCode());
    }

    @Override
    public void switchToLoggedInView() {
        authorizePresenter.switchToLoggedInView();
    }

}
