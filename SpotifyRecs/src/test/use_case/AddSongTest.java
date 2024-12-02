package use_case;

import com.spotify.api.SpotifyClient;
import com.spotify.entity.Song;
import com.spotify.use_case.add_song.AddSongInteractor;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddSongTest{

    private AddSongInteractor addSongInteractor;
    private SpotifyClient spotifyClient;

    @BeforeEach
    public void setup() {
        // Initialize the Spotify client (You can replace this with your actual SpotifyClient constructor)
        String clientID = "a54ea954b9fe41408a55d3a577126fa1";
        String redirect = "http://localhost:8080/callback";
        spotifyClient = new SpotifyClient(clientID, redirect);

        // Initialize the AddSongInteractor use case
        addSongInteractor = new AddSongInteractor(spotifyClient);
    }

    @Test
    public void testAddSongsWithValidSeedData() throws IOException, ParseException {
        // Given: valid seed data for artists, genres, and tracks
        List<String> artists = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        List<String> tracks = new ArrayList<>();

        // Add some sample data (replace with actual Spotify IDs or valid test data)
        artists.add("1vCWHaC5f2uS3yLP6A3r0T"); // Example artist ID
        genres.add("rock");
        tracks.add("3n3PzaYXNJSm49j5mnDw57"); // Example track ID

        // When: adding songs to the playlist
        Integer numberOfSongsToAdd = 5; // Example number of songs to add
        List<Song> addedSongs = addSongInteractor.execute(artists, genres, tracks, numberOfSongsToAdd);

        // Then: The added songs list should not be null and should contain the correct number of songs
        assertNotNull(addedSongs, "Added songs list should not be null");
        assertTrue(addedSongs.size() == numberOfSongsToAdd, "Added songs list should contain the correct number of songs");

        // Optionally, check that each song has a name and artist(s)
        for (Song song : addedSongs) {
            assertNotNull(song.getName(), "Song name should not be null");
            assertNotNull(song.getArtists(), "Song artists should not be null");
            assertTrue(song.getArtists().getFirst().length() > 0, "Song should have an artist name");
        }
    }

    @Test
    public void testAddSongsWithEmptySeedData() throws IOException, ParseException {
        // Given: empty seed data for artists, genres, and tracks
        List<String> artists = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        List<String> tracks = new ArrayList<>();

        // When: trying to add songs with empty seed data
        Integer numberOfSongsToAdd = 5;
        List<Song> addedSongs = addSongInteractor.execute(artists, genres, tracks, numberOfSongsToAdd);

        // Then: The added songs list should be empty
        assertNotNull(addedSongs, "Added songs list should not be null");
        assertTrue(addedSongs.isEmpty(), "Added songs list should be empty when no seed data is provided");
    }

    @Test
    public void testAddSongsWithInvalidSeedData() throws IOException, ParseException {
        // Given: invalid seed data (non-existent artist, genre, and track)
        List<String> artists = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        List<String> tracks = new ArrayList<>();

        // Add some invalid data
        artists.add("invalid_artist_id");
        genres.add("invalid_genre");
        tracks.add("invalid_track_id");

        // When: trying to add songs with invalid data
        Integer numberOfSongsToAdd = 5;
        List<Song> addedSongs = addSongInteractor.execute(artists, genres, tracks, numberOfSongsToAdd);

        // Then: The result should either be empty or an error, depending on the implementation
        assertNotNull(addedSongs, "Added songs list should not be null");
        assertTrue(addedSongs.isEmpty(), "Added songs list should be empty with invalid seed data");
    }

    @Test
    public void testAddSongsWithZeroCount() throws IOException, ParseException {
        // Given: valid seed data
        List<String> artists = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        List<String> tracks = new ArrayList<>();

        // Add valid sample data
        artists.add("1vCWHaC5f2uS3yLP6A3r0T"); // Example artist ID
        genres.add("rock");
        tracks.add("3n3PzaYXNJSm49j5mnDw57"); // Example track ID

        // When: attempting to add zero songs
        Integer numberOfSongsToAdd = 0;
        List<Song> addedSongs = addSongInteractor.execute(artists, genres, tracks, numberOfSongsToAdd);

        // Then: The added songs list should be empty
        assertNotNull(addedSongs, "Added songs list should not be null");
        assertTrue(addedSongs.isEmpty(), "Added songs list should be empty when no songs are requested");
    }

    @Test
    public void testAddSongsWithExcessiveNumber() throws IOException, ParseException {
        // Given: valid seed data
        List<String> artists = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        List<String> tracks = new ArrayList<>();

        // Add valid sample data
        artists.add("1vCWHaC5f2uS3yLP6A3r0T"); // Example artist ID
        genres.add("rock");
        tracks.add("3n3PzaYXNJSm49j5mnDw57"); // Example track ID

        // When: attempting to add an excessive number of songs
        Integer numberOfSongsToAdd = 100; // Excessive number of songs
        List<Song> addedSongs = addSongInteractor.execute(artists, genres, tracks, numberOfSongsToAdd);

        // Then: The added songs list should not be null and should contain songs, but might not contain the full requested amount depending on the API
        assertNotNull(addedSongs, "Added songs list should not be null");
        assertTrue(addedSongs.size() > 0, "Added songs list should contain at least one song");
    }
}