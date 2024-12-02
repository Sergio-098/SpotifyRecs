package com.spotify.use_case.authorize;

public interface AuthorizeOutputBoundary {


    /**
     * Prepares the success view for the Authorize Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(AuthorizeOutputData outputData);


    /**
     * Switches to the Welcome View.
     */
    void switchToLoggedInView();

    void switchToLoginView();

    void switchToWelcomeView();
}
