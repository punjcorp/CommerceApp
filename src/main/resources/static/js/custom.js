/**
 * This file contains all the custom JS code for commerce website 
 */
var menuStatus=0;
var accountmenuStatus=0;
var suppliermenuStatus=0;
var stylemenuStatus=0;
var ordermenuStatus=0;

$(document).ready(function() {
	$('.offcanvas').click(function() {
		if(menuStatus==0){
			$('#wrapper').toggleClass('toggled');
		}		

		if(this.id=='suppliermenu'){
			if(suppliermenuStatus==0){
				suppliermenuStatus=1;
				stylemenuStatus=0;
				accountmenuStatus=0;
				ordermenuStatus=0;
			}else{
				suppliermenuStatus=0;
				stylemenuStatus=0;
				accountmenuStatus=0;
				ordermenuStatus=0;
			}
		}
		if(this.id=='stylemenu'){
			if(stylemenuStatus==0){
				stylemenuStatus=1;
				suppliermenuStatus=0;
				accountmenuStatus=0;
				ordermenuStatus=0;
			}else{
				suppliermenuStatus=0;
				stylemenuStatus=0;
				accountmenuStatus=0;
				ordermenuStatus=0;
			}
		}

		if(this.id=='accountmenu'){
			if(accountmenuStatus==0){
				accountmenuStatus=1;
				suppliermenuStatus=0;
				stylemenuStatus=0;
				ordermenuStatus=0;
			}else{
				suppliermenuStatus=0;
				stylemenuStatus=0;
				accountmenuStatus=0;
				ordermenuStatus=0;
			}
		}
		if(this.id=='ordermenu'){
			if(ordermenuStatus==0){
				ordermenuStatus=1;
				accountmenuStatus=0;
				suppliermenuStatus=0;
				stylemenuStatus=0;
			}else{
				suppliermenuStatus=0;
				accountmenuStatus=0;
				stylemenuStatus=0;
				ordermenuStatus=0;
			}
		}		
		
		menuStatus=suppliermenuStatus+stylemenuStatus+accountmenuStatus+ordermenuStatus;
		if(menuStatus<1){
			$('#wrapper').toggleClass('toggled');
		}
		
	});
});