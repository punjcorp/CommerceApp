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

$(function() {
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
	});

	$('#btnAddTender').click(function() {
		addTender();
	});

	$('#btnSaveExpense').click(function() {

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

	expense.setExpenseDetails(txn_locationId, txn_registerId, txn_businessDate, txn_user, expenseId, expenseType, expenseAmount, remarks);

}

function validateExpenseDetails() {
	var validationPassed = true;
	var remarksValidation = $('#remarks').parsley({
		maxlength : 10
	});
	if (remarksValidation.isValid()) {
		$('#remarks').addClass('is-valid');
		$('#remarksMsg').addClass('valid-feedback');
	} else {
		validationPassed = false;
		$('#remarks').addClass('is-invalid');
		$('#remarksMsg').addClass('invalid-feedback');
		$('#remarksMsg').html('<small>The remarks cannot be more than 150 characters</small>');
		$('#remarksMsg').show();
	}
	
	var remarksValidation = $('#toPayeeName').parsley({
		maxlength : 10
	});
	if (remarksValidation.isValid()) {
		$('#toPayeeName').addClass('is-valid');
		$('#payeeNameMsg').addClass('valid-feedback');
	} else {
		validationPassed = false;
		$('#toPayeeName').addClass('is-invalid');
		$('#payeeNameMsg').addClass('invalid-feedback');
		$('#payeeNameMsg').html('<small>The remarks cannot be more than 150 characters</small>');
		$('#remarksMsg').show();
	}	

	return validationPassed;
}

function addTender() {

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
