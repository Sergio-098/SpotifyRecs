package com.spotify.view;

import com.spotify.api.SpotifyClient;
import com.spotify.interface_adapter.authorize.AuthorizeController;
import com.spotify.interface_adapter.authorize.AuthorizeViewModel;
import com.spotify.interface_adapter.generate.LoggedInViewModel;
import com.spotify.use_case.authorize.AuthorizeUseCase;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * The View for when the user is logging into the program.
 */
public class AuthorizationView extends JPanel {
    private final String viewName = "authorization";
    private final AuthorizeViewModel authorizeViewModel;

    private static final String clientId = "a54ea954b9fe41408a55d3a577126fa1";
    private static final String redirect = "http://localhost:8080/callback"; // Redirect URI
    private final String authUrl;
    private final SpotifyClient spotifyClient;

    private AuthorizeController authorizeController;

    public AuthorizationView(AuthorizeViewModel authorizeViewModel) throws IOException {
        this.authorizeViewModel = authorizeViewModel;
        // Build the Spotify authorization URL
        this.spotifyClient = new SpotifyClient(clientId, redirect);
        this.authUrl = spotifyClient.getAuthorizationUrl();

        // Set layout
        this.setLayout(new BorderLayout());

        // Embed JavaFX WebView
        JFXPanel jfxPanel = new JFXPanel();
        Platform.runLater(() -> createWebView(jfxPanel));

        this.add(jfxPanel, BorderLayout.CENTER);
    }

    private void createWebView(JFXPanel jfxPanel) {
        // Create a WebView and load the Spotify authentication page
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Monitor URL changes to detect redirect URI
        webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.startsWith(redirect)) {
                // Extract authorization code from the URL
                String code = extractAuthorizationCode(newValue);
                if (code != null) {
                    SwingUtilities.invokeLater(() -> {
                        try {
                            handleAuthorizationCode(code);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        });

        webEngine.load(authUrl);

        // Add WebView to the JavaFX scene
        BorderPane root = new BorderPane(webView);
        Scene scene = new Scene(root, 1000, 800);
        jfxPanel.setScene(scene);
    }

    private String extractAuthorizationCode(String url) {
        // Parse the URL to extract the 'code' query parameter
        if (url.contains("code=")) {
            String[] parts = url.split("code=");
            return parts[1].split("&")[0]; // Extract the code before any '&'
        }
        return null;
    }

    private void handleAuthorizationCode(String code) throws IOException {
        // Handle the authorization code (e.g., transition to next view)
        AuthorizeUseCase auth = new AuthorizeUseCase(spotifyClient);
        auth.execute(code);
        // Transition to the next view, e.g., LoggedInView
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.setContentPane(new LoggedInView(new LoggedInViewModel()));

        parentFrame.revalidate();
    }

    public void setAuthorizeController(AuthorizeController controller) {
        this.authorizeController = controller;
    }

    public String getViewName() {
      return viewName;
    }
}
