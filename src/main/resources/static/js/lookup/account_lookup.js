var accountDTO = new AccountDTO();
var searchBean = new SearchBean();
var token = $("meta[name='_csrf']").attr("content");
var supplierChangeFlag = 'F';
var selectedSupplierData;
var journalTable;

$(function() {
	
	$(window).keydown(function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
	      return false;
	    }
	  });

	$("#searchBean\\.searchText").change(function() {

		if (supplierChangeFlag == 'F') {
			$('#supplierId').val('');
			$('#supplierMsg').addClass('invalid-feedback');
			$('#supplierMsg').html('<h6>Please enter a valid Supplier!</h6>');
			$('#supplierMsg').show();

			$('#supplierDetails').addClass('d-none');
			$('#supplierAccountDetails').addClass('d-none');
			$('#searchBtn').addClass('d-none');

		}
	});

	journalTable=$('#paymentDetails').DataTable( {
        keys: true,
        responsive : true,
        colReorder: true,
        select: true,
        buttons: [
            'copyHtml5',
            'excelHtml5',
            'csvHtml5',
            'pdfHtml5'
        ],
		columns: [ { "name": "sNo" },
		    { "name": "createdOn" },
		    { "name": "locationId" },
		    { "name": "txnType" },
		    { "name": "desc" },
		    { "name": "credit" },
		    { "name": "debit" } ]
    } );
	
	journalTable.buttons().container().appendTo( $('#tableBtns') );
	
	$("#selectedLocation").change(function() {
		updateBasedOnLocation(this.value);
	});
	
	$("#searchBean\\.searchText")
			.autocomplete(
					{
						minLength : 2,
						source : function(request, response) {
							$('#supplierSearchBusy').removeClass('d-none');
							accountDTO.entityType = $(
									"#searchBean\\.searchText").val();
							accountDTO.searchAccount = 'true';
							var formdata = JSON.stringify(accountDTO);
							supplierChangeFlag = 'T';
							// AJAX call here and refresh the Expense Screen
							// after the save
							$
									.ajax({
										url : '/admin/search_supplier',
										type : 'POST',
										cache : false,
										data : formdata,
										contentType : "application/json; charset=utf-8",
										dataType : "json",
										success : function(suppliers) {
											$('#supplierSearchBusy').addClass(
													'd-none');
											if (!suppliers.length) {
												$('#supplierId').val('');
												$('#supplierMsg').addClass('invalid-feedback');
												$('#supplierMsg').html('<h6>Please enter a valid Supplier!</h6>');
												$('#supplierMsg').show();
												
												$('#supplierDetails').addClass('d-none');
												$('#supplierAccountDetails').addClass('d-none');
												$('#searchBtn').addClass('d-none');
											} else {
												$('#supplierMsg').hide();

												response($
														.map(
																suppliers,
																function(
																		supplier) {
																	var dataVal = ""
																			+ supplier.name
																			+ "-( Ph- "
																			+ supplier.phone1
																			+ " )";
																	var descVal = supplier.email;
																	return {
																		display : dataVal,
																		name : supplier.name,
																		phone : supplier.phone1,
																		phone2 : supplier.phone2,
																		supplierId : supplier.supplierId,
																		emailOrg : supplier.email,
																		email : descVal,
																		type : 'SUPPLIER',
																		addressId : supplier.primaryAddress.addressId,
																		addressType : supplier.primaryAddress.addressType,
																		address1 : supplier.primaryAddress.address1,
																		address2 : supplier.primaryAddress.address2,
																		city : supplier.primaryAddress.city,
																		state : supplier.primaryAddress.state,
																		country : supplier.primaryAddress.country,
																		pincode : supplier.primaryAddress.pincode,
																		supplierAccounts : supplier.supplierAccounts
																	}
																}));
											}
										},
										beforeSend : function(xhr) {
											xhr.setRequestHeader(
													'X-CSRF-TOKEN', token)
										},
										error : function(e){
											$('#supplierSearchBusy').addClass('d-none');
											$('#supplierId').val('');
											$('#supplierMsg').addClass('invalid-feedback');
											$('#supplierMsg').html('<h6>No Supplier Found!</h6>');
											$('#supplierMsg').show();
											
											$('#supplierDetails').addClass('d-none');
											$('#supplierAccountDetails').addClass('d-none');
											$('#searchBtn').addClass('d-none');
										}
									});

						},
						change : function(event, ui) {
							if (ui.item == null || ui.item == undefined) {
								$('#supplierSearchBusy').addClass('d-none');
								$('#supplierId').val('');
								$('#supplierMsg').addClass('invalid-feedback');
								$('#supplierMsg').html('<h6>Please choose a valid Supplier from the list!</h6>');
								$('#supplierMsg').show();
								
								$('#supplierDetails').addClass('d-none');
								$('#supplierAccountDetails').addClass('d-none');
								$('#searchBtn').addClass('d-none');
							} else {
								$('#supplierMsg').hide();
							}
						},
						select : function(event, ui) {
							event.preventDefault();
							if (ui.item) {
								getSupplierDetails(event, ui);
							}
						}
					});

	afterSuccessSearchRequest();
});

/* This section will allow the item listing to be in a specific format */
$["ui"]["autocomplete"].prototype["_renderItem"] = function(ul, item) {

	return $("<li></li>").data("item.autocomplete", item).html(

	$('<div/>', {
		'class' : 'row px-2'
	}).append($('<div/>', {
		'class' : 'col-12'
	}).append($('<span/>', {
		'html' : '<b>' + item.display + '</b>'
	}))).append($('<div/>', {
		'class' : 'col-12'
	}).append($('<span/>', {
		'html' : item.email
	})))

	).appendTo(ul);

};

