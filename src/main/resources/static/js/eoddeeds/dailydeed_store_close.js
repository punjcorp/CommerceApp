var totalAmountVal = 0;
var totalCountVal = 0;
var token = $("meta[name='_csrf']").attr("content");

var alertUtil = new Utils();
var denomLineItems = new Array();
var dNL = new DenominationLineItem();

$(function() {

	$('#btnPrintTxnReceipt').click(function() {
        printTxnReceipt();
	});
	
	$('#btnViewTxnReceipt').click(function() {
        viewTxnReceipt();
	});	
		
	$('#btnOpenStore').click(function() {
        viewStoreOepen();
	});
	
	$('input:radio[name=locationId]').change(function() {
		var locValue = $(this).val();
		updateLocCloseRequest(locValue);
	});

	$("#btnConcileStore").click(function() {

		var enteredValue = $('#tenders0\\.calTAmount').val();
		var expectedValue = $('#concilationBean\\.expectedTotalAmt').val();

		validateConcilationAmounts(enteredValue, expectedValue);

	});

	$("#btnCancelConciliation").click(function() {
		window.location.href = sale_txn_url;
	});

	// use plugins and options as needed, for options, detail see
	// http://i18next.com/docs/
	i18next
			.init(
					{
						lng : current_locale, // evtl. use language-detector https://github.com/i18next/i18next-browser-languageDetector
						resources : { // evtl. load via xhr https://github.com/i18next/i18next-xhr-backend
							en : {
								translation : {
									common_currency_sign_inr: '₹',
									error_entered_low : 'The <b>Counted Total Amount</b> is less than <b>Expected Closing Amount</b>. <br><br>Please <b>Approve</b> to proceed with the Entered Total Amount or <b>Cancel</b> to make changes.',
									error_entered_high : 'The <b>Counted Total Amount</b> is greater than <b>Expected Closing Amount</b>. <br><br>Please <b>Approve</b> to proceed with the Entered Total Amount or <b>Cancel</b> to make changes.',
									error_entered_zero : 'Please enter amount greater than <b>₹ 0.00</b> to reconcile.',
									error_simple_alert_header : 'Alert Message',
									error_confirmation_alert_header : 'Confirmation Message',
									alert_btn_ok : 'OK',
									alert_btn_approve : 'Approve' ,
									alert_btn_cancel : 'Cancel',
									screen_lbl_tender_denomination : 'Tender Denomination',
									screen_lbl_tender_denomination_first_option : 'Select Denomination',
									screen_lbl_media_count : 'Media Count',
									screen_lbl_amount : 'Amount',
									screen_lbl_media_count_placeholder : 'Enter Tender Denomination Count',
									screen_lbl_amount_placeholder : 'Enter Tender Denomination Amount',
									error_store_closure_invalid_status : 'The location status is not valid for Closure process!!',
									error_store_closure_invalid_bdate : 'The business date is not valid for the selected location!!',
								}
							},
							hi : {
								translation : {

								}
							},
							pa : {
								translation : {

								}
							}
						}
					}, function(err, t) {
						// for options see
						// https://github.com/i18next/jquery-i18next#initialize-the-plugin
						jqueryI18next.init(i18next, $);

					});

});



function addNewDenom(selectedIndex){
	selectedIndex= +selectedIndex;
	var dLineItem=new DenominationLineItem();
	dLineItem.initialize(selectedIndex+1);
	denomLineItems.splice(selectedIndex+1,0,dLineItem);
	
	updateIndexValues();
	renderAll();
}

function removeSelectedDenom(selectedIndex){
	selectedIndex= +selectedIndex;
	$('#denominationLine'+selectedIndex).remove();
	denomLineItems.splice(selectedIndex,1);

	updateIndexValues();
	renderAll();
	
}

function updateIndexValues(){
	$.each(denomLineItems, function(index, denomLineItem) {
		denomLineItem.denomIndex=index;
	});
}

function renderAll(){
	$('#denomContainer').html('');
	$.each(denomLineItems, function(index, denomLineItem) {
		denomLineItem.render();
	});
	updateTenderTotal(0);
}

