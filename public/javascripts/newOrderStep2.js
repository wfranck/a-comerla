$(function(){
    
    var createDish = #{jsAction @OrderController.createDish(':description', ':price', ':restaurant') /}
    
    $("#addDish").click(function(){
        var dishDescription = $("#dishDescription").val();
        var dishPrice = $("#dishPrice").val();
        
        $.post(createDish(),{description: dishDescription, price: dishPrice, restaurant: '${r.id}' });
    });
    
})();    

