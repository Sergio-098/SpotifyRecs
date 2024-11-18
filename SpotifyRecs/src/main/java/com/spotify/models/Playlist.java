package com.spotify.models;

import java.util.List;

public class Playlist {
    private String playlistId;
    private String userId;
    private String name;
    private String description;
    private boolean isPublic;
    private List<Song> songs;

    // Constructor
    public Playlist(String playlistId, String name, String description, boolean isPublic, List<Song> songs, String user) {
        this.playlistId = playlistId;
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
        this.songs = songs;
        this.userId = user;
    }

    // Getters and Setters
    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void addSongs(List<Song> songs) {
        this.songs.addAll(songs);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

