package controllers;

import java.util.Date;
import java.util.List;

import models.DeliveryOrder;
import models.DishChildOrder;
import models.DishOrder;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;

public class SideBarController extends Controller {

    @Before
    public static void renderOrders() {
        User u = Security.connected();
        if (u != null) {
            List<DishChildOrder> dishes =  DishOrder.findForUser(u);
            boolean showMine = false;
            if (!dishes.isEmpty()) {
                showMine = true;
                renderArgs.put("alldishes", dishes);
            }
            List<DeliveryOrder> orders = DeliveryOrder.find("expirationPolicy.expirationDate >= ? order by expirationPolicy.expirationDate desc", new Date()).fetch();
            renderArgs.put("orders", orders);
            renderArgs.put("showMine", showMine);
        }
    }
}
