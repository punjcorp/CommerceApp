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
var token = $("meta[name='_csrf']").attr("content");
var orderItemsTable;
var selectedItems = new Array();
var returnItems = new Array();
var alertUtil = new Utils();

$(function() {
	
	$('#orderReturn\\.reasonCodeId').on('change', function(e) {
		var optionSelected = $(this).val();
		var selIndex = 0;
		$("[id^=orderReturn\\.orderReturnItems][id$=reasonCodeId]").each(function() {
				$(this).val(optionSelected);
		});

	});
	

	orderItemsTable = $('#orderItemsTable').DataTable({
		columnDefs : [ {
			orderable : false,
			className : 'select-checkbox',
			targets : 0
		} ],
		select : {
			style : 'os',
			selector : 'tr',
			style : 'multi'
		},
		order : [ [ 1, 'asc' ] ],
		"columns" : [ {
			"data" : "id"
		}, {
			"data" : "itemId"
		}, {
			"data" : "itemDesc"
		}, {
			"data" : "qty"
		}, {
			"data" : "unitCost"
		}, {
			"data" : "itemCost"
		},{
			"data" : "itemDiscount"
		}, {
			"data" : "sgstTax"
		}, {
			"data" : "cgstTax"
		}, {
			"data" : "itemTotal"
		}, ],
	});

	$('#btnChooseItems').click(function() {
		displaySelectedItems();

	});

	$('#btnSaveOrderReturn').click(function() {
		$('#screenBusyModal').modal({
			backdrop : 'static',
			keyboard : false
		});
	});

	$('#btnApproveOrderReturn').click(function() {
		$('#screenBusyModal').modal({
			backdrop : 'static',
			keyboard : false
		});
	});

	$('#btnViewOrderReturnReport').click(function() {
		viewOrderReturnReport();
	});

	$('#btnPrintReportAndNewOrderReturn').click(function() {
		printOrderReturnReport();
	});

	$('#btnEditOrderReturn').click(function() {
		editOrderReturn();
	});

	$('#btnApproveOrderReturn').click(function() {
		approveOrderReturn();
	});

	$('#btnNewOrderReturn').click(function() {
		startNewOrderReturn();
	});

	$('#btnSearchOrderReturn').click(function() {
		searchOrderReturn();
	});

	$('#btnSearchReturnItem').click(function() {
		showOrderItems();
	});

	calculateTotals();

});

