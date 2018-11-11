/**
 * The global variables are defined in this section
 */

var tli_index = 0;
var tenderLineItems = new Array();

var ConfigBean= function() {
	this.configKey;
	this.configValue;
}

$.extend(ConfigBean.prototype, {
	updateConfig : function(configKey, configValue) {
		this.configKey=configKey;
		this.configValue=configValue;
	}
});


var TourHelp= function() {

}

$.extend(TourHelp.prototype, {
	checkTourFlag : function() {
		if(typeof(tour_help_flag)!=='undefined' && tour_help_flag!=undefined && tour_help_flag=='true')
			return true;
		/*else if(typeof(tour_help_flag)==='undefined' || tour_help_flag==undefined )
			return true;*/
		else
			return false;
	},
	disableTourFlag : function(){
		var configBean=new ConfigBean();
		configBean.updateConfig('app.tour.flag', 'false');
		var formdata= JSON.stringify(configBean);
		
		if(typeof(token)!=='undefined' && token!=undefined && token!=''){
			$.ajax({
				url : '/disable_global_config',
				type : 'POST',
				cache : false,
				data : formdata,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					
					if(typeof(data)!=='undefined' && data!=undefined && data.status=='S')
						console.log('The auto tour help has been disabled now!');
					
				},				
				beforeSend : function(xhr) {
					xhr.setRequestHeader('X-CSRF-TOKEN', token)
				}
			});		
		}else{
			console.log('There is no token existing for AJAX POST call');
		}
		
		
	}
});


var TenderLineItem = function() {
	this.tenderId;
	this.index;
	this.name = '';
	this.amount = 0.00;
}

