/**
 * This JS file has all the sources needed for item lookup
 * 
 */
var txnDataTable;
var startDateTime;
var endDateTime;
var token = $("meta[name='_csrf']").attr("content");
var alertUtil = new Utils();
var uniqueTxnNo = '';

$(function() {

	$(window).keydown(function(event) {
		if (event.keyCode == 13) {
			event.preventDefault();
			return false;
		}
	});

	startDateTime = $('#startDateTime').flatpickr({
		dateFormat : 'd-M-y',
		maxDate : "today"
	});

	endDateTime = $('#endDateTime').flatpickr({
		dateFormat : 'd-M-y',
		maxDate : "today"
	});

	txnDataTable = $('#txnDataTable').DataTable({
		responsive : true,
		colReorder : true,
		buttons : [ 'copyHtml5', 'excelHtml5', 'csvHtml5', 'pdfHtml5' ],
		columns : [ {
			"name" : "uniqueTxnNo"
		}, {
			"name" : "createdDate"
		}, {
			"name" : "txnType"
		}, {
			"name" : "customerName"
		}, {
			"name" : "createdBy"
		}, {
			"name" : "totalTax"
		}, {
			"name" : "totalAmount"
		}, {
			"name" : "actions"
		} ]
	});

	txnDataTable.buttons().container().appendTo($('#tableBtns'));

	$('#btnPrintDuplicate').click(function() {
		var rcpt_action_url = encodeURIComponent(view_rcpt_url + "=" + uniqueTxnNo + "&rcpt_type=DUPLICATE");
		var finalUrl = view_rcpt_viewer_url + '?file=' + rcpt_action_url;
		var win = window.open(finalUrl, '_blank');
		win.focus();
	});

	$('#btnPrintOriginal').click(function() {
		var rcpt_action_url = encodeURIComponent(view_rcpt_url + "=" + uniqueTxnNo + "&rcpt_type=ORIGINAL");
		var finalUrl = view_rcpt_viewer_url + '?file=' + rcpt_action_url;
		var win = window.open(finalUrl, '_blank');
		win.focus();
	});

});

function showTxnDetailsModal(uniqueTxnNo) {
	this.uniqueTxnNo = uniqueTxnNo;
	$('#txnDetailsModal').modal('show');
	$('#txnDetailsProgressDiv').removeClass('d-none');
	$('#txnModalBodyContainer').addClass('d-none');

	$.get("/pos/txn_details_lookup", {
		uniqueTxnNo : uniqueTxnNo
	}, function(data, status) {

		$('#txnDetailsProgressDiv').addClass('d-none');
		if (data == undefined || data == null || data == '') {
			$('#txnModalBodyContainer').removeClass('d-none');

			$('#txnModalHeader').addClass('invalid-feedback');
			$('#txnModalHeader').html('The transaction details were not found!!');
		} else {
			showTxnDetailsResponse(data);
		}

	});

}

function showTxnDetailsResponse(txnDetails) {

	var txnHeaderHtml = parseTxnHeader(txnDetails);
	$('#txnModalHeader').html(txnHeaderHtml);

	var txnCustomerHtml = parseTxnCustomer(txnDetails);
	$('#txnModalCustomerDetails').html(txnCustomerHtml);

	var txnItemsHtml = parseTxnItems(txnDetails);
	$('#txnModalItemDetails').html(txnItemsHtml);

	$('#txnModalTenderDetails').html('');

	$('#txnModalBodyContainer').removeClass('d-none');
	$('#txnDetailsProgressDiv').addClass('d-none');
}

