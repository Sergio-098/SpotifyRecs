package com.spotify.interface_adapter.authorize;

import com.spotify.use_case.authorize.AuthorizeInteractor;
import java.io.IOException;

 /**
 * The controller for switching from WelcomeView to AuthorizationView
*/
public class WelcomeController {
        private final AuthorizeInteractor authorizeInteractor;
        public WelcomeController(AuthorizeInteractor authorizeInteractor) {
            this.authorizeInteractor = authorizeInteractor;
        }


        /**
         * Executes the "switch to Authorize" Use Case.
         */
        public void switchToAuthorizeView() {
            authorizeInteractor.switchToLoginView();
        }

    }
