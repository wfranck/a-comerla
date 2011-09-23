package controllers;

import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        redirect("OrderController.newOrder");
    }
    
    public static void adminHome() {
        redirect("/admin");
    }

}