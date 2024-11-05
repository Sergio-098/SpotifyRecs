package com.spotify.util;

import org.json.JSONObject;

public class SongIDParser implements JSONParser<String> {
    //Method for getting Song ID from get reccomendations response
    @Override
    public String parse(JSONObject json) {
        return json.getJSONArray("tracks").getJSONObject(0).getString("id");
    }
}

