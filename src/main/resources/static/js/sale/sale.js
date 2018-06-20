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
	txnStartTime = moment().format("DD-MMM-YY hh:mm:ss");
	
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
		$('#div_customerResult').html('');
		
	});	
	
	
	$('#btnSaveCustomer').click(function() {
		
		associateCustomerToTxn();
		
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
	
	 // use plugins and options as needed, for options, detail see
    // http://i18next.com/docs/
    i18next.init({
      lng: current_locale, // evtl. use language-detector https://github.com/i18next/i18next-browser-languageDetector
      resources: { // evtl. load via xhr https://github.com/i18next/i18next-xhr-backend
        en: {
          translation: {
              common_currency_sign_inr: '₹',
              sale_txn_validate_item:"The selected item already exists in the transaction, please increase the quantity if needed",
              sale_txn_validate_qty:'The quantity should be a positive value always.Please correct the quantity.',
              sale_txn_validate_range_discount:'The discount amount should be between INR 0.00 and item price amount.Please correct the amount.',
              sale_txn_validate_exceed_discount:'The discount amount cannot be more than item price',
              sale_txn_validate_tender:'Please select tender for the payment',
              sale_txn_validate_amount_tender:'The tendered amount should be more than 0.00',
              sale_txn_lbl_qty: 'Quantity',
              sale_txn_lbl_unit_cost: 'Unit Price',
              sale_txn_lbl_suggested_price: 'Suggested Price',
              sale_txn_lbl_mrp: 'Max Retail Price',
              sale_txn_lbl_discount: 'Discount',
              sale_txn_lbl_item_price: 'Item Price',
              sale_txn_lbl_tax: 'Tax',
              sale_txn_lbl_sgst: 'SGST',
              sale_txn_lbl_cgst: 'CGST',
              sale_txn_lbl_igst: 'IGST',
              sale_txn_lbl_item_total: 'Item Total',
              sale_txn_lbl_discount_percent: '%',
              sale_txn_lbl_discount_amount: '₹'
              
          }
        },
        hi: {
            translation: {
            	common_currency_sign_inr: '₹'
            }
          },
          pa: {
              translation: {
            	  common_currency_sign_inr: 'ਰੁ.'
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
	
	window.open(pdfRcptUrl);
	return false;
}

function associateCustomerToTxn(){
	var customerType='CUSTOMER';
	var customerId=$('#h_customerId').val();
	
	var customerName='undefined';
	var customerPhone='undefined';
	var customerEmail='undefined';
	
	if(customerId === undefined || customerId == null || customerId.length <= 0){
		if(validateCustomer()){
			customerName=$('#name').val();
			customerPhone=$('#phone').val();
			customerEmail=$('#email').val();
		}else{
			return;
		}

	}else{
		customerName=$('#h_customerName').val();
		customerPhone=$('#h_customerPhone').val();
		customerEmail=$('#h_customerEmail').val();
		
	}	
	
	// Assign data to JS class
	txnAction.associateCustomer(customerId,customerType,customerName,customerPhone,customerEmail);
	$('#txnCustomerModal').modal('hide');
	
}


function validateCustomer(){
	
	var validationPassed = true;
	
	var customerNameValidation = $('#name').parsley({
		maxlength : 80,
		minlength : 4,
		required: ''
	});
	if (customerNameValidation.isValid()) {
		$('#name').removeClass('is-invalid');
		$('#nameMsg').hide();
	} else {
		validationPassed = false;
		$('#name').addClass('is-invalid');
		$('#nameMsg').addClass('invalid-feedback');
		if(customerNameValidation.validationResult[0].assert.name=='required'){
			$('#nameMsg').html('<h6>Please enter the Customer Name!</h6>');
		}else{
			$('#nameMsg').html('<h6>Customer Name should be between 4 and 80 characters!</h6>');
		}
		$('#nameMsg').show();
	}	
	
	
	var customerPhoneValidation = $('#phone').parsley({
		minlength: 7,
		maxlength : 12,
		required: ''
	});
	if (customerPhoneValidation.isValid()) {
		$('#phone').removeClass('is-invalid');
		$('#phoneMsg').hide();
	} else {
		validationPassed = false;
		$('#phone').addClass('is-invalid');
		$('#phoneMsg').addClass('invalid-feedback');
		if(customerPhoneValidation.validationResult[0].assert.name=='required'){
			$('#phoneMsg').html('<h6>Please enter the Customer Phone!</h6>');
		}else if(customerPhoneValidation.validationResult[0].assert.name=='maxlength'){
			$('#phoneMsg').html('<h6>Customer Phone cannot be more than 12 digits!</h6>');
		}else if(customerPhoneValidation.validationResult[0].assert.name=='minlength'){
			$('#phoneMsg').html('<h6>Customer Phone should be minimum of 7 digits!</h6>');
		}
		
		$('#phoneMsg').show();
	}
	
	
	var customerEmailValidation = $('#email').parsley({
		maxlength : 80,
		minlength: 5
	});
	if (customerEmailValidation.isValid()) {
		$('#email').removeClass('is-invalid');
		$('#emailMsg').hide();
	} else {
		validationPassed = false;
		$('#email').addClass('is-invalid');
		$('#emailMsg').addClass('invalid-feedback');
		if(customerEmailValidation.validationResult[0].assert.name=='type'){
			$('#emailMsg').html('<h6>Please enter a valid Email!</h6>');
		}else{
			$('#emailMsg').html('<h6>Customer Email should be between 5 and 80 characters!</h6>');
		}
		$('#emailMsg').show();
	}
	
	return validationPassed;
}


function showCustomerDetails(event, ui){
	
	$('#div_customerSearch').addClass('d-none');
	
	var customerName='';
	customerName +='<div class="row">';
	customerName +='<div class="col text-left">';
	customerName +='<label for="name"><small><span class="pos-mandatory">Customer Name</span></small></label>';
	customerName +='<div class="input-group text-left">';
	customerName +='<h5><span>'+ui.item.customerId+' - '+ui.item.name+'</span></h5>';
	customerName +='<input type="hidden" id="h_customerId" value="'+ui.item.customerId+'"></input>';
	customerName +='<input type="hidden" id="h_customerName" value="'+ui.item.name+'"></input>';
	customerName +='</div>';
	customerName +='</div>';
	customerName +='</div>';
			
	
	var customerPhone='';
	customerPhone +='<div class="row">';
	customerPhone +='<div class="col text-left">';
	customerPhone +='<label for="name"><small><span class="pos-mandatory">Customer Phone</span></small></label>';
	customerPhone +='<div class="input-group text-left">';
	customerPhone +='<h5><span>'+ui.item.phone+'</span></h5>';
	customerPhone +='<input type="hidden" id="h_customerPhone" value="'+ui.item.phone+'"></input>';
	customerPhone +='</div>';
	customerPhone +='</div>';
	customerPhone +='</div>';

	var customerEmail='';
	customerEmail +='<div class="row">';
	customerEmail +='<div class="col text-left">';
	customerEmail +='<label for="name"><small><span >Customer Email</span></small></label>';
	customerEmail +='<div class="input-group text-left">';
	customerEmail +='<h5><span>'+ui.item.email+'</span></h5>';
	customerEmail +='<input type="hidden" id="h_customerEmail" value="'+ui.item.email+'"></input>';
	customerEmail +='</div>';
	customerEmail +='</div>';
	customerEmail +='</div>';	
	
	
	var customerDetails=customerName+customerPhone+customerEmail;
	$('#div_customerResult').html(customerDetails);
	
	$('#div_customerActions').removeClass('d-none');
	
}

