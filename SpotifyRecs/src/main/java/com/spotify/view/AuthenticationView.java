package com.spotify.view;

import com.spotify.api.SpotifyClient;
import com.spotify.entity.User;
import com.spotify.repositories.FileUserRepository;
import com.spotify.use_case.authorize.AuthorizeInteractor;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.hc.core5.http.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AuthenticationView extends JPanel {
    private static final String clientId = "a54ea954b9fe41408a55d3a577126fa1";
    private static final String redirect = "http://localhost:8080/callback"; // Redirect URI
    private final String authUrl;
    private FileUserRepository fuRepo;
    private SpotifyClient spotifyClient;

    public AuthenticationView() throws IOException {
        // Build the Spotify authorization URL
        this.spotifyClient = new SpotifyClient(clientId, redirect);
        this.authUrl = spotifyClient.getAuthorizationUrl();
        this.fuRepo = new FileUserRepository();

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
                        } catch (IOException | ParseException e) {
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

    private void handleAuthorizationCode(String code) throws IOException, ParseException {
        // Handle the authorization code (e.g., transition to next view)
        AuthorizeInteractor auth = new AuthorizeInteractor(spotifyClient);
        auth.execute(code);
        // Transition to the next view, e.g., LoggedInView
        User user = spotifyClient.getCurrentUser();
        fuRepo.save(user);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.setContentPane(new LoggedInView(spotifyClient, user));

        parentFrame.revalidate();
    }
}
