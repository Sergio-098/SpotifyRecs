package com.spotify.use_case.save_playlist;

import com.spotify.api.SpotifyClient;
import com.spotify.data_access.FileUserDataAccessObject;
import com.spotify.entity.Playlist;
import com.spotify.entity.Song;
import com.spotify.entity.User;

import java.io.IOException;
import java.util.List;

public class MockSavePlaylistInteractor {
    private final SpotifyClient spotifyClient;
    private final FileUserDataAccessObject fileUserDAO;

    public MockSavePlaylistInteractor(SpotifyClient spotifyClient, FileUserDataAccessObject fileUserDAO) {
        this.spotifyClient = spotifyClient;
        this.fileUserDAO = fileUserDAO;
    }

    public Playlist execute(String playlistName, String description,List<Song> songs, boolean isPublic, User user) throws IOException {
        // Create Spotify playlist and add songs
        Playlist spotifyPlaylist = spotifyClient.createPlaylist(user, playlistName, description, isPublic);
        spotifyClient.addSongsToPlaylist(spotifyPlaylist, songs);
        // Save to local Collection
        user.addPlaylist(spotifyPlaylist);
        fileUserDAO.save(user);

        return spotifyPlaylist;
    }
}
