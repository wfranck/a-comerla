package controllers;

import java.math.BigDecimal;
import java.util.List;

import models.Dish;
import models.Restaurant;
import play.modules.paginate.ModelPaginator;
import play.mvc.Controller;

public class OrderController extends Controller {

    public static void newOrder() {
        final ModelPaginator<Restaurant> restaurants = new ModelPaginator<Restaurant>(Restaurant.class);
        render(restaurants);
    }

    public static void newOrderStep2(final Long id) {
        final Restaurant r =  Restaurant.findById(id);
        final List<Dish> dishes = Dish.find("byRestaurant", r).fetch();
        render(r, dishes);
    }

    public static void create(){

    }

    public static void createDish(final String description, final BigDecimal price, final Long restaurant) {
    	final Restaurant r = Restaurant.findById(restaurant);
    	final Dish dish = new Dish(description,price,r).save();
    	renderJSON(dish);
    }
}
