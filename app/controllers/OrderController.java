package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        notFoundIfNull(r);
        List<Restaurant> dishes = Dish.findByRestaurant(r);
        render(r, dishes);
    }
    
    public static void addToOrder(final Long id) {
        DeliveryOrder order = DeliveryOrder.findById(id);
        notFoundIfNull(order);
        List<Restaurant> dishes = Dish.findByRestaurant(order.restaurant);
        render(order, dishes);
    }
    
    public static void newOrder(final Long restaurantId, final Long dishId) {
        Restaurant r = Restaurant.findById(restaurantId);
        Dish dish = Dish.findById(dishId);
        notFoundIfNull(r);
        notFoundIfNull(dish);
        validateDishAndRestaurant(r, dish);
        render(r, dish);
    }
    
    public static void addToExistentOrder(final Long orderId, final Long dishId) {
        DeliveryOrder order = DeliveryOrder.findById(orderId);
        Dish dish = Dish.findById(dishId);
        notFoundIfNull(order);
        notFoundIfNull(dish);
        validateDishAndRestaurant(order.restaurant, dish);
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



    public static void createNewOrder(@Valid @Required final Restaurant restaurant, @Valid @Required final Dish dish,@Required  final String date) throws ParseException{
        Date theDate = parseDate(date);
        final User user =  Security.connected();
        validateDishAndRestaurant(restaurant, dish);
        theDate = validateDate(theDate);
        final DeliveryOrder order = new DeliveryOrder(new DueDateExpirationPolicy(theDate), restaurant);
        final DishOrder dishOrder = new DishOrder(user, dish);
        order.addDishOrder(dishOrder);
        if (validation.hasErrors()) {
            validation.keep();
            validateDish();
            params.flash();
            newOrder(restaurant.id, dish.id);
        }
        order.validateAndCreate();
//        Mails.newOrder(order);
        index();

    }

    /**
     * @param restaurant
     * @param dish
     */
    private static void validateDishAndRestaurant(final Restaurant restaurant, final Dish dish) {
        if (!dish.restaurant.id.equals(restaurant.id)) {
            notFound();
        }
    }

    /**
     * @param theDate
     * @return
     */
    private static Date validateDate(Date theDate) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
        if (theDate != null) {
            theDate = todayize(theDate);
            validation.future("date", theDate, c.getTime());
        }
        return theDate;
    }

    /**
     * 
     */
    private static void validateDish() {
        Error error = validation.error("dish.description");
        if (error != null) {
            validation.addError("dish.id", error.message());
        }
    }

    /**
     * @param date
     * @return
     */
    private static Date parseDate(final String date) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        Date theDate = null;
        try {
            theDate = df.parse(date);
        } catch (ParseException ex) {
            validation.addError("date", "validation.future");
        }
        return theDate;
    }
    
    public static void deleteDish(final Long dishOrderId,
            final Long dishId) {
        DeliveryOrder order = DeliveryOrder.findByDishOrderId(dishOrderId);
        DishOrder dishOrder = DishOrder.findById(dishOrderId);
        Dish dish = Dish.findById(dishId);
        if (!dishOrder.user.id.equals(Security.connected().id)) {
            notFound();
        }
        notFoundIfNull(order);
        notFoundIfNull(dishOrder);
        notFoundIfNull(dish);
        if (order.remove(dishOrder, dish)) {
            order.delete();
        } else {
            order.save();
        }
        OrderController.index();
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
