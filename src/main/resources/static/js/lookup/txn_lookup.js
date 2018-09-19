/**
 * This JS file has all the sources needed for item lookup
 * 
 */
var txnDataTable;
var startDateTime;
var endDateTime;
var token = $("meta[name='_csrf']").attr("content");
var alertUtil = new Utils();
var uniqueTxnNo='';

$(function() {
	
	$(window).keydown(function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
	      return false;
	    }
	  });
	
	startDateTime=$('#startDateTime').flatpickr({
		dateFormat : 'd-M-y',
		maxDate : "today"
	});
	
	endDateTime=$('#endDateTime').flatpickr({
		dateFormat : 'd-M-y',
		maxDate : "today"
	});
	
	txnDataTable=$('#txnDataTable').DataTable( {
        responsive : true,
        colReorder: true,
        buttons: [
            'copyHtml5',
            'excelHtml5',
            'csvHtml5',
            'pdfHtml5'
        ],
		columns: [ { "name": "invoiceNo" },
			{ "name": "createdDate" },
			{ "name": "txnType" },
		    { "name": "customerName" },
		    { "name": "createdBy" },
		    { "name": "totalTax" },
		    { "name": "totalAmount" },
		    { "name": "actions" } ]
    } );
	
	txnDataTable.buttons().container().appendTo( $('#tableBtns') );
	
	
	$('#btnPrintDuplicate').click(function() {
		var rcpt_action_url=encodeURIComponent(view_rcpt_url+"="+uniqueTxnNo+"&rcpt_type=DUPLICATE");
		var finalUrl=view_rcpt_viewer_url+'?file='+rcpt_action_url;
		var win = window.open(finalUrl, '_blank');
		win.focus();
	});
	
	
	$('#btnPrintOriginal').click(function() {
		var rcpt_action_url=encodeURIComponent(view_rcpt_url+"="+uniqueTxnNo+"&rcpt_type=ORIGINAL");
		var finalUrl=view_rcpt_viewer_url+'?file='+rcpt_action_url;
		var win = window.open(finalUrl, '_blank');
		win.focus();
	});

	
});


function showTxnDetailsModal(uniqueTxnNo){
	this.uniqueTxnNo=uniqueTxnNo;
	$('#txnDetailsModal').modal('show');
	$('#txnDetailsProgressDiv').removeClass('d-none');
	$('#txnModalBodyContainer').addClass('d-none');

	$.get("/pos/txn_details_lookup", {
		uniqueTxnNo : uniqueTxnNo
	}, function(data, status) {

		$('#txnDetailsProgressDiv').addClass('d-none');
		if(data==undefined || data==null || data==''){
			$('#txnModalBodyContainer').removeClass('d-none');
			
			$('#txnModalHeader').addClass('invalid-feedback');
			$('#txnModalHeader').html('The transaction details were not found!!');
		}else{
			showTxnDetailsResponse(data);
		}
		
	});
	
	
}


function showTxnDetailsResponse(txnDetails){
	
	var txnHeaderHtml=parseTxnHeader(txnDetails);
	$('#txnModalHeader').html(txnHeaderHtml);
	
	var txnCustomerHtml=parseTxnCustomer(txnDetails);
	$('#txnModalCustomerDetails').html(txnCustomerHtml);
	
	var txnItemsHtml=parseTxnItems(txnDetails);
	$('#txnModalItemDetails').html(txnItemsHtml);
	
	$('#txnModalTenderDetails').html('');
	
	$('#txnModalBodyContainer').removeClass('d-none');
	$('#txnDetailsProgressDiv').addClass('d-none');
}

