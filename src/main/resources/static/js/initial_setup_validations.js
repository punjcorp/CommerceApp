/**
 * This JS file has all the code needed for initial setup screen
 */


function validateStep(stepId){
	if(typeof(stepId)!=='undefined' && stepId!=undefined){
		switch(stepId){
		case 'step1':
			return validateLocationDetails();
		case 'step2':
			return validateLocationAddress();
		case 'step3':
			return validateManagerDetails();
		case 'step4':
			return validateAdminDetails();			
		}
	}
	
}

function validateLocationDetails(){
	var validationPassed = true;
	
	var locationIdValidation = $('#location\\.locationId').parsley({
		maxlength : 4,
		minlength : 1,
		required: ''
	});
	if (locationIdValidation.isValid()) {
		$('#location\\.locationId').removeClass('is-invalid');
		$('#locationIdMsg').hide();
	} else {
		validationPassed = false;
		$('#location\\.locationId').addClass('is-invalid');
		$('#locationIdMsg').addClass('invalid-feedback');
		if(locationIdValidation.validationResult[0].assert.name=='required'){
			$('#locationIdMsg').html('<h6>Location# cannot be empty!</h6>');
		}else{
			$('#locationIdMsg').html('<h6>Location# should be between 1 and 9998 numbers!</h6>');
		}
		$('#locationIdMsg').show();
	}	
	
	var locationNameValidation = $('#location\\.name').parsley({
		maxlength : 100,
		minlength : 3,
		required: ''
	});
	if (locationNameValidation.isValid()) {
		$('#location\\.name').removeClass('is-invalid');
		$('#locationNameMsg').hide();
	} else {
		validationPassed = false;
		$('#location\\.name').addClass('is-invalid');
		$('#locationNameMsg').addClass('invalid-feedback');
		if(locationNameValidation.validationResult[0].assert.name=='required'){
			$('#locationNameMsg').html('<h6>Location Name cannot be empty!</h6>');
		}else{
			$('#locationNameMsg').html('<h6>Location# should be between 3 and 100 characters!</h6>');
		}
		$('#locationNameMsg').show();
	}

	
	var locationDescValidation = $('#location\\.description').parsley({
		maxlength : 150
	});
	if (locationDescValidation.isValid()) {
		$('#location\\.description').removeClass('is-invalid');
		$('#locationDescMsg').hide();
	} else {
		validationPassed = false;
		$('#location\\.description').addClass('is-invalid');
		$('#locationDescMsg').addClass('invalid-feedback');
		$('#locationDescMsg').html('<h6>Location Description should not be more than 150 characters!</h6>');
		$('#locationDescMsg').show();
	}
	
	var selectedTendersValid = $('#selectedTenders').parsley({
		required: ''
	});
	if (selectedTendersValid.isValid()) {
		$('#selectedTenders').removeClass('is-invalid');
		$('#selectedTendersMsg').hide();
	} else {
		validationPassed = false;
		$('#selectedTenders').addClass('is-invalid');
		$('#selectedTendersMsg').addClass('invalid-feedback');
		$('#selectedTendersMsg').html('<h6>Atleast one tender should be selected!</h6>');
		$('#selectedTendersMsg').show();
	}
	
	var gstNoValid = $('#location\\.gstNo').parsley({
		maxLength: 15,
		minLength: 15
	});
	if (gstNoValid.isValid()) {
		$('#location\\.gstNo').removeClass('is-invalid');
		$('#gstNoMsg').hide();
	} else {
		validationPassed = false;
		$('#location\\.gstNo').addClass('is-invalid');
		$('#gstNoMsg').addClass('invalid-feedback');
		$('#gstNoMsg').html('<h6>Enter an valid 15 characters long GST Number!</h6>');
		$('#gstNoMsg').show();
	}
	
	
	return validationPassed;
	
}

