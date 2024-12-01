package com.spotify.interface_adapter.save_playlist;

import com.spotify.interface_adapter.ViewManagerModel;
import com.spotify.interface_adapter.ViewModel;
import com.spotify.interface_adapter.generate.LoggedInViewModel;
import com.spotify.use_case.save_playlist.SavePlaylistOutputBoundary;
import com.spotify.use_case.save_playlist.SavePlaylistOutputData;


public class SavePlaylistPresenter implements SavePlaylistOutputBoundary {

    private final SavePlaylistViewModel saveViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ViewModel viewModel;

    public SavePlaylistPresenter(ViewManagerModel viewManagerModel,
                           SavePlaylistViewModel saveViewModel, ViewModel viewModel) {
        this.viewManagerModel = viewManagerModel;
        this.saveViewModel = saveViewModel;
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SavePlaylistOutputData response) {
//        // On success, switch to the next view.
//        final EndState endState = viewModel.getState();
//        endState.setName(response.getName());
//        this.viewModel.setState(endState);
//        viewModel.firePropertyChanged();
//
//        viewManagerModel.setState(viewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final SavePlaylistState saveState = saveViewModel.getState();
        saveState.setUsernameError(error);
        saveViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoggedInView() {
        viewManagerModel.setState(viewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
