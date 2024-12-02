package com.spotify.app;

import com.spotify.api.SpotifyClient;
import com.spotify.data_access.FileUserDataAccessObject;
import com.spotify.entity.UserFactory;
import com.spotify.interface_adapter.ViewManagerModel;
import com.spotify.interface_adapter.authorize.*;
import com.spotify.interface_adapter.generate.GenerateController;
import com.spotify.interface_adapter.generate.GeneratePresenter;
import com.spotify.interface_adapter.generate.LoggedInViewModel;
import com.spotify.interface_adapter.save_playlist.*;
import com.spotify.use_case.authorize.AuthorizeInteractor;
import com.spotify.use_case.authorize.AuthorizeOutputBoundary;
import com.spotify.use_case.authorize.AuthorizeInputBoundary;
import com.spotify.use_case.generate.*;
import com.spotify.use_case.save_playlist.SavePlaylistInputBoundary;
import com.spotify.use_case.save_playlist.SavePlaylistInteractor;
import com.spotify.use_case.save_playlist.SavePlaylistOutputBoundary;
import com.spotify.view.*;
import org.apache.hc.core5.http.ParseException;

import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * This is done by adding each View and then adding related Use Cases.
*/

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject();
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private AddSongViewModel addSongViewModel;
    private AddSongView addSongView;
    private AuthorizeViewModel authorizeViewModel;
    private AuthorizationView authorizationView;
    private PlaylistViewModel playlistViewModel;
    private PlaylistView playlistView;
    private SavePlaylistView savePlaylistView;
    private SavePlaylistViewModel savePlaylistViewModel;
    private WelcomeViewModel welcomeViewModel;
    private WelcomeView welcomeView;



    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }




    /**
     * Adds the Authentication View to the application.
     * @return this builder
     */
     public AppBuilder addAuthorizationView(SpotifyClient spotifyClient) throws IOException {
         authorizeViewModel = new AuthorizeViewModel();
         authorizationView = new AuthorizationView(authorizeViewModel, spotifyClient);
         cardPanel.add(authorizationView, authorizationView.getViewName());
         return this;
     }

    /**
     * Adds the Welcome View to the application.
     * @return this builder
     */
    public AppBuilder addWelcomeView(SpotifyClient spotifyClient) {
        welcomeViewModel = new WelcomeViewModel();
        welcomeView = new WelcomeView(welcomeViewModel, spotifyClient);
        cardPanel.add(welcomeView, welcomeView.getViewName());
        final WelcomeController controller = new WelcomeController(new WelcomePresenter(authorizeViewModel, viewManagerModel));
        welcomeView.setWelcomeController(controller);
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView(SpotifyClient spotifyClient) throws IOException, ParseException {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel, spotifyClient);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Playlist View to the application.
     * @return this builder
     */
    public AppBuilder addPlaylistView() {
        playlistViewModel = new PlaylistViewModel();
        playlistView = new PlaylistView(playlistViewModel);
        cardPanel.add(playlistView, playlistView.getViewName());
        return this;
    }

    /**
     * Adds the SavePlaylist View to the application.
     * @return this builder
     */
    public AppBuilder addSavePlaylistView() {
        savePlaylistViewModel = new SavePlaylistViewModel();
        savePlaylistView = new SavePlaylistView(savePlaylistViewModel);
        cardPanel.add(savePlaylistView, savePlaylistView.getViewName());
        return this;
    }

    /**
     * Adds the AddSong View to the application.
     * @return this builder
     */
    public AppBuilder addAddSongView() {
        addSongViewModel = new AddSongViewModel();
        addSongView = new AddSongView(addSongViewModel);
        cardPanel.add(addSongView, addSongView.getViewName());
        return this;
    }

    /**
     * Adds the Authorize Use Case to the application.
     * @return this builder
     */
    public AppBuilder addAuthorizeUseCase(SpotifyClient spotifyClient) {
        final AuthorizeOutputBoundary authorizeOutputBoundary = new AuthorizePresenter(viewManagerModel, loggedInViewModel);
        final AuthorizeInputBoundary authorizeInteractor = new AuthorizeInteractor(spotifyClient,
                authorizeOutputBoundary, userDataAccessObject);

        final AuthorizeController controller = new AuthorizeController(authorizeInteractor);
        authorizationView.setAuthorizeController(controller);
        return this;
    }

    public AppBuilder addGenerateUseCase(SpotifyClient spotifyClient) {
        final GenerateOutputBoundary generateOutputBoundary = new GeneratePresenter(viewManagerModel,
                loggedInViewModel, playlistViewModel);
        final GenerateInputBoundary generateInteractor = new GenerateInteractor(userDataAccessObject,
                generateOutputBoundary, spotifyClient);

        final GenerateController controller = new GenerateController(generateInteractor);
        loggedInView.setPlaylistController(controller);
        return this;
    }

    public AppBuilder addSavePlaylistUseCase(SpotifyClient spotifyClient) {
        final SavePlaylistOutputBoundary savePlaylistOutputBoundary = new SavePlaylistPresenter(viewManagerModel,
                savePlaylistViewModel,loggedInViewModel);
        final SavePlaylistInputBoundary savePlaylistInteractor = new SavePlaylistInteractor(savePlaylistOutputBoundary,
                spotifyClient, GenerateOutputData.getRecommendations(),userDataAccessObject);
        final SavePlaylistController controller = new SavePlaylistController(savePlaylistInteractor);
        playlistView.setController(controller);
        return this;
    }


    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("SpotifyRecs");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(welcomeView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
