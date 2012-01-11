package notifiers;

import helpers.ExtendedJavaExtensions;
import models.DeliveryOrder;
import models.DeliveryOrderResult;
import models.User;
import play.Play;
import play.mvc.Mailer;

public class Mails extends Mailer {

    public static void sendOrder(final DeliveryOrderResult result) {
        setFrom("zaubertest.test@gmail.com");
        for (User user : result.people) {
            addRecipient(user.mail);
        }
        setSubject("A Comerlaaaaa!!!!");
        send(result);
    }

    public static void newOrder(final DeliveryOrder order) {
        setFrom("zaubertest.test@gmail.com");
        setSubject("[A-COMERLA] Nuevo pedido para " + order.restaurant.name + " vence a las "
                + ExtendedJavaExtensions.format(order.expirationPolicy.expirationDate, "HH:mm", "GMT-3"));
        addRecipient(Play.configuration.getProperty("broadcast.mail", "hq@zauberlabs.com"));
        send(order);
    }

}
