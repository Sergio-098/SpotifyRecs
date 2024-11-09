package com.spotify.factory;

import com.spotify.models.User;
import com.spotify.util.UserInfoParser;
import org.json.JSONObject;

import java.util.Map;

public class UserFactory {
    public User createUser(JSONObject json) {
        UserInfoParser userParse = new UserInfoParser();
        Map<String, String> userInfo = userParse.parse(json);
        return new User(userInfo.get("id"), userInfo.get("username"));
    }
}
