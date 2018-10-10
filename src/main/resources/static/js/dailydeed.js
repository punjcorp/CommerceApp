var totalAmountVal = 0;
var totalCountVal = 0;
var alertUtil = new Utils();
var locStatus = "";
var token = $("meta[name='_csrf']").attr("content");

function actionDenomination(tenderId, denominationId, actionParam, formName) {

	$("#selectedTenderId").val(tenderId);
	$("#selectedDenominationId").val(denominationId);
	submitForm(formName, actionParam);
}

function submitForm(formName, actionParam) {
	$('<input />').attr('type', 'hidden').attr('name', actionParam).appendTo('#' + formName);
	$('#' + formName).submit();
}

function updateBusinessDate(cntl) {
	var splittedData1;
	var splittedData2;
	var locValue = $(cntl).val();
	var today = new Date();
	today = new Date(today.getFullYear(), today.getMonth(), today.getDate());
	var minDateValue = new Date();
	var parsedDate;

	var locbDateValue = $('#' + locValue + '_loc_bdate').val();
	if (locbDateValue != undefined && locbDateValue != '') {
		splittedData1 = locbDateValue.split("-");
		splittedData2 = splittedData1[2].split("T");
		parsedDate = new Date(splittedData1[0], splittedData1[1] - 1, splittedData2[0]);

		if (parsedDate > today)
			minDateValue.setDate(parsedDate.getDate() + 1);
		else if (parsedDate.getTime() === today.getTime())
			minDateValue.setDate(today.getDate() + 1);
		else
			minDateValue = today;

	} else {
		minDateValue = today;
	}

	bDateCntl.set('minDate', minDateValue);
}

function updateRegChangeValues(regValue) {
	var regName = $('#' + regValue + '_reg_name').val();
	$('#registerId').val(regValue);
	$('#registerName').val(regName);

}

function changedDenomination(tndrIndex, denomIndex) {
	var denomVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId').val();
	var denomText = $('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId  option:selected').text();
	denomText = +denomText.replace(/[^0-9.]/gi, '');

	var mediaCountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.mediaCount').val();
	var amountVal = denomText * mediaCountVal;
	amountVal = amountVal.toFixed(2);

	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val(amountVal);
	updateTenderTotal(tndrIndex);
}

