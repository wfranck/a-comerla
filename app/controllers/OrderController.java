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
import play.mvc.Http.StatusCode;
import serializers.DishSerializer;
import serializers.Serializer;

public class OrderController extends Controller {

    public static void newOrder() {
        final ModelPaginator<Restaurant> restaurants = new ModelPaginator<Restaurant>(Restaurant.class);
        render(restaurants);
    }

    public static void newOrderStep2(final Long id) {
        Restaurant r= Restaurant.findById(id);
        List<Restaurant> dishes = Dish.findByRestaurant(r);
        render(r, dishes);
    }
    public static void list() {
        ModelPaginator<DeliveryOrder> orders = 
            new ModelPaginator<DeliveryOrder>(DeliveryOrder.class, "expirationPolicy.expirationDate >= ?", new Date());
        orders.getPageCount();
        render(orders);
    }
    
    public static void show(final Long id) {
        DeliveryOrder order = DeliveryOrder.findById(id);
        List<Restaurant> dishes = Dish.findByRestaurant(order.restaurant);
        render(order, dishes);
    }
    
    public static void addNewDishOrder(final DeliveryOrder order, final Dish dish) {
      //TODO: User is mocked
        final User user =  User.all().first();
        order.addDishOrder(new DishOrder(user, dish));
        if (!order.validateAndSave()) {
            validation.keep();
            flash.keep();
            show(order.id);
        }
        flash.success("Se guardo OK tu pedido", order);
        list();
    }
    
    
    
    public static void create(@Valid final Restaurant restaurant, @Valid final Dish dish, @As("dd/MM/yy HH:mm") final Date date) throws ParseException{
        //TODO: User is mocked
        final User user =  User.all().first();
        //TODO: Now we only use Due Date
        final DeliveryOrder order = new DeliveryOrder(new DueDateExpirationPolicy(date), restaurant);
        final DishOrder dishOrder = new DishOrder(user, dish);
        order.addDishOrder(dishOrder);
        validation.valid(order);
        if (validation.hasErrors()) {
            validation.keep();
            flash.keep();
            newOrderStep2(restaurant.getId());
        }
        order.validateAndCreate();
        redirect("Application.index");

    }

    /**
     * Creates a new Dish
     * @param description
     * @param price
     * @param restaurant
     */
    public static void createDish(final String description, final BigDecimal price, final Long restaurant) {
        final Serializer serializer = new DishSerializer();

    	final Restaurant r = Restaurant.findById(restaurant);
    	final Dish dish = new Dish(description,price,r);

    	if(validation.valid(dish).ok){
    	    response.status = StatusCode.CREATED;
    	    r.save();

    	    renderJSON(serializer.serialize(dish));
    	}
    	else{
    	    response.status = StatusCode.BAD_REQUEST;
    	}
    }
}
