package com.spotify.entity;

import java.util.List;

public class PlaylistFactory {
    public Playlist createPlaylist(String id, String name, String description,
                                   boolean isPublic, List<Song> songs) {
        return new Playlist(id, name, description, isPublic, songs);
    }
}
