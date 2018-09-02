/**
 * This file contains all the classes needed for a add customer screen
 */

var State = function() {
	this.stateId;
	this.gstStateCode;
	this.name;
	this.shortName;
}

$.extend(State.prototype, {
	initialize : function(gstState) {
		if(gstState.stateId!=undefined)
			this.stateId = gstState.stateId;
		if(gstState.stateCode!=undefined)
			this.gstStateCode = gstState.stateCode.trim();
		if(gstState.stateName!=undefined)
			this.name = gstState.stateName.trim();
		if(gstState.stateShortName!=undefined)
			this.shortName = gstState.stateShortName.trim();
	}
});

var Customer = function() {
	this.customerId;
	this.name;
	this.phone;
	this.phone2;
	this.email;
	this.gstNo;
	this.panNo;
	this.addresses = [];
}

$.extend(Customer.prototype, {

});

var Address = function() {
	this.addressId;
	this.seq;
	this.address1;
	this.address2;
	this.landmark;
	this.city;
	this.district;
	this.state;
	this.country;
	this.pincode;
	this.isPrimary;
	this.addressType;
}

$.extend(Address.prototype, {
	initialize : function(customer_address, seqNo){
		this.addressId=customer_address.addressId;
		this.seq=seqNo;
		this.address1=customer_address.address1;
		this.address2=customer_address.address2;
		this.landmark=customer_address.landmark;
		this.city=customer_address.city;
		this.district=customer_address.district;
		this.state=customer_address.state;
		this.country=customer_address.country;
		this.pincode=customer_address.pincode;
		this.isPrimary=customer_address.primary;
		this.addressType=customer_address.addressType;
		
	},
	render : function() {
		var addressContainerStarts = '<div class="row">';
		addressContainerStarts += '<div class="col-12" id="addressContainer' + this.seq + '">';

		var addressContainerEnds = '</div>';

		var address1Html = '<div class="form-group">';
		address1Html += '<label for="address1"><small><span class="pos-mandatory">' + i18next.t('screeen_lbl_address_line_1') + '</span></small></label>';
		address1Html += '<div class="input-group text-left">';
		address1Html += '<input type="hidden" name="addresses[' + this.seq + '].addressId"';
		if(this.addressId!=undefined && this.addressId!='')
			address1Html += ' value="'+this.addressId+'" ';
		address1Html += 'id="addresses' + this.seq + '.addressId" class="form-control input-sm"></input>';
		address1Html += '<input type="text" onChange="addressChanged(this,' + this.seq + ')" name="addresses[' + this.seq + '].address1"';
		address1Html += 'id="addresses' + this.seq + '.address1" class="form-control input-sm"';
		if(this.address1!=undefined && this.address1!='')
			address1Html += ' value="'+this.address1+'" ';
		address1Html += 'placeholder="' + i18next.t('screeen_lbl_placeholder_address_line_1') + '" maxlength="150"></input>';
		address1Html += '</div>';
		address1Html += '</div>';

		var address2Html = '<div class="row">';

		address2Html += '<div class="col-xs-6 col-sm-6 col-md-6">';
		address2Html += '<div class="form-group">';
		address2Html += '<label for="address2"><small><span>' + i18next.t('screeen_lbl_address_line_2') + '</span></small></label>';
		address2Html += '<div class="input-group text-left">';
		address2Html += '<input type="text" onChange="addressChanged(this,' + this.seq + ')" name="addresses[' + this.seq + '].address2"';
		address2Html += 'id="addresses' + this.seq + '.address2" class="form-control input-sm"';
		if(this.address2!=undefined && this.address2!='')
			address2Html += ' value="'+this.address2+'" ';
		address2Html += 'placeholder="' + i18next.t('screeen_lbl_placeholder_address_line_2') + '" maxlength="150"></input>';
		address2Html += '</div>';
		address2Html += '</div>';
		address2Html += '</div>';
		
		
		var landmarkHtml = '<div class="col-xs-6 col-sm-6 col-md-6">';
		landmarkHtml += '<div class="form-group">';
		landmarkHtml += '<label for="landmark"><small><span>' + i18next.t('screeen_lbl_address_landmark') + '</span></small></label>';
		landmarkHtml += '<div class="input-group text-left">';
		landmarkHtml += '<input type="text" onChange="addressChanged(this,' + this.seq + ')" name="addresses[' + this.seq + '].landmark"';
		landmarkHtml += 'id="addresses' + this.seq + '.landmark" class="form-control input-sm"';
		if(this.landmark!=undefined && this.landmark!='null' &&this.landmark!='')
			landmarkHtml += ' value="'+this.landmark+'" ';
		landmarkHtml += 'placeholder="' + i18next.t('screeen_lbl_placeholder_address_landmark') + '" maxlength="150"></input>';
		landmarkHtml += '</div>';
		landmarkHtml += '</div>';
		landmarkHtml += '</div>';
		
		landmarkHtml += '</div>';

		var cityHtml = '<div class="row">';

		cityHtml += '<div class="col-xs-4 col-sm-4 col-md-4">';
		cityHtml += '<div class="form-group">';
		cityHtml += '<label for="address2"><small><span class="pos-mandatory">' + i18next.t('screeen_lbl_address_city') + '</span></small></label>';
		cityHtml += '<div class="input-group text-left">';
		cityHtml += '<input type="text" onChange="addressChanged(this,' + this.seq + ')" name="addresses[' + this.seq + '].city"';
		cityHtml += 'id="addresses' + this.seq + '.city" class="form-control input-sm"';
		if(this.city!=undefined && this.city!='')
			cityHtml += ' value="'+this.city+'" ';
		cityHtml += 'placeholder="' + i18next.t('screeen_lbl_placeholder_address_city') + '" maxlength="30"></input>';
		cityHtml += '</div>';
		cityHtml += '</div>';
		cityHtml += '</div>';
		
		
		var districtHtml = '<div class="col-xs-4 col-sm-4 col-md-4">';
		districtHtml += '<div class="form-group">';
		districtHtml += '<label for="district"><small><span>' + i18next.t('screeen_lbl_address_district') + '</span></small></label>';
		districtHtml += '<div class="input-group text-left">';
		districtHtml += '<input type="text" onChange="addressChanged(this,' + this.seq + ')" name="addresses[' + this.seq + '].district"';
		districtHtml += 'id="addresses' + this.seq + '.district" class="form-control input-sm"';
		if(this.district!=undefined && this.district!='null' &&this.district!='')
			districtHtml += ' value="'+this.district+'" ';
		districtHtml += 'placeholder="' + i18next.t('screeen_lbl_placeholder_address_district') + '" maxlength="50"></input>';
		districtHtml += '</div>';
		districtHtml += '</div>';
		districtHtml += '</div>';
		

		var gstStatesHtml = '<div class="col-xs-4 col-sm-4 col-md-4">';
		gstStatesHtml += '<div class="form-group text-left">';
		gstStatesHtml += '<label for="state"><small><span class="pos-mandatory">' + i18next.t('screeen_lbl_address_state') + '</span></small></label>';
		gstStatesHtml += '<div class="input-group">';
		gstStatesHtml += '<select onChange="addressChanged(this,' + this.seq + ')" name="addresses[' + this.seq + '].state"';
		gstStatesHtml += 'id="addresses' + this.seq + '.state" class="form-control input-sm">';
		if(this.state==undefined || this.state=='')
			gstStatesHtml += '<option class="form-text text-muted" value="" selected>' + i18next.t('screeen_lbl_placeholder_address_state') + '</option>';
		else
			gstStatesHtml += '<option class="form-text text-muted" value="">' + i18next.t('screeen_lbl_placeholder_address_state') + '</option>';

		var objState=this.state;
		$.each(gstStates, function(index, gstState) {
			if(objState!=undefined && objState!='' && objState==gstState.shortName)
				gstStatesHtml += '<option class="form-text" value="' + gstState.shortName + '" selected>' + gstState.gstStateCode + ' - ' + gstState.name
				+ '</option>';
			else
				gstStatesHtml += '<option class="form-text" value="' + gstState.shortName + '">' + gstState.gstStateCode + ' - ' + gstState.name
					+ '</option>';
		});

		gstStatesHtml += '</select>';
		gstStatesHtml += '</div>';
		gstStatesHtml += '</div>';
		gstStatesHtml += '</div>';

		gstStatesHtml += '</div>';

		var countryHtml = '<div class="row">';

		countryHtml += '<div class="col-xs-6 col-sm-6 col-md-6">';
		countryHtml += '<div class="form-group">';
		countryHtml += '<label for="country"><small><span class="pos-mandatory">' + i18next.t('screeen_lbl_address_country') + '</span></small></label>';
		countryHtml += '<div class="input-group text-left">';
		countryHtml += '<input type="text" onChange="addressChanged(this,' + this.seq + ')" name="addresses[' + this.seq + '].country"';
		countryHtml += 'id="addresses' + this.seq + '.country" class="form-control input-sm"';
		if(this.country!=undefined && this.country!='')
			countryHtml += ' value="'+this.country+'" ';
		countryHtml += 'placeholder="' + i18next.t('screeen_lbl_placeholder_address_country') + '" maxlength="45"></input>';
		countryHtml += '</div>';
		countryHtml += '</div>';
		countryHtml += '</div>';

		var pinCodeHtml = '<div class="col-xs-6 col-sm-6 col-md-6">';
		pinCodeHtml += '<div class="form-group">';
		pinCodeHtml += '<label for="pincode"><small><span class="pos-mandatory">' + i18next.t('screeen_lbl_address_pincode') + '</span></small></label>';
		pinCodeHtml += '<div class="input-group text-left">';
		pinCodeHtml += '<input type="text" onChange="addressChanged(this,' + this.seq + ')" name="addresses[' + this.seq + '].pincode"';
		pinCodeHtml += 'id="addresses' + this.seq + '.pincode" class="form-control input-sm"';
		if(this.pincode!=undefined && this.pincode!='')
			pinCodeHtml += ' value="'+this.pincode+'" ';
		pinCodeHtml += 'placeholder="' + i18next.t('screeen_lbl_placeholder_address_pincode') + '" maxlength="6"></input>';
		pinCodeHtml += '</div>';
		pinCodeHtml += '</div>';
		pinCodeHtml += '</div>';

		pinCodeHtml += '</div>';

		var primaryHtml = '<div class="row">';

		primaryHtml += '<div class="col-xs-3 col-sm-3 col-md-3">';
		primaryHtml += '<div class="form-group">';
		primaryHtml += '<label for="primary"><small><span></span></small></label>';
		primaryHtml += '<div class="input-group text-left">';
		primaryHtml += '<div class="form-check text-center">';
		primaryHtml += '<input type="radio" onChange="addressChanged(this,' + this.seq + ')" id="primaryAddressIndex"';
		primaryHtml += 'name="primaryAddressIndex"';
		if(this.isPrimary!=undefined && this.isPrimary!='' && this.isPrimary=='Y')
			primaryHtml += ' checked ';
		
		primaryHtml += 'value="' + this.seq + '" class="form-check-input"></input>';
		primaryHtml += '<label class="form-check-label"><span>' + i18next.t('screeen_lbl_address_primary') + '</span></label>';
		primaryHtml += '</div>';
		primaryHtml += '</div>';
		primaryHtml += '</div>';
		primaryHtml += '</div>';

		var addressTypeHtml = '<div class="col-xs-3 col-sm-3 col-md-3">';
		addressTypeHtml += '<div class="form-group">';
		addressTypeHtml += '<label for="addressType"><small><span class="pos-mandatory">' + i18next.t('screeen_lbl_address_type_selection')
				+ '</span></small></label>';
		addressTypeHtml += '<div class="input-group text-left">';
		addressTypeHtml += '<select onChange="addressChanged(this,' + this.seq + ')" name="addresses[' + this.seq + '].addressType"';
		addressTypeHtml += 'id="addresses' + this.seq + '.addressType" class="form-control input-sm">';
		addressTypeHtml += '<option value="Work"';
		if(this.addressType!=undefined && this.addressType!='' && this.addressType=='Work')
			addressTypeHtml += ' selected ';
		addressTypeHtml += '>' + i18next.t('screeen_lbl_address_type_work') + '</option>';
		addressTypeHtml += '<option value="Warehouse"';
		if(this.addressType!=undefined && this.addressType!='' && this.addressType=='Warehouse')
			addressTypeHtml += ' selected ';
		addressTypeHtml += '>' + i18next.t('screeen_lbl_address_type_warehouse') + '</option>';
		addressTypeHtml += '<option value="Home"';
		if(this.addressType!=undefined && this.addressType!='' && this.addressType=='Home')
			addressTypeHtml += ' selected ';
		addressTypeHtml += '>' + i18next.t('screeen_lbl_address_type_home') + '</option>';
		
		addressTypeHtml += '</select>';
		addressTypeHtml += '</div>';
		addressTypeHtml += '</div>';
		addressTypeHtml += '</div>';

		var addressBtns = '<div class="col-xs-6 col-sm-6 col-md-6">';
		addressBtns += '<div class="form-group">';
		addressBtns += '<label for="primary"><small><span></span></small></label>';
		addressBtns += '<div class="input-group text-left">';
		addressBtns += '<button type="button" onclick="addAddress(\'' + this.seq + '\')" class="btn btn-secondary mr-2">';
		addressBtns += '<span class="fa-layers fa-fw fa-2x mr-3">';
		addressBtns += ' <i class="fas fa-address-card"></i>';
		addressBtns += '<i class="fas fa-plus" data-fa-transform="shrink-10 right-14"></i>';
		addressBtns += '</span>';
		addressBtns += '<span>' + i18next.t('screeen_lbl_btn_add_address') + '</span>';
		addressBtns += '</button>';
		addressBtns += '<button type="button" onclick="deleteAddress(\'' + this.seq + '\')" class="btn btn-secondary mr-2">';
		addressBtns += '<span class="fa-layers fa-fw fa-2x mr-3">';
		addressBtns += '<i class="fas fa-address-card"></i>';
		addressBtns += '<i class="fas fa-times" data-fa-transform="shrink-10 right-14"></i>';
		addressBtns += '</span>';
		addressBtns += '<span>' + i18next.t('screeen_lbl_btn_delete_address') + '</span>';
		addressBtns += '</button>';
		addressBtns += '</div>';
		addressBtns += '</div>';
		addressBtns += '</div>';

		addressBtns += '</div>';
		
		var finalHtml= addressContainerStarts + address1Html+ address2Html + landmarkHtml + cityHtml+ districtHtml+gstStatesHtml;
		finalHtml += countryHtml + pinCodeHtml+primaryHtml+ addressTypeHtml + addressBtns;
		
		return finalHtml;

	}

});
