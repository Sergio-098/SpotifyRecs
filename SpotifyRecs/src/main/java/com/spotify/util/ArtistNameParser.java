package com.spotify.util;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ArtistNameParser implements JSONParser<List<String>> {
    @Override
    public List<String> parse(JSONObject json) {
        List<String> artistNames = new ArrayList<>();
        JSONArray artistsArray = json.getJSONArray("tracks").getJSONObject(0).getJSONArray("artists");

        for (int i = 0; i < artistsArray.length(); i++) {
            artistNames.add(artistsArray.getJSONObject(i).getString("name"));
        }
        return artistNames;
    }
}
