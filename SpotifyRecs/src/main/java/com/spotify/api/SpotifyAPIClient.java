package com.spotify.api;

import com.spotify.entity.Playlist;
import com.spotify.entity.RecommendationCriteria;
import com.spotify.entity.Song;
import com.spotify.entity.User;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;


public interface SpotifyAPIClient {

    /**
     * Creates a new playlist for the specified user.
     *
     * @param user        The user for whom the playlist is to be created.
     * @param name        The name of the new playlist.
     * @param description A description for the new playlist.
     * @param isPublic    A boolean indicating if the playlist should be public.
     * @return The created Playlist object.
     * @throws IOException If there is an issue with the API request.
     */
    Playlist createPlaylist(User user, String name, String description, boolean isPublic) throws IOException;

    /**
     * Adds songs to an existing playlist.
     *
     * @param playlist The playlist to which songs will be added.
     * @param songs    The list of songs to add to the playlist.
     * @throws IOException If there is an issue with the API request.
     */
    void addSongsToPlaylist(Playlist playlist, List<Song> songs) throws IOException;

    /**
     * Retrieves song recommendations based on the provided criteria.
     *
     * @param criteria The criteria for generating song recommendations.
     * @return A list of recommended Song objects.
     * @throws IOException If there is an issue with the API request.
     */
    List<Song> getRecommendations(RecommendationCriteria criteria, Integer num) throws IOException, ParseException;

    /**
     * Authenticates the user and retrieves the access token (optional).
     */
    boolean authenticate() throws IOException; // Optional method, can be used for getting access tokens

    /**
     * Returns current user info
     * @return user object
     * @throws IOException
     */
    User getCurrentUser() throws IOException, ParseException;
}

