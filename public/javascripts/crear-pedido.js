$(function(){
    
    /**
     * Despues de creado un nuevo Dish, el post responde el json con el nuevo ID para el dish, y lo agregamos en el combo de dishes.
     */
    
    $(".addDish").click(function(){
        var description = $(".description").val();
        var price = $(".price").val();
        var restaurant = $(".restaurant").val();
        
        $.post(createDish({description: description, price: price, restaurant: restaurant}), function(data){
            addOptionInCombo(data);
        })
        .error(function(){
            var error = $(".errorOn-dishCreation").html();
            alert(error);
        });
    });
    
    function addOptionInCombo(dish){
    	var option = $('<option>', { value : dish.id });
    	
        $('.dishes').append(option);
    	
    	option.attr('selected', 'selected').text(dish.description + " | $ " + dish.price );
    };
	$('.date').datetimepicker({
		dateFormat: 'dd/mm/yy',
		timeFormat: 'hh:mm'
	});
    
    
});    

