package com.spotify.models;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    private List<Playlist> playlists;

    // Constructor
    public Collection() {
        this.playlists = new ArrayList<>();
    }

    // Method to add a playlist to the collection
    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
    }

    // Method to remove a playlist from the collection
    public boolean removePlaylist(String playlistId) {
        return playlists.removeIf(playlist -> playlist.getPlaylistId().equals(playlistId));
    }

    // Method to get a playlist by its ID
    public Playlist getPlaylist(String playlistId) {
        for (Playlist playlist : playlists) {
            if (playlist.getPlaylistId().equals(playlistId)) {
                return playlist;
            }
        }
        return null;
    }
}