function initializeDenominationLineItems(denominationLineItems){
	var dLineItem;
	$.each(denominationLineItems, function(index, denominationLineItem) {
		dLineItem=new DenominationLineItem();
		dLineItem.initialize(index, denominationLineItem.denominationId, denominationLineItem.denomValue, denominationLineItem.mediaCount, denominationLineItem.amount);		
		denomLineItems.push(dLineItem);
	});
}

function validateConcilationAmounts(enteredValue, expectedValue) {
	var btnLabels = new Array();
	var locValue= $('input:radio[name=locationId]').filter(':checked').val();
	var locStatus=$('#'+locValue+'_loc_status').val();
	var locBDate=$('#'+locValue+'_loc_bdate').val();
	
	if(locStatus==undefined || locStatus!='OPEN_STORE'){
		btnLabels = [ i18next.t('alert_btn_ok'), '' ];
		alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('error_store_closure_invalid_status'), btnLabels);
		return;
	}
	
	if(locBDate==undefined || locBDate==''){
		btnLabels = [ i18next.t('alert_btn_ok'), '' ];
		alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('error_store_closure_invalid_bdate'), btnLabels);
		return;
	}
	
	if (enteredValue != undefined && enteredValue != '' && enteredValue>0) {
		if (enteredValue == expectedValue) {
			processStoreClosure();
		} else if (enteredValue < expectedValue) {
			btnLabels = [ i18next.t('alert_btn_approve'), i18next.t('alert_btn_cancel') ];
			alertUtil.renderAlert('CONFIRM', i18next.t('error_confirmation_alert_header'), i18next.t('error_entered_low'), btnLabels);
		} else if (enteredValue > expectedValue) {
			btnLabels = [ i18next.t('alert_btn_approve'), i18next.t('alert_btn_cancel') ];
			alertUtil.renderAlert('CONFIRM', i18next.t('error_confirmation_alert_header'), i18next.t('error_entered_high'), btnLabels);
		}

	} else {
		btnLabels = [ i18next.t('alert_btn_ok'), '' ];
		alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('error_entered_zero'), btnLabels);
	}
}



function alertAction(cntl) {
	if (cntl.id.indexOf('Cancel') > 0) {
		$('#alertModal').modal('hide');
	} else {
		processStoreClosure();
	}

}

function actionDenomination(tenderId, denominationId, actionParam, formName) {

	$("#selectedTenderId").val(tenderId);
	$("#selectedDenominationId").val(denominationId);
	submitForm(formName, actionParam);
}

function submitForm(formName, actionParam) {
	$('<input />').attr('type', 'hidden').attr('name', actionParam).appendTo('#' + formName);
	$('#' + formName).submit();
}

function changedDenomination(tndrIndex, denomIndex) {
	var denomVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId').val();
	var denomText = $('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId  option:selected').text();
	denomText = +denomText.replace(/[^0-9.]/gi, '');

	var mediaCountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.mediaCount').val();
	var amountVal = denomText * mediaCountVal;
	amountVal = amountVal.toFixed(2);

	var denomLineItem=denomLineItems[denomIndex];
	denomLineItem.denominationId=denomVal;
	denomLineItem.denominationVal=denomText;
	denomLineItem.denomCount=mediaCountVal;
	denomLineItem.denomAmount = amountVal;
	
	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val(amountVal);
	updateTenderTotal(tndrIndex);
}

function changedCount(tndrIndex, denomIndex) {
	var denomVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId').val();
	var denomText = $('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId  option:selected').text();
	denomText = +denomText.replace(/[^0-9.]/gi, '');

	var mediaCountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.mediaCount').val();
	var amountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val();

	amountVal = denomText * mediaCountVal;
	amountVal = amountVal.toFixed(2);
	
	var denomLineItem=denomLineItems[denomIndex];
	denomLineItem.denominationId=denomVal;
	denomLineItem.denominationVal=denomText;
	denomLineItem.denomCount=mediaCountVal;
	denomLineItem.denomAmount = amountVal;

	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val(amountVal);
	updateTenderTotal(tndrIndex);

}

