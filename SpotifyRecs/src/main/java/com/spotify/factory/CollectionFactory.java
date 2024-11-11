package com.spotify.factory;

import com.spotify.models.Collection;
import com.spotify.models.Playlist;

import java.util.List;

public class CollectionFactory {
    public Collection createCollection(List<Playlist> playlists) {
        return new Collection(playlists);
    }
}
