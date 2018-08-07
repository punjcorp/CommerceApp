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
var payment = new PaymentHeader();
var accountDTO = new AccountDTO();
var customerBean = new Customer();
var token = $("meta[name='_csrf']").attr("content");
var txnStartTime;
var txnEndTime;

$(function() {
	txnStartTime = moment().format("DD-MMM-YY hh:mm:ss");
	$('#tenderAmount').val('0.00');
	
	$("#entityType").autocomplete({
		minLength : 3,
		source : function(request, response) {
			$('#customerSearchBusy').removeClass('d-none');
			
			customerBean.customerSearchText = $("#entityType").val();
			customerBean.searchAccount = 'true';
			
			customerChangeFlag = 'T';
			var formdata = JSON.stringify(customerBean);

			// AJAX call here and refresh the Expense Screen after the save
			$.ajax({
				url : '/admin/customer_account_lookup',
				type : 'POST',
				cache : false,
				data : formdata,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(customers) {

					$('#customerSearchBusy').addClass('d-none');
					if (!customers.length) {
						$('#entityId').val('');
						$('#entityTypeMsg').addClass('invalid-feedback');
						$('#entityTypeMsg').html('<h6>Please enter a valid Customer!</h6>');
						$('#entityTypeMsg').show();
		
						$('#customerDetails').addClass('d-none');
						$('#customerAccountDetails').addClass('d-none');
						$('#searchBtn').addClass('d-none');
						$('#tenderCard').addClass('d-none');
						
					} else {
						$('#entityTypeMsg').hide();

					
						response($.map(customers, function(customer) {
							var dataVal = "" + customer.name + "-( Ph- " + customer.phone + " )";
							var descVal = customer.email;
							return {
								label : dataVal,
								name : customer.name,
								phone : customer.phone,
								customerId : customer.customerId,
								email : descVal,
								type: 'CUSTOMER'
							}
						}));
					
					}
					

				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader('X-CSRF-TOKEN', token)
				},
				error: function(e) {
					$('#customerSearchBusy').addClass('d-none');
					$('#entityId').val('');
					$('#entityTypeMsg').addClass('invalid-feedback');
					$('#entityTypeMsg').html('<h6>No Customer found!</h6>');
					$('#entityTypeMsg').show();

					$('#customerDetails').addClass('d-none');
					$('#customerAccountDetails').addClass('d-none');
					$('#searchBtn').addClass('d-none');
					$('#tenderCard').addClass('d-none');
				}
			});

		},
		change : function(event, ui) {
			if (ui.item == null || ui.item == undefined) {
				$('#customerSearchBusy').addClass('d-none');
				$('#entityId').val('');
				$('#entityTypeMsg').addClass('invalid-feedback');
				$('#entityTypeMsg').html('<h6>Please choose a valid Customer from the list!</h6>');
				$('#entityTypeMsg').show();

				$('#customerDetails').addClass('d-none');
				$('#customerAccountDetails').addClass('d-none');
				$('#searchBtn').addClass('d-none');
				$('#tenderCard').addClass('d-none');
				
			} else {
				$('#entityTypeMsg').hide();
			}
		},

		select : function(event, ui) {
			event.preventDefault();
			if (ui.item) {
				getPaymentAccountDetails(event, ui);
			}
		}
	});

	$('input[name="tenderRadio"]').change(function() {
		displayTenderControls();
		clearTenderErrors();
	});

	$('#tenderAmount').change(function(){
		var tenderAmt=+$('#tenderAmount').val();
		if(tenderAmt==''){
			$('#tenderAmount').val('0.00');
		}else if(tenderAmt>0){
			$('#tenderAmount').val(tenderAmt.toFixed(2));
		}
	})
	
	$('#btnAddTender').click(function() {
		addTender();
	});

	$('#btnSavePayment').click(function() {
		txnEndTime = moment().format("DD-MMM-YY hh:mm:ss");
		if (validatePaymentDetails()) {
			$('#screenBusyModal').modal({backdrop: 'static', keyboard: false});
			readPaymentDetails();
			payment.savePayment();
		}
	});
	
	$('#btnNewPayment').click(function() {
		startNewPayment();
	});

	$('#btnViewPaymentReceipt').click(function() {
		viewPaymentReceipt();
	});

	$('#btnPrintReceiptAndNewPayment').click(function() {
		printPaymentReceipt();
	});
	
	$('#btnCustomerLookup').click(function() {
		window.location.href = customerLookupURL;
	});

});

function startNewPayment() {
	window.location.href = newPaymentURL;
}


