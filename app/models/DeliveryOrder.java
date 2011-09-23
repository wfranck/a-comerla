package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class DeliveryOrder extends Model {
    
    @OneToOne
    @JoinColumn(name = "ExpirationPolicyId", nullable = false)
    public ExpirationPolicy expirationPolicy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    public List<DishOrder> dishOrders;

}