function changedCount(tndrIndex, denomIndex) {
	var denomVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId').val();
	var denomText = $('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.denominationId  option:selected').text();
	denomText = +denomText.replace(/[^0-9.]/gi, '');

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
	denomText = +denomText.replace(/[^0-9.]/gi, '');

	var mediaCountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.mediaCount').val();
	var amountVal = +$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val();

	amountVal = amountVal.toFixed(2);
	$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val(amountVal);

	if ((amountVal % denomText) == 0) {
		$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.mediaCount').val((amountVal / denomText));
		updateTenderTotal(tndrIndex);
	} else {
		amountVal = denomText * mediaCountVal;
		amountVal = amountVal.toFixed(2);
		$('#tenders' + tndrIndex + '\\.denominations' + denomIndex + '\\.amount').val(amountVal);
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
	$('#' + tndrIndex + 'lbltenderAmount').text("INR " + totalAmountVal.toFixed(2));
	$('#tenders' + tndrIndex + '\\.calMCount').val(totalCountVal);
	$('#tenders' + tndrIndex + '\\.calTAmount').val(totalAmountVal.toFixed(2));

}

function checkTotalAgainstStoreOpenAmts(tndrIndex, fieldName) {

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

function processLocationRequest(locValue, url) {

	var defaultTenderVal = $('#' + locValue + '_loc_default_tender').val();
	var locNameVal = $('#' + locValue + '_loc_name').val();
	var locGstVal = $('#' + locValue + '_gst_no').val();

	var actualBusinessDate = $('#businessDate').val();

	$('#locationName').val(locNameVal);
	$('#gstNo').val(locGstVal);
	$('#defaultTender').val(defaultTenderVal);

	var cntl_check = $('#' + locValue + '_loc_status');
	if (cntl_check) {
		var locBusinessDate = $('#' + locValue + '_loc_bdate').val();
		var referrerURL = $('#referrerURL').val();
		var locName = $('#' + locValue + '_loc_name').val();
		var gstNo = $('#' + locValue + '_gst_no').val();
		var locDefaultTender = $('#' + locValue + '_loc_default_tender').val();
		var locChkStatus = cntl_check.val();

		if (locStatus.hasOpen) {
			url += "=" + locValue + "&businessDate=" + actualBusinessDate + "&locName=" + locName + "&gstNo=" + gstNo + "&defaultTender=" + locDefaultTender
					+ "&referrerURL=" + referrerURL;
			window.location.href = url;
		} else {
			$('#btnOpenStore').removeClass("d-none");
			$('#businessDateContainer').removeClass("d-none");
			$('#tenderListContainer').removeClass("d-none");
		}
	} else {
		$('#businessDateContainer').removeClass("d-none");
		$('#tenderListContainer').removeClass("d-none");
	}

}

function processRegisterRequest(regValue, url) {
	var cntl_check = $('#' + regValue + '_reg_status');
	if (cntl_check) {
		var locChkStatus = cntl_check.val();
		var isClosedToday = $('#' + regValue + '_reg_is_closed').val();
		var lastBusinessDate= $('#' + regValue + '_reg_last_business_date').val();
		var businessDate= $('#businessDate').val();
		var regName = $('#' + regValue + '_reg_name').val();
		var referrerURL = $('#referrerURL').val();
		if (referrerURL == '' || referrerURL == 'null' || referrerURL == 'undefined' || referrerURL=='/pos')
			referrerURL = url + "=" + regValue + "&regName=" + regName;
		else
			referrerURL = referrerURL + "?registerId=" + regValue + "&regName=" + regName;

		if (locChkStatus == 'OPEN_REGISTER' && lastBusinessDate==businessDate) {
			window.location.href = referrerURL;
		} else if (typeof (isClosedToday) !== "undefined" && isClosedToday != undefined && isClosedToday == 'true') {
			btnLabels = [ i18next.t('alert_btn_ok'), '' ];
			alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('error_register_already_closed_today'), btnLabels);
		} else {
			$('#btnOpenRegister').removeClass("d-none");
			$('#tenderListContainer').removeClass("d-none");
		}
	} else {
		$('#tenderListContainer').removeClass("d-none");
	}

}

function saveRegister() {

	if (validateNewRegisterDetails()) {
		var locationId = $('#locationId').val();
		var newRegNo = $('#newReg_registerId').val();
		var newRegName = $('#newReg_name').val();
		var token = $("meta[name='_csrf']").attr("content");

		$('#regSaveProgressDiv').removeClass('d-none');
		$('#btnCloseModal').addClass('d-none');

		var newRegister = new Register();
		newRegister.registerId = newRegNo;
		newRegister.name = newRegName;
		newRegister.locationId = locationId;

		var formdata = JSON.stringify(newRegister);
		// AJAX call here and refresh the Expense Screen after the save
		$.ajax({
			url : '/pos/create_new_register',
			type : 'POST',
			cache : false,
			data : formdata,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {

				$('#regSaveProgressDiv').addClass('d-none');
				if (typeof (data) !== "undefined" && data != undefined && data.length < 1) {
					processRegCreationFailure();
				} else {
					processSuccessfulRegCreation(data);
				}
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader('X-CSRF-TOKEN', token)
			}
		});

	}

}

function validateNewRegisterDetails() {
	var result = true;
	var newRegNo = $('#newReg_registerId').val();
	var newRegName = $('#newReg_name').val();

	if (typeof (newRegNo) !== "undefined" && newRegNo != undefined && newRegNo != "") {
		if (isNaN(newRegNo)) {
			$('#newRegNoError').html('<small>The Register No should be between 1 and 999 !!</small>');
			result = false;
		} else {
			newRegNo = +newRegNo;
			if (newRegNo < 1 || newRegNo > 999) {
				$('#newRegNoError').html('<small>The Register No should be between 1 and 999 !!</small>');
				result = false;
			}
			if (typeof (existing_reg_nos) !== "undefined" && existing_reg_nos != undefined && existing_reg_nos.length > 0) {
				if ($.inArray(newRegNo, existing_reg_nos) !== -1) {
					$('#newRegNoError').html('<small>The Register No already exists, please change Register No!!</small>');
					result = false;
				}
			}
		}

	} else {
		$('#newRegNoError').html('<small>The Register No cannot be empty!!</small>');
		result = false;
	}

	if (result)
		$('#newRegNoError').html('');

	if (typeof (newRegName) == "undefined" || newRegName == undefined || newRegName == "") {
		$('#newRegNameError').html('<small>The Register Name cannot be empty!!</small>');
		result = false;
	} else {
		$('#newRegNameError').html('');
	}

	return result;
}

function processSuccessfulRegCreation(data) {

	if (typeof (data.status) !== "undefined" && data.status != undefined && data.status == 'S') {
		$('#commonNewRegError').addClass("text-success");
		$('#commonNewRegError').removeClass("text-danger");
		$('#commonNewRegError').html('<h5>' + data.statusMsg + '</h5>');

		$('#btnSaveRegister').addClass('d-none');
		$('#btnRegOpenProceed').removeClass('d-none');
		$('#btnCloseModal').addClass('d-none');

	} else if (typeof (data.status) !== "undefined" && data.status != undefined && data.status == 'F') {
		$('#commonNewRegError').removeClass("text-success");
		$('#commonNewRegError').addClass("text-danger");

		$('#btnSaveRegister').removeClass('d-none');
		$('#btnRegOpenProceed').addClass('d-none');
		$('#btnCloseModal').removeClass('d-none');
		$('#commonNewRegError').html('<h5>' + data.statusMsg + '!!</h5>');
	}

}

function processRegCreationFailure() {
	$('#commonNewRegError').removeClass("text-success");
	$('#commonNewRegError').addClass("text-danger");

	$('#btnSaveRegister').removeClass('d-none');
	$('#btnRegOpenProceed').addClass('d-none');
	$('#btnCloseModal').removeClass('d-none');
	$('#commonNewRegError').html('<h5>There was some issue while saving Register Details, please contact system administrator!!</h5>');
}

function processSkipOpening(){
	var locValue = $('input:radio[name=locationId]:checked').val();
	var bDateVal = $('#businessDate').val();
	var salesBDate = $('#'+locValue+'_sale_b_date').val();
	var orgSalesBDate= $('#'+locValue+'_sale_b_date').val();
	
	if(typeof (bDateVal) !== "undefined" && bDateVal != undefined && bDateVal != "" &&  typeof (salesBDate) !== "undefined" && salesBDate != undefined && salesBDate != "" ){
		bDateVal=moment(bDateVal, "DD-MMM-YY");
		salesBDate=moment(salesBDate, "DD-MMM-YY");
		if(bDateVal.isBefore(salesBDate)){
			bDateCntl.set("minDate",orgSalesBDate);
		}else{
			if (typeof (locValue) !== "undefined" && locValue != undefined
					&& locValue != "") {
				$('#screenBusyModal').modal({backdrop: 'static', keyboard: false});
				submitForm('openStoreForm','openStore');
			}
			bDateCntl.set("minDate",orgSalesBDate);
		}
	}else{
		bDateCntl.set("minDate",orgSalesBDate);
	}
	
}

function locValChanged(cntl) {
	var skipOpening=$('#skipClosingProcess').val();
	if(skipOpening=='true'){
		processSkipOpening();
	}else{
		var locValue = $(cntl).val();
		var bDateVal = $('#businessDate').val();
		if (typeof (bDateVal) !== "undefined" && bDateVal != undefined && bDateVal != "" && typeof (locValue) !== "undefined" && locValue != undefined
				&& locValue != "") {
			// updateBusinessDate(cntl);
			// processLocationRequest(locValue, url);
			checkLocationStatus(locValue, bDateVal);
		}
	}

}

function bDateValChanged(cntl) {
	var skipOpening=$('#skipClosingProcess').val();
	if(skipOpening=='true'){
		processSkipOpening();
	}else{
		$('#btnOpenStore').addClass("d-none");
		$('#tenderListContainer').addClass("d-none");
		var bDateVal = $('#businessDate').val();
		var locId = $('input:radio[name=locationId]:checked').val();
		if (typeof (bDateVal) !== "undefined" && bDateVal != undefined && bDateVal != "" && typeof (locId) !== "undefined" && locId != undefined && locId != "") {
			checkLocationStatus(locId, bDateVal);
			// processLocationRequest(locValue, url);
		}
	}
	

}

function checkLocationStatus(locationId, businessDate) {
	// $('#screenBusyModal').modal({backdrop: 'static', keyboard: false});

	$.ajax({
		url : '/pos/store_status',
		type : 'GET',
		cache : false,
		async : false,
		data : {
			locationId : locationId,
			businessDate : businessDate
		},
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {

			$('#screenBusyModal').modal('hide');
			if (typeof (data) !== "undefined" && data != undefined && data != "") {
				if (typeof (data.status) !== "undefined" && data.status != undefined && data.status == "S") {
					locStatus = data.resultObj;

					if (locStatus != "") {
						processLocationOpen(locationId, businessDate);
					} else {
						btnLabels = [ i18next.t('alert_btn_ok'), '' ];
						alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('error_store_status_retrieval_failed'), btnLabels);
					}
				} else {
					locStatus = "";
					btnLabels = [ i18next.t('alert_btn_ok'), '' ];
					alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('error_store_status_retrieval_failed'), btnLabels);
				}

			} else {
				btnLabels = [ i18next.t('alert_btn_ok'), '' ];
				alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('error_store_status_retrieval_failed'), btnLabels);
			}
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader('X-CSRF-TOKEN', token)
		}
	});

}

