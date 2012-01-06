$(document).ready(function(){
    //Delete line button
    $('.delete').click(function(e) {
        //Check if there's at least one
        e.preventDefault();
        var deleteableContainer = $(this).closest('.list');
        var deleteables = $('.deleteable', deleteableContainer);
        if (deleteables.size() <= 1) {
            return;
        }
        var element = $(this).closest('.deleteable');
        element.remove();
    });
    $('.adder').click(function(e){
        e.preventDefault();
        var list = $(this).closest('.list');
        var className = $('.template-class', list).text();
        var deleteable = $('.' + className).first();
        var cloned = deleteable.clone(true).removeClass('template').removeClass('hidden');
        cloned.insertAfter($('.deleteable:last', list));
    });
    $('.sender').click(function(){
        var lists = $('.list');
        lists.each(function(){
            value = 0;
            $('.deleteable', $(this)).each(function(){
                $("input[type='text'],input[type='hidden'],,input[type='file']", $(this)).each(function(){
                    $(this).attr('name', $(this).attr('name').replace(/\d+/, value));
                });
                $(".order", $(this)).each(function(){
                    $(this).attr('value', $(this).attr('value').replace(/\d+/, value));
                });
                value++;
            });
        });
        return true;
    });
});