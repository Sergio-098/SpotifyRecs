package com.spotify.factory;

import com.spotify.models.RecommendationCriteria;

import java.util.List;

public class RecCritFactory {
    public RecommendationCriteria createRecCrit(List<String> artists, List<String> genres, List<String> tracks) {
        return new RecommendationCriteria(artists, genres, tracks);
    }
}
