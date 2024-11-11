package com.spotify.memory.data_access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.spotify.memory.logout.LogoutUserDataAccessInterface;
import com.spotify.models.Playlist;
import com.spotify.models.Song;
import com.spotify.models.User;
import com.spotify.factory.UserFactory;
import com.spotify.factory.PlaylistFactory;
import com.spotify.factory.SongFactory;
import com.spotify.memory.change_password.ChangePasswordUserDataAccessInterface;
import com.spotify.memory.login.LoginUserDataAccessInterface;
import com.spotify.memory.signup.SignupUserDataAccessInterface;

public class FileUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface {

    private static final String USER_HEADER = "username,password,userId";
    private static final String PLAYLIST_HEADER = "userId,playlistId,name,description,isPublic";
    private static final String SONG_HEADER = "playlistId,songId,name,artists";

    private final File userCsvFile;
    private final File playlistCsvFile;
    private final File songCsvFile;
    private final Map<String, Integer> userHeaders = new LinkedHashMap<>();
    private final Map<String, Integer> playlistHeaders = new LinkedHashMap<>();
    private final Map<String, Integer> songHeaders = new LinkedHashMap<>();
    private final Map<String, User> accounts = new HashMap<>();
    private String currentUsername;

    private final UserFactory userFactory;
    private final PlaylistFactory playlistFactory;
    private final SongFactory songFactory;

    public FileUserDataAccessObject(String userCsvPath, String playlistCsvPath, String songCsvPath,
                                    UserFactory userFactory, PlaylistFactory playlistFactory, SongFactory songFactory) throws IOException {
        this.userCsvFile = new File(userCsvPath);
        this.playlistCsvFile = new File(playlistCsvPath);
        this.songCsvFile = new File(songCsvPath);

        this.userFactory = userFactory;
        this.playlistFactory = playlistFactory;
        this.songFactory = songFactory;

        // Initialize headers
        userHeaders.put("username", 0);
        userHeaders.put("password", 1);
        userHeaders.put("userId", 2);

        playlistHeaders.put("userId", 0);
        playlistHeaders.put("playlistId", 1);
        playlistHeaders.put("name", 2);
        playlistHeaders.put("description", 3);
        playlistHeaders.put("isPublic", 4);

        songHeaders.put("playlistId", 0);
        songHeaders.put("songId", 1);
        songHeaders.put("name", 2);
        songHeaders.put("artists", 3);

        load();
    }

    private void load() throws IOException {
        // Load users
        if (userCsvFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(userCsvFile))) {
                reader.readLine(); // skip header
                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",");
                    final String username = col[userHeaders.get("username")];
                    final String password = col[userHeaders.get("password")];
                    final String userId = col[userHeaders.get("userId")];
                    User user = userFactory.initUser(username, password);
                    user.setUserId(userId);
                    accounts.put(username, user);
                }
            }
        }

        // Load playlists and associate them with users
        if (playlistCsvFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(playlistCsvFile))) {
                reader.readLine(); // skip header
                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",");
                    final String userId = col[playlistHeaders.get("userId")];
                    final String playlistId = col[playlistHeaders.get("playlistId")];
                    final String name = col[playlistHeaders.get("name")];
                    final String description = col[playlistHeaders.get("description")];
                    final boolean isPublic = Boolean.parseBoolean(col[playlistHeaders.get("isPublic")]);

                    List<Song> songs = new ArrayList<>();
                    Playlist playlist = playlistFactory.createPlaylist(name, playlistId, description, isPublic, songs);

                    // Find the user and add the playlist
                    for (User user : accounts.values()) {
                        if (user.getUserId().equals(userId)) {
                            user.getCollection().addPlaylist(playlist);
                            break;
                        }
                    }
                }
            }
        }

        // Load songs and associate them with playlists
        if (songCsvFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(songCsvFile))) {
                reader.readLine(); // skip header
                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",");
                    final String playlistId = col[songHeaders.get("playlistId")];
                    final String songId = col[songHeaders.get("songId")];
                    final String name = col[songHeaders.get("name")];
                    final String artists = col[songHeaders.get("artists")];
                    List<String> artistList = List.of(artists.split("\\|"));

                    Song song = new Song(songId, name, artistList);

                    // Find the playlist and add the song
                    for (User user : accounts.values()) {
                        for (Playlist playlist : user.getCollection().getPlaylists()) {
                            if (playlist.getPlaylistId().equals(playlistId)) {
                                playlist.getSongs().add(song);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void save() throws IOException {
        saveUsers();
        savePlaylists();
        saveSongs();
    }

    private void saveUsers() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userCsvFile))) {
            writer.write(USER_HEADER);
            writer.newLine();

            for (User user : accounts.values()) {
                final String line = String.format("%s,%s,%s",
                        user.getUsername(), user.getPassword(), user.getUserId());
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private void savePlaylists() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(playlistCsvFile))) {
            writer.write(PLAYLIST_HEADER);
            writer.newLine();

            for (User user : accounts.values()) {
                for (Playlist playlist : user.getCollection().getPlaylists()) {
                    final String line = String.format("%s,%s,%s,%s,%b",
                            user.getUserId(), playlist.getPlaylistId(), playlist.getName(),
                            playlist.getDescription(), playlist.isPublic());
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }

    private void saveSongs() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(songCsvFile))) {
            writer.write(SONG_HEADER);
            writer.newLine();

            for (User user : accounts.values()) {
                for (Playlist playlist : user.getCollection().getPlaylists()) {
                    for (Song song : playlist.getSongs()) {
                        final String artistNames = String.join("|", song.getArtists());
                        final String line = String.format("%s,%s,%s,%s",
                                playlist.getPlaylistId(), song.getSongId(), song.getName(), artistNames);
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        }
    }

    @Override
    public void save(User user) throws IOException {
        accounts.put(user.getUsername(), user);
        save();
    }

    @Override
    public User get(String username) {
        return accounts.get(username);
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }

    @Override
    public void changePassword(User user) throws IOException {
        accounts.put(user.getUsername(), user);
        save();
    }
}
