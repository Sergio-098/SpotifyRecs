package com.spotify.interface_adapter.authorize;

import com.spotify.use_case.authorize.AuthorizeInputBoundary;
import com.spotify.use_case.authorize.AuthorizeInputData;

import java.io.IOException;

/**
 * The controller for the Authorize Use Case.
 */
public class AuthorizeController {

    private final AuthorizeInputBoundary authorizeInteractor;

    public AuthorizeController(AuthorizeInputBoundary authorizeInteractor) {
        this.authorizeInteractor = authorizeInteractor;
    }

    /**
     * Executes the Authorize Use Case.
     */
    public void execute(String code) throws IOException {
        final AuthorizeInputData authorizeInputData = new AuthorizeInputData(code);
        authorizeInteractor.execute(authorizeInputData);
    }

    /**
     * Executes the "switch to LoggedInView" Use Case.
     */
    public void switchToLoggedInView() {
        authorizeInteractor.switchToLoginView();
    }

}
