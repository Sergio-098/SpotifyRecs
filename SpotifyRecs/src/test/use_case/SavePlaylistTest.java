package use_case;
import com.spotify.api.SpotifyClient;
import com.spotify.entity.*;
import com.spotify.repositories.FilePlaylistRepository;
import com.spotify.use_case.save_playlist.SavePlaylistUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class SavePlaylistTest {
    private SpotifyClient spotifyClient;
    private FilePlaylistRepository filePlaylistRepository;
    private SavePlaylistUseCase savePlaylistUseCase;
    private User user;

    @BeforeEach
    void setUp() throws IOException {

        String clientId = "your-client-id"; // Replace with actual client ID
        String redirectUri = "http://localhost:8080/callback"; // Replace with actual redirect URI
        spotifyClient = new SpotifyClient(clientId, redirectUri);

        filePlaylistRepository = new FilePlaylistRepository();
        savePlaylistUseCase = new SavePlaylistUseCase(spotifyClient, filePlaylistRepository);

    }

    @Test
    void testSavePlaylistSuccessfully() throws IOException {
        // Arrange: Create some sample songs
        Song song1 = new Song("1", "Song One", List.of("Artist One"));
        Song song2 = new Song("2", "Song Two", List.of("Artist Two"));
        List<Song> songs = new ArrayList<>();
        songs.add(song1);
        songs.add(song2);

        String playlistName = "Test Playlist";
        String description = "A playlist for testing";
        boolean isPublic = true;
        Playlist playlist = savePlaylistUseCase.execute(playlistName, description, songs, isPublic, user);

        assertNotNull(playlist);
        assertEquals(playlistName, playlist.getName());
        assertEquals(description, playlist.getDescription());
        assertTrue(playlist.isPublic());
        assertEquals(2, playlist.getSongs().size());
        assertTrue(playlist.getSongs().contains(song1));
        assertTrue(playlist.getSongs().contains(song2));
    }

    @Test
    void testSavePlaylistFailsWhenUserIsNull() throws IOException {

        Song song1 = new Song("1", "Song One", List.of("Artist One"));
        List<Song> songs = new ArrayList<>();
        songs.add(song1);

        assertThrows(NullPointerException.class, () -> {
            savePlaylistUseCase.execute("Test Playlist", "Description", songs, true, null);
        });
    }

    @Test
    void testSavePlaylistToRepository() throws IOException {

        Song song1 = new Song("1", "Song One", List.of("Artist One"));
        Song song2 = new Song("2", "Song Two", List.of("Artist Two"));
        List<Song> songs = new ArrayList<>();
        songs.add(song1);
        songs.add(song2);

        Playlist playlist = savePlaylistUseCase.execute("Test Playlist", "Description", songs, true, user);

        assertNotNull(playlist);
    }
}


