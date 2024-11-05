package com.spotify.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SongIDParser implements JSONParser<List<String>> {
    //Method for getting Song ID from get reccomendations response
    @Override
    public List<String> parse(JSONObject jsonResponse) {
        List<String> songIDs = new ArrayList<>();

        JSONArray tracksArray = jsonResponse.getJSONArray("tracks");
        for (int i = 0; i < tracksArray.length(); i++) {
            JSONObject track = tracksArray.getJSONObject(i);
            songIDs.add(track.getString("id")); // Collect each track's ID
        }
        return songIDs;
    }
}

