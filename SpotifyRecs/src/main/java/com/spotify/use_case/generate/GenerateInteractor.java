package com.spotify.use_case.generate;

import com.spotify.Constants;
import com.spotify.api.SpotifyClient;
import com.spotify.data_access.FileUserDataAccessObject;
import com.spotify.entity.RecommendationCriteriaFactory;
import com.spotify.entity.Song;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateInteractor implements GenerateInputBoundary{

    private final GenerateOutputBoundary userPresenter;
    private final SpotifyClient spotifyClient;
    private final FileUserDataAccessObject userDAO;

    public GenerateInteractor(FileUserDataAccessObject userDataAccessObject,
                              GenerateOutputBoundary generateOutputBoundary, SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
        this.userDAO = userDataAccessObject;
        this.userPresenter = generateOutputBoundary;
    }

    @Override
    public List<Song> execute(GenerateInputData generateInputData) throws IOException, ParseException {
            List<Song> recommendations = spotifyClient.getRecommendations(RecommendationCriteriaFactory.
                    createRecCrit(generateInputData.getArtistsIds(), generateInputData.getGenresIds(),
                            generateInputData.getSongsIds()), Constants.SongLimit);
        GenerateOutputData generateOutputData = new GenerateOutputData(recommendations);
            return recommendations;
    }

}

