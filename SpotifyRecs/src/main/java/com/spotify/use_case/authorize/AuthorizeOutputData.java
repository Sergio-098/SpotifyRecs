package com.spotify.use_case.authorize;

public class AuthorizeOutputData {
    private final String username;


    public AuthorizeOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
