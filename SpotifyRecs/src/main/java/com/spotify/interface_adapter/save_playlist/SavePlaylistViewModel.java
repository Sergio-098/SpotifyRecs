package com.spotify.interface_adapter.save_playlist;


import com.spotify.interface_adapter.ViewModel;

public class SavePlaylistViewModel extends ViewModel<SavePlaylistState> {

    public static final String TITLE_LABEL = "Save Playlist";
    public static final String NAME_LABEL = "Choose Playlist Name: ";
    public static final String DESCRIPTION_LABEL = "Type In Description";
    public static final String PUBLIC_LABEL = "Public: ";

    public static final String SAVE_BUTTON_LABEL = "SAVE";
    public static final String CANCEL_BUTTON_LABEL = "CANCEL";

    public SavePlaylistViewModel() {
        super("sign up");
        setState(new SavePlaylistState());
    }
}
