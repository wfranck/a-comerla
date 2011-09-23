package controllers;

import models.ImageRestaurant;



public class Restaurants extends CRUD {
    
    public static void addImage() {
        redirect("ImageRestaurantController.blank");
    }
    
    public static void getPicture(final Long id) {
        ImageRestaurant img =  ImageRestaurant.findById(id);
        renderBinary(img.image.get());
    }
    
    public static void deletePicture(final Long id) {
        ImageRestaurant img =  ImageRestaurant.findById(id);
        img.delete();
        img.restaurant.images.remove(img);
        redirect("Restaurants.show");
    }

}