function viewPaymentReceipt() {
	$('#progressDiv').removeClass("d-none");
	view_rcpt_url = encodeURIComponent(view_rcpt_url + '=' + txn_paymentId);
	var pdfRcptUrl = view_rcpt_viewer_url + '?file=' + view_rcpt_url;
	$('#reportPDFContainer').attr("src", pdfRcptUrl);
	$('#progressDiv').addClass("d-none");

}


function printPaymentReceipt() {
	var token = $("meta[name='_csrf']").attr("content");
	var formdata = JSON.stringify(txnId);
	// AJAX call here and refresh the sell item page with receipt printing
	$.ajax({
		url : print_rcpt_url,
		type : 'POST',
		cache : false,
		data : formdata,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			startNewPayment();
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader('X-CSRF-TOKEN', token)
		}
	});
	
	
}


/* This section will allow the item listing to be in a specific format */
$["ui"]["autocomplete"].prototype["_renderItem"] = function(ul, item) {
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
};

/**
 * This function will take the selected expense reason code type
 * 
 * @param event
 * @param ui
 * @returns
 */
function getPaymentAccountDetails(event, ui) {
	
	accountDTO.entityType=ui.item.type;
	accountDTO.entityId=ui.item.customerId;
	
	$('#entityType').val(ui.item.name);
	$('#entityId').val(ui.item.customerId);	

	this.renderCustomerDetails(ui.item);
	
	var formdata = JSON.stringify(accountDTO);	
	
	// AJAX call here and refresh the Expense Screen after the save
	$.ajax({
		url : '/admin/payment_account',
		type : 'POST',
		cache : false,
		data : formdata,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(accountDTO) {
			
			$('#lbl_dueAmt').text(accountDTO.dueAmt.toFixed(2));
			$('#dueAmt').val(accountDTO.dueAmt.toFixed(2));
			
			if(accountDTO.dueAmt> 0){
				$('#dueSettleDiv').removeClass('d-none');
			}
			
			$('#customerAccountDetails').removeClass('d-none');
			$('#tenderCard').removeClass('d-none');
			
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader('X-CSRF-TOKEN', token)
		},
		error : function(e){
			$('#lbl_dueAmt').text('0.00');
			$('#dueAmt').val('');
			
			$('#tenderCard').addClass('d-none');
			$('#customerAccountDetails').addClass('d-none');
		}
	});
	
}



function renderCustomerDetails(customer){
	$('#lbl_cust_name').text(customer.name);
	$('#lbl_cust_email').text(customer.email);
	$('#lbl_cust_phone').text(customer.phone);

	$('#customer\\.customerId').val(customer.customerId);
	$('#customer\\.name').val(customer.name);
	$('#customer\\.phone').val(customer.phone);
	$('#customer\\.email').val(customer.emailOrg);
	$('#customer\\.customerType').val('CUSTOMER');

	$('#customerDetails').removeClass('d-none');
}


function displayTenderControls() {
	var tenderRadio = $('input[name="tenderRadio"]');
	var tenderId = tenderRadio.filter(':checked').val();
	var tenderType = $('#' + tenderId + 'tenderType').val();

	if (tenderType && tenderType == 'CASH') {
		$('#payeeAccountDiv').addClass('d-none');
		$('#payeeBankDiv').addClass('d-none');
		$('#payeeBranchDiv').addClass('d-none');
	}

	if (tenderType && tenderType == 'CC') {
		$('#payeeAccountDiv').removeClass('d-none');
		$('#payeeBankDiv').addClass('d-none');
		$('#payeeBranchDiv').addClass('d-none');
	}
	if (tenderType && tenderType == 'PAYPAL') {
		$('#payeeAccountDiv').removeClass('d-none');
		$('#payeeBankDiv').addClass('d-none');
		$('#payeeBranchDiv').addClass('d-none');
	}
	if (tenderType && tenderType == 'PAYTM') {
		$('#payeeAccountDiv').removeClass('d-none');
		$('#payeeBankDiv').addClass('d-none');
		$('#payeeBranchDiv').addClass('d-none');
	}
	if (tenderType && tenderType == 'CHEQUE') {
		$('#payeeAccountDiv').removeClass('d-none');
		$('#payeeBankDiv').removeClass('d-none');
		$('#payeeBranchDiv').removeClass('d-none');
	}

}


