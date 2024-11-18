package com.spotify.repositories;

import com.spotify.entity.Playlist;
import com.spotify.entity.User;

import java.io.IOException;

public interface PlaylistRepository {
    void save(Playlist playlist, User user) throws IOException;
}

