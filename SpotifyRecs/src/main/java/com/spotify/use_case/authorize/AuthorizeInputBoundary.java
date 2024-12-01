package com.spotify.use_case.authorize;


import java.io.IOException;

public interface AuthorizeInputBoundary {

    /**
     * Executes the save playlist use case.
     * @param code the access code given
     */
    boolean execute(String code) throws IOException;
}
