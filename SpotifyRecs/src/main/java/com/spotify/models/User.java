package com.spotify.models;

import com.spotify.models.Collection;

public class User {
    private String userId;
    private String username;
    private Collection collection;

    // Constructor
    public User(String userId, String username, Collection collection) {
        this.userId = userId;
        this.username = username;
        this.collection = collection;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection getCollection() {
        return collection;
    }

    public void addPlaylist(Playlist playlist) {
        collection.addPlaylist(playlist);
    }
}