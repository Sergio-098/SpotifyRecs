package com.spotify.interface_adapter.save_playlist;

public class SavePlaylistState {
    private String name = "";
    private String nameError;
    private String description = "";
    private String descriptionError;
    private Boolean isPublic = false;

    public String getUsername() {
        return name;
    }

    public String getNameError() {
        return nameError;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionError() {
        return descriptionError;
    }

    public Boolean getIsPublic() {return isPublic;}

    public void setName(String name) {
        this.name = name;
    }

    public void setUsernameError(String nameError) {
        this.nameError = nameError;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }

    public void setIsPublic(Boolean isPublic) {this.isPublic = isPublic;}


    @Override
    public String toString() {
        return "SignupState{"
                + "username='" + name + '\''
                + ", password='" + description + '\''
                + ", repeatPassword='" + isPublic.toString() + '\''
                + '}';
    }
}