$.extend(TenderLineItem.prototype, {
	addTenderLineItem : function() {
		tenderLineItems.push(this);
	},
	renderTenderLineItem : function() {
		tli_index += 1;

		if (tli_index > 0) {
			$('#tenderLineItemContainer').removeClass('d-none');
		}

		var htmlContent = '<div class="row" id="' + tli_index + 'tenderLineItem">';
		htmlContent += '<div class="col-6">';
		if (this.amount.toFixed(2) < 0) {
			htmlContent += '<h5><span><i class="far fa-money-bill-alt fa-2x"></i>  Change</span></h5>';
		} else {
			var iconImg = '';
			if (this.name == 'Cash') {
				iconImg += '<i class="far fa-money-bill-alt fa-2x"></i>';
			} else if (this.name == 'Credit Card') {
				iconImg += '<i class="fas fa-credit-card fa-2x"></i>';
			} else if (this.name == 'Paypal') {
				iconImg += '<i class="fab fa-paypal fa-2x"></i>';
			}
			htmlContent += '<h5><span>' + iconImg + '  ' + this.name + '</span></h5>';
		}

		htmlContent += '</div><div class="col-4">';
		htmlContent += '<input id="tli_amt_' + tli_index + '" type="hidden" value="' + this.amount.toFixed(2) + '"></input>';
		htmlContent += '<h5><span>INR ' + this.amount.toFixed(2) + '</span></h5>';
		htmlContent += '</div><div class="col-2">';
		htmlContent += '<button type="button" id="btnDeleteTLI"';
		htmlContent += 'onClick="cashTenderLineItem.deleteTenderLineItem(' + tli_index;
		htmlContent += ')" class="btn btn-danger btn-sm"><i class="far fa-trash-alt fa-2x"></i></button>';
		htmlContent += '</div></div>';

		$('#tenderLineItemContainer').append(htmlContent);

	},
	setTenderLineItem : function(tenderEnteredAmt, tenderName) {
		this.name = tenderName;
		this.amount = tenderEnteredAmt;
	},

	deleteTenderLineItem : function(deleteIndex) {
		var deletedAmt = +$('#tli_amt_' + deleteIndex).val();
		$('#dueAmt').removeAttr("disabled");
		var dueAmt = +$('#dueAmt').val();
		$('#' + deleteIndex + 'tenderLineItem').remove();
		dueAmt += deletedAmt;
		$('#dueAmt').val(dueAmt.toFixed(2));

		$('#btnTenderOK').removeClass('d-none');
		$('#btnCompleteTxn').addClass('d-none');

		$('#lbl_tenderDue').html('Due Amount');

		tli_index -= 1;
		if (tli_index < 1) {
			$('#tenderLineItemContainer').addClass('d-none');
		}
	},
	calculateDue : function(tenderEnteredAmt, totalDueAmt, tenderType) {

		var totalPaidSoFar = 0.00;
		var tliPaidAmt;
		$("[id^=tli_amt_").each(function() {
			tliPaidAmt = +$(this).val();
			totalPaidSoFar += tliPaidAmt;
		});

		var remainingAmt = totalDueAmt.toFixed(2) - (totalPaidSoFar.toFixed(2));

		this.setTenderLineItem(tenderEnteredAmt, tenderType);
		if (parseFloat(tenderEnteredAmt.toFixed(2)) > parseFloat(remainingAmt.toFixed(2))) {
			remainingAmt -= tenderEnteredAmt;

			$('#btnTenderOK').removeClass('d-none');
			$('#btnCompleteTxn').addClass('d-none');

			$('#lbl_tenderDue').html('Change Due');

			$('#dueAmt').val(remainingAmt.toFixed(2));
			$('#dueAmt').attr("disabled", "disabled");
			this.renderTenderLineItem();

			this.calculateDue(remainingAmt, totalDueAmt, tenderType);

		} else if (parseFloat(tenderEnteredAmt.toFixed(2)) < parseFloat(remainingAmt.toFixed(2))) {
			remainingAmt -= tenderEnteredAmt;
			$('#dueAmt').val(remainingAmt.toFixed(2));
			this.renderTenderLineItem();
			$('#lbl_tenderDue').html('Due Amount');

			$('#btnTenderOK').removeClass('d-none');
			$('#btnCompleteTxn').addClass('d-none');

		} else if (parseFloat(tenderEnteredAmt.toFixed(2)) == parseFloat(remainingAmt.toFixed(2))) {
			remainingAmt -= tenderEnteredAmt;

			$('#btnTenderOK').addClass('d-none');
			$('#btnCompleteTxn').removeClass('d-none');

			$('#lbl_tenderDue').html('Due Amount');
			$('#dueAmt').val((0.00).toFixed(2));
			$('#dueAmt').attr("disabled", "disabled");
			this.renderTenderLineItem();
		}
	}
});

var Utils = function() {
	this.alertType;
}

