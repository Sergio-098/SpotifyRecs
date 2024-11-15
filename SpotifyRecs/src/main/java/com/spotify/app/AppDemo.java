package com.spotify.app;

import com.spotify.factory.RecCritFactory;
import com.spotify.models.*;
import com.spotify.api.SpotifyClient;
import com.spotify.repositories.FilePlaylistRepository;
import com.spotify.use_cases.generate_use_case.GenerateUseCase;
import com.spotify.use_cases.save_playlist.SaveUseCase;
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
        FilePlaylistRepository fplRepo = new FilePlaylistRepository();

        GenerateUseCase gen = new GenerateUseCase(spotifyClient);
        SaveUseCase save = new SaveUseCase(spotifyClient, fplRepo);

        if (spotifyClient.authenticate()) {

            User user = spotifyClient.getCurrentUser();

            System.out.println("User successfully authenticated!");
            // Now you can use spotifyClient to make authorized API calls

            System.out.println("What is your seed artist id: ");
            String artist = scanner.nextLine();

            System.out.println("What is your seed genre id: ");
            String genre = scanner.nextLine();

            System.out.println("What is your seed song id: ");
            String track = scanner.nextLine();

            System.out.println("Generating playlist...");

            //this is a placeholder for the search use case
            List<String> artists = new ArrayList<>();
            List<String> genres = new ArrayList<>();
            List<String> tracks = new ArrayList<>();
            artists.add(artist);
            genres.add(genre);
            tracks.add(track);

            //generate use case
            List<Song> songs = gen.execute(artists, genres, tracks);
            System.out.println("Here is your generated playlist");

            while (true) {
                for (Song song : songs) {
                    System.out.println(song.getName() + " by " + song.getArtists().getFirst());
                }
                //blank line
                System.out.println(" ");
                //ask if you would like to save
                System.out.println("Do you want to save your playlist? type save/add/delete/no");

                String savePlaylist = scanner.nextLine();

                //save use case
                if (savePlaylist.equals("save")) {
                    System.out.println("Enter playlist name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter playlist description:");
                    String description = scanner.nextLine();
                    System.out.println("Do you want it to be public? type y/n");
                    if (scanner.nextLine().equals("y")) {
                        System.out.println("Saving generated playlist...");
                        save.execute(name, description, songs, true, user);
                    } else {
                        System.out.println("Saving generated playlist...");
                        save.execute(name, description, songs, false, user);                    }
                    break;

                //Add Songs use case
                }else if (savePlaylist.equals("add")) {
                    System.out.println("How many songs:");
                    String number = scanner.nextLine();
                    Integer num = Integer.valueOf(number);
                    RecCritFactory rc = new RecCritFactory();
                    RecommendationCriteria recCriteria = rc.createRecCrit(artists, genres, tracks);
                    List<Song> newSongs = spotifyClient.getRecommendations(recCriteria, num);
                    songs.addAll(newSongs);
                //Delete Songs use case
                } else if (savePlaylist.equals("delete")) {
                    System.out.println("Which song would you like to delete?:");
                    String remove = scanner.nextLine();
                    songs.removeIf(song -> song.getName().equalsIgnoreCase(remove));
                //Case where you don't want to save the playlist at all and you return to the home screen
                } else {
                    break;
                }

            }

            System.out.println("Thank you for using our program :)");

        } else {
            System.err.println("Authentication failed. Check credentials or authorization code.");
        }
    }
}
