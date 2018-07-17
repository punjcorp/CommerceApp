/**
 * 
 */

function associateCustomerToTxn(){
	var customerType='CUSTOMER';
	var customerId=$('#h_customerId').val();
	
	var customerName='undefined';
	var customerPhone='undefined';
	var customerEmail='undefined';
	
	if(customerId === undefined || customerId == null || customerId.length <= 0){
		if(validateCustomer()){
			customerName=$('#name').val();
			customerPhone=$('#phone').val();
			customerEmail=$('#email').val();
		}else{
			return;
		}

	}else{
		customerName=$('#h_customerName').val();
		customerPhone=$('#h_customerPhone').val();
		customerEmail=$('#h_customerEmail').val();
		
	}	
	
	// Assign data to JS class
	txnAction.associateCustomer(customerId,customerType,customerName,customerPhone,customerEmail);
	$('#txnCustomerModal').modal('hide');
	
	btnLabels = [ i18next.t('alert_btn_ok'), '' ];
	alertUtil.renderAlert('SUCCESS', i18next.t('error_simple_success_header'), i18next.t('customer_add_success'), btnLabels);
	
}


function validateCustomer(){
	
	var validationPassed = true;
	
	var customerNameValidation = $('#name').parsley({
		maxlength : 80,
		minlength : 4,
		required: ''
	});
	if (customerNameValidation.isValid()) {
		$('#name').removeClass('is-invalid');
		$('#nameMsg').hide();
	} else {
		validationPassed = false;
		$('#name').addClass('is-invalid');
		$('#nameMsg').addClass('invalid-feedback');
		if(customerNameValidation.validationResult[0].assert.name=='required'){
			$('#nameMsg').html('<h6>Please enter the Customer Name!</h6>');
		}else{
			$('#nameMsg').html('<h6>Customer Name should be between 4 and 80 characters!</h6>');
		}
		$('#nameMsg').show();
	}	
	
	
	var customerPhoneValidation = $('#phone').parsley({
		minlength: 7,
		maxlength : 12,
		required: ''
	});
	if (customerPhoneValidation.isValid()) {
		$('#phone').removeClass('is-invalid');
		$('#phoneMsg').hide();
	} else {
		validationPassed = false;
		$('#phone').addClass('is-invalid');
		$('#phoneMsg').addClass('invalid-feedback');
		if(customerPhoneValidation.validationResult[0].assert.name=='required'){
			$('#phoneMsg').html('<h6>Please enter the Customer Phone!</h6>');
		}else if(customerPhoneValidation.validationResult[0].assert.name=='maxlength'){
			$('#phoneMsg').html('<h6>Customer Phone cannot be more than 12 digits!</h6>');
		}else if(customerPhoneValidation.validationResult[0].assert.name=='minlength'){
			$('#phoneMsg').html('<h6>Customer Phone should be minimum of 7 digits!</h6>');
		}
		
		$('#phoneMsg').show();
	}
	
	
	var customerEmailValidation = $('#email').parsley({
		maxlength : 80,
		minlength: 5
	});
	if (customerEmailValidation.isValid()) {
		$('#email').removeClass('is-invalid');
		$('#emailMsg').hide();
	} else {
		validationPassed = false;
		$('#email').addClass('is-invalid');
		$('#emailMsg').addClass('invalid-feedback');
		if(customerEmailValidation.validationResult[0].assert.name=='type'){
			$('#emailMsg').html('<h6>Please enter a valid Email!</h6>');
		}else{
			$('#emailMsg').html('<h6>Customer Email should be between 5 and 80 characters!</h6>');
		}
		$('#emailMsg').show();
	}
	
	return validationPassed;
}


function showCustomerDetails(event, ui){
	
	$('#div_customerSearch').addClass('d-none');
	
	var customerName='';
	customerName +='<div class="row">';
	customerName +='<div class="col text-left">';
	customerName +='<label for="name"><small><span class="pos-mandatory">Customer Name</span></small></label>';
	customerName +='<div class="input-group text-left">';
	customerName +='<h5><span>'+ui.item.customerId+' - '+ui.item.name+'</span></h5>';
	customerName +='<input type="hidden" id="h_customerId" value="'+ui.item.customerId+'"></input>';
	customerName +='<input type="hidden" id="h_customerName" value="'+ui.item.name+'"></input>';
	customerName +='</div>';
	customerName +='</div>';
	customerName +='</div>';
			
	
	var customerPhone='';
	customerPhone +='<div class="row">';
	customerPhone +='<div class="col text-left">';
	customerPhone +='<label for="name"><small><span class="pos-mandatory">Customer Phone</span></small></label>';
	customerPhone +='<div class="input-group text-left">';
	customerPhone +='<h5><span>'+ui.item.phone+'</span></h5>';
	customerPhone +='<input type="hidden" id="h_customerPhone" value="'+ui.item.phone+'"></input>';
	customerPhone +='</div>';
	customerPhone +='</div>';
	customerPhone +='</div>';

	var customerEmail='';
	customerEmail +='<div class="row">';
	customerEmail +='<div class="col text-left">';
	customerEmail +='<label for="name"><small><span >Customer Email</span></small></label>';
	customerEmail +='<div class="input-group text-left">';
	customerEmail +='<h5><span>'+ui.item.email+'</span></h5>';
	customerEmail +='<input type="hidden" id="h_customerEmail" value="'+ui.item.email+'"></input>';
	customerEmail +='</div>';
	customerEmail +='</div>';
	customerEmail +='</div>';	
	
	
	var customerDetails=customerName+customerPhone+customerEmail;
	$('#div_customerResult').html(customerDetails);
	
	$('#div_customerActions').removeClass('d-none');
	
}