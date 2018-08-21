/**
 * This file contains all the classes needed for a simple no sale expense transaction
 */

var AccountDTO = function() {
	this.entityId;
	this.entityType;
}

$.extend(AccountDTO.prototype, {

});

var Address = function() {
	this.addressId;
	this.addressType;
	this.primary;
	this.address1;
	this.address2;
	this.city;
	this.state;
	this.country;
	this.pincode;
}

$.extend(Address.prototype, {

});

var Supplier = function() {
	this.supplierId;
	this.name;
	this.phone1;
	this.phone2;
	this.email;
	this.createdBy;
	this.createdDate;

	this.primaryAddress = new Address();

}

$.extend(Supplier.prototype, {

});

var Order = function() {
	this.orderId;
	this.supplierId;
	this.locationId;
	this.comments;
}

$.extend(Order.prototype, {

});

var OrderItem = function() {
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
}

$.extend(OrderItem.prototype, {

});

var OrderReturn = function() {
	this.orderReturnId;
	this.order;
}

$.extend(OrderReturn.prototype, {

});

var OrderReturnItem = function() {
	this.orderReturnItemId;
	this.itemId;
	this.itemName;
	this.itemDesc;
	this.qty;
	this.unitCost;
	this.price;
	this.discount;

	this.taxGroupId;

	this.cgstRateRuleId;
	this.cgstCode;
	this.cgstTaxId;
	this.cgstTax;
	this.sgstTax;
	this.igstTax;

	this.sgstRateRuleId;
	this.sgstCode;
	this.sgstTaxId;
	this.cgstTaxRate;
	this.sgstTaxRate;
	this.igstTaxRate;
	this.taxTotal;
	this.itemTotal;
}

