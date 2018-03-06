/**
 * This file contains all the details about Tender Line Item
 * 
 */
var g_nbr_zero = 0.00;
var g_tenderIndex = 0;

/**
 * Class definition for Tender Line Item Starts
 */
var TenderLineItem = function(tenderId, tenderIndex, name, amount) {
	this.tenderId = tenderId;
	this.tenderIndex = tenderIndex;
	this.name = name;
	this.amount = amount;
}

$.extend(TenderLineItem.prototype, {
	removeTenderItem : function(removeLineItem) {
		tenderLineItems = $.grep(tenderLineItems, function(tenderLineItem) {
			return tenderLineItem.tenderIndex != removeLineItem.tenderIndex;
		});
	},

	addTenderItem : function(newLineItem) {
		tenderLineItems.push(newLineItem);
	},

	render : function() {
		if (g_tenderIndex > 0) {
			$('#tenderLineItemContainer').removeClass('d-none');
		}
		var htmlContent = '<div class="row" id="' + g_tenderIndex + 'tenderLineItem">';
		htmlContent += '<div class="col-6">';

		var iconImg = '';
		if (this.name == 'Cash') {
			iconImg += '<i class="far fa-money-bill-alt fa-2x"></i>';
		} else if (this.name == 'Credit Card') {
			iconImg += '<i class="fas fa-credit-card fa-2x"></i>';
		} else if (this.name == 'Paypal') {
			iconImg += '<i class="fab fa-paypal fa-2x"></i>';
		}
		htmlContent += '<h5><span>' + iconImg + '  ' + this.name + '</span></h5>';

		htmlContent += '</div><div class="col-4">';
		htmlContent += '<input id="tli_amt_' + g_tenderIndex + '" type="hidden" value="' + this.amount.toFixed(2) + '"></input>';
		htmlContent += '<h5><span>INR ' + this.amount.toFixed(2) + '</span></h5>';
		htmlContent += '</div><div class="col-2">';
		htmlContent += '<button type="button" id="btnDeleteTLI"';
		htmlContent += 'onClick="deleteTender(' + g_tenderIndex;
		htmlContent += ')" class="btn btn-danger btn-sm"><i class="far fa-trash-alt fa-2x"></i></button>';
		htmlContent += '</div></div>';

		$('#tenderLineItemContainer').append(htmlContent);

	},

	initialize : function(tenderEnteredAmt, tenderName) {
		this.tenderIndex = g_tenderIndex;
		g_tenderIndex++;
		this.name = tenderName;
		this.amount = tenderEnteredAmt;
	},

	obscureTenderLineItem : function(deleteIndex) {
		$('#' + deleteIndex + 'tenderLineItem').remove();
	},
});
/**
 * Class definition for Tender Line Item Ends
 */

var tenderLineItems = [];
var remainingAmount = 0.00;
var totalPaidAmount = 0.00;
var changeDueAmount = 0.00;
var amountAfterPayment = 0.00;

/**
 * Class definition for TxnAction Starts
 */
var TxnAction = function() {
};

$.extend(TxnAction.prototype, {
	removeTenderItem : function(removeIndex, totalDueAmt) {
		var removeLineItem = tenderLineItems[removeIndex - 1];
		removeLineItem.obscureTenderLineItem(removeIndex);
		this.calculateDue(g_nbr_zero, totalDueAmt, 'Cash');
	},
	addTenderItem : function(tenderEnteredAmt, tenderName) {
		var tenderLineItem = new TenderLineItem();
		tenderLineItem.initialize(tenderEnteredAmt, tenderName);
		tenderLineItems.push(tenderLineItem);
		return tenderLineItem;
	},
	calculateBiggerAmt : function(tenderEnteredAmt, totalDueAmt, tenderName) {
		$('#btnTenderOK').addClass('d-none');
		$('#btnCompleteTxn').removeClass('d-none');
		if (tenderEnteredAmt.toFixed(2) != g_nbr_zero.toFixed(2)) {
			this.showTender(tenderEnteredAmt, tenderName);
		}
	},
	calculateEqualAmt : function(tenderEnteredAmt, tenderName) {

		$('#btnTenderOK').addClass('d-none');
		$('#btnCompleteTxn').removeClass('d-none');

		if (tenderEnteredAmt.toFixed(2) != g_nbr_zero.toFixed(2)) {
			this.showTender(tenderEnteredAmt, tenderName);
		}
		this.calculateChangeDueAmount(g_nbr_zero);
		$('#dueAmt').val(g_nbr_zero.toFixed(2));

	},
	calculateLessAmt : function(tenderEnteredAmt, totalDueAmt, tenderName) {
		var amountAfterPayment = remainingAmount.toFixed(2) - tenderEnteredAmt.toFixed(2);

		$('#dueAmt').val(amountAfterPayment.toFixed(2));

		if (tenderEnteredAmt.toFixed(2) != g_nbr_zero.toFixed(2)) {
			this.showTender(tenderEnteredAmt, tenderName);
		}
		this.resetChangeDueAmount();

		$('#btnTenderOK').removeClass('d-none');
		$('#btnCompleteTxn').addClass('d-none');
	},
	showTender : function(amount, tenderName) {

		var tenderLineItem = this.addTenderItem(amount, tenderName);
		tenderLineItem.render();

	},
	calculateChangeDueAmount : function(changeDueAmount) {

		if ((!changeDueAmount) || (changeDueAmount < 0)) {
			$('#dueAmt').val(g_nbr_zero.toFixed(2));

			$('#tenderChangeDueCol').removeClass('d-none');
			$('#hc_changeDueAmt').val(changeDueAmount.toFixed(2));
			$('#tenderChangeDueAmt').text("INR " + changeDueAmount.toFixed(2));

		}

	},
	resetChangeDueAmount : function() {
		$('#tenderChangeDueCol').addClass('d-none');
		$('#hc_changeDueAmt').val(g_nbr_zero.toFixed(2));
		$('#tenderChangeDueAmt').text("INR " + g_nbr_zero.toFixed(2));

	},
	calculateDue : function(tenderEnteredAmt, totalDueAmt, tenderName) {

		var tliPaidAmt;
		totalPaidAmount = g_nbr_zero;
		$("[id^=tli_amt_").each(function() {
			tliPaidAmt = +$(this).val();
			totalPaidAmount += tliPaidAmt;
		});

		remainingAmount = totalDueAmt.toFixed(2) - (totalPaidAmount.toFixed(2));

		if ((remainingAmount.toFixed(2) == totalDueAmt.toFixed(2)) && (tenderEnteredAmt.toFixed(2) == g_nbr_zero.toFixed(2))) {
			$('#tenderLineItemContainer').addClass("d-none");
		}

		if (parseFloat(tenderEnteredAmt.toFixed(2)) > parseFloat(remainingAmount.toFixed(2))) {
			var changeDueAmount = remainingAmount - tenderEnteredAmt;
			this.calculateChangeDueAmount(changeDueAmount);
			this.calculateBiggerAmt(tenderEnteredAmt, totalDueAmt, tenderName);
		} else if (parseFloat(tenderEnteredAmt.toFixed(2)) < parseFloat(remainingAmount.toFixed(2))) {

			this.calculateLessAmt(tenderEnteredAmt, totalDueAmt, tenderName);

		} else if (parseFloat(tenderEnteredAmt.toFixed(2)) == parseFloat(remainingAmount.toFixed(2))) {

			this.calculateEqualAmt(tenderEnteredAmt, tenderName);

		}
	}
});
/**
 * Class definition for Tender Line Item Ends
 */
