package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class User extends Model {
    
    @Column(name = "Mail", nullable = false, unique = true)
    public String mail;
    
    @Column(name = "Username", nullable = false, unique = true)
    public String username;
    
    public User(final String mail, final String username) {
        this.mail = mail;
        this.username = username;
    }
    
}
