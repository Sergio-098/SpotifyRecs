package com.spotify.factory;

import com.spotify.models.Playlist;
import com.spotify.models.Song;
import com.spotify.models.User;

import java.util.ArrayList;
import java.util.List;

public class PlaylistFactory {
    public Playlist createPlaylist(String id, String name, String description,
                                   boolean isPublic, List<Song> songs, User user) {
        return new Playlist(id, name, description, isPublic, songs, user.getUserId());
    }
}
