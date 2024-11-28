package com.spotify.use_case.generate;

import com.spotify.Constants;
import com.spotify.api.SpotifyClient;
import com.spotify.entity.RecommendationCriteriaFactory;
import com.spotify.entity.RecommendationCriteria;
import com.spotify.entity.Song;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class GeneratePlaylistSongs {
    private final SpotifyClient spotifyClient;

    public GeneratePlaylistSongs(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public List<Song> execute(List<String> artistsIds, List<String> genreIds, List<String> songsIds) throws IOException, ParseException {
        RecommendationCriteriaFactory recommendationCriteriaFactory = new RecommendationCriteriaFactory();
        RecommendationCriteria recommendationCriteria = recommendationCriteriaFactory.createRecCrit(artistsIds, genreIds, songsIds);
        return spotifyClient.getRecommendations(recommendationCriteria, Constants.SongTotal);
    }
}