function parseTxnItems(txnDetails) {
	var itemHeaderHtml = '<div class="row">';
	itemHeaderHtml += '<div class="col-1">';
	itemHeaderHtml += '<h6>S#</h6>';
	itemHeaderHtml += '</div>';
	itemHeaderHtml += '<div class="col-3">';
	itemHeaderHtml += '<h6>Item Description</h6>';
	itemHeaderHtml += '</div>';
	itemHeaderHtml += '<div class="col">';
	itemHeaderHtml += '<h6>HSN/SAC</h6>';
	itemHeaderHtml += '</div>';
	itemHeaderHtml += '<div class="col">';
	itemHeaderHtml += '<h6>Unit Price</h6>';
	itemHeaderHtml += '</div>';
	itemHeaderHtml += '<div class="col">';
	itemHeaderHtml += '<h6>Quantity</h6>';
	itemHeaderHtml += '</div>';
	itemHeaderHtml += '<div class="col">';
	itemHeaderHtml += '<h6>Extended Price</h6>';
	itemHeaderHtml += '</div>';
	itemHeaderHtml += '<div class="col">';
	itemHeaderHtml += '<h6>Tax Amount</h6>';
	itemHeaderHtml += '</div>';
	itemHeaderHtml += '<div class="col">';
	itemHeaderHtml += '<h6>Item Total</h6>';
	itemHeaderHtml += '</div>';
	itemHeaderHtml += '</div>';

	var itemDetailHtml = '';
	$.each(txnDetails.txnSaleLineItems, function(index, saleItem) {
		itemDetailHtml += '<div class="row">';
		itemDetailHtml += '<div class="col-1">';
		itemDetailHtml += index + 1;
		itemDetailHtml += '</div>';
		itemDetailHtml += '<div class="col-3">';
		itemDetailHtml += saleItem.itemId + ' - ' + saleItem.itemDesc;
		itemDetailHtml += '</div>';
		itemDetailHtml += '<div class="col">';
		itemDetailHtml += saleItem.hsnNo;
		itemDetailHtml += '</div>';
		itemDetailHtml += '<div class="col">';
		itemDetailHtml += i18next.t('common_currency_sign_inr') + ' ' + saleItem.unitPrice.toFixed(2);
		itemDetailHtml += '</div>';
		itemDetailHtml += '<div class="col">';
		itemDetailHtml += saleItem.qty;
		itemDetailHtml += '</div>';
		itemDetailHtml += '<div class="col">';
		itemDetailHtml += i18next.t('common_currency_sign_inr') + ' ' + saleItem.extendedAmount.toFixed(2);
		itemDetailHtml += '</div>';
		itemDetailHtml += '<div class="col">';
		itemDetailHtml += i18next.t('common_currency_sign_inr') + ' ' + saleItem.taxAmount.toFixed(2);
		itemDetailHtml += '</div>';
		itemDetailHtml += '<div class="col">';
		itemDetailHtml += i18next.t('common_currency_sign_inr') + ' ' + Math.round(saleItem.grossAmount).toFixed(2);
		itemDetailHtml += '</div>';
		itemDetailHtml += '</div>';
	});

	var finalItemHtml = itemHeaderHtml + itemDetailHtml;
	return finalItemHtml;
}

function parseTxnCustomer(txnDetails) {
	var leftSideHtml = '<div class="col-4">';
	leftSideHtml += '<div class="form-group">';
	leftSideHtml += '<label><small><span>Customer Name</span></small></label>';
	leftSideHtml += '<div class="input-group">';
	if (typeof (txnDetails.customer) == 'undefined' || txnDetails.customer == undefined || typeof (txnDetails.customer.name) == 'undefined'
			|| txnDetails.customer.name == undefined || txnDetails.customer.name == '')
		leftSideHtml += '<h6><span> - </span></h6>';
	else
		leftSideHtml += '<h6><span>' + txnDetails.customer.name + '</span></h6>';
	leftSideHtml += '</div>';
	leftSideHtml += '</div>';
	leftSideHtml += '</div>';

	var rightHtml = '<div class="col-4">';
	rightHtml += '<div class="form-group">';
	rightHtml += '<label><small><span>Email</span></small></label>';
	rightHtml += '<div class="input-group">';
	if (typeof (txnDetails.customer) == 'undefined' || txnDetails.customer == undefined || typeof (txnDetails.customer.email) == 'undefined'
			|| txnDetails.customer.email == undefined || txnDetails.customer.email == '')
		rightHtml += '<h6><span> - </span></h6>';
	else
		rightHtml += '<h6><span>' + txnDetails.customer.email + '</span></h6>';
	rightHtml += '</div>';
	rightHtml += '</div>';
	rightHtml += '</div>';

	rightHtml += '<div class="col-4">';
	rightHtml += '<div class="form-group">';
	rightHtml += '<label><small><span>Phone</span></small></label>';
	rightHtml += '<div class="input-group">';
	if (typeof (txnDetails.customer) == 'undefined' || txnDetails.customer == undefined || typeof (txnDetails.customer.phone) == 'undefined'
			|| txnDetails.customer.phone == undefined || txnDetails.customer.phone == '')
		rightHtml += '<h6><span> - </span></h6>';
	else
		rightHtml += '<h6><span>' + txnDetails.customer.phone + '</span></h6>';
	rightHtml += '</div>';
	rightHtml += '</div>';
	rightHtml += '</div>';

	var finalCustomerHtml = '<div class="row">';
	finalCustomerHtml += leftSideHtml + rightHtml;
	finalCustomerHtml += '</div>';

	return finalCustomerHtml;
}

