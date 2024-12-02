package com.spotify.interface_adapter.authorize;

import com.spotify.interface_adapter.ViewManagerModel;
import com.spotify.interface_adapter.generate.LoggedInState;
import com.spotify.interface_adapter.generate.LoggedInViewModel;
import com.spotify.use_case.authorize.AuthorizeOutputBoundary;
import com.spotify.use_case.authorize.AuthorizeOutputData;


/**
* The Presenter for the Login Use Case.
*/
public class AuthorizePresenter implements AuthorizeOutputBoundary {

    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;


    public AuthorizePresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(AuthorizeOutputData outputData) {
        // On success, switch to the welcome view.
        final LoggedInState loginState = loggedInViewModel.getState();
        this.loggedInViewModel.setState(loginState);
        loggedInViewModel.firePropertyChanged();

        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }


    @Override
    public void switchToLoggedInView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
