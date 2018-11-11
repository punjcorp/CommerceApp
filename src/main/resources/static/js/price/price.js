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

var SearchBean= function() {
	this.searchText;
	this.pager;
}

$.extend(SearchBean.prototype, {

	
});


var clearanceReset = new ClearancePrice();
var searchBean = new SearchBean();
var token = $("meta[name='_csrf']").attr("content");

$(function() {

	$('#itemDesc').autocomplete({
		minLength : 3,
		source : function(request, response) {
			$('#itemSearchErrorMsg').hide();
			$('#itemSearchBusy').removeClass('d-none');
			var searchText = this.element[0].value;
			searchBean.searchText = searchText;
			var formdata = JSON.stringify(searchBean);
			
			// AJAX call here and refresh the Expense Screen after the save
			$.ajax({
				url : '/order_item_lookup',
				type : 'POST',
				cache : false,
				data : formdata,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					$('#itemSearchBusy').addClass('d-none');
					if (!data.length) {
						$('#itemDesc').val('');
						$('#itemId').val('');
						$('#itemSearchErrorMsg').addClass('invalid-feedback');
						$('#itemSearchErrorMsg').html('Item Not found!!');
						$('#itemSearchErrorMsg').show();
					} else {
						response($.map(data, function(item) {
							var dataVal = "<small>" + item.itemId + "-" + item.name + "</small>";
							var descVal = item.name;

							var finalItemPic = '';
							if (item.imageType && item.imageType.length > 0)
								finalItemPic = 'data:' + item.imageType + ';base64,' + item.baseEncodedImage;
							else
								finalItemPic = '/images/item_image_default_200.png';

							return {
								label : dataVal,
								value : item.itemId,
								desc : descVal,
								longDesc : item.longDesc,
								taxInclusive : item.itemOptions.taxInclusive,
								pic : '/images/default_image.png',
								skuImage : finalItemPic
							}
						}));
					}
				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader('X-CSRF-TOKEN', token)
				}
			});
		},
		change : function(event, ui) {
			if (ui.item == null || ui.item == undefined) {
				$('#itemDesc').val('');
				$('#itemId').val('');
				$('#itemSearchErrorMsg').addClass('invalid-feedback');
				$('#itemSearchErrorMsg').html('Please select an valid item!!');
				$('#itemSearchErrorMsg').show();
			} else {
				$('#itemSearchErrorMsg').hide();
			}
			controlValueChanged();
		},
		select : function(event, ui) {
			event.preventDefault();
			if (ui.item) {
				updateItemDetails(event, ui);
			}
		}
	});

	$("#priceType").change(function() {

		renderControls();
		controlValueChanged();
		
	});

	$("#itemId").change(function() {
		controlValueChanged();
	});

	$("#locationId").change(function() {
		controlValueChanged();
	});

});

/* This section will allow the item listing to be in a specific format */
$["ui"]["autocomplete"].prototype["_renderItem"] = function(ul, item) {
	

	return $("<li></li>").data("item.autocomplete", item).html($('<div/>', {
		'class' : 'row'
	}).append($('<div/>', {
		'class' : 'col-4'
	}).append($('<div/>', {
		'class' : 'preview-thumbnail-cart'
	}).append($('<img/>', {
		src : item.skuImage,
		class: 'img-thumbnail'
	})))).append($('<div/>', {
		'class' : 'col-6'
	}).append($('<span/>', {
		html : "<b>" + item.value + "</b><br/>" + item.desc
	})))).appendTo(ul);
};

function controlValueChanged(){
	if (validateClrSearchInputs()) {
		retreiveExistingClearance();
	}
	getItemDetails();
}

function updateItemDetails(event, ui) {

	$('#itemDesc').val(ui.item.desc);
	$('#itemId').val(ui.item.value);
	if(typeof(ui.item.taxInclusive)!=="undefined" && ui.item.taxInclusive!=undefined && ui.item.taxInclusive=="true"){
		$('#taxType').val('inclusive');
	}

}

function validateClrSearchInputs() {
	var priceTypeVal = $("#priceType").val();
	var locationIdVal = $("#locationId").val();
	var itemIdVal = $("#itemId").val();

	if (priceTypeVal && locationIdVal && itemIdVal && priceTypeVal == 4) {
		clearanceReset.itemId = itemIdVal;
		clearanceReset.locationId = locationIdVal;
		clearanceReset.priceType = priceTypeVal;
		return true;
	} else {
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
		error : function() {
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
		$('#taxTypeContainer').addClass('d-none');
	}

}

function clearClearanceContainer() {
	$('#clearanceResetId').val('');
	$('#oldestClrContainer').addClass('d-none');
	$('#oldClrDate').text('');
	$('#oldClrPrice').text('');
	$('#oldClrCreatedBy').text('');
}



/**
 * This function will take the selected item and show the current price details
 * 
 * @param event
 * @param ui
 * @returns
 */
function getItemDetails(event, ui) {

	var priceTypeVal = $("#priceType").val();
	var locationIdVal = $("#locationId").val();
	var itemIdVal = $("#itemId").val();

	if (priceTypeVal && locationIdVal && itemIdVal && locationIdVal !='' && itemIdVal !='' && priceTypeVal != 4 && priceTypeVal != '') {
		
		$.get("/sale_item_lookup", {
			itemId : itemIdVal,
			locationId : locationIdVal,
			gstFlag: 'S'
		}, function(data, status) {
			
			if(!data.length>0){
				updatePriceDetails(data);
			}else{
				clearPriceDetailsContainer();
			}
		});
	} else {
		clearPriceDetailsContainer();
	}
	
}


function updatePriceDetails(priceDetails) {

	$('#priceDtlsContainer').removeClass('d-none');
	$('#currentPrice').text(priceDetails.priceAmt.toFixed(2));
	$('#unitCost').text(priceDetails.unitCostAmt.toFixed(2));
	$('#suggestedPrice').text(priceDetails.suggestedPrice.toFixed(2));
	$('#maxRetailPrice').text(priceDetails.maxRetailPrice.toFixed(2));
	
}


function clearPriceDetailsContainer() {
	$('#priceDtlsContainer').addClass('d-none');
	$('#currentPrice').text('0.00');
	$('#unitCost').text('0.00');
	$('#suggestedPrice').text('0.00');
	$('#maxRetailPrice').text('0.00');
}
