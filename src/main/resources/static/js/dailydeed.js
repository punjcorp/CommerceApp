var totalAmountVal = 0;
var totalCountVal = 0;

function actionDenomination(tenderId, denominationId, actionParam, formName) {

	$("#selectedTenderId").val(tenderId);
	$("#selectedDenominationId").val(denominationId);
	submitForm(formName, actionParam);
}

function submitForm(formName, actionParam) {
	$('<input />').attr('type', 'hidden').attr('name', actionParam).appendTo('#' + formName);
	$('#' + formName).submit();
}

function changedDenomination(tndrIndex, denomIndex) {
	var denomVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denomination').val();
	var mediaCountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.mediaCount').val();
	var amountVal = denomVal * mediaCountVal;
	amountVal = amountVal.toFixed(2);

	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val(amountVal);
	updateTenderTotal(tndrIndex);
}

function changedCount(tndrIndex, denomIndex) {
	var denomVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denomination').val();
	var mediaCountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.mediaCount').val();
	var amountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val();

	amountVal = denomVal * mediaCountVal;
	amountVal = amountVal.toFixed(2);

	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val(amountVal);
	updateTenderTotal(tndrIndex);

}

function changedAmount(tndrIndex, denomIndex) {
	var denomVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denomination').val();
	var mediaCountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.mediaCount').val();
	var amountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val();

	amountVal = amountVal.toFixed(2);
	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val(amountVal);

	if ((amountVal % denomVal) == 0) {
		$('#tenders' + tndrIndex + '\\.denominations' + denomIndex	+ '\\.mediaCount').val((amountVal / denomVal));
		updateTenderTotal(tndrIndex);
	} else {
		amountVal = denomVal * mediaCountVal;
		amountVal = amountVal.toFixed(2);
		$('#tenders' + tndrIndex + '\\.denominations' + denomIndex+ '\\.amount').val(amountVal);
		alert('The amount should be exact multiple of denomination');
	}

}

function updateTenderTotal(tndrIndex) {
	totalAmountVal = 0;
	totalCountVal = 0;
	$("[id^=tenders" + tndrIndex + "]").each(function() {
		if (this.id.indexOf("mediaCount") >= 0) {
			totalCountVal += +$(this).val();
		}
		if (this.id.indexOf("amount") >= 0) {
			totalAmountVal += +$(this).val();
		}
	});

	$('#' + tndrIndex + 'lblMediaCount').text(totalCountVal);
	$('#' + tndrIndex + 'lbltenderAmount').text("INR "+totalAmountVal.toFixed(2));
	$('#tenders' + tndrIndex + '\\.calMCount').val(totalCountVal);
	$('#tenders' + tndrIndex + '\\.calTAmount').val(totalAmountVal.toFixed(2));	
	
}

function checkTotalAgainstStoreOpenAmts(tndrIndex, fieldName){
	
	$("[id^=tenders" + tndrIndex + "]").each(function() {
		if (this.id.indexOf("mediaCount") >= 0) {
			totalCountVal += +$(this).val();
		}
		if (this.id.indexOf("amount") >= 0) {
			totalAmountVal += +$(this).val();
		}
	});	
	
	$('#tenders' + tndrIndex + '\\.storeOpenMCount').val();
	$('#tenders' + tndrIndex + '\\.storeOpenTAmount').val();	
}

function processLocationRequest(locValue, url){
	var cntl_check=$('#'+locValue+'_loc_status');
	if(cntl_check){
		var locBusinessDate=$('#'+locValue+'_loc_bdate').val();
		var locStatus=cntl_check.val();
		if(locStatus=='OPEN_STORE'){
			url+="="+locValue+"&businessDate="+locBusinessDate;
			window.location.href=url;
		}else{
			$('#businessDateContainer').removeClass("d-none");
			$('#tenderListContainer').removeClass("d-none");						
		}
	}else{
		$('#businessDateContainer').removeClass("d-none");
		$('#tenderListContainer').removeClass("d-none");
	}
	
}


function processRegisterRequest(regValue, url){
	var cntl_check=$('#'+regValue+'_reg_status');
	if(cntl_check){
		var locStatus=cntl_check.val();
		if(locStatus=='OPEN_REGISTER'){
			url+="="+regValue;
			window.location.href=url;
		}else{
			$('#tenderListContainer').removeClass("d-none");						
		}
	}else{
		$('#tenderListContainer').removeClass("d-none");
	}
	
}

