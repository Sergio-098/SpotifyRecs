package com.spotify.util;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ArtistNameParser implements JSONParser<List<List<String>>> {
    @Override
    public List<List<String>> parse(JSONObject json) {

        List<List<String>> artistNames = new ArrayList<>();
        JSONArray tracksArray = json.getJSONArray("tracks");

        for (int i = 0; i < tracksArray.length(); i++) {
            JSONArray artistsArray = tracksArray.getJSONObject(i).getJSONArray("artists");
            List<String> tempNames = new ArrayList<>();
            for (int j = 0; j < artistsArray.length(); j++) {
               tempNames.add(artistsArray.getJSONObject(j).getString("name"));
            }
            artistNames.add(tempNames);
        }
        return artistNames;
    }
}
