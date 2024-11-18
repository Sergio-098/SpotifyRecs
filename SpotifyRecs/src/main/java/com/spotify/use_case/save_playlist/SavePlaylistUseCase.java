package com.spotify.use_case.save_playlist;

import com.spotify.api.SpotifyClient;
import com.spotify.entity.Playlist;
import com.spotify.entity.Song;
import com.spotify.entity.User;
import com.spotify.repositories.PlaylistRepository;

import java.io.IOException;
import java.util.List;

public class SavePlaylistUseCase {
    private final SpotifyClient spotifyClient;
    private final PlaylistRepository playlistRepository;

    public SavePlaylistUseCase(SpotifyClient spotifyClient, PlaylistRepository playlistRepository) {
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
