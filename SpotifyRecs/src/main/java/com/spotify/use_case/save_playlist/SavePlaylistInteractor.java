package com.spotify.use_case.save_playlist;

import com.spotify.api.SpotifyClient;
import com.spotify.entity.Playlist;
import com.spotify.entity.PlaylistFactory;
import com.spotify.entity.Song;
import com.spotify.entity.User;
import com.spotify.repositories.FilePlaylistRepository;
import com.spotify.repositories.PlaylistRepository;

import java.io.IOException;
import java.util.List;

public class SavePlaylistInteractor implements SavePlaylistInputBoundary{
    private final SavePlaylistOutputBoundary userPresenter;
    private final PlaylistFactory playlistFactory;
    private final SpotifyClient spotifyClient;
    private final User user;
    private final List<Song> songs;
    private final PlaylistRepository playlistRepository;

    public SavePlaylistInteractor(SavePlaylistOutputBoundary signupOutputBoundary,
                            PlaylistFactory playlistFactory, SpotifyClient spotifyClient, User user,
                                  List<Song> songs) {

        this.userPresenter = signupOutputBoundary;
        this.playlistFactory = playlistFactory;
        this.spotifyClient = spotifyClient;
        this.user = user;
        this.songs = songs;
        this.playlistRepository = new FilePlaylistRepository();
    }

    @Override
    public void execute(SavePlaylistInputData savePlaylistInputData) throws IOException {
        // Create Spotify playlist and add songs
        Playlist spotifyPlaylist = spotifyClient.createPlaylist(user,
                savePlaylistInputData.getName(),
                savePlaylistInputData.getDescription(),
                savePlaylistInputData.getIsPublic());

        spotifyClient.addSongsToPlaylist(spotifyPlaylist, songs);

        // Save to local Collection
        playlistRepository.save(spotifyPlaylist, user);
    }

    @Override
    public void switchToEndView() {
        userPresenter.switchToEndView();
    }
}
