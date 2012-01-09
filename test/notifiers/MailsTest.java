package notifiers;

import java.math.BigDecimal;
import java.util.Date;

import models.DeliveryOrder;
import models.DeliveryOrderResult;
import models.Dish;
import models.DishOrder;
import models.DueDateExpirationPolicy;
import models.Restaurant;
import models.User;

import org.junit.Test;

import play.test.UnitTest;

public class MailsTest extends UnitTest {

    @Test
    public void testSendEmail() {
        Restaurant r = new Restaurant("gonto", "48557524");
        Dish dish = new Dish("Plato", BigDecimal.ONE, r);
        DeliveryOrder order = new DeliveryOrder(new DueDateExpirationPolicy(new Date()), r);
        User user = new User();
        user.mail = "gonto@gonto.com";
        order.dishOrders.add(new DishOrder(user, dish));
        DeliveryOrderResult close = order.close();
        Mails.sendOrder(close);
    }

}
