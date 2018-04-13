/**
 * This file contains all the custom JS code for commerce website
 */
var menuStatus = 0;
var accountmenuStatus = 0;
var suppliermenuStatus = 0;
var stylemenuStatus = 0;
var ordermenuStatus = 0;
var inventorymenuStatus = 0;
var lookupmenuStatus = 0;
var pricemenuStatus = 0;

$(document).ready(function() {

	$('#langSelect').change(function() {

		var langSelected = $('#langSelect').val();
		var currentUrl = window.location.href;
		currentUrl=removeParam('lang',currentUrl);

		if (currentUrl.indexOf("?") > 0) {
			currentUrl = currentUrl + '&lang=' + langSelected;
		} else {
			currentUrl = currentUrl + '?lang=' + langSelected;
		}

		window.location.href = currentUrl;
	});

	$('.offcanvas').click(function() {
		if (menuStatus == 0) {
			$('#wrapper').toggleClass('toggled');
		}

		if (this.id == 'suppliermenu') {
			if (suppliermenuStatus == 0) {
				suppliermenuStatus = 1;
				stylemenuStatus = 0;
				accountmenuStatus = 0;
				ordermenuStatus = 0;
				inventorymenuStatus = 0;
				lookupmenuStatus = 0;
				pricemenuStatus = 0;
			} else {
				suppliermenuStatus = 0;
				stylemenuStatus = 0;
				accountmenuStatus = 0;
				ordermenuStatus = 0;
				inventorymenuStatus = 0;
				lookupmenuStatus = 0;
				pricemenuStatus = 0;
			}
		}
		if (this.id == 'stylemenu') {
			if (stylemenuStatus == 0) {
				stylemenuStatus = 1;
				suppliermenuStatus = 0;
				accountmenuStatus = 0;
				ordermenuStatus = 0;
				inventorymenuStatus = 0;
				lookupmenuStatus = 0;
				pricemenuStatus = 0;
			} else {
				suppliermenuStatus = 0;
				stylemenuStatus = 0;
				accountmenuStatus = 0;
				ordermenuStatus = 0;
				inventorymenuStatus = 0;
				lookupmenuStatus = 0;
				pricemenuStatus = 0;
			}
		}

		if (this.id == 'accountmenu') {
			if (accountmenuStatus == 0) {
				accountmenuStatus = 1;
				suppliermenuStatus = 0;
				stylemenuStatus = 0;
				ordermenuStatus = 0;
				inventorymenuStatus = 0;
				lookupmenuStatus = 0;
				pricemenuStatus = 0;
			} else {
				suppliermenuStatus = 0;
				stylemenuStatus = 0;
				accountmenuStatus = 0;
				ordermenuStatus = 0;
				inventorymenuStatus = 0;
				lookupmenuStatus = 0;
				pricemenuStatus = 0;
			}
		}
		if (this.id == 'ordermenu') {
			if (ordermenuStatus == 0) {
				ordermenuStatus = 1;
				accountmenuStatus = 0;
				suppliermenuStatus = 0;
				stylemenuStatus = 0;
				inventorymenuStatus = 0;
				lookupmenuStatus = 0;
				pricemenuStatus = 0;
			} else {
				suppliermenuStatus = 0;
				accountmenuStatus = 0;
				stylemenuStatus = 0;
				ordermenuStatus = 0;
				inventorymenuStatus = 0;
				lookupmenuStatus = 0;
				pricemenuStatus = 0;
			}
		}
		if (this.id == 'inventorymenu') {
			if (inventorymenuStatus == 0) {
				ordermenuStatus = 0;
				accountmenuStatus = 0;
				suppliermenuStatus = 0;
				stylemenuStatus = 0;
				lookupmenuStatus = 0;
				inventorymenuStatus = 1;
				pricemenuStatus = 0;
			} else {
				suppliermenuStatus = 0;
				accountmenuStatus = 0;
				stylemenuStatus = 0;
				ordermenuStatus = 0;
				inventorymenuStatus = 0;
				lookupmenuStatus = 0;
				pricemenuStatus = 0;
			}
		}
		if (this.id == 'lookupmenu') {
			if (lookupmenuStatus == 0) {
				ordermenuStatus = 0;
				accountmenuStatus = 0;
				suppliermenuStatus = 0;
				stylemenuStatus = 0;
				lookupmenuStatus = 1;
				inventorymenuStatus = 0;
				pricemenuStatus = 0;
			} else {
				suppliermenuStatus = 0;
				accountmenuStatus = 0;
				stylemenuStatus = 0;
				ordermenuStatus = 0;
				inventorymenuStatus = 0;
				lookupmenuStatus = 0;
				pricemenuStatus = 0;
			}
		}
		if (this.id == 'pricemenu') {
			if (lookupmenuStatus == 0) {
				ordermenuStatus = 0;
				accountmenuStatus = 0;
				suppliermenuStatus = 0;
				stylemenuStatus = 0;
				lookupmenuStatus = 0;
				inventorymenuStatus = 0;
				pricemenuStatus = 1;
			} else {
				suppliermenuStatus = 0;
				accountmenuStatus = 0;
				stylemenuStatus = 0;
				ordermenuStatus = 0;
				inventorymenuStatus = 0;
				lookupmenuStatus = 0;
				pricemenuStatus = 0;
			}
		}
		menuStatus = suppliermenuStatus + stylemenuStatus + accountmenuStatus + ordermenuStatus + inventorymenuStatus + lookupmenuStatus + pricemenuStatus;
		if (menuStatus < 1) {
			$('#wrapper').toggleClass('toggled');
		}

	});
});


function removeParam(key, sourceURL) {
    var rtn = sourceURL.split("?")[0],
        param,
        params_arr = [],
        queryString = (sourceURL.indexOf("?") !== -1) ? sourceURL.split("?")[1] : "";
    if (queryString !== "") {
        params_arr = queryString.split("&");
        for (var i = params_arr.length - 1; i >= 0; i -= 1) {
            param = params_arr[i].split("=")[0];
            if (param === key) {
                params_arr.splice(i, 1);
            }
        }
        if(params_arr.length<1){
        	rtn = rtn;
        }else if(params_arr.length==1){
        	rtn = rtn + "?" + params_arr.join();
        }else{
        	rtn = rtn + "?" + params_arr.join("&");
        }
        
    }
    return rtn;
}
