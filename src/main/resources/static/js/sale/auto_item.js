/**
 * This file has all the javascript code needed for Sales Screen
 * Auto Complete for item lookup functionality
 * 
 */

/**
 * All the global variables for sale screen will be defined here
 */

var scannedItems = [];
var txnAction = new TxnAction();
var txnStartTime;
var txnEndTime;

/**
 * This function will ensure the item auto complete functionality is executed when at least 3 letters has been typed in the item category
 * 
 * @returns
 */
$(function() {
	txnStartTime = moment().format("DD-MMM-YY hh:mm:ss");

	$("#searchText").autocomplete({
		minLength : 3,
		source : function(request, response) {
			$.post({
				url : "/sku_lookup",
				data : $('form[id=autoCompleteForm]').serialize(),
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

	$('#btnTenderOK').click(function() {
		if (txnAction.tenderLineItem.validateTenderLineItem()) {
			txnAction.processTender();
		}
	});

	$('#btnCompleteTxn').click(function() {
		txnEndTime = moment().format("DD-MMM-YY hh:mm:ss");
		txnAction.processCompletedTxn();
	});

});

/* This section will allow the item listing to be in a specific format */
$["ui"]["autocomplete"].prototype["_renderItem"] = function(ul, item) {
	return $("<li></li>").data("item.autocomplete", item).html($('<div/>', {
		'class' : 'row'
	}).append($('<div/>', {
		'class' : 'col-4'
	}).append($('<img/>', {
		src : item.pic
	}))).append($('<div/>', {
		'class' : 'col-6'
	}).append($('<span/>', {
		html : "<b>" + item.value + "</b><br/>" + item.desc
	})))).appendTo(ul);
};

/**
 * This function will take the selected item and add the item to transaction
 * 
 * @param event
 * @param ui
 * @returns
 */
function getItemDetails(event, ui) {

	$.get("/sale_item_lookup", {
		itemId : ui.item.value,
		locationId : txn_locationId
	}, function(data, status) {

		txnAction.showSaleLineItem(data);
		reCalculateTenders();
		
	});

	$('#searchText').val('');

}

function saleItemChanged(cntl) {
	var itemId = cntl.id.replace(/[^0-9]/gi, '');

	if (txnAction.reCalculateSaleItemAmounts(cntl, itemId)) {
		this.reCalculateTenders();
	}
}

function deleteTender(deleteIndex) {
	var totalDueAmt = +$('#hc_totalDueAmt').val();
	txnAction.removeTenderItem(deleteIndex, totalDueAmt);
}

function reCalculateTenders() {
	var totalDueAmt = +$('#hc_totalDueAmt').val();
	txnAction.calculateDue(0.00, totalDueAmt, 'Cash');
}


function deleteSaleItem(deleteItemId) {
	var totalDueAmt = +$('#hc_totalDueAmt').val();
	txnAction.deleteSaleItemInList(deleteItemId, totalDueAmt);
}

function receiptPrint(data){
	$('#txnReceiptModal').modal({backdrop: 'static', keyboard: false});

}
