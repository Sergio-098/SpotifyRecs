package com.spotify.use_case.remove_song;

/*
import com.spotify.entity.RecommendationCriteria;
import com.spotify.entity.RecommendationCriteriaFactory;
import com.spotify.entity.Song;
import java.util.Scanner;
import com.spotify.app.AppDemo;

import java.util.List;

public class RemoveSongInteractor implements RemoveSongInputBoundary{
    private final RemoveSongUserDataAccessInterface userDataAccessInterface;
    private final RemoveSongOutputBoundary removeSongPreesenter;

    Scanner scanner = new Scanner(System.in);

    public RemoveSongInteractor(RemoveSongUserDataAccessInterface userDataAccessInterface,
                                RemoveSongOutputBoundary removeSongOutputBoundary) {
        this.userDataAccessInterface = userDataAccessInterface;
        this.removeSongPreesenter = removeSongOutputBoundary;
    }

    @Override
    public void execute(RemoveSongInputData removeSongInputData) {
        final String songID = removeSongInputData.getSongID();

        System.out.println("Which song would you like to delete?:");
        String remove = scanner.nextLine();
        songs.removeIf(song -> song.getName().equalsIgnoreCase(remove));
        //Case where you don't want to save the playlist at all and you return to the home screen
        //Delete Songs use case
    }
}
*/