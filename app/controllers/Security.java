package controllers;

import javax.inject.Inject;

import models.User;
import authentication.LDAPAuthenticator;

public class Security extends Secure.Security {

    @Inject
    private static LDAPAuthenticator authenticator;

    static boolean authentify(final String username, final String password) {
        return authenticator.login(username, password);
    }

    public static User loggedIn() {
        return User.findByUsername(Security.connected());
    }

}