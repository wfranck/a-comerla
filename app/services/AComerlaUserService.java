package services;

import models.User;
import models.UserSocialInformation;
import securesocial.provider.SocialUser;
import securesocial.provider.UserId;
import securesocial.provider.UserService.Service;

public class AComerlaUserService implements Service {

    @Override
    public SocialUser find(final UserId id) {
        UserSocialInformation u = UserSocialInformation.findByUserId(id);
        if (u == null) {
            return null;
        }
        return u.toSocialUser();
    }

    @Override
    public void save(final SocialUser user) {
        UserSocialInformation usi = UserSocialInformation.findByUserId(user.id);
        if (usi != null) {
            usi.update(user);
            usi.validateAndSave();
            return;
        }
        User u = User.findByEmail(user.email);
        if (u == null) {
            u = new User();
            u.mail = user.email;
        }
        u.addSocialinformation(new UserSocialInformation(user));
        u.validateAndSave();
        
        
    }

    @Override
    public String createActivation(final SocialUser user) {
        // Not implemented
        return null;
    }

    @Override
    public boolean activate(final String uuid) {
     // Not implemented
        return true;
    }

    @Override
    public void deletePendingActivations() {
     // Not implemented
    }

}