function validateLocationAddress(){
	var validationPassed = true;
	
	var address1Valid= $('#location\\.address1').parsley({
		maxlength : 150,
		minlength : 4,
		required: ''
	});
	if (address1Valid.isValid()) {
		$('#location\\.address1').removeClass('is-invalid');
		$('#address1Msg').hide();
	} else {
		validationPassed = false;
		$('#location\\.address1').addClass('is-invalid');
		$('#address1Msg').addClass('invalid-feedback');
		if(address1Valid.validationResult[0].assert.name=='required'){
			$('#address1Msg').html('<h6>Address Line 1 should not be empty!</h6>');
		}else{
			$('#address1Msg').html('<h6>Address Line 1 should be between 4 and 150 characters long!</h6>');
		}
		$('#address1Msg').show();
	}	
	
	var address2Valid= $('#location\\.address2').parsley({
		maxlength : 150,
		minlength : 4,
	});
	if (address2Valid.isValid()) {
		$('#location\\.address2').removeClass('is-invalid');
		$('#address2Msg').hide();
	} else {
		validationPassed = false;
		$('#location\\.address2').addClass('is-invalid');
		$('#address2Msg').addClass('invalid-feedback');
		$('#address2Msg').html('<h6>Address Line 2 should be between 4 and 150 characters long!</h6>');
		$('#address2Msg').show();
	}	
	
	var cityValid= $('#location\\.city').parsley({
		maxlength : 30,
		minlength : 4,
		required: ''
	});
	if (cityValid.isValid()) {
		$('#location\\.city').removeClass('is-invalid');
		$('#cityMsg').hide();
	} else {
		validationPassed = false;
		$('#location\\.city').addClass('is-invalid');
		$('#cityMsg').addClass('invalid-feedback');
		if(cityValid.validationResult[0].assert.name=='required'){
			$('#cityMsg').html('<h6>The City should not be empty!</h6>');
		}else{
			$('#cityMsg').html('<h6>The City should be between 4 and 30 characters long!</h6>');
		}
		$('#cityMsg').show();
	}
	
	var stateValid= $('#location\\.state').parsley({
		required: ''
	});
	if (stateValid.isValid()) {
		$('#location\\.state').removeClass('is-invalid');
		$('#stateMsg').hide();
	} else {
		validationPassed = false;
		$('#location\\.state').addClass('is-invalid');
		$('#stateMsg').addClass('invalid-feedback');
		$('#stateMsg').html('<h6>The State should not be empty!</h6>');
		$('#stateMsg').show();
	}
	
	
	var countryValid= $('#location\\.country').parsley({
		required: ''
	});
	if (countryValid.isValid()) {
		$('#location\\.country').removeClass('is-invalid');
		$('#countryMsg').hide();
	} else {
		validationPassed = false;
		$('#location\\.country').addClass('is-invalid');
		$('#countryMsg').addClass('invalid-feedback');
		$('#countryMsg').html('<h6>The Country should not be empty!</h6>');
		$('#countryMsg').show();
	}
	
	var pincodeValid= $('#location\\.pincode').parsley({
		maxlength : 6,
		minlength : 6,
		required: ''
	});
	if (pincodeValid.isValid()) {
		$('#location\\.pincode').removeClass('is-invalid');
		$('#pincodeMsg').hide();
	} else {
		validationPassed = false;
		$('#location\\.pincode').addClass('is-invalid');
		$('#pincodeMsg').addClass('invalid-feedback');
		if(pincodeValid.validationResult[0].assert.name=='required'){
			$('#pincodeMsg').html('<h6>The Pincode should not be empty!</h6>');
		}else{
			$('#pincodeMsg').html('<h6>The Valid Pincode should be of 6 digits long!</h6>');
		}
		$('#pincodeMsg').show();
	}
	
	return validationPassed;
	
}



