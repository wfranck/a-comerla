package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.MaxSize;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.db.jpa.Model;
import controllers.CRUD.Exclude;

@Entity
public class Restaurant extends Model {

    @Column(name = "Name", length = ModelConstants.RESTAURANT_NAME_LENGTH)
    @Required
    @MaxSize(value = ModelConstants.RESTAURANT_NAME_LENGTH)
    public String name;
    
    @Column(name = "Telephone", length = ModelConstants.TELEPHONE_LENGTH)
    @Required
    @MaxSize(value = ModelConstants.TELEPHONE_LENGTH)
    @Phone
    public String telephone;

    @OneToMany(mappedBy = "restaurant")
    @Exclude
    public List<Dish> dishes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    public List<ImageRestaurant> images;

    /**
     * Creates the Restaurant.
     * 
     * @param name
     * @param telephone
     * @param dishes
     * @param images
     */
    public Restaurant(final String name, final String telephone,
            final ImageRestaurant... images) {
        this.name = name;
        this.telephone = telephone;
        this.images = Arrays.asList(images);
        dishes = new ArrayList<Dish>();
    }
    
    @Override
    public String toString() {
        return name;
    }

}
