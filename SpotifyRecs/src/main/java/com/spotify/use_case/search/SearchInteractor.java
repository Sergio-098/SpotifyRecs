package com.spotify.use_case.search;

import com.spotify.api.SpotifyClient;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class SearchInteractor implements SearchInputBoundary {

    private final SpotifyClient spotifyClient;

    public SearchInteractor(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }
    @Override
    public void execute(SearchInputData searchInputData) throws IOException, ParseException {
        List<String> result;
        if (searchInputData.getType().equals("song")) {
            result = spotifyClient.getSongQuery(searchInputData.getQuery());
        }
        else {
            result = spotifyClient.getArtistQuery(searchInputData.getQuery());
        }
        final SearchOutputData searchOutputData = new SearchOutputData(result);

    }
}
