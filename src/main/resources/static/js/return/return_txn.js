/**
 * The global variable needed for transaction operations
 */
var g_nbr_zero = 0.00;
var alertUtil = new Utils();

/**
 * Class definition for TxnAction Starts
 */
var TxnAction = function() {
	this.saleTxn = new SaleTransaction();
	this.txnHeader = new TransactionHeader();
	this.saleLineItem = new SaleLineItem();
	this.tenderLineItem = new TenderLineItem();
	this.customer = new Customer();
	
	this.saleItemList = new Array();
	this.tenderLineItems = new Array();
	this.remainingAmount = 0.00;
};

$.extend(TxnAction.prototype, {
	isDuplicateSaleLineItem : function(itemId) {
		if ((this.saleItemList) && (this.saleItemList.length > 0)) {
			var alreadyExistingItem = $.grep(this.saleItemList, function(item) {
				return item.itemId == itemId;
			})
			if (alreadyExistingItem && alreadyExistingItem.length > 0) {
				return true;
			}
		}
		return false;
	},
	renderAll : function() {

		$('#result').html('');

		$.each(this.saleItemList, function(itrIndex, itrSaleLineItem) {

			itrSaleLineItem.renderReturnLineItem(itrSaleLineItem, viewType);

		});
		this.renderHeaderTotals();

	},
	reCheckGSTCalculation : function(){
		this.calculateGSTFlag();
		$.each(this.saleItemList, function(itrIndex, itrSaleLineItem) {

			if(isGSTFlag=='S'){
				itrSaleLineItem.taxLineItems=itrSaleLineItem.sTaxLineItems;
			}else if(isGSTFlag=='I'){
				itrSaleLineItem.taxLineItems=itrSaleLineItem.iTaxLineItems;
			}

		});
	},
	showSaleLineItem : function(data) {
		var actualSaleItem = this.saleLineItem.parseReturnLineItem(data);
		if (this.isDuplicateSaleLineItem(actualSaleItem.itemId)) {
			btnLabels = [ i18next.t('alert_btn_ok'), '' ];
			alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('sale_txn_validate_item'), btnLabels);
		} else {
			this.saleLineItem.renderReturnLineItem(actualSaleItem, viewType);
			// Check when we need to add this to list it should be after successful render as per my understanding
			this.saleItemList.push(actualSaleItem);
			this.renderHeaderTotals();
			
			if(this.saleItemList.length>0)
				$('#tenderOptionsContainer').removeClass('d-none');
			else
				$('#tenderOptionsContainer').addClass('d-none');
		}
	},
	deleteSaleItemInList : function(removeItemId, totalDueAmt) {
		// Remove the Sale Line Item
		this.saleItemList = $.grep(this.saleItemList, function(saleLineItem) {
			return saleLineItem.itemId != removeItemId;
		});
		this.saleLineItem.obscureSaleLineItem(removeItemId);
		this.removeAllTenderItems(totalDueAmt);
		this.renderHeaderTotals();
		if(this.saleItemList.length>0)
			$('#tenderOptionsContainer').removeClass('d-none');
		else
			$('#tenderOptionsContainer').addClass('d-none');
	},
	reCalculateSaleItemAmounts : function(cntl, itemId) {
		if (this.saleLineItem.validateSaleLineItem(cntl, itemId)) {
			var itemRcd = $.grep(this.saleItemList, function(item) {
				return item.itemId == itemId;
			})
			if (itemRcd && itemRcd.length > 0) {
				var modifiedSaleItem = itemRcd[0];
				modifiedSaleItem.calculateAllAmounts(itemId);
				this.renderHeaderTotals();
				modifiedSaleItem.updateModifiedItemValues();
			}
		}

	},
	associateCustomer : function(customerId, customerType, customerName, customerPhone, customerEmail, customerGstNo) {
		this.customer.customerId = customerId;
		this.customer.name = customerName;
		this.customer.phone = customerPhone;
		this.customer.email = customerEmail;
		this.customer.gstNo = customerGstNo;
		this.customer.customerType = customerType;

		if (customerName != 'undefined' && customerName != '') {
			$('#salesHeaderCustomer').html(customerName+'<br><h6>( '+ customerGstNo+' )</h6>');
			$('#customerHeader').removeClass('d-none');
		}

	},
	renderHeaderTotals : function() {
		var totalAmt = 0.00;
		var totalDiscount = 0.00;
		var totalTax = 0.00;
		var totalSGSTTax = 0.00;
		var totalCGSTTax = 0.00;
		var totalIGSTTax = 0.00;
		var totalPrice = 0.00;

		var totalAmtText;
		$("[id^=li_").each(function() {
			if (this.id.indexOf("li_itemTotal") >= 0) {
				totalAmtText = $(this).text();
				totalAmtText = +totalAmtText.replace(i18next.t('common_currency_sign_inr') + ' ', '');
				totalAmt += totalAmtText;
			}
			if (this.id.indexOf("li_uh_discountAmt") >= 0) {
				totalDiscount += +$(this).val();
			}
			if (this.id.indexOf("li_uh_priceAmt") >= 0) {
				var tmp_totalPrice = +$(this).val();
				totalPrice += tmp_totalPrice;
			}
			if (this.id.indexOf("li_uh_sgstAmt") >= 0) {
				totalSGSTTax += +$(this).val();
			}
			if (this.id.indexOf("li_uh_cgstAmt") >= 0) {
				totalCGSTTax += +$(this).val();
			}
			if (this.id.indexOf("li_uh_igstAmt") >= 0) {
				totalIGSTTax += +$(this).val();
			}
		});

		if(isGSTFlag=='S')
			totalTax = totalSGSTTax + totalCGSTTax;
		else if(isGSTFlag=='I')
			totalTax = totalIGSTTax;

		$('#salesHeaderSubTotalAmt').text(i18next.t('common_currency_sign_inr') + '  ' + totalPrice.toFixed(2));
		$('#salesHeaderDiscountAmt').text(i18next.t('common_currency_sign_inr') + '  ' + totalDiscount.toFixed(2));
		$('#salesHeaderTaxAmt').text(i18next.t('common_currency_sign_inr') + '  ' + totalTax.toFixed(2));
		$('#salesHeaderTotalAmt').text(i18next.t('common_currency_sign_inr') + '  ' + totalAmt.toFixed(2));

		$('#hc_totalSubAmt').val(totalPrice.toFixed(2));
		$('#hc_totalDiscountAmt').val(totalDiscount.toFixed(2));
		$('#hc_totalTaxAmt').val(totalTax.toFixed(2));
		$('#hc_totalDueAmt').val(totalAmt.toFixed(2));

		$('#dueAmt').val(totalAmt.toFixed(2));

		// Setting the sales header object
		this.txnHeader.locationId = txn_locationId;
		this.txnHeader.registerId = txn_registerId;
		this.txnHeader.businessDate = txn_businessDate;
		this.txnHeader.username = txn_user;
		this.txnHeader.createdBy = txn_user;

		this.txnHeader.startTime = txnStartTime;

		this.txnHeader.subTotalAmt = totalPrice;
		this.txnHeader.totalDiscountAmt = totalDiscount;
		this.txnHeader.totalSGSTTaxAmt = totalSGSTTax;
		this.txnHeader.totalCGSTTaxAmt = totalCGSTTax;
		this.txnHeader.totalIGSTTaxAmt = totalIGSTTax;
		this.txnHeader.totalTaxAmt = totalTax;
		this.txnHeader.totalDueAmt = totalAmt;

	},
	showTender : function(amount, tenderId) {
		var tenderLineItem = this.addTenderItem(amount, tenderId);
		var tenderIndex = this.tenderLineItems.length - 1;
		tenderLineItem.updateTenderIndex(tenderIndex);
		tenderLineItem.render();
	},
	addTenderItem : function(tenderEnteredAmt, tenderId) {
		var tenderLineItem = new TenderLineItem();
		tenderLineItem.initialize(tenderEnteredAmt, tenderId);
		this.tenderLineItems.push(tenderLineItem);
		return tenderLineItem;
	},
	removeTenderItem : function(removeIndex, totalDueAmt) {
		var removeLineItem = this.tenderLineItems[removeIndex];
		removeLineItem.clearTenderContainer();
		this.tenderLineItems.splice(removeIndex, 1);
		$.each(this.tenderLineItems, function(index) {
			this.updateTenderIndex(index);
			this.render();
		});
		this.calculateDue(g_nbr_zero, totalDueAmt, 'Cash');

	},
	removeAllTenderItems : function(totalDueAmt) {
		var rcdCount = this.tenderLineItems.length;
		this.tenderLineItem.clearTenderContainer();
		this.tenderLineItems = new Array();
		this.calculateDue(g_nbr_zero, totalDueAmt, 'Cash');
	},
	processTender : function() {
		var tenderRadio = $('input[name="tenderRadio"]');
		var tenderId = tenderRadio.filter(':checked').val();
		if (tenderId) {
			var tenderEnteredAmt = +$('#dueAmt').val();
			var totalDueAmt = +$('#hc_totalDueAmt').val();

			this.calculateDue(tenderEnteredAmt, totalDueAmt, tenderId);
		} else {
			btnLabels = [ i18next.t('alert_btn_ok'), '' ];
			alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('sale_txn_validate_tender'), btnLabels);
		}

	},
	validateCreditTender : function() {

		var tenderRadio = $('input[name="tenderRadio"]');
		var tenderId = tenderRadio.filter(':checked').val();
		var tenderName = $('#' + tenderId + 'tenderName').val();

		if (tenderName != undefined && tenderName == 'Credit') {
			if (this.customer.name == undefined || this.customer.name == '') {
				btnLabels = [ i18next.t('alert_btn_ok'), '' ];
				alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('sale_txn_customer_association_needed'), btnLabels);
				return false;

			} else {
				return true;
			}
		} else {
			return true;
		}

	},
	calculateDue : function(tenderEnteredAmt, totalDueAmt, tenderId) {
		var tliPaidAmt;
		var totalPaidAmount = g_nbr_zero;
		$("[id^=tli_amt_").each(function() {
			tliPaidAmt = +$(this).val();
			totalPaidAmount += tliPaidAmt;
		});
		this.remainingAmount = totalDueAmt.toFixed(2) - (totalPaidAmount.toFixed(2));
		if ((this.remainingAmount.toFixed(2) == totalDueAmt.toFixed(2)) && (tenderEnteredAmt.toFixed(2) == g_nbr_zero.toFixed(2))) {
			$('#tenderLineItemContainer').addClass("d-none");
		}
		if (parseFloat(tenderEnteredAmt.toFixed(2)) < parseFloat(this.remainingAmount.toFixed(2))) {
			var changeDueAmount = this.remainingAmount - tenderEnteredAmt;
			this.calculateChangeDueAmount(changeDueAmount);
			this.calculateBiggerAmt(tenderEnteredAmt, totalDueAmt, tenderId);
		} else if (parseFloat(tenderEnteredAmt.toFixed(2)) > parseFloat(this.remainingAmount.toFixed(2))) {
			this.calculateLessAmt(tenderEnteredAmt, totalDueAmt, tenderId);
		} else if (parseFloat(tenderEnteredAmt.toFixed(2)) == parseFloat(this.remainingAmount.toFixed(2))) {
			this.calculateEqualAmt(tenderEnteredAmt, tenderId);
		}
	},

	calculateBiggerAmt : function(tenderEnteredAmt, totalDueAmt, tenderId) {
		$('#btnTenderOK').addClass('d-none');
		$('#btnCompleteTxn').removeClass('d-none');
		if (tenderEnteredAmt.toFixed(2) != g_nbr_zero.toFixed(2)) {
			this.showTender(tenderEnteredAmt, tenderId);
		}
	},
	calculateEqualAmt : function(tenderEnteredAmt, tenderId) {

		$('#btnTenderOK').addClass('d-none');
		$('#btnCompleteTxn').removeClass('d-none');

		if (tenderEnteredAmt.toFixed(2) != g_nbr_zero.toFixed(2)) {
			this.showTender(tenderEnteredAmt, tenderId);
		}
		this.calculateChangeDueAmount(g_nbr_zero);
		$('#dueAmt').val(g_nbr_zero.toFixed(2));

	},
	calculateLessAmt : function(tenderEnteredAmt, totalDueAmt, tenderId) {
		var amountAfterPayment = this.remainingAmount.toFixed(2) - tenderEnteredAmt.toFixed(2);

		$('#dueAmt').val(amountAfterPayment.toFixed(2));

		if (tenderEnteredAmt.toFixed(2) != g_nbr_zero.toFixed(2)) {
			this.showTender(tenderEnteredAmt, tenderId);
		}
		this.resetChangeDueAmount();

		$('#btnTenderOK').removeClass('d-none');
		$('#btnCompleteTxn').addClass('d-none');
	},

	calculateChangeDueAmount : function(changeDueAmount) {
		if ((!changeDueAmount) || (changeDueAmount < 0)) {
			$('#dueAmt').val(g_nbr_zero.toFixed(2));
			$('#tenderChangeDueCol').removeClass('d-none');
			$('#hc_changeDueAmt').val(changeDueAmount.toFixed(2));
			$('#tenderChangeDueAmt').text(i18next.t('common_currency_sign_inr') + ' ' + changeDueAmount.toFixed(2));
		}
	},

	resetChangeDueAmount : function() {
		$('#tenderChangeDueCol').addClass('d-none');
		$('#hc_changeDueAmt').val(g_nbr_zero.toFixed(2));
		$('#tenderChangeDueAmt').text(i18next.t('common_currency_sign_inr') + ' ' + g_nbr_zero.toFixed(2));
	},
	preTxnCompletion : function() {
		this.txnHeader.endTime = txnEndTime;
		var changeAmount = +$('#hc_changeDueAmt').val();
		if (changeAmount < 0) {
			var tenderCount = this.tenderLineItems.length;
			this.tenderLineItems[tenderCount - 1].changeFlag = true;
			this.addTenderItem(changeAmount, tndr_change_id);
		}
	},
	processCompletedTxn : function() {
		this.preTxnCompletion();
		this.saleTxn.transactionHeader = this.txnHeader;
		this.saleTxn.txnSaleLineItems = this.saleItemList;
		this.saleTxn.txnTenderLineItems = this.tenderLineItems;
		this.saleTxn.customer = this.customer;
		this.saleTxn.saveReturnTxnDetails();

	},
	focusFirstItemQty : function() {
		if (this.saleItemList != undefined && this.saleItemList.length > 0) {
			var sli_access = this.saleItemList[0];
			$('#li_qty' + sli_access.itemId).focus();
		}
	},
	focusFirstItemDiscount : function() {
		if (this.saleItemList != undefined && this.saleItemList.length < 1) {
			var sli_access = this.saleItemList[0];
			$('#li_discountAmt' + sli_access.itemId).focus();
		}

	},
	focusNext : function(itemId) {
		var findIndex;
		$.each(this.saleItemList, function(index, saleLineItem) {
			if (saleLineItem.itemId == itemId)
				findIndex = index;
		});
		if (findIndex != undefined && this.saleItemList.length > (findIndex + 1)) {
			findIndex = +findIndex;
			var nextSLI = this.saleItemList[findIndex + 1];
			$('#li_qty' + nextSLI.itemId).focus();
		} else {
			$('#dueAmt').focus();
		}
	},
	focusPrevious : function(itemId) {
		var findIndex;
		$.each(this.saleItemList, function(index, saleLineItem) {
			if (saleLineItem.itemId == itemId)
				findIndex = index;
		});
		if (findIndex != undefined && findIndex === 0) {
			$('#searchText').focus();
		} else if (findIndex != undefined && findIndex > 0) {
			findIndex = +findIndex;
			var previousSLI = this.saleItemList[findIndex - 1];
			$('#btnDeleteSLI' + previousSLI.itemId).focus();
		}
	},
	focusLastDeleteBtn : function() {
		var itemLength = this.saleItemList.length;
		var lastIndex = itemLength - 1;
		var previousSLI = this.saleItemList[lastIndex];
		$('#btnDeleteSLI' + previousSLI.itemId).focus();
	},
	focusFirstTender : function() {
		var tenderItemLength = this.tenderLineItems.length;
		if (tenderItemLength > 0)
			$('#btnDeleteTLI0').focus();
	},
	txnLIHoverEffect : function(cntl) {
		var txn_liIndex = cntl.id.replace(/[^0-9]/gi, '');
		$('#' + txn_liIndex + 'Container').addClass('txn-line-item-hover');
	},
	txnLIHoverAwayEffect : function(cntl) {
		var txn_liIndex = cntl.id.replace(/[^0-9]/gi, '');
		$('#' + txn_liIndex + 'Container').removeClass('txn-line-item-hover');
	},
	tenderHoverEffect : function(cntl) {
		var tliIndex = cntl.id.replace(/[^0-9]/gi, '');
		$('#' + tliIndex + 'tenderLineItem').addClass('tender-line-item-hover');
	},
	tenderHoverAwayEffect : function(cntl) {
		var tliIndex = cntl.id.replace(/[^0-9]/gi, '');
		$('#' + tliIndex + 'tenderLineItem').removeClass('tender-line-item-hover');
	},
	focusPreviousTender : function(tenderIndex){
		if(tenderIndex>0){
			var tenderItemLength = this.tenderLineItems.length;
			if(tenderItemLength>0){
				tenderIndex=tenderIndex-1;
				$('#btnDeleteTLI'+tenderIndex).focus();
			}
				
		}
		
		
	},
	focusNextTender : function(tenderIndex){
			var tenderItemLength = this.tenderLineItems.length;
			if(tenderItemLength > tenderIndex+1){
				tenderIndex=tenderIndex+1;
				$('#btnDeleteTLI'+tenderIndex).focus();
			}else if(tenderItemLength == tenderIndex){
				$('#dueAmt').focus();
			}else{
				$('#dueAmt').focus();
			}

	}, 
	calculateGSTFlag : function(){

		var openLocGST=$('#gstNo').val();
		var billingLocGST=this.customer.gstNo;
		
		var billingState=this.customer.primaryAddress.state;
		var openLocState='';
		var billingLocState='';
		
		if(typeof(openLocGST)!=='undefined' && openLocGST!=undefined && openLocGST!=''){
			openLocState=openLocGST.substr(0,2);
		}
		if(typeof(billingLocGST)!=='undefined' && billingLocGST!=undefined && billingLocGST!=''){
			billingLocState=billingLocGST.substr(0,2);
		}else if(typeof(billingState)!=='undefined' && billingState!=undefined && billingState!=''){
			// put the billing address state check here
			billingLocState=billingState;
		}
		
		if(openLocState!='' && billingLocState!=''){
			if(openLocState==billingLocState){
				isGSTFlag='S';
			}else{
				isGSTFlag='I';
			}
		}else if(openLocState!='' && billingLocState==''){
			isGSTFlag='S';
		}else{
			// All cases where openLocState is empty or null or not defined
			isGSTFlag='N';
		}
		txnAction.txnHeader.applicableTax=isGSTFlag;
		

}

});
/**
 * Class definition for Tender Line Item Ends
 */
