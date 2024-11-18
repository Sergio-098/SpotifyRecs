package com.spotify.entity;

import java.util.List;

public class Collection {
    final private List<Playlist> playlists;

    // Constructor
    public Collection(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    // Method to add a playlist to the collection
    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
    }

    // Method to remove a playlist from the collection
    public boolean removePlaylist(String playlistId) {
        return playlists.removeIf(playlist -> playlist.getPlaylistId().equals(playlistId));
    }

    public List<Playlist> getPlaylists() {
        return playlists;
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