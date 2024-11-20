package com.spotify.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

public class WelcomeView extends JPanel {

    final private JLabel welcomeLabel;
    final private JButton getAuthorizedButton;

    public WelcomeView() {
        // Set the background color
        this.setBackground(new Color(30, 40, 45));
        this.setLayout(null); // Keep null layout for absolute positioning

        // Create and style the welcome label
        welcomeLabel = new JLabel("Welcome to SpotifyRecs", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 80));
        welcomeLabel.setForeground(new Color(0, 150, 0));
        this.add(welcomeLabel);

        // Create and style the button
        getAuthorizedButton = new JButton("Get Authorized");
        getAuthorizedButton.setFont(new Font("Arial", Font.BOLD, 25));
        getAuthorizedButton.setBackground(new Color(0, 150, 0));
        getAuthorizedButton.setFocusPainted(false);
        getAuthorizedButton.setBorderPainted(false);
        getAuthorizedButton.setOpaque(true);
        getAuthorizedButton.addActionListener(e -> {
            try {
                openAuthenticationView();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        this.add(getAuthorizedButton);

        // Add a resize listener to adjust positions dynamically
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
        });

        // Initial positioning
        resizeComponents();
    }

    private void resizeComponents() {
        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();

        // Adjust welcome label
        int welcomeLabelWidth = panelWidth / 2;
        int welcomeLabelHeight = panelHeight / 10;
        int welcomeLabelX = (panelWidth - welcomeLabelWidth) / 2;
        int welcomeLabelY = panelHeight / 3;
        welcomeLabel.setBounds(welcomeLabelX, welcomeLabelY, welcomeLabelWidth, welcomeLabelHeight);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, panelHeight / 20)); // Adjust font size dynamically

        // Adjust button
        int buttonWidth = panelWidth / 3;
        int buttonHeight = panelHeight / 12;
        int buttonX = (panelWidth - buttonWidth) / 2;
        int buttonY = welcomeLabelY + welcomeLabelHeight + panelHeight / 20;
        getAuthorizedButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        getAuthorizedButton.setFont(new Font("Arial", Font.BOLD, panelHeight / 25)); // Adjust font size dynamically
    }

    private void openAuthenticationView() throws IOException {
        // Switch to Authentication View
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.setContentPane(new AuthenticationView());
        parentFrame.revalidate();
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("SpotifyRecs");
            WelcomeView welcomeView = new WelcomeView();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 800);
            frame.add(welcomeView);
            frame.setVisible(true);
        });
    }
}
