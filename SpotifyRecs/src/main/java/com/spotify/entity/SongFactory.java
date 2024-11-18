package com.spotify.entity;

import com.spotify.util.ArtistNameParser;
import com.spotify.util.SongIDParser;
import com.spotify.util.SongNameParser;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SongFactory {
    public List<Song> createSongs(JSONObject json) {
        List<Song> songs = new ArrayList<>();

        SongNameParser songNameParser = new SongNameParser();
        SongIDParser songIDParser = new SongIDParser();
        ArtistNameParser artistNameParser = new ArtistNameParser();

        List<String> names = songNameParser.parse(json);
        List<String> ids = songIDParser.parse(json);
        List<List<String>> artists = artistNameParser.parse(json);

        for (int i = 0; i < names.size(); i++) {
            Song song = new Song(ids.get(i), names.get(i), artists.get(i));
            songs.add(song);
        }
        return songs;
    }
}
