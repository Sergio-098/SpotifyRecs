package com.spotify.use_case.search;

import java.util.List;

public class SearchOutputData {

    private final List<String> results;

    public SearchOutputData(List<String> results) {
        this.results = results;
    }

    public List<String> getResults() {
        return results;
    }
}
