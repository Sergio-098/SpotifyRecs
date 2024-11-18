package com.spotify.entity;

public class User {
    private String userId;
    private String username;
    private String password;
    private Collection collection;

    // Constructor
    public User(String userId, String username, String password, Collection collection) {
        this.userId = userId;
        this.username = username;
        this.password = password;
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
        this.collection.addPlaylist(playlist);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}