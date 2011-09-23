package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;
@Entity
public class ImageRestaurant extends Model {
    
    @Required
    public Blob image;
    
    @ManyToOne
    @JoinColumn(name = "RestaurantId", nullable = false)
    public Restaurant restaurant;

}
