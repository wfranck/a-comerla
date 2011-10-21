package controllers;

import models.User;
 
public class Security extends Secure.Security {
    
    static boolean authentify(final String username, final String password) {
        return true;
    }    
    
    public static User loggedIn() {
        return User.find("byMail", Security.connected()).first();
    }
    
}