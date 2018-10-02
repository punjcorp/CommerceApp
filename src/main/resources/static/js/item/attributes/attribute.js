var token = $("meta[name='_csrf']").attr("content");
var attrVals = new Array();
var attribute = new AttributeBean();

$(function() {
	$('#existingAttr').chosen({});

	$('.chosen-container').css('width', '90%');

	$('#existingAttr').change(function() {
		attrVals = [];
		$('#alertText').addClass('d-none');
		$('#successText').addClass('d-none');
		$('#btnSaveOrderAttrVal').addClass('d-none');
		attributeChanged();
	});

	$('#btnAddAttr').click(function() {
		resetPopUpScreen(true);
		$('#addActionType').val('attr');
		var selectedAttrCode = $('#existingAttr').val();
		$('#code').val(selectedAttrCode);
		$('#attrModal').modal({
			backdrop : 'static',
			keyboard : false
		});
		$('#newAttrContainer').removeClass('d-none');
		$('#newAttrValContainer').removeClass('d-none');
	});

	$('#btnAddAttrVal').click(function() {
		resetPopUpScreen(false);
		
		$('#addActionType').val('attrval');
		$('#attrModal').modal({
			backdrop : 'static',
			keyboard : false
		});
		$('#newAttrContainer').addClass('d-none');
		$('#newAttrValContainer').removeClass('d-none');
		

		
	});

	$('#btnCancelSaveAttribute').click(function() {
		var saveStatus = $('#saveActionStatus').val();

		if (typeof (saveStatus) !== 'undefined' && saveStatus != undefined && saveStatus == 'attrsave') {
			window.location.href = '/admin/attr_val_action';
		} else if (typeof (saveStatus) !== 'undefined' && saveStatus != undefined && saveStatus == 'attrvalsave') {
			attrVals = [];
			$('#alertText').addClass('d-none');
			$('#successText').addClass('d-none');
			$('#btnSaveOrderAttrVal').addClass('d-none');
			attributeChanged();
			$('#attrModal').modal('toggle');
		} else {
			$('#attrModal').modal('toggle');
		}

	});

	$('#btnSaveAttribute').click(function() {
		saveAttributeDetails();

	});

	$('#btnSaveOrderAttrVal').click(function() {
		var valOrder = $('#sortableList').sortable("toArray");
		// var valOrder=$('#sortableList').sortable( "serialize", { key: "attrValId" } );
		var finalOrderList = [];
		var attr = '';
		if (valOrder != undefined && valOrder.length > 0) {
			$.each(valOrder, function(index, attrValId) {
				attrValId = attrValId.replace(/[^0-9]/gi, '');
				attr = new AttributeBean();
				attr.attributeId = attrValId;
				attr.valSeqNo = index + 1;
				finalOrderList.push(attr);
			});

			var attrValData = JSON.stringify(finalOrderList);

			// AJAX call here and refresh the Expense Screen after the save
			$.ajax({
				url : '/admin/save_attr_val_order',
				type : 'POST',
				cache : false,
				data : attrValData,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					if (data !== undefined && data.length > 0) {
						$('#successText').html('<b>The attribute values order has been successfully updated.</b>');
						$('#alertText').html('');
						$('#alertText').addClass('d-none');
						$('#successText').removeClass('d-none');
					} else {
						$('#successText').html('');
						$('#alertText').html('<b>There was some error while updating the attribute values order.</b>');
						$('#alertText').removeClass('d-none');
						$('#successText').addClass('d-none');
					}

				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader('X-CSRF-TOKEN', token)
				}
			});

			// 

		}

	});

});

function attributeChanged() {
	var existingAttrCode = $('#existingAttr').val();

	if (existingAttrCode != '') {

		var searchBean = new SearchBean();
		searchBean.searchText = existingAttrCode;
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
				showAttrValList(data);
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader('X-CSRF-TOKEN', token)
			}
		});
	} else {
		$('#existingAttrValueContainer').addClass('d-none');
		$('#maxAttrValSeq').val('');
	}
}

function showAttrValList(data) {
	if (data && data.length > 0) {
		var attr = '';
		$.map(data, function(attrData, index) {
			attr = new AttributeBean();
			attr.initialize(attrData.attributeId, attrData.name, attrData.code, attrData.description, attrData.valCode, attrData.valName,
					attrData.valDescription, attrData.valSeqNo)
			this.attrVals.push(attr);
		});
		
		$('#maxAttrValSeq').val(data.length);

		renderAttrVals();

	}else{
		$('#maxAttrValSeq').val('');
	}

}

function renderAttrVals() {
	attribute.renderSelectedAttribute(this.attrVals);
	$('#existingAttrValueContainer').removeClass('d-none');
}

function saveChangedOrder() {
	$('#btnSaveOrderAttrVal').removeClass('d-none');
}

