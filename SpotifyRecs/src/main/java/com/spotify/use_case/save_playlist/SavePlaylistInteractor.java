package com.spotify.use_case.save_playlist;

import com.spotify.api.SpotifyClient;
import com.spotify.data_access.FileUserDataAccessObject;
import com.spotify.entity.Playlist;
import com.spotify.entity.Song;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class SavePlaylistInteractor implements SavePlaylistInputBoundary{
    private final SavePlaylistOutputBoundary userPresenter;
    private final SpotifyClient spotifyClient;
    private final List<Song> songs;
    private final FileUserDataAccessObject userDAO;

    public SavePlaylistInteractor(SavePlaylistOutputBoundary savePlaylistOutputBoundary, SpotifyClient spotifyClient,
                                  List<Song> songs, FileUserDataAccessObject userDAO) {

        this.userPresenter = savePlaylistOutputBoundary;
        this.spotifyClient = spotifyClient;
        this.songs = songs;
        this.userDAO = userDAO;

    }

    @Override
    public void execute(SavePlaylistInputData savePlaylistInputData) throws IOException, ParseException {
        // Create Spotify playlist and add songs
        Playlist spotifyPlaylist = spotifyClient.createPlaylist(
                spotifyClient.getCurrentUser(),
                savePlaylistInputData.getName(),
                savePlaylistInputData.getDescription(),
                savePlaylistInputData.getIsPublic());

        spotifyClient.addSongsToPlaylist(spotifyPlaylist, songs);

        // Save to local Collection
        spotifyClient.getCurrentUser().addPlaylist(spotifyPlaylist);
        userDAO.save(spotifyClient.getCurrentUser());
    }

    @Override
    public void switchToLoggedInView() {
        userPresenter.switchToLoggedInView();
    }
}
