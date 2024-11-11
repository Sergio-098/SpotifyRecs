package com.spotify.memory.change_password;

import com.spotify.models.User;

import java.io.IOException;

/**
 * The interface of the DAO for the Change Password Use Case.
 */
public interface ChangePasswordUserDataAccessInterface {

    /**
     * Updates the system to record this user's password.
     * @param user the user whose password is to be updated
     */
    void changePassword(User user) throws IOException;
}
