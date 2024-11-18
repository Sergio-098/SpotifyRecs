package com.spotify.repositories;

import com.spotify.entity.PlaylistFactory;
import com.spotify.entity.SongFactory;
import com.spotify.entity.UserFactory;
import com.spotify.data_access.FileUserDataAccessObject;
import com.spotify.entity.Playlist;
import com.spotify.entity.User;

import java.io.IOException;

public class FilePlaylistRepository implements PlaylistRepository {

    @Override
    public void save(Playlist playlist, User user) throws IOException {
        UserFactory uf = new UserFactory();
        PlaylistFactory pf = new PlaylistFactory();
        SongFactory sf = new SongFactory();
        FileUserDataAccessObject fileData = new FileUserDataAccessObject("user_data.csv",
                "playlist_data.csv", "song_data.csv", uf, pf, sf);
        user.addPlaylist(playlist);
        fileData.savePlaylist();
    }
}
