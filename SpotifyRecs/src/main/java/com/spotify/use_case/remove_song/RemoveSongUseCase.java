package com.spotify.use_case.remove_song;
import com.spotify.entity.Song;
import java.util.List;

public class RemoveSongUseCase {
    public boolean execute(List<Song> songs, String songName) {
        return songs.removeIf(song -> song.getName().equalsIgnoreCase(songName));
    }
}
