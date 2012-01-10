package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.Unique;
import play.db.jpa.Model;

import com.google.common.collect.Lists;

@Entity
@Table(name ="SocialUser")
public class User extends Model {

    @Column(name = "Mail", nullable = false, unique = true)
    @Unique
    public String mail;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy= "user")
    public List<UserSocialInformation> socialInformation = Lists.newArrayList();
    

    public static User findByEmail(final String email) {
        return find("byMail", email).first();
    }


    public void addSocialinformation(final UserSocialInformation userSocialInformation) {
        this.socialInformation.add(userSocialInformation);
        userSocialInformation.user = this;
    }
    

}
