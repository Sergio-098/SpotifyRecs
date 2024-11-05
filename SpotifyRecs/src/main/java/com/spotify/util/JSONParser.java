package com.spotify.util;

import org.json.JSONObject;

public interface JSONParser<T> {
    T parse(JSONObject json);
}