function changedAmount(tndrIndex, denomIndex) {
	var denomVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId').val();
	var denomText = $('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId  option:selected').text();
	denomText = +denomText.replace(/[^0-9.]/gi, '');

	var mediaCountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.mediaCount').val();
	var amountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val();

	amountVal = amountVal.toFixed(2);
	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val(amountVal);

	var denomLineItem=denomLineItems[denomIndex];
	denomLineItem.denominationId=denomVal;
	denomLineItem.denominationVal=denomText;
	denomLineItem.denomCount=mediaCountVal;
	denomLineItem.denomAmount = amountVal;
	
	if ((amountVal % denomText) == 0) {
		
		$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.mediaCount').val((amountVal / denomText));
		updateTenderTotal(tndrIndex);
	} else {
		amountVal = denomText * mediaCountVal;
		amountVal = amountVal.toFixed(2);
		
		denomLineItem.denomAmount = amountVal;
		
		$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val(amountVal);
		alert('The amount should be exact multiple of denomination');
	}

}

function updateTenderTotal(tndrIndex) {
	totalAmountVal = 0;
	totalCountVal = 0;
	$("[id^=tenders" + tndrIndex + "]").each(function() {
		if (this.id.indexOf("mediaCount") >= 0) {
			totalCountVal += +$(this).val();
		}
		if (this.id.indexOf("amount") >= 0) {
			totalAmountVal += +$(this).val();
		}
	});

	$('#' + tndrIndex + 'lblMediaCount').text(totalCountVal);
	$('#' + tndrIndex + 'lbltenderAmount').text("INR " + totalAmountVal.toFixed(2));
	$('#tenders' + tndrIndex + '\\.calMCount').val(totalCountVal);
	$('#tenders' + tndrIndex + '\\.calTAmount').val(totalAmountVal.toFixed(2));

}

function updateLocCloseRequest(locValue){
	var locStatus=$('#'+locValue+'_loc_status').val();
	var locBDate=$('#'+locValue+'_loc_bdate').val();
	var locName=$('#'+locValue+'_loc_name').val();
	$('#locationId').val(locValue);
	$('#locationName').val(locName);
	$('#locationStatus').val(locStatus);
	$('#businessDate').val(locBDate);
	$('#register').val(1);
	
	
	
}

function processStoreClosure(){
	$('#alertModal').modal('hide');
	$('#screenBusyModal').modal({backdrop: 'static', keyboard: false});
	var formdata = $('#closeStoreForm').serialize();
	$.post({
		url : "/pos/close_store",
		data : formdata,
		success : function(data) {
			postClosureAction(data);
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader('X-CSRF-TOKEN', token)
		}
	});
	
}

function postClosureAction(data){
	$('#screenBusyModal').modal('hide');
	
	if(data.status!=undefined && data.status=='F'){
		btnLabels = [ i18next.t('alert_btn_ok'), '' ];
		alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), data.status.msg, btnLabels);
	} else if(data.status!=undefined && data.status=='S'){

        txn_locationId =data.resultObj.txnId.locationId;
        txn_registerId =data.resultObj.txnId.registerId;
        txn_businessDate =data.resultObj.txnId.locationId;
        rcpt_txn_no = data.resultObj.txnId.transactionSeq;
        rcpt_txn_id = data.resultObj.uniqueTxnNo;

		$('#txnReceiptModal').modal({backdrop: 'static', keyboard: false});
		
	}
	
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
            viewStoreClose();
        },
        beforeSend : function(xhr) {
            xhr.setRequestHeader('X-CSRF-TOKEN', token)
        }
    });

}

function viewTxnReceipt(){
    $('#progressDiv').removeClass("d-none");

    var pdfRcptUrl = view_rcpt_viewer_url+'?file='+view_rcpt_url;
    $('#receiptPDFContainer').attr("src",pdfRcptUrl);
    $('#progressDiv').addClass("d-none");
}

function viewStoreOepen(){
    window.location.href = open_store_url;
}

