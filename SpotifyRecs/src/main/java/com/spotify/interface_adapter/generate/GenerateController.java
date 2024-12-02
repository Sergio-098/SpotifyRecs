package com.spotify.interface_adapter.generate;

import com.spotify.api.SpotifyClient;
import com.spotify.use_case.authorize.AuthorizeInputData;
import com.spotify.use_case.generate.GenerateInputBoundary;
import com.spotify.use_case.generate.GenerateInputData;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class GenerateController {

    private final GenerateInputBoundary generateInteractor;

    public GenerateController(GenerateInputBoundary generateInteractor) {
        this.generateInteractor = generateInteractor;
    }

    /**
     * Executes the Authorize Use Case.
     */
    public void execute(List<String> artistsIds, List<String> genresIds, List<String> sondsIds, SpotifyClient spotifyClient) throws IOException, ParseException {
        final GenerateInputData generateInputData = new GenerateInputData(artistsIds, genresIds, sondsIds, spotifyClient);
        generateInteractor.execute(generateInputData);
    }

}
