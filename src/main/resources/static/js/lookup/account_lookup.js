var accountDTO = new AccountDTO();
var searchBean = new SearchBean();
var token = $("meta[name='_csrf']").attr("content");
var supplierChangeFlag = 'F';
var selectedSupplierData;

$(function() {

	$("#selectedLocation").change(function() {
		updateBasedOnLocation();
	});

	$("#searchBean\\.searchText").change(function() {

		if (supplierChangeFlag == 'F') {
			$('#supplierId').val('');
			$('#supplierMsg').addClass('invalid-feedback');
			$('#supplierMsg').html('<h6>Please enter a valid Supplier!</h6>');
			$('#supplierMsg').show();
		}
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
												$('#supplierMsg').addClass(
														'invalid-feedback');
												$('#supplierMsg')
														.html(
																'<h6>Please enter a valid Supplier!</h6>');
												$('#supplierMsg').show();
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
																		accountHeads : supplier.supplierAccounts
																	}
																}));
											}
										},
										beforeSend : function(xhr) {
											xhr.setRequestHeader(
													'X-CSRF-TOKEN', token)
										}
									});

						},
						change : function(event, ui) {
							if (ui.item == null || ui.item == undefined) {
								$('#supplierId').val('');
								$('#supplierMsg').addClass('invalid-feedback');
								$('#supplierMsg')
										.html(
												'<h6>Please choose a valid Supplier from the list!</h6>');
								$('#supplierMsg').show();
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

	$('#searchBean\\.searchText').val(ui.item.name);
	$('#supplierId').val(ui.item.supplierId);
	selectedSupplierData = ui.item;
	this.renderSupplierPrimaryAddress(ui.item);
	this.processAccountHeadDetails(ui.item);

}

function updateBasedOnLocation() {
	this.renderSupplierPrimaryAddress(selectedSupplierData);
	this.processAccountHeadDetails(selectedSupplierData);

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
	var accounts = supplier.accountHeads;
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

								supplierStoreAccount.advanceAmt = supplierStoreAccount.advanceAmt
										+ itrAccount.advanceAmt;
								supplierStoreAccount.dueAmt = supplierStoreAccount.dueAmt
										+ itrAccount.dueAmt;
								supplierStoreAccount.paymentAmt = supplierStoreAccount.paymentAmt
										+ itrAccount.paymentAmt;

								supplierStoreAccount.locationIds
										.push(itrAccount.locationId);
								supplierStoreAccount.accountIds
										.push(itrAccount.accountId);

							});

		} else {
			var lookedUpAccounts = $.grep(accounts, function(account) {
				return (account.locationId == selectedStore && account.entityId == supplier.supplierId);
			});

			supplierStoreAccount=lookedUpAccounts[0]; 
		}
		supplierStoreAccount.advanceAmt=supplierStoreAccount.advanceAmt.toFixed(2);
		supplierStoreAccount.dueAmt=supplierStoreAccount.dueAmt.toFixed(2);
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
