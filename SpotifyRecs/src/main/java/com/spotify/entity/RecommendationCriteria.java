package com.spotify.entity;

import java.util.List;

public class RecommendationCriteria {
    private List<String> artistIds;
    private List<String> genreIds;
    private List<String> trackIds;

    // Constructor
    public RecommendationCriteria(List<String> artistIds, List<String> genreIds, List<String> trackIds) {
        this.artistIds = artistIds;
        this.genreIds = genreIds;
        this.trackIds = trackIds;
    }

    // Getters and Setters
    public List<String> getArtistIds() {
        return artistIds;
    }

    public void setArtistIds(List<String> artistIds) {
        this.artistIds = artistIds;
    }

    public List<String> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<String> genreIds) {
        this.genreIds = genreIds;
    }

    public List<String> getTrackIds() {
        return trackIds;
    }

    public void setTrackIds(List<String> trackIds) {
        this.trackIds = trackIds;
    }
}
