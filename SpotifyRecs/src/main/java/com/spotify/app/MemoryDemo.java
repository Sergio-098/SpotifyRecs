package com.spotify.app;

import javax.swing.JFrame;
import java.io.IOException;

/**
 * The Main class of our application.
 */
public class MemoryDemo {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) throws IOException {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addLoginView()
                .addSignupView()
                .addLoggedInView()
                .addLogoutUseCase()
                .addSignupUseCase()
                .addLoginUseCase()
                .addChangePasswordUseCase()
                .build();

        application.pack();
        application.setVisible(true);
    }
}