function parseTxnItems(txnDetails){
	var itemHeaderHtml='<div class="row">';
	itemHeaderHtml+='<div class="col-1">';
	itemHeaderHtml+='<h6>S#</h6>';
	itemHeaderHtml+='</div>';
	itemHeaderHtml+='<div class="col-3">';
	itemHeaderHtml+='<h6>Item Description</h6>';
	itemHeaderHtml+='</div>';
	itemHeaderHtml+='<div class="col">';
	itemHeaderHtml+='<h6>HSN/SAC</h6>';
	itemHeaderHtml+='</div>';
	itemHeaderHtml+='<div class="col">';
	itemHeaderHtml+='<h6>Unit Price</h6>';
	itemHeaderHtml+='</div>';
	itemHeaderHtml+='<div class="col">';
	itemHeaderHtml+='<h6>Quantity</h6>';
	itemHeaderHtml+='</div>';
	itemHeaderHtml+='<div class="col">';
	itemHeaderHtml+='<h6>Extended Price</h6>';
	itemHeaderHtml+='</div>';
	itemHeaderHtml+='<div class="col">';
	itemHeaderHtml+='<h6>Tax Amount</h6>';
	itemHeaderHtml+='</div>';
	itemHeaderHtml+='<div class="col">';
	itemHeaderHtml+='<h6>Item Total</h6>';
	itemHeaderHtml+='</div>';
	itemHeaderHtml+='</div>';
	
	var itemDetailHtml='';
	$.each(txnDetails.txnSaleLineItems, function(index,saleItem){
		itemDetailHtml+='<div class="row">';
		itemDetailHtml+='<div class="col-1">';
		itemDetailHtml+=index+1;
		itemDetailHtml+='</div>';
		itemDetailHtml+='<div class="col-3">';
		itemDetailHtml+=saleItem.itemId+' - '+saleItem.itemDesc;
		itemDetailHtml+='</div>';
		itemDetailHtml+='<div class="col">';
		itemDetailHtml+=saleItem.hsnNo;
		itemDetailHtml+='</div>';
		itemDetailHtml+='<div class="col">';
		itemDetailHtml+= i18next.t('common_currency_sign_inr') + ' ' +saleItem.unitPrice.toFixed(2);
		itemDetailHtml+='</div>';
		itemDetailHtml+='<div class="col">';
		itemDetailHtml+=saleItem.qty;
		itemDetailHtml+='</div>';
		itemDetailHtml+='<div class="col">';
		itemDetailHtml+=i18next.t('common_currency_sign_inr') + ' ' +saleItem.extendedAmount.toFixed(2);
		itemDetailHtml+='</div>';
		itemDetailHtml+='<div class="col">';
		itemDetailHtml+=i18next.t('common_currency_sign_inr') + ' ' +saleItem.taxAmount.toFixed(2);
		itemDetailHtml+='</div>';
		itemDetailHtml+='<div class="col">';
		itemDetailHtml+= i18next.t('common_currency_sign_inr') + ' ' +Math.round(saleItem.grossAmount).toFixed(2);
		itemDetailHtml+='</div>';
		itemDetailHtml+='</div>';
	});
	
	
	var finalItemHtml=itemHeaderHtml+itemDetailHtml;
	return finalItemHtml;
}

