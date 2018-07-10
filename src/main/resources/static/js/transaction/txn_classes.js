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
		unitPrice,
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
		this.unitPrice = unitPrice;
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
		this.unitPrice;
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
	this.discountPct;
	this.taxLineItems = new Array();
}

$.extend(SaleLineItem.prototype, {
	validateSaleLineItem : function(cntl, itemId) {

		if (cntl.id.indexOf('li_qty') == 0 && txn_type == 'R') {
			var qtyValue = +$('#li_qty' + itemId).val();
			if ((qtyValue === '') || (qtyValue.toFixed(2) >= 0.00)) {
				btnLabels = [ i18next.t('alert_btn_ok'), '' ];
				alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('sale_txn_validate_qty'), btnLabels);
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
				btnLabels = [ i18next.t('alert_btn_ok'), '' ];
				alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('sale_txn_validate_qty'), btnLabels);
				$('#li_qty' + itemId).addClass("is-invalid");
				$('#li_qty' + itemId).val(1);
				$('#li_qty' + itemId).focus();
				return false;
			} else {
				$('#li_qty' + itemId).removeClass("is-invalid");
			}
		}

		if ((cntl.id.indexOf('li_discountAmt') == 0 || cntl.id.indexOf('discountType') == 0) && txn_type == 'R') {
			
			var discountValue = +$('#li_discountAmt' + itemId).val();
			
			var discountType= $('#discountType' + itemId).val();
			var priceValue = +$('#li_uh_priceAmt' + itemId).val();
			
			if(discountType=='percent'){
				
				if(discountValue>100 || discountValue<0){
					btnLabels = [ i18next.t('alert_btn_ok'), '' ];
					alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('sale_txn_validate_range_discount_pct'), btnLabels);
					
					$('#li_discountAmt' + itemId).addClass("is-invalid");
					$('#li_discountAmt' + itemId).focus();
					$('#li_discountAmt' + itemId).val(0.00);
					
				}else{
					$('#li_discountAmt' + itemId).removeClass("is-invalid");
				}
					
					
				
			}else if(discountType=='amount'){
				discountValue= discountValue * -1;
				if ((discountValue === '') || (discountValue < priceValue) || (discountValue > 0)) {
					btnLabels = [ i18next.t('alert_btn_ok'), '' ];
					alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('sale_txn_validate_range_discount'), btnLabels);

					$('#li_discountAmt' + itemId).addClass("is-invalid");
					$('#li_discountAmt' + itemId).focus();
					$('#li_discountAmt' + itemId).val(0.00);
					return false;
				} else {
					$('#li_discountAmt' + itemId).removeClass("is-invalid");
				}
			}
			
			
			
			
		} else if ((cntl.id.indexOf('li_discountAmt') == 0 || cntl.id.indexOf('discountType') == 0) && txn_type != 'R') {
		
			var discountValue = +$('#li_discountAmt' + itemId).val();
			var discountType= $('#discountType' + itemId).val();
			var priceValue = +$('#li_uh_priceAmt' + itemId).val();
			
			if(discountType=='percent'){
				if(discountValue>100 || discountValue<0){
					btnLabels = [ i18next.t('alert_btn_ok'), '' ];
					alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('sale_txn_validate_range_discount_pct'), btnLabels);
					
					$('#li_discountAmt' + itemId).addClass("is-invalid");
					$('#li_discountAmt' + itemId).focus();
					$('#li_discountAmt' + itemId).val(0.00);
					
				}else{
					$('#li_discountAmt' + itemId).removeClass("is-invalid");
				}
					
			}else if(discountType=='amount'){
				if ((discountValue === '') || (discountValue.toFixed(2) < 0.00) || (discountValue > priceValue)) {
					btnLabels = [ i18next.t('alert_btn_ok'), '' ];
					alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('sale_txn_validate_range_discount'), btnLabels);

					$('#li_discountAmt' + itemId).addClass("is-invalid");
					$('#li_discountAmt' + itemId).focus();
					$('#li_discountAmt' + itemId).val(0.00);
					return false;
				} else {
					$('#li_discountAmt' + itemId).removeClass("is-invalid");
				}
			}
			
			
			
		}

		return true;

	},
	obscureSaleLineItem : function(removeItemId) {
		$('#' + removeItemId + 'Container').remove();
	},
	updateModifiedItemValues : function(){
		var itemId=this.itemId;
		
		this.qty=+$('#li_qty' + itemId).val();

		this.unitPrice=+$('#li_uh_unitPriceAmt' + itemId).val();
		this.price=+$('#li_uh_priceAmt' + itemId).val();
		this.suggestedPrice=+$('#li_uh_suggestedPriceAmt' + itemId).val();
		this.maxRetailPrice=+$('#li_uh_maxRetailPriceAmt' + itemId).val();
		
		
		
		var discountType= $('#discountType' + itemId).val();
		var discountAmt= +$('#li_discountAmt' + itemId).val();
		
		if(discountType=='percent'){
			var calculatedDiscountAmt=(this.price * discountAmt)/100;
			if(txn_type == 'R')
				calculatedDiscountAmt=calculatedDiscountAmt * -1;
			this.discount=calculatedDiscountAmt ;
			this.discountPct=discountAmt;

		}else if(discountType=='amount') {
			
			var calculatedDiscountAmt =discountAmt;
			this.discount=calculatedDiscountAmt;
			
		}
		
						
		this.cgstTax=+$('#li_uh_cgstAmt' + itemId).val();
		this.sgstTax=+$('#li_uh_sgstAmt' + itemId).val();
		this.igstTax=+$('#li_uh_igstAmt' + itemId).val();
		this.cgstTaxRate=+$('#li_uh_cgstRate' + itemId).val();
		this.sgstTaxRate=+$('#li_uh_sgstRate' + itemId).val();
		this.igstTaxRate=+$('#li_uh_igstRate' + itemId).val();
		
		if(txn_type == 'R' && this.price>0)
			this.price=this.price * -1;
		
		var totalItemPrice = 0;
		if(txn_type == 'R')
			totalItemPrice = this.price + this.discount + this.sgstTax + this.cgstTax;
		else
			totalItemPrice = this.price - this.discount + this.sgstTax + this.cgstTax;
		
		this.itemTotal=totalItemPrice;
		
	},
	calculateAllAmounts : function(itemId) {
		this.calculateSaleLineItemPrice(itemId);
		this.calculateSaleLineItemDiscount(itemId);
		this.calculateSaleLineItemTax(itemId);
		this.calculateSaleLineItemTotal(itemId);
	},
	calculateSaleLineItemPrice : function(itemId) {
		var qty = $('#li_qty' + itemId).val();
		
		var unitPrice =0;
		
		if(viewType=='COMPACT'){
			unitPrice = $('#li_uh_unitPriceAmt' + itemId).val();
		}else if(viewType=='DETAILED'){
			unitPrice = $('#li_unitPriceAmt' + itemId).val();
		}

		$('#li_uh_unitPriceAmt' + itemId).val(unitPrice);
		
		var itemPrice = unitPrice * qty;
		itemPrice = itemPrice.toFixed(2);
		
		$('#li_priceAmt' + itemId).text(itemPrice);
		$('#li_uh_priceAmt' + itemId).val(itemPrice);
	},
	calculateSaleLineItemDiscount : function(itemId) {
		
		var discountType= $('#discountType' + itemId).val();
		var discountAmt = +$('#li_discountAmt' + itemId).val();
		var itemPrice = +$('#li_uh_priceAmt' + itemId).val();
		
		if(discountType=='percent'){
			
				var calculatedDiscountAmt=(itemPrice * discountAmt)/100;
				if (txn_type == 'R') 
					calculatedDiscountAmt= calculatedDiscountAmt * -1;
				
				calculatedDiscountAmt=calculatedDiscountAmt.toFixed(2);
				
				$('#li_uh_discountAmt' + itemId).val(calculatedDiscountAmt);
				
						
		}else if(discountType=='amount') {
			if (discountAmt < itemPrice && txn_type == 'R') {
				$('#li_discountAmt' + itemId).val(0.00);
				$('#li_uh_discountAmt' + itemId).val(0.00);
				btnLabels = [ i18next.t('alert_btn_ok'), '' ];
				alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('sale_txn_validate_exceed_discount'), btnLabels);
				
			} else if (discountAmt > itemPrice && txn_type != 'R') {
				$('#li_discountAmt' + itemId).val(0.00);
				$('#li_uh_discountAmt' + itemId).val(0.00);
				btnLabels = [ i18next.t('alert_btn_ok'), '' ];
				alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('sale_txn_validate_exceed_discount'), btnLabels);
				
			} else {
				$('#li_discountAmt' + itemId).val(discountAmt.toFixed(2));
				$('#li_uh_discountAmt' + itemId).val(discountAmt.toFixed(2));
			}
		}
		
	},
	calculateSaleLineItemTax : function(itemId) {

		var discountAmt = +$('#li_uh_discountAmt' + itemId).val();
		var itemPrice = +$('#li_uh_priceAmt' + itemId).val();

		var sgstUnitTax = +$('#li_uh_sgstRate' + itemId).val();
		var sgstTaxAmt = 0 ;
		if (txn_type == 'R') 
			sgstTaxAmt = (itemPrice + discountAmt) * sgstUnitTax / 100;
		else
			sgstTaxAmt = (itemPrice - discountAmt) * sgstUnitTax / 100;
		
		sgstTaxAmt = sgstTaxAmt.toFixed(2);
		

		var cgstUnitTax = $('#li_uh_cgstRate' + itemId).val();
		var cgstTaxAmt = 0 ;
		if (txn_type == 'R')
			cgstTaxAmt = (itemPrice + discountAmt) * cgstUnitTax / 100;
		else
			cgstTaxAmt = (itemPrice - discountAmt) * cgstUnitTax / 100;
		
		cgstTaxAmt = cgstTaxAmt.toFixed(2);
		
		if(viewType=='COMPACT'){
			$('#li_uh_sgstAmt' + itemId).val(sgstTaxAmt);
			$('#li_uh_cgstAmt' + itemId).val(cgstTaxAmt);
		}else if(viewType=='DETAILED'){
			$('#li_sgstAmt' + itemId).text(sgstTaxAmt);
			$('#li_uh_sgstAmt' + itemId).val(sgstTaxAmt);
			
			$('#li_cgstAmt' + itemId).text(cgstTaxAmt);
			$('#li_uh_cgstAmt' + itemId).val(cgstTaxAmt);
			
		}
		
		
		
		var totalTaxAmt=(+sgstTaxAmt) + (+cgstTaxAmt);
		totalTaxAmt=totalTaxAmt.toFixed(2);
		$('#li_itemTaxAmt' + itemId).text(totalTaxAmt);
		$('#li_uh_itemTaxAmt' + itemId).val(totalTaxAmt);
		
	},
	calculateSaleLineItemTotal : function(itemId) {
		var qty = +$('#li_qty' + itemId).val();
		this.qty = qty;
		
		var unitPrice = $('#li_uh_unitPriceAmt' + itemId).val();
		this.unitPrice=unitPrice;
		
		var discountType = $('#discountType' + itemId).val();
		if(discountType=='percent'){
			var discountPct = +$('#li_discountAmt' + itemId).val();
			this.discountPct = discountPct;
		}else{
			this.discountPct = undefined;
		}
		
		var discountAmt = +$('#li_uh_discountAmt' + itemId).val();
		this.discount = discountAmt;

		var sgstTaxAmt = +$('#li_uh_sgstAmt' + itemId).val();
		this.sgstTax = sgstTaxAmt;

		var cgstTaxAmt = +$('#li_uh_cgstAmt' + itemId).val();
		this.cgstTax = cgstTaxAmt;

		var itemPrice = +$('#li_uh_priceAmt' + itemId).val();

		this.price = itemPrice;
		
		var totalItemPrice =0;
		if (txn_type == 'R')
			totalItemPrice = itemPrice + discountAmt + sgstTaxAmt + cgstTaxAmt;
		else
			totalItemPrice = itemPrice - discountAmt + sgstTaxAmt + cgstTaxAmt;
		
		totalItemPrice = totalItemPrice.toFixed(2);

		$('#li_itemTotal' + itemId).text(i18next.t('common_currency_sign_inr') + ' ' + totalItemPrice);
		this.itemTotal = totalItemPrice;

	},
	renderSaleLineItem : function(saleLineItem, viewType) {
		


		var outerContainer='<div id="' + saleLineItem.itemId + 'Container">';
		var outerContainerEnds='</div>';
		var firstRowStarts='<div class="row">';
		var firstRowEnds='</div>';
		var secondRowStarts='<div class="row"> <div class="col-4"></div>';
		var secondRowEnds='</div>';
		
		
		
		var saleLineItemHtml = '<div class="col-1 padding-sm">';
		saleLineItemHtml += '<img src="' + saleLineItem.itemImage + '" class="img-fluid" alt="Image for item ' + saleLineItem.itemId + '"/>';
		saleLineItemHtml += '</div>';
		saleLineItemHtml += '<div class="col-2 padding-sm"><span>';
		saleLineItemHtml += '<b>' + saleLineItem.itemId + '</b><br>';
		saleLineItemHtml += saleLineItem.itemName;
		saleLineItemHtml += '</span></div>';

		var qty = '<div class="col-1 padding-sm">';
		qty += '<label><small> <span>' + i18next.t('sale_txn_lbl_qty') + '</span></small> </label><br />';
		qty += '<input class="form-control form-control-sm " onChange="saleItemChanged(this);" id="li_qty';
		qty += saleLineItem.itemId + '" type="number" min="0" value="';
		qty += saleLineItem.qty;
		qty += '"></input></div>';

		
		var compactUnitPriceAmt = '<input id="li_uh_unitPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		compactUnitPriceAmt += saleLineItem.unitPrice.toFixed(2);
		compactUnitPriceAmt += '"></input>';
		
		
		
		var unitPriceAmt = '<div class="col padding-sm">';
		unitPriceAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_unit_cost') + '</span></small> </label><br />';
		unitPriceAmt += '<div class="input-group text-left">';
		unitPriceAmt += '<input class="form-control form-control-sm  pos-amount" onChange="saleItemChanged(this);" id="li_unitPriceAmt' + saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		unitPriceAmt += saleLineItem.unitPrice.toFixed(2);
		unitPriceAmt += '"></input></div></div>';
		unitPriceAmt += '<input id="li_uh_unitPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		unitPriceAmt += saleLineItem.unitPrice.toFixed(2);
		unitPriceAmt += '"></input>';

		var priceAmt = '<div class="col padding-sm text-center">';
		priceAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_item_price') + '</span></small> </label><br />';
		priceAmt += '<span>' + i18next.t('common_currency_sign_inr') + ' ' + '</span>';
		priceAmt += '<span id="li_priceAmt' + saleLineItem.itemId + '">';
		priceAmt += saleLineItem.price.toFixed(2);
		priceAmt += '</span></div>';
		priceAmt += '<input id="li_uh_priceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		priceAmt += saleLineItem.price.toFixed(2);
		priceAmt += '"></input>';

		
		var compactSuggestedPriceAmt = '<input id="li_uh_suggestedPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		compactSuggestedPriceAmt += saleLineItem.suggestedPrice.toFixed(2);
		compactSuggestedPriceAmt += '"></input>';
		
		var compactMaxRetailPriceAmt = '<input id="li_uh_maxRetailPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		compactMaxRetailPriceAmt += saleLineItem.maxRetailPrice.toFixed(2);
		compactMaxRetailPriceAmt += '"></input>';
		
		
		
		
		var suggestedPriceAmt = '<div class="col padding-sm">';
		suggestedPriceAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_suggested_price') + '</span></small> </label><br />';
		suggestedPriceAmt += '<div class="input-group text-left">';
		suggestedPriceAmt += '<input class="form-control form-control-sm  pos-amount" id="li_suggestedPriceAmt' + saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		suggestedPriceAmt += saleLineItem.suggestedPrice.toFixed(2);
		suggestedPriceAmt += '" ></input></div></div>';
		suggestedPriceAmt += '<input id="li_uh_suggestedPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		suggestedPriceAmt += saleLineItem.suggestedPrice.toFixed(2);
		suggestedPriceAmt += '"></input>';

		var maxRetailPriceAmt = '<div class="col padding-sm">';
		maxRetailPriceAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_mrp') + '</span></small> </label><br />';
		maxRetailPriceAmt += '<div class="input-group text-left">';
		maxRetailPriceAmt += '<input class="form-control form-control-sm  pos-amount" id="li_maxRetailPriceAmt' + saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		maxRetailPriceAmt += saleLineItem.maxRetailPrice.toFixed(2);
		maxRetailPriceAmt += '"></input></div></div>';
		maxRetailPriceAmt += '<input id="li_uh_maxRetailPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		maxRetailPriceAmt += saleLineItem.maxRetailPrice.toFixed(2);
		maxRetailPriceAmt += '"></input>';

		var discountAmt = '<div class="col padding-sm">';
		discountAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_discount') + '</span></small> </label><br />';
		discountAmt += '<div class="input-group text-left">';
		discountAmt += '<input class="form-control form-control-sm  pos-amount" onChange="saleItemChanged(this);" id="li_discountAmt';
		discountAmt += saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		if(saleLineItem.discountPct!=undefined && saleLineItem.discountPct!='')
			discountAmt += saleLineItem.discountPct.toFixed(2);
		else
			discountAmt += saleLineItem.discount.toFixed(2);
		discountAmt += '"></input>';
		
		discountAmt += '<select class="form-control form-control-sm" onChange="saleItemChanged(this);" id="discountType'+saleLineItem.itemId + '">';
		if((saleLineItem.discountPct!=undefined && saleLineItem.discountPct!='') || saleLineItem.discount==0){
			discountAmt += '<option value="percent" selected>'+i18next.t('sale_txn_lbl_discount_percent')+'</option>';
			discountAmt += '<option value="amount">'+i18next.t('sale_txn_lbl_discount_amount')+'</option>';
		}
		else{
			discountAmt += '<option value="percent">'+i18next.t('sale_txn_lbl_discount_percent')+'</option>';
			discountAmt += '<option value="amount" selected>'+i18next.t('sale_txn_lbl_discount_amount')+'</option>';
		}
			
		
		discountAmt += '</select>';
		
		discountAmt += '</div></div>';
		discountAmt += '<input id="li_uh_discountAmt' + saleLineItem.itemId + '" type="hidden" value="';
		discountAmt += saleLineItem.discount.toFixed(2);
		discountAmt += '"></input>';
		
		
		var compactSgstTaxAmt = '<input id="li_uh_sgstRate' + saleLineItem.itemId + '" type="hidden" value="';
		compactSgstTaxAmt += saleLineItem.sgstTaxRate.toFixed(2);
		compactSgstTaxAmt += '"></input>';
		compactSgstTaxAmt += '<input id="li_uh_sgstAmt' + saleLineItem.itemId + '" type="hidden" value="';
		compactSgstTaxAmt += saleLineItem.sgstTax.toFixed(2);
		compactSgstTaxAmt += '"></input>';
		
		var compactCgstTaxAmt = '<input id="li_uh_cgstRate' + saleLineItem.itemId + '" type="hidden" value="';
		compactCgstTaxAmt += saleLineItem.cgstTaxRate.toFixed(2);
		compactCgstTaxAmt += '"></input>';
		compactCgstTaxAmt += '<input id="li_uh_cgstAmt' + saleLineItem.itemId + '" type="hidden" value="';
		compactCgstTaxAmt += saleLineItem.cgstTax.toFixed(2);
		compactCgstTaxAmt += '"></input>';
		
		

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
		sgstTaxAmt += '<input id="li_uh_sgstAmt' + saleLineItem.itemId + '" type="hidden" value="';
		sgstTaxAmt += saleLineItem.sgstTax.toFixed(2);
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
		cgstTaxAmt += '"></input>';
		cgstTaxAmt += '<input id="li_uh_cgstAmt' + saleLineItem.itemId + '" type="hidden" value="';
		cgstTaxAmt += saleLineItem.cgstTax.toFixed(2);
		cgstTaxAmt += '"></input>';
		
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
		total += '</div>';

		var finalSaleItemHtml = '';
		
		if(viewType=='COMPACT'){
			
			finalSaleItemHtml += outerContainer +firstRowStarts;
			finalSaleItemHtml += saleLineItemHtml + qty + compactUnitPriceAmt+ compactSuggestedPriceAmt + compactMaxRetailPriceAmt + compactSgstTaxAmt + compactCgstTaxAmt;
			finalSaleItemHtml += priceAmt + discountAmt + itemTaxAmt + total;
			finalSaleItemHtml += firstRowEnds +outerContainerEnds;
			
		}else if (viewType=='DETAILED'){
			
			finalSaleItemHtml += outerContainer +firstRowStarts;
			finalSaleItemHtml += saleLineItemHtml + qty +  unitPriceAmt + suggestedPriceAmt + maxRetailPriceAmt + sgstTaxAmt + cgstTaxAmt;
			finalSaleItemHtml += firstRowEnds +secondRowStarts;
			finalSaleItemHtml += priceAmt + discountAmt + itemTaxAmt + total;
			finalSaleItemHtml += secondRowEnds +outerContainerEnds;
		}
		
		$('#result').append(finalSaleItemHtml);
	},
	renderReturnLineItem : function(saleLineItem, viewType) {

		var outerContainer='<div id="' + saleLineItem.itemId + 'Container">';
		var outerContainerEnds='</div>';
		var firstRowStarts='<div class="row">';
		var firstRowEnds='</div>';
		var secondRowStarts='<div class="row"> <div class="col-4"></div>';
		var secondRowEnds='</div>';
		
		
		
		var saleLineItemHtml = '<div class="col-1 padding-sm">';
		saleLineItemHtml += '<img src="' + saleLineItem.itemImage + '" class="img-fluid" alt="Image for item ' + saleLineItem.itemId + '"/>';
		saleLineItemHtml += '</div>';
		saleLineItemHtml += '<div class="col-2 padding-sm"><span>';
		saleLineItemHtml += '<b>' + saleLineItem.itemId + '</b><br>';
		saleLineItemHtml += saleLineItem.itemName;
		saleLineItemHtml += '</span></div>';

		
		var qty = '<div class="col-1 padding-sm">';
		qty += '<label><small> <span>' + i18next.t('sale_txn_lbl_qty') + '</span></small> </label><br />';
		qty += '<input class="form-control form-control-sm " onChange="saleItemChanged(this);" id="li_qty';
		qty += saleLineItem.itemId + '" type="number" min="0" value="';
		qty += saleLineItem.qty;
		qty += '"></input></div>';
		
		var compactUnitPriceAmt = '<input id="li_uh_unitPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		compactUnitPriceAmt += saleLineItem.unitPrice.toFixed(2);
		compactUnitPriceAmt += '"></input>';
		
		var compactSuggestedPriceAmt = '<input id="li_uh_suggestedPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		compactSuggestedPriceAmt += saleLineItem.suggestedPrice.toFixed(2);
		compactSuggestedPriceAmt += '"></input>';
		
		var compactMaxRetailPriceAmt = '<input id="li_uh_maxRetailPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		compactMaxRetailPriceAmt += saleLineItem.maxRetailPrice.toFixed(2);
		compactMaxRetailPriceAmt += '"></input>';
		
		
		var unitPriceAmt = '<div class="col padding-sm">';
		unitPriceAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_unit_cost') + '</span></small> </label><br />';
		unitPriceAmt += '<div class="input-group text-left">';
		unitPriceAmt += '<input class="form-control form-control-sm  pos-amount" onChange="saleItemChanged(this);" id="li_unitPriceAmt' + saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		unitPriceAmt += saleLineItem.unitPrice.toFixed(2);
		unitPriceAmt += '"></input></div></div>';
		unitPriceAmt += '<input id="li_uh_unitPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		unitPriceAmt += saleLineItem.unitPrice.toFixed(2);
		unitPriceAmt += '"></input>';
		

		var priceAmt = '<div class="col padding-sm text-center">';
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
		suggestedPriceAmt += '<input class="form-control form-control-sm  pos-amount" id="li_suggestedPriceAmt' + saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		suggestedPriceAmt += saleLineItem.suggestedPrice.toFixed(2);
		suggestedPriceAmt += '" ></input></div></div>';
		suggestedPriceAmt += '<input id="li_uh_suggestedPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		suggestedPriceAmt += saleLineItem.suggestedPrice.toFixed(2);
		suggestedPriceAmt += '"></input>';

		var maxRetailPriceAmt = '<div class="col padding-sm">';
		maxRetailPriceAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_mrp') + '</span></small> </label><br />';
		maxRetailPriceAmt += '<div class="input-group text-left">';
		maxRetailPriceAmt += '<input class="form-control form-control-sm  pos-amount" id="li_maxRetailPriceAmt' + saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		maxRetailPriceAmt += saleLineItem.maxRetailPrice.toFixed(2);
		maxRetailPriceAmt += '"></input></div></div>';
		maxRetailPriceAmt += '<input id="li_uh_maxRetailPriceAmt' + saleLineItem.itemId + '" type="hidden" value="';
		maxRetailPriceAmt += saleLineItem.maxRetailPrice.toFixed(2);
		maxRetailPriceAmt += '"></input>';
		
				
		
		var discountAmt = '<div class="col padding-sm">';
		discountAmt += '<label><small> <span>' + i18next.t('sale_txn_lbl_discount') + '</span></small> </label><br />';
		discountAmt += '<div class="input-group text-left">';
		discountAmt += '<input class="form-control form-control-sm  pos-amount" onChange="saleItemChanged(this);" id="li_discountAmt';
		discountAmt += saleLineItem.itemId + '" type="number" min="0" step="0.01" value="';
		if(saleLineItem.discountPct!=undefined && saleLineItem.discountPct!='')
			discountAmt += saleLineItem.discountPct.toFixed(2);
		else
			discountAmt += saleLineItem.discount.toFixed(2);
		discountAmt += '"></input>';
		
		
		discountAmt += '<select class="form-control form-control-sm" onChange="saleItemChanged(this);" id="discountType'+saleLineItem.itemId + '">';
		if((saleLineItem.discountPct!=undefined && saleLineItem.discountPct!='') || saleLineItem.discount==0){
			discountAmt += '<option value="percent" selected>'+i18next.t('sale_txn_lbl_discount_percent')+'</option>';
			discountAmt += '<option value="amount">'+i18next.t('sale_txn_lbl_discount_amount')+'</option>';
		}
		else{
			discountAmt += '<option value="percent">'+i18next.t('sale_txn_lbl_discount_percent')+'</option>';
			discountAmt += '<option value="amount" selected>'+i18next.t('sale_txn_lbl_discount_amount')+'</option>';
		}
		discountAmt += '</select>';
		
		discountAmt += '</div></div>';
		discountAmt += '<input id="li_uh_discountAmt' + saleLineItem.itemId + '" type="hidden" value="';
		discountAmt += saleLineItem.discount.toFixed(2);
		discountAmt += '"></input>';
			
		
		
		var compactSgstTaxAmt = '<input id="li_uh_sgstRate' + saleLineItem.itemId + '" type="hidden" value="';
		compactSgstTaxAmt += saleLineItem.sgstTaxRate.toFixed(2);
		compactSgstTaxAmt += '"></input>';
		compactSgstTaxAmt += '<input id="li_uh_sgstAmt' + saleLineItem.itemId + '" type="hidden" value="';
		compactSgstTaxAmt += saleLineItem.sgstTax.toFixed(2);
		compactSgstTaxAmt += '"></input>';
		
		var compactCgstTaxAmt = '<input id="li_uh_cgstRate' + saleLineItem.itemId + '" type="hidden" value="';
		compactCgstTaxAmt += saleLineItem.cgstTaxRate.toFixed(2);
		compactCgstTaxAmt += '"></input>';
		compactCgstTaxAmt += '<input id="li_uh_cgstAmt' + saleLineItem.itemId + '" type="hidden" value="';
		compactCgstTaxAmt += saleLineItem.cgstTax.toFixed(2);
		compactCgstTaxAmt += '"></input>';
		
		
		
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
		sgstTaxAmt += '<input id="li_uh_sgstAmt' + saleLineItem.itemId + '" type="hidden" value="';
		sgstTaxAmt += saleLineItem.sgstTax.toFixed(2);
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
		cgstTaxAmt += '"></input>';	
		cgstTaxAmt += '<input id="li_uh_cgstAmt' + saleLineItem.itemId + '" type="hidden" value="';
		cgstTaxAmt += saleLineItem.cgstTax.toFixed(2);
		cgstTaxAmt += '"></input>';
		
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
		total += '</div>';

		
		
		var finalReturnItemHtml = '';
		
		if(viewType=='COMPACT'){
			
			finalReturnItemHtml += outerContainer +firstRowStarts;
			finalReturnItemHtml += saleLineItemHtml + qty + compactUnitPriceAmt+ compactSuggestedPriceAmt + compactMaxRetailPriceAmt + compactSgstTaxAmt + compactCgstTaxAmt;
			finalReturnItemHtml += priceAmt + discountAmt + itemTaxAmt + total;
			finalReturnItemHtml += firstRowEnds +outerContainerEnds;
			
		}else if (viewType=='DETAILED'){
			
			finalReturnItemHtml += outerContainer +firstRowStarts;
			finalReturnItemHtml += saleLineItemHtml + qty +  unitPriceAmt + suggestedPriceAmt + maxRetailPriceAmt + sgstTaxAmt + cgstTaxAmt;
			finalReturnItemHtml += firstRowEnds +secondRowStarts;
			finalReturnItemHtml += priceAmt + discountAmt + itemTaxAmt + total;
			finalReturnItemHtml += secondRowEnds +outerContainerEnds;
		}		
		
		
		$('#result').append(finalReturnItemHtml);
	},

	parseReturnLineItem : function(data) {
		var returnLineItem = this.parseSaleLineItem(data);

		if (returnLineItem.qty > 0)
			returnLineItem.qty = returnLineItem.qty * -1;

		if (returnLineItem.price > 0)
			returnLineItem.price = returnLineItem.price * -1;
		
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
		var unitPrice = data.priceAmt;
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
		var saleLineItem = new SaleLineItem(itemId, itemName, itemDesc, qty, unitPrice, price, suggestedPrice, maxRetailPrice, discount, cgstTax, sgstTax, igstTax,
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
			btnLabels = [ i18next.t('alert_btn_ok'), '' ];
			alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('sale_txn_validate_amount_tender'), btnLabels);
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
			btnLabels = [ i18next.t('alert_btn_ok'), '' ];
			alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('return_txn_validate_amount_refund'), btnLabels);
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
	this.customer = new Customer();
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





