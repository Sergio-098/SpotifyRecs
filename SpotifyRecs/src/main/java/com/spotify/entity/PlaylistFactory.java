package com.spotify.entity;

import java.util.List;

public class PlaylistFactory {
    public static Playlist createPlaylist(String id, String name, String description,
                                          boolean isPublic, List<Song> songs, User user) {
        return new Playlist(id, name, description, isPublic, songs, user.getUserId());
    }
}
