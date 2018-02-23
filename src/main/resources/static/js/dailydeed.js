var totalAmountVal=0;
var totalCountVal=0;

$(function() {

	$('#businessDate').flatpickr({
		dateFormat : 'd-M-y',
	});
});

function actionDenomination(tenderId, denominationId, actionParam) {

	$("#selectedTenderId").val(tenderId);
	$("#selectedDenominationId").val(denominationId);
	submitForm(actionParam);
}

function submitForm(actionParam) {
	$('<input />').attr('type', 'hidden').attr('name', actionParam).appendTo(
			'#openStoreForm');

	$('#openStoreForm').submit();
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
	totalAmountVal=0;
	totalCountVal=0;
	$("[id^=tenders" + tndrIndex + "]").each(function() {
		if (this.id.indexOf("mediaCount") >= 0) {
			totalCountVal += +$(this).val();
		}
		if (this.id.indexOf("amount") >= 0) {
			totalAmountVal += +$(this).val();
		}
	});

	$('#' + tndrIndex + 'lblMediaCount').text(totalCountVal);
	$('#' + tndrIndex + 'lbltenderAmount').text(totalAmountVal.toFixed(2));
}