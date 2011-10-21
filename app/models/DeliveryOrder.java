package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.MinSize;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;

@Entity
public class DeliveryOrder extends Model {
    
    @OneToOne
    @JoinColumn(name = "ExpirationPolicyId", nullable = false)
    @Required
    @Valid
    public ExpirationPolicy expirationPolicy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @MinSize(value = 1)
    @Valid
    private List<DishOrder> dishOrders = new ArrayList<DishOrder>();
    @ManyToOne
    @JoinColumn(name = "RestaurantId")
    @Required
    @Valid
    public Restaurant restaurant;
    
    public DeliveryOrder(final ExpirationPolicy policy, final Restaurant r) {
        restaurant = r;
        expirationPolicy = policy;
    }
    
    public void addDishOrder(final DishOrder order) {
        this.dishOrders.add(order);
        order.setOrder(this);
    }
    
    public List<DishOrder> getDishOrders() {
        return Collections.unmodifiableList(dishOrders);
    }
    
    private void setDishOrders(final List<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
    }
    

}
