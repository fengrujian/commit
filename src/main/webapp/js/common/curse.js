// JavaScript Document
$(function(){
	$(document).on('mouseenter', '.curse-content li', function(){
	    $(this).find('.user').addClass('on'); 
	    $(this).find('.shade')
	        .stop()
	        .animate({ opacity : '1'}, 300);
	});
	$(document).on('mouseleave', '.curse-content li', function(){
	    $(this).find('.user').removeClass('on');
	    $(this).find('.shade')
	        .stop()
	        .animate({ opacity : '0'}, 300);
	});

});