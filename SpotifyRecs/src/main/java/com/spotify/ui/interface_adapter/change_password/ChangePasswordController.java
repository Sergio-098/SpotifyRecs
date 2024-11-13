package com.spotify.ui.interface_adapter.change_password;

import com.spotify.memory.change_password.ChangePasswordInputBoundary;
import com.spotify.memory.change_password.ChangePasswordInputData;

import java.io.IOException;

/**
 * Controller for the Change Password Use Case.
 */
public class ChangePasswordController {
    private final ChangePasswordInputBoundary userChangePasswordUseCaseInteractor;

    public ChangePasswordController(ChangePasswordInputBoundary userChangePasswordUseCaseInteractor) {
        this.userChangePasswordUseCaseInteractor = userChangePasswordUseCaseInteractor;
    }

    /**
     * Executes the Change Password Use Case.
     * @param password the new password
     * @param username the user whose password to change
     */
    public void execute(String password, String username) throws IOException {
        final ChangePasswordInputData changePasswordInputData = new ChangePasswordInputData(username, password);

        userChangePasswordUseCaseInteractor.execute(changePasswordInputData);
    }
}
