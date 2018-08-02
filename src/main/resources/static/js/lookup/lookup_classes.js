/**
 * This file contains all the classes needed for 
 * supplier account lookup
 */

var AccountDTO= function() {
	this.entityId;
	this.entityType;
	this.searchAccount;
}

$.extend(AccountDTO.prototype, {

	
});

var AccountHead= function() {
	this.entityId;
	this.entityType;
	this.locationId;
	this.accountId;
	this.entityName;
	this.advanceAmt=0.00;
	this.dueAmt=0.00;
	this.paymentAmt=0.00;	
	this.accountIds=new Array();
	this.locationIds=new Array();
}

$.extend(AccountHead.prototype, {

	
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

