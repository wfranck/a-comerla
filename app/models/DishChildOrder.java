package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class DishChildOrder extends Model {

    @ManyToOne
    @JoinColumn(name = "DishId")
    @Required
    public Dish dish;
    
    @ManyToOne
    @Required
    @JoinColumn(name = "DishOrderId")
    public DishOrder dishOrder;

    public DishChildOrder(final Dish dish, final DishOrder dishOrder) {
        this.dish = dish;
        this.dishOrder = dishOrder;
    }




}
