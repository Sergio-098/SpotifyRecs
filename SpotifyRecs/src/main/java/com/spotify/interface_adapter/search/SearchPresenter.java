package com.spotify.interface_adapter.search;

import com.spotify.use_case.search.SearchOutputBoundary;
import com.spotify.use_case.search.SearchOutputData;

import java.util.List;

public class SearchPresenter implements SearchOutputBoundary {
    @Override
    public void present(SearchOutputData searchOutputData) {
        List<String> result = searchOutputData.getResults();

    }

}
