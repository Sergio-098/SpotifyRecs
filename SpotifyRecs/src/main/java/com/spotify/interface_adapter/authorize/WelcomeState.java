package com.spotify.interface_adapter.authorize;

public class WelcomeState {

    private String username = "";

    public WelcomeState(WelcomeState state) {
        username = state.username;

    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public WelcomeState() {
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username){
        this.username = username;
    }
}
