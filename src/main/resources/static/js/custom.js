/**
 * This file contains all the custom JS code for commerce website
 */

var commonUtil = new Utils();
var logout_url='/logout';

$(function() {
	
	 // use plugins and options as needed, for options, detail see
    // http://i18next.com/docs/
    i18next.init({
      lng: current_locale, // evtl. use language-detector https://github.com/i18next/i18next-browser-languageDetector
      resources: { // evtl. load via xhr https://github.com/i18next/i18next-xhr-backend
        en: {
          translation: {
              common_currency_sign_inr: '₹',
              sale_txn_validate_item:"The selected item already exists in the transaction, please increase the quantity if needed",
              sale_txn_validate_qty:'The quantity should be a positive value always.Please correct the quantity.',
              sale_txn_validate_range_discount:'The discount amount should be between ₹ 0.00 and item price amount.Please correct the amount.',
              sale_txn_validate_range_discount_pct:'The discount percentage should be between 0 and 100 of item price amount.',
              sale_txn_validate_exceed_discount:'The discount amount cannot be more than item price',
              sale_txn_validate_tender:'Please select tender for the payment',
              sale_txn_validate_amount_tender:'The tendered amount should be more than ₹ 0.00',
              sale_txn_customer_association_needed : 'The customer details are needed for selecting Credit Tender!!',
              sale_txn_lbl_qty: 'Quantity',
              sale_txn_lbl_unit_cost: 'Unit Price',
              sale_txn_lbl_suggested_price: 'Suggested Price',
              sale_txn_lbl_mrp: 'Max Retail Price',
              sale_txn_lbl_discount: 'Discount',
              sale_txn_lbl_item_price: 'Item Price',
              sale_txn_lbl_tax: 'Tax',
              sale_txn_lbl_sgst: 'SGST',
              sale_txn_lbl_cgst: 'CGST',
              sale_txn_lbl_igst: 'IGST',
              sale_txn_lbl_item_total: 'Item Total',
              sale_txn_lbl_discount_percent: '%',
              sale_txn_lbl_discount_amount: '₹',
			  error_simple_alert_header : 'Alert Message',
			  error_confirmation_alert_header : 'Confirmation Message',
			  alert_btn_ok : 'OK',
			  alert_btn_approve : 'Approve' ,
			  alert_btn_cancel : 'Cancel',              
			  last_txn_title : 'Last Successful Transaction',
			  alert_btn_logout : 'Logout',
			  error_confirm_logout : 'Are you sure you want to Logout? This will end your current session!',
              
          }
        },
        hi: {
            translation: {
            	common_currency_sign_inr: '₹'
            }
          },
          pa: {
              translation: {
            	  common_currency_sign_inr: 'ਰੁ.'
              }
            }          
      }
    }, function(err, t) {
      // for options see
      // https://github.com/i18next/jquery-i18next#initialize-the-plugin
      jqueryI18next.init(i18next, $);
      
    });	
	
	

	$('input[type=number][class$="pos-amount"]').each(function() {

		var nbrValue = +$(this).val();
		if (nbrValue == 'undefined' || $.trim(nbrValue) == '') {
			$(this).val('0.00');
		} else if (nbrValue >= 0) {
			$(this).val(nbrValue.toFixed(2));
		}
	});

	$('input[type=number][class$="pos-amount"]').change(function() {
		var nbrValue = +$(this).val();
		if (nbrValue == 'undefined' || $.trim(nbrValue) == '') {
			$(this).val('0.00');
		} else if (nbrValue >= 0) {
			$(this).val(nbrValue.toFixed(2));
		}
	});

	$('#langSelect').change(function() {

		var langSelected = $('#langSelect').val();
		var currentUrl = window.location.href;
		currentUrl = removeParam('lang', currentUrl);

		if (currentUrl.indexOf("?") > 0) {
			currentUrl = currentUrl + '&lang=' + langSelected;
		} else {
			currentUrl = currentUrl + '?lang=' + langSelected;
		}

		window.location.href = currentUrl;
	});

});

/**
 * 
 * @param key
 * @param sourceURL
 * @returns
 */
function removeParam(key, sourceURL) {
	var rtn = sourceURL.split("?")[0], param, params_arr = [], queryString = (sourceURL.indexOf("?") !== -1) ? sourceURL.split("?")[1] : "";
	if (queryString !== "") {
		params_arr = queryString.split("&");
		for (var i = params_arr.length - 1; i >= 0; i -= 1) {
			param = params_arr[i].split("=")[0];
			if (param === key) {
				params_arr.splice(i, 1);
			}
		}
		if (params_arr.length < 1) {
			rtn = rtn;
		} else if (params_arr.length == 1) {
			rtn = rtn + "?" + params_arr.join();
		} else {
			rtn = rtn + "?" + params_arr.join("&");
		}

	}
	return rtn;
}


function confirmLogout(){
	var btnLabels = [ i18next.t('alert_btn_logout'),  i18next.t('alert_btn_cancel') ];
	var btnActions= ['btnLogout' , 'btnCancel'];
	commonUtil.renderAlert('GLOBAL', i18next.t('error_confirmation_alert_header'), i18next.t('error_confirm_logout'), btnLabels, btnActions, 'globalAction');	
}

function globalAction(cntl){
	if (cntl.id.indexOf('Cancel') > 0) {
		$('#alertModal').modal('hide');
	} else if(cntl.id.indexOf('Logout')>0){
		window.location.href = logout_url;
	} 
}

function hideAlert() {
	$('#alertModal').modal('hide');
}