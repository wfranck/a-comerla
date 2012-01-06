package controllers;

import models.ImageRestaurant;
import models.Restaurant;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
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

}
