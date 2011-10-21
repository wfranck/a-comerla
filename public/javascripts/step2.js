$(function(){
    $(".addDish").click(function(){
        var description = $(".description").val();
        var price = $(".price").val();
        var restaurant = $(".restaurant").val();
        
        $.post(createDish({description: description, price: price, restaurant: restaurant}), function(data){
            addOptionInCombo(data);
        })
        .error(function(){
            alert("No se pudo cargar tu Alto Guiso");
        });
    });
    
    /**
     * After created a new Dish, a json is returned to add the new element to the combo
     */
    function addOptionInCombo(dish){
        $('.dishes')
                .append($('<option>', { value : dish.id })
                .text(dish.description + " | $ " + dish.price ));
    };
    
});    

