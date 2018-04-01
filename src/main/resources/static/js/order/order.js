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

var accountDTO = new AccountDTO();
var searchBean = new SearchBean();
var token = $("meta[name='_csrf']").attr("content");
var targetItemIndex;

$(function() {

	$("#supplierSearch\\.searchText").autocomplete({
		minLength : 3,
		source : function(request, response) {
			accountDTO.entityType = $("#supplierSearch\\.searchText").val();
			var formdata = JSON.stringify(accountDTO);

			// AJAX call here and refresh the Expense Screen after the save
			$.ajax({
				url : '/admin/search_supplier',
				type : 'POST',
				cache : false,
				data : formdata,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(suppliers) {
					response($.map(suppliers, function(supplier) {
						var dataVal = "" + supplier.name + "-( Ph- " + supplier.phone1 + " )";
						var descVal = supplier.email;
						return {
							label : dataVal,
							name : supplier.name,
							phone : supplier.phone1,
							phone2 : supplier.phone2,
							supplierId : supplier.supplierId,
							emailOrg : supplier.email,
							email : descVal,
							type : 'SUPPLIER',
							addressId : supplier.primaryAddress.addressId,
							addressType : supplier.primaryAddress.addressType,
							address1 : supplier.primaryAddress.address1,
							address2 : supplier.primaryAddress.address2,
							city : supplier.primaryAddress.city,
							state : supplier.primaryAddress.state,
							country : supplier.primaryAddress.country,
							pincode : supplier.primaryAddress.pincode
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
				getSupplierDetails(event, ui);
			}
		}
	});

	$('[id^=order\\.orderItems][id$=\\.itemId]').autocomplete({
		minLength : 3,
		source : function(request, response) {
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
					response($.map(data, function(item) {
						var dataVal = "<small>" + item.itemId + "-" + item.name + "</small>";
						var descVal = item.name;
						return {
							label : dataVal,
							value : item.itemId,
							desc : descVal,
							longDesc : item.longDesc,
							pic : '/images/default_image.png',
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
				getItemDetails(event, ui);
			}
		}
	});

	$('[id^=order\\.orderItems][id$=\\.itemId]').keypress(function() {
		validateSupplierAndLocation();
	});

	
	$('#btnNewOrder').click(function() {
		startNewOrder();
	});	

	$('#btnPrintReportAndNewOrder').click(function() {
		printOrderReport();
	});
	
	$('#btnViewOrderReport').click(function() {
		viewOrderReport();
	});			
	
	
	calculateTotals();
	
});

/* This section will allow the item listing to be in a specific format */
$["ui"]["autocomplete"].prototype["_renderItem"] = function(ul, item) {
	if (this.element[0].id.indexOf('supplierSearch') > -1) {
		return $("<li></li>").data("item.autocomplete", item).html(

		$('<div/>', {
			'class' : 'row px-2'
		}).append($('<div/>', {
			'class' : 'col-12'
		}).append($('<span/>', {
			'html' : '<b>' + item.label + '</b>'
		}))).append($('<div/>', {
			'class' : 'col-12'
		}).append($('<span/>', {
			'html' : item.email
		})))

		).appendTo(ul);
	} else {
		return $("<li></li>").data("item.autocomplete", item).html($('<div/>', {
			'class' : 'row'
		}).append($('<div/>', {
			'class' : 'col-4'
		}).append($('<img/>', {
			src : item.pic,
			'class' : 'img-fluid'
		}))).append($('<div/>', {
			'class' : 'col-6'
		}).append($('<span/>', {
			html : "<b>" + item.value + "</b><br/>" + item.desc
		})))).appendTo(ul);
	}

};

/**
 * This function will take the selected expense reason code type
 * 
 * @param event
 * @param ui
 * @returns
 */
function getSupplierDetails(event, ui) {

	$('#supplierSearch\\.searchText').val(ui.item.name);
	$('#order\\.supplierId').val(ui.item.supplierId);

	this.renderSupplierPrimaryAddress(ui.item);

}

function renderSupplierPrimaryAddress(supplier) {
	$('#primaryAddress1').text(supplier.address1);
	if (supplier.address2 && supplier.address2 != 'undefined' && supplier.address2 != '')
		$('#primaryAddress2').text(supplier.address2);
	$('#primaryCity').text(supplier.city + ', ' + supplier.state);
	$('#primaryCountry').text(supplier.country + ' - ' + supplier.pincode);
	$('#supplierPhone').text(supplier.phone);
	$('#supplierEmail').text(supplier.email);

	$('#order\\.supplier\\.supplierId').val(supplier.supplierId);
	$('#order\\.supplier\\.name').val(supplier.name);
	$('#order\\.supplier\\.phone1').val(supplier.phone);
	$('#order\\.supplier\\.phone2').val(supplier.phone2);
	$('#order\\.supplier\\.email').val(supplier.emailOrg);

	$('#order\\.supplier\\.primaryAddress\\.addressId').val(supplier.addressId);
	$('#order\\.supplier\\.primaryAddress\\.addressType').val(supplier.addressType);
	$('#order\\.supplier\\.primaryAddress\\.primary').val('Y');
	$('#order\\.supplier\\.primaryAddress\\.address1').val(supplier.address1);
	$('#order\\.supplier\\.primaryAddress\\.address2').val(supplier.address2);
	$('#order\\.supplier\\.primaryAddress\\.city').val(supplier.city);
	$('#order\\.supplier\\.primaryAddress\\.state').val(supplier.state);
	$('#order\\.supplier\\.primaryAddress\\.country').val(supplier.country);
	$('#order\\.supplier\\.primaryAddress\\.pincode').val(supplier.pincode);

	$('#supplierDetails').removeClass('d-none');
}

/**
 * This function will take the selected item and add the item to transaction
 * 
 * @param event
 * @param ui
 * @returns
 */
function getItemDetails(event, ui) {

	var locationId = $('#order\\.locationId').val();
	var supplierId = $('#order\\.supplier\\.supplierId').val();

	targetItemIndex = event.target.id.replace(/[^0-9]/gi, '');

	$.get("/search_order_item", {
		itemId : ui.item.value,
		locationId : locationId,
		supplierId : supplierId
	}, function(data, status) {

		renderOrderItem(data);
		calculateTotals();
	});
}

function renderOrderItem(orderItem) {
	$('#order\\.orderItems' + targetItemIndex + '\\.orderedQty').val(orderItem.qty.toFixed(2));
	$('#order\\.orderItems' + targetItemIndex + '\\.unitCost').val((+orderItem.unitCostAmt).toFixed(2));

	$('#order\\.orderItems' + targetItemIndex + '\\.costAmount').val((+orderItem.priceAmt).toFixed(2));
	$('#lbl_' + targetItemIndex + '_costAmount').text((+orderItem.priceAmt).toFixed(2));

	if (orderItem.igstTax && orderItem.igstTax.amount) {
		$('#order\\.orderItems' + targetItemIndex + '\\.igstTaxAmount').val(orderItem.igstTax.amount.toFixed(2));
		$('#order\\.orderItems' + targetItemIndex + '\\.igstRate').val(orderItem.igstTax.percentage.toFixed(2));

		$('#lbl_' + targetItemIndex + '_igstTaxAmount').text(orderItem.igstTax.amount.toFixed(2));

	} else if (orderItem.cgstTax && orderItem.cgstTax.amount) {
		$('#order\\.orderItems' + targetItemIndex + '\\.sgstTaxAmount').val(orderItem.sgstTax.amount.toFixed(2));
		$('#order\\.orderItems' + targetItemIndex + '\\.cgstTaxAmount').val(orderItem.cgstTax.amount.toFixed(2));
		$('#order\\.orderItems' + targetItemIndex + '\\.sgstRate').val(orderItem.sgstTax.percentage.toFixed(2));
		$('#order\\.orderItems' + targetItemIndex + '\\.cgstRate').val(orderItem.cgstTax.percentage.toFixed(2));

		$('#lbl_' + targetItemIndex + '_sgstTaxAmount').text(orderItem.sgstTax.amount.toFixed(2));
		$('#lbl_' + targetItemIndex + '_cgstTaxAmount').text(orderItem.cgstTax.amount.toFixed(2));

		$('#lbl_' + targetItemIndex + '_sgstRate').text(orderItem.sgstTax.percentage.toFixed(2));
		$('#lbl_' + targetItemIndex + '_cgstRate').text(orderItem.cgstTax.percentage.toFixed(2));

	}

	$('#order\\.orderItems' + targetItemIndex + '\\.taxAmount').val(orderItem.taxAmt.toFixed(2));

	$('#order\\.orderItems' + targetItemIndex + '\\.totalCost').val(orderItem.totalAmt.toFixed(2));
	$('#lbl_' + targetItemIndex + '_totalCost').text(orderItem.totalAmt.toFixed(2));

}

function saleItemChanged(cntl) {

	var orderItemIdx = cntl.id.replace(/[^0-9]/gi, '');
	calculateAmounts(orderItemIdx, cntl.id);

}

function calculateAmounts(orderItemIdx, cntlId) {
	var orderQty = +$('#order\\.orderItems' + orderItemIdx + '\\.orderedQty').val();
	var unitCost = +$('#order\\.orderItems' + orderItemIdx + '\\.unitCost').val();

	var costAmount = orderQty * unitCost;

	$('#order\\.orderItems' + orderItemIdx + '\\.costAmount').val(costAmount.toFixed(2));
	$('#lbl_' + orderItemIdx + '_costAmount').text(costAmount.toFixed(2));

	var igstTaxRate = +$('#order\\.orderItems' + orderItemIdx + '\\.igstRate').val();
	var sgstTaxRate = +$('#order\\.orderItems' + orderItemIdx + '\\.sgstRate').val();
	var cgstTaxRate = +$('#order\\.orderItems' + orderItemIdx + '\\.cgstRate').val();

	var igstTaxAmount = (igstTaxRate * costAmount) / 100;
	var sgstTaxAmount = (sgstTaxRate * costAmount) / 100;
	var cgstTaxAmount = (cgstTaxRate * costAmount) / 100;

	$('#lbl_' + orderItemIdx + '_sgstTaxAmount').text(sgstTaxAmount.toFixed(2));
	$('#lbl_' + orderItemIdx + '_cgstTaxAmount').text(cgstTaxAmount.toFixed(2));
	$('#lbl_' + orderItemIdx + '_igstTaxAmount').text(igstTaxAmount.toFixed(2));

	$('#order\\.orderItems' + orderItemIdx + '\\.sgstTaxAmount').val(sgstTaxAmount.toFixed(2));
	$('#order\\.orderItems' + orderItemIdx + '\\.cgstTaxAmount').val(cgstTaxAmount.toFixed(2));
	$('#order\\.orderItems' + orderItemIdx + '\\.igstTaxAmount').val(igstTaxAmount.toFixed(2));

	var taxAmount = sgstTaxAmount + cgstTaxAmount;

	var totalCost = costAmount + taxAmount;

	$('#order\\.orderItems' + orderItemIdx + '\\.taxAmount').val(taxAmount.toFixed(2));
	$('#order\\.orderItems' + orderItemIdx + '\\.totalCost').val(totalCost.toFixed(2));
	$('#lbl_' + orderItemIdx + '_totalCost').text(totalCost.toFixed(2));

	// The totals are calculated based on the item action or order action
	calculateTotals();
	
	if(txn_orderId && txn_orderId!='undefined' && txn_orderId!=''){
		postOrderSave();	
	}


}

function calculateTotals() {

	if($("[id^=order\\.orderItems][id$=\\.sgstTaxAmount]").length>0){
		
	
	
	var totalSGSTAmt = 0.00;

	$("[id^=order\\.orderItems][id$=\\.sgstTaxAmount]").map(function() {
		totalSGSTAmt += (+$(this).val());
	});

	var totalCGSTAmt = 0.00;

	$("[id^=order\\.orderItems][id$=\\.cgstTaxAmount]").map(function() {
		totalCGSTAmt += (+$(this).val());
	});

	var totalIGSTAmt = 0.00;

	$("[id^=order\\.orderItems][id$=\\.igstTaxAmount]").map(function() {
		totalIGSTAmt += (+$(this).val());
	});

	var totalTaxAmt = 0.00;

	$("[id^=order\\.orderItems][id$=\\.taxAmount]").map(function() {
		totalTaxAmt += (+$(this).val());
	});

	$('#order\\.taxAmount').val(totalTaxAmt.toFixed(2));
	$('#order\\.sgstTaxAmount').val(totalSGSTAmt.toFixed(2));
	$('#order\\.cgstTaxAmount').val(totalCGSTAmt.toFixed(2));

	$('#lbl_taxAmount').text(totalTaxAmt.toFixed(2));
	$('#lbl_sgstTaxAmount').text(totalSGSTAmt.toFixed(2));
	$('#lbl_cgstTaxAmount').text(totalCGSTAmt.toFixed(2));

	var totalCost = 0.00;
	$("[id^=order\\.orderItems][id$=\\.totalCost]").map(function() {
		totalCost += (+$(this).val());
	});

	var totalCostAmount = 0.00;
	$("[id^=order\\.orderItems][id$=\\.costAmount]").map(function() {
		totalCostAmount += (+$(this).val());
	});

	$('#order\\.estimatedCost').val(totalCostAmount.toFixed(2));
	$('#order\\.totalAmount').val(totalCost.toFixed(2));

	$('#lbl_estimatedCost').text(totalCostAmount.toFixed(2));
	$('#lbl_totalAmount').text(totalCost.toFixed(2));
	
	
	}
}

function validateSupplierAndLocation() {
	var validationPassed = true;

	// Validating Supplier Details
	var supplierId = $('#order\\.supplierId').val();
	if (supplierId && supplierId != 'undefined' && supplierId != '') {
		$('#supplierSearch\\.searchText').removeClass('is-invalid');
		$('#supplierMsg').hide();

	} else {
		validationPassed = false;
		$('#supplierSearch\\.searchText').addClass('is-invalid');
		$('#supplierMsg').addClass('invalid-feedback');
		$('#supplierMsg').html('<h6>Please enter a valid Supplier!</h6>');
		$('#supplierMsg').show();
	}

	// Validating Supplier Details
	var locationId = $('#order\\.locationId').val();
	if (locationId && locationId != 'undefined' && locationId != '') {
		$('#order\\.locationId').removeClass('is-invalid');
		$('#locationMsg').hide();

	} else {
		validationPassed = false;
		$('#order\\.locationId').addClass('is-invalid');
		$('#locationMsg').addClass('invalid-feedback');
		$('#locationMsg').html('<h6>Please select a valid Location!</h6>');
		$('#locationMsg').show();
	}

	return validationPassed;
}



function deleteItem(deleteItemAction) {
		$("#addForm").attr("action", deleteItemAction);
		$('#addForm').submit();
}




function postOrderSave(){
	$('#orderReportModal').modal({backdrop: 'static', keyboard: false});
	
}


function startNewOrder(){
	window.location.href = newOrderURL;
}
	

function printOrderReport(){
	
}


function viewOrderReport(){
	$('#progressDiv').removeClass("d-none");
	var pdfRcptUrl = view_rcpt_viewer_url+'?file='+view_rcpt_url;
	$('#reportPDFContainer').attr("src",pdfRcptUrl);
	$('#progressDiv').addClass("d-none");
	
}

