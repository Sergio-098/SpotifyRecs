package com.spotify.entity;

import java.util.List;

public class CollectionFactory {
    public Collection createCollection(List<Playlist> playlists) {
        return new Collection(playlists);
    }
}
