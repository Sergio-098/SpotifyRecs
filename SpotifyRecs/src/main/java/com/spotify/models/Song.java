package com.spotify.models;

public class Song {
    private String songId;
    private String name;
    private String artist;

    // Constructor
    public Song(String songId, String name, String artist) {
        this.songId = songId;
        this.name = name;
        this.artist = artist;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
