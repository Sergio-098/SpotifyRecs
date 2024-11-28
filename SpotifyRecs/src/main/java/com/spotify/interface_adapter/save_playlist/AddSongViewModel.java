package com.spotify.interface_adapter.save_playlist;

import com.spotify.interface_adapter.ViewModel;

public class AddSongViewModel extends ViewModel<AddSongState> {
    public AddSongViewModel() {
        super("add song");
        setState(new AddSongState());
    }
}
