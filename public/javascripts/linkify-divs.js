$(function(){
    
   $('div.linkeable').click(function(event){
	   window.location = $('.url', $(this)).html(); 
   });
    
    
});    

