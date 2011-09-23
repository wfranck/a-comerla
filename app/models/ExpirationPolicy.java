package models;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import play.db.jpa.Model;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ExpirationPolicy extends Model {
    
    public abstract boolean isExpired();

}
