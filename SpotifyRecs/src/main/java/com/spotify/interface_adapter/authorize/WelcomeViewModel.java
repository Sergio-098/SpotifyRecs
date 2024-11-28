package com.spotify.interface_adapter.authorize;

import com.spotify.interface_adapter.ViewModel;

public class WelcomeViewModel extends ViewModel<WelcomeState> {
    public WelcomeViewModel() {
        super("welcome");
        setState(new WelcomeState());
    }
}
