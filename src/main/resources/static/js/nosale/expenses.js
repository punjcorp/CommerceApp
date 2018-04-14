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
var expense = new ExpenseHeader();
var txnStartTime;
var txnEndTime;

$(function() {
	txnStartTime = moment().format("DD-MMM-YY hh:mm:ss");
	
	$("#expenseType").autocomplete({
		minLength : 3,
		source : function(request, response) {
			$.post({
				url : "/get_reasons",
				data : $("#expenseForm").serialize(),
				success : function(data) {
					response($.map(data, function(reason) {
						var dataVal = "" + reason.name + "-( Type - " + reason.type + " )";
						var descVal = "<small>" + reason.description + "</small>";
						return {
							label : dataVal,
							value : reason.name,
							type : reason.type,
							reasonId : reason.reasonCodeId,
							desc : descVal
						}
					}));
				}
			});
		},

		select : function(event, ui) {
			event.preventDefault();
			if (ui.item) {
				getExpenseDetails(event, ui);
			}
		}
	});

	$('input[name="tenderRadio"]').change(function() {
		displayTenderControls();
		clearTenderErrors();
	});

	$('#btnAddTender').click(function() {
		addTender();
	});

	$('#btnSaveExpense').click(function() {
		txnEndTime = moment().format("DD-MMM-YY hh:mm:ss");
		if (validateExpenseDetails()) {
			readExpenseDetails();
			expense.saveExpense();
		} else {
			alert('the validation is working now');
		}
	});

});

