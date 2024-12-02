package com.spotify.use_case.remove_song;
import com.spotify.entity.Song;

import java.util.List;

public class RemoveSongInteractor{



    public void execute(List<Song> songs, String songToRemove) {
        songs.removeIf(song -> song.getName().equalsIgnoreCase(songToRemove));
        System.out.println("Song " + songToRemove + " removed from the playlist.");

    }
}
