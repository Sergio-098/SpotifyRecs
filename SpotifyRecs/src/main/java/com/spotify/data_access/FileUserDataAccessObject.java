package com.spotify.data_access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.spotify.entity.Playlist;
import com.spotify.entity.Song;
import com.spotify.entity.User;
import com.spotify.entity.UserFactory;
import com.spotify.entity.PlaylistFactory;
import com.spotify.entity.SongFactory;
import com.spotify.use_case.login.LoginUserDataAccessInterface;

//This is going to interact with the CSV files (basically Excel spreadsheets) and
//save all the information we need to them so that they don't get lost. It will
//also be responsible for repopulating all the objects when we start the program
//and loading them in so that we can pick right up where we left off between sessions.
//It does this by taking all the data in the CSVs and creating the objects again.
//Never edit the CSVs manually.

public class FileUserDataAccessObject implements LoginUserDataAccessInterface{

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

    //This loads all the data back into out program so that it has all the user's
    //information from previous sessions. It is used heavily in the app builder class
    //under the app package. It is the same as lab 5.

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

    //Saves all info
    private void save() throws IOException {
        saveUsers();
        savePlaylists();
        saveSongs();
    }

    //saves user info
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

    //saves playlist info
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

    //saves song info
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

    public void addSongToPlaylist(String playlistId, Song newSong) throws IOException {
        // Load the playlist from CSV
        Playlist playlist = loadPlaylistById(playlistId);

        // Add the new song to the playlist's song list
        playlist.getSongs().add(newSong);

        // Save the updated playlist back to the CSV
        saveUpdatedPlaylist(playlist);
    }

    public void savePlaylist() throws IOException {
        save();
    }

    public void removeSongFromPlaylist(String playlistId, String songId) throws IOException {
        // Load the playlist from CSV
        Playlist playlist = loadPlaylistById(playlistId);

        // Remove the song from the playlist's song list
        playlist.getSongs().removeIf(song -> song.getSongId().equals(songId));

        // Save the updated playlist back to the CSV
        saveUpdatedPlaylist(playlist);
    }

    public Playlist loadPlaylistById(String playlistId) throws IOException {
        // Read the CSV file and find the playlist with the given ID
        Playlist playlist;
        try (BufferedReader reader = new BufferedReader(new FileReader("playlist_data.csv"))) {
            String line;
            playlist = null;

            while ((line = reader.readLine()) != null) {
                // Assuming CSV structure like: playlistId, playlistName, description, isPublic, songIds
                String[] data = line.split(",");

                if (data[0].equals(playlistId)) {
                    String playlistName = data[1];
                    String description = data[2];
                    boolean isPublic = Boolean.parseBoolean(data[3]);
                    List<Song> songs = parseSongs(data[4]); // Parse the song IDs from the CSV

                    playlist = new Playlist(playlistId, playlistName, description, isPublic, songs);
                    break;
                }
            }

        }
        return playlist;
    }

    private void saveUpdatedPlaylist(Playlist playlist) throws IOException {
        // Write the updated playlist back to the CSV file
        try (FileWriter writer = new FileWriter("playlist_data.csv", true)) {

            // Write the playlist's details in CSV format (adjust according to your CSV structure)
            String playlistData = playlist.getPlaylistId() + "," +
                    playlist.getName() + "," +
                    playlist.getDescription() + "," +
                    playlist.isPublic() + "," +
                    formatSongs(playlist.getSongs()) + "\n";

            writer.write(playlistData);
        } // Use append mode if needed
    }

    private String formatSongs(List<Song> songs) {
        // Convert the list of songs to a CSV-compatible format (e.g., comma-separated song IDs)
        StringBuilder songIds = new StringBuilder();
        for (Song song : songs) {
            songIds.append(song.getSongId()).append(",");
        }
        return songIds.toString();
    }

    private List<Song> parseSongs(String songData) {
        List<Song> songs = new ArrayList<>();

        String[] songEntries = songData.split(",");

        for (String songEntry : songEntries) {
            String[] songAttributes = songEntry.split("\\|");

            if (songAttributes.length == 3) {
                String songId = songAttributes[0];
                String songName = songAttributes[1];
                List<String> artists = parseArtists(songAttributes[2]); // Parse the artists from a comma-separated string

                songs.add(new Song(songId, songName, artists)); // Create the Song object with the correct attributes
            }
        }
        return songs;
    }


    private List<String> parseArtists(String artistsData) {
        // Split the artist string by commas and return as a list
        return new ArrayList<>(Arrays.asList(artistsData.split(",")));
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

}