/* This section will allow the item listing to be in a specific format */
$["ui"]["autocomplete"].prototype["_renderItem"] = function(ul, item) {
	return $("<li></li>").data("item.autocomplete", item).html(

	$('<div/>', {
		'class' : 'row'
	}).append($('<div/>', {
		'class' : 'col-12'
	}).append($('<span/>', {
		'html' : '<b>' + item.label + '</b>'
	}))).append($('<div/>', {
		'class' : 'col-12'
	}).append($('<span/>', {
		'html' : item.desc
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
function getExpenseDetails(event, ui) {

	var outputHtml = '<div class="row">';
	outputHtml += '<div class="col-12">';
	outputHtml += '<div class="form-group">';
	outputHtml += '<label><small><span> Selected Expense Type</span></small></label>';
	outputHtml += '<div class="input-group text-left">';
	outputHtml += '<h3><span>' + ui.item.value + '</span></h3>';
	outputHtml += '<input type="hidden" id="h_expenseType" value="' + ui.item.type + '"></input>';
	outputHtml += '</div>';
	outputHtml += '<div class="input-group text-left">';
	outputHtml += '<span>' + ui.item.desc + '</span>';
	outputHtml += '</div>';	
	outputHtml += '</div>';
	outputHtml += '</div>';
	outputHtml += '</div>';

	$('#expenseDetails').html(outputHtml);
	$('#expenseId').val(ui.item.reasonId);

	$('#expenseType').val('');
}

function displayTenderControls() {
	var tenderRadio = $('input[name="tenderRadio"]');
	var tenderId = tenderRadio.filter(':checked').val();
	var tenderType = $('#' + tenderId + 'tenderType').val();

	if (tenderType && tenderType == 'CASH') {
		$('#payeeAccountDiv').addClass('d-none');
		$('#payeePhoneDiv').addClass('d-none');
		$('#payeeBankDiv').addClass('d-none');
		$('#payeeBranchDiv').addClass('d-none');
	}

	if (tenderType && tenderType == 'CC') {
		$('#payeeAccountDiv').removeClass('d-none');
		$('#payeePhoneDiv').removeClass('d-none');
		$('#payeeBankDiv').addClass('d-none');
		$('#payeeBranchDiv').addClass('d-none');
	}
	if (tenderType && tenderType == 'PAYPAL') {
		$('#payeeAccountDiv').removeClass('d-none');
		$('#payeePhoneDiv').removeClass('d-none');
		$('#payeeBankDiv').addClass('d-none');
		$('#payeeBranchDiv').addClass('d-none');
	}
	if (tenderType && tenderType == 'PAYTM') {
		$('#payeeAccountDiv').removeClass('d-none');
		$('#payeePhoneDiv').removeClass('d-none');
		$('#payeeBankDiv').addClass('d-none');
		$('#payeeBranchDiv').addClass('d-none');
	}
	if (tenderType && tenderType == 'CHEQUE') {
		$('#payeeAccountDiv').removeClass('d-none');
		$('#payeePhoneDiv').removeClass('d-none');
		$('#payeeBankDiv').removeClass('d-none');
		$('#payeeBranchDiv').removeClass('d-none');
	}

}

function readExpenseDetails() {

	var expenseId = $('#expenseId').val();
	var expenseType = $('#h_expenseType').val();
	var expenseAmount = +$('#expenseAmount').val();
	var remarks = $('#remarks').val();

	expense.setExpenseDetails(txn_locationId, txn_registerId, txn_businessDate, txn_user, expenseId, expenseType, expenseAmount, remarks, txnStartTime, txnEndTime);

}

function validateExpenseDetails() {
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
	
	var payeeNameValidation = $('#toPayeeName').parsley({
		maxlength : 100,
		required: ''
	});
	if (payeeNameValidation.isValid()) {
		$('#toPayeeName').removeClass('is-invalid');
		$('#payeeNameMsg').hide();
	} else {
		validationPassed = false;
		$('#toPayeeName').addClass('is-invalid');
		$('#payeeNameMsg').addClass('invalid-feedback');
		if(payeeNameValidation.validationResult[0].assert.name=='required'){
			$('#payeeNameMsg').html('<h6>Please enter the Payee Name!</h6>');
		}else{
			$('#payeeNameMsg').html('<h6>Payee Name cannot be more than 100 characters!</h6>');
		}
		$('#payeeNameMsg').show();
	}	

	var expenseType = $('#h_expenseType').val();
	
	if(expenseType && expenseType!=''){
		$('#expenseType').removeClass('is-invalid');
		$('#expenseTypeMsg').hide();
	}else{
		validationPassed = false;
		$('#expenseType').addClass('is-invalid');
		$('#expenseTypeMsg').addClass('invalid-feedback');
		$('#expenseTypeMsg').html('<h6>Please select a Expense Type!</h6>');
		$('#expenseTypeMsg').show();		
	}
	
	var expenseAmount = +$('#expenseAmount').val();
	if(expenseAmount && expenseAmount>0){
		$('#tenderAreaCard').removeClass('is-invalid');
		$('#tenderSelectionMsg').hide();
	}else{
		validationPassed = false;
		$('#tenderAreaCard').addClass('is-invalid');
		$('#tenderSelectionMsg').addClass('invalid-feedback');
		$('#tenderSelectionMsg').html('<h6>Please add atleast a single tender!</h6>');
		$('#tenderSelectionMsg').show();		
	}	
	
	
	return validationPassed;
}

function addTender() {
	if(validateTender()){
		var tenderRadio = $('input[name="tenderRadio"]');
		var tenderId = tenderRadio.filter(':checked').val();
		var tenderName = $('#' + tenderId + 'tenderName').val();
		var tenderType = $('#' + tenderId + 'tenderType').val();
		var tenderAmount = +$('#tenderAmount').val();
		var accountNo = $('#toAccountNo').val();
		var payeeName = $('#toPayeeName').val();
		var payeePhone = $('#toPayeePhone').val();
		var bankName = $('#toBankName').val();
		var bankBranch = $('#toBankBranch').val();
		var tenderDetails = $('#tenderDetails').val();

		expense.addExpenseTender(tenderId, tenderName, tenderType, tenderAmount, accountNo, payeeName, payeePhone, bankName, bankBranch, tenderDetails);
		calculateTotal();		
		//Hide any validation error
		$('#tenderAreaCard').removeClass('is-invalid');
		$('#tenderSelectionMsg').hide();
	}

}

function clearTenderErrors() {
	$('#tenderAmount').val('0.00');
	$('#tenderAmount').removeClass('is-invalid');
	$('#tenderAmountMsg').hide();
	
	$('#tenderDetails').val('');
	$('#tenderDetails').removeClass('is-invalid');
	$('#tenderDetailsMsg').hide();
	
	$('#toAccountNo').val('');
	$('#toAccountNo').removeClass('is-invalid');
	$('#accountNoMsg').hide();
	
	$('#toPayeePhone').val('');
	$('#toPayeePhone').removeClass('is-invalid');
	$('#phoneMsg').hide();
	
	$('#toBankName').val('');
	$('#toBankName').removeClass('is-invalid');
	$('#bankMsg').hide();
	
	$('#toBankBranch').val('');
	$('#toBankBranch').removeClass('is-invalid');
	$('#branchMsg').hide();
	
}

function validateTender() {
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

	var tenderDtlsValidation = $('#tenderDetails').parsley({
		maxlength : 100
	});
	if (tenderDtlsValidation.isValid()) {
		$('#tenderDetails').removeClass('is-invalid');
		$('#tenderDetailsMsg').hide();
	} else {
		validationPassed = false;
		$('#tenderDetails').addClass('is-invalid');
		$('#tenderDetailsMsg').addClass('invalid-feedback');
		$('#tenderDetailsMsg').html('<h6>The Additional Details cannot be more than 100 characters!</h6>');
		$('#tenderDetailsMsg').show();
	}
	
	
	if (tenderName!='Cash' ){
		//Account Number Validation
		var accountNoValidation = $('#toAccountNo').parsley({
			maxlength : 50,
			required: ''
		});
		if (accountNoValidation.isValid()) {
			$('#toAccountNo').removeClass('is-invalid');
			$('#accountNoMsg').hide();
		} else {
			validationPassed = false;
			$('#toAccountNo').addClass('is-invalid');
			$('#accountNoMsg').addClass('invalid-feedback');
			if(accountNoValidation.validationResult[0].assert.name=='required'){
				$('#accountNoMsg').html('<h6>Please enter Payee Account#/Card#/Cheque#!</h6>');
			}else{
				$('#accountNoMsg').html('<h6>The Payee Account#/Card#/Cheque# cannot be more than 50 characters!</h6>');
			}
			$('#accountNoMsg').show();
		}
		
		//Payee Phone Validation
		var phoneValidation = $('#toPayeePhone').parsley({
			minlength : 7,
			maxlength : 10,
			required: ''
		});
		if (phoneValidation.isValid()) {
			$('#toPayeePhone').removeClass('is-invalid');
			$('#phoneMsg').hide();
		} else {
			validationPassed = false;
			$('#toPayeePhone').addClass('is-invalid');
			$('#phoneMsg').addClass('invalid-feedback');
			if(phoneValidation.validationResult[0].assert.name=='required'){
				$('#phoneMsg').html('<h6>Please enter Payee Phone!</h6>');
			}else if(phoneValidation.validationResult[0].assert.name=='maxlength'){
				$('#phoneMsg').html('<h6>The Payee Phone cannot be more than 10 digits!</h6>');
			}else{
				$('#phoneMsg').html('<h6>The Payee Phone should be more than or equal to 7 digits!</h6>');
			}
			$('#phoneMsg').show();
		}		
		
	} 
	
	if (tenderName=='Cheque'){
		//Bank Name Validation
		var bankValidation = $('#toBankName').parsley({
			maxlength : 100,
			required: ''
		});
		if (bankValidation.isValid()) {
			$('#toBankName').removeClass('is-invalid');
			$('#bankMsg').hide();
		} else {
			validationPassed = false;
			$('#toBankName').addClass('is-invalid');
			$('#bankMsg').addClass('invalid-feedback');
			if(bankValidation.validationResult[0].assert.name=='required'){
				$('#bankMsg').html('<h6>Please enter Bank Name for Cheque!</h6>');
			}else{
				$('#bankMsg').html('<h6>Bank Name cannot be more than 100 characters!</h6>');
			}
			$('#bankMsg').show();
		}		
		
		//Bank Branch Validation
		var branchValidation = $('#toBankBranch').parsley({
			maxlength : 100,
			required: ''
		});
		if (branchValidation.isValid()) {
			$('#toBankBranch').removeClass('is-invalid');
			$('#branchMsg').hide();
		} else {
			validationPassed = false;
			$('#toBankBranch').addClass('is-invalid');
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
	expense.deleteExpenseTender(deleteTenderIndex);
	calculateTotal();
}

function calculateTotal() {
	var totalAmount = expense.totalExpenseTenders();
	$('#totalAmount').text(totalAmount.toFixed(2));
	$('#expenseAmount').val(totalAmount.toFixed(2));
	$('#tenderAmount').val('0.00');
}

function postExpenseSave(data) {
	alert('We are validating your expense data to confirm the successful save');
}
