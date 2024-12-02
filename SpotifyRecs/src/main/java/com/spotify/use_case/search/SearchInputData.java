package com.spotify.use_case.search;

public class SearchInputData {
    private String query;
    private String type;

    public SearchInputData(String query, String type) {
        this.query = query;
        this.type = type;
    }

    public String getQuery() {
        return query;
    }

    public String getType() {
        return type;
    }

}
