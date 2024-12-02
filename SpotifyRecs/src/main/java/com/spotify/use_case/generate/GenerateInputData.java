package com.spotify.use_case.generate;

import com.spotify.api.SpotifyClient;
import java.util.List;

public class GenerateInputData {

    private final List<String> artistsIds;
    private final List<String> genresIds;
    private final List<String> songsIds;
    private final SpotifyClient spotifyClient;

    public GenerateInputData(List<String> artistsIds, List<String> genresIds, List<String> sondsIds, SpotifyClient spotifyClient) {
        this.artistsIds = artistsIds;
        this.genresIds = genresIds;
        this.songsIds = sondsIds;
        this.spotifyClient = spotifyClient;
    }

    public List<String> getArtistsIds() {
        return artistsIds;
    }

    public List<String> getGenresIds() {
        return genresIds;
    }

    public List<String> getSongsIds() {
        return songsIds;
    }
}
