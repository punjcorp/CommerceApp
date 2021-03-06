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
		if (txnAction.tenderLineItem.validateReturnTenderLineItem() && txnAction.validateCreditTender()) {
			txnAction.processTender();
		}
	});
	
	$('#btnLastTxn').click(function() {
		printLastxn();
	});	
	
	$('#btnAssociateCustomer').click(function() {
		$('#txnCustomerModal').modal({backdrop: 'static', keyboard: false});
	});
	
	$('#btnCloseRegister').click(function() {
		window.location.href = register_close_url + '=' + txn_registerId;
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
	$('[id^=btnDeleteTLI]').focus(function() {
		var tliIndex=this.id.replace(/[^0-9]/gi, '');
		$('#'+tliIndex+'tenderLineItem').css("background", "#89dfaa");
	}, function() {
		var tliIndex=this.id.replace(/[^0-9]/gi, '');
		$('#'+tliIndex+'tenderLineItem').css("background", "#8ee4af");
	});
	
	$('[id^=btnDeleteTLI]').hover(function() {
		var tliIndex=this.id.replace(/[^0-9]/gi, '');
		$('#'+tliIndex+'tenderLineItem').css("background", "#89dfaa");
	}, function() {
		var tliIndex=this.id.replace(/[^0-9]/gi, '');
		$('#'+tliIndex+'tenderLineItem').css("background", "#8ee4af");
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
	
	
	$('#btnCompleteTxn').click(function() {
		$('#screenBusyModal').modal({backdrop: 'static', keyboard: false});
		txnEndTime = moment().format("DD-MMM-YY hh:mm:ss");
		txnAction.processCompletedTxn();
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
	
	$('#dueAmt').change(function() {
		var dueAmt=+$('#dueAmt').val();
		if(dueAmt>0){
			dueAmt= dueAmt * -1;
			$('#dueAmt').val(dueAmt.toFixed(2));
		}				
	});	
	
    /*
	 * This section will register the shortcut keys for various functions on sale screen
	 */
    
    
    var itemCntl=document.querySelector('#searchText');
    Mousetrap(itemCntl).bind('right', function() {
    	var itemSearchText=$('#searchText').val();
    	if(itemSearchText==undefined || itemSearchText=='')
    		txnAction.focusFirstItemQty(); 
    });
    
    Mousetrap.bind('ctrl a', function() { $('#btnLastTxn').focus(); });
    var btnLastTxnCntl=document.querySelector('#btnLastTxn');
    Mousetrap(btnLastTxnCntl).bind('left', function() { $('#searchText').focus(); });
    Mousetrap(btnLastTxnCntl).bind('right', function() { $('#btnAssociateCustomer').focus(); });
    Mousetrap(btnLastTxnCntl).bind('down', function() { txnAction.focusFirstItemQty(); });
    
    var btnAssociateCustomerCntl=document.querySelector('#btnAssociateCustomer');
    Mousetrap(btnAssociateCustomerCntl).bind('left', function() { $('#btnLastTxn').focus(); });
    Mousetrap(btnAssociateCustomerCntl).bind('right', function() { $('#btnCompact').focus(); });
    Mousetrap(btnAssociateCustomerCntl).bind('down', function() { txnAction.focusFirstItemQty(); });
    
    var btnCompactCntl=document.querySelector('#btnCompact');
    Mousetrap(btnCompactCntl).bind('left', function(e) { 
    	e.preventDefault();
    	$('#btnAssociateCustomer').focus();
    	return false;
    });
    Mousetrap(btnCompactCntl).bind('right', function() { $('#btnDetailed').focus(); });
    Mousetrap(btnCompactCntl).bind('down', function() { txnAction.focusFirstItemQty(); });
    
    var btnDetailedCntl=document.querySelector('#btnDetailed');
    Mousetrap(btnDetailedCntl).bind('left', function() { $('#btnCompact').focus(); });
    Mousetrap(btnDetailedCntl).bind('right', function(e) {
    	e.preventDefault();
    	$('#btnCloseRegister').focus();
    	return false;
    });
    Mousetrap(btnDetailedCntl).bind('down', function() { txnAction.focusFirstItemQty(); });
        
    var btnCloseRegisterCntl=document.querySelector('#btnCloseRegister');
    Mousetrap(btnCloseRegisterCntl).bind('left', function() { $('#btnDetailed').focus(); });
    Mousetrap(btnCloseRegisterCntl).bind('right', function() { txnAction.focusFirstItemQty(); });
    Mousetrap(btnCloseRegisterCntl).bind('down', function() { txnAction.focusFirstItemQty(); });
    
    var dueAmtCntl=document.querySelector('#dueAmt');
    Mousetrap(dueAmtCntl).bind('left', function() { txnAction.focusLastDeleteBtn();  });
    Mousetrap(dueAmtCntl).bind('enter', function() { 
    	if($('#btnCompleteTxn').hasClass('d-none')){
    		if (txnAction.tenderLineItem.validateTenderLineItem() && txnAction.validateCreditTender()) {
    			txnAction.processTender();
    		}
    	}else{
    		$('#screenBusyModal').modal({backdrop: 'static', keyboard: false});
    		txnEndTime = moment().format("DD-MMM-YY hh:mm:ss");
    		txnAction.processCompletedTxn();
    	}
    	
    	
    });
    
    // item search selection
    Mousetrap.bindGlobal('ctrl+enter', function() { $('#searchText').focus(); });
    // sale line item selection
    Mousetrap.bindGlobal('ctrl+alt+enter', function() { txnAction.focusFirstItemQty(); });    
    // tender due text selection
    Mousetrap.bindGlobal('ctrl+shift+enter', function() { $('#dueAmt').focus();});            
    // tender line item selection
    Mousetrap.bindGlobal('ctrl+alt+t', function() { txnAction.focusFirstTender(); });

	
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

	if(cntl.id.indexOf('li_qty')==0){
		var qty=+$('#'+cntl.id).val();
		if(qty && qty=='')
			qty=0;
		else if(qty>0)
			qty=qty * -1;
		
		$('#'+cntl.id).val(qty);
			
	}else if(cntl.id.indexOf('li_discountAmt')==0){
		var discountAmt=+$('#'+cntl.id).val();
		if(discountAmt && discountAmt=='')
			discountAmt=0;
		
		$('#'+cntl.id).val(discountAmt);
	}
	
	
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
	/* txnAction.calculateDue(0.00, totalDueAmt, 'Cash'); */
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
	
	resizeModal('txnReceiptModal', 'receiptPDFContainer');
	
	$('#progressDiv').removeClass("d-none");
	var pdfRcptUrl = view_rcpt_viewer_url+'?file='+view_rcpt_url;
	$('#receiptPDFContainer').attr("src",pdfRcptUrl);
	$('#progressDiv').addClass("d-none");
}

function startNewTxn(){
	var newTxnURL=new_txn_prefix;
	window.location.href = newTxnURL;
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

