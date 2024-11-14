package com.spotify.repositories;

import com.spotify.models.Playlist;
import com.spotify.models.User;

import java.io.IOException;

public interface PlaylistRepository {
    void save(Playlist playlist, User user) throws IOException;
}