$.extend(Utils.prototype, {

	renderAlert : function(alertType, headerTitle, bodyMsg, btnLabels, btnActions, actionFunction) {

		$('#alertModalTitle').html(headerTitle);
		
		if (alertType != undefined && alertType == 'SIMPLE') {
			this.createSimpleModal(bodyMsg, btnLabels);
		} else if (alertType != undefined && alertType == 'SUCCESS') {
			this.createSimpleSuccessModal(bodyMsg, btnLabels);
		} else if (alertType != undefined && alertType == 'CONFIRM') {
			this.createConfirmModal(bodyMsg, btnLabels);
		} else if (alertType != undefined && alertType == 'CUSTOM') {
			this.createCustomModal(bodyMsg, btnLabels, btnActions);
		} else if (alertType != undefined && alertType == 'GLOBAL') {
			this.createGlobalAlertModal(bodyMsg, btnLabels, btnActions, actionFunction);
		}  

		$('#alertModal').modal({
			backdrop : 'static',
			keyboard : false
		});

	},
	createSimpleModal : function(bodyMsg, btnLabels) {

		$('#alertModalHeader').addClass('alert-danger');

		var footerHtml = '<div class="col text-center">';
		footerHtml += '<button class="btn btn-danger" id="btnAlertOK" onClick="hideAlert();">' + btnLabels[0] + '</button>';
		footerHtml += '</div>';
		$('#alertModalFooter').html(footerHtml);

		var bodyHtml = '<div class="row">';
		bodyHtml += '<div class="col-3 text-left"> <i class="fas fa-exclamation-triangle fa-3x mx-2 text-danger"></i></div>';
		bodyHtml += '<div class="col text-left"> ' + bodyMsg + '</div>';
		bodyHtml += '</div>';

		$('#alertModalBody').html(bodyHtml);

	},
	createSimpleSuccessModal : function(bodyMsg, btnLabels) {

		$('#alertModalHeader').addClass('alert-success');

		var footerHtml = '<div class="col text-center">';
		footerHtml += '<button class="btn btn-success" id="btnAlertOK" onClick="hideAlert();">' + btnLabels[0] + '</button>';
		footerHtml += '</div>';
		$('#alertModalFooter').html(footerHtml);

		var bodyHtml = '<div class="row">';
		bodyHtml += '<div class="col-3 text-left"> <i class="fas fa-exclamation-triangle fa-3x mx-2 text-success"></i></div>';
		bodyHtml += '<div class="col text-left"> ' + bodyMsg + '</div>';
		bodyHtml += '</div>';

		$('#alertModalBody').html(bodyHtml);

	},
	createConfirmModal : function(bodyMsg, btnLabels, btnActionId) {

		$('#alertModalHeader').addClass('alert-warning');

		var twoActionsHtml = '<div class="row">';
		twoActionsHtml += '<div class="col-3 text-left"> <i class="fas fa-exclamation-triangle fa-3x mx-2 text-warning"></i></div>';
		twoActionsHtml += '<div class="col text-left"> ' + bodyMsg + '</div>';
		twoActionsHtml += '</div>';

		$('#alertModalBody').html(twoActionsHtml);

		var twoActionsFooterHtml = '<div class="col text-center">';
		$.each(btnLabels, function(index, btnLabel) {
			twoActionsFooterHtml += '<button class="btn btn-info mx-2" id="btnAlert' + btnLabel + '" onClick="alertAction(this);">' + btnLabel + '</button>';
		});
		twoActionsFooterHtml += '</div>';
		$('#alertModalFooter').html(twoActionsFooterHtml);

	},
	createCustomModal : function(bodyMsg, btnLabels, btnActions) {

		$('#alertModalHeader').addClass('alert-warning');

		var twoActionsHtml = '<div class="row">';
		twoActionsHtml += '<div class="col-3 text-left"> <i class="fas fa-exclamation-triangle fa-3x mx-2 text-warning"></i></div>';
		twoActionsHtml += '<div class="col text-left"> ' + bodyMsg + '</div>';
		twoActionsHtml += '</div>';

		$('#alertModalBody').html(twoActionsHtml);

		var twoActionsFooterHtml = '<div class="col text-center">';
		$.each(btnLabels, function(index, btnLabel) {
			twoActionsFooterHtml += '<button class="btn btn-info mx-2" id="'+btnActions[index]+'" onClick="alertAction(this);">' + btnLabel + '</button>';
		});
		twoActionsFooterHtml += '</div>';
		$('#alertModalFooter').html(twoActionsFooterHtml);

	},
	createGlobalAlertModal : function(bodyMsg, btnLabels, btnActions, actionFunction) {

		$('#alertModalHeader').addClass('alert-warning');

		var twoActionsHtml = '<div class="row">';
		twoActionsHtml += '<div class="col-3 text-left"> <i class="fas fa-exclamation-triangle fa-3x mx-2 text-warning"></i></div>';
		twoActionsHtml += '<div class="col text-left"> ' + bodyMsg + '</div>';
		twoActionsHtml += '</div>';

		$('#alertModalBody').html(twoActionsHtml);

		var twoActionsFooterHtml = '<div class="col text-center">';
		$.each(btnLabels, function(index, btnLabel) {
			twoActionsFooterHtml += '<button class="btn btn-info mx-2" id="'+btnActions[index]+'" onClick="'+actionFunction+'(this);">' + btnLabel + '</button>';
		});
		twoActionsFooterHtml += '</div>';
		$('#alertModalFooter').html(twoActionsFooterHtml);

	}		

});

