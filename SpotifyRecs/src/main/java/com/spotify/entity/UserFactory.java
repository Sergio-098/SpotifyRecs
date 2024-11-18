package com.spotify.entity;

import com.spotify.util.UserInfoParser;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserFactory {
    public User createUser(JSONObject json) {
        UserInfoParser userParse = new UserInfoParser();
        Map<String, String> userInfo = userParse.parse(json);
        List<Playlist> playlists = new ArrayList<>();
        Collection emptyCollection = new Collection(playlists);
        return new User(userInfo.get("id"), userInfo.get("username"),
                null, emptyCollection);
    }
    public User initUser(String username, String password) {
        List<Playlist> playlists = new ArrayList<>();
        Collection emptyCollection = new Collection(playlists);
        return new User(null, username, password, emptyCollection);
    }
}
