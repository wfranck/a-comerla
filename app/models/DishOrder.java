package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "DishOrderDishes",
    joinColumns = @JoinColumn(name = "DishOrderId"),
    inverseJoinColumns = @JoinColumn(name = "DishId"))
    @Required
    @MinSize(1)
    public List<DishChildOrder> dishes = new ArrayList<DishChildOrder>();

    @ManyToOne
    @JoinColumn(name = "OrderId")
    @Required
    public DeliveryOrder order;

    public DishOrder(final User user, final Dish... dishes) {
        this.user = user;
        for(Dish dish : dishes) {
            this.dishes.add(new DishChildOrder(dish));
        }
    }

    public void setOrder(final DeliveryOrder order) {
        if (!order.dishOrders.contains(this)) {
            throw new IllegalArgumentException("You can't add this if it wasn't added");
        }
        this.order = order;
    }

    public static DishOrder findExistent(final DeliveryOrder order, final User user) {
        return DishOrder.find("order = ? and user = ?", order, user).first();
    }

}
