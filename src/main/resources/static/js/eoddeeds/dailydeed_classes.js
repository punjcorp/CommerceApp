var DenominationLineItem = function() {
	this.denomIndex;
	this.denominationId;
	this.denominationVal=0;
	this.denomCount=0;
	this.denomAmount = 0.00;
}

$.extend(DenominationLineItem.prototype, {
	render : function(){
		
		var denomId=this.denominationId;
		
		var denomSelectHtml='<div class="col-3">';
		denomSelectHtml += '<div class="form-group">';
		denomSelectHtml += '<label><small><span class="pos-mandatory">'+i18next.t('screen_lbl_tender_denomination')+'</span> </small></label>';
		denomSelectHtml += '<div class="input-group text-left">';
		denomSelectHtml += '<select class="form-control input-sm" onchange="changedDenomination(\'0\',\''+this.denomIndex+'\')" id="tenders0.denominations'+this.denomIndex+'.denominationId" name="tenders[0].denominations['+this.denomIndex+'].denominationId">';
		denomSelectHtml += '<option class="form-text text-muted" value="">'+i18next.t('screen_lbl_tender_denomination_first_option')+'</option>';
		$.each(denomination_list, function(index, denomItem) {
			denomSelectHtml += '<option class="form-text text-muted" value="'+denomItem.denominationId+'" ';
			if(denomItem.denominationId==denomId)
				denomSelectHtml += ' selected';
			denomSelectHtml += '>'+i18next.t('common_currency_sign_inr')+' '+denomItem.code+'</option>';
		});
		denomSelectHtml += '</select>';
		denomSelectHtml +='</div>';
		denomSelectHtml +='</div>';
		denomSelectHtml +='</div>';
		
		
		
		var denomCountHtml='<div class="col-3">';
		denomCountHtml += '<div class="form-group">';
		denomCountHtml += '<label><small><span class="pos-mandatory">'+i18next.t('screen_lbl_media_count')+'</span> </small></label>';
		denomCountHtml += '<div class="input-group text-left">';
		denomCountHtml += '<input type="number" class="form-control input-sm" data-input="data-input" onchange="changedCount(\'0\',\''+this.denomIndex+'\')"'; 
		denomCountHtml += 'placeholder="'+i18next.t('screen_lbl_media_count_placeholder')+'"';
		denomCountHtml += 'id="tenders0.denominations'+this.denomIndex+'.mediaCount" name="tenders[0].denominations['+this.denomIndex+'].mediaCount"';
		denomCountHtml += ' value="'+this.denomCount+'"></input>';
		denomCountHtml +='</div>';
		denomCountHtml +='</div>';
		denomCountHtml +='</div>';
		
		
		var denomTotalHtml='<div class="col-3">';
		denomTotalHtml += '<div class="form-group">';
		denomTotalHtml += '<label><small><span class="pos-mandatory">'+i18next.t('screen_lbl_amount')+'</span> </small></label>';
		denomTotalHtml += '<div class="input-group text-left">';
		denomTotalHtml += '<div class="input-group-prepend">';
		denomTotalHtml += '<span class="input-group-text"><span>'+i18next.t('common_currency_sign_inr')+'</span></span>';
		denomTotalHtml += '</div>';
		denomTotalHtml += '<input type="number" class="form-control input-sm" data-input="data-input" onchange="changedAmount(\'0\',\''+this.denomIndex+'\')"'; 
		denomTotalHtml += 'placeholder="'+i18next.t('screen_lbl_amount_placeholder')+'"';
		denomTotalHtml += 'id="tenders0.denominations'+this.denomIndex+'.amount" name="tenders[0].denominations['+this.denomIndex+'].amount"';
		denomTotalHtml += ' value="'+this.denomAmount+'"></input>';
		denomTotalHtml +='</div>';
		denomTotalHtml +='</div>';
		denomTotalHtml +='</div>';
		
		
		var denomActionsHtml='<div class="col">';
		denomActionsHtml +='<div class="form-group">';
		denomActionsHtml += '<label><span></span></label>';
		denomActionsHtml += '<div class="input-group text-left">';
		denomActionsHtml += '<button type="button" name="addDenomination" onclick="addNewDenom(\''+this.denomIndex+'\')" class="btn btn-secondary btn-sm">';
		denomActionsHtml += '<i class="fas fa-plus fa-2x"></i>';
		denomActionsHtml += '</button>';
		if(denomLineItems.length>1){
			denomActionsHtml += '<button type="button" name="removeDenomination" onclick="removeSelectedDenom(\''+this.denomIndex+'\')" class="btn btn-secondary btn-sm">';
			denomActionsHtml += '<i class="fas fa-minus fa-2x"></i>';
			denomActionsHtml += '</button>';
		}
		denomActionsHtml +='</div>';
		denomActionsHtml +='</div>';
		denomActionsHtml +='</div>';		
						
		
		var denomHtml='<div class="row" id="denominationLine'+this.denomIndex+'">';
		denomHtml +=denomSelectHtml + denomCountHtml + denomTotalHtml + denomActionsHtml;
		denomHtml +='</div>';
		
		$('#denomContainer').append(denomHtml);
		
	},
	initialize : function(index, denominationId, denominationVal, denomCount, denomAmount){
		this.denomIndex=index;
		this.denominationId=denominationId;
		this.denominationVal=denominationVal;
		this.denomCount=denomCount;
		this.denomAmount = denomAmount;
	}
	
	
});
