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
var customerSearch = new Customer();
var token = $("meta[name='_csrf']").attr("content");
var txnStartTime;
var txnEndTime;
var viewType='COMPACT';

/**
 * This function will ensure the item auto complete functionality is executed when at least 3 letters has been typed in the item category
 * 
 * @returns
 */
$(function() {
	
	introJs().start();
	
	
	txnStartTime = moment().format("DD-MMM-YY hh:mm:ss");
	
	$(window).keydown(function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
	      return false;
	    }
	  });
	
	$("#searchText").autocomplete({
		minLength : 3,
		source : function(request, response) {
			$('#itemErrorMsg').hide();
			$('#itemSearchBusy').removeClass('d-none');
			$.post({
				url : "/sku_lookup",
				data : $('form[id=autoCompleteForm]').serialize(),
				success : function(data) {
					$('#itemSearchBusy').addClass('d-none');
					if(!data.length){
						$('#searchText').val('');						
						$('#itemErrorMsg').addClass('invalid-feedback');
						$('#itemErrorMsg').html('Item Not found!!');
						$('#itemErrorMsg').show();
					}else{
						response($.map(data, function(item) {
							var dataVal = "<small>" + item.itemId + "-" + item.name + "</small>";
							var descVal = item.name;
							
							var finalItemPic='';
							if(item.imageType && item.imageType.length>0)
								finalItemPic='data:'+item.imageType+';base64,'+item.baseEncodedImage;
							else
								finalItemPic='/images/item_image_default_200.png';
							
							return {
								label : dataVal,
								value : item.itemId,
								desc : descVal,
								longDesc : item.longDesc,
								pic : '/images/default_image.png',
								skuImage: finalItemPic
							}
						}));
					}
				}
			});
		},
		change: function (event, ui) {
            if (ui.item == null || ui.item == undefined) {
            	$('#searchText').val('');						
				$('#itemErrorMsg').addClass('invalid-feedback');
				$('#itemErrorMsg').html('Please select an valid item!!');
				$('#itemErrorMsg').show();
            } else {
            	$('#itemErrorMsg').hide();
            }
        },
		select : function(event, ui) {
			event.preventDefault();
			if (ui.item) {
				getItemDetails(event, ui);
			}
		}
	});
	
	$("#customerSearchText").autocomplete({
		minLength : 3,
		appendTo : $('#txnCustomerModal'),
		source : function(request, response) {
			$('#customerErrorMsg').hide();
			$('#customerSearchBusy').removeClass('d-none');
			
			customerSearch.customerSearchText=$('#customerSearchText').val();
			var formdata=JSON.stringify(customerSearch);
					
			// AJAX call here and refresh the Expense Screen after the save
			$.ajax({
				url : '/customer_account_lookup',
				type : 'POST',
				cache : false,
				data : formdata,
				
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					
					$('#customerSearchBusy').addClass('d-none');
					if(!data.length){
						$('#customerSearchText').val('');						
						$('#customerErrorMsg').addClass('invalid-feedback');
						$('#customerErrorMsg').html('Customer Not found!!');
						$('#customerErrorMsg').show();
					}else{
						response($.map(data, function(item) {
							
							var itemlbl=item.name +' - '+ item.phone;
							
							return {
								label : itemlbl,
								value : item.name,
								desc : item.email,
								phone : item.phone,
								email : item.email,
								name : item.name,
								customerId : item.customerId
								
							}
						}));
					}
					
				},				
				beforeSend : function(xhr) {
					xhr.setRequestHeader('X-CSRF-TOKEN', token)
				}
			});			
			
			
			
		},
		change: function (event, ui) {
            if (ui.item == null || ui.item == undefined) {
            	$('#customerSearchText').val('');						
				$('#customerErrorMsg').addClass('invalid-feedback');
				$('#customerErrorMsg').html('Please select a Valid Customer!!');
				$('#customerErrorMsg').show();
            } else {
            	$('#customerErrorMsg').hide();
            }
        },
		select : function(event, ui) {
			event.preventDefault();
			if (ui.item) {
				showCustomerDetails(event, ui);
			}
		}
	});	
	
	

	$('#btnTenderOK').click(function() {
		if (txnAction.tenderLineItem.validateTenderLineItem() && txnAction.validateCreditTender()) {
			txnAction.processTender();
		}
	});

	$('#btnLastTxn').click(function() {
		printLastxn();
	});	
	
	$('#btnAssociateCustomer').click(function() {
		$('#txnCustomerModal').modal({backdrop: 'static', keyboard: false});
	});
	
	$('#btnAddCustomer').click(function() {
		$('#div_customerAdd').removeClass('d-none');
		$('#div_customerActions').removeClass('d-none');
		$('#div_customerSearch').addClass('d-none');
		
		
	});
	
	$('#btnSearchCustomer').click(function() {
		$('#div_customerAdd').addClass('d-none');
		$('#div_customerSearch').removeClass('d-none');
		$('#div_customerActions').addClass('d-none');
		$('#div_customerResult').html('');
		
	});	
	
	
	$('#btnSaveCustomer').click(function() {
		associateCustomerToTxn();
	});
	
	$('#btnCloseRegister').click(function() {
		window.location.href = register_close_url + '=' + txn_registerId;
	});
		
	
	$('#btnCompleteTxn').click(function() {
		$('#screenBusyModal').modal({backdrop: 'static', keyboard: false});
		txnEndTime = moment().format("DD-MMM-YY hh:mm:ss");
		txnAction.processCompletedTxn();
	});
	
	
	
	
	$('input[name="btnView"]').change(function() {
		var viewRadio = $('input[name="btnView"]');
		var viewNames = viewRadio.filter(':checked')
		if(viewNames.length>0){
			var selectedViewType=viewNames[0].id;
			if(selectedViewType=='btnCompact')
				viewType='COMPACT';
			else if (selectedViewType=='btnDetailed')
				viewType='DETAILED';
		}
		
		txnAction.renderAll();
		
		
	});
	
	
	
	
	$('#btnNewSaleTxn').click(function() {
		startNewSaleTxn();
	});
	
	$('#btnNewReturnTxn').click(function() {
		startNewReturnTxn();
	});		

	$('#btnPrintReceiptAndNewTxn').click(function() {
		printTxnReceipt();
	});
	
	$('#btnViewTxnReceipt').click(function() {
		viewTxnReceipt();
	});			
	
	
	
	
    /*
	 * This section will register the shortcut keys for various functions on sale screen
	 */
    
    // item search selection
    Mousetrap.bind('enter', function() { $('#searchText').focus(); });
    // item qty selection
    Mousetrap.bind('ctrl+q', function() { });
    // item discount selection
    Mousetrap.bind('ctrl+d', function() { });
    // tender due text selection
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
	
	if (this.element[0].id.indexOf('customerSearchText') > -1) {
		
		return $("<li></li>").data("item.autocomplete", item).html($('<div/>', {
			'class' : 'row'
		}).append($('<div/>', {
			'class' : 'col'
		}).append($('<div/>', {
			'class' : 'card-text'
		}).append($('<span/>', {
			html : "<b>" + item.label + "</b><br/>" + item.desc
		}))))).appendTo(ul);
		
	}else{
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
	}

	
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
	// Update the txn no after successful save
	rcpt_txn_id= data.uniqueTxnNo;
	rcpt_txn_no= data.txnNo;
	rcpt_pdfBlob=data.pdfbytes;
	$('#txnReceiptModal').modal({backdrop: 'static', keyboard: false});
	$('#screenBusyModal').modal('hide');
	
}

function viewTxnReceipt(){
	$('#progressDiv').removeClass("d-none");

	var pdfRcptUrl = view_rcpt_viewer_url+'?file='+view_rcpt_url;
	$('#receiptPDFContainer').attr("src",pdfRcptUrl);
	$('#progressDiv').addClass("d-none");
}



function printTxnReceipt(){
	var txnHeader = new TransactionHeader();
	txnHeader.uniqueTxnNo=rcpt_txn_id;
	txnHeader.locationId=txn_locationId;
	txnHeader.registerId=txn_registerId;
	txnHeader.businessDate=txn_businessDate;
	txnHeader.txnNo=rcpt_txn_no;

	// The AJAX call for receipt printing
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


function printLastxn(){
	var lastTxnRcptURL="/pos/last_txn_rcpt";
	lastTxnRcptURL+="?locationId="+txn_locationId+"&registerId="+txn_registerId+"&businessDate="+txn_businessDate;
	
	var pdfRcptUrl = view_rcpt_viewer_url+'?file='+lastTxnRcptURL;
	
	var txnWindow=window.open(pdfRcptUrl);
	txnWindow.document.title=i18next.t('last_txn_title');
	return false;
}



