package controllers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import models.DeliveryOrder;
import models.Dish;
import models.DishChildOrder;
import models.DishOrder;
import models.DueDateExpirationPolicy;
import models.Restaurant;
import models.User;
import play.data.binding.As;
import play.data.validation.Error;
import play.data.validation.InFuture;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http.StatusCode;
import serializers.DishSerializer;
import serializers.Serializer;

public class OrderController extends Controller {
    
    @Before
    public static void renderOrders() {
        List<DeliveryOrder> orders = DeliveryOrder.find("expirationPolicy.expirationDate >= ?", new Date()).fetch();
        renderArgs.put("orders", orders);
    }

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

    public static void addNewDishOrder(@Valid final DeliveryOrder order, @Valid final Dish dish) {
        final User user =  Security.connected();
        DishOrder dishOrder = DishOrder.findExistent(order, user);
        if (dishOrder == null) {
            dishOrder = new DishOrder(user, dish);
            order.addDishOrder(new DishOrder(user, dish));
        } else {
            dishOrder.dishes.add(new DishChildOrder(dish));
        }
        if (!order.validateAndSave()) {
            validation.keep();
            params.flash();
            addToOrder(order.id);
        }
        flash.success("Se guardo OK tu pedido");
        index();
    }



    public static void createNewOrder(@Valid @Required final Restaurant restaurant, @Valid @Required final Dish dish,@Required @InFuture   @As("dd/MM/yy HH:mm") final Date date) throws ParseException{
        final User user =  Security.connected();
        final DeliveryOrder order = new DeliveryOrder(new DueDateExpirationPolicy(date), restaurant);
        final DishOrder dishOrder = new DishOrder(user, dish);
        order.addDishOrder(dishOrder);
        if (validation.hasErrors()) {
            validation.keep();
            Error error = validation.error("dish.description");
            if (error != null) {
                validation.addError("dish.id", error.message());
            }
            params.flash();
            createForRestaurant(restaurant.getId());
        }
        order.validateAndCreate();
        index();

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
