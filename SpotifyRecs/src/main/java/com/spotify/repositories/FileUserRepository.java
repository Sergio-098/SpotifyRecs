package com.spotify.repositories;

import com.spotify.factory.PlaylistFactory;
import com.spotify.factory.SongFactory;
import com.spotify.factory.UserFactory;
import com.spotify.memory.data_access.FileUserDataAccessObject;
import com.spotify.models.User;

import java.io.IOException;

public class FileUserRepository {
    public void save(User user) throws IOException {
        UserFactory uf = new UserFactory();
        PlaylistFactory pf = new PlaylistFactory();
        SongFactory sf = new SongFactory();
        FileUserDataAccessObject fileData = new FileUserDataAccessObject("user_data.csv",
                "playlist_data.csv", "song_data.csv", uf, pf, sf);
        fileData.save(user);
    }
}
