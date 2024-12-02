package use_case;

import com.spotify.api.SpotifyClient;
import com.spotify.entity.Song;
import com.spotify.use_case.remove_song.RemoveSongInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoveSongTest {

    private RemoveSongInteractor removeSongInteractor;
    private SpotifyClient spotifyClient;

    @BeforeEach
    public void setup() {
        // Initialize the Spotify client (You can replace this with your actual SpotifyClient constructor)
        String clientID = "a54ea954b9fe41408a55d3a577126fa1";
        String redirect = "http://localhost:8080/callback";
        spotifyClient = new SpotifyClient(clientID, redirect);

        // Initialize the RemoveSongInteractor use case
        removeSongInteractor = new RemoveSongInteractor();
    }

    @Test
    public void testRemoveSongFromPlaylist() {
        // Given: a list of songs
        List<Song> songs = new ArrayList<>();

        // Add some sample songs to the list (using example song names and artists)
        Song song1 = new Song("123", "Song 1", Arrays.asList("Artist 1", "Artist 2"));
        Song song2 = new Song("1234", "Song 2", Arrays.asList("Artist 1", "Artist 2"));
        Song song3 = new Song("12345", "Song 3", Arrays.asList("Artist 1", "Artist 2"));

        songs.add(song1);
        songs.add(song2);
        songs.add(song3);

        // When: removing a song (Song 2) from the playlist
        String songToRemove = "Song 2";
        removeSongInteractor.execute(songs, songToRemove);

        // Then: The song list should not contain "Song 2"
        assertNotNull(songs, "Song list should not be null");
        assertTrue(songs.size() == 2, "Song list should contain 2 songs after removal");
        assertTrue(songs.stream().noneMatch(song -> song.getName().equals(songToRemove)),
                "Song list should not contain the removed song");
    }

    @Test
    public void testRemoveSongThatDoesNotExist() {
        // Given: a list of songs
        List<Song> songs = new ArrayList<>();

        // Add some sample songs to the list (using example song names and artists)
        Song song1 = new Song("123", "Song 1", Arrays.asList("Artist 1", "Artist 2") );
        Song song2 = new Song("1234", "Song 2", Arrays.asList("Artist 1", "Artist 2") );

        songs.add(song1);
        songs.add(song2);

        // When: attempting to remove a song that does not exist in the playlist
        String songToRemove = "Non-existent Song";
        removeSongInteractor.execute(songs, songToRemove);

        // Then: The song list should still contain all songs
        assertNotNull(songs, "Song list should not be null");
        assertTrue(songs.size() == 2, "Song list should contain 2 songs after attempted removal");
    }

    @Test
    public void testRemoveSongFromEmptyPlaylist() {
        // Given: an empty playlist
        List<Song> songs = new ArrayList<>();

        // When: attempting to remove a song from an empty playlist
        String songToRemove = "Any Song";
        removeSongInteractor.execute(songs, songToRemove);

        // Then: The song list should still be empty
        assertNotNull(songs, "Song list should not be null");
        assertTrue(songs.isEmpty(), "Song list should remain empty after removal attempt");
    }

    @Test
    public void testRemoveLastSongFromPlaylist() {
        // Given: a playlist with only one song
        List<Song> songs = new ArrayList<>();
        Song song = new Song("123", "Song 1", Arrays.asList("Artist 1", "Artist 2") );
        songs.add(song);

        // When: removing the only song in the playlist
        String songToRemove = "Song 1";
        removeSongInteractor.execute(songs, songToRemove);

        // Then: The song list should be empty
        assertNotNull(songs, "Song list should not be null");
        assertTrue(songs.isEmpty(), "Song list should be empty after removal");
    }

    @Test
    public void testRemoveSongWithMultipleInstances() {
        // Given: a playlist with multiple instances of the same song
        List<Song> songs = new ArrayList<>();

        // Add two identical songs to the list
        Song song1 = new Song("123123", "Song1", Arrays.asList("Artist 1", "Artist 2") );
        songs.add(song1);
        songs.add(song1);

        // When: removing one instance of the song from the playlist
        String songToRemove = "Song 1";
        removeSongInteractor.execute(songs, songToRemove);

        // Then: The song list should still contain one instance of the song
        assertNotNull(songs, "Song list should not be null");
        assertTrue(songs.size() == 1, "Song list should contain 1 song after removal");
        assertTrue(songs.stream().anyMatch(song -> song.getName().equals(songToRemove)),
                "Song list should still contain one instance of the removed song");
    }
}