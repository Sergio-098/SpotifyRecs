package com.spotify.util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserInfoParser implements JSONParser<Map<String, String>> {

    public Map<String, String> parse(JSONObject jsonResponse) {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("id", jsonResponse.getString("id"));
        userInfo.put("username", jsonResponse.getString("display_name"));
        return userInfo;
    }
}
