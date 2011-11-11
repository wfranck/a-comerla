package notifiers;

import models.DeliveryOrderResult;
import models.User;
import play.mvc.Mailer;

public class Mails extends Mailer {

    public static void sendOrder(final DeliveryOrderResult result) {
        setFrom("zaubertest.test@gmail.com");
        for(User user : result.people) {
            addRecipient(user.mail);
        }
        setSubject("A Comerlaaaaa!!!!");
        send(result);
    }



}