function saveAttributeDetails() {
	var actionType = $('#addActionType').val();
	if (actionType != undefined && addActionType != '') {

		if (attribute.validateNewAttribute(actionType)) {

			if (actionType == 'attr') {
				var attrName = $('#name').val();
				var attrDesc = $('#description').val();
				var valName = $('#valName').val();
				var valDesc = $('#valDescription').val();
				var newAttribute = new AttributeBean();
				newAttribute.initialize(null, attrName, null, attrDesc, null, valName, valDesc, null);
				newAttribute.saveAttribute();
			} else if (actionType == 'attrval') {
				
				var attrCode=$('#existingAttr').val();
				var attrName=$('#existingAttr option:selected').text();
				var valName = $('#valName').val();
				var valDesc = $('#valDescription').val();
				var valSeqNo = +$('#maxAttrValSeq').val();
				valSeqNo = valSeqNo +1;
				
				var newAttributeVal = new AttributeBean();
				newAttributeVal.initialize(null, attrName, attrCode, null, null, valName, valDesc, valSeqNo);
				newAttributeVal.saveAttributeVal();
				
			}
		}
	}

}

function postAttrSave(ajaxResponse) {

	if (ajaxResponse != undefined && ajaxResponse != '') {

		if (typeof (ajaxResponse.status) !== 'undefined' && ajaxResponse.status != undefined && ajaxResponse.status == 'S') {
			$('#btnSaveAttribute').addClass('d-none');
			$('#saveActionMsg').html(ajaxResponse.statusMsg);
			$('#saveActionMsg').addClass('valid-feedback alert alert-success');
			$('#saveActionMsg').show();
			$('#saveActionStatus').val('attrsave');
		} else if (typeof (ajaxResponse.status) !== 'undefined' && ajaxResponse.status != undefined && ajaxResponse.status == 'F') {
			$('#btnSaveAttribute').removeClass('d-none');
			$('#saveActionMsg').html(ajaxResponse.statusMsg);
			$('#saveActionMsg').addClass('invalid-feedback alert alert-danger');
			$('#saveActionMsg').show();
		}

	} else {
		$('#btnSaveAttribute').removeClass('d-none');
		$('#saveActionMsg').html('<h6>An error has occurred while saving details, please try again later!</h6>');
		$('#saveActionMsg').addClass('invalid-feedback alert alert-danger');
		$('#saveActionMsg').show();
	}

}


function postAttrValSave(ajaxResponse) {

	if (ajaxResponse != undefined && ajaxResponse != '') {

		if (typeof (ajaxResponse.status) !== 'undefined' && ajaxResponse.status != undefined && ajaxResponse.status == 'S') {
			$('#btnSaveAttribute').addClass('d-none');
			$('#saveActionMsg').html(ajaxResponse.statusMsg);
			$('#saveActionMsg').addClass('valid-feedback alert alert-success');
			$('#saveActionMsg').show();
			$('#saveActionStatus').val('attrvalsave');
		} else if (typeof (ajaxResponse.status) !== 'undefined' && ajaxResponse.status != undefined && ajaxResponse.status == 'F') {
			$('#btnSaveAttribute').removeClass('d-none');
			$('#saveActionMsg').html(ajaxResponse.statusMsg);
			$('#saveActionMsg').addClass('invalid-feedback alert alert-danger');
			$('#saveActionMsg').show();
		}

	} else {
		$('#btnSaveAttribute').removeClass('d-none');
		$('#saveActionMsg').html('<h6>An error has occurred while saving details, please try again later!</h6>');
		$('#saveActionMsg').addClass('invalid-feedback alert alert-danger');
		$('#saveActionMsg').show();
	}

}


function deleteAttr(attributeId){
	var deleteAttr=new AttributeBean();
	deleteAttr.deleteAttributeVal(attributeId);
	
}

function postAttrValDeletion(ajaxResponse) {
	
	if (ajaxResponse != undefined && ajaxResponse != '') {

		if (typeof (ajaxResponse.status) !== 'undefined' && ajaxResponse.status != undefined && ajaxResponse.status == 'S') {
			$('#successText').html(ajaxResponse.statusMsg);
			$('#alertText').html('');
			$('#alertText').addClass('d-none');
			$('#successText').removeClass('d-none');
			
			// Render All values again
			attrVals = [];
			$('#btnSaveOrderAttrVal').addClass('d-none');
			attributeChanged();
			
			
		} else if (typeof (ajaxResponse.status) !== 'undefined' && ajaxResponse.status != undefined && ajaxResponse.status == 'F') {
			
			$('#successText').html('');
			$('#alertText').html(ajaxResponse.statusMsg);
			$('#alertText').removeClass('d-none');
			$('#successText').addClass('d-none');			

		}

	} else {
		$('#successText').html('');
		$('#alertText').html('<h6>An error has occurred while deleting attribute value details, please try again later!</h6>');
		$('#alertText').removeClass('d-none');
		$('#successText').addClass('d-none');			

	}

}


function resetPopUpScreen(isAttrBtn){
	$('#successText').html('');
	$('#successText').addClass('d-none');
	$('#alertText').html('');
	$('#alertText').addClass('d-none');
	
	$('#btnSaveAttribute').removeClass('d-none');
	$('#saveActionMsg').html('');
	$('#saveActionMsg').hide();
			
	if(isAttrBtn){
		$('#name').val('');
		$('#description').val('');
	}
	
	$('#valName').val('');
	$('#valDescription').val('');
	
}