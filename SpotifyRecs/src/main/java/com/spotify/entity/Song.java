package com.spotify.entity;

import java.util.List;

public class Song {
    private String songId;
    private String name;
    private List <String> artists;

    // Constructor
    public Song(String songId, String name, List<String> artists) {
        this.songId = songId;
        this.name = name;
        this.setArtists(artists);
    }

    // Getters and Setters
    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }
}
