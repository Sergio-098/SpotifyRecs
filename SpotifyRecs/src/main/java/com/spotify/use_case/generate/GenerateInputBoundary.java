package com.spotify.use_case.generate;

import com.spotify.entity.Song;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public interface GenerateInputBoundary {


        /**
         * Executes the Generate Playlist use case.
         *
         * @param generateInputData the input data
         * @return
         */
        List<Song> execute(GenerateInputData generateInputData) throws IOException, ParseException;


}


