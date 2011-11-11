package models;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class DeliveryOrderTest  {

    @Test
    public void testDelOrderClose() {
        Restaurant r = new Restaurant("gonto", "48557524");
        Dish dish = new Dish("Plato", BigDecimal.ONE, r);
        DeliveryOrder order = new DeliveryOrder(new DueDateExpirationPolicy(new Date()), r);
        User user = new User("gonto@gonto.com", "gonto");
        order.dishOrders.add(new DishOrder(user, dish));

        DeliveryOrderResult close = order.close();
        Assert.assertEquals(1, close.people.size());
        Assert.assertEquals(user, close.caller);
        Assert.assertEquals(user, close.payer);
    }

}
