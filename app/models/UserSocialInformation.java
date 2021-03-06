package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;
import securesocial.provider.AuthenticationMethod;
import securesocial.provider.ProviderType;
import securesocial.provider.SocialUser;
import securesocial.provider.UserId;

@Entity
public class UserSocialInformation extends Model{
    
    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    public User user;
    
    @Column(name = "ProviderType", nullable = false)
    @Enumerated(EnumType.STRING)
    public ProviderType provider;
    
    @Column(name = "ProviderId", nullable = false)
    public String providerId;
    
    @Column(name = "Token")
    public String token;

    @Column(name = "Secret")
    public String secret;

    @Column(name = "AccessToken")
    public String accessToken;
    
    @Column(name = "AuthenticationMethod")
    @Enumerated(EnumType.STRING)
    public AuthenticationMethod authMethod;
    
    public UserSocialInformation(final SocialUser user) {
        updateLocal(user);
    }

    public SocialUser toSocialUser() {
        SocialUser socialUser = new SocialUser();
        socialUser.accessToken = accessToken;
        socialUser.authMethod = authMethod;
        socialUser.email = user.mail;
        socialUser.secret = secret;
        socialUser.token = token;
        socialUser.id = new UserId();
        socialUser.id.provider = provider;
        socialUser.id.id = providerId;
        return socialUser;
    }
    
    public static UserSocialInformation findByUserId(final UserId id) {
        return find("select si from User u inner join u.socialInformation si where si.provider = ? and si.providerId = ?", id.provider, id.id).first();
    }

    public void update(final SocialUser socialUser) {
        updateLocal(socialUser);
        this.user.mail =  socialUser.email;
    }
    
    public void updateLocal(final SocialUser socialUser) {
        accessToken =  socialUser.accessToken;
        authMethod =  socialUser.authMethod;
        secret = socialUser.secret;
        token = socialUser.token;
        provider = socialUser.id.provider;
        providerId = socialUser.id.id;
    }

}
