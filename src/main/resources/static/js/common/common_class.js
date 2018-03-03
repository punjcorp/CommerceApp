/**
 * 
 */

var tli_index = 0;

var TenderLineItem = function() {
	this.tenderId;
	this.name = '';
	this.amount = 0.00;
}

$.extend(TenderLineItem.prototype, {
	renderTenderLineItem : function() {
		tli_index += 1;

		if (tli_index > 0) {
			$('#tenderLineItemContainer').removeClass('d-none');
		}

		var htmlContent = '<div class="row" id="' + tli_index + 'tenderLineItem">';
		htmlContent += '<div class="col-2">';
		if(this.amount.toFixed(2)<0){
			htmlContent += '<h5><span><i class="far fa-money-bill-alt fa-2x"></i>Change</span></h5>';
		}else{
			htmlContent += '<h5><span><i class="far fa-money-bill-alt fa-2x"></i>' + this.name + '</span></h5>';
		}
		
		htmlContent += '</div><div class="col-3">';
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
	calculateDue : function(tenderEnteredAmt, totalDueAmt,tenderType) {

		var totalPaidSoFar = 0.00;
		var tliPaidAmt;
		$("[id^=tli_amt_").each(function() {
			tliPaidAmt = +$(this).val();
			totalPaidSoFar += tliPaidAmt;
		});

		var remainingAmt = totalDueAmt.toFixed(2) - (totalPaidSoFar.toFixed(2));

		this.setTenderLineItem(tenderEnteredAmt, tenderType);
		if (tenderEnteredAmt.toFixed(2) > remainingAmt.toFixed(2)) {
			remainingAmt -= tenderEnteredAmt;
			
			$('#btnTenderOK').removeClass('d-none');
			$('#btnCompleteTxn').addClass('d-none');
			
			$('#lbl_tenderDue').html('Change Due');
			
			$('#dueAmt').val(remainingAmt.toFixed(2));
			$('#dueAmt').attr("disabled", "disabled");
			this.renderTenderLineItem();
			
		} else if (tenderEnteredAmt.toFixed(2) < remainingAmt.toFixed(2)) {
			remainingAmt -= tenderEnteredAmt;
			$('#dueAmt').val(remainingAmt.toFixed(2));
			this.renderTenderLineItem();
			$('#lbl_tenderDue').html('Due Amount');
			
			$('#btnTenderOK').removeClass('d-none');
			$('#btnCompleteTxn').addClass('d-none');
			

		} else if (tenderEnteredAmt.toFixed(2) == remainingAmt.toFixed(2)) {
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
