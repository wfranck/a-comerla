package controllers;

import securesocial.provider.AuthenticationMethod;
import securesocial.provider.SocialUser;
import securesocial.provider.UserId;
import securesocial.provider.UserService.Service;

public class MockService implements Service {

    @Override
    public SocialUser find(final UserId id) {
        SocialUser socialUser = new SocialUser();
        socialUser.email = "gonto@gonto.com";
        socialUser.password = "hola";
        socialUser.authMethod = AuthenticationMethod.USER_PASSWORD;
        return socialUser;
    }

    @Override
    public void save(final SocialUser user) {
        // TODO: Auto-generated method stub

    }

    @Override
    public String createActivation(final SocialUser user) {
        // TODO: Auto-generated method stub
        return null;
    }

    @Override
    public boolean activate(final String uuid) {
        // TODO: Auto-generated method stub
        return false;
    }

    @Override
    public void deletePendingActivations() {
        // TODO: Auto-generated method stub

    }

}
