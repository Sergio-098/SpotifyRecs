package com.spotify.interface_adapter.search;

import com.spotify.use_case.search.SearchInputBoundary;
import com.spotify.use_case.search.SearchInputData;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

/**
 * The controller for the Search Use Case.
 */
public class SearchController {
    private final SearchInputBoundary searchInteractor;

    public SearchController(SearchInputBoundary searchInteractor) {

        this.searchInteractor = searchInteractor;
    }

    /**
     * Executes the Search Use Case.
     */
    public void execute(String query, String type) throws IOException, ParseException {
        final SearchInputData searchInputData = new SearchInputData(query, type);
        searchInteractor.execute(searchInputData);
    }
}
