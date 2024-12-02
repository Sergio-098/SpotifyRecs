package use_case;

import com.spotify.api.SpotifyClient;
import com.spotify.entity.Song;
import com.spotify.use_case.generate.GeneratePlaylistSongs;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenerateTest {

    private GeneratePlaylistSongs generatePlaylistSongs;
    private SpotifyClient spotifyClient;

    @BeforeEach
    public void setup() {
        // Initialize the Spotify client (You can replace this with your actual SpotifyClient constructor)
        String clientID = "a54ea954b9fe41408a55d3a577126fa1";
        String redirect = "http://localhost:8080/callback";
        spotifyClient = new SpotifyClient(clientID, redirect);

        // Initialize the GeneratePlaylistSongs use case
        generatePlaylistSongs = new GeneratePlaylistSongs(spotifyClient);
    }

    @Test
    public void testGeneratePlaylistWithValidSeedData() throws IOException, ParseException {
        // Given: valid seed data for artists, genres, and tracks
        List<String> artists = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        List<String> tracks = new ArrayList<>();

        // Add some sample data (replace with actual Spotify IDs or valid test data)
        artists.add("1vCWHaC5f2uS3yLP6A3r0T"); // Example artist ID
        genres.add("rock");
        tracks.add("3n3PzaYXNJSm49j5mnDw57"); // Example track ID

        // When: generating playlist songs
        List<Song> songs = generatePlaylistSongs.execute(artists, genres, tracks);

        // Then: The songs list should not be null and should contain at least one song
        assertNotNull(songs, "Generated playlist should not be null");
        assertTrue(songs.size() > 0, "Generated playlist should contain at least one song");

        // Optionally, you can also check that each song has a name and artist(s)
        for (Song song : songs) {
            assertNotNull(song.getName(), "Song name should not be null");
            assertNotNull(song.getArtists(), "Song artists should not be null");
            assertTrue(song.getArtists().getFirst().length() > 0, "Song should have an artist name");
        }
    }

    @Test
    public void testGeneratePlaylistWithEmptySeedData() throws IOException, ParseException {
        // Given: empty seed data for artists, genres, and tracks
        List<String> artists = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        List<String> tracks = new ArrayList<>();

        // When: generating playlist songs
        List<Song> songs = generatePlaylistSongs.execute(artists, genres, tracks);

        // Then: The songs list should be empty
        assertNotNull(songs, "Generated playlist should not be null");
        assertTrue(songs.isEmpty(), "Generated playlist should be empty when no seed data is provided");
    }

    @Test
    public void testGeneratePlaylistWithInvalidSeedData() throws IOException, ParseException {
        // Given: invalid seed data (non-existent artist, genre, and track)
        List<String> artists = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        List<String> tracks = new ArrayList<>();

        // Add some invalid data
        artists.add("invalid_artist_id");
        genres.add("invalid_genre");
        tracks.add("invalid_track_id");

        // When: generating playlist songs with invalid data
        List<Song> songs = generatePlaylistSongs.execute(artists, genres, tracks);

        // Then: The result should be either empty or an error, depending on the implementation of the API
        assertNotNull(songs, "Generated playlist should not be null");
        assertTrue(songs.isEmpty(), "Generated playlist should be empty with invalid seed data");
    }


}