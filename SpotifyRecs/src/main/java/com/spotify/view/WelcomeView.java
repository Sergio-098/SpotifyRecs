package com.spotify.view;

import com.spotify.interface_adapter.authorize.WelcomeViewModel;
import com.spotify.interface_adapter.authorize.AuthorizeViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class WelcomeView extends JPanel implements PropertyChangeListener {

    private final String viewName = "welcome";
    private final WelcomeViewModel welcomeViewModel;

    private final JLabel welcomeLabel;
    private final JButton getAuthorizedButton;


    public WelcomeView(WelcomeViewModel welcomeViewModel ) {
        this.welcomeViewModel = welcomeViewModel;
        welcomeViewModel.addPropertyChangeListener(this);

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
        // should be implemented by authorize controller
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.setContentPane(new AuthorizationView(new AuthorizeViewModel()));
        parentFrame.revalidate();
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("SpotifyRecs");
            WelcomeView welcomeView = new WelcomeView(new WelcomeViewModel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 800);
            frame.add(welcomeView);
            frame.setVisible(true);
        });
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