function clearTenderErrors() {
	$('#tenderAmount').val('0.00');
	$('#tenderAmount').removeClass('is-invalid');
	$('#tenderAmountMsg').hide();
	
	$('#tenderComment').val('');
	$('#tenderComment').removeClass('is-invalid');
	$('#tenderCommentMsg').hide();
	
	$('#accountNo').val('');
	$('#accountNo').removeClass('is-invalid');
	$('#accountNoMsg').hide();
	
	$('#bankName').val('');
	$('#bankName').removeClass('is-invalid');
	$('#bankMsg').hide();
	
	$('#bankBranch').val('');
	$('#bankBranch').removeClass('is-invalid');
	$('#branchMsg').hide();
	
}


function addTender() {
	if(validateTender()){
		var tenderRadio = $('input[name="tenderRadio"]');
		var tenderId = tenderRadio.filter(':checked').val();
		var tenderName = $('#' + tenderId + 'tenderName').val();
		var tenderType = $('#' + tenderId + 'tenderType').val();
		var tenderAmount = +$('#tenderAmount').val();
		var accountNo = $('#accountNo').val();
		var bankName = $('#bankName').val();
		var bankBranch = $('#bankBranch').val();
		var tenderDetails = $('#tenderComment').val();

		payment.addPaymentTender(tenderId, tenderName, tenderType, tenderAmount, accountNo, bankName, bankBranch, tenderDetails);
		calculateTotal();		
		//Hide any validation error
		$('#tenderAreaCard').removeClass('is-invalid');
		$('#tenderSelectionMsg').hide();
	}

}


function calculateTotal() {
	var totalAmount = payment.totalPaymentTenders();
	$('#lbl_paymentAmt').text(totalAmount.toFixed(2));
	$('#paymentAmt').val(totalAmount.toFixed(2));
	$('#tenderAmt').val('0.00');
	
}

function validateTender(){
	var validationPassed = true;
	
	
	var tenderRadio = $('input[name="tenderRadio"]');
	var tenderId = tenderRadio.filter(':checked').val();
	var tenderName = $('#' + tenderId + 'tenderName').val();
	
	var tenderAmount = +$('#tenderAmount').val();
	if(tenderAmount && tenderAmount>0){
		$('#tenderAmount').removeClass('is-invalid');
		$('#tenderAmountMsg').hide();
	}else{
		validationPassed = false;
		$('#tenderAmount').addClass('is-invalid');
		$('#tenderAmountMsg').addClass('invalid-feedback');
		$('#tenderAmountMsg').html('<h6>Please enter the tender Amount!</h6>');
		$('#tenderAmountMsg').show();		
	}	

	var tenderDtlsValidation = $('#tenderComment').parsley({
		maxlength : 100
	});
	if (tenderDtlsValidation.isValid()) {
		$('#tenderComment').removeClass('is-invalid');
		$('#tenderCommentMsg').hide();
	} else {
		validationPassed = false;
		$('#tenderComment').addClass('is-invalid');
		$('#tenderCommentMsg').addClass('invalid-feedback');
		$('#tenderCommentMsg').html('<h6>The Additional Details cannot be more than 100 characters!</h6>');
		$('#tenderCommentMsg').show();
	}
	
	
	if (tenderName!='Cash' ){
		//Account Number Validation
		var accountNoValidation = $('#accountNo').parsley({
			maxlength : 50,
			required: ''
		});
		if (accountNoValidation.isValid()) {
			$('#accountNo').removeClass('is-invalid');
			$('#accountNoMsg').hide();
		} else {
			validationPassed = false;
			$('#accountNo').addClass('is-invalid');
			$('#accountNoMsg').addClass('invalid-feedback');
			if(accountNoValidation.validationResult[0].assert.name=='required'){
				$('#accountNoMsg').html('<h6>Please enter Payee Account#/Card#/Cheque#!</h6>');
			}else{
				$('#accountNoMsg').html('<h6>The Payee Account#/Card#/Cheque# cannot be more than 50 characters!</h6>');
			}
			$('#accountNoMsg').show();
		}
	} 
	
	if (tenderName=='Cheque'){
		//Bank Name Validation
		var bankValidation = $('#bankName').parsley({
			maxlength : 100,
			required: ''
		});
		if (bankValidation.isValid()) {
			$('#bankName').removeClass('is-invalid');
			$('#bankMsg').hide();
		} else {
			validationPassed = false;
			$('#bankName').addClass('is-invalid');
			$('#bankMsg').addClass('invalid-feedback');
			if(bankValidation.validationResult[0].assert.name=='required'){
				$('#bankMsg').html('<h6>Please enter Bank Name for Cheque!</h6>');
			}else{
				$('#bankMsg').html('<h6>Bank Name cannot be more than 100 characters!</h6>');
			}
			$('#bankMsg').show();
		}		
		
		//Bank Branch Validation
		var branchValidation = $('#bankBranch').parsley({
			maxlength : 100,
			required: ''
		});
		if (branchValidation.isValid()) {
			$('#bankBranch').removeClass('is-invalid');
			$('#branchMsg').hide();
		} else {
			validationPassed = false;
			$('#bankBranch').addClass('is-invalid');
			$('#branchMsg').addClass('invalid-feedback');
			if(branchValidation.validationResult[0].assert.name=='required'){
				$('#branchMsg').html('<h6>Please enter Bank Branch for Cheque!</h6>');
			}else{
				$('#branchMsg').html('<h6>Bank Branch cannot be more than 100 characters!</h6>');
			}
			$('#branchMsg').show();
		}	
	}
	
	
	return validationPassed;
}

