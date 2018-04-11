/**
 * This file contains all the classes needed for a
 * simple no sale expense transaction
 */

var AccountDTO= function() {
	this.entityId;
	this.entityType;
}

$.extend(AccountDTO.prototype, {

	
});

var SearchBean= function() {
	this.searchText;
	this.pager;
}

$.extend(SearchBean.prototype, {

	
});


var Address = function() {
	this.addressId;
	this.addressType;
	this.primary;
	this.address1;
	this.address2;
	this.city;
	this.state;
	this.country;
	this.pincode;
}

$.extend(Address.prototype, {

});

var Supplier = function() {
	this.supplierId;
	this.name;
	this.phone1;
	this.phone2;
	this.email;
	this.createdBy;
	this.createdDate;

	this.primaryAddress = new Address();

}

$.extend(Supplier.prototype, {

});

var Order= function() {
	this.orderId;
	this.supplierId;
	this.locationId;
	this.comments;
}

$.extend(Order.prototype, {

	
});


var OrderItem = function() {
	this.itemId;
	this.itemName;
	this.itemDesc;
	this.qty;
	this.price;
	this.discount;
	this.cgstTax;
	this.sgstTax;
	this.igstTax;
	this.cgstTaxRate;
	this.sgstTaxRate;
	this.igstTaxRate;
	this.itemTotal;
}

$.extend(OrderItem.prototype, {

});
