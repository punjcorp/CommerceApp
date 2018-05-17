/**
 * This file has all the java script code needed for Price Event Screens
 * 
 */

var ClearancePrice = function() {
	this.itemId;
	this.locationId;
	this.priceType;
}

$.extend(ClearancePrice.prototype, {

});

var clearanceReset = new ClearancePrice();

$(function() {

	$("#priceType").change(function() {

		renderControls();
		if (validateClrSearchInputs()) {
			retreiveExistingClearance();
		}
	});

	$("#itemId").change(function() {
		if (validateClrSearchInputs()) {
			retreiveExistingClearance();
		}
	});

	$("#locationId").change(function() {
		if (validateClrSearchInputs()) {
			retreiveExistingClearance();
		}
	});

});

function validateClrSearchInputs() {
	var priceTypeVal = $("#priceType").val();
	var locationIdVal = $("#locationId").val();
	var itemIdVal = $("#itemId").val();

	if (priceTypeVal && locationIdVal && itemIdVal && priceTypeVal == 4){
		clearanceReset.itemId = itemIdVal;
		clearanceReset.locationId = locationIdVal;
		clearanceReset.priceType = priceTypeVal;
		return true;
	}
	else{
		return false;
	}
		
}

function retreiveExistingClearance() {

	var token = $("meta[name='_csrf']").attr("content");
	var formdata = JSON.stringify(clearanceReset);
	// AJAX call here and refresh the Expense Screen after the save
	$.ajax({
		url : '/admin/search_oldest_clearance',
		type : 'POST',
		cache : false,
		data : formdata,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(clearanceRecord) {
			if (clearanceRecord && clearanceRecord.itemPriceId && clearanceRecord.itemPriceId != '') {
				$('#priceTypeMsg').addClass('d-none');
				updateClearanceValues(clearanceRecord);
			} else {
				$('#priceTypeMsg').text('There is no clearance existing to reset!!');
				$('#priceTypeMsg').removeClass('d-none');
			}
		},
		error: function(){
			clearClearanceContainer();
			$('#priceTypeMsg').text('There is no clearance existing to reset!!');
			$('#priceTypeMsg').removeClass('d-none');
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader('X-CSRF-TOKEN', token)
		}
	});

}

function updateClearanceValues(clearanceRecord) {

	$('#clearanceResetId').val(clearanceRecord.itemPriceId);
	$('#oldestClrContainer').removeClass('d-none');
	$('#oldClrDate').text(clearanceRecord.startDate);
	$('#oldClrPrice').text(clearanceRecord.itemPriceAmt.toFixed(2));
	$('#oldClrCreatedBy').text(clearanceRecord.createdBy);

}

function renderControls() {

	var priceTypeVal = $("#priceType").val();

	if (priceTypeVal == 1) {
		clearClearanceContainer();
		$('#priceTypeMsg').addClass('d-none');
		$('#endDateContainer').addClass('d-none');
		$('#amtContainer').removeClass('d-none');
	} else if (priceTypeVal == 3) {
		$('#priceTypeMsg').addClass('d-none');
		clearClearanceContainer();
		$('#endDateContainer').addClass('d-none');
		$('#amtContainer').removeClass('d-none');
	} else if (priceTypeVal == 2) {
		$('#priceTypeMsg').addClass('d-none');
		clearClearanceContainer();
		$('#endDateContainer').removeClass('d-none');
		$('#amtContainer').removeClass('d-none');
	} else if (priceTypeVal == 4) {
		$('#endDateContainer').addClass('d-none');
		$('#amtContainer').addClass('d-none');
	}

}

function clearClearanceContainer() {
	$('#clearanceResetId').val('');
	$('#oldestClrContainer').addClass('d-none');
	$('#oldClrDate').text('');
	$('#oldClrPrice').text('');
	$('#oldClrCreatedBy').text('');
}
