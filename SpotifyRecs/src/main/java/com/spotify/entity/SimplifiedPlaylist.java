package com.spotify.entity;

/**
 * This function creates an object to hold the user's playlists without loading in all of their playlists and tracks.
 */
public record SimplifiedPlaylist(String playlistId, String playlistName) {
}
