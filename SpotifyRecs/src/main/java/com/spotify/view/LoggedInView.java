package com.spotify.view;

import com.spotify.Constants;
import com.spotify.api.SpotifyClient;
import com.spotify.entity.User;
import com.spotify.interface_adapter.generate.GenerateController;
import com.spotify.interface_adapter.generate.LoggedInViewModel;
import com.spotify.entity.Song;
import com.spotify.entity.User;
import com.spotify.use_case.generate.GeneratePlaylistSongs;
import org.apache.hc.core5.http.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoggedInView extends JPanel {
    private final LoggedInViewModel viewModel;
    private SpotifyClient spotify;
    private List<String> artistCriteria;  // List to store artists
    private List<String> genreCriteria;   // List to store genres
    private List<String> songCriteria;    // List to store songs
    private List<String> playlistCriteria; // List to store playlists
    private User user;

    public LoggedInView(LoggedInViewModel viewModel, SpotifyClient spotify) throws IOException, ParseException {
        // Initialize the lists

        this.viewModel = viewModel;
        this.artistCriteria = new ArrayList<>();
        this.genreCriteria = new ArrayList<>();
        this.songCriteria = new ArrayList<>();
        this.playlistCriteria = new ArrayList<>();

        // Set layout and background
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);

        // Title label
        JLabel label = new JLabel("Welcome to Spotify Recs", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.WHITE);
        this.add(label, BorderLayout.NORTH);

        // Center panel for options
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setOpaque(false);

        JLabel optionsTitle = new JLabel("Please Select an Option", SwingConstants.CENTER);
        optionsTitle.setFont(new Font("Arial", Font.BOLD, 20));
        optionsTitle.setForeground(Color.WHITE);
        optionsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(optionsTitle);
        optionsPanel.add(Box.createVerticalStrut(20)); // Add spacing below the title

        // Button for artists option
        JButton artistButton = new JButton("Option 1: Artists");
        artistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        artistButton.setBackground(Color.DARK_GRAY);
        artistButton.setForeground(Constants.Primary_Green);
        artistButton.setFocusPainted(false);
        artistButton.setFont(new Font("Arial", Font.PLAIN, 16));
        artistButton.addActionListener(e -> showTextSelectionDialog("Artist Selection", artistCriteria, spotify));
        optionsPanel.add(artistButton);
        optionsPanel.add(Box.createVerticalStrut(10)); // Add spacing between buttons

        // Button for genres option
        List<String> genres = spotify.getGenres();
        JButton genreButton = new JButton("Option 2: Genres");
        genreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        genreButton.setBackground(Color.DARK_GRAY);
        genreButton.setForeground(Constants.Primary_Green);
        genreButton.setFocusPainted(false);
        genreButton.setFont(new Font("Arial", Font.PLAIN, 16));
        genreButton.addActionListener(e -> showGenreSelectionDialog(genres, genreCriteria));
        optionsPanel.add(genreButton);
        optionsPanel.add(Box.createVerticalStrut(10));

        //Button for Song Selection option
        JButton songButton = new JButton("Option 3: Songs");
        songButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        songButton.setBackground(Color.DARK_GRAY);
        songButton.setForeground(Constants.Primary_Green);
        songButton.setFocusPainted(false);
        songButton.setFont(new Font("Arial", Font.PLAIN, 16));
        songButton.addActionListener(e -> showTextSelectionDialog("Song Selection", songCriteria, spotify));
        optionsPanel.add(songButton);
        optionsPanel.add(Box.createVerticalStrut(10));


        this.add(optionsPanel, BorderLayout.CENTER);

        JButton generateButton = new JButton("Generate Playlist");
        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateButton.setBackground(Color.DARK_GRAY);
        generateButton.setForeground(Constants.Primary_Green);
        generateButton.setFocusPainted(false);
        generateButton.setFont(new Font("Arial", Font.PLAIN, 20));
        GeneratePlaylistSongs songReccomendation = new GeneratePlaylistSongs(spotify);
        generateButton.addActionListener(e -> {
            try {
                showRecommendationDialog(songReccomendation.execute(artistCriteria, genreCriteria, songCriteria));
            } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
        this.add(generateButton, BorderLayout.SOUTH);
    }

    // Method to show the selection dialog for artists
    private void showTextSelectionDialog(String title, List<String> criteria, SpotifyClient spotify) {
        JFrame newFrame = new JFrame(title);
        newFrame.setSize(400, 600);
        newFrame.getContentPane().setBackground(Color.BLACK);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        JLabel label = new JLabel("Select Your Preferred Choice", SwingConstants.CENTER);
        CreateLabel(mainPanel, label);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);
        list.setBackground(Color.DARK_GRAY);
        list.setForeground(Color.WHITE);
        list.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setOpaque(false);

        JTextField inputField = new JTextField(20);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setToolTipText("Enter " + "Selection".toLowerCase());
        JButton addButton = new JButton("Add");
        addButton.setForeground(Constants.Primary_Green);
        addButton.setBackground(Color.DARK_GRAY);
        addButton.setFocusPainted(false);

        inputPanel.add(inputField);
        inputPanel.add(addButton);
        mainPanel.add(inputPanel);

        // Add functionality to the Add button
        addButton.addActionListener(e -> {
            String itemName = inputField.getText().trim();
            if (!itemName.isEmpty()) {
                listModel.addElement(itemName);
                if (title.equals("Song Selection")) {
                    try {
                        criteria.add(spotify.getSearchSong(itemName));
                    } catch (IOException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    try {
                        criteria.add(spotify.getSearchArtist(itemName));
                    } catch (IOException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                inputField.setText("");
            } else {
                JOptionPane.showMessageDialog(newFrame, "Selection" + " name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add Close button
        JButton closeButton = new JButton("Close");
        closeButton(closeButton, newFrame, mainPanel);
    }

    private void CreateLabel(JPanel mainPanel, JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(label);
        mainPanel.add(Box.createVerticalStrut(10));

        DefaultListModel<String> listModel = new DefaultListModel<>();
    }

    private void showGenreSelectionDialog(List<String> genres, List<String> selectedGenres) {
        // Create a new JFrame to display the genre selection dialog
        JFrame newFrame = new JFrame("Genre Selection");
        newFrame.setSize(400, 600);
        newFrame.getContentPane().setBackground(Color.BLACK);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel to hold the components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        // Label for the dialog
        JLabel label = new JLabel("Select Your Genres", SwingConstants.CENTER);
        CreateLabel(mainPanel, label);
        JList<String> list = getStringJList(genres);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalStrut(10)); // Spacer

        // Button to confirm the selection
        JButton doneButton = new JButton("Done");
        doneButton.setBackground(Color.DARK_GRAY);
        doneButton.setForeground(Constants.Primary_Green);
        doneButton.setFocusPainted(false);
        doneButton.addActionListener(e -> {
            // Get the selected genres
            List<String> selectedValues = list.getSelectedValuesList();
            if (!selectedValues.isEmpty()) {
                selectedGenres.addAll(selectedValues); // Add selected genres to the list
                JOptionPane.showMessageDialog(newFrame, "You selected: " + selectedValues, "Selection", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(newFrame, "No genres selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            newFrame.dispose();  // Close the frame when done
        });
        mainPanel.add(doneButton);

        // Button to close the dialog without making a selection
        JButton closeButton = new JButton("Cancel");
        closeButton(closeButton, newFrame, mainPanel);
    }

    private static JList<String> getStringJList(List<String> genres) {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (String genre : genres) {
            listModel.addElement(genre);
        }

        // Create the JList with multiple selection enabled
        JList<String> list = new JList<>(listModel);
        list.setBackground(Color.DARK_GRAY);
        list.setForeground(Color.WHITE);
        list.setFont(new Font("Arial", Font.PLAIN, 16));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Allow multiple selections
        return list;
    }

    private static void closeButton(JButton closeButton, JFrame newFrame, JPanel mainPanel) {
        closeButton.setBackground(Color.DARK_GRAY);
        closeButton.setForeground(Constants.Primary_Green);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> newFrame.dispose());  // Close the dialog without saving selections
        mainPanel.add(Box.createVerticalStrut(10));  // Spacer
        mainPanel.add(closeButton);

        // Add the panel to the frame and display it
        newFrame.add(mainPanel);
        newFrame.setVisible(true);
    }


    // Method to show recommendations (e.g., based on selected artists)
    private void showRecommendationDialog(List<Song> recommendations) {
        // Create a new JFrame for the recommendations
        JFrame newFrame = new JFrame("Recommendations");
        newFrame.setSize(400, 600);
        newFrame.getContentPane().setBackground(Color.BLACK);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel to hold components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        // Title label
        JLabel titleLabel = new JLabel("Your Recommendations", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10)); // Spacer

        // Populate the JList with the given recommendations
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Song recommendation : recommendations) {
            listModel.addElement(recommendation.getName());
        }

        // Create the JList to display recommendations
        JList<String> recommendationList = new JList<>(listModel);
        recommendationList.setBackground(Color.DARK_GRAY);
        recommendationList.setForeground(Color.WHITE);
        recommendationList.setFont(new Font("Arial", Font.PLAIN, 16));
        recommendationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Optional
        JScrollPane scrollPane = new JScrollPane(recommendationList);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        mainPanel.add(scrollPane);

        // Close button to exit the recommendation view
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(Color.DARK_GRAY);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> newFrame.dispose()); // Close the dialog
        mainPanel.add(Box.createVerticalStrut(10)); // Spacer
        mainPanel.add(closeButton);

        // Add the panel to the frame and display it
        newFrame.add(mainPanel);
        newFrame.setVisible(true);
    }

    // Method to display the list of selected artists
    private void showArtistList() {
        if (artistCriteria.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No artists selected yet.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder message = new StringBuilder("Selected Artists:\n");
            for (String artist : artistCriteria) {
                message.append("- ").append(artist).append("\n");
            }
            JOptionPane.showMessageDialog(this, message.toString(), "Artists", JOptionPane.INFORMATION_MESSAGE);

            // Debug print: print list of selected artists
            System.out.println("Debug: Artist list button clicked. Current selected artists:");
            for (String artist : artistCriteria) {
                System.out.println(artist); // Print each artist to the console
            }
        }
    }

    // Getter for the artistCriteria list
    public List<String> getArtistCriteria() {
        return artistCriteria;
    }

    // Getter for the genreCriteria list
    public List<String> getGenreCriteria() {
        return genreCriteria;
    }

    // Getter for the songCriteria list
    public List<String> getSongCriteria() {
        return songCriteria;
    }

    // Getter for the playlistCriteria list
    public List<String> getPlaylistCriteria() {
        return playlistCriteria;
        this.add(label, BorderLayout.CENTER);
        spotify = new SpotifyClient("a54ea954b9fe41408a55d3a577126fa1", "http://localhost:8080/callback");
        if(spotify.getAccessToken() != null) {
            JLabel newLabel = new JLabel("Authorization Worked!!!!", SwingConstants.CENTER);
            newLabel.setFont(new Font("Arial", Font.BOLD, 30));
            this.add(newLabel, BorderLayout.SOUTH);
        }
    }

    public void setPlaylistController(GenerateController controller) {
    }

    public String getViewName() {
        return "logged in";
    }
}
