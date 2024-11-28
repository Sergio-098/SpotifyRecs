package com.spotify.use_case.authorize;


public class AuthorizeInteractor implements AuthorizeInputBoundary {
    private final AuthorizeUserDataAccessInterface userDataAccessObject;
    private final AuthorizeOutputBoundary authorizePresenter;

    public AuthorizeInteractor(AuthorizeUserDataAccessInterface userDataAccessInterface, AuthorizeOutputBoundary authorizeOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.authorizePresenter = authorizeOutputBoundary;
    }

}
