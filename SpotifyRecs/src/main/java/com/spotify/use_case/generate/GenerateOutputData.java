package com.spotify.use_case.generate;

import com.spotify.entity.Song;

import java.util.List;

public class GenerateOutputData {
    public static List<Song> recommendations = List.of();

    public GenerateOutputData(List<Song> recommendations) {
        GenerateOutputData.recommendations = recommendations;
    }

    public static List<Song> getRecommendations() {
        return recommendations;
    }
}
