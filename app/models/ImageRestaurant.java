package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;
@Entity
public class ImageRestaurant extends Model {
    
    @Required
    public Blob image;

}