function parseTxnCustomer(txnDetails){
	var leftSideHtml='<div class="col-6">';
	leftSideHtml+='<div class="input-group">';
	leftSideHtml+='<h5><span>'+txnDetails.customer.name+'</span></h5>'; 
	leftSideHtml+='</div>';
	leftSideHtml+='<div class="input-group">';
	leftSideHtml+='<span>'+txnDetails.customer.primaryAddress.address1+'</span>, ';
	leftSideHtml+='</div>';
	if(typeof(txnDetails.customer.primaryAddress.address2)!=='undefined' && txnDetails.customer.primaryAddress.address2!=undefined && txnDetails.customer.primaryAddress.address2!=""){
		leftSideHtml+='<div class="input-group">';
		leftSideHtml+='<span>'+txnDetails.customer.primaryAddress.address2+'</span>, ';
		leftSideHtml+='</div>';
	}
	
	leftSideHtml+='<div class="input-group">';
	leftSideHtml+='<span>'+txnDetails.customer.primaryAddress.city+'</span>, ';
	if(typeof(txnDetails.customer.primaryAddress.district)!=='undefined' && txnDetails.customer.primaryAddress.district!=undefined && txnDetails.customer.primaryAddress.district!="")
		leftSideHtml+='<span>'+txnDetails.customer.primaryAddress.district+'</span>, ';
	leftSideHtml+='</div>';
	leftSideHtml+='<div class="input-group">';
	leftSideHtml+='<span>'+txnDetails.customer.primaryAddress.state+'</span> - ';
	leftSideHtml+='<span>'+txnDetails.customer.primaryAddress.pincode+'</span>.';
	leftSideHtml+='</div>';
	leftSideHtml+='</div>';
	
	var rightHtml='<div class="col-6">';
	
	rightHtml+='<div class="row">';
	rightHtml+='<div class="col-4">';
	rightHtml+='<label><small><span>Email</span></small></label>';
	rightHtml+='</div>';
	rightHtml+='<div class="col-8">';
	rightHtml+='<div class="input-group">';
	rightHtml+='<h6><span>'+txnDetails.customer.email+'</span></h6>';
	rightHtml+='</div>';
	rightHtml+='</div>';
	rightHtml+='</div>';
	
	
	rightHtml+='<div class="row">';
	rightHtml+='<div class="col-4">';
	rightHtml+='<label><small><span>Phone</span></small></label>';
	rightHtml+='</div>';
	rightHtml+='<div class="col-8">';
	rightHtml+='<div class="input-group">';
	rightHtml+='<h6>';
	rightHtml+='<span>'+txnDetails.customer.phone+'</span>';
	if(typeof(txnDetails.customer.phone2)!=='undefined' && txnDetails.customer.phone2!=undefined && txnDetails.customer.phone2!="")
		rightHtml+=', <span>'+txnDetails.customer.phone2+'</span>';
	rightHtml+='</h6>';
	rightHtml+='</div>';
	rightHtml+='</div>';
	rightHtml+='</div>';	
	
	
	rightHtml+='<div class="row">';
	rightHtml+='<div class="col-4">';
	rightHtml+='<label><small><span>GSTIN</span></small></label>';
	rightHtml+='</div>';
	rightHtml+='<div class="col-8">';
	rightHtml+='<div class="input-group">';
	rightHtml+='<h6><span>'+txnDetails.customer.gstNo+'</span></h6>';
	rightHtml+='</div>';
	rightHtml+='</div>';
	rightHtml+='</div>';
	
	rightHtml+='<div class="row">';
	rightHtml+='<div class="col-4">';
	rightHtml+='<label><small><span>PAN</span></small></label>';
	rightHtml+='</div>';
	rightHtml+='<div class="col-8">';
	rightHtml+='<div class="input-group">';
	rightHtml+='<h6><span>'+txnDetails.customer.panNo+'</span></h6>';
	rightHtml+='</div>';
	rightHtml+='</div>';
	rightHtml+='</div>';
	
	rightHtml+='<div class="row">';
	rightHtml+='<div class="col-4">';
	rightHtml+='<label><small><span>State Code</span></small></label>';
	rightHtml+='</div>';
	rightHtml+='<div class="col-8">';
	rightHtml+='<div class="input-group">';
	rightHtml+='<h6><span>'+txnDetails.customer.stateCode+'</span></h6>';
	rightHtml+='</div>';
	rightHtml+='</div>';
	rightHtml+='</div>';
	
	
	rightHtml+='</div>';
	
	var finalCustomerHtml='<div class="row">';
	finalCustomerHtml+=leftSideHtml+rightHtml;
	finalCustomerHtml+='</div>';
	
	return finalCustomerHtml;
}

