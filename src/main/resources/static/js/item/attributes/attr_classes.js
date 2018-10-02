/**
 * This file contains all the classes needed for a item creation screen
 */

var SearchBean = function() {
	this.searchText;
	this.pager;
}

$.extend(SearchBean.prototype, {

});

var AttributeBean = function() {
	this.attributeId;
	this.name;
	this.code;
	this.description;
	this.valCode;
	this.valName;
	this.valDescription;
	this.valSeqNo;
	this.attrValList;
}

$.extend(AttributeBean.prototype, {

	initialize : function(attributeId, name, code, description, valCode, valName, valDescription, valSeqNo) {
		this.attributeId = attributeId;
		this.name = name;
		this.code = code;
		this.description = description;
		this.valCode = valCode;
		this.valName = valName;
		this.valDescription = valDescription;
		this.valSeqNo = valSeqNo;
		this.attrValList = new Array();
	},
	saveAttribute : function() {

		
		var formData=JSON.stringify(this);
		
		$.ajax({
			url : '/admin/save_attr_details',
			type : 'POST',
			cache : false,
			data : formData,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				postAttrSave(data);
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader('X-CSRF-TOKEN', token)
			}
		});
		
		
	},
	saveAttributeVal : function() {

		
		var formData=JSON.stringify(this);
		
		$.ajax({
			url : '/admin/save_attr_val_details',
			type : 'POST',
			cache : false,
			data : formData,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				postAttrValSave(data);
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader('X-CSRF-TOKEN', token)
			}
		});
		
		
	},
	deleteAttributeVal : function(attributeId) {
		this.attributeId=attributeId;
		
		var formData=JSON.stringify(this);
		
		$.ajax({
			url : '/admin/delete_attr_val_details',
			type : 'POST',
			cache : false,
			data : formData,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				postAttrValDeletion(data);
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader('X-CSRF-TOKEN', token)
			}
		});
		
		
	},
	validateNewAttribute : function(actionType) {
		var result = true;
		var attrName = $('#name').val();
		var valName = $('#valName').val();
		if (actionType == 'attr') {
			if (attrName != undefined && attrName !== 'undefined' && attrName.trim() != '') {
				$('#name').removeClass('is-invalid');
				$('#nameMsg').hide();
				result = true;
			} else {
				$('#name').addClass('is-invalid');
				$('#nameMsg').addClass('invalid-feedback');
				$('#nameMsg').html('<h6>Please enter the Attribute Name!</h6>');
				$('#nameMsg').show();
				result = false;
			}
		}

		if (actionType == 'attr' || actionType == 'attrval') {

			if (valName != undefined && valName !== 'undefined' && valName.trim() != '') {
				$('#valName').removeClass('is-invalid');
				$('#valNameMsg').hide();
				if (!result)
					result = true;
			} else {
				$('#valName').addClass('is-invalid');
				$('#valNameMsg').addClass('invalid-feedback');
				$('#valNameMsg').html('<h6>Please enter the Attribute Value!</h6>');
				$('#valNameMsg').show();
				result = false;
			}
		}
		return result;
	},
	renderSelectedAttribute : function(selectedAttributes) {
		if (selectedAttributes && selectedAttributes.length > 0) {

			var outputHtml = '<div class="row">';
			outputHtml += '<div class="col-xs-12 col-sm-12 col-md-12">';
			outputHtml += '<div class="form-group">';
			outputHtml += '<div class="input-group">';
			outputHtml += '<div class="list-group col-12" id="sortableList">';

			var outputColRightHtml = '';

			$.each(selectedAttributes,
					function(index, attribute) {
						outputColRightHtml += '<div class="row" id="sortableItem_' + attribute.attributeId + '">';
						outputColRightHtml += '<div class="col">';
						outputColRightHtml += '<a class="list-group-item list-group-item-action" id="attrItem' + index + '" onClick="selectAttr(this, ' + index
								+ ')">';
						outputColRightHtml += '<div class="form-check float-left">';
						outputColRightHtml += '<input type="hidden" id="style.selectedAttributes' + index + '.attributeId"';
						outputColRightHtml += 'name="style.selectedAttributes[' + index + '].attributeId" value="' + attribute.attributeId + '"></input> ';
						outputColRightHtml += '<input type="hidden" id="style.selectedAttributes' + index + '.name"';
						outputColRightHtml += 'name="style.selectedAttributes[' + index + '].name" value="' + attribute.name + '"></input> ';
						outputColRightHtml += '<input type="hidden" id="style.selectedAttributes' + index + '.code"';
						outputColRightHtml += 'name="style.selectedAttributes[' + index + '].code" value="' + attribute.code + '"></input> ';
						outputColRightHtml += '</div>';
						outputColRightHtml += '<i class="fas fa-arrows-alt mx-2"></i>';
						outputColRightHtml += '<span class="mx-4">' + attribute.valName + '</span></a>';
						outputColRightHtml += '</div>';
						outputColRightHtml += '<div class="col-1">';
						outputColRightHtml += '<button type="button" id="btnDeleteAttr"';
						outputColRightHtml += 'onClick="deleteAttr(' + attribute.attributeId
								+ ')" class="btn btn-danger btn-sm mx-1"><i class="far fa-trash-alt fa-2x"></i></button> ';

						outputColRightHtml += '</div>';
						outputColRightHtml += '</div>';

					});

			outputHtml += outputColRightHtml;
			outputHtml += '</div>';
			outputHtml += '</div>';
			outputHtml += '</div>';
			outputHtml += '</div>';
			outputHtml += '</div>';

			$('#attrValListContainer').html(outputHtml);

			$('#sortableList').sortable({
				update : function(event, ui) {
					saveChangedOrder();
				},
				placeholder : "ui-state-highlight"
			});

		} else {
			$('#attrValListContainer').html('');
		}

	}

});
