package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.InFuture;

@Entity
public class DueDateExpirationPolicy extends ExpirationPolicy {
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ExpirationDate" , nullable = false)
    @InFuture
    public Date expirationDate;
    
    public DueDateExpirationPolicy(final Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean isExpired() {
        return new Date().compareTo(expirationDate) >= 0;
    }

}
