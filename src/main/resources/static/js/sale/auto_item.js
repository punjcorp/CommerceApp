/**
 * This file has all the javascript code needed for Sales Screen
 * Auto Complete for item lookup functionality
 * 
 */

/**
 * All the global variables for sale screen will be defined here
 */

var scannedItems = [];
var txnAction = new TxnAction();

/**
 * This function will ensure the item auto complete functionality is executed when at least 3 letters has been typed in the item category
 * 
 * @returns
 */
$(function() {
	$("#searchText").autocomplete({
		minLength : 3,
		source : function(request, response) {
			$.post({
				url : "/sku_lookup",
				data : $('form[id=autoCompleteForm]').serialize(),
				success : function(data) {
					response($.map(data, function(item) {
						var dataVal = "<small>" + item.itemId + "-" + item.name + "</small>";
						var descVal = item.name;
						return {
							label : dataVal,
							value : item.itemId,
							desc : descVal,
							longDesc : item.longDesc,
							pic : '/images/logo/cit-76x76.png',
						}
					}));
				}
			});
		},

		select : function(event, ui) {
			event.preventDefault();
			if (ui.item) {
				if ($.inArray(ui.item.value, scannedItems) >= 0) {
					alert("The fucking item already has been added to transaction");
				} else {
					// This will be replaced with function to call add
					// item in transaction area functionality
					addItemToTxn(event, ui);
				}
			}
		}
	});

	$('#btnTenderOK').click(function() {
		if(validateTender()){
			processTender();
		}
	});

});

/* This section will allow the item listing to be in a specific format */
$["ui"]["autocomplete"].prototype["_renderItem"] = function(ul, item) {
	return $("<li></li>").data("item.autocomplete", item).html($('<div/>', {
		'class' : 'row'
	}).append($('<div/>', {
		'class' : 'col-4'
	}).append($('<img/>', {
		src : item.pic
	}))).append($('<div/>', {
		'class' : 'col-6'
	}).append($('<span/>', {
		html : "<b>" + item.value + "</b><br/>" + item.desc
	})))).appendTo(ul);
};

/**
 * This function will take the selected item and add the item to transaction
 * 
 * @param event
 * @param ui
 * @returns
 */
function addItemToTxn(event, ui) {

	$.get("/sale_item_lookup", {
		itemId : ui.item.value,
		locationId : "7997"
	}, function(data, status) {

		// Add item to the global variable
		scannedItems.push(data.itemId);

		createSaleItemLine(data);

	});

	$('#searchText').val('');

}

/**
 * This function will create the elements for item on sales screen and ensure the uniqueness of the control ids
 * 
 * @param data
 * @returns
 */
function createSaleItemLine(data) {
	var output = '<div class="row"> <div class="col-3"><span>';
	output += '<b>' + data.itemId + '-' + data.name + '</b>';
	output += '<br>' + data.longDesc;
	output += '</span></div>';

	var qty = '<div class="col padding-sm"><input class="form-control" onChange="saleItemChanged(this);" id="li_qty';
	qty += data.itemId + '" type="number" min="0" step="0.01" value="';
	qty += data.qty;
	qty += '"></input></div>';

	var priceAmt = '<div class="col padding-sm"><input class="form-control" id="li_priceAmt' + data.itemId + '" type="number" min="0" step="0.01" value="';
	priceAmt += data.priceAmt.toFixed(2);
	priceAmt += '" disabled></input></div>';
	priceAmt += '<input id="li_uh_priceAmt' + data.itemId + '" type="hidden" value="';
	priceAmt += data.priceAmt.toFixed(2);
	priceAmt += '"></input>';

	var discountAmt = '<div class="col padding-sm"><input class="form-control" onChange="saleItemChanged(this);" id="li_discountAmt';
	discountAmt += data.itemId + '" type="number" min="0" step="0.01" value="';
	discountAmt += data.discountAmt.toFixed(2);
	discountAmt += '"></input></div>';
	discountAmt += '<input id="li_uh_discountAmt' + data.itemId + '" type="hidden" value="';
	discountAmt += data.discountAmt.toFixed(2);
	discountAmt += '"></input>';

	var sgstTaxAmt = '<div class="col-1 padding-sm">';
	sgstTaxAmt += '<input class="form-control" id="li_sgstAmt' + data.itemId + '" type="number" min="0" step="0.01" value="';
	sgstTaxAmt += data.sgstTax.amount.toFixed(2);
	sgstTaxAmt += '" disabled></input>';
	sgstTaxAmt += '<label><small><span>(' + data.sgstTax.percentage.toFixed(2) + '%)</span></small></label></div>';
	sgstTaxAmt += '<input id="li_uh_sgstRate' + data.itemId + '" type="hidden" value="';
	sgstTaxAmt += data.sgstTax.percentage.toFixed(2);
	sgstTaxAmt += '"></input>';

	var cgstTaxAmt = '<div class="col-1 padding-sm">';
	cgstTaxAmt += '<input class="form-control" id="li_cgstAmt' + data.itemId + '" type="number" min="0" step="0.01" value="';
	cgstTaxAmt += data.cgstTax.amount.toFixed(2);
	cgstTaxAmt += '" disabled></input>';
	cgstTaxAmt += '<label><small><span>(' + data.cgstTax.percentage.toFixed(2) + '%)</span></small></label></div>';
	cgstTaxAmt += '<input id="li_uh_cgstRate' + data.itemId + '" type="hidden" value="';
	cgstTaxAmt += data.cgstTax.percentage.toFixed(2);
	cgstTaxAmt += '"></input>';

	var total = '<div class="col-2 padding-sm"><h5><span id="li_itemTotal' + data.itemId + '">';
	total += 'INR ' + data.totalAmt.toFixed(2);
	total += '</span></h5></div></div>';

	var finalOutput = output + qty + priceAmt + discountAmt + sgstTaxAmt + cgstTaxAmt + total;

	$('#result').append(finalOutput);
	calculateHeaderTotals();
}

function reCalculateSaleItemFields(liIndex) {
	calculateItemPrice(liIndex);
	calculateDiscount(liIndex);
	calculateTax(liIndex);
	calculateItemTotal(liIndex);
}

function calculateItemPrice(liIndex) {
	var qty = $('#li_qty' + liIndex).val();
	var unitPrice = $('#li_uh_priceAmt' + liIndex).val();
	var itemPrice = unitPrice * qty;
	itemPrice = itemPrice.toFixed(2);
	$('#li_priceAmt' + liIndex).val(itemPrice);
}

function calculateDiscount(liIndex) {
	var discountAmt = +$('#li_discountAmt' + liIndex).val();
	var itemPrice = +$('#li_priceAmt' + liIndex).val();
	if (discountAmt > itemPrice) {
		$('#li_discountAmt' + liIndex).val(0.00);
		alert('The discount amount cannot be more than item price');
	} else {
		$('#li_discountAmt' + liIndex).val(discountAmt.toFixed(2));
	}
	// This will be applicable when we will have automatic discounts
}

function calculateTax(liIndex) {
	var qty = +$('#li_qty' + liIndex).val();
	var discountAmt = +$('#li_discountAmt' + liIndex).val();

	var sgstUnitTax = +$('#li_uh_sgstRate' + liIndex).val();
	var itemPrice = +$('#li_priceAmt' + liIndex).val();

	var sgstTaxAmt = (itemPrice - discountAmt) * sgstUnitTax / 100;
	sgstTaxAmt = sgstTaxAmt.toFixed(2);
	$('#li_sgstAmt' + liIndex).val(sgstTaxAmt);

	var cgstUnitTax = $('#li_uh_cgstRate' + liIndex).val();
	var cgstTaxAmt = (itemPrice - discountAmt) * cgstUnitTax / 100;
	cgstTaxAmt = cgstTaxAmt.toFixed(2);
	$('#li_cgstAmt' + liIndex).val(cgstTaxAmt);
}

function calculateItemTotal(liIndex) {
	var discountAmt = +$('#li_discountAmt' + liIndex).val();
	var sgstTaxAmt = +$('#li_sgstAmt' + liIndex).val();
	var cgstTaxAmt = +$('#li_cgstAmt' + liIndex).val();
	var itemPrice = +$('#li_priceAmt' + liIndex).val();
	var totalItemPrice = itemPrice - discountAmt + sgstTaxAmt + cgstTaxAmt;
	totalItemPrice = totalItemPrice.toFixed(2);
	$('#li_itemTotal' + liIndex).text('INR ' + totalItemPrice);
}

function saleItemChanged(cntl) {
	var liIndex = cntl.id.replace(/[^0-9]/gi, '');
	if (validateQtyAndDiscountAmt(cntl, liIndex)) {
		reCalculateSaleItemFields(liIndex);
		calculateHeaderTotals();
		this.reCalculateTenders();
	}

}

