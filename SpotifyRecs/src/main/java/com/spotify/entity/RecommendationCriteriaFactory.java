package com.spotify.entity;

import java.util.List;

public class RecommendationCriteriaFactory {
    public static RecommendationCriteria createRecCrit(List<String> artists, List<String> genres, List<String> tracks) {
        return new RecommendationCriteria(artists, genres, tracks);
    }
}
