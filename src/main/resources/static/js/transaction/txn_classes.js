/**
 * This file contains all the classes needed for a
 * simple Sales transaction
 */

/**
 * Class definition for Transaction Id Starts
 */
var TransactionId = function() {
	this.txnNo;
	this.locationId;
	this.registerId;
	this.businessDate;
	this.username;
}

$.extend(TransactionId.prototype, {

});
/**
 * Class definition for Transaction Id Ends
 */

/**
 * Class definition for Transaction Header Details Starts
 */
var TransactionHeader = function() {
	this.txnNo;
	this.locationId;
	this.registerId;
	this.businessDate;
	this.username;
	this.createdBy;
	this.uniqueTxnNo;

	this.startTime;
	this.endTime;
	
	this.subTotalAmt = 0.00;
	this.totalDiscountAmt = 0.00;
	this.totalSGSTTaxAmt = 0.00;
	this.totalCGSTTaxAmt = 0.00;
	this.totalIGSTTaxAmt = 0.00;
	this.totalTaxAmt = 0.00;
	this.totalDueAmt = 0.00;

}

$.extend(TransactionHeader.prototype, {

});
/**
 * Class definition for Transaction Header Details Ends
 */

/**
 * Class definition for Sale Line Item Starts
 */
var SaleLineItem = function(itemId, itemName, itemDesc, qty, price, discount, cgstTax, sgstTax, igstTax, cgstTaxRate, sgstTaxRate, igstTaxRate, itemTotal,itemImage) {
	if (arguments.length > 0) {
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
		this.itemImage = itemImage;
	} else {
		this.itemId;
		this.itemName;
		this.itemDesc;
		this.qty;
		this.price;
		this.discount;
		this.cgstTax;
		this.sgstTax;
		this.igstTax;
		this.cgstTaxRate;
		this.sgstTaxRate;
		this.igstTaxRate;
		this.itemTotal;
		this.itemImage;
	}
	this.seqNo;
	this.taxLineItems = new Array();
}

