package controllers;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import models.DeliveryOrder;
import models.Dish;
import models.DishChildOrder;
import models.DishOrder;
import models.DueDateExpirationPolicy;
import models.Restaurant;
import models.User;
import play.data.binding.As;
import play.data.validation.Error;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;
import controllers.securesocial.SecureSocial;
@With(value = {SideBarController.class, SecureSocial.class})
//@With(value = {SideBarController.class})
public class OrderController extends Controller {
    

    public static void index() {
        final List<Restaurant> restaurants = Restaurant.all().fetch();
        render(restaurants);
    }

    public static void createForRestaurant(final Long id) {
        Restaurant r= Restaurant.findById(id);
        List<Restaurant> dishes = Dish.findByRestaurant(r);
        render(r, dishes);
    }
    
    public static void addToOrder(final Long id) {
        DeliveryOrder order = DeliveryOrder.findById(id);
        List<Restaurant> dishes = Dish.findByRestaurant(order.restaurant);
        render(order, dishes);
    }
    
    public static void newOrder(final Long restaurantId, final Long dishId) {
        Restaurant r = Restaurant.findById(restaurantId);
        Dish dish = Dish.findById(dishId);
        notFoundIfNull(r);
        notFoundIfNull(dish);
        render(r, dish);
    }
    
    public static void addToExistentOrder(final Long orderId, final Long dishId) {
        DeliveryOrder order = DeliveryOrder.findById(orderId);
        Dish dish = Dish.findById(dishId);
        notFoundIfNull(order);
        notFoundIfNull(dish);
        render(order, dish);
    }
    
    

    public static void addNewDishOrder(@Valid final DeliveryOrder order, @Valid final Dish dish) {
        final User user =  Security.connected();
        DishOrder dishOrder = DishOrder.findExistent(order, user);
        if (dishOrder == null) {
            dishOrder = new DishOrder(user, dish);
            order.addDishOrder(new DishOrder(user, dish));
        } else {
            dishOrder.dishes.add(new DishChildOrder(dish, dishOrder));
            dishOrder.validateAndSave();
        }
        if (!order.validateAndSave()) {
            validation.keep();
            params.flash();
            addToOrder(order.id);
        }
        flash.success("Se guardo OK tu pedido");
        index();
    }



    public static void createNewOrder(@Valid @Required final Restaurant restaurant, @Valid @Required final Dish dish,@Required @As("HH:mm") final Date date) throws ParseException{
        final User user =  Security.connected();
        Date theDate = date;
        if (date != null) {
            theDate = todayize(date);
            validation.future("date", (Object) theDate);
        }
        final DeliveryOrder order = new DeliveryOrder(new DueDateExpirationPolicy(theDate), restaurant);
        final DishOrder dishOrder = new DishOrder(user, dish);
        order.addDishOrder(dishOrder);
        if (validation.hasErrors()) {
            validation.keep();
            Error error = validation.error("dish.description");
            if (error != null) {
                validation.addError("dish.id", error.message());
            }
            params.flash();
            newOrder(restaurant.id, dish.id);
        }
        order.validateAndCreate();
        index();

    }
    
    public static void deleteDish(final Long dishOrderId) {
        DishOrder dishOrder = DishOrder.findById(dishOrderId);
        notFoundIfNull(dishOrder);
        dishOrder.order.dishOrders.remove(dishOrder);
        dishOrder.delete();
        dishOrder.order.validateAndSave();
        index();
    }

    private static Date todayize(final Date date) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
        Calendar cDate = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
        cDate.setTime(date);
        c.set(Calendar.HOUR, cDate.get(Calendar.HOUR));
        c.set(Calendar.MINUTE, cDate.get(Calendar.MINUTE));
        c.set(Calendar.AM_PM, cDate.get(Calendar.AM_PM));
        return c.getTime();
    }

}
