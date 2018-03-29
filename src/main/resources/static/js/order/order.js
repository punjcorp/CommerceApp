/**
 * This file has all the javascript code needed for Sales Screen
 * Auto Complete for item lookup functionality
 * 
 */

/**
 * All the global variables for sale screen will be defined here
 */

/**
 * This function will ensure the item auto complete functionality is executed when at least 3 letters has been typed in the item category
 * 
 * @returns
 */
/**
 * The expense related global variables
 */

var accountDTO = new AccountDTO();
var searchBean = new SearchBean();
var token = $("meta[name='_csrf']").attr("content");


$(function() {
	
	$("#supplierSearch\\.searchText").autocomplete({
		minLength : 3,
		source : function(request, response) {
			accountDTO.entityType = $("#supplierSearch\\.searchText").val();
			var formdata = JSON.stringify(accountDTO);

			// AJAX call here and refresh the Expense Screen after the save
			$.ajax({
				url : '/admin/search_supplier',
				type : 'POST',
				cache : false,
				data : formdata,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(suppliers) {
					response($.map(suppliers, function(supplier) {
						var dataVal = "" + supplier.name + "-( Ph- " + supplier.phone1 + " )";
						var descVal = supplier.email;
						return {
							label : dataVal,
							name : supplier.name,
							phone : supplier.phone1,
							phone2 : supplier.phone2,
							supplierId : supplier.supplierId,
							emailOrg : supplier.email,
							email : descVal,
							type: 'SUPPLIER',
							addressId: supplier.primaryAddress.addressId,
							addressType: supplier.primaryAddress.addressType,
							address1: supplier.primaryAddress.address1,
							address2: supplier.primaryAddress.address2,
							city: supplier.primaryAddress.city,
							state: supplier.primaryAddress.state,
							country: supplier.primaryAddress.country,
							pincode: supplier.primaryAddress.pincode
						}
					}));

				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader('X-CSRF-TOKEN', token)
				}
			});

		},

		select : function(event, ui) {
			event.preventDefault();
			if (ui.item) {
				getSupplierDetails(event, ui);
			}
		}
	});

	$('[id^=order\\.orderItems][id$=\\.itemId]').autocomplete({
		minLength : 3,
		source : function(request, response) {
			var searchText= this.element[0].value;
			searchBean.searchText=searchText; 
			var formdata = JSON.stringify(searchBean);

			// AJAX call here and refresh the Expense Screen after the save
			$.ajax({
				url : '/order_item_lookup',
				type : 'POST',
				cache : false,
				data : formdata,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					response($.map(data, function(item) {
						var dataVal = "<small>" + item.itemId + "-" + item.name + "</small>";
						var descVal = item.name;
						return {
							label : dataVal,
							value : item.itemId,
							desc : descVal,
							longDesc : item.longDesc,
							pic : '/images/default_image.png',
						}
					}));
				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader('X-CSRF-TOKEN', token)
				}
			});

		},

		select : function(event, ui) {
			event.preventDefault();
			if (ui.item) {
				getItemDetails(event, ui);
			}
		}
	});	
	
	
	$('input[name="tenderRadio"]').change(function() {
		displayTenderControls();
		clearTenderErrors();
	});

	$('#tenderAmount').change(function(){
		var tenderAmt=+$('#tenderAmount').val();
		if(tenderAmt==''){
			$('#tenderAmount').val('0.00');
		}else if(tenderAmt>0){
			$('#tenderAmount').val(tenderAmt.toFixed(2));
		}
	})
	
	$('#btnAddTender').click(function() {
		addTender();
	});

	$('#btnSavePayment').click(function() {
		txnEndTime = moment().format("DD-MMM-YY hh:mm:ss");
		if (validatePaymentDetails()) {
			readPaymentDetails();
			payment.savePayment();
		}
	});

});

/* This section will allow the item listing to be in a specific format */
$["ui"]["autocomplete"].prototype["_renderItem"] = function(ul, item) {
	if(this.element[0].id.indexOf('supplierSearch')>-1){
		return $("<li></li>").data("item.autocomplete", item).html(

				$('<div/>', {
					'class' : 'row px-2'
				}).append($('<div/>', {
					'class' : 'col-12'
				}).append($('<span/>', {
					'html' : '<b>' + item.label + '</b>'
				}))).append($('<div/>', {
					'class' : 'col-12'
				}).append($('<span/>', {
					'html' : item.email
				})))

				).appendTo(ul);		
	}else{
		return $("<li></li>").data("item.autocomplete", item).html($('<div/>', {
			'class' : 'row'
		}).append($('<div/>', {
			'class' : 'col-4'
		}).append($('<img/>', {
			src : item.pic,
			'class': 'img-fluid' 
		}))).append($('<div/>', {
			'class' : 'col-6'
		}).append($('<span/>', {
			html : "<b>" + item.value + "</b><br/>" + item.desc
		})))).appendTo(ul);
	}

};

