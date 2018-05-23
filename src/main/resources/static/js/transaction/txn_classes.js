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
var SaleLineItem = function(
		itemId,
		itemName,
		itemDesc,
		qty,
		price,
		suggestedPrice,
		maxRetailPrice,
		discount,
		cgstTax,
		sgstTax,
		igstTax,
		cgstTaxRate,
		sgstTaxRate,
		igstTaxRate,
		itemTotal,
		itemImage) {
	if (arguments.length > 0) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemDesc = itemDesc;
		this.qty = qty;
		this.price = price;
		this.suggestedPrice = suggestedPrice;
		this.maxRetailPrice = maxRetailPrice;
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
		this.suggestedPrice;
		this.maxRetailPrice;
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

		if (cntl.id.indexOf('li_qty') == 0 && txn_type == 'R') {
			var qtyValue = +$('#li_qty' + itemId).val();
			if ((qtyValue === '') || (qtyValue.toFixed(2) >= 0.00)) {
				alert(i18next.t('sale_txn_validate_qty'));
				$('#li_qty' + itemId).addClass("is-invalid");
				$('#li_qty' + itemId).val(-1);
				$('#li_qty' + itemId).focus();
				return false;
			} else {
				$('#li_qty' + itemId).removeClass("is-invalid");
			}
		} else if (cntl.id.indexOf('li_qty') == 0 && txn_type != 'R') {
			var qtyValue = +$('#li_qty' + itemId).val();
			if ((qtyValue === '') || (qtyValue.toFixed(2) <= 0.00)) {
				alert(i18next.t('sale_txn_validate_qty'));
				$('#li_qty' + itemId).addClass("is-invalid");
				$('#li_qty' + itemId).val(1);
				$('#li_qty' + itemId).focus();
				return false;
			} else {
				$('#li_qty' + itemId).removeClass("is-invalid");
			}
		}

		if (cntl.id.indexOf('li_discountAmt') == 0 && txn_type == 'R') {
			var discountValue = +$('#li_discountAmt' + itemId).val();
			var priceValue = +$('#li_priceAmt' + itemId).text();
			priceValue = priceValue * -1;
			if ((discountValue === '') || (discountValue < priceValue) || (discountValue > 0)) {
				alert(i18next.t('sale_txn_validate_range_discount'));

				$('#li_discountAmt' + itemId).addClass("is-invalid");
				$('#li_discountAmt' + itemId).focus();
				$('#li_discountAmt' + itemId).val(0.00);
				return false;
			} else {
				$('#li_discountAmt' + itemId).removeClass("is-invalid");
			}
		} else if (cntl.id.indexOf('li_discountAmt') == 0 && txn_type != 'R') {
			var discountValue = +$('#li_discountAmt' + itemId).val();
			var priceValue = +$('#li_priceAmt' + itemId).text();
			if ((discountValue === '') || (discountValue.toFixed(2) < 0.00) || (discountValue > priceValue)) {
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
		var unitPrice = $('#li_unitPriceAmt' + itemId).val();
		$('#li_uh_unitPriceAmt' + itemId).val(unitPrice);
		var itemPrice = unitPrice * qty;
		if (txn_type == 'R')
			itemPrice = itemPrice * -1;
		itemPrice = itemPrice.toFixed(2);
		
		$('#li_priceAmt' + itemId).text(itemPrice);
		$('#li_uh_priceAmt' + itemId).val(itemPrice);
	},
	calculateSaleLineItemDiscount : function(itemId) {
		var discountAmt = +$('#li_discountAmt' + itemId).val();
		var itemPrice = +$('#li_uh_priceAmt' + itemId).val();
		if (txn_type == 'R')
			itemPrice = itemPrice * -1;
		if (discountAmt < itemPrice && txn_type == 'R') {
			$('#li_discountAmt' + itemId).val(0.00);
			alert(i18next.t('sale_txn_validate_exceed_discount'));
		} else if (discountAmt > itemPrice && txn_type != 'R') {
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
		var itemPrice = +$('#li_uh_priceAmt' + itemId).val();

		var sgstTaxAmt = (itemPrice - discountAmt) * sgstUnitTax / 100;
		if (txn_type == 'R')
			sgstTaxAmt = sgstTaxAmt * -1;

		sgstTaxAmt = sgstTaxAmt.toFixed(2);
		$('#li_sgstAmt' + itemId).text(sgstTaxAmt);

		var cgstUnitTax = $('#li_uh_cgstRate' + itemId).val();
		var cgstTaxAmt = (itemPrice - discountAmt) * cgstUnitTax / 100;

		if (txn_type == 'R')
			cgstTaxAmt = cgstTaxAmt * -1;
		cgstTaxAmt = cgstTaxAmt.toFixed(2);
		$('#li_cgstAmt' + itemId).text(cgstTaxAmt);
		
		var totalTaxAmt=(+sgstTaxAmt) + (+cgstTaxAmt);
		totalTaxAmt=totalTaxAmt.toFixed(2);
		$('#li_itemTaxAmt' + itemId).text(totalTaxAmt);
		$('#li_uh_itemTaxAmt' + itemId).val(totalTaxAmt);
		
	},
	calculateSaleLineItemTotal : function(itemId) {
		var discountAmt = +$('#li_discountAmt' + itemId).val();
		this.discount = discountAmt;

		var sgstTaxAmt = +$('#li_sgstAmt' + itemId).text();
		this.sgstTax = sgstTaxAmt;

		var cgstTaxAmt = +$('#li_cgstAmt' + itemId).text();
		this.cgstTax = cgstTaxAmt;

		var itemPrice = +$('#li_uh_priceAmt' + itemId).val();
		this.price = itemPrice;

		if (txn_type == 'R')
			itemPrice = itemPrice * -1;

		var totalItemPrice = itemPrice - discountAmt + sgstTaxAmt + cgstTaxAmt;
		totalItemPrice = totalItemPrice.toFixed(2);

		$('#li_itemTotal' + itemId).text(i18next.t('common_currency_sign_inr') + ' ' + totalItemPrice);
		this.itemTotal = totalItemPrice;

	},
	renderSaleLineItem : function(saleLineItem) {

		var saleLineItemHtml = '<div id="' + saleLineItem.itemId + 'Container"><div class="row"> <div class="col-1 padding-sm">';
		saleLineItemHtml += '<img src="' + saleLineItem.itemImage + '" class="img-fluid" alt="Image for item ' + saleLineItem.itemId + '"/>';
		saleLineItemHtml += '</div>';
		saleLineItemHtml += '<div class="col-2 padding-sm"><span>';
		saleLineItemHtml += '<b>' + saleLineItem.itemId + '</b><br>';
		saleLineItemHtml += saleLineItem.itemName;
		saleLineItemHtml += '</span></div>';

		var qty = '<div class="col-1 padding-sm">';
		qty += '<label><small> <span>' + i18next.t('sale_txn_lbl_qty') + '</span></small> </label><br />';
		qty += '<input class="form-control" onChange="saleItemChanged(this);" id="li_qty';
		qty += saleLineItem.itemId + '" type="number" min="0" value="';
		qty += saleLineItem.qty;
		qty += '"></input></div>';

		var unitPriceAmt = '<div class="col padding-sm">';
		unitPriceAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_unit_cost') + '</span></small> </label><br />';
		unitPriceAmt += '<div class="input-group text-left">';
		unitPriceAmt += '<div class="input-group-prepend">';
		unitPriceAmt += '<span class="input-group-text"><span>' + i18next.t('common_currency_sign_inr') + ' ' + '</span></span>';
		unitPriceAmt += '</div>';
		unitPriceAmt += '<input class="form-control pos-amount" onChange="saleItemChanged(this);" id="li_unitPriceAmt' + saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		unitPriceAmt += saleLineItem.price.toFixed(2);
		unitPriceAmt += '"></input></div></div>';
		unitPriceAmt += '<input id="li_uh_unitPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		unitPriceAmt += saleLineItem.price.toFixed(2);
		unitPriceAmt += '"></input>';

		var priceAmt = '<div class="row"> <div class="col-4"></div> <div class="col padding-sm text-center">';
		priceAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_item_price') + '</span></small> </label><br />';
		priceAmt += '<span>' + i18next.t('common_currency_sign_inr') + ' ' + '</span>';
		priceAmt += '<span id="li_priceAmt' + saleLineItem.itemId + '">';
		priceAmt += saleLineItem.price.toFixed(2);
		priceAmt += '</span></div>';
		priceAmt += '<input id="li_uh_priceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		priceAmt += saleLineItem.price.toFixed(2);
		priceAmt += '"></input>';

		var suggestedPriceAmt = '<div class="col padding-sm">';
		suggestedPriceAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_suggested_price') + '</span></small> </label><br />';
		suggestedPriceAmt += '<div class="input-group text-left">';
		suggestedPriceAmt += '<div class="input-group-prepend">';
		suggestedPriceAmt += '<span class="input-group-text"><span>' + i18next.t('common_currency_sign_inr') + ' ' + '</span></span>';
		suggestedPriceAmt += '</div>';
		suggestedPriceAmt += '<input class="form-control pos-amount" id="li_suggestedPriceAmt' + saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		suggestedPriceAmt += saleLineItem.suggestedPrice.toFixed(2);
		suggestedPriceAmt += '" ></input></div></div>';
		suggestedPriceAmt += '<input id="li_uh_suggestedPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		suggestedPriceAmt += saleLineItem.suggestedPrice.toFixed(2);
		suggestedPriceAmt += '"></input>';

		var maxRetailPriceAmt = '<div class="col padding-sm">';
		maxRetailPriceAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_mrp') + '</span></small> </label><br />';
		maxRetailPriceAmt += '<div class="input-group text-left">';
		maxRetailPriceAmt += '<div class="input-group-prepend">';
		maxRetailPriceAmt += '<span class="input-group-text"><span>' + i18next.t('common_currency_sign_inr') + ' ' + '</span></span>';
		maxRetailPriceAmt += '</div>';
		maxRetailPriceAmt += '<input class="form-control pos-amount" id="li_maxRetailPriceAmt' + saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		maxRetailPriceAmt += saleLineItem.maxRetailPrice.toFixed(2);
		maxRetailPriceAmt += '"></input></div></div>';
		maxRetailPriceAmt += '<input id="li_uh_maxRetailPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		maxRetailPriceAmt += saleLineItem.maxRetailPrice.toFixed(2);
		maxRetailPriceAmt += '"></input>';

		var discountAmt = '<div class="col padding-sm">';
		discountAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_discount') + '</span></small> </label><br />';
		discountAmt += '<div class="input-group text-left">';
		discountAmt += '<div class="input-group-prepend">';
		discountAmt += '<span class="input-group-text"><span>' + i18next.t('common_currency_sign_inr') + ' ' + '</span></span>';
		discountAmt += '</div>';
		discountAmt += '<input class="form-control pos-amount" onChange="saleItemChanged(this);" id="li_discountAmt';
		discountAmt += saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		discountAmt += saleLineItem.discount.toFixed(2);
		discountAmt += '"></input></div></div>';
		discountAmt += '<input id="li_uh_discountAmt' + saleLineItem.itemId + '" type="hidden" value="';
		discountAmt += saleLineItem.discount.toFixed(2);
		discountAmt += '"></input>';

		var sgstTaxAmt = '<div class="col-1 padding-sm text-center">';
		sgstTaxAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_sgst') + '</span></small> </label><br />';
		sgstTaxAmt += '<span>' + i18next.t('common_currency_sign_inr') + ' ' + '</span>';
		sgstTaxAmt += '<span id="li_sgstAmt' + saleLineItem.itemId + '">';
		sgstTaxAmt += saleLineItem.sgstTax.toFixed(2);
		sgstTaxAmt += '</span><br/>';
		sgstTaxAmt += '<label><small><span>(' + saleLineItem.sgstTaxRate.toFixed(2) + '%)</span></small></label></div>';
		sgstTaxAmt += '<input id="li_uh_sgstRate' + saleLineItem.itemId + '" type="hidden" value="';
		sgstTaxAmt += saleLineItem.sgstTaxRate.toFixed(2);
		sgstTaxAmt += '"></input>';

		var cgstTaxAmt = '<div class="col-1 padding-sm text-center">';
		cgstTaxAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_cgst') + '</span></small> </label><br />';
		cgstTaxAmt += '<span>' + i18next.t('common_currency_sign_inr') + ' ' + '</span>';
		cgstTaxAmt += '<span id="li_cgstAmt' + saleLineItem.itemId + '">';
		cgstTaxAmt += saleLineItem.cgstTax.toFixed(2);
		cgstTaxAmt += '</span> <br/>';
		cgstTaxAmt += '<label><small><span>(' + saleLineItem.cgstTaxRate.toFixed(2) + '%)</span></small></label></div>';
		cgstTaxAmt += '<input id="li_uh_cgstRate' + saleLineItem.itemId + '" type="hidden" value="';
		cgstTaxAmt += saleLineItem.cgstTaxRate.toFixed(2);
		cgstTaxAmt += '"></input></div>';

		var totalTaxRateVal = saleLineItem.sgstTaxRate + saleLineItem.cgstTaxRate;
		var totalTaxAmtVal = saleLineItem.sgstTax + saleLineItem.cgstTax;

		var itemTaxAmt = '<div class="col padding-sm text-center">';
		itemTaxAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_tax') + '</span></small> </label><br />';
		itemTaxAmt += '<span>' + i18next.t('common_currency_sign_inr') + ' ' + '</span>';
		itemTaxAmt += '<span id="li_itemTaxAmt' + saleLineItem.itemId + '">';
		itemTaxAmt += totalTaxAmtVal.toFixed(2);
		itemTaxAmt += '</span> <br/>';
		itemTaxAmt += '<label><small><span>(' + totalTaxRateVal.toFixed(2) + '%)</span></small></label></div>';
		itemTaxAmt += '<input id="li_uh_itemTaxRate' + saleLineItem.itemId + '" type="hidden" value="';
		itemTaxAmt += totalTaxRateVal.toFixed(2);
		itemTaxAmt += '"></input>';

		var total = '<div class="col-2 form-group padding-sm text-center">';
		total += '<label><small> <span>' + i18next.t('sale_txn_lbl_item_total') + '</span></small> </label><br />';
		total += '<h5><span id="li_itemTotal' + saleLineItem.itemId + '">';
		total += i18next.t('common_currency_sign_inr') + ' ' + saleLineItem.itemTotal.toFixed(2);
		total += '</span><button type="button" id="btnDeleteSLI"';
		total += 'onClick="deleteSaleItem(' + saleLineItem.itemId;
		total += ')" class="btn btn-danger btn-sm ml-2"><i class="fas fa-times"></i></button> ';
		total += '</h5>';
		total += '</div></div></div>';

		var finalSaleItemHtml = saleLineItemHtml + qty +  unitPriceAmt + suggestedPriceAmt + maxRetailPriceAmt + sgstTaxAmt + cgstTaxAmt;
		finalSaleItemHtml += priceAmt + discountAmt + itemTaxAmt + total;

		$('#result').append(finalSaleItemHtml);
	},
	renderReturnLineItem : function(saleLineItem) {

		var saleLineItemHtml = '<div class="row" id="' + saleLineItem.itemId + 'Container"> <div class="col-1 padding-sm">';
		saleLineItemHtml += '<img src="' + saleLineItem.itemImage + '" class="img-fluid" alt="Image for item ' + saleLineItem.itemId + '"/>';
		saleLineItemHtml += '</div>';
		saleLineItemHtml += '<div class="col-2 padding-sm"><span>';
		saleLineItemHtml += '<b>' + saleLineItem.itemId + '</b><br>';
		saleLineItemHtml += saleLineItem.itemName;
		saleLineItemHtml += '</span></div>';

		var qty = '<div class="col padding-sm"><input class="form-control" onChange="saleItemChanged(this);" id="li_qty';
		qty += saleLineItem.itemId + '" type="number" min="0" value="';
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
		total += i18next.t('common_currency_sign_inr') + ' ' + saleLineItem.itemTotal.toFixed(2);
		total += '</span><button type="button" id="btnDeleteSLI"';
		total += 'onClick="deleteSaleItem(' + saleLineItem.itemId;
		total += ')" class="btn btn-danger btn-sm ml-2"><i class="fas fa-times"></i></button> ';
		total += '</h5>';
		total += '</div></div>';

		var finalReturnItemHtml = saleLineItemHtml + qty + priceAmt + discountAmt + sgstTaxAmt + cgstTaxAmt + total;

		$('#result').append(finalReturnItemHtml);
	},

	parseReturnLineItem : function(data) {
		var returnLineItem = this.parseSaleLineItem(data);

		if (returnLineItem.qty > 0)
			returnLineItem.qty = returnLineItem.qty * -1;

		if (returnLineItem.discount > 0)
			returnLineItem.discount = returnLineItem.discount * -1;

		if (returnLineItem.sgstTax > 0) {
			returnLineItem.sgstTax = returnLineItem.sgstTax * -1;
		}

		if (returnLineItem.cgstTax > 0) {
			returnLineItem.cgstTax = returnLineItem.cgstTax * -1;
		}

		if (returnLineItem.itemTotal > 0) {
			returnLineItem.itemTotal = returnLineItem.itemTotal * -1;
		}

		return returnLineItem;

	},
	parseSaleLineItem : function(data) {
		var itemId = data.itemId;
		var itemName = data.name;
		var itemDesc = data.longDesc;
		var qty = data.qty;
		var price = data.priceAmt;
		var suggestedPrice = data.suggestedPrice;
		var maxRetailPrice = data.maxRetailPrice;
		var discount = data.discountAmt;

		var itemImage = '';
		if (data.imageType && data.imageType.length > 0)
			itemImage = 'data:' + data.imageType + ';base64,' + data.imageData;
		else
			itemImage = '/images/item_image_default_200.png';

		var cgstTax;
		var sgstTax;
		var cgstTaxRate;
		var sgstTaxRate;
		var igstTax;
		var igstTaxRate;

		var igstTaxLineItem;
		var sgstTaxLineItem;
		var cgstTaxLineItem;

		// Retrieve tax and check for tax types
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

		// calculate the total for item after taxes and everything for sale item
		var itemTotal = data.totalAmt;
		var saleLineItem = new SaleLineItem(itemId, itemName, itemDesc, qty, price, suggestedPrice, maxRetailPrice, discount, cgstTax, sgstTax, igstTax,
				cgstTaxRate, sgstTaxRate, igstTaxRate, itemTotal, itemImage);

		/**
		 * Update the tax line items in the sale item
		 */
		if (data.igstTax) {
			saleLineItem.taxLineItems.push(igstTaxLineItem);
		} else {
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
	updateTenderIndex : function(tenderIndex) {
		g_tenderIndex = tenderIndex;
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
		htmlContent += '<h5><span>' + i18next.t('common_currency_sign_inr') + ' ' + this.amount.toFixed(2) + '</span></h5>';
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
	},
	validateReturnTenderLineItem : function() {
		var tenderEnteredAmt = +$('#dueAmt').val();
		var totalDueAmt = +$('#hc_totalDueAmt').val();

		var tliPaidAmt;
		var totalPaidAmount = g_nbr_zero;
		$("[id^=tli_amt_").each(function() {
			tliPaidAmt = +$(this).val();
			totalPaidAmount += tliPaidAmt;
		});

		var remainingAmount = totalDueAmt.toFixed(2) - (totalPaidAmount.toFixed(2));

		if ((!tenderEnteredAmt) || (tenderEnteredAmt == '') || (tenderEnteredAmt >= 0.00) || (tenderEnteredAmt < remainingAmount)) {
			alert(i18next.t('return_txn_validate_amount_refund'));
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

			var saleItemTaxLineItems = this.taxLineItems;
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
	},
	saveReturnTxnDetails : function() {
		this.preSaveActions();

		var token = $("meta[name='_csrf']").attr("content");
		var formdata = JSON.stringify(this);
		// AJAX call here and refresh the sell item page with receipt printing
		$.ajax({
			url : '/pos/save_return_txn',
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




function startNewSaleTxn(){
	var newTxnURL=new_txn_prefix;
	window.location.href = newTxnURL;
}

function startNewReturnTxn(){
	var newTxnURL=new_return_txn_prefix;
	window.location.href = newTxnURL;
}