/**
 * This file has all the javascript code needed for Sales Screen
 * Auto Complete for item lookup functionality
 * 
 */

/**
 * All the global variables for sale screen will be defined here
 */

/**
 * This function will ensure the item auto complete functionality is executed when at least 3 letters has been typed in the item category
 * 
 * @returns
 */
/**
 * The expense related global variables
 */

var token = $("meta[name='_csrf']").attr("content");
var searchBean = new SearchBean();
var selectedAttributes = new Array();
var skuCombinations = new Array();
var attrCount = -1;

var attrAction=new AttributeBean();


$(function() {

	// The auto complete for item attributes starts
	$('#style\\.attrName').autocomplete({
		minLength : 3,
		source : function(request, response) {
			var searchText = this.element[0].value;
			searchBean.searchText = searchText;
			var formdata = JSON.stringify(searchBean);

			// AJAX call here and refresh the Expense Screen after the save
			$.ajax({
				url : '/admin/search_attributes',
				type : 'POST',
				cache : false,
				data : formdata,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					response($.map(data, function(attribute) {
						return {
							name : attribute.name,
							code : attribute.code,
							desc : attribute.description,
							valCode : attribute.valCode,
							attributeId : attribute.attributeId,
							valName : attribute.valName
						}
					}));
				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader('X-CSRF-TOKEN', token)
				}
			});

		},

		select : function(event, ui) {
			event.preventDefault();
			if (ui.item) {
				// Do with the attribute to pull attribute values and ask use to select
				getAttributeValues(event, ui);
			}
		}
	});

	// The auto complete for item attributes ends

});

/* This section will allow the item listing to be in a specific format */
$["ui"]["autocomplete"].prototype["_renderItem"] = function(ul, item) {

	return $("<li></li>").data("item.autocomplete", item).html($('<div/>', {
		'class' : 'row'
	}).append($('<div/>', {
		'class' : 'col-12'
	}).append($('<span/>', {
		html : "<b>" + item.name + "</b><br/>" + item.desc
	})))).appendTo(ul);

};

function retrieveAttribute(attrCode) {
	var alreadyExistingAttr;
	if ((selectedAttributes) && (selectedAttributes.length > 0)) {
		alreadyExistingAttr = $.grep(selectedAttributes, function(attribute) {
			return attribute.code == attrCode;
		})
	}
	return alreadyExistingAttr;

}

function isDuplicateAttribute(attrCode) {
	alreadyExistingAttr = retrieveAttribute();
	if (alreadyExistingAttr && alreadyExistingAttr.length > 0) {
		return true;
	}
	return false;
}

function addSelectedAttribute(attribute) {
	if (!isDuplicateAttribute(attribute.code)) {
		attrCount++;
		var attributeBean = new AttributeBean();
		attributeBean.initialize(attribute.attributeId, attribute.name, attribute.code, attribute.description, attribute.valCode, attribute.valName,
				attribute.valDescription, attribute.valSeqNo);
		selectedAttributes.push(attributeBean);
		return true;
	} else {
		alert('The attribute has already been selected for this item!!');
		return false;
	}

}

function getAttributeValues(event, ui) {

	var attribute = ui.item;

	if (addSelectedAttribute(attribute)) {
		attrAction.renderSelectedAttribute(selectedAttributes);

		var searchText = attribute.code;
		searchBean.searchText = searchText;
		var formdata = JSON.stringify(searchBean);

		// AJAX call here and refresh the Expense Screen after the save
		$.ajax({
			url : '/admin/search_attribute_values',
			type : 'POST',
			cache : false,
			data : formdata,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				addAttributeList(data);
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader('X-CSRF-TOKEN', token)
			}
		});
	}
}

function addAttributeList(data) {
	if (data && data.length > 0) {
		var attrCode = data[0].code;

		var attribute = retrieveAttribute(attrCode);
		if (attribute && attribute.length > 0) {
			var attrValueList = attribute[0].attrValList;

			$.map(data, function(attr, index) {
				attrValueList.push(attr);
			});
		}

	}

	var finalSKUCombinationList=attrAction.generateSKUCombinations(selectedAttributes);
	attrAction.renderSKUs(selectedAttributes,finalSKUCombinationList);

}


function selectAttr(customInput, index) {
	var cntrlId = customInput.id;
	var cbCntrl = cntrlId.replace('attrItem', 'selectedAttributes');
	cbCntrl += '\\.attrValues';
	$('#' + cbCntrl).attr("checked", !$('#' + cbCntrl).attr("checked"));
}

function deleteAttr(attrCode) {
	// Remove the Attribute
	selectedAttributes = $.grep(this.selectedAttributes, function(attribute) {
		return attribute.code != attrCode;
	});
	attrAction.renderSelectedAttribute(selectedAttributes);
	var finalSKUCombinationList=attrAction.generateSKUCombinations(selectedAttributes);
	attrAction.renderSKUs(selectedAttributes,finalSKUCombinationList);	
}