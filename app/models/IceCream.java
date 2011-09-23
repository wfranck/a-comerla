package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class IceCream extends Model {
    
    @Column(name = "Flavour")
    public final String flavour;
    
    public IceCream(final String flavour) {
        this.flavour = flavour;
    }
}
