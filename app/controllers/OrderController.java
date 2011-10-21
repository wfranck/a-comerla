package controllers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import models.DeliveryOrder;
import models.Dish;
import models.DishOrder;
import models.DueDateExpirationPolicy;
import models.Restaurant;
import models.User;
import play.data.binding.As;
import play.data.validation.Valid;
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
    
    
    
    public static void create(@Valid final Restaurant restaurant, @Valid final Dish dish, @As("dd/MM/yy HH:mm") final Date date) throws ParseException{
        //TODO: User is mocked
        User user =  User.all().first();
        //TODO: Now we only use Due Date
        DeliveryOrder order = new DeliveryOrder(new DueDateExpirationPolicy(date), restaurant);
        DishOrder dishOrder = new DishOrder(user, dish);
        order.addDishOrder(dishOrder);
        validation.valid(order);
        if (validation.hasErrors()) {
            validation.keep();
            flash.keep();
            newOrderStep2(restaurant.getId());
        }
        redirect("Application.index");
        
    }
    
    public static void createDish(final String description, final BigDecimal price, final Long restaurant) {
    	Restaurant r = Restaurant.findById(restaurant);
    	Dish dish = new Dish(description,price,r).save();
    	renderJSON(dish);
    }
}
