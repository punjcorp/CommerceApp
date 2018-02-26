var totalAmountVal = 0;
var totalCountVal = 0;

function actionDenomination(tenderId, denominationId, actionParam, formName) {

	$("#selectedTenderId").val(tenderId);
	$("#selectedDenominationId").val(denominationId);
	submitForm(formName, actionParam);
}

function submitForm(formName, actionParam) {
	$('<input />').attr('type', 'hidden').attr('name', actionParam).appendTo(
			'#' + formName);

	$('#' + formName).submit();
}

function changedDenomination(tndrIndex, denomIndex) {
	var denomVal = +$(
			'#tenders' + tndrIndex + '\\.denominations' + denomIndex
					+ '\\.denomination').val();
	var mediaCountVal = +$(
			'#tenders' + tndrIndex + '\\.denominations' + denomIndex
					+ '\\.mediaCount').val();
	var amountVal = denomVal * mediaCountVal;
	amountVal = amountVal.toFixed(2);

	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount')
			.val(amountVal);
	updateTenderTotal(tndrIndex);
}

function changedCount(tndrIndex, denomIndex) {
	var denomVal = +$(
			'#tenders' + tndrIndex + '\\.denominations' + denomIndex
					+ '\\.denomination').val();
	var mediaCountVal = +$(
			'#tenders' + tndrIndex + '\\.denominations' + denomIndex
					+ '\\.mediaCount').val();
	var amountVal = +$(
			'#tenders' + tndrIndex + '\\.denominations' + denomIndex
					+ '\\.amount').val();

	var amountVal = denomVal * mediaCountVal;
	amountVal = amountVal.toFixed(2);

	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount')
			.val(amountVal);
	updateTenderTotal(tndrIndex);

}

function changedAmount(tndrIndex, denomIndex) {
	var denomVal = +$(
			'#tenders' + tndrIndex + '\\.denominations' + denomIndex
					+ '\\.denomination').val();
	var mediaCountVal = +$(
			'#tenders' + tndrIndex + '\\.denominations' + denomIndex
					+ '\\.mediaCount').val();
	var amountVal = +$(
			'#tenders' + tndrIndex + '\\.denominations' + denomIndex
					+ '\\.amount').val();

	amountVal = amountVal.toFixed(2);
	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount')
			.val(amountVal);

	if ((amountVal % denomVal) == 0) {
		$(
				'#tenders' + tndrIndex + '\\.denominations' + denomIndex
						+ '\\.mediaCount').val((amountVal / denomVal));
		updateTenderTotal(tndrIndex);
	} else {
		var amountVal = denomVal * mediaCountVal;
		amountVal = amountVal.toFixed(2);
		$(
				'#tenders' + tndrIndex + '\\.denominations' + denomIndex
						+ '\\.amount').val(amountVal);
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