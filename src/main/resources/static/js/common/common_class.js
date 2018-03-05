/**
 * The global variables are defined in this section 
 */

var tli_index = 0;
var tenderLineItems = new Array();

var TenderLineItem = function() {
	this.tenderId;
	this.index;
	this.name = '';
	this.amount = 0.00;
}

$.extend(TenderLineItem.prototype, {
	addTenderLineItem: function(){
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
			
			this.calculateDue(remainingAmt,totalDueAmt, tenderType);

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