function processLocationOpen(locationId, businessDate) {
	$('#screenBusyModal').modal('hide');
	if (isFutureStatusAllClear()) {

		// hasSales

		if (!locStatus.hasOpen) {
			// Go for normal route to show denominations
			processLocationRequest(locationId, url);

		} else if (locStatus.hasOpen && locStatus.hasClosed) {
			// then reset EOD daily totals
			// reset the store and register status
			// delete last register close and store close txns

			var btnLabels = [ i18next.t('alert_btn_approve'), i18next.t('alert_btn_cancel') ];
			var btnActions = [ 'btnResetTxns', 'btnCancel' ];
			commonUtil.renderAlert('GLOBAL', i18next.t('error_confirmation_alert_header'), i18next.t('warn_txn_eod_reset_confirmation'), btnLabels, btnActions,
					'globalAction');

		} else if (locStatus.hasOpen && !locStatus.hasClosed) {
			// set url for rerouting
			processLocationRequest(locationId, url);
		}

	} else {
		if (locStatus.hasOpenedInFuture && !locStatus.hasSalesInFuture && !locStatus.hasClosedInFuture) {
			// then reset EOD daily totals for selected business date
			// reset the store and register status for selected business date
			// delete last register close and store close txn for selected business date
			// delete all future transactions for the store which are after selected business date

			var btnLabels = [ i18next.t('alert_btn_approve'), i18next.t('alert_btn_cancel') ];
			var btnActions = [ 'btnResetTxns', 'btnCancel' ];
			commonUtil.renderAlert('GLOBAL', i18next.t('error_confirmation_alert_header'), i18next.t('warn_txn_eod_reset_confirmation'), btnLabels, btnActions,
					'globalAction');

		} else if (locStatus.hasOpenedInFuture && locStatus.hasSalesInFuture) {
			// show error
			btnLabels = [ i18next.t('alert_btn_ok'), '' ];
			alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('error_past_date_edit_future_sale_exists'), btnLabels);
		} else if (locStatus.hasOpenedInFuture && !locStatus.hasSalesInFuture && locStatus.hasClosedInFuture) {
			// delete all future transactions starting from selected business date close txns onwards
			var btnLabels = [ i18next.t('alert_btn_approve'), i18next.t('alert_btn_cancel') ];
			var btnActions = [ 'btnResetTxns', 'btnCancel' ];
			commonUtil.renderAlert('GLOBAL', i18next.t('error_confirmation_alert_header'), i18next.t('warn_txn_eod_reset_confirmation'), btnLabels, btnActions,
					'globalAction');
		}
	}
}

