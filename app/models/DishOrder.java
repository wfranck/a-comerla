package models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class DishOrder extends Model {
    
    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    @Required
    public User user;
    
    @OneToMany
    @JoinTable(name = "DishOrderDishes", 
            joinColumns = @JoinColumn(name = "DishOrderId"), 
            inverseJoinColumns = @JoinColumn(name = "DishId"))
    @Required
    @MinSize(1)
    private List<Dish> dishes;
    
    @ManyToOne
    @JoinColumn(name = "OrderId")
    @Required
    public DeliveryOrder order;
    
    public DishOrder(final User user, final Dish... dishes) {
        this.user = user;
        this.dishes = Arrays.asList(dishes);
    }
    
    public void setOrder(final DeliveryOrder order) {
        if (!order.getDishOrders().contains(this)) {
            throw new IllegalArgumentException("You can't add this if it wasn't added");
        }
        this.order = order;
    }
    
    public List<Dish> getDishes() {
        return Collections.unmodifiableList(dishes);
    }

}
