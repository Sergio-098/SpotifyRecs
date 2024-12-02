package use_case;
import com.spotify.api.SpotifyClient;
import com.spotify.use_case.authorize.AuthorizeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AuthorizeTest {
    private AuthorizeUseCase authorizeUseCase;
    private SpotifyClient spotifyClient;

    @BeforeEach
    public void setup() {
        // Initialize the Spotify client (You can replace this with your actual SpotifyClient constructor)
        String clientID = "a54ea954b9fe41408a55d3a577126fa1";
        String redirect = "http://localhost:8080/callback";
        spotifyClient = new SpotifyClient(clientID, redirect);

        // Initialize the AuthorizeUseCase use case
        authorizeUseCase = new AuthorizeUseCase(spotifyClient);
    }

    @Test
    public void testAuthorizationSuccess() throws IOException {
        // Given: valid credentials and a valid authorization code (this would be mocked in real tests)

        // When: executing the authorization use case successfully
        boolean isAuthorized = authorizeUseCase.execute2(); // This should be your authorization function

        // Then: The authorization should succeed, returning true
        assertTrue(isAuthorized, "Authorization should succeed and return true");
    }

    @Test
    public void testMultipleAuthorizations() throws IOException {
        // Given: valid credentials
        // Simulate valid credentials for this test

        // When: attempting to authorize multiple times
        boolean firstAuthorization = authorizeUseCase.execute2(); // First authorization
        boolean secondAuthorization = authorizeUseCase.execute2(); // Second authorization

        // Then: Both authorizations should succeed
        assertTrue(firstAuthorization, "First authorization should succeed");
        assertTrue(secondAuthorization, "Second authorization should succeed after the first");
    }
}
