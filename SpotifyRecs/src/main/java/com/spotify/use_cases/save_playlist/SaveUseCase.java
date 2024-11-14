package com.spotify.use_cases.save_playlist;

import com.spotify.api.SpotifyClient;
import com.spotify.models.Playlist;
import com.spotify.models.Song;
import com.spotify.models.User;
import com.spotify.repositories.PlaylistRepository;

import java.io.IOException;
import java.util.List;

public class SaveUseCase {
    private final SpotifyClient spotifyClient;
    private final PlaylistRepository playlistRepository;

    public SaveUseCase(SpotifyClient spotifyClient, PlaylistRepository playlistRepository) {
        this.spotifyClient = spotifyClient;
        this.playlistRepository = playlistRepository;
    }

    public Playlist execute(String playlistName, String description,List<Song> songs, boolean isPublic, User user) throws IOException {
        // Create Spotify playlist and add songs
        Playlist spotifyPlaylist = spotifyClient.createPlaylist(user, playlistName, description, isPublic);
        spotifyClient.addSongsToPlaylist(spotifyPlaylist, songs);
        // Save to local Collection
        playlistRepository.save(spotifyPlaylist, user);

        return spotifyPlaylist;
    }
}
