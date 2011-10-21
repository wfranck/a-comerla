$(function(){
    $(".addDish").click(function(){
        var description = $(".description").val();
        var price = $(".price").val();
        var restaurant = $(".restaurant").val();
        
        $.post(createDish({description: description, price: price, restaurant: restaurant}), function(){
            addOptionInCombo();
        })
        .error(function(){
            alert("No se pudo cargar tu Alto Guiso");
        });
    });
    
    
    function addOptionInCombo(){
        alert(":D")
    };
    
});    

