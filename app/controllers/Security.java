package controllers;

import models.User;
import models.UserSocialInformation;
import controllers.securesocial.SecureSocial;

public class Security {
    
    public static User connected() {
        if (SecureSocial.getCurrentUser() == null) {
            return null;
        }
        return UserSocialInformation.findByUserId(SecureSocial.getCurrentUser().id).user;
    }

}
