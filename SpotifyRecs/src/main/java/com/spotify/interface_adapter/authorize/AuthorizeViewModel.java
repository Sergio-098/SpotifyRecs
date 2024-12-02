package com.spotify.interface_adapter.authorize;

import com.spotify.interface_adapter.ViewModel;


/**
 * The View Model for the Authorize View.
 */
public class AuthorizeViewModel extends ViewModel<AuthorizeState> {

    public AuthorizeViewModel() {
        super("authorize");
        setState(new AuthorizeState());
    }

}
