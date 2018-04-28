/**
 * This file contains all the classes needed for a
 * item creation screen
 */

var SearchBean= function() {
	this.searchText;
	this.pager;
}

$.extend(SearchBean.prototype, {

	
});



var AttributeBean= function() {
	this.attributeId;
	this.name;
	this.code;
	this.description;
	this.valCode;
	this.valName;
	this.valDescription;
	this.valSeqNo;
}

$.extend(AttributeBean.prototype, {

	initialize : function( attributeId, name, code, description, valCode, valName, valDescription, valSeqNo){
		this.attributeId=attributeId;
		this.name=name;
		this.code=code;
		this.description=description;
		this.valCode=valCode;
		this.valName=valName;
		this.valDescription=valDescription;
		this.valSeqNo=valSeqNo;		
		
	}
	
});