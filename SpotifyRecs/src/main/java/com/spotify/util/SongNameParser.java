package com.spotify.util;

import org.json.JSONObject;

public class SongNameParser implements JSONParser<String> {
    //method to parse song names from get recommendations json response
    @Override
    public String parse(JSONObject json) {
        return json.getJSONArray("tracks").getJSONObject(0).getString("name");
    }
}