function getSupplierDetails(event, ui) {
	$('#searchBtn').removeClass('d-none');
	$('#searchBean\\.searchText').val(ui.item.name);
	$('#supplierId').val(ui.item.supplierId);
	selectedSupplierData = ui.item;
	this.renderSupplierPrimaryAddress(ui.item);
	this.processAccountHeadDetails(ui.item);

}

function afterSuccessSearchRequest(){
	if(supplier_data!=undefined && supplier_data.supplierId!=null){
		selectedSupplierData = supplier_data;
		this.renderSupplierPrimaryAddress(selectedSupplierData);
		this.processAccountHeadDetails(selectedSupplierData);
		var selectedLocationVal=$('#selectedLocation').val();
		updateBasedOnLocation(selectedLocationVal);
	}
	
}

function updateBasedOnLocation(selectedLocationVal) {
	if (selectedSupplierData != undefined) {
		this.renderSupplierPrimaryAddress(selectedSupplierData);
		this.processAccountHeadDetails(selectedSupplierData);
	}else{
		$('#supplierDetails').addClass('d-none');
		$('#supplierAccountDetails').addClass('d-none');
	}

	if(selectedLocationVal==0){
		journalTable.search('').columns().search('').draw();
	}else{
		journalTable.column('locationId:name').search( selectedLocationVal ).draw();
	}
	
}

function renderSupplierPrimaryAddress(supplier) {
	$('#primaryAddress1').text(supplier.address1);
	if (supplier.address2 && supplier.address2 != 'undefined'
			&& supplier.address2 != '')
		$('#primaryAddress2').text(supplier.address2);
	$('#primaryCity').text(supplier.city + ', ' + supplier.state);
	$('#primaryCountry').text(supplier.country + ' - ' + supplier.pincode);
	$('#supplierPhone').text(supplier.phone);
	$('#supplierEmail').text(supplier.email);

	$('#supplier\\.supplierId').val(supplier.supplierId);
	$('#supplier\\.name').val(supplier.name);
	$('#supplier\\.phone1').val(supplier.phone);
	$('#supplier\\.phone2').val(supplier.phone2);
	$('#supplier\\.email').val(supplier.emailOrg);

	$('#supplier\\.primaryAddress\\.addressId').val(supplier.addressId);
	$('#supplier\\.primaryAddress\\.addressType').val(supplier.addressType);
	$('#supplier\\.primaryAddress\\.primary').val('Y');
	$('#supplier\\.primaryAddress\\.address1').val(supplier.address1);
	$('#supplier\\.primaryAddress\\.address2').val(supplier.address2);
	$('#supplier\\.primaryAddress\\.city').val(supplier.city);
	$('#supplier\\.primaryAddress\\.state').val(supplier.state);
	$('#supplier\\.primaryAddress\\.country').val(supplier.country);
	$('#supplier\\.primaryAddress\\.pincode').val(supplier.pincode);

	$('#supplierDetails').removeClass('d-none');
}

function processAccountHeadDetails(supplier) {
	var supplierStoreAccount;
	var selectedStore = $('#selectedLocation').val();
	var accounts = supplier.supplierAccounts;
	if (accounts && accounts.length > 0) {
		if (selectedStore == 0) {
			supplierStoreAccount = new AccountHead();

			$
					.each(
							accounts,
							function(itrIndex, itrAccount) {

								supplierStoreAccount.entityId = itrAccount.entityId;
								supplierStoreAccount.entityType = itrAccount.entityType;
								supplierStoreAccount.entityName = itrAccount.entityName;

								supplierStoreAccount.advanceAmt = parseFloat(supplierStoreAccount.advanceAmt)
										+ parseFloat(itrAccount.advanceAmt);
								supplierStoreAccount.dueAmt = parseFloat(supplierStoreAccount.dueAmt)
										+ parseFloat(itrAccount.dueAmt);
								supplierStoreAccount.paymentAmt = parseFloat(supplierStoreAccount.paymentAmt)
										+ parseFloat(itrAccount.paymentAmt);

								supplierStoreAccount.locationIds
										.push(itrAccount.locationId);
								supplierStoreAccount.accountIds
										.push(itrAccount.accountId);

							});

		} else {
			var lookedUpAccounts = $
					.grep(
							accounts,
							function(account) {
								return (account.locationId == selectedStore && account.entityId == supplier.supplierId);
							});

			supplierStoreAccount = lookedUpAccounts[0];
		}
		supplierStoreAccount.advanceAmt = parseFloat(
				supplierStoreAccount.advanceAmt).toFixed(2);
		supplierStoreAccount.dueAmt = parseFloat(supplierStoreAccount.dueAmt)
				.toFixed(2);
		this.renderAccountHeadDetails(supplierStoreAccount);
	}

}

function renderAccountHeadDetails(supplierStoreAccount) {

	$('#accountHead\\.locationId').val(supplierStoreAccount.locationId);
	$('#accountHead\\.accountId').val(supplierStoreAccount.accountId);
	$('#accountHead\\.entityType').val(supplierStoreAccount.entityType);
	$('#accountHead\\.entityId').val(supplierStoreAccount.entityId);
	$('#accountHead\\.entityName').val(supplierStoreAccount.entityName);

	$('#accountHead\\.advanceAmt').val(supplierStoreAccount.advanceAmt);
	$('#accountHead\\.dueAmt').val(supplierStoreAccount.dueAmt);

	$('#lbl_advanceAmt').text(supplierStoreAccount.advanceAmt);
	$('#lbl_dueAmt').text(supplierStoreAccount.dueAmt);

	$('#supplierAccountDetails').removeClass('d-none');
}
