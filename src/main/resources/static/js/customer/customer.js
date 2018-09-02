/**
 * These are the global variables for customer screen
 */
var token = $("meta[name='_csrf']").attr("content");
var gstStates = new Array();
var addresses = new Array();


$(function() {

	$('#btnAddCustomerAddress').click(function() {
		addAddress();
	});

	updateGSTStates();
	
	if(typeof(customer_addresses)!=="undefined" && customer_addresses!=undefined && customer_addresses.length>0){
		updateAddresses();
	}

});

function addAddress(seqNo){
	var address = new Address();
	address.isPrimary="N";
	address.addressType="Work";
	if(addresses.length>0){
		address.seq=addresses.length;
	}else{
		address.seq=0;
	}
	addresses.push(address);
	renderAllAddresses();
}

function deleteAddress(seqNo){
	addresses.splice(seqNo,1);
	renderAllAddresses();
}

function renderAllAddresses() {
	if(addresses.length>0){
		var renderHtml='';
		$.each(addresses, function(index, address){
			address.seq=index;
			renderHtml += address.render();
		});
		$('#addressesContainer').html(renderHtml);
		$('#addressesContainer').removeClass('d-none');
		$('#btnAddCustomerAddress').addClass('d-none');
	}else{
		$('#addressesContainer').html(i18next.t('screeen_error_zero_address'));
		$('#btnAddCustomerAddress').removeClass('d-none');
	}
	
}

function updateAddresses() {
	if (customer_addresses != undefined && customer_addresses.length > 0) {
		var addressObj;
		$.each(customer_addresses, function(index, customer_address) {
			addressObj = new Address();
			addressObj.initialize(customer_address, index);
			addresses.push(addressObj);
		});
	}
}

function updateGSTStates() {

	if (typeof(gst_states)!=="undefined" && gst_states != undefined && gst_states.length > 0) {
		var stateObj;
		$.each(gst_states, function(index, gst_state) {
			stateObj = new State();
			stateObj.initialize(gst_state);
			gstStates.push(stateObj);
		});
	}

}


function addressChanged(cntl,seqNo){

	var cntlId=cntl.id;
	var cntlVal=cntl.value.trim();
	
	if(addresses.length>seqNo){
		var addressToBeUpdated=addresses[seqNo]
		if(cntlId.indexOf('addressId')>0){
			addressToBeUpdated.addressId=cntlVal;
		} else if(cntlId.indexOf('address1')>0){
			addressToBeUpdated.address1=cntlVal;
		}else if(cntlId.indexOf('address2')>0){
			addressToBeUpdated.address2=cntlVal;
		}else if(cntlId.indexOf('landmark')>0){
			addressToBeUpdated.landmark=cntlVal;
		}else if(cntlId.indexOf('city')>0){
			addressToBeUpdated.city=cntlVal;
		}else if(cntlId.indexOf('district')>0){
			addressToBeUpdated.district=cntlVal;
		}else if(cntlId.indexOf('state')>0){
			addressToBeUpdated.state=cntlVal;
		}else if(cntlId.indexOf('country')>0){
			addressToBeUpdated.country=cntlVal;
		}else if(cntlId.indexOf('pincode')>0){
			addressToBeUpdated.pincode=cntlVal;
		}else if(cntlId.indexOf('addressType')>0){
			addressToBeUpdated.addressType=cntlVal;
		}
		
		var isPrimary='N';
		if($('#addressContainer'+seqNo).find('#primaryAddressIndex').is(':checked'))
			isPrimary='Y';
		addressToBeUpdated.isPrimary=isPrimary;
		
	}
}
