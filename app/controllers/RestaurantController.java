package controllers;

import models.DeliveryOrder;
import models.Dish;
import models.ImageRestaurant;
import models.Restaurant;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;
import controllers.securesocial.SecureSocial;

@With(value = {SideBarController.class, SecureSocial.class})
public class RestaurantController extends Controller {
    
    public static void newRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.images.add(new ImageRestaurant());
        render(restaurant);
    }
    
    public static void create(@Valid final Restaurant restaurant) {
        if (validation.hasErrors()) {
            params.flash();
            render("@newRestaurant", restaurant);
        }
        restaurant.validateAndSave();
        OrderController.index();
    }
    
    public static void image(final Long id) {
        ImageRestaurant ir = ImageRestaurant.findById(id);
        notFoundIfNull(ir);
        renderBinary(ir.image.getFile());
    }
    
    public static void newDish(@Required final boolean isNew, final Long orderId,
            final Long restaurantId) {
        Restaurant r = Restaurant.findById(restaurantId);
        notFoundIfNull(r);
        checkForNotFound(isNew, orderId, restaurantId);
        render(isNew, orderId, restaurantId, r);
    }

    /**
     * @param isNew
     * @param orderId
     * @param restaurantId
     */
    private static void checkForNotFound(final boolean isNew, final Long orderId, final Long restaurantId) {
        if (!isNew && orderId == null) {
            notFound();
        }
        if (orderId != null) {
            DeliveryOrder order = DeliveryOrder.findById(orderId);
            notFoundIfNull(order);
        }
    }
    
    public static void addDish(@Valid final Dish dish, @Required final boolean isNew, final Long orderId) {
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            newDish(isNew, orderId, dish.restaurant.id);
        }
        dish.validateAndSave();
        if (isNew) {
            OrderController.createForRestaurant(dish.restaurant.id);
        } else {
            DeliveryOrder order = DeliveryOrder.findById(orderId);
            notFoundIfNull(order);
            OrderController.addToOrder(orderId);
        }
        
    }

}
