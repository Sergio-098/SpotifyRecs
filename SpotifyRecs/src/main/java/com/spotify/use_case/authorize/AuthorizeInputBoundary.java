package com.spotify.use_case.authorize;


import java.io.IOException;

public interface AuthorizeInputBoundary {


    /**
     * Executes the save playlist use case.
     * @param authorizeInputData the input for authorize use case
     */
    boolean execute(AuthorizeInputData authorizeInputData) throws IOException;


    void switchToLoggedInView();
}
