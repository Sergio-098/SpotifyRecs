package com.spotify.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private String username;
    private List<Playlist> collection;
    private Sting favourite;

    // Constructor
    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
        this.collection = new ArrayList<>();
        this.favourite = "";
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

    public List<Playlist> getCollection() {
        return collection;
    }

    public void addPlaylist(Playlist playlist) {
        this.collection.add(playlist);
    }

    public String getFavourite() {return this.favourite;}
}
