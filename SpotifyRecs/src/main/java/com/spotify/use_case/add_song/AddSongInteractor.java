package com.spotify.use_case.add_song;


import com.spotify.api.SpotifyClient;
import com.spotify.entity.RecommendationCriteriaFactory;
import com.spotify.entity.RecommendationCriteria;
import com.spotify.entity.Song;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class AddSongInteractor {

    private final SpotifyClient spotifyClient;

    public AddSongInteractor(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public List<Song> execute(List<String> artists, List<String> genres, List<String> tracks, int num) throws IOException, ParseException {
        RecommendationCriteriaFactory rcFactory = new RecommendationCriteriaFactory();
        RecommendationCriteria recCriteria = rcFactory.createRecCrit(artists, genres, tracks);
        return spotifyClient.getRecommendations(recCriteria, num);
    }
}



