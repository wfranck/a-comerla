package controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import models.DeliveryOrder;
import models.Dish;
import models.Restaurant;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Cookie;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

import com.google.common.collect.Maps;

public class OrderControllerTest extends FunctionalTest {

    private static final String LOCATION_HEADER = "Location";
    private Map<String, Cookie> cookies;

    @Before
    public void setUp() {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("data.yml");
    }

    private void LOGIN() {
        Map<String, String> loginUserParams = new HashMap<String, String>();
        loginUserParams.put("username", "admin");
        loginUserParams.put("password", "admin");
        Response loginResponse = POST("/secure/authenticate", loginUserParams);
        cookies = loginResponse.cookies;
    }

    @Test
    public void testNewOrderShow() {
        LOGIN();
        Response response = SECURED_GET("/order/new");
        assertStatus(200, response);
    }

    @Test
    public void testNewOrderPostFailed() {
        LOGIN();
        Map<String, String> map = Maps.newHashMap();
        map.put("date", "hola");
        String restId = Restaurant.all().<Restaurant>first().id.toString();
        map.put("restaurant.id", restId);
        map.put("dish.id", Dish.all().<Dish>first().id.toString());
        Response response = SECURED_POST("/order/new", map);
        assertStatus(302, response); //Redirected for error
        assertHeaderEquals(LOCATION_HEADER, "/order/new/" + restId, response);
    }

    @Test
    public void testNewOrderPost() {
        LOGIN();
        Map<String, String> map = Maps.newHashMap();
        map.put("date", "12/12/2012 16:23");
        String restId = Restaurant.all().<Restaurant>first().id.toString();
        map.put("restaurant.id", restId);
        map.put("dish.id", Dish.all().<Dish>first().id.toString());
        Response response = SECURED_POST("/order/new", map);
        assertStatus(302, response); //Redirected for error
        assertHeaderEquals(LOCATION_HEADER, "/", response); //Redirected to index if it's ok

        Assert.assertEquals(1, DeliveryOrder.all().fetch().size());
    }

    private Response SECURED_GET(final String url) {
        Request request = newRequest();
        request.cookies = cookies;
        return GET(request, url);
    }

    private Response SECURED_POST(final String url, final Map<String, String> params) {
        Request request = newRequest();
        request.cookies = cookies; // this makes the request authenticated
        return POST(request, url, params, new HashMap<String, File>());
    }


}
