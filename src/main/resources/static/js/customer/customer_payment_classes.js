/**
 * This file contains all the classes needed for a
 * simple no sale expense transaction
 */


var AccountDTO= function() {
	this.entityId;
	this.entityType;
}

$.extend(AccountDTO.prototype, {

	
});


/**
 * Class definition for Expense Header Details Starts
 */
var PaymentHeader = function() {
	
	this.locationId;
	this.registerId;
	this.businessDate;
	this.username;

	this.journalId;
	this.accountId;
	this.journalType;

	this.amount;

	this.createdBy;
	this.createdDate;
	this.modifiedBy;
	this.modifiedDate;

	
	this.paymentTenderIndex=0;
	this.paymentTenders = new Array();

}

$.extend(PaymentHeader.prototype, {
	addPaymentTender : function(tenderId, tenderName, tenderType, tenderAmount, accountNo, bankName, bankBranch, tenderDetails) {
		if(this.isDuplicateTender(tenderId)){
			alert('The tender is already existing, please delete existing tender to modify the amount!');
		}else{
			var paymentTender=new PaymentTender();

			paymentTender.tenderId = tenderId;
			paymentTender.typeCode = tenderType;
			paymentTender.name = tenderName;
			paymentTender.tenderAmount = tenderAmount;

			paymentTender.bankName= bankName;
			paymentTender.bankBranch= bankBranch;
			paymentTender.accountNo= accountNo;
			paymentTender.description= tenderDetails;
			
			this.paymentTenders.push(paymentTender);
			paymentTender.render(this.paymentTenderIndex);
			
			this.paymentTenderIndex++;
			this.clearTenderControls();
		}

		
	},
	clearTenderControls : function(){
		$('#tenderAmount').val('');
		$('#accountNo').val('');
		$('#bankName').val('');
		$('#bankBranch').val('');
		$('#tenderComment').val('');		
	},
	isDuplicateTender : function(tenderId){
		var alreadyExistingTender = $.grep(this.paymentTenders, function(paymentTender) {
			return paymentTender.tenderId == tenderId;
		})
		if (alreadyExistingTender && alreadyExistingTender.length > 0) {
			return true;
		}
		return false;
	},
	deletePaymentTender : function(paymentTenderIndex){
		// Remove the Expense Tender
		var removeTender = this.paymentTenders[paymentTenderIndex];
		removeTender.clearTenderContainer();
		this.paymentTenders.splice(paymentTenderIndex, 1);
		var newIndex=0;
		$.each(this.paymentTenders,function(index){
			this.render(newIndex);
			newIndex++;
		});
		
		this.paymentTenderIndex=newIndex;
		if(this.paymentTenders && this.paymentTenders.length>0){
			$('#tenderLineItemContainer').removeClass('d-none');
		}else{
			$('#tenderLineItemContainer').addClass('d-none');
		}
		
	},
	totalPaymentTenders : function(){
		var totalAmount=0.00;
		$.each(this.paymentTenders,function(index){
			totalAmount+=this.tenderAmount;
		});
		
		return totalAmount;
	},
	setPaymentDetails: function(entityId, entityType,paymentAmount,remarks,startTime, endTime){
		
		this.journalId;
		this.accountId=entityId;
		this.journalType=entityType;

		this.amount=paymentAmount;

		this.remarks=remarks;		
		
		this.startTime=startTime;
		this.endTime=endTime;
		
		
	},
	savePayment: function(){
		var formdata = JSON.stringify(this);
		// AJAX call here and refresh the Expense Screen after the save
		$.ajax({
			url : '/admin/customer/payment',
			type : 'POST',
			cache : false,
			data : formdata,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				postPaymentSave(data);
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
 * Class definition for Payment Tender Starts
 */
var PaymentTender = function() {

	this.tenderId;
	this.typeCode;
	this.name;
	this.tenderAmount = 0.00;

	this.bankName;
	this.bankBranch;
	this.accountNo;
	this.description;
}

$.extend(PaymentTender.prototype, {
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

/**
 * Class definition for Payment Tender Ends
 */

var Customer = function() {
	this.customerSearchText;
	this.customerId;
	this.name;
	this.phone;
	this.email;
	this.customerType;
}

$.extend(Customer.prototype, {

});
