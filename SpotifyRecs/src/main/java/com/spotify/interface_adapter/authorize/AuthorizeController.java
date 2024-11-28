package com.spotify.interface_adapter.authorize;

import com.spotify.use_case.authorize.AuthorizeInputBoundary;

public class AuthorizeController {
    private final AuthorizeInputBoundary authorizeInteractor;

    public AuthorizeController(AuthorizeInputBoundary authorizeInteractor) {
        this.authorizeInteractor = authorizeInteractor;
    }
}
