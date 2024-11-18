package com.spotify.use_case.generate;

import com.spotify.api.SpotifyClient;
import com.spotify.entity.RecommendationCriteriaFactory;
import com.spotify.entity.RecommendationCriteria;
import com.spotify.entity.Song;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class GenerateUseCase {
    private final SpotifyClient spotifyClient;

    public GenerateUseCase(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public List<Song> execute(List<String> artists, List<String> genre, List<String> songs) throws IOException, ParseException {
        RecommendationCriteriaFactory rf = new RecommendationCriteriaFactory();
        RecommendationCriteria recCrit = rf.createRecCrit(artists, genre, songs);
        return spotifyClient.getRecommendations(recCrit, 20);
    }
}
