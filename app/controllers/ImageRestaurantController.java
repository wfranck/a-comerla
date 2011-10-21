package controllers;

import models.ImageRestaurant;
import play.mvc.With;

@CRUD.For(ImageRestaurant.class)
@With(Secure.class)
public class ImageRestaurantController extends CRUD {

}
