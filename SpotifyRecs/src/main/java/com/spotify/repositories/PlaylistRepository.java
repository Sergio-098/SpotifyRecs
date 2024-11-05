package com.spotify.repositories;

import com.spotify.models.Playlist;
import com.spotify.models.User;

public class PlaylistRepository {
    private User user;
    private Playlist playlist;

    public PlaylistRepository(Playlist playlist, User user) {
        this.setPlaylist(playlist);
        this.setUser(user);
    }
    public void savePlaylist() {
        getUser().addPlaylist(getPlaylist());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
