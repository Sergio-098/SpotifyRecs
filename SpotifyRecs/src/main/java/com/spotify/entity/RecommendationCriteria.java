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

    public List<String> getGenreIds() {
        return genreIds;
    }

    public List<String> getTrackIds() {
        return trackIds;
    }
}