function parseTxnHeader(txnDetails) {
	var invoiceHtml1 = '<div class="col-1">';
	invoiceHtml1 += '<label><small><span>Invoice No</span></small></label>';
	invoiceHtml1 += '</div>';
	invoiceHtml1 += '<div class="col-3">';
	invoiceHtml1 += '<div class="input-group">';
	invoiceHtml1 += '<h6><span>' + txnDetails.transactionHeader.uniqueTxnNo + '</span></h6>';
	invoiceHtml1 += '</div>';
	invoiceHtml1 += '</div>';

	var invoiceHtml2 = '<div class="col-1">';
	invoiceHtml2 += '<label><small><span>Invoice Date</span></small></label>';
	invoiceHtml2 += '</div>';
	invoiceHtml2 += '<div class="col-3">';
	invoiceHtml2 += '<div class="input-group">';
	invoiceHtml2 += '<h6><span>' + txnDetails.transactionHeader.businessDate.substr(0, 9) + '</span></h6>';
	invoiceHtml2 += '</div>';
	invoiceHtml2 += '</div>';

	var txnTotalHtml1 = '<div class="col-2">';
	txnTotalHtml1 += '<label><small><span>Sub Total Amount</span></small></label>';
	txnTotalHtml1 += '</div>';
	txnTotalHtml1 += '<div class="col">';
	txnTotalHtml1 += '<div class="input-group">';
	txnTotalHtml1 += '<h6><span>' + i18next.t('common_currency_sign_inr') + ' ' + txnDetails.transactionHeader.subTotalAmt.toFixed(2) + '</span></h6>';
	txnTotalHtml1 += '</div>';
	txnTotalHtml1 += '</div>';

	var txnTotalHtml2 = '<div class="col-2">';
	txnTotalHtml2 += '<label><small><span>Total Tax Amount</span></small></label>';
	txnTotalHtml2 += '</div>';
	txnTotalHtml2 += '<div class="col">';
	txnTotalHtml2 += '<div class="input-group">';
	txnTotalHtml2 += '<h6><span>' + i18next.t('common_currency_sign_inr') + ' ' + txnDetails.transactionHeader.totalTaxAmt.toFixed(2) + '</span></h6>';
	txnTotalHtml2 += '</div>';
	txnTotalHtml2 += '</div>';

	var txnTotalHtml3 = '<div class="col-2">';
	txnTotalHtml3 += '<label><small><span>Total Amount</span></small></label>';
	txnTotalHtml3 += '</div>';
	txnTotalHtml3 += '<div class="col">';
	txnTotalHtml3 += '<div class="input-group">';
	txnTotalHtml3 += '<h6><span>' + i18next.t('common_currency_sign_inr') + ' ' + txnDetails.transactionHeader.totalDueAmt.toFixed(2) + '</span></h6>';
	txnTotalHtml3 += '</div>';
	txnTotalHtml3 += '</div>';

	var txnHeaderHtml = '<div class="row">';
	txnHeaderHtml += invoiceHtml1;
	txnHeaderHtml += txnTotalHtml1;
	txnHeaderHtml += txnTotalHtml3;
	txnHeaderHtml += '</div>';

	txnHeaderHtml += '<div class="row">';
	txnHeaderHtml += invoiceHtml2;
	txnHeaderHtml += txnTotalHtml2;
	txnHeaderHtml += '</div>';

	return txnHeaderHtml;

}

function confirmTransactionDeletion(txnNo) {
	uniqueTxnNo = txnNo;

	var btnLabels = [ i18next.t('alert_btn_delete'), i18next.t('alert_btn_cancel') ];
	var btnActions = [ 'btnDeleteTxn', 'btnCancel' ];
	commonUtil.renderAlert('GLOBAL', i18next.t('error_confirmation_alert_header'), i18next.t('screen_confirm_txn_deletion'), btnLabels, btnActions,
			'globalAction');

}

function deleteTxnConfirmed() {
	window.location.href = delete_txn_url + "=" + uniqueTxnNo;
	$('#screenBusyModal').modal({
		backdrop : 'static',
		keyboard : false
	});
}
