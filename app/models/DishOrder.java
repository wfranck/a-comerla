package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class DishOrder extends Model {
    
    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    public User user;
    
    @OneToMany
    @JoinTable(name = "DishOrderDishes", 
            joinColumns = @JoinColumn(name = "DishOrderId"), 
            inverseJoinColumns = @JoinColumn(name = "DishId"))
    public List<Dish> dishes;
    
    @ManyToOne
    @JoinColumn(name = "OrderId")
    public DeliveryOrder order;

}
