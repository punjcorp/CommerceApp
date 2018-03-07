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

/**
 * Class definition for Tender Line Item Starts
 */
var SaleLineItem = function(itemId, itemName, itemDesc, qty, price, discount, cgstTax, sgstTax, igstTax, cgstTaxRate, sgstTaxRate, igstTaxRate, itemTotal) {
	this.itemId = itemId;
	this.itemName = itemName;
	this.itemDesc = itemDesc;
	this.qty = qty;
	this.price = price;
	this.discount = discount;
	this.cgstTax = cgstTax;
	this.sgstTax = sgstTax;
	this.igstTax = igstTax;
	this.cgstTaxRate = cgstTaxRate;
	this.sgstTaxRate = sgstTaxRate;
	this.igstTaxRate = igstTaxRate;
	this.itemTotal = itemTotal;
}

$.extend(TxnAction.prototype, {
	renderSaleLineItem : function(saleLineItem) {

		var saleLineItemHtml = '<div class="row"> <div class="col-3"><span>';
		saleLineItemHtml += '<b>' + saleLineItem.itemId + '-' + saleLineItem.itemName + '</b>';
		saleLineItemHtml += '<br>' + saleLineItem.itemDesc;
		saleLineItemHtml += '</span></div>';

		var qty = '<div class="col padding-sm"><input class="form-control" onChange="saleItemChanged(this);" id="li_qty';
		qty += saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		qty += saleLineItem.qty;
		qty += '"></input></div>';

		var priceAmt = '<div class="col padding-sm"><input class="form-control" id="li_priceAmt' + saleLineItem.itemId
				+ '" type="number" min="0" step="0.01" value="';
		priceAmt += saleLineItem.price.toFixed(2);
		priceAmt += '" disabled></input></div>';
		priceAmt += '<input id="li_uh_priceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		priceAmt += saleLineItem.price.toFixed(2);
		priceAmt += '"></input>';

		var discountAmt = '<div class="col padding-sm"><input class="form-control" onChange="saleItemChanged(this);" id="li_discountAmt';
		discountAmt += saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		discountAmt += saleLineItem.discount.toFixed(2);
		discountAmt += '"></input></div>';
		discountAmt += '<input id="li_uh_discountAmt' + saleLineItem.itemId + '" type="hidden" value="';
		discountAmt += saleLineItem.discount.toFixed(2);
		discountAmt += '"></input>';

		var sgstTaxAmt = '<div class="col-1 padding-sm">';
		sgstTaxAmt += '<input class="form-control" id="li_sgstAmt' + saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		sgstTaxAmt += saleLineItem.sgstTax.toFixed(2);
		sgstTaxAmt += '" disabled></input>';
		sgstTaxAmt += '<label><small><span>(' + saleLineItem.sgstTaxRate.toFixed(2) + '%)</span></small></label></div>';
		sgstTaxAmt += '<input id="li_uh_sgstRate' + saleLineItem.itemId + '" type="hidden" value="';
		sgstTaxAmt += saleLineItem.sgstTaxRate.toFixed(2);
		sgstTaxAmt += '"></input>';

		var cgstTaxAmt = '<div class="col-1 padding-sm">';
		cgstTaxAmt += '<input class="form-control" id="li_cgstAmt' + saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		cgstTaxAmt += saleLineItem.cgstTax.toFixed(2);
		cgstTaxAmt += '" disabled></input>';
		cgstTaxAmt += '<label><small><span>(' + saleLineItem.cgstTaxRate.toFixed(2) + '%)</span></small></label></div>';
		cgstTaxAmt += '<input id="li_uh_cgstRate' + saleLineItem.itemId + '" type="hidden" value="';
		cgstTaxAmt += saleLineItem.cgstTaxRate.toFixed(2);
		cgstTaxAmt += '"></input>';

		var total = '<div class="col-2 padding-sm"><h5><span id="li_itemTotal' + saleLineItem.itemId + '">';
		total += 'INR ' + saleLineItem.itemTotal.toFixed(2);
		total += '</span></h5></div></div>';

		var finalSaleItemHtml = saleLineItemHtml + qty + priceAmt + discountAmt + sgstTaxAmt + cgstTaxAmt + total;

		$('#result').append(finalSaleItemHtml);
	},
	parseSaleLineItem : function(data) {

	}
});
