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
var selectedAttrValues = new Array();

$(function() {

	// The auto complete for item hierarchy starts
	$("#hierarchy\\.name").autocomplete({
		minLength : 3,
		source : function(request, response) {
			searchBean.searchText = $("#hierarchy\\.name").val();
			var formdata = JSON.stringify(searchBean);

			// AJAX call here and refresh the Expense Screen after the save
			$.ajax({
				url : '/admin/search_hierarchy',
				type : 'POST',
				cache : false,
				data : formdata,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(hierarchies) {
					response($.map(hierarchies, function(hierarchy) {
						return {
							name : hierarchy.name,
							code : hierarchy.code,
							hierarchyId : hierarchy.hierarchyId,
							desc : hierarchy.description,
							createdBy : hierarchy.createdBy
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
				$('#hierarchy\\.hierarchyId').val(ui.item.hierarchyId);
				$('#hierarchy\\.name').val(ui.item.name);
			}
		}
	});
	// The auto complete for item hierarchy ends

	// The auto complete for item attributes starts
	$('[type=text][id^=attributes][id$=\\.name]').autocomplete({
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

	//The event for file upload starts
	//*
	$("[id^=itemImages][id$=imageData]").change(function () {

	        if (typeof (FileReader) != "undefined") {
	        	
	        	if(this.files.length>4){
	        		alert("Please select max 4 pictures to upload.");
	        		this.preventDefault();
	        	}else{
		        	var imageIndex=this.id.replace(/[^0-9]/gi, '');

		            var image_holder = $("#preview"+imageIndex);
		            image_holder.empty();

		            var reader = new FileReader();
		            reader.onload = function (e) {
		                $("<img />", {
		                    "src": e.target.result,
		                    "class": "img-fluid img-thumbnail"
		                }).appendTo(image_holder);

		            }
		            image_holder.show();
		            reader.readAsDataURL($(this)[0].files[0]);
	        	}
	        	

	        } else {
	            alert("This browser does not support FileReader.");
	        }
	    });
	//*
	
	//The event for file upload ends
});

/* This section will allow the item listing to be in a specific format */
$["ui"]["autocomplete"].prototype["_renderItem"] = function(ul, item) {

	if (this.element[0].id.indexOf('hierarchy') > -1) {
		return $("<li></li>").data("item.autocomplete", item).html($('<div/>', {
			'class' : 'row'
		}).append($('<div/>', {
			'class' : 'col-12'
		}).append($('<span/>', {
			html : "<b>" + item.name + "</b><br/>" + item.desc
		})))).appendTo(ul);
	} else {
		return $("<li></li>").data("item.autocomplete", item).html($('<div/>', {
			'class' : 'row'
		}).append($('<div/>', {
			'class' : 'col-12'
		}).append($('<span/>', {
			html : "<b>" + item.name + "</b><br/>" + item.desc
		})))).appendTo(ul);
	}

};

function showPopUpBusy() {
	$('#progressDiv').removeClass("d-none");

}

function hidePopUpBusy() {
	$('#progressDiv').addClass("d-none");

}

function isDuplicateAttribute(attrCode) {
	if ((selectedAttributes) && (selectedAttributes.length > 0)) {
		var alreadyExistingAttr = $.grep(selectedAttributes, function(attribute) {
			return attribute.code == attrCode;
		})
		if (alreadyExistingAttr && alreadyExistingAttr.length > 0) {
			return true;
		}
	}
	return false;
}


function addSelectedAttribute(attribute) {
	if(!isDuplicateAttribute(attribute.code)){
		var attributeBean = new AttributeBean();
		attributeBean.initialize(attribute.attributeId, attribute.name, attribute.code, attribute.description, attribute.valCode, attribute.valName,
				attribute.valDescription, attribute.valSeqNo);
		selectedAttributes.push(attributeBean);		
		return true;
	}else{
		alert('The attribute has already been selected for this item!!');
		return false;
	}

}

function getAttributeValues(event, ui) {
	showPopUpBusy();

	var attribute = ui.item;

	if (addSelectedAttribute(attribute)) {

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
				displayAttributeValues(data);

			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader('X-CSRF-TOKEN', token)
			}
		});
	}
	hidePopUpBusy();
}

function displayAttributeValues(data) {

	var outputHtml = '<div class="row">';

	var outputColLeftHtml = '';
	outputColLeftHtml += '<div class="col-xs-6 col-sm-6 col-md-6">';
	outputColLeftHtml += '<div class="form-group">';
	outputColLeftHtml += '<label><small><span>Selected Item Attribute</span></small></label>';
	outputColLeftHtml += '<div>' + data[0].name + '</div>';
	outputColLeftHtml += '</div>';
	outputColLeftHtml += '</div>';

	var outputColRightHtml = '';
	outputColRightHtml += '<div class="col-xs-6 col-sm-6 col-md-6">';
	outputColRightHtml += '<div class="form-group">';
	outputColRightHtml += '<label><small><span>Select Item Attribute Values</span></small></label>';
	outputColRightHtml += '<div class="input-group">';
	outputColRightHtml += '<div class="list-group col-12">';

	$.map(data, function(attribute, index) {

		outputColRightHtml += '<a class="list-group-item list-group-item-action" id="attrValItem'+attribute.code + index + '" onClick="selectAttrValue(this, ' + index + ')">';
		outputColRightHtml += '<div class="form-check float-left">';
		outputColRightHtml += '<input type="checkbox" class="form-check-input" id="attrValCB'+attribute.code + index + '" name="attrValCB" value="' + attribute.valCode
				+ '"></input> ';
		outputColRightHtml += '</div>';
		outputColRightHtml += '<span class="mx-4">' + attribute.valName + '</span></a>';
	});

	outputColRightHtml += '</div>';
	outputColRightHtml += '</div>';
	outputColRightHtml += '</div>';
	outputColRightHtml += '</div>';

	outputHtml = '<div class="row">' + outputColLeftHtml + outputColRightHtml + '</div>';

	var existingHtml = $('#attributesDiv').html();

	// $('#attrValContent').html(outputHtml);
	$('#attributesDiv').html(outputHtml + existingHtml);

	

}

function selectAttrValue(customInput, index) {
	var cntrlId=customInput.id;
	var cbCntrl=cntrlId.replace('attrValItem','attrValCB');
	$('#'+cbCntrl).attr("checked", !$('#'+cbCntrl).attr("checked"));
}
