package com.spotify.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SongNameParser implements JSONParser<List<String>> {
    //method to parse song names from get recommendations json response
    @Override
    public List<String> parse(JSONObject jsonResponse) {
        List<String> songNames = new ArrayList<>();

        JSONArray tracksArray = jsonResponse.getJSONArray("tracks");
        for (int i = 0; i < tracksArray.length(); i++) {
            JSONObject track = tracksArray.getJSONObject(i);
            songNames.add(track.getString("name")); // Collect each track's name
        }
        return songNames;
    }
}
