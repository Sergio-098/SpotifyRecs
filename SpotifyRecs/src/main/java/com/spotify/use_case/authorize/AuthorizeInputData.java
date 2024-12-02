package com.spotify.use_case.authorize;

/**
 * The Input Data for the Authorize Use Case.
 */
public class AuthorizeInputData {
     private final String code;

     public AuthorizeInputData(String code) {
         this.code = code;
     }

     public String getCode() {
         return code;
     }
}
