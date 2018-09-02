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

function updateBusinessDate(cntl){
	var splittedData1;
	var splittedData2;
	var locValue=$(cntl).val();
	var today=new Date();
	today=new Date(today.getFullYear(),today.getMonth(),today.getDate());
	var minDateValue=new Date();
	var parsedDate;
	
	var locbDateValue= $('#'+locValue+'_loc_bdate').val();
	if(locbDateValue!=undefined && locbDateValue!=''){
		splittedData1=locbDateValue.split("-");
		splittedData2=splittedData1[2].split("T");
		parsedDate=new Date(splittedData1[0], splittedData1[1]-1, splittedData2[0]);
		
		if(parsedDate > today)
			minDateValue.setDate(parsedDate.getDate()+1);
		else if (parsedDate.getTime()===today.getTime())
			minDateValue.setDate(today.getDate()+1);
		else
			minDateValue=today;

		
	}else{
		minDateValue=today;
	}
	
	
	bDateCntl.set('minDate', minDateValue);
}


function updateRegChangeValues(regValue){
	var regName=$('#'+regValue+'_reg_name').val();
	$('#registerId').val(regValue);
	$('#registerName').val(regName);
	
	
	
}

function changedDenomination(tndrIndex, denomIndex) {
	var denomVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId').val();
	var denomText = $('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId  option:selected').text();
	denomText=+denomText.replace(/[^0-9.]/gi, '');
	
	var mediaCountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.mediaCount').val();
	var amountVal = denomText * mediaCountVal;
	amountVal = amountVal.toFixed(2);

	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val(amountVal);
	updateTenderTotal(tndrIndex);
}

function changedCount(tndrIndex, denomIndex) {
	var denomVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId').val();
	var denomText = $('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId  option:selected').text();
	denomText=+denomText.replace(/[^0-9.]/gi, '');
	
	var mediaCountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.mediaCount').val();
	var amountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val();

	amountVal = denomText * mediaCountVal;
	amountVal = amountVal.toFixed(2);

	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val(amountVal);
	updateTenderTotal(tndrIndex);

}

function changedAmount(tndrIndex, denomIndex) {
	var denomVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId').val();
	var denomText = $('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId  option:selected').text();
	denomText=+denomText.replace(/[^0-9.]/gi, '');
	
	var mediaCountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.mediaCount').val();
	var amountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val();

	amountVal = amountVal.toFixed(2);
	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val(amountVal);

	if ((amountVal % denomText) == 0) {
		$('#tenders' + tndrIndex + '\\.denominations' + denomIndex	+ '\\.mediaCount').val((amountVal / denomText));
		updateTenderTotal(tndrIndex);
	} else {
		amountVal = denomText * mediaCountVal;
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
	
	var defaultTenderVal=$('#'+locValue+'_loc_default_tender').val();
	var locNameVal=$('#'+locValue+'_loc_name').val();
	var locGstVal=$('#'+locValue+'_gst_no').val();
	
	$('#locationName').val(locNameVal);
	$('#gstNo').val(locGstVal);
	$('#defaultTender').val(defaultTenderVal);
	
	
	var cntl_check=$('#'+locValue+'_loc_status');
	if(cntl_check){
		var locBusinessDate=$('#'+locValue+'_loc_bdate').val();
		var referrerURL=$('#referrerURL').val();
		var locName=$('#'+locValue+'_loc_name').val();
		var gstNo=$('#'+locValue+'_gst_no').val();
		var locDefaultTender=$('#'+locValue+'_loc_default_tender').val();
		var locStatus=cntl_check.val();
		if(locStatus=='OPEN_STORE'){
			url+="="+locValue+"&businessDate="+locBusinessDate+"&locName="+locName+"&gstNo="+gstNo+"&defaultTender="+locDefaultTender+"&referrerURL="+referrerURL;
			window.location.href=url;
		}else{
			$('#btnOpenStore').removeClass("d-none");
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
		var regName=$('#'+regValue+'_reg_name').val();
		var referrerURL=$('#referrerURL').val();
		if(referrerURL=='' ||  referrerURL=='null' || referrerURL=='undefined')
			referrerURL=url+"="+regValue+"&regName="+regName;
		else
			referrerURL=referrerURL+"?registerId="+regValue+"&regName="+regName;
		
		if(locStatus=='OPEN_REGISTER'){
			window.location.href=referrerURL;
		}else{
			$('#btnOpenRegister').removeClass("d-none");
			$('#tenderListContainer').removeClass("d-none");						
		}
	}else{
		$('#tenderListContainer').removeClass("d-none");
	}
	
}