$.extend(SaleLineItem.prototype, {
	validateSaleLineItem : function(cntl, itemId) {

		if (cntl.id.indexOf('li_qty') == 0) {
			var qtyValue = +$('#li_qty' + itemId).val();
			if ((!qtyValue) || (qtyValue.toFixed(2) <= 0.00)) {
				alert(i18next.t('sale_txn_validate_qty'));
				$('#li_qty' + itemId).addClass("is-invalid");
				$('#li_qty' + itemId).val(1);
				$('#li_qty' + itemId).focus();
				return false;
			} else {
				$('#li_qty' + itemId).removeClass("is-invalid");
			}
		}
		if (cntl.id.indexOf('li_discountAmt') == 0) {
			var discountValue = +$('#li_discountAmt' + itemId).val();
			var priceValue = +$('#li_priceAmt' + itemId).val();
			if ((!discountValue) || (discountValue.toFixed(2) < 0.00) || (discountValue > priceValue)) {
				alert(i18next.t('sale_txn_validate_range_discount'));

				$('#li_discountAmt' + itemId).addClass("is-invalid");
				$('#li_discountAmt' + itemId).focus();
				$('#li_discountAmt' + itemId).val(0.00);
				return false;
			} else {
				$('#li_discountAmt' + itemId).removeClass("is-invalid");
			}
		}

		return true;

	},
	obscureSaleLineItem : function(removeItemId) {
		$('#' + removeItemId + 'Container').remove();
	},
	calculateAllAmounts : function(itemId) {
		this.calculateSaleLineItemPrice(itemId);
		this.calculateSaleLineItemDiscount(itemId);
		this.calculateSaleLineItemTax(itemId);
		this.calculateSaleLineItemTotal(itemId);
	},
	calculateSaleLineItemPrice : function(itemId) {
		var qty = $('#li_qty' + itemId).val();
		var unitPrice = $('#li_uh_priceAmt' + itemId).val();
		var itemPrice = unitPrice * qty;
		itemPrice = itemPrice.toFixed(2);
		$('#li_priceAmt' + itemId).val(itemPrice);
	},
	calculateSaleLineItemDiscount : function(itemId) {
		var discountAmt = +$('#li_discountAmt' + itemId).val();
		var itemPrice = +$('#li_priceAmt' + itemId).val();
		if (discountAmt > itemPrice) {
			$('#li_discountAmt' + itemId).val(0.00);
			alert(i18next.t('sale_txn_validate_exceed_discount'));
		} else {
			$('#li_discountAmt' + itemId).val(discountAmt.toFixed(2));
		}

	},
	calculateSaleLineItemTax : function(itemId) {
		var qty = +$('#li_qty' + itemId).val();
		this.qty = qty;
		
		var discountAmt = +$('#li_discountAmt' + itemId).val();

		var sgstUnitTax = +$('#li_uh_sgstRate' + itemId).val();
		var itemPrice = +$('#li_priceAmt' + itemId).val();

		var sgstTaxAmt = (itemPrice - discountAmt) * sgstUnitTax / 100;
		sgstTaxAmt = sgstTaxAmt.toFixed(2);
		$('#li_sgstAmt' + itemId).val(sgstTaxAmt);

		var cgstUnitTax = $('#li_uh_cgstRate' + itemId).val();
		var cgstTaxAmt = (itemPrice - discountAmt) * cgstUnitTax / 100;
		cgstTaxAmt = cgstTaxAmt.toFixed(2);
		$('#li_cgstAmt' + itemId).val(cgstTaxAmt);
	},
	calculateSaleLineItemTotal : function(itemId) {
		var discountAmt = +$('#li_discountAmt' + itemId).val();
		this.discount =discountAmt;
		
		var sgstTaxAmt = +$('#li_sgstAmt' + itemId).val();
		this.sgstTax =sgstTaxAmt;
			
		var cgstTaxAmt = +$('#li_cgstAmt' + itemId).val();
		this.cgstTax = cgstTaxAmt;
		
		var itemPrice = +$('#li_priceAmt' + itemId).val();
		this.price =+$('#li_uh_priceAmt' + itemId).val();;
		
		var totalItemPrice = itemPrice - discountAmt + sgstTaxAmt + cgstTaxAmt;
		totalItemPrice = totalItemPrice.toFixed(2);
		
		$('#li_itemTotal' + itemId).text(i18next.t('common_currency_sign_inr')+' ' + totalItemPrice);
		this.itemTotal =totalItemPrice;
		
	},
	renderSaleLineItem : function(saleLineItem) {

		var saleLineItemHtml = '<div class="row" id="' + saleLineItem.itemId + 'Container"> <div class="col-1 padding-sm">';
		saleLineItemHtml += '<img src="'+saleLineItem.itemImage+'" class="img-fluid" alt="Image for item '+saleLineItem.itemId+'"/>';
		saleLineItemHtml += '</div>';
		saleLineItemHtml += '<div class="col-2 padding-sm"><span>';
		saleLineItemHtml += '<b>' + saleLineItem.itemId + '</b><br>';
		saleLineItemHtml += saleLineItem.itemName;
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

		var total = '<div class="col-2 form-group padding-sm"><h5><span id="li_itemTotal' + saleLineItem.itemId + '">';
		total += i18next.t('common_currency_sign_inr')+' ' + saleLineItem.itemTotal.toFixed(2);
		total += '</span><button type="button" id="btnDeleteSLI"';
		total += 'onClick="deleteSaleItem(' + saleLineItem.itemId;
		total += ')" class="btn btn-danger btn-sm ml-2"><i class="fas fa-times"></i></button> ';
		total += '</h5>';
		total += '</div></div>';
		

		var finalSaleItemHtml = saleLineItemHtml + qty + priceAmt + discountAmt + sgstTaxAmt + cgstTaxAmt + total;

		$('#result').append(finalSaleItemHtml);
	},
	parseSaleLineItem : function(data) {
		var itemId = data.itemId;
		var itemName = data.name;
		var itemDesc = data.longDesc;
		var qty = data.qty;
		var price = data.priceAmt;
		var discount = data.discountAmt;
		var itemImage = 'data:'+data.imageType+';base64,'+data.imageData;

		var cgstTax;
		var sgstTax;
		var cgstTaxRate;
		var sgstTaxRate;
		var igstTax;
		var igstTaxRate;

		var igstTaxLineItem;
		var sgstTaxLineItem;
		var cgstTaxLineItem;
		
		//Retrieve tax and check for tax types
		if (data.igstTax) {
			igstTax = data.igstTax.amount;
			igstTaxRate = data.igstTax.percentage;

			igstTaxLineItem = new TaxLineItem(data.itemId, data.igstTax.taxGroupId, data.igstTax.taxRuleRateId, igstTax, igstTaxRate);
		} else {
			cgstTax = data.cgstTax.amount;
			cgstTaxRate = data.cgstTax.percentage;
			sgstTax = data.sgstTax.amount;
			sgstTaxRate = data.sgstTax.percentage;

			cgstTaxLineItem = new TaxLineItem(data.itemId, data.cgstTax.taxGroupId, data.cgstTax.taxRuleRateId, cgstTax, cgstTaxRate);
			sgstTaxLineItem = new TaxLineItem(data.itemId, data.sgstTax.taxGroupId, data.sgstTax.taxRuleRateId, sgstTax, sgstTaxRate);
		}

		//calculate the total for item after taxes and everything for sale item
		var itemTotal = data.totalAmt;
		var saleLineItem = new SaleLineItem(itemId, itemName, itemDesc, qty, price, discount, cgstTax, sgstTax, igstTax, cgstTaxRate, sgstTaxRate, igstTaxRate,
				itemTotal,itemImage);
		
		/**
		 * Update the tax line items in the sale item
		 */
		if (data.igstTax) {
			saleLineItem.taxLineItems.push(igstTaxLineItem);
		}else{
			saleLineItem.taxLineItems.push(sgstTaxLineItem);
			saleLineItem.taxLineItems.push(cgstTaxLineItem);
		}
		

		return saleLineItem;
	}
});
/**
 * Class definition for Sale Line Item Ends
 */

