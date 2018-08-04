var accountDTO = new AccountDTO();
var customerBean = new Customer();
var searchBean = new SearchBean();
var token = $("meta[name='_csrf']").attr("content");
var customerChangeFlag = 'F';
var selectedCustomerData;
var journalTable;

$(function() {

	$(window).keydown(function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
	      return false;
	    }
	  });

	$("#searchBean\\.searchText").change(function() {

		if (customerChangeFlag == 'F') {
			$('#customerId').val('');
			$('#customerMsg').addClass('invalid-feedback');
			$('#customerMsg').html('<h6>Please enter a valid Customer!</h6>');
			$('#customerMsg').show();

			$('#customerDetails').addClass('d-none');
			$('#customerAccountDetails').addClass('d-none');
			$('#searchBtn').addClass('d-none');
		}
	});

	journalTable = $('#paymentDetails').DataTable({
		keys : true,
		responsive : true,
		colReorder : true,
		select : true,
		buttons : [ 'copyHtml5', 'excelHtml5', 'csvHtml5', 'pdfHtml5' ],
		columns: [ { "name": "sNo" },
		    { "name": "createdOn" },
		    { "name": "locationId" },
		    { "name": "desc" },
		    { "name": "credit" },
		    { "name": "debit" } ],
	});

	journalTable.buttons().container().appendTo($('#tableBtns'));

	$("#selectedLocation").change(function() {
		updateBasedOnLocation(this.value);
	});
	
	
	
	$("#searchBean\\.searchText")
			.autocomplete(
					{
						minLength : 2,
						source : function(request, response) {
							$('#customerSearchBusy').removeClass('d-none');
							customerBean.customerSearchText = $(
									"#searchBean\\.searchText").val();
							customerBean.searchAccount = 'true';
							var formdata = JSON.stringify(customerBean);
							customerChangeFlag = 'T';
							// AJAX call here and refresh the Expense Screen
							// after the save
							$
									.ajax({
										url : '/admin/customer_account_lookup',
										type : 'POST',
										cache : false,
										data : formdata,
										contentType : "application/json; charset=utf-8",
										dataType : "json",
										success : function(customers) {
											$('#customerSearchBusy').addClass(
													'd-none');
											if (!customers.length) {
												$('#customerId').val('');
												$('#customerMsg').addClass('invalid-feedback');
												$('#customerMsg').html('<h6>Please enter a valid Customer!</h6>');
												$('#customerMsg').show();

												$('#customerDetails').addClass('d-none');
												$('#customerAccountDetails').addClass('d-none');
												$('#searchBtn').addClass('d-none');
											} else {
												$('#customerMsg').hide();

												response($
														.map(
																customers,
																function(
																		customer) {
																	var dataVal = ""
																			+ customer.name
																			+ "-( Ph- "
																			+ customer.phone
																			+ " )";
																	var descVal = customer.email;
																	return {
																		display : dataVal,
																		name : customer.name,
																		phone : customer.phone,
																		customerId : customer.customerId,
																		emailOrg : customer.email,
																		email : descVal,
																		type : 'CUSTOMER',
																		customerAccounts : customer.customerAccounts
																	}
																}));
											}
										},
										beforeSend : function(xhr) {
											xhr.setRequestHeader(
													'X-CSRF-TOKEN', token)
										},
										error: function(e) {
											$('#customerSearchBusy').addClass('d-none');
											$('#customerId').val('');
											$('#customerMsg').addClass('invalid-feedback');
											$('#customerMsg').html('<h6>No Customer found!</h6>');
											$('#customerMsg').show();

											$('#customerDetails').addClass('d-none');
											$('#customerAccountDetails').addClass('d-none');
											$('#searchBtn').addClass('d-none');
										}
									});

						},
						change : function(event, ui) {
							if (ui.item == null || ui.item == undefined) {
								$('#customerSearchBusy').addClass('d-none');
								$('#customerId').val('');
								$('#customerMsg').addClass('invalid-feedback');
								$('#customerMsg').html('<h6>Please choose a valid Customer from the list!</h6>');
								$('#customerMsg').show();

								$('#customerDetails').addClass('d-none');
								$('#customerAccountDetails').addClass('d-none');
								$('#searchBtn').addClass('d-none');

							} else {
								$('#supplierMsg').hide();
							}
						},
						select : function(event, ui) {
							event.preventDefault();
							if (ui.item) {
								getCustomerDetails(event, ui);
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

function getCustomerDetails(event, ui) {
	$('#searchBtn').removeClass('d-none');
	$('#searchBean\\.searchText').val(ui.item.name);
	$('#customerId').val(ui.item.customerId);
	selectedCustomerData = ui.item;
	this.renderCustomerData(ui.item);
	this.processAccountHeadDetails(ui.item);

}

function afterSuccessSearchRequest(){
	if(customer_data!=undefined && customer_data.customerId!=null){
		selectedCustomerData = customer_data;
		this.renderCustomerData(selectedCustomerData);
		this.processAccountHeadDetails(selectedCustomerData);
		var selectedLocationVal=$('#selectedLocation').val();
		updateBasedOnLocation(selectedLocationVal);
	}
	
}

function updateBasedOnLocation(selectedLocationVal) {
	if (selectedCustomerData != undefined) {
		this.renderCustomerData(selectedCustomerData);
		this.processAccountHeadDetails(selectedCustomerData);
	} else {
		$('#customerDetails').addClass('d-none');
		$('#customerAccountDetails').addClass('d-none');
	}

	if(selectedLocationVal==0){
		journalTable.search('').columns().search('').draw();
	}else{
		journalTable.column('locationId:name').search( selectedLocationVal ).draw();
	}
	
}

function renderCustomerData(customer) {

	$('#lbl_cust_name').text(customer.name);
	$('#lbl_cust_email').text(customer.email);
	$('#lbl_cust_phone').text(customer.phone);

	$('#customer\\.customerId').val(customer.customerId);
	$('#customer\\.name').val(customer.name);
	$('#customer\\.phone').val(customer.phone);
	$('#customer\\.email').val(customer.emailOrg);
	$('#customer\\.customerType').val('CUSTOMER');

	$('#customerDetails').removeClass('d-none');

}

function processAccountHeadDetails(customer) {
	var customerStoreAccount;
	var selectedStore = $('#selectedLocation').val();
	var accounts = customer.customerAccounts;
	if (accounts && accounts.length > 0) {
		if (selectedStore == 0) {
			customerStoreAccount = new AccountHead();

			$
					.each(
							accounts,
							function(itrIndex, itrAccount) {

								customerStoreAccount.entityId = itrAccount.entityId;
								customerStoreAccount.entityType = itrAccount.entityType;
								customerStoreAccount.entityName = itrAccount.entityName;

								customerStoreAccount.advanceAmt = parseFloat(customerStoreAccount.advanceAmt)
										+ parseFloat(itrAccount.advanceAmt);
								customerStoreAccount.dueAmt = parseFloat(customerStoreAccount.dueAmt)
										+ parseFloat(itrAccount.dueAmt);
								customerStoreAccount.paymentAmt = parseFloat(customerStoreAccount.paymentAmt)
										+ parseFloat(itrAccount.paymentAmt);

								customerStoreAccount.locationIds
										.push(itrAccount.locationId);
								customerStoreAccount.accountIds
										.push(itrAccount.accountId);

							});

		} else {
			var lookedUpAccounts = $
					.grep(
							accounts,
							function(account) {
								return (account.locationId == selectedStore && account.entityId == customer.customerId);
							});

			customerStoreAccount = lookedUpAccounts[0];
		}
		customerStoreAccount.advanceAmt = parseFloat(
				customerStoreAccount.advanceAmt).toFixed(2);
		customerStoreAccount.dueAmt = parseFloat(customerStoreAccount.dueAmt)
				.toFixed(2);
		this.renderAccountHeadDetails(customerStoreAccount);
	}

}

function renderAccountHeadDetails(customerStoreAccount) {

	$('#accountHead\\.locationId').val(customerStoreAccount.locationId);
	$('#accountHead\\.accountId').val(customerStoreAccount.accountId);
	$('#accountHead\\.entityType').val(customerStoreAccount.entityType);
	$('#accountHead\\.entityId').val(customerStoreAccount.entityId);
	$('#accountHead\\.entityName').val(customerStoreAccount.entityName);

	$('#accountHead\\.advanceAmt').val(customerStoreAccount.advanceAmt);
	$('#accountHead\\.dueAmt').val(customerStoreAccount.dueAmt);

	$('#lbl_advanceAmt').text(customerStoreAccount.advanceAmt);
	$('#lbl_dueAmt').text(customerStoreAccount.dueAmt);

	$('#customerAccountDetails').removeClass('d-none');
}
