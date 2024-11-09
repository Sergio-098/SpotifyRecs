package com.spotify.app;

import com.spotify.factory.RecCritFactory;
import com.spotify.models.RecommendationCriteria;
import com.spotify.models.Song;
import com.spotify.api.SpotifyClient;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppDemo {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("Starting Spotify Playlist Generator...");
        String clientID = "a54ea954b9fe41408a55d3a577126fa1";
        String redirect = "http://localhost:8080/callback";

        Scanner scanner = new Scanner(System.in);

        SpotifyClient spotifyClient = new SpotifyClient(clientID, redirect);

        if (spotifyClient.authenticate()) {
            System.out.println("User successfully authenticated!");
            // Now you can use spotifyClient to make authorized API calls
            System.out.println("What is your seed artist: ");

            String crit = scanner.nextLine();

            System.out.println("Generating playlist based on " + crit + "...");

            RecCritFactory rf = new RecCritFactory();
            List<String> artists = new ArrayList<>();
            List<String> genres = new ArrayList<>();
            List<String> tracks = new ArrayList<>();
            artists.add("1ybINI1qPiFbwDXamRtwxD"); //smino
            genres.add("hiphop"); //rap
            tracks.add("4LLNd36WeJKv8pzjGeoMXZ"); //backstage pass by smino

            RecommendationCriteria rec = rf.createRecCrit(artists, genres, tracks);

            List<Song> songs = spotifyClient.getRecommendations(rec);

            System.out.println("Here is your generated playlist");
            for (Song song : songs) {
                System.out.println(song.getName() + " by " + song.getArtists().getFirst());
            }

        } else {
            System.err.println("Authentication failed. Check credentials or authorization code.");
        }
    }
}
