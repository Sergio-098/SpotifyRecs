package com.spotify.app;

import org.apache.hc.core5.http.ParseException;

import com.spotify.api.SpotifyClient;

import javax.swing.*;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, ParseException {
        String clientID = "a54ea954b9fe41408a55d3a577126fa1";
        String redirect = "http://localhost:8080/callback";
        SpotifyClient spotifyClient = new SpotifyClient(clientID, redirect);

        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addWelcomeView(spotifyClient)
                .addAuthorizationView(spotifyClient)
                .addLoggedInView(spotifyClient)
                .addPlaylistView()
                .addSavePlaylistView()
                .addAddSongView()
                .addAuthorizeUseCase(spotifyClient)
                .addGenerateUseCase(spotifyClient)
                .addSavePlaylistUseCase(spotifyClient)
                .build();

        application.pack();
        application.setVisible(true);


//        System.out.println("Starting Spotify Playlist Generator...");

//
//        Scanner scanner = new Scanner(System.in);
//        FileUserDataAccessObject fileUserDAO = new FileUserDataAccessObject();
//

//        GenerateUseCase gen = new GenerateUseCase(spotifyClient);
//        MockSavePlaylistInteractor save = new MockSavePlaylistInteractor(spotifyClient, fileUserDAO);
//        AuthorizeUseCase auth = new AuthorizeUseCase(spotifyClient);
//
//        if (auth.execute2()) {
//
//            User user = spotifyClient.getCurrentUser();
//            fileUserDAO.save(user);
//
//            System.out.println("User successfully authenticated!");
//            // Now you can use spotifyClient to make authorized API calls
//
//            System.out.println("What is your seed artist: ");
//            String artist = scanner.nextLine();
//            String artistId = spotifyClient.getSearchArtist(artist);
//
//            System.out.println("Select seed genre from the following list: ");
//            System.out.println(spotifyClient.getGenres());
//
//            String genre = scanner.nextLine();
//
//            System.out.println("What is your seed song: ");
//            String track = scanner.nextLine();
//            String trackId = spotifyClient.getSearchSong(track);
//
//            System.out.println("Generating playlist...");
//
//            //this is a placeholder for the search use case
//            List<String> artists = new ArrayList<>();
//            List<String> genres = new ArrayList<>();
//            List<String> tracks = new ArrayList<>();
//            artists.add(artistId);
//            genres.add(genre);
//            tracks.add(trackId);
//
//            //generate use case
//            List<Song> songs = gen.execute(artists, genres, tracks);
//            System.out.println("Here is your generated playlist");
//
//            label:
//            while (true) {
//                for (Song song : songs) {
//                    System.out.println(song.getName() + " by " + song.getArtists().getFirst());
//                }
//                //blank line
//                System.out.println(" ");
//                //ask if you would like to save
//                System.out.println("Do you want to save your playlist? type save/add/delete/no");
//
//                String savePlaylist = scanner.nextLine();
//
//                //save use case
//                switch (savePlaylist) {
//                    case "save":
//                        System.out.println("Enter playlist name:");
//                        String name = scanner.nextLine();
//                        System.out.println("Enter playlist description:");
//                        String description = scanner.nextLine();
//                        System.out.println("Do you want it to be public? type y/n");
//                        if (scanner.nextLine().equals("y")) {
//                            System.out.println("Saving generated playlist...");
//                            save.execute(name, description, songs, true, user);
//                        } else {
//                            System.out.println("Saving generated playlist...");
//                            save.execute(name, description, songs, false, user);
//                        }
//                        break label;
//
//                    //Add Songs use case
//                    case "add":
//                        System.out.println("How many songs:");
//                        String number = scanner.nextLine();
//                        Integer num = Integer.valueOf(number);
//                        RecommendationCriteriaFactory rc = new RecommendationCriteriaFactory();
//                        RecommendationCriteria recCriteria = rc.createRecCrit(artists, genres, tracks);
//                        List<Song> newSongs = spotifyClient.getRecommendations(recCriteria, num);
//                        songs.addAll(newSongs);
//                        //Delete Songs use case
//                        break;
//                    case "delete":
//                        System.out.println("Which song would you like to delete?:");
//                        String remove = scanner.nextLine();
//                        songs.removeIf(song -> song.getName().equalsIgnoreCase(remove));
//                        //Case where you don't want to save the playlist at all and you return to the home screen
//                        break;
//
//                    case "no":
//                        break label;
//                    default:
//                        System.out.println("Invalid Choice");
//                }
//            }
//
//            System.out.println("Thank you for using our program :)");
//
//        }
//        else {
//            System.err.println("Authentication failed. Check credentials or authorization code.");
//        }
    }
}