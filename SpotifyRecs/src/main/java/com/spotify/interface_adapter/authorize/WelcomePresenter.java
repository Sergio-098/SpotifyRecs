package com.spotify.interface_adapter.authorize;

import com.spotify.interface_adapter.ViewManagerModel;

public class WelcomePresenter {

    private final AuthorizeViewModel authorizeViewModel;
    private final ViewManagerModel viewManagerModel;

    public WelcomePresenter(AuthorizeViewModel authorizeViewModel, ViewManagerModel viewManagerModel) {
        this.authorizeViewModel = authorizeViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void switchToAuthorizeView() {
        viewManagerModel.setState(authorizeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
