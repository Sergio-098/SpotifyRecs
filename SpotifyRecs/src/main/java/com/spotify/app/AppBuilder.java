package com.spotify.app;

import com.spotify.factory.PlaylistFactory;
import com.spotify.factory.SongFactory;
import com.spotify.memory.data_access.FileUserDataAccessObject;
import com.spotify.factory.UserFactory;
import com.spotify.ui.interface_adapter.ViewManagerModel;
import com.spotify.ui.interface_adapter.change_password.ChangePasswordController;
import com.spotify.ui.interface_adapter.change_password.ChangePasswordPresenter;
import com.spotify.ui.interface_adapter.change_password.LoggedInViewModel;
import com.spotify.ui.interface_adapter.login.LoginController;
import com.spotify.ui.interface_adapter.login.LoginPresenter;
import com.spotify.ui.interface_adapter.login.LoginViewModel;
import com.spotify.ui.interface_adapter.logout.LogoutController;
import com.spotify.ui.interface_adapter.logout.LogoutPresenter;
import com.spotify.ui.interface_adapter.signup.SignupController;
import com.spotify.ui.interface_adapter.signup.SignupPresenter;
import com.spotify.ui.interface_adapter.signup.SignupViewModel;
import com.spotify.memory.change_password.ChangePasswordInputBoundary;
import com.spotify.memory.change_password.ChangePasswordInteractor;
import com.spotify.memory.change_password.ChangePasswordOutputBoundary;
import com.spotify.memory.login.LoginInputBoundary;
import com.spotify.memory.login.LoginInteractor;
import com.spotify.memory.login.LoginOutputBoundary;
import com.spotify.memory.logout.LogoutInputBoundary;
import com.spotify.memory.logout.LogoutInteractor;
import com.spotify.memory.logout.LogoutOutputBoundary;
import com.spotify.memory.signup.SignupInputBoundary;
import com.spotify.memory.signup.SignupInteractor;
import com.spotify.memory.signup.SignupOutputBoundary;
import com.spotify.ui.views.LoggedInViewUI;
import com.spotify.ui.views.LoginViewerUI;
import com.spotify.ui.views.SignupView;
import com.spotify.ui.view_manager.ViewManager;

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

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInViewUI loggedInView;
    private LoginViewerUI loginView;

    public AppBuilder() throws IOException {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginViewerUI(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInViewUI(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
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
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
