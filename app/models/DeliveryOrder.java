package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.MinSize;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.Model;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

@Entity
public class DeliveryOrder extends Model {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ExpirationPolicyId", nullable = false)
    @Required
    @Valid
    public DueDateExpirationPolicy expirationPolicy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @MinSize(value = 1)
    @Valid
    public List<DishOrder> dishOrders = new ArrayList<DishOrder>();

    @ManyToOne
    @JoinColumn(name = "RestaurantId")
    @Required
    @Valid
    public Restaurant restaurant;

    @Column(name = "Expired")
    public boolean expired;

    public DeliveryOrder(final DueDateExpirationPolicy policy, final Restaurant r) {
        expired = false;
        restaurant = r;
        expirationPolicy = policy;
    }

    public void addDishOrder(final DishOrder order) {
        this.dishOrders.add(order);
        order.setOrder(this);
    }


    public static List<DeliveryOrder> findExpiringOrders(final Date start, final Date end) {
        return find("expired = false and expirationPolicy.expirationDate between ? and ?", start,end).<DeliveryOrder>fetch();
    }

    public DeliveryOrderResult close() {
        expired = true;
        Collection<User> allUsers = Collections2.transform(dishOrders, new Function<DishOrder, User>() {

            @Override
            public User apply(final DishOrder input) {
                return input.user;
            }
        });
        BigDecimal total = BigDecimal.ZERO;
        for(DishOrder dishOrder : dishOrders) {
            for(DishChildOrder dish : dishOrder.dishes) {
                total = total.add(dish.dish.price);
            }
        }
        List<User> users = new ArrayList<User>(allUsers);
        Collections.shuffle(users);
        User caller;
        User payer;
        if (users.size() == 1) {
            caller = users.get(0);
            payer = users.get(0);
        } else {
            caller = users.get(0);
            payer = users.get(1);
        }
        return new DeliveryOrderResult(this, caller, payer, users, total);

    }

    public boolean hasPeople() {
        return !dishOrders.isEmpty();
    }

    public void expire() {
        expired = true;
    }


}
