    package com.spotify.app;

    import com.spotify.entity.RecommendationCriteriaFactory;
    import com.spotify.entity.*;
    import com.spotify.api.SpotifyClient;
    import com.spotify.repositories.FilePlaylistRepository;
    import com.spotify.use_case.generate.GenerateUseCase;
    import com.spotify.use_case.remove_song.RemoveSongUseCase;
    import com.spotify.use_case.save_playlist.SavePlaylistUseCase;
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
            SavePlaylistUseCase save = new SavePlaylistUseCase(spotifyClient, fplRepo);

            if (spotifyClient.authenticate()) {

                User user = spotifyClient.getCurrentUser();

                System.out.println("User successfully authenticated!");
                // Now you can use spotifyClient to make authorized API calls

                System.out.println("What is your seed artist: ");
                String artist = scanner.nextLine();
                String artistId = spotifyClient.getSearchArtist(artist);

                System.out.println("Select seed genre from the following list: ");
                System.out.println(spotifyClient.getGenres());

                String genre = scanner.nextLine();

                System.out.println("What is your seed song: ");
                String track = scanner.nextLine();
                String trackId = spotifyClient.getSearchSong(track);

                System.out.println("Generating playlist...");

                //this is a placeholder for the search use case
                List<String> artists = new ArrayList<>();
                List<String> genres = new ArrayList<>();
                List<String> tracks = new ArrayList<>();
                artists.add(artistId);
                genres.add(genre);
                tracks.add(trackId);

                //generate use case
                List<Song> songs = gen.execute(artists, genres, tracks);
                System.out.println("Here is your generated playlist");

                label:
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
                    switch (savePlaylist) {
                        case "save":
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
                                save.execute(name, description, songs, false, user);
                            }
                            break label;

                        //Add Songs use case
                        case "add":
                            System.out.println("How many songs:");
                            String number = scanner.nextLine();
                            Integer num = Integer.valueOf(number);
                            RecommendationCriteriaFactory rc = new RecommendationCriteriaFactory();
                            RecommendationCriteria recCriteria = rc.createRecCrit(artists, genres, tracks);
                            List<Song> newSongs = spotifyClient.getRecommendations(recCriteria, num);
                            songs.addAll(newSongs);
                            //Delete Songs use case
                            break;
                        case "delete":
                            System.out.println("Which song would you like to delete?:");
                            String remove = scanner.nextLine();
                            RemoveSongUseCase removeSongUseCase = new RemoveSongUseCase();
                            removeSongUseCase.execute(songs, remove);
                            //Case where you don't want to save the playlist at all and you return to the home screen
                            break;
                        default:
                            break label;
                    }

                }

                System.out.println("Thank you for using our program :)");

            } else {
                System.err.println("Authentication failed. Check credentials or authorization code.");
            }
        }
    }