$.extend(OrderReturnItem.prototype, {
	parse : function(returnItem, roiId) {
		if (returnItem.orderReturnItemId == undefined && roiId == undefined )
			this.orderReturnItemId = -1;
		else if (roiId != undefined && roiId!='')
			this.orderReturnItemId = roiId;
		else
			this.orderReturnItemId = returnItem.orderReturnItemId;
		this.itemId = returnItem.itemId;
		this.itemName = returnItem.itemDesc;
		this.itemDesc = returnItem.itemDesc;
		this.qty = returnItem.delieveredQty - returnItem.returnedQty;
		this.unitCost = returnItem.actualUnitCost;
		this.price = returnItem.actualCostAmount;
		this.discount = returnItem.actualDiscountAmount;
		this.cgstTax = returnItem.cgstActualTaxAmount;
		this.sgstTax = returnItem.sgstActualTaxAmount;
		this.igstTax = returnItem.igstActualTaxAmount;
		this.cgstTaxRate = returnItem.cgstRate;
		this.sgstTaxRate = returnItem.sgstRate;
		this.igstTaxRate = returnItem.igstRate;
		this.taxTotal = returnItem.actualTaxAmount;
		this.itemTotal = returnItem.actualTotalCost;

		this.taxGroupId = returnItem.taxGroupId;

		if (returnItem.orderReturnItemId == undefined)
			this.cgstTaxId = -1;
		else
			this.cgstTaxId = returnItem.cgstTaxId;

		this.cgstRateRuleId = returnItem.cgstRateRuleId;
		this.cgstCode = returnItem.cgstCode;

		if (returnItem.orderReturnItemId == undefined)
			this.sgstTaxId = -1;
		else
			this.sgstTaxId = returnItem.sgstTaxId;

		this.sgstRateRuleId = returnItem.sgstRateRuleId;
		this.sgstCode = returnItem.sgstCode;

	},
	parseReturnItem : function(returnItem) {
		if (returnItem.orderReturnItemId == undefined)
			this.orderReturnItemId = -1;
		else
			this.orderReturnItemId = returnItem.orderReturnItemId;
		
		this.itemId = returnItem.itemId;
		this.itemName = returnItem.itemDesc;
		this.itemDesc = returnItem.itemDesc;
		this.qty = returnItem.returnedQty;
		this.unitCost = returnItem.unitCost;
		this.price = returnItem.costAmount;
		this.discount = returnItem.discountAmount;
		this.cgstTax = returnItem.cgstActualTaxAmount;
		this.sgstTax = returnItem.sgstActualTaxAmount;
		this.igstTax = returnItem.igstActualTaxAmount;
		this.cgstTaxRate = returnItem.cgstRate;
		this.sgstTaxRate = returnItem.sgstRate;
		this.igstTaxRate = returnItem.igstRate;
		this.taxTotal = returnItem.taxAmount;
		this.itemTotal = returnItem.totalCost;

		this.taxGroupId = returnItem.taxGroupId;

		if (returnItem.orderReturnItemId == undefined)
			this.cgstTaxId = -1;
		else
			this.cgstTaxId = returnItem.cgstTaxId;
		
		
		this.cgstRateRuleId = returnItem.cgstRateRuleId;
		this.cgstCode = returnItem.cgstCode;
		

		if (returnItem.orderReturnItemId == undefined)
			this.sgstTaxId = -1;
		else
			this.sgstTaxId = returnItem.sgstTaxId;
		
		this.sgstRateRuleId = returnItem.sgstRateRuleId;
		this.sgstCode = returnItem.sgstCode;
		

	},
	render : function(sNo) {
		var finalItemHtml = '';

		var rowStartHtml = '<div class="row" id="' + this.itemId + 'Container">';
		var rowEndHtml = '</div>';

		var itemSNoHtml = '<div class="col col-width-sm">';
		itemSNoHtml += '<div class="form-group"><div class="input-group">';
		itemSNoHtml += '<span>' + (sNo + 1) + '</span>';
		itemSNoHtml += '</div></div>';
		itemSNoHtml += '</div>';

		var itemDescHtml = '<div class="col-2 padding-sm">';
		itemDescHtml += '<div class="form-group">';
		itemDescHtml += '<span>' + this.itemId + '</span><br/>';
		itemDescHtml += '<span>' + this.itemDesc + '</span>';
		itemDescHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.itemId" ';
		itemDescHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].itemId" ';
		itemDescHtml += 'type="hidden" value="';
		itemDescHtml += this.itemId;
		itemDescHtml += '"></input>';
		itemDescHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.itemDesc" ';
		itemDescHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].itemDesc" ';
		itemDescHtml += 'type="hidden" value="';
		itemDescHtml += this.itemDesc;
		itemDescHtml += '"></input>';
		itemDescHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.orderReturnItemId" ';
		itemDescHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].orderReturnItemId" ';
		itemDescHtml += 'type="hidden" value="';
		itemDescHtml += this.orderReturnItemId;
		itemDescHtml += '"></input>';
		itemDescHtml += '</div>';
		itemDescHtml += '</div>';

		var unitCostHtml = '<div class="col padding-sm">';
		unitCostHtml += '<div class="form-group"><div class="input-group">';
		unitCostHtml += '<span>' + i18next.t('common_currency_sign_inr') + ' </span>';
		unitCostHtml += '<span>' + this.unitCost.toFixed(2) + '</span>';
		unitCostHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.unitCost" ';
		unitCostHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].unitCost" ';
		unitCostHtml += 'type="hidden" value="';
		unitCostHtml += this.unitCost.toFixed(2);
		unitCostHtml += '"></input>';
		unitCostHtml += '</div></div>';
		unitCostHtml += '</div>';

		var globalReason = $('#orderReturn\\.reasonCodeId').val();

		var itemReasonCodeHtml = '<div class="col padding-sm">';
		itemReasonCodeHtml += '<div class="form-group"><div class="input-group">';
		itemReasonCodeHtml += '<select class="form-control form-control-sm " id="orderReturn.orderReturnItems' + sNo + '.reasonCodeId" ';
		itemReasonCodeHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].reasonCodeId">';
		if (globalReason == "")
			itemReasonCodeHtml += '<option class="form-text text-muted" value="" selected>';
		else
			itemReasonCodeHtml += '<option class="form-text text-muted" value="">';
		itemReasonCodeHtml += i18next.t('screeen_lbl_order_return_reason_code_select');
		itemReasonCodeHtml += '</option>';

		$.each(return_reason_codes, function(index, return_reason_code) {
			if (globalReason == return_reason_code.reasonCodeId)
				itemReasonCodeHtml += '<option value="' + return_reason_code.reasonCodeId + '" selected>';
			else
				itemReasonCodeHtml += '<option value="' + return_reason_code.reasonCodeId + '">';

			itemReasonCodeHtml += return_reason_code.name;
			itemReasonCodeHtml += '</option>';
		});

		itemReasonCodeHtml += '</select>';
		itemReasonCodeHtml += '</div></div>';
		itemReasonCodeHtml += '</div>';

		var itemQtyHtml = '<div class="col padding-sm">';
		itemQtyHtml += '<div class="form-group"><div class="input-group">';
		itemQtyHtml += '<input class="form-control form-control-sm " onChange="qtyChanged(' + sNo + ',' + this.itemId + ');"';
		itemQtyHtml += ' id="orderReturn.orderReturnItems' + sNo + '.returnedQty" ';
		itemQtyHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].returnedQty" ';
		itemQtyHtml += 'type="number" min="1" value="';
		itemQtyHtml += this.qty;
		itemQtyHtml += '"></input>';
		itemQtyHtml += '</div></div>';
		itemQtyHtml += '</div>';

		var itemCostHtml = '<div class="col padding-sm">';
		itemCostHtml += '<div class="form-group"><div class="input-group">';
		itemCostHtml += '<span>' + i18next.t('common_currency_sign_inr') + ' </span>';
		itemCostHtml += '<span id="lbl_costAmount' + sNo + '">' + this.price.toFixed(2) + '</span>';
		itemCostHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.costAmount" ';
		itemCostHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].costAmount" ';
		itemCostHtml += 'type="hidden" value="';
		itemCostHtml += this.price.toFixed(2);
		itemCostHtml += '"></input>';
		itemCostHtml += '</div></div>';
		itemCostHtml += '</div>';

		var discountHtml = '<div class="col padding-sm text-center">';
		discountHtml += '<div class="form-group">';
		discountHtml += '<span>' + i18next.t('common_currency_sign_inr') + '</span>';
		discountHtml += '<span id="lbl_discountAmount' + sNo + '">' + ' ' + this.discount.toFixed(2) + '</span>';
		discountHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.actualDiscountAmount" ';
		discountHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].actualDiscountAmount" ';
		discountHtml += 'type="hidden" value="';
		discountHtml += this.discount.toFixed(2);
		discountHtml += '"></input>';
		discountHtml += '</div>';
		discountHtml += '</div>';

		var sgstTaxHtml = '<input id="orderReturn.orderReturnItems' + sNo + '.sgstTaxAmount" ';
		sgstTaxHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].sgstTaxAmount" ';
		sgstTaxHtml += 'type="hidden" value="';
		sgstTaxHtml += this.sgstTax.toFixed(2);
		sgstTaxHtml += '"></input>';
		sgstTaxHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.sgstRate" ';
		sgstTaxHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].sgstRate" ';
		sgstTaxHtml += 'type="hidden" value="';
		sgstTaxHtml += this.sgstTaxRate.toFixed(2);
		sgstTaxHtml += '"></input>';
		sgstTaxHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.sgstRateRuleId" ';
		sgstTaxHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].sgstRateRuleId" ';
		sgstTaxHtml += 'type="hidden" value="';
		sgstTaxHtml += this.sgstRateRuleId;
		sgstTaxHtml += '"></input>';
		sgstTaxHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.sgstCode" ';
		sgstTaxHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].sgstCode" ';
		sgstTaxHtml += 'type="hidden" value="';
		sgstTaxHtml += this.sgstCode;
		sgstTaxHtml += '"></input>';
		sgstTaxHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.taxGroupId" ';
		sgstTaxHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].taxGroupId" ';
		sgstTaxHtml += 'type="hidden" value="';
		sgstTaxHtml += this.taxGroupId;
		sgstTaxHtml += '"></input>';
		sgstTaxHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.sgstTaxId" ';
		sgstTaxHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].sgstTaxId" ';
		sgstTaxHtml += 'type="hidden" value="';
		sgstTaxHtml += this.sgstTaxId;
		sgstTaxHtml += '"></input>';

		var cgstTaxHtml = '<input id="orderReturn.orderReturnItems' + sNo + '.cgstTaxAmount" ';
		cgstTaxHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].cgstTaxAmount" ';
		cgstTaxHtml += 'type="hidden" value="';
		cgstTaxHtml += this.cgstTax.toFixed(2);
		cgstTaxHtml += '"></input>';
		cgstTaxHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.cgstRate" ';
		cgstTaxHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].cgstRate" ';
		cgstTaxHtml += 'type="hidden" value="';
		cgstTaxHtml += this.cgstTaxRate.toFixed(2);
		cgstTaxHtml += '"></input>';
		cgstTaxHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.cgstRateRuleId" ';
		cgstTaxHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].cgstRateRuleId" ';
		cgstTaxHtml += 'type="hidden" value="';
		cgstTaxHtml += this.cgstRateRuleId;
		cgstTaxHtml += '"></input>';
		cgstTaxHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.cgstCode" ';
		cgstTaxHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].cgstCode" ';
		cgstTaxHtml += 'type="hidden" value="';
		cgstTaxHtml += this.cgstCode;
		cgstTaxHtml += '"></input>';
		cgstTaxHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.cgstTaxId" ';
		cgstTaxHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].cgstTaxId" ';
		cgstTaxHtml += 'type="hidden" value="';
		cgstTaxHtml += this.cgstTaxId;
		cgstTaxHtml += '"></input>';

		var totalTaxHtml = '<div class="col-1 padding-sm text-center">';
		totalTaxHtml += '<div class="form-group">';
		totalTaxHtml += '<span>' + i18next.t('common_currency_sign_inr') + ' </span>';
		totalTaxHtml += '<span id="lbl_taxAmount' + sNo + '">' + this.taxTotal.toFixed(2) + '</span>';
		totalTaxHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.taxAmount" ';
		totalTaxHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].taxAmount" ';
		totalTaxHtml += 'type="hidden" value="';
		totalTaxHtml += this.taxTotal.toFixed(2);
		totalTaxHtml += '"></input>';
		totalTaxHtml += '</div>';
		totalTaxHtml += '</div>';

		var totalAmtHtml = '<div class="col-2">';
		totalAmtHtml += '<div class="form-group"><div class="input-group">';
		totalAmtHtml += '<b><span>' + i18next.t('common_currency_sign_inr') + ' </span>';
		totalAmtHtml += '<span id="lbl_totalCost' + sNo + '">' + this.itemTotal.toFixed(2) + '</span></b>';
		totalAmtHtml += '<input id="orderReturn.orderReturnItems' + sNo + '.totalCost" ';
		totalAmtHtml += 'name="orderReturn.orderReturnItems[' + sNo + '].totalCost" ';
		totalAmtHtml += 'type="hidden" value="';
		totalAmtHtml += this.itemTotal.toFixed(2);
		totalAmtHtml += '"></input>';
		totalAmtHtml += '</div></div>';
		totalAmtHtml += '</div>';

		var deleteHtml = '<div class="col-1">';
		deleteHtml += '<div class="form-group"><div class="input-group">';
		deleteHtml += '<button id="btnDelete' + sNo + '" ';
		deleteHtml += 'type="button" class="btn btn-danger" onClick="deleteReturnItem(';
		deleteHtml += this.itemId;
		deleteHtml += ')"><i class="fas fa-trash-alt fa-2x"></i></button>';
		deleteHtml += '</div></div>';
		deleteHtml += '</div>';

		finalItemHtml = rowStartHtml + itemSNoHtml + itemDescHtml + itemReasonCodeHtml + itemQtyHtml + unitCostHtml + itemCostHtml;
		finalItemHtml += discountHtml + sgstTaxHtml + cgstTaxHtml + totalTaxHtml + totalAmtHtml + deleteHtml + rowEndHtml;

		return finalItemHtml;
	},
	renderHeaders : function() {
		var headerHtml = '<div class="row">';
		headerHtml += '<div class="col col-width-sm"><h6>' + i18next.t('screeen_lbl_common_serial') + '</h6></div>';
		headerHtml += '<div class="col-2 padding-sm"><h6>' + i18next.t('screeen_lbl_order_item_details') + '</h6></div>';
		headerHtml += '<div class="col padding-sm"><h6 class="pos-mandatory">' + i18next.t('screeen_lbl_order_return_reason_code') + '</h6></div>';
		headerHtml += '<div class="col padding-sm"><h6 class="pos-mandatory">' + i18next.t('screeen_lbl_order_quantity') + '</h6></div>';
		headerHtml += '<div class="col padding-sm"><h6>' + i18next.t('screeen_lbl_order_unit_cost') + '</h6></div>';
		headerHtml += '<div class="col padding-sm"><h6>' + i18next.t('screeen_lbl_order_item_cost') + '</h6></div>';
		headerHtml += '<div class="col padding-sm text-center"><h6>' + i18next.t('screeen_lbl_order_discount') + '</h6></div>';
		headerHtml += '<div class="col-1 padding-sm text-center"><h6>' + i18next.t('screeen_lbl_order_item_tax') + '</h6></div>';
		headerHtml += '<div class="col-2"><h6>' + i18next.t('screeen_lbl_order_item_total') + '</h6></div>';
		headerHtml += '<div class="col-1"></div>';
		headerHtml += '</div>';

		return headerHtml;
	}

});
