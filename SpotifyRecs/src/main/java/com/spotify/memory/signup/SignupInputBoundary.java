package com.spotify.memory.signup;

import java.io.IOException;

/**
 * Input Boundary for actions which are related to signing up.
 */
public interface SignupInputBoundary {

    /**
     * Executes the signup use case.
     * @param signupInputData the input data
     */
    void execute(SignupInputData signupInputData) throws IOException;

    /**
     * Executes the switch to login view use case.
     */
    void switchToLoginView();
}
