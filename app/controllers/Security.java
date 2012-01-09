package controllers;

import models.User;
import models.UserSocialInformation;
import controllers.securesocial.SecureSocial;

public class Security {
    
    public static User connected() {
        return UserSocialInformation.findByUserId(SecureSocial.getCurrentUser().id).user;
    }

}