/**
 * This function will take the selected expense reason code type
 * 
 * @param event
 * @param ui
 * @returns
 */
function getSupplierDetails(event, ui) {
	
	$('#supplierSearch\\.searchText').val(ui.item.name);
	$('#order\\.supplierId').val(ui.item.supplierId);	

	this.renderSupplierPrimaryAddress(ui.item);

}


function renderSupplierPrimaryAddress(supplier){
	$('#primaryAddress1').text(supplier.address1);
	if(supplier.address2 && supplier.address2!='undefined' && supplier.address2!='')
		$('#primaryAddress2').text(supplier.address2);
	$('#primaryCity').text(supplier.city+', '+supplier.state);
	$('#primaryCountry').text(supplier.country+' - '+supplier.pincode);
	$('#supplierPhone').text(supplier.phone);
	$('#supplierEmail').text(supplier.email);
	
	$('#order\\.supplier\\.supplierId').val(supplier.supplierId);
	$('#order\\.supplier\\.name').val(supplier.name);
	$('#order\\.supplier\\.phone1').val(supplier.phone);
	$('#order\\.supplier\\.phone2').val(supplier.phone2);
	$('#order\\.supplier\\.email').val(supplier.emailOrg);
	
	$('#order\\.supplier\\.primaryAddress\\.addressId').val(supplier.addressId);
	$('#order\\.supplier\\.primaryAddress\\.addressType').val(supplier.addressType);
	$('#order\\.supplier\\.primaryAddress\\.primary').val('Y');
	$('#order\\.supplier\\.primaryAddress\\.address1').val(supplier.address1);
	$('#order\\.supplier\\.primaryAddress\\.address2').val(supplier.address2);
	$('#order\\.supplier\\.primaryAddress\\.city').val(supplier.city);
	$('#order\\.supplier\\.primaryAddress\\.state').val(supplier.state);
	$('#order\\.supplier\\.primaryAddress\\.country').val(supplier.country);
	$('#order\\.supplier\\.primaryAddress\\.pincode').val(supplier.pincode);
	
	$('#supplierDetails').removeClass('d-none');
}



function renderSupplierPrimaryAddressBKP(supplier){
	var supplierDetails='<div class="card border-box-filled">';
	supplierDetails+='<div class="card-body">';
	supplierDetails+='<div class="row">';
	supplierDetails+='<div class="col-4">';
	supplierDetails+='<div class="card-text"><h4>Primary Address</h4</div>';
	supplierDetails+='<div class="card-text my-3"><div class="fa-3x"><span class="fa-layers fa-fw">';
	supplierDetails+='<i class="fas fa-map-marker"></i> <i class="fas fa-circle color-blue" ';
	supplierDetails+=' data-fa-transform="shrink-10 up-2"></i></span></div></i></div>';
	supplierDetails+='</div>';
	supplierDetails+='</div>';
	supplierDetails+='<div class="col-8">';
	supplierDetails+='<div class="row"><div class="col-2"><i class="fas fa-address-card"></i></div><div class="col text-left"><div class="card-text"><small>'+supplier.address1+'</small></div></div></div>';
	if(supplier.address2 && supplier.address2!='undefined' && supplier.address2!='')
		supplierDetails+='<div class="row"><div class="col-2"></div><div class="col text-left"><div class="card-text"><small>'+supplier.address2+'</small></div></div></div>';
	supplierDetails+='<div class="row"><div class="col-2"></div><div class="col text-left"><div class="card-text"><small>'+supplier.city+', '+supplier.state+'</small></div></div></div>';
	supplierDetails+='<div class="row"><div class="col-2"></div><div class="col text-left"><div class="card-text"><small>'+supplier.country+' - '+supplier.pincode+'</small></div></div></div>';
	supplierDetails+='<div class="row"><div class="col-2"><i class="fas fa-phone" data-fa-transform="flip-h"></i></div><div class="col text-left"><div class="card-text"><small>'+supplier.phone+'</small></div></div></div>';
	supplierDetails+='<div class="row"><div class="col-2"><i class="fas fa-envelope"></i></div><div class="col text-left"><div class="card-text">'+supplier.email+'</div></div></div>';
	supplierDetails+='</div>';
	supplierDetails+='</div>';
	supplierDetails+='</div>';
	supplierDetails+='</div>';
	$('#supplierDetails').html(supplierDetails);
}
