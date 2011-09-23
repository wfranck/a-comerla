package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class DeliveryOrder extends Model {
    
    @OneToOne
    @JoinColumn(name = "ExpirationPolicyId", nullable = false)
    @Required
    public ExpirationPolicy expirationPolicy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @MinSize(value = 1)
    public List<DishOrder> dishOrders;
    @ManyToOne
    @JoinColumn(name = "RestaurantId")
    @Required
    public Restaurant restaurant;

}