function parseTxnHeader(txnDetails){
	var invoiceHtml1='<div class="col-1">';
	invoiceHtml1+='<label><small><span>Invoice No</span></small></label>';
	invoiceHtml1+='</div>';
	invoiceHtml1+='<div class="col-3">';
	invoiceHtml1+='<div class="input-group">';
	invoiceHtml1+='<h6><span>'+txnDetails.invoiceNo+'</span></h6>'; 
	invoiceHtml1+='</div>';
	invoiceHtml1+='</div>';
	
	var invoiceHtml2='<div class="col-1">';
	invoiceHtml2+='<label><small><span>Invoice Date</span></small></label>';
	invoiceHtml2+='</div>';	
	invoiceHtml2+='<div class="col-3">';	
	invoiceHtml2+='<div class="input-group">';
	invoiceHtml2+='<h6><span>'+txnDetails.transactionHeader.businessDate.substr(0,9)+'</span></h6>'; 
	invoiceHtml2+='</div>';
	invoiceHtml2+='</div>';
	
	
	var orderHtml1='<div class="col-1">';
	orderHtml1+='<label><small><span>P.O. No</span></small></label>';
	orderHtml1+='</div>';
	orderHtml1+='<div class="col-3">';
	orderHtml1+='<div class="input-group">';
	if(typeof(txnDetails.orderRequest.customerOrderNo)!=='undefined' && txnDetails.orderRequest.customerOrderNo!=undefined && txnDetails.orderRequest.customerOrderNo!="")
		orderHtml1+='<h6><span>'+txnDetails.orderRequest.customerOrderNo+'</span></h6>'; 
	orderHtml1+='</div>';
	orderHtml1+='</div>';
	
	var orderHtml2='<div class="col-1">';
	orderHtml2+='<label><small><span>P.O. Date</span></small></label>';
	orderHtml2+='</div>';
	orderHtml2+='<div class="col-3">';	
	orderHtml2+='<div class="input-group">';
	if(typeof(txnDetails.orderRequest.orderDate)!=='undefined' && txnDetails.orderRequest.orderDate!=undefined && txnDetails.orderRequest.orderDate!="")
		orderHtml2+='<h6><span>'+txnDetails.orderRequest.orderDate.substr(0,9)+'</span></h6>'; 
	orderHtml2+='</div>';
	orderHtml2+='</div>';
	
	
	var gprrHtml1='<div class="col-1">';
	gprrHtml1+='<label><small><span>GP/RR No</span></small></label>';
	gprrHtml1+='</div>';
	gprrHtml1+='<div class="col-3">';
	gprrHtml1+='<div class="input-group">';
	if(typeof(txnDetails.shipment.gpPrNo)!=='undefined' && txnDetails.shipment.gpPrNo!=undefined && txnDetails.shipment.gpPrNo!="")	
		gprrHtml1+='<h6><span>'+txnDetails.shipment.gpPrNo+'</span></h6>'; 
	gprrHtml1+='</div>';
	gprrHtml1+='</div>';
	
	var gprrHtml2='<div class="col-1">';
	gprrHtml2+='<label><small><span>GP/RR Date</span></small></label>';
	gprrHtml2+='</div>';
	gprrHtml2+='<div class="col-3">';
	gprrHtml2+='<div class="input-group">';
	if(typeof(txnDetails.shipment.gpPrDate)!=='undefined' && txnDetails.shipment.gpPrDate!=undefined && txnDetails.shipment.gpPrDate!="")
		gprrHtml2+='<h6><span>'+txnDetails.shipment.gpPrDate.substr(0,9)+'</span></h6>'; 
	gprrHtml2+='</div>';
	gprrHtml2+='</div>';
	
	
	var transportHtml1='<div class="col-1">';
	transportHtml1+='<label><small><span>Transport</span></small></label>';
	transportHtml1+='</div>';
	transportHtml1+='<div class="col-3">';
	transportHtml1+='<div class="input-group">';
	if(typeof(txnDetails.shipment.shippingCompany)!=='undefined' && txnDetails.shipment.shippingCompany!=undefined && txnDetails.shipment.shippingCompany!="")
		transportHtml1+='<h6><span>'+txnDetails.shipment.shippingCompany+'</span></h6>'; 
	transportHtml1+='</div>';
	transportHtml1+='</div>';
	
	var transportHtml2='<div class="col-1">';
	transportHtml2+='<label><small><span>Vehicle No</span></small></label>';
	transportHtml2+='</div>';
	transportHtml2+='<div class="col-3">';
	transportHtml2+='<div class="input-group">';
	if(typeof(txnDetails.shipment.vehicleNo)!=='undefined' && txnDetails.shipment.vehicleNo!=undefined && txnDetails.shipment.vehicleNo!="")
		transportHtml2+='<h6><span>'+txnDetails.shipment.vehicleNo+'</span></h6>'; 
	transportHtml2+='</div>';
	transportHtml2+='</div>';
	
	
	var txnTotalHtml1='<div class="col-2">';
	txnTotalHtml1+='<label><small><span>Sub Total Amount</span></small></label>';
	txnTotalHtml1+='</div>';
	txnTotalHtml1+='<div class="col">';
	txnTotalHtml1+='<div class="input-group">';
	txnTotalHtml1+='<h6><span>'+ i18next.t('common_currency_sign_inr') + ' ' +txnDetails.transactionHeader.subTotalAmt.toFixed(2)+'</span></h6>'; 
	txnTotalHtml1+='</div>';
	txnTotalHtml1+='</div>';
	
	var txnTotalHtml2='<div class="col-2">';
	txnTotalHtml2+='<label><small><span>Total Tax Amount</span></small></label>';
	txnTotalHtml2+='</div>';
	txnTotalHtml2+='<div class="col">';
	txnTotalHtml2+='<div class="input-group">';
	txnTotalHtml2+='<h6><span>'+ i18next.t('common_currency_sign_inr') + ' ' +txnDetails.transactionHeader.totalTaxAmt.toFixed(2)+'</span></h6>'; 
	txnTotalHtml2+='</div>';
	txnTotalHtml2+='</div>';
	
	
	var txnTotalHtml3='<div class="col-2">';
	txnTotalHtml3+='<label><small><span>Total Amount</span></small></label>';
	txnTotalHtml3+='</div>';
	txnTotalHtml3+='<div class="col">';
	txnTotalHtml3+='<div class="input-group">';
	txnTotalHtml3+='<h6><span>'+ i18next.t('common_currency_sign_inr') + ' ' +txnDetails.transactionHeader.totalDueAmt.toFixed(2)+'</span></h6>'; 
	txnTotalHtml3+='</div>';
	txnTotalHtml3+='</div>';
	
	var txnHeaderHtml='<div class="row">';
	txnHeaderHtml+=invoiceHtml1;
	txnHeaderHtml+=gprrHtml1;
	txnHeaderHtml+=txnTotalHtml1;
	txnHeaderHtml+='</div>';
	
	txnHeaderHtml+='<div class="row">';
	txnHeaderHtml+=invoiceHtml2;
	txnHeaderHtml+=gprrHtml2;
	txnHeaderHtml+=txnTotalHtml2;
	txnHeaderHtml+='</div>';
	
	txnHeaderHtml+='<div class="row">';
	txnHeaderHtml+=orderHtml1;
	txnHeaderHtml+=transportHtml1;
	txnHeaderHtml+=txnTotalHtml3;
	txnHeaderHtml+='</div>';
	
	txnHeaderHtml+='<div class="row">';
	txnHeaderHtml+=orderHtml2;
	txnHeaderHtml+=transportHtml2;
	txnHeaderHtml+='</div>';
	
	return txnHeaderHtml;
	
}


function confirmTransactionDeletion(txnNo){
	uniqueTxnNo=txnNo;

	var btnLabels = [ i18next.t('alert_btn_delete'), i18next.t('alert_btn_cancel') ];
	var btnActions = [ 'btnDeleteTxn', 'btnCancel' ];
	commonUtil.renderAlert('GLOBAL', i18next.t('error_confirmation_alert_header'), i18next.t('screen_confirm_txn_deletion'), btnLabels, btnActions, 'globalAction');
	
}

function deleteTxnConfirmed(){
	window.location.href=delete_txn_url+"="+uniqueTxnNo;
	$('#screenBusyModal').modal({backdrop: 'static', keyboard: false});
}

