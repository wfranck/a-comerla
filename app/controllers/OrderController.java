package controllers;

import java.util.List;

import models.Dish;
import models.Restaurant;
import play.modules.paginate.ModelPaginator;
import play.mvc.Controller;

public class OrderController extends Controller {

    public static void newOrder() {
        ModelPaginator<Restaurant> restaurants = new ModelPaginator<Restaurant>(Restaurant.class);
        render(restaurants);
    }
    
    public static void newOrderStep2(final Long id) {
        Restaurant r =  Restaurant.findById(id);
        List<Dish> dishes = Dish.findAll();
        render(r, dishes);
    }
    
    public static void create(){
        
    }
    
    public static void createDish() {
        
    }
}
