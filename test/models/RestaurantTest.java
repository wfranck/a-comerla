package models;

import org.junit.Test;

import play.test.UnitTest;

public class RestaurantTest extends UnitTest {
    
    @Test
    public void testSaveRestaurant() {
        Restaurant r = new Restaurant("Las cabras", "4855-9589");
        r.save();
        
    }

}