function validateManagerDetails(){
	var validationPassed = true;
	
	var managerNameValidation = $('#location\\.manager').parsley({
		maxlength : 80,
		minlength : 5,
		required: ''
	});
	if (managerNameValidation.isValid()) {
		$('#location\\.manager').removeClass('is-invalid');
		$('#managerMsg').hide();
	} else {
		validationPassed = false;
		$('#location\\.manager').addClass('is-invalid');
		$('#managerMsg').addClass('invalid-feedback');
		if(managerNameValidation.validationResult[0].assert.name=='required'){
			$('#managerMsg').html('<h6>Location Manager Name cannot be empty!</h6>');
		}else{
			$('#managerMsg').html('<h6>Location Manager Name should be between 5 and 80 characters!</h6>');
		}
		$('#managerMsg').show();
	}	
	
	var managerEmailValidation = $('#location\\.email').parsley({
		maxlength : 150,
		minlength : 5,
		required: ''
	});
	if (managerEmailValidation.isValid()) {
		$('#location\\.email').removeClass('is-invalid');
		$('#locationEmailMsg').hide();
	} else {
		validationPassed = false;
		$('#location\\.email').addClass('is-invalid');
		$('#locationEmailMsg').addClass('invalid-feedback');
		if(managerEmailValidation.validationResult[0].assert.name=='required'){
			$('#locationEmailMsg').html('<h6>Location Email cannot be empty!</h6>');
		}else if(managerEmailValidation.validationResult[0].assert.name=='type'){
			$('#locationEmailMsg').html('<h6>Enter an valid email!</h6>');
		}else{
			$('#locationEmailMsg').html('<h6>Location Email should be between 5 and 150 characters!</h6>');
		}
		$('#locationEmailMsg').show();
	}

	
	var locationPhone1Validation = $('#location\\.telephone1').parsley({
		maxlength : 15,
		minlength : 7,
		required: ''
	});
	if (locationPhone1Validation.isValid()) {
		$('#location\\.telephone1').removeClass('is-invalid');
		$('#locationPhone1Msg').hide();
	} else {
		validationPassed = false;
		$('#location\\.telephone1').addClass('is-invalid');
		$('#locationPhone1Msg').addClass('invalid-feedback');
		if(locationPhone1Validation.validationResult[0].assert.name=='required'){
			$('#locationPhone1Msg').html('<h6>Phone 1 cannot be empty!</h6>');
		}else{
			$('#locationPhone1Msg').html('<h6>Phone 1 should be between 7 and 15 characters!</h6>');
		}
		$('#locationPhone1Msg').show();
	}
	
	var locationPhone2Validation = $('#location\\.telephone2').parsley({
		maxlength : 15,
		minlength : 7,
	});
	if (locationPhone2Validation.isValid()) {
		$('#location\\.telephone2').removeClass('is-invalid');
		$('#locationPhone2Msg').hide();
	} else {
		validationPassed = false;
		$('#location\\.telephone2').addClass('is-invalid');
		$('#locationPhone2Msg').addClass('invalid-feedback');
		$('#locationPhone2Msg').html('<h6>Phone 1 should be between 7 and 15 characters!</h6>');
		$('#locationPhone2Msg').show();
	}
	
	
	return validationPassed;
	
}



function validateAdminDetails(){
	var validationPassed = true;
	
	var passwordValidation = $('#account\\.password').parsley({
		maxlength : 30,
		minlength : 6,
		required: ''
	});
	if (passwordValidation.isValid()) {
		$('#account\\.password').removeClass('is-invalid');
		$('#passwordMsg').hide();
	} else {
		validationPassed = false;
		$('#account\\.password').addClass('is-invalid');
		$('#passwordMsg').addClass('invalid-feedback');
		if(passwordValidation.validationResult[0].assert.name=='required'){
			$('#passwordMsg').html('<h6>Password cannot be empty!</h6>');
		}else{
			$('#passwordMsg').html('<h6>Password should be between 6 and 30 characters!</h6>');
		}
		$('#passwordMsg').show();
	}	
	
	var confirmPasswordValidation = $('#account\\.confirmPassword').parsley({
		maxlength : 30,
		minlength : 6,
		required: ''
	});
	
	if (confirmPasswordValidation.isValid()) {
		$('#account\\.confirmPassword').removeClass('is-invalid');
		$('#confirmPasswordMsg').hide();
	} else {
		validationPassed = false;
		$('#account\\.confirmPassword').addClass('is-invalid');
		$('#confirmPasswordMsg').addClass('invalid-feedback');
		if(confirmPasswordValidation.validationResult[0].assert.name=='required'){
			$('#confirmPasswordMsg').html('<h6>Password cannot be empty!</h6>');
		}else{
			$('#confirmPasswordMsg').html('<h6>Password should be between 6 and 30 characters!</h6>');
		}
		$('#confirmPasswordMsg').show();
	}	
	var passwordText=$('#account\\.password').val();
	var confirmPasswordText=$('#account\\.confirmPassword').val();
	if(passwordText!==confirmPasswordText){
		validationPassed = false;
		$('#account\\.confirmPassword').addClass('is-invalid');
		$('#confirmPasswordMsg').addClass('invalid-feedback');
		$('#confirmPasswordMsg').html('<h6>The confirmed password is not matching!</h6>');
		$('#confirmPasswordMsg').show();
	}else{
		$('#account\\.confirmPassword').removeClass('is-invalid');
		$('#confirmPasswordMsg').hide();
	}
		
	
	return validationPassed;
	
}