function deleteTender(deleteTenderIndex) {
	payment.deletePaymentTender(deleteTenderIndex);
	calculateTotal();
}


function validatePaymentDetails(){
	
	var validationPassed = true;
	var remarksValidation = $('#remarks').parsley({
		maxlength : 150
	});
	if (remarksValidation.isValid()) {
		$('#remarks').removeClass('is-invalid');
		$('#remarksMsg').hide();
	} else {
		validationPassed = false;
		$('#remarks').addClass('is-invalid');
		$('#remarksMsg').addClass('invalid-feedback');
		$('#remarksMsg').html('<h6>The remarks cannot be more than 150 characters!</h6>');
		$('#remarksMsg').show();
	}
	

	var entityType = $('#entityType').val();
	
	if(entityType && entityType!=''){
		$('#entityType').removeClass('is-invalid');
		$('#entityTypeMsg').hide();
	}else{
		validationPassed = false;
		$('#entityType').addClass('is-invalid');
		$('#entityTypeMsg').addClass('invalid-feedback');
		$('#entityTypeMsg').html('<h6>Please select a entity Type for payment!</h6>');
		$('#entityTypeMsg').show();		
	}
	
	var paymentAmount = +$('#paymentAmt').val();
	if(paymentAmount && paymentAmount>0){
		$('#tenderAreaCard').removeClass('is-invalid');
		$('#tenderSelectionMsg').hide();
	}else{
		validationPassed = false;
		$('#tenderAreaCard').addClass('is-invalid');
		$('#tenderSelectionMsg').addClass('invalid-feedback');
		$('#tenderSelectionMsg').html('<h6>Please add atleast a single tender!</h6>');
		$('#tenderSelectionMsg').show();		
	}	
	
	var journalType = $('#journalType').val();
	if(journalType=='full'){
		var paymentAmt=+$('#paymentAmt').val();
		var dueAmt=+$('#dueAmt').val();
		if(paymentAmt!=dueAmt){
			validationPassed = false;
			$('#paymentAmtMsg').addClass('invalid-feedback');
			$('#paymentAmtMsg').html('<h6>The Payment Amount should be equal to Due Amount for Full Payment, please make the needed adjustments in tenders!</h6>');
			$('#paymentAmtMsg').show();		
		}else{
			$('#paymentAmtMsg').hide();
		}
	} else 	if(journalType=='part'){
		var paymentAmt=+$('#paymentAmt').val();
		var dueAmt=+$('#dueAmt').val();
		if(paymentAmt<=dueAmt){
			$('#paymentAmtMsg').hide();
		}else{
			validationPassed = false;
			$('#paymentAmtMsg').addClass('invalid-feedback');
			$('#paymentAmtMsg').html('<h6>The Payment Amount should be less than Due Amount for Part Payment, please make the needed adjustments in tenders!</h6>');
			$('#paymentAmtMsg').show();		

		}
	}
	
	return validationPassed;	
	
}




function readPaymentDetails() {

	var entityId = $('#entityId').val();
	var entityType = $('#journalType').val();
	var paymentAmt = +$('#paymentAmt').val();
	var remarks = $('#remarks').val();

	payment.setPaymentDetails(entityId, entityType, paymentAmt, remarks, txnStartTime, txnEndTime);

}

function postPaymentSave(data){
	if(data!=undefined && data.journalId!=undefined && data.journalId>0){
		
		txn_paymentId=data.journalId;
		$('#paymentReceiptModal').modal({
			backdrop : 'static',
			keyboard : false
		});	
		$('#screenBusyModal').modal('hide');
	}else{
		alert('There is some error while saving the payment details');
	}

}