package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.InFuture;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class DueDateExpirationPolicy extends Model {
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ExpirationDate" , nullable = false)
    @InFuture
    @Required
    public Date expirationDate;
    
    public DueDateExpirationPolicy(final Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isExpired() {
        return new Date().compareTo(expirationDate) >= 0;
    }

}
