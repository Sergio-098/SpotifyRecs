package com.spotify.use_case.add_song;

import com.spotify.api.SpotifyClient;
import com.spotify.entity.RecommendationCriteriaFactory;
import com.spotify.entity.RecommendationCriteria;
import com.spotify.entity.Song;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class AddSongUseCase {
    private final SpotifyClient spotifyClient;

    public AddSongUseCase(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public List<Song> execute(List<String> artists, List<String> genres, List<String> tracks, int num) throws IOException, ParseException {
        // Creating recommendation criteria based on the seed values
        RecommendationCriteriaFactory rcFactory = new RecommendationCriteriaFactory();
        RecommendationCriteria recCriteria = rcFactory.createRecCrit(artists, genres, tracks);

        // Get the recommended songs from Spotify and add them to the list
        return spotifyClient.getRecommendations(recCriteria, num);
    }
}