function validateQtyAndDiscountAmt(cntl, liIndex) {
	if (cntl.id.indexOf('li_qty') == 0) {
		var qtyValue = +$('#li_qty' + liIndex).val();
		if ((!qtyValue) || (qtyValue.toFixed(2) <= 0.00)) {
			alert('The quantity should be a positive value always.Please correct the quantity.');
			$('#li_qty' + liIndex).addClass("is-invalid");
			$('#li_qty' + liIndex).val(1);
			$('#li_qty' + liIndex).focus();
			return false;
		}else{
			$('#li_qty' + liIndex).removeClass("is-invalid");
		}
	}
	if (cntl.id.indexOf('li_discountAmt') == 0) {
		var discountValue = +$('#li_discountAmt' + liIndex).val();
		var priceValue = +$('#li_priceAmt' + liIndex).val();
		if ( (!discountValue) || (discountValue.toFixed(2) < 0.00) || (discountValue > priceValue)) {
			alert('The discount amount should be between INR 0.00 and item price amount.Please correct the amount.');
			
			$('#li_discountAmt' + liIndex).addClass("is-invalid");
			$('#li_discountAmt' + liIndex).focus();
			$('#li_discountAmt' + liIndex).val(0.00);
			return false;
		}else{
			$('#li_discountAmt' + liIndex).removeClass("is-invalid");
		}
	}

	return true;
}

function calculateHeaderTotals() {
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
			totalAmtText = +totalAmtText.replace('INR ', '');
			totalAmt += totalAmtText;
		}
		if (this.id.indexOf("li_discountAmt") >= 0) {
			totalDiscount += +$(this).val();
		}
		if (this.id.indexOf("li_priceAmt") >= 0) {
			totalPrice += +$(this).val();
		}
		if (this.id.indexOf("li_sgstAmt") >= 0) {
			totalSGSTTax += +$(this).val();
		}
		if (this.id.indexOf("li_cgstAmt") >= 0) {
			totalCGSTTax += +$(this).val();
		}
	});

	totalTax = totalSGSTTax + totalCGSTTax;

	$('#salesHeaderSubTotalAmt').text('INR  ' + totalPrice.toFixed(2));
	$('#salesHeaderDiscountAmt').text('INR  ' + totalDiscount.toFixed(2));
	$('#salesHeaderTaxAmt').text('INR  ' + totalTax.toFixed(2));
	$('#salesHeaderTotalAmt').text('INR  ' + totalAmt.toFixed(2));

	$('#hc_totalSubAmt').val(totalPrice.toFixed(2));
	$('#hc_totalDiscountAmt').val(totalDiscount.toFixed(2));
	$('#hc_totalTaxAmt').val(totalTax.toFixed(2));
	$('#hc_totalDueAmt').val(totalAmt.toFixed(2));

	$('#dueAmt').val(totalAmt.toFixed(2));

}

function validateTender(){
	var tenderEnteredAmt = +$('#dueAmt').val();
	if((!tenderEnteredAmt) || (tenderEnteredAmt=='') || (tenderEnteredAmt<=0.00)){
		alert('The tendered amount should be more than 0.00');
		$('#dueAmt').addClass("is-invalid");
		$('#dueAmt').focus();
		return false;
	}else{
		
		$('#dueAmt').removeClass("is-invalid");
	}
	return true;
}


function processTender() {
	var tenderRadio = $('input[name="tenderRadio"]');
	var tenderName = tenderRadio.filter(':checked').val();

	if (tenderName) {
		var tenderEnteredAmt = +$('#dueAmt').val();
		if (!tenderEnteredAmt) {
			tenderEnteredAmt = 0.00;
		}
		if (tenderEnteredAmt.toFixed(2) != 0.00) {
			var totalDueAmt = +$('#hc_totalDueAmt').val();
			txnAction.calculateDue(tenderEnteredAmt, totalDueAmt, tenderName);
		} else {
			$('#dueAmt').attr('title', 'The amount should be greater than INR 0.00');
			('[data-toggle="tooltip"]').tooltip();
		}
	} else {
		alert('Please select tender for the payment');
	}
}

function deleteTender(deleteIndex) {
	var totalDueAmt = +$('#hc_totalDueAmt').val();
	txnAction.removeTenderItem(deleteIndex, totalDueAmt);
}

function reCalculateTenders() {
	var totalDueAmt = +$('#hc_totalDueAmt').val();
	txnAction.calculateDue(0.00, totalDueAmt, 'Cash');
}
