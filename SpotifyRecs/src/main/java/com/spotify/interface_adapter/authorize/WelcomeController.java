package com.spotify.interface_adapter.authorize;


 /**
 * The controller for switching from WelcomeView to AuthorizationView
*/
public class WelcomeController {

        private WelcomePresenter welcomePresenter;
        public WelcomeController(WelcomePresenter welcomePresenter) {
            this.welcomePresenter = welcomePresenter;
        }


     public void switchToAuthorizeView() {
            welcomePresenter.switchToAuthorizeView();
     }
 }