function isFutureStatusAllClear() {
	if (!locStatus.hasOpenedInFuture && !locStatus.hasClosedInFuture && !locStatus.hasSalesInFuture)
		return true;
	else
		return false;

}

function resetLocationStatus() {
	var businessDate = $('#businessDate').val();
	var locationId = $('input:radio[name=locationId]:checked').val();
	$('#screenBusyModal').modal({
		backdrop : 'static',
		keyboard : false
	});

	$.get("/pos/reset_store_status", {
		locationId : locationId,
		businessDate : businessDate
	}, function(data, status) {
		$('#screenBusyModal').modal('hide');
		if (typeof (data) !== "undefined" && data != undefined && data != "") {
			if (typeof (data.status) !== "undefined" && data.status != undefined && data.status == "S") {
				// Reroute to the store open url
				window.location.href = store_open_url;
			} else {
				// Show error that reset has failed
				btnLabels = [ i18next.t('alert_btn_ok'), '' ];
				alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('error_store_open_reset_eod_txn_failed'), btnLabels);
			}

		} else {
			// Show error that reset has failed
			btnLabels = [ i18next.t('alert_btn_ok'), '' ];
			alertUtil.renderAlert('SIMPLE', i18next.t('error_simple_alert_header'), i18next.t('error_store_open_reset_eod_txn_failed'), btnLabels);
		}

	});

}
