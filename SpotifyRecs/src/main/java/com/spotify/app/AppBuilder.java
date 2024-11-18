package com.spotify.app;

import com.spotify.entity.PlaylistFactory;
import com.spotify.entity.SongFactory;
import com.spotify.data_access.FileUserDataAccessObject;
import com.spotify.entity.UserFactory;
import com.spotify.interface_adapter.ViewManagerModel;
import com.spotify.interface_adapter.logged_in.LoggedInViewModel;
import com.spotify.interface_adapter.login.LoginController;
import com.spotify.interface_adapter.login.LoginPresenter;
import com.spotify.interface_adapter.login.LoginViewModel;

import com.spotify.use_case.login.LoginInputBoundary;
import com.spotify.use_case.login.LoginInteractor;
import com.spotify.use_case.login.LoginOutputBoundary;
import com.spotify.view.LoggedInView;
import com.spotify.view.LoginView;
import com.spotify.view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new UserFactory();
    private final PlaylistFactory playlistFactory = new PlaylistFactory();
    private final SongFactory songFactory = new SongFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private final FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("user_data.csv",
            "playlist_data.csv","song_data.csv", userFactory, playlistFactory, songFactory);

    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;

    public AppBuilder() throws IOException {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }


    /**
     * Creates the JFrame for the application and initially sets the LoginView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(loginView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
