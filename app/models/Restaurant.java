package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import play.data.validation.MaxSize;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;

import com.google.common.collect.Lists;

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

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @Valid
    public List<Dish> dishes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ImagesForRestaurant")
    @Valid
    public List<ImageRestaurant> images = Lists.newArrayList();
    
    public Restaurant() {
        
    }

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
        this.dishes = new ArrayList<Dish>();
    }

    @Override
    public String toString() {
        return this.name;
    }

}
