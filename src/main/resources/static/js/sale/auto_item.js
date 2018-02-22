/**
 * This file has all the javascript code needed for Sales Screen
 * Auto Complete for item lookup functionality
 * 
 */


/**
 * All the global variables for sale screen will be defined here
 */

var scannedItems=[];








/**
 * This function will ensure the item auto complete functionality is executed
 * when at least 3 letters has been typed in the item category
 * 
 * @returns
 */
$(function() {
	$("#searchText").autocomplete(
			{
				minLength : 3,
				source : function(request, response) {
					$.post({
						url : "/sku_lookup",
						data : $('form[id=autoCompleteForm]').serialize(),
						success : function(data) {
							response($.map(data, function(item) {
								var dataVal = "<small>" + item.itemId + "-"
										+ item.name + "</small>";
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
						if ($.inArray( ui.item.value, scannedItems )>=0){
							alert("The fucking item already has been added to transaction");
						} else{
							// This will be replaced with function to call add
							// item in transaction area functionality
							addItemToTxn(event, ui);
						}
					}
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

	$.get("/sale_item_lookup",
		{
			itemId : ui.item.value,
			locationId : "7997"
		},function(data, status) {
			
			// Add item to the global variable
			scannedItems.push(data.itemId);

			
			createSaleItemLine(data);
			


		});

	$('#searchText').val('');

}

/**
 * This function will create the elements for item on sales screen and ensure
 * the uniqueness of the control ids
 * 
 * @param data
 * @returns
 */
function createSaleItemLine(data){
	var output = '<div class="row"> <div class="col-3"><span>';
	output += '<b>' + data.itemId + '-' + data.name
			+ '</b>';
	output += '<br>' + data.longDesc;
	output += '</span></div>';

	var qty = '<div class="col"><input class="form-control" onChange="saleItemChanged(this);" id="li_qty'+data.itemId+'" type="text" value="';
	qty += data.qty;
	qty += '"></input></div>';

	var priceAmt = '<div class="col"><input class="form-control" id="li_priceAmt'+data.itemId+'" type="text" value="';
	priceAmt += data.priceAmt.toFixed(2);
	priceAmt += '" disabled></input></div>';
	priceAmt+='<input id="li_uh_priceAmt'+data.itemId+'" type="hidden" value="';
	priceAmt += data.priceAmt.toFixed(2);
	priceAmt += '"></input>';

	var discountAmt = '<div class="col"><input class="form-control" onChange="saleItemChanged(this);" id="li_discountAmt'+data.itemId+'" type="text" value="';
	discountAmt += data.discountAmt.toFixed(2);
	discountAmt += '"></input></div>';
	discountAmt += '<input id="li_uh_discountAmt'+data.itemId+'" type="hidden" value="';
	discountAmt += data.discountAmt.toFixed(2);
	discountAmt += '"></input>';
	

	var sgstTaxAmt = '<div class="col-1"><input class="form-control" id="li_sgstAmt'+data.itemId+'" type="text" value="';
	sgstTaxAmt += data.sgstTax.amount.toFixed(2);
	sgstTaxAmt += '" disabled></input></div><br><br><br><span>'+data.sgstTax.percentage.toFixed(2)+'%</span>';
	sgstTaxAmt += '<input id="li_uh_sgstRate'+data.itemId+'" type="hidden" value="';
	sgstTaxAmt += data.sgstTax.percentage.toFixed(2);
	sgstTaxAmt += '"></input>';	
	
	var cgstTaxAmt = '<div class="col-1"><input class="form-control" id="li_cgstAmt'+data.itemId+'" type="text" value="';
	cgstTaxAmt += data.cgstTax.amount.toFixed(2);
	cgstTaxAmt += '" disabled></input></div><br><br><br><span>'+data.sgstTax.percentage.toFixed(2)+'%</span>';
	cgstTaxAmt += '<input id="li_uh_cgstRate'+data.itemId+'" type="hidden" value="';
	cgstTaxAmt += data.cgstTax.percentage.toFixed(2);
	cgstTaxAmt += '"></input>';
	
	
	var total = '<div class="col-2"><h4><span id="li_itemTotal'+data.itemId+'">';
	total += 'INR '+data.totalAmt.toFixed(2);
	total += '</span></h4></div></div>';

	var finalOutput = output + qty + priceAmt + discountAmt
			+ sgstTaxAmt + cgstTaxAmt + total;

	$('#result').append(finalOutput);	
}


function reCalculateSaleItemFields(liIndex){
	calculateItemPrice(liIndex);
	calculateDiscount(liIndex);
	calculateTax(liIndex);
	calculateItemTotal(liIndex);
}

function calculateItemPrice(liIndex){
	var qty=$('#li_qty'+liIndex).val();
	var unitPrice=$('#li_uh_priceAmt'+liIndex).val();
	var itemPrice= unitPrice * qty;
	itemPrice=itemPrice.toFixed(2);
	$('#li_priceAmt'+liIndex).val(itemPrice);
}

function calculateDiscount(liIndex){
	var discountAmt=+$('#li_discountAmt'+liIndex).val();
	$('#li_discountAmt'+liIndex).val(discountAmt.toFixed(2));
//This will be applicable when we will have automatic discounts
}

function calculateTax(liIndex){
	var sgstUnitTax=$('#li_uh_sgstRate'+liIndex).val();
	var itemPrice=$('#li_priceAmt'+liIndex).val();
	var sgstTaxAmt= itemPrice * sgstUnitTax/100;
	sgstTaxAmt=sgstTaxAmt.toFixed(2);
	$('#li_sgstAmt'+liIndex).val(sgstTaxAmt);
	
	var cgstUnitTax=$('#li_uh_cgstRate'+liIndex).val();	
	var cgstTaxAmt= itemPrice * cgstUnitTax/100;
	cgstTaxAmt=cgstTaxAmt.toFixed(2);
	$('#li_cgstAmt'+liIndex).val(cgstTaxAmt);	
}


function calculateItemTotal(liIndex){
	var discountAmt=+$('#li_discountAmt'+liIndex).val();
	var sgstTaxAmt=+$('#li_sgstAmt'+liIndex).val();
	var cgstTaxAmt=+$('#li_cgstAmt'+liIndex).val();
	var itemPrice=+$('#li_priceAmt'+liIndex).val();
	var totalItemPrice= itemPrice - discountAmt + sgstTaxAmt + cgstTaxAmt;
	totalItemPrice=totalItemPrice.toFixed(2);
	$('#li_itemTotal'+liIndex).text('INR  '+totalItemPrice);	
}



function saleItemChanged(cntl){
	var liIndex=cntl.id.replace(/[^0-9]/gi, '');
	reCalculateSaleItemFields(liIndex);
}





