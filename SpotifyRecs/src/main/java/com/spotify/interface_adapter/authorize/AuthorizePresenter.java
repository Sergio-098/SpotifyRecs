package com.spotify.interface_adapter.authorize;

import com.spotify.interface_adapter.ViewManagerModel;
import com.spotify.use_case.authorize.AuthorizeOutputBoundary;
import com.spotify.use_case.authorize.AuthorizeOutputData;


/**
* The Presenter for the Login Use Case.
*/
public class AuthorizePresenter implements AuthorizeOutputBoundary {

    private final WelcomeViewModel welcomeViewModel;
    private final ViewManagerModel viewManagerModel;


    public AuthorizePresenter(ViewManagerModel viewManagerModel, WelcomeViewModel welcomeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.welcomeViewModel = welcomeViewModel;
    }

    @Override
    public void prepareSuccessView(AuthorizeOutputData outputData) {
        // On success, switch to the welcome view.
        final WelcomeState welcomeState = welcomeViewModel.getState();
        // show welcome <username> on switching to welcome view
        welcomeState.setUsername(outputData.getUsername());
        this.welcomeViewModel.setState(welcomeState);
        welcomeViewModel.firePropertyChanged();

        viewManagerModel.setState(welcomeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToWelcomeView() {
        viewManagerModel.setState(welcomeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }



}
