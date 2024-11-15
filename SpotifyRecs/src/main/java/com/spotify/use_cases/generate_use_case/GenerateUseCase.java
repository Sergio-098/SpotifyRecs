package com.spotify.use_cases.generate_use_case;

import com.spotify.api.SpotifyClient;
import com.spotify.factory.RecCritFactory;
import com.spotify.models.RecommendationCriteria;
import com.spotify.models.Song;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class GenerateUseCase {
    private final SpotifyClient spotifyClient;

    public GenerateUseCase(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public List<Song> execute(List<String> artists, List<String> genre, List<String> songs) throws IOException, ParseException {
        RecCritFactory rf = new RecCritFactory();
        RecommendationCriteria recCrit = rf.createRecCrit(artists, genre, songs);
        return spotifyClient.getRecommendations(recCrit, 20);
    }
}
