package models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.MaxSize;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Dish extends Model {

    @Column(name = "Description", nullable = false, length = ModelConstants.DISH_DESCRIPTION_LENGTH)
    @Required
    @MaxSize(value = ModelConstants.DISH_DESCRIPTION_LENGTH)
    public String description;
    
    @Column(name = "Name", nullable = false, length = ModelConstants.DISH_NAME_LENGTH )
    @Required
    @MaxSize(value = ModelConstants.DISH_NAME_LENGTH )
    public String name;

    @Column(name = "Price")
    @Required
    @Min(value = 0.1)
    public BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "RestaurantId", nullable = false)
    @Required
    public Restaurant restaurant;
    
    public static List<Restaurant> findByRestaurant(final Restaurant r) {
        return Dish.find("byRestaurant", r).fetch();
    }
    
    public static List<Restaurant> findByRestaurant(final Long id) {
        return findByRestaurant((Restaurant) Restaurant.findById(id));
    }

    public Dish(final String description, final BigDecimal price, final Restaurant restaurant) {
		this.description = description;
		this.price = price;
		this.restaurant = restaurant;
		restaurant.dishes.add(this);
	}

	@Override
    public String toString() {
    	return  String.format("%s | $ %s",this.description,this.price);
    }
}
