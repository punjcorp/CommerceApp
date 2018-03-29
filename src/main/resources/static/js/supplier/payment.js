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
var token = $("meta[name='_csrf']").attr("content");
var txnStartTime;
var txnEndTime;

$(function() {
	txnStartTime = moment().format("DD-MMM-YY hh:mm:ss");
	$('#tenderAmount').val('0.00');
	
	$("#entityType").autocomplete({
		minLength : 3,
		source : function(request, response) {
			
			accountDTO.entityType = $('#entityType').val();
			var formdata = JSON.stringify(accountDTO);

			// AJAX call here and refresh the Expense Screen after the save
			$.ajax({
				url : '/admin/search_supplier',
				type : 'POST',
				cache : false,
				data : formdata,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(suppliers) {
					response($.map(suppliers, function(supplier) {
						var dataVal = "" + supplier.name + "-( Ph- " + supplier.phone1 + " )";
						var descVal = "<small>" + supplier.email + "</small>";
						return {
							label : dataVal,
							name : supplier.name,
							phone : supplier.phone1,
							supplierId : supplier.supplierId,
							email : descVal,
							type: 'SUPPLIER',
							addressType: supplier.primaryAddress.addressType,
							address1: supplier.primaryAddress.address1,
							address2: supplier.primaryAddress.address2,
							city: supplier.primaryAddress.city,
							state: supplier.primaryAddress.state,
							country: supplier.primaryAddress.country,
							pincode: supplier.primaryAddress.pincode
						}
					}));

				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader('X-CSRF-TOKEN', token)
				}
			});

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
			readPaymentDetails();
			payment.savePayment();
		}
	});

});

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
	accountDTO.entityId=ui.item.supplierId;
	
	$('#entityType').val(ui.item.name);
	$('#entityId').val(ui.item.supplierId);	

	this.renderSupplierPrimaryAddress(ui.item);
	
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
			$('#lbl_advanceAmt').text(accountDTO.advanceAmt.toFixed(2));
			
			if(accountDTO.dueAmt> 0 && accountDTO.advanceAmt>0){
				$('#dueSettleDiv').removeClass('d-none');
			}
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader('X-CSRF-TOKEN', token)
		}
	});
	
}



function renderSupplierPrimaryAddress(supplier){
	var supplierDetails='<div class="card border-box-filled">';
	supplierDetails+='<div class="card-body">';
	supplierDetails+='<div class="card-text"><h4>Primary Address</h4</div>';
	supplierDetails+='<div class="card-text my-3"><div class="fa-3x"><span class="fa-layers fa-fw">';
	supplierDetails+='<i class="fas fa-map-marker"></i> <i class="fas fa-circle color-blue" ';
	supplierDetails+=' data-fa-transform="shrink-10 up-2"></i></span></div></i></div>';
	supplierDetails+='<div class="row"><div class="col-2"><i class="fas fa-address-card"></i></div><div class="col text-left"><div class="card-text"><small>'+supplier.address1+'</small></div></div></div>';
	if(supplier.address2 && supplier.address2!='undefined' && supplier.address2!='')
		supplierDetails+='<div class="row"><div class="col-2"></div><div class="col text-left"><div class="card-text"><small>'+supplier.address2+'</small></div></div></div>';
	supplierDetails+='<div class="row"><div class="col-2"></div><div class="col text-left"><div class="card-text"><small>'+supplier.city+', '+supplier.state+'</small></div></div></div>';
	supplierDetails+='<div class="row"><div class="col-2"></div><div class="col text-left"><div class="card-text"><small>'+supplier.country+' - '+supplier.pincode+'</small></div></div></div>';
	supplierDetails+='<div class="row"><div class="col-2"><i class="fas fa-phone" data-fa-transform="flip-h"></i></div><div class="col text-left"><div class="card-text"><small>'+supplier.phone+'</small></div></div></div>';
	supplierDetails+='<div class="row"><div class="col-2"><i class="fas fa-envelope"></i></div><div class="col text-left"><div class="card-text">'+supplier.email+'</div></div></div>';
	supplierDetails+='</div>';
	supplierDetails+='</div>';
	$('#supplierDetails').html(supplierDetails);
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

function postPaymentSave(){
	alert('Process the data after processing for report printing');
}