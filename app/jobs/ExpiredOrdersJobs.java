package jobs;

import java.util.Date;
import java.util.List;

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
        Date date = new Date();
        List<DeliveryOrder> findExpiringOrders = DeliveryOrder.findExpiringOrders(
                DateUtils.addMinutes(date, -1), date);
        for(DeliveryOrder order: findExpiringOrders) {
            DeliveryOrderResult close = order.close();
            order.validateAndSave();
            Mails.sendOrder(close);
            close.validateAndCreate();
        }
    }

}
