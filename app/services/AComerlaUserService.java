package services;

import models.User;
import models.UserSocialInformation;
import securesocial.provider.SocialUser;
import securesocial.provider.UserId;
import securesocial.provider.UserService.Service;

public class AComerlaUserService implements Service {

    private static final String ZAUBERLABS_COM = "@zauberlabs.com";
    private static final String CUPOINT_COM = "@cupoint.com";

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
        checkDomain(user);
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

    private void checkDomain(final SocialUser user) {
        if (!(user.email.toLowerCase().endsWith(ZAUBERLABS_COM) || user.email.toLowerCase().endsWith(CUPOINT_COM))) {
            throw new IllegalStateException("Solo mails de Zauberlabs");
        }
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
