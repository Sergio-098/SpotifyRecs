package com.spotify.view;

import com.spotify.entity.Song;
import com.spotify.interface_adapter.save_playlist.PlaylistViewModel;
import com.spotify.interface_adapter.save_playlist.SavePlaylistController;
import com.spotify.use_case.generate.GenerateOutputData;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class PlaylistView extends JPanel {
        private SavePlaylistController controller;
        private List<Song> playlist;

    public PlaylistView(PlaylistViewModel playlistViewModel) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        this.playlist = GenerateOutputData.getRecommendations();

        JLabel titleLabel = new JLabel("Playlist", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel, BorderLayout.NORTH);

        // Create a panel for the song list
        JPanel songPanel = new JPanel();
        songPanel.setLayout(new BoxLayout(songPanel, BoxLayout.Y_AXIS)); // Vertical list
        songPanel.setOpaque(false);

        // Create a DefaultListModel to hold the song data
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Song song : this.playlist) {
            // Add each song's name to the list model (You can modify to show more details)
            listModel.addElement(song.getName() + " - " + song.getArtists());

            // Create a JList to display the songs
            JList<String> songList = new JList<>(listModel);
            songList.setBackground(Color.DARK_GRAY);
            songList.setForeground(Color.WHITE);
            songList.setFont(new Font("Arial", Font.PLAIN, 16));
            songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(songList);
            scrollPane.setPreferredSize(new Dimension(300, 400));
            songPanel.add(scrollPane);

            // Add the song panel to the main panel
            this.add(songPanel, BorderLayout.CENTER);


            JButton closeButton = new JButton("Close Playlist");
            closeButton.setBackground(Color.DARK_GRAY);
            closeButton.setForeground(Color.GREEN);
            closeButton.setFocusPainted(false);
            closeButton.setFont(new Font("Arial", Font.PLAIN, 16));
            closeButton.addActionListener(e -> {

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.dispose();
            });

            this.add(closeButton, BorderLayout.SOUTH);

        }
    }

    public String getViewName() {
        return "playlist";
    }

    public void setController(SavePlaylistController controller) {
        this.controller = controller;
    }
}