/**
 * Class definition for Tax Line Item Starts
 */
var TaxLineItem = function(itemId, taxGroupId, taxRuleRateId, totalTaxAmt, taxRuleRate) {
	this.txnId = new TransactionId();

	this.seqNo;
	this.itemId = itemId;
	this.taxGroupId = taxGroupId;
	this.taxRuleRateId = taxRuleRateId;
	this.totalTaxAmt = totalTaxAmt;
	this.totalTaxableAmt = 0.00;
	this.totalTaxExemptAmt = 0.00;
	this.taxOverrideAmt = 0.00;
	this.taxOverrideRate = 0.00;
	this.taxOverrideFlag = false;
	this.taxOverrideReason;
	this.voidFlag;
	this.taxRuleRate = taxRuleRate;
	this.taxRuleAmt = totalTaxAmt;
	this.orgTaxableAmt = 0.00;
	this.orgTaxGroupId;
	this.createdDate;

}

$.extend(TaxLineItem.prototype, {

});
/**
 * Class definition for Tax Line Item Ends
 */

/**
 * These global variables are used in tender line item class
 * 
 */
var g_tenderIndex = 0;

/**
 * Class definition for Tender Line Item Starts
 */
var TenderLineItem = function(tenderId, tenderIndex, code, name, amount) {

	this.tenderId = tenderId;
	this.tenderIndex = tenderIndex;
	this.typeCode = code;
	this.name = name;
	this.amount = amount;
	this.seqNo;
	this.changeFlag = false;

}

$.extend(TenderLineItem.prototype, {
	clearTenderContainer : function() {
		$('#tenderLineItemContainer').html('');
	},
	updateTenderIndex: function(tenderIndex) {
			g_tenderIndex=tenderIndex;
	},	
	render : function() {
		if (g_tenderIndex >= 0) {
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
		htmlContent += '<h5><span>'+i18next.t('common_currency_sign_inr')+' ' + this.amount.toFixed(2) + '</span></h5>';
		htmlContent += '</div><div class="col-2">';
		htmlContent += '<button type="button" id="btnDeleteTLI"';
		htmlContent += 'onClick="deleteTender(' + g_tenderIndex;
		htmlContent += ')" class="btn btn-danger btn-sm"><i class="far fa-trash-alt fa-2x"></i></button>';
		htmlContent += '</div></div>';

		$('#tenderLineItemContainer').append(htmlContent);

	},

	initialize : function(tenderEnteredAmt, tenderId) {
		var tenderName = $('#' + tenderId + 'tenderName').val();
		var tenderType = $('#' + tenderId + 'tenderType').val();
		this.tenderId = tenderId;
		this.name = tenderName;
		this.typeCode = tenderType;
		this.amount = tenderEnteredAmt;
	},

	obscureTenderLineItem : function(deleteIndex) {
		$('#' + deleteIndex + 'tenderLineItem').remove();
	},
	validateTenderLineItem : function() {
		var tenderEnteredAmt = +$('#dueAmt').val();
		if ((!tenderEnteredAmt) || (tenderEnteredAmt == '') || (tenderEnteredAmt <= 0.00)) {
			alert(i18next.t('sale_txn_validate_amount_tender'));
			$('#dueAmt').addClass("is-invalid");
			$('#dueAmt').focus();
			return false;
		} else {

			$('#dueAmt').removeClass("is-invalid");
		}
		return true;
	}
});

/**
 * Class definition for Sale Transaction Starts
 */
var SaleTransaction = function() {
	this.txnSaleLineItems = [];
	this.txnTenderLineItems = [];
	this.transactionHeader = new TransactionHeader();
}

$.extend(SaleTransaction.prototype, {
	addSaleLineItem : function(saleLineItem) {
		this.txnSaleLineItems.push(saleLineItem);
	},
	addTenderLineItem : function(tenderLineItem) {
		this.txnTenderLineItems.push(tenderLineItem);
	},
	addTxnHeader : function(txnHeader) {
		this.transactionHeader = txnHeader;
	},
	addTxnSeqs : function() {
		var seqVal = 1;

		$.each(this.txnSaleLineItems, function() {
			this.seqNo = seqVal;
			seqVal = seqVal + 1;
			
			var saleItemTaxLineItems=this.taxLineItems;
			$.each(saleItemTaxLineItems, function() {
				this.seqNo = seqVal;
				seqVal = seqVal + 1;
			});
			
		});

		
		$.each(this.txnTenderLineItems, function() {
			this.seqNo = seqVal;
			seqVal = seqVal + 1;
		});

	},
	preSaveActions : function() {
		this.addTxnSeqs();
	},
	saveTxnDetails : function() {
		this.preSaveActions();

		var token = $("meta[name='_csrf']").attr("content");
		var formdata = JSON.stringify(this);
		// AJAX call here and refresh the sell item page with receipt printing
		$.ajax({
			url : '/pos/save_txn',
			type : 'POST',
			cache : false,
			data : formdata,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				postTxnSave(data);
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader('X-CSRF-TOKEN', token)
			}
		});
	}
});
/**
 * Class definition for Sale Transaction Ends
 */