function renderOrderItem(orderItem) {
	$('#order\\.orderItems' + targetItemIndex + '\\.itemId').val(orderItem.itemId);
	$('#order\\.orderItems' + targetItemIndex + '\\.itemDesc').val(orderItem.name);
	$('#itemSearchHelp').html(orderItem.name + '<br>' + orderItem.longDesc);
	$('#order\\.orderItems' + targetItemIndex + '\\.orderedQty').val(orderItem.qty.toFixed(2));
	$('#order\\.orderItems' + targetItemIndex + '\\.unitCost').val((+orderItem.unitCostAmt).toFixed(2));

	$('#order\\.orderItems' + targetItemIndex + '\\.costAmount').val((+orderItem.priceAmt).toFixed(2));
	$('#lbl_' + targetItemIndex + '_costAmount').text((+orderItem.priceAmt).toFixed(2));

	if (orderItem.igstTax && orderItem.igstTax.amount != null) {
		$('#order\\.orderItems' + targetItemIndex + '\\.taxGroupId').val(orderItem.igstTax.taxGroupId);
		$('#order\\.orderItems' + targetItemIndex + '\\.igstRateRuleId').val(orderItem.igstTax.taxRuleRateId);
		$('#order\\.orderItems' + targetItemIndex + '\\.igstCode').val(orderItem.igstTax.typeCode);

		$('#order\\.orderItems' + targetItemIndex + '\\.igstTaxAmount').val(orderItem.igstTax.amount.toFixed(2));
		$('#order\\.orderItems' + targetItemIndex + '\\.igstRate').val(orderItem.igstTax.percentage.toFixed(2));

		$('#lbl_' + targetItemIndex + '_igstTaxAmount').text(orderItem.igstTax.amount.toFixed(2));

	} else if (orderItem.cgstTax && orderItem.cgstTax.amount != null) {
		$('#order\\.orderItems' + targetItemIndex + '\\.taxGroupId').val(orderItem.sgstTax.taxGroupId);
		$('#order\\.orderItems' + targetItemIndex + '\\.sgstRateRuleId').val(orderItem.sgstTax.taxRuleRateId);
		$('#order\\.orderItems' + targetItemIndex + '\\.cgstRateRuleId').val(orderItem.cgstTax.taxRuleRateId);
		$('#order\\.orderItems' + targetItemIndex + '\\.sgstCode').val(orderItem.sgstTax.typeCode);
		$('#order\\.orderItems' + targetItemIndex + '\\.cgstCode').val(orderItem.cgstTax.typeCode);

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

function calculateTotals() {

	if ($("[id^=orderReturn\\.orderReturnItems][id$=\\.sgstTaxAmount]").length > 0) {

		var totalDiscountAmt = 0.00;

		$("[id^=orderReturn\\.orderReturnItems][id$=\\.actualDiscountAmount]").map(function() {
			totalDiscountAmt += (+$(this).val());
		});		
		
		var totalSGSTAmt = 0.00;

		$("[id^=orderReturn\\.orderReturnItems][id$=\\.sgstTaxAmount]").map(function() {
			totalSGSTAmt += (+$(this).val());
		});

		var totalCGSTAmt = 0.00;

		$("[id^=orderReturn\\.orderReturnItems][id$=\\.cgstTaxAmount]").map(function() {
			totalCGSTAmt += (+$(this).val());
		});

		var totalIGSTAmt = 0.00;

		$("[id^=orderReturn\\.orderReturnItems][id$=\\.igstTaxAmount]").map(function() {
			totalIGSTAmt += (+$(this).val());
		});

		var totalTaxAmt = 0.00;

		$("[id^=orderReturn\\.orderReturnItems][id$=\\.taxAmount]").map(function() {
			totalTaxAmt += (+$(this).val());
		});

		$('#orderReturn\\.taxAmount').val(totalTaxAmt.toFixed(2));
		$('#orderReturn\\.sgstTaxAmount').val(totalSGSTAmt.toFixed(2));
		$('#orderReturn\\.cgstTaxAmount').val(totalCGSTAmt.toFixed(2));

		$('#lbl_taxAmount').text(totalTaxAmt.toFixed(2));
		$('#lbl_sgstTaxAmount').text(totalSGSTAmt.toFixed(2));
		$('#lbl_cgstTaxAmount').text(totalCGSTAmt.toFixed(2));

		var totalCost = 0.00;
		$("[id^=orderReturn\\.orderReturnItems][id$=\\.totalCost]").map(function() {
			totalCost += (+$(this).val());
		});

		var totalCostAmount = 0.00;
		$("[id^=orderReturn\\.orderReturnItems][id$=\\.costAmount]").map(function() {
			totalCostAmount += (+$(this).val());
		});

		$('#orderReturn\\.estimatedCost').val(totalCostAmount.toFixed(2));
		$('#orderReturn\\.discountAmount').val(totalDiscountAmt.toFixed(2));
		$('#orderReturn\\.totalAmount').val(totalCost.toFixed(2));

		$('#lbl_estimatedCost').text(totalCostAmount.toFixed(2));
		$('#lbl_discountAmount').text(totalDiscountAmt.toFixed(2));
		$('#lbl_totalAmount').text(totalCost.toFixed(2));

	}
}

function postOrderReturnSave() {
	$('#orderReportModal').modal({
		backdrop : 'static',
		keyboard : false
	});

}

function viewOrderReturnReport() {
	$('#progressDiv').removeClass("d-none");
	view_rcpt_url = encodeURIComponent(view_rcpt_url + '=' + txn_orderId);
	var pdfRcptUrl = view_rcpt_viewer_url + '?file=' + view_rcpt_url;
	$('#reportPDFContainer').attr("src", pdfRcptUrl);
	$('#progressDiv').addClass("d-none");

}

function printOrderReturnReport() {

	//The AJAX call for receipt printing
	orderDTO.orderId = txn_orderId;

	executePrintReturnReport(orderDTO);
}

function executePrintReturnReport(orderDTO) {
	var token = $("meta[name='_csrf']").attr("content");
	var formdata = JSON.stringify(orderDTO);
	// AJAX call here and refresh the sell item page with receipt printing
	$.ajax({
		url : print_rcpt_url,
		type : 'POST',
		cache : false,
		data : formdata,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			startNewOrder();
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader('X-CSRF-TOKEN', token)
		}
	});

}

function editOrderReturn() {
	window.location.href = editOrderReturnURL + '=' + txn_orderId;
}

function approveOrderReturn() {
	window.location.href = approveOrderReturnURL + '=' + txn_orderId;
}

function startNewOrderReturn() {
	window.location.href = newOrderReturnURL;
}

function searchOrderReturn() {
	window.location.href = searchOrderReturnURL;
}

function showOrderItems() {
	$('#orderItemReportModal').modal('show');
}

function displaySelectedItems() {
	var selectedItemsArray = orderItemsTable.rows({
		selected : true
	}).data();
	var itemIdVal;
	selectedItems = [];
	$.each($(selectedItemsArray), function(key, value) {
		itemIdVal = value["itemId"];
		itemIdVal = itemIdVal.replace(/[^0-9]/gi, '');
		selectedItems.push(itemIdVal);
	});
	if (selectedItems.length > 0) {
		renderSelectedItems(selectedItems);
		calculateTotals();
	}
	$('#orderItemReportModal').modal('hide');
}

function renderSelectedItems(selectedItems) {
	var finalItemsHtml = '';

	var returnItemHeader = new OrderReturnItem();
	var itemHeaderHtml = returnItemHeader.renderHeaders();
	finalItemsHtml = finalItemsHtml + itemHeaderHtml;

	$.each(selectedItems, function(index, selectedItemId) {

		var existingOrderItem = $.grep(order_items, function(order_item, itemIndex) {
			return order_item.itemId == selectedItemId;
		});

		var returnItem = new OrderReturnItem();
		returnItem.parse(existingOrderItem[0]);
		var itemHtml = returnItem.render(index);
		finalItemsHtml = finalItemsHtml + itemHtml;
		returnItems.push(returnItem);
	});

	$('#selectedReturnItem').html(finalItemsHtml);
	$('#selectedReturnItem').removeClass('d-none');
	$('#orderTotals').removeClass('d-none');
}

function reRenderAllItems() {
	var finalItemsHtml = '';

	var returnItemHeader = new OrderReturnItem();
	var itemHeaderHtml = returnItemHeader.renderHeaders();
	finalItemsHtml = finalItemsHtml + itemHeaderHtml;
	
	$.each(returnItems, function(index, returnItem) {
		var itemHtml = returnItem.render(index);
		finalItemsHtml = finalItemsHtml + itemHtml;
	});
	
	$('#selectedReturnItem').html(finalItemsHtml);
	$('#selectedReturnItem').removeClass('d-none');
	
	calculateTotals();
}

function qtyChanged(index, itemId) {

	var changedReturnItem = $.grep(returnItems, function(returnItem, itemIndex) {
		return returnItem.itemId == itemId;
	});
	
	var returnItemIndex= $.grep(returnItems, function(returnItem, itemIndex) {
		if(returnItem.itemId == itemId)
			return itemIndex;
	});

	var changedQty = +$('#orderReturn\\.orderReturnItems' + index + '\\.orderedQty').val();
	if (validateQty(itemId, changedQty, index)) {
		//returnItems.splice(returnItemIndex,1);
		updateReturnItemDetails(changedQty, changedReturnItem[0], index, returnItemIndex);
		reRenderAllItems();
	}

}

function validateQty(itemId, changedQty, index) {
	if (changedQty > 0) {
		var existingOrderItem = $.grep(order_items, function(order_item, itemIndex) {
			return order_item.itemId == itemId;
		});
		if (existingOrderItem[0].delieveredQty < changedQty) {
			$('#orderReturn\\.orderReturnItems' + index + '\\.orderedQty').val(1);
			qtyChanged(index, itemId);
			btnLabels = [ i18next.t('alert_btn_ok'), '' ];
			alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('screeen_order_return_validate_qty'), btnLabels);
			return false;
		} else {
			return true;
		}
	} else {
		$('#orderReturn\\.orderReturnItems' + index + '\\.orderedQty').val(1);
		qtyChanged(index, itemId);
		btnLabels = [ i18next.t('alert_btn_ok'), '' ];
		alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('screeen_order_return_validate_qty'), btnLabels);
		return false;
	}

}

function updateReturnItemDetails(changedQty, changedReturnItem, index, returnItemIndex) {
	changedReturnItem.qty = changedQty;

	var unitCost = +$('#orderReturn\\.orderReturnItems' + index + '\\.unitCost').val();

	var itemCost = unitCost * changedQty;
	changedReturnItem.price = itemCost;

	var discountAmount = +$('#orderReturn\\.orderReturnItems' + index + '\\.actualDiscountAmount').val();
	var taxableAmt = itemCost - discountAmount;
	var sgstRate = +$('#orderReturn\\.orderReturnItems' + index + '\\.sgstRate').val();
	var cgstRate = +$('#orderReturn\\.orderReturnItems' + index + '\\.cgstRate').val();
	var totalRate = sgstRate + cgstRate;

	var taxAmount = taxableAmt * (totalRate / 100);
	changedReturnItem.taxTotal = taxAmount;

	var itemTotal = taxableAmt + taxAmount;
	changedReturnItem.itemTotal = itemTotal;
	returnItems[returnItemIndex]=changedReturnItem;
}