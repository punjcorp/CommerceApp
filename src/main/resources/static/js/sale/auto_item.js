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
	
	$('#btnNewTxn').click(function() {
		startNewTxn();
	});	

	$('#btnPrintReceiptAndNewTxn').click(function() {
		printTxnReceipt();
	});
	
	$('#btnViewTxnReceipt').click(function() {
		viewTxnReceipt();
	});			
	
	 // use plugins and options as needed, for options, detail see
    // http://i18next.com/docs/
    i18next.init({
      lng: current_locale, // evtl. use language-detector https://github.com/i18next/i18next-browser-languageDetector
      resources: { // evtl. load via xhr https://github.com/i18next/i18next-xhr-backend
        en: {
          translation: {
              common_currency_sign_inr: 'INR',
              sale_txn_validate_item:"The selected item already exists in the transaction, please increase the quantity if needed",
              sale_txn_validate_qty:'The quantity should be a positive value always.Please correct the quantity.',
              sale_txn_validate_range_discount:'The discount amount should be between INR 0.00 and item price amount.Please correct the amount.',
              sale_txn_validate_exceed_discount:'The discount amount cannot be more than item price',
              sale_txn_validate_tender:'Please select tender for the payment',
              sale_txn_validate_amount_tender:'The tendered amount should be more than 0.00'              
          }
        },
        hi: {
            translation: {
            	common_currency_sign_inr: '₹'
            }
          },
          pa: {
              translation: {
            	  common_currency_sign_inr: 'ਪੈਸਾ'
              }
            }          
      }
    }, function(err, t) {
      // for options see
      // https://github.com/i18next/jquery-i18next#initialize-the-plugin
      jqueryI18next.init(i18next, $);
      
    });	
	
	
    /*
     * This section will register the shortcut keys for various functions on sale screen
     */
    
    //item search selection
    Mousetrap.bind('enter', function() { $('#searchText').focus(); });
    //item qty selection
    Mousetrap.bind('ctrl+q', function() { });
    //item discount selection
    Mousetrap.bind('ctrl+d', function() { });
    //tender due text selection
    Mousetrap.bind('ctrl+enter', function() { $('#dueAmt').focus();});            
    // First tender selection - CASH
    Mousetrap.bind('ctrl+1', function() { });
    // First tender selection - Credit Card
    Mousetrap.bind('ctrl+2', function() { });
    // First tender selection - Paypal
    Mousetrap.bind('ctrl+3', function() { });
    // First tender selection - Paytm
    Mousetrap.bind('ctrl+4', function() { });
    // First tender selection - Print the Receipt
    Mousetrap.bind('ctrl+shift+p', function() { });
    // First tender selection - View the Receipt
    Mousetrap.bind('ctrl+shift+v', function() { });
    // First tender selection - Start New Txn
    Mousetrap.bind('alt+enter', function() { });    
    
	
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

function postTxnSave(data){
	//Update the txn no after successful save
	rcpt_txn_id= data.uniqueTxnNo;
	rcpt_txn_no= data.txnNo;
	rcpt_pdfBlob=data.pdfbytes;
	$('#txnReceiptModal').modal({backdrop: 'static', keyboard: false});
	
}

function viewTxnReceipt(){
	$('#progressDiv').removeClass("d-none");

	var pdfRcptUrl = view_rcpt_viewer_url+'?file='+view_rcpt_url;
	$('#receiptPDFContainer').attr("src",pdfRcptUrl);
	$('#progressDiv').addClass("d-none");
}

function startNewTxn(){
	var newTxnURL=new_txn_prefix+'='+txn_registerId +'&regName='+txn_registerName;
	window.location.href = newTxnURL;
}

function printTxnReceipt(){
	var txnHeader = new TransactionHeader();
	txnHeader.uniqueTxnNo=rcpt_txn_id;
	txnHeader.locationId=txn_locationId;
	txnHeader.registerId=txn_registerId;
	txnHeader.businessDate=txn_businessDate;
	txnHeader.txnNo=rcpt_txn_no;

	//The AJAX call for receipt printing
	var token = $("meta[name='_csrf']").attr("content");
	var formdata = JSON.stringify(txnHeader);
	// AJAX call here and refresh the sell item page with receipt printing
	$.ajax({
		url : print_rcpt_url,
		type : 'POST',
		cache : false,
		data : formdata,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			startNewTxn();
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader('X-CSRF-TOKEN', token)
		}
	});	

}


