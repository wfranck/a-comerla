package jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import models.DeliveryOrder;
import models.DeliveryOrderResult;
import notifiers.Mails;

import org.apache.commons.lang.time.DateUtils;

import play.jobs.Every;
import play.jobs.Job;

@Every("1min")
public class ExpiredOrdersJobs extends Job<Void> {

    @Override
    public void doJob() throws Exception {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
        Date date = c.getTime();
        List<DeliveryOrder> findExpiringOrders = DeliveryOrder.findExpiringOrders(
                DateUtils.addMinutes(date, -1), date);
        for(DeliveryOrder order: findExpiringOrders) {
            if (order.hasPeople()) {
                DeliveryOrderResult close = order.close();
                Mails.sendOrder(close);
            } else {
                order.expire();
            }
            order.validateAndSave();
        }
    }

}
