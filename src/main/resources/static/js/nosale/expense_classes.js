/**
 * This file contains all the classes needed for a
 * simple no sale expense transaction
 */

/**
 * Class definition for Transaction Id Starts
 */

/**
 * Class definition for Expense Header Details Starts
 */
var ExpenseHeader = function() {
	this.txnNo;
	this.locationId;
	this.registerId;
	this.businessDate;
	this.username;
	this.createdBy;
	this.uniqueTxnNo;

	this.startTime;
	this.endTime;

	this.expenseAmount = 0.00;
	this.expenseId;
	this.expenseType;
	this.remarks;
	
	this.expenseTenderIndex=0;
	this.expenseTenders = new Array();

}

$.extend(ExpenseHeader.prototype, {
	addExpenseTender : function(tenderId, tenderName, tenderType, tenderAmount, accountNo, payeeName, payeePhone, bankName, bankBranch, tenderDetails) {
		if(this.isDuplicateTender(tenderId)){
			alert('The tender is already existing, please delete existing tender to modify the amount!');
		}else{
			var expenseTender=new ExpenseTender();

			expenseTender.tenderId = tenderId;
			expenseTender.typeCode = tenderType;
			expenseTender.name = tenderName;
			expenseTender.tenderAmount = tenderAmount;

			expenseTender.payeeName = payeeName;
			expenseTender.payeePhone= payeePhone;
			expenseTender.bankName= bankName;
			expenseTender.bankBranch= bankBranch;
			expenseTender.accountNo= accountNo;
			expenseTender.tenderDetails= tenderDetails;
			
			this.expenseTenders.push(expenseTender);
			expenseTender.render(this.expenseTenderIndex);
			
			this.expenseTenderIndex++;
			this.clearTenderControls();
		}

		
	},
	clearTenderControls : function(){
		$('#expenseAmount').val('');
		$('#toAccountNo').val('');
		$('#toBankName').val('');
		$('#toBankBranch').val('');
		$('#tenderDetails').val('');		
	},
	isDuplicateTender : function(tenderId){
		var alreadyExistingTender = $.grep(this.expenseTenders, function(expenseTender) {
			return expenseTender.tenderId == tenderId;
		})
		if (alreadyExistingTender && alreadyExistingTender.length > 0) {
			return true;
		}
		return false;
	},	
	deleteExpenseTender : function(expenseTenderIndex){
		// Remove the Expense Tender
		var removeTender = this.expenseTenders[expenseTenderIndex];
		removeTender.clearTenderContainer();
		this.expenseTenders.splice(expenseTenderIndex, 1);
		var newIndex=0;
		$.each(this.expenseTenders,function(index){
			this.render(newIndex);
			newIndex++;
		});
		
		this.expenseTenderIndex=newIndex;
		if(this.expenseTenders && this.expenseTenders.length>0){
			$('#tenderLineItemContainer').removeClass('d-none');
		}else{
			$('#tenderLineItemContainer').addClass('d-none');
		}
		
	},
	totalExpenseTenders : function(){
		var totalAmount=0.00;
		$.each(this.expenseTenders,function(index){
			totalAmount+=this.tenderAmount;
		});
		
		return totalAmount;
	},
	setExpenseDetails: function(locationId, registerId,businessDate,username,expenseId, expenseType,expenseAmount,remarks,startTime, endTime){
		
		this.locationId=locationId;
		this.registerId=registerId;
		this.businessDate=businessDate;
		this.username=username;
		this.createdBy=username;

		this.startTime=startTime;
		this.endTime=endTime;
		this.expenseId=expenseId;
		this.expenseAmount = expenseAmount;
		this.expenseType=expenseType;
		this.remarks=remarks;		
		
	},	
	expenseValidated: function(){
		return true;
	},
	saveExpense: function(){
		var token = $("meta[name='_csrf']").attr("content");
		var formdata = JSON.stringify(this);
		// AJAX call here and refresh the Expense Screen after the save
		$.ajax({
			url : '/pos/expenses',
			type : 'POST',
			cache : false,
			data : formdata,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				postExpenseSave(data);
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader('X-CSRF-TOKEN', token)
			}
		});		
	}
	
});
/**
 * Class definition for Expense Header Details Ends
 */

/**
 * Class definition for Tender Line Item Starts
 */
var ExpenseTender = function() {

	this.tenderId;
	this.typeCode;
	this.name;
	this.tenderAmount = 0.00;

	this.payeeName;
	this.payeePhone;
	this.bankName;
	this.bankBranch;
	this.accountNo;
	this.tenderDetails;
}

$.extend(ExpenseTender.prototype, {
	clearTenderContainer : function() {
		$('#tenderLineItemContainer').html('');
	},	
	render : function(expenseTenderIndex) {
		if (expenseTenderIndex >= 0) {
			$('#tenderLineItemContainer').removeClass('d-none');
		}
		var htmlContent = '<div class="row" id="' + expenseTenderIndex + 'tenderLineItem">';
		htmlContent += '<div class="col-6">';

		var iconImg = '';
		if (this.name == 'Cash') {
			iconImg += '<i class="far fa-money-bill-alt fa-2x"></i>';
		} else if (this.name == 'Credit Card') {
			iconImg += '<i class="fas fa-credit-card fa-2x"></i>';
		} else if (this.name == 'Paypal') {
			iconImg += '<i class="fab fa-paypal fa-2x"></i>';
		} else if (this.name == 'Paytm') {
			iconImg += '<img class="img-fluid" src="' + logo_paytm + '"></img>';
		} else if (this.name == 'Cheque') {
			iconImg += '<i class="fas fa-id-card fa-2x"></i>';
		}
		htmlContent += '<h5><span>' + iconImg + '  ' + this.name + '</span></h5>';

		htmlContent += '</div><div class="col-4">';
		htmlContent += '<h5><span>INR ' + this.tenderAmount.toFixed(2) + '</span></h5>';
		htmlContent += '</div><div class="col-2">';
		htmlContent += '<button type="button" id="btnDeleteTLI"';
		htmlContent += 'onClick="deleteTender(' + expenseTenderIndex;
		htmlContent += ')" class="btn btn-danger btn-sm"><i class="far fa-trash-alt fa-2x"></i></button>';
		htmlContent += '</div></div>';

		$('#tenderLineItemContainer').append(htmlContent);

	}
});
