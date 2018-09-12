/**
 * This file contains all the custom JS code for commerce website
 */

var commonUtil = new Utils();
var logout_url = '/logout';

$(function() {

	// use plugins and options as needed, for options, detail see
	// http://i18next.com/docs/
	i18next
			.init(
					{
						lng : current_locale, // evtl. use language-detector https://github.com/i18next/i18next-browser-languageDetector
						resources : { // evtl. load via xhr https://github.com/i18next/i18next-xhr-backend
							en : {
								translation : {
									//common_currency_sign_inr : '₹',
									common_currency_sign_inr : 'Rs.',
									tooltip_btn_delete_tender_line_item : 'Delete Tender Line Item',
									tooltip_btn_delete_sale_line_item : 'Delete Sale Line Item',
									tooltip_btn_delete_return_line_item : 'Delete Return Line Item',
									sale_txn_validate_item : "The selected item already exists in the transaction, please increase the quantity if needed",
									sale_txn_validate_item_name : 'The description should be of minimum 5 characters.',
									sale_txn_validate_qty : 'The quantity should be a positive value always.Please correct the quantity.',
									sale_txn_validate_range_discount : 'The discount amount should be between ₹ 0.00 and item price amount.Please correct the amount.',
									sale_txn_validate_range_discount_pct : 'The discount percentage should be between 0 and 100 of item price amount.',
									sale_txn_validate_exceed_discount : 'The discount amount cannot be more than item price',
									sale_txn_validate_tender : 'Please select tender for the payment',
									sale_txn_validate_amount_tender : 'The tendered amount should be more than ₹ 0.00',
									sale_txn_customer_association_needed : 'The customer details are needed for selecting Credit Tender!!',
									sale_txn_lbl_qty : 'Quantity',
									sale_txn_lbl_unit_cost : 'Unit Price',
									sale_txn_lbl_suggested_price : 'Suggested Price',
									sale_txn_lbl_mrp : 'Max Retail Price',
									sale_txn_lbl_discount : 'Discount',
									sale_txn_lbl_item_price : 'Item Price',
									sale_txn_lbl_tax : 'Tax',
									sale_txn_lbl_sgst : 'SGST',
									sale_txn_lbl_cgst : 'CGST',
									sale_txn_lbl_igst : 'IGST',
									sale_txn_lbl_item_total : 'Item Total',
									sale_txn_lbl_discount_percent : '%',
									//sale_txn_lbl_discount_amount : '₹',
									sale_txn_lbl_discount_amount : 'Rs',
									error_simple_alert_header : 'Alert Message',
									error_confirmation_alert_header : 'Confirmation Message',
									alert_btn_ok : 'OK',
									alert_btn_approve : 'Approve',
									alert_btn_cancel : 'Cancel',
									last_txn_title : 'Last Successful Transaction',
									alert_btn_logout : 'Logout',
									error_confirm_logout : 'Are you sure you want to Logout? This will end your current session!',
									error_entered_low : 'The <b>Counted Total Amount</b> is less than <b>Expected Closing Amount</b>. <br><br>Please <b>Approve</b> to proceed with the Entered Total Amount or <b>Cancel</b> to make changes.',
									error_entered_high : 'The <b>Counted Total Amount</b> is greater than <b>Expected Closing Amount</b>. <br><br>Please <b>Approve</b> to proceed with the Entered Total Amount or <b>Cancel</b> to make changes.',
									error_entered_zero : 'Please enter amount greater than <b>₹ 0.00</b> to reconcile.',
									error_status_open_register : 'The register cannot be closed, Do you want to open register?',
									alert_btn_open_register : 'Open Register',
									alert_btn_close_store : 'Close Store',
									screen_lbl_tender_denomination : 'Tender Denomination',
									screen_lbl_tender_denomination_first_option : 'Select Denomination',
									screen_lbl_media_count : 'Media Count',
									screen_lbl_amount : 'Amount',
									screen_lbl_media_count_placeholder : 'Enter Tender Denomination Count',
									screen_lbl_amount_placeholder : 'Enter Tender Denomination Amount',
									error_store_closure_invalid_status : 'The location status is not valid for Closure process!!',
									error_store_closure_invalid_bdate : 'The business date is not valid for the selected location!!',
									return_txn_validate_amount_refund : 'The refunded amount should be more than ₹ 0.00',
									error_simple_success_header : 'Success Message',
									customer_add_success : 'The customer details has been added and associated with transactions. However, the details will be saved along with transaction details',
									screeen_lbl_common_serial : 'S',
									screeen_lbl_order_item_id : 'Item Id',
									screeen_lbl_order_item_desc : 'Item Description',
									screeen_lbl_order_item_details : 'Item Details',
									screeen_lbl_order_quantity : 'Quantity',
									screeen_lbl_order_unit_cost : 'Unit Cost',
									screeen_lbl_order_item_cost : 'Item Cost',
									screeen_lbl_order_discount : 'Discount',
									screeen_lbl_order_item_tax : 'Item Tax',
									screeen_lbl_order_item_total : 'Item Total',
									screeen_order_return_validate_qty : 'The quantity should be a greater than zero and less than or equal to original order\'s received quantity.Please correct the quantity.',
									screeen_lbl_order_return_reason_code_select : 'Select Return Reason Code',
									screeen_lbl_order_return_reason_code : 'Reason Code',
									screeen_lbl_btn_add_address : 'Add Address',
									screeen_lbl_btn_delete_address : 'Delete Address',
									screeen_lbl_address_line_1 : 'Address Line 1',
									screeen_lbl_address_line_2 : 'Address Line 2',
									screeen_lbl_address_landmark : 'Nearby Landmark',
									screeen_lbl_address_city : 'City',
									screeen_lbl_address_district : 'District',
									screeen_lbl_address_state : 'State',
									screeen_lbl_address_country : 'Country',
									screeen_lbl_address_pincode : 'Pincode',
									screeen_lbl_address_primary : 'Primary Address',
									screeen_lbl_address_type_selection : 'Select Address Type',
									screeen_lbl_address_type_work : 'Work',
									screeen_lbl_address_type_warehouse : 'Warehouse',
									screeen_lbl_address_type_home : 'Home',
									screeen_lbl_placeholder_address_line_1 : 'Enter Address Line 1',
									screeen_lbl_placeholder_address_line_2 : 'Enter Address Line 2',
									screeen_lbl_placeholder_address_landmark : 'Enter any nearby landmark',
									screeen_lbl_placeholder_address_city : 'Enter City',
									screeen_lbl_placeholder_address_district : 'Enter District',
									screeen_lbl_placeholder_address_state : 'Select State',
									screeen_lbl_placeholder_address_country : 'Country',
									screeen_lbl_placeholder_address_pincode : 'Enter Pincode',
									screeen_error_zero_address : 'There is no address existing!',
									error_confirm_customer_delete : 'The deletion of customer(s) will also destroy the finance account(s)!!<br> Do you want to Approve the Deletion?',
									error_register_already_closed_today: 'This register has been closed for current business date!!<br><br> Please open another register if you want to perform transactions with current business date',
								}
							},
							hi : {
								translation : {
									common_currency_sign_inr : '₹',
									tooltip_btn_delete_tender_line_item : 'भुगतान आइटम हटाएं',
									tooltip_btn_delete_sale_line_item : 'बिक्री लाइन आइटम हटाएं',
									tooltip_btn_delete_return_line_item : 'वापसी  लाइन आइटम हटाएं',
									sale_txn_validate_item : "चयनित वस्तु लेनदेन विधि में पहले से मौजूद है, कृपया आवश्यकता होने पर मात्रा बढ़ाएं",
									sale_txn_validate_qty : 'मात्रा हमेशा सकारात्मक मूल्य होना चाहिए। कृपया मात्रा को सही करें।',
									sale_txn_validate_range_discount : 'छूट राशि ₹ 0.00 और वस्तु मूल्य राशि के बीच होनी चाहिए। कृपया राशि को सही करें।',
									sale_txn_validate_range_discount_pct : 'छूट प्रतिशत वस्तु  मूल्य राशि के 0 और 100 प्रतिशत के बीच होना चाहिए।',
									sale_txn_validate_exceed_discount : 'छूट राशि वस्तु  मूल्य से अधिक नहीं हो सकती है',
									sale_txn_validate_tender : 'कृपया भुगतान के लिए मुद्रा का चयन करें',
									sale_txn_validate_amount_tender : 'भुगतान राशि ₹ 0.00 से अधिक होनी चाहिए',
									sale_txn_customer_association_needed : 'उधार के लिए ग्राहक विवरण ज़रूरी है !!',
									sale_txn_lbl_qty : 'मात्रा',
									sale_txn_lbl_unit_cost : 'इकाई मूल्य',
									sale_txn_lbl_suggested_price : 'सुझाव मूल्य',
									sale_txn_lbl_mrp : 'अधिकतम मूल्य',
									sale_txn_lbl_discount : 'छूट',
									sale_txn_lbl_item_price : 'वस्तु मूल्य',
									sale_txn_lbl_tax : 'कर',
									sale_txn_lbl_sgst : 'एसजीएसटी',
									sale_txn_lbl_cgst : 'सीजीएसटी',
									sale_txn_lbl_igst : 'आईजीएसटी',
									sale_txn_lbl_item_total : 'वस्तु कुल',
									sale_txn_lbl_discount_percent : '%',
									sale_txn_lbl_discount_amount : '₹',
									error_simple_alert_header : 'चेतावनी संदेश',
									error_confirmation_alert_header : 'पुष्टि संदेश',
									alert_btn_ok : 'ठीक',
									alert_btn_approve : 'स्वीकृत करें',
									alert_btn_cancel : 'रद्द करें',
									last_txn_title : 'अंतिम सफल लेनदेन विधि',
									alert_btn_logout : 'लॉगआउट',
									error_confirm_logout : 'क्या आप लॉगआउट करना चाहते हैं? यह आपके वर्तमान सत्र को समाप्त कर देगा !!',
									error_entered_low : '<b>गिनी कुल राशि</b> <b>अपेक्षित समापन राशि </b> से कम है।<br><br>कृपया परिवर्तन करने के लिए दर्ज कुल राशि या <b> रद्द करें </b> के साथ आगे बढ़ने के लिए <b> स्वीकृति दें </b>।',
									error_entered_high : '<b>गिनी कुल राशि</b> <b>अपेक्षित समापन राशि </b> से अधिक है। <br><br>कृपया परिवर्तन करने के लिए दर्ज कुल राशि या <b> रद्द करें </b> के साथ आगे बढ़ने के लिए <b> स्वीकृति दें </b>।',
									error_entered_zero : 'बंद करने के लिए  <b>₹0.00</b> से अधिक राशि दर्ज करें।',
									error_status_open_register : 'रजिस्टर बंद नहीं किया जा सकता है, क्या आप इसके बजाय रजिस्टर खोलना चाहते हैं?',
									alert_btn_open_register : 'रजिस्टर खोलें',
									alert_btn_close_store : 'स्टोर बंद करें',
									screen_lbl_tender_denomination : 'मूल्यवर्ग',
									screen_lbl_tender_denomination_first_option : 'मूल्यवर्ग का चयन करें',
									screen_lbl_media_count : 'मुद्रा गणना',
									screen_lbl_amount : 'राशि',
									screen_lbl_media_count_placeholder : 'मुद्रा गणना दर्ज करें',
									screen_lbl_amount_placeholder : 'मूल्यवर्ग राशि दर्ज करें',
									error_store_closure_invalid_status : 'स्थान की स्थिति क्लोजर प्रक्रिया के लिए मान्य नहीं है !!',
									error_store_closure_invalid_bdate : 'व्यवसाय की तारीख चयनित स्थान के लिए मान्य नहीं है !!',
									return_txn_validate_amount_refund : 'धनवापसी राशि ₹0.00 से अधिक होनी चाहिए',
									error_simple_success_header : 'सफल संदेश',
									customer_add_success : 'ग्राहक विवरण लेनदेन विधि के साथ जोड़ दिया गया है। हालांकि, विवरण लेनदेन विधि के विवरण के साथ बचाया जाएगा',
									screeen_lbl_common_serial : 'S#',
									screeen_lbl_order_item_id : 'Item Id',
									screeen_lbl_order_item_desc : 'Item Description',
									screeen_lbl_order_item_details : 'Item Details',
									screeen_lbl_order_quantity : 'Quantity',
									screeen_lbl_order_unit_cost : 'Unit Cost',
									screeen_lbl_order_item_cost : 'Item Cost',
									screeen_lbl_order_discount : 'Discount',
									screeen_lbl_order_item_tax : 'Item Tax',
									screeen_lbl_order_item_total : 'Item Total',
								}
							},
							pa : {
								translation : {
									common_currency_sign_inr : 'ਰੁ.',
									tooltip_btn_delete_tender_line_item : 'ਭੁਗਤਾਨ ਲਾਈਨ ਆਈਟਮ ਮਿਟਾਓ',
									tooltip_btn_delete_sale_line_item : 'ਵਿਕਰੀ ਲਾਈਨ ਆਈਟਮ ਮਿਟਾਓ',
									tooltip_btn_delete_return_line_item : 'ਵਾਪਸੀ ਲਾਈਨ ਆਈਟਮ ਮਿਟਾਓ',
									sale_txn_validate_item : "ਚੁਣੀ ਗਈ ਚੀਜ਼ ਪਹਿਲਾਂ ਹੀ ਟ੍ਰਾਂਜੈਕਸ਼ਨ ਵਿੱਚ ਮੌਜੂਦ ਹੈ, ਕਿਰਪਾ ਕਰਕੇ ਲੋੜੀਂਦੀ ਮਾਤਰਾ ਨੂੰ ਵਧਾਓ",
									sale_txn_validate_qty : 'ਮਾਤਰਾ ਹਮੇਸ਼ਾ ਇੱਕ ਸਕਾਰਾਤਮਕ ਹੋਣੀ ਚਾਹੀਦੀ ਹੈ. ਕਿਰਪਾ ਕਰਕੇ ਮਾਤਰਾ ਨੂੰ ਠੀਕ ਕਰੋ.',
									sale_txn_validate_range_discount : 'ਛੂਟ ਦੀ ਰਕਮ ਨੂੰ  ਰੁ.0.00 ਅਤੇ ਚੀਜ਼ ਦੀ ਕੀਮਤ ਦੇ ਵਿਚਕਾਰ ਹੋਣਾ ਚਾਹੀਦਾ ਹੈ. ਕਿਰਪਾ ਕਰਕੇ ਰਕਮ ਨੂੰ ਸਹੀ ਕਰੋ',
									sale_txn_validate_range_discount_pct : 'ਛੂਟ ਪ੍ਰਤੀਸ਼ਤਤਾ ਆਈਟਮ ਦੀ ਕੀਮਤ ਦੇ 0 ਅਤੇ 100 ਪ੍ਰਤੀਸ਼ਤ ਦੇ ਵਿਚਕਾਰ ਹੋਣੀ ਚਾਹੀਦੀ ਹੈ',
									sale_txn_validate_exceed_discount : 'ਛੋਟ ਦੀ ਰਕਮ ਚੀਜ਼ ਦੀ ਕੀਮਤ ਤੋਂ ਵੱਧ ਨਹੀਂ ਹੋ ਸਕਦੀ ਹੈ',
									sale_txn_validate_tender : 'ਕਿਰਪਾ ਕਰਕੇ ਭੁਗਤਾਨ ਲਈ ਮੁਦਰਾ ਚੁਣੋ',
									sale_txn_validate_amount_tender : 'ਭੁਗਤਾਨ ਦੀ ਰਾਸ਼ੀ  ਰੁ.0.00 ਤੋਂ ਵੱਧ ਹੋਣੀ ਚਾਹੀਦੀ ਹੈ',
									sale_txn_customer_association_needed : 'ਉਧਾਰ ਲਈ ਗਾਹਕ ਦੇ ਵੇਰਵੇ ਦੀ ਜ਼ਰੂਰਤ ਹੈ !!',
									sale_txn_lbl_qty : 'ਗਿਣਤੀ',
									sale_txn_lbl_unit_cost : 'ਇਕਾਈ ਮੁੱਲ',
									sale_txn_lbl_suggested_price : 'ਸੁਝਾਏ ਮੁੱਲ',
									sale_txn_lbl_mrp : 'ਅਧਿਕਤਮ ਪ੍ਰਚੂਨ ਮੁੱਲ',
									sale_txn_lbl_discount : 'ਛੂਟ',
									sale_txn_lbl_item_price : 'ਚੀਜ਼ ਮੁੱਲ',
									sale_txn_lbl_tax : 'ਟੈਕਸ',
									sale_txn_lbl_sgst : 'ਐਸਜੀਐਸਟੀ',
									sale_txn_lbl_cgst : 'ਸੀਜੀਐਸਟੀ',
									sale_txn_lbl_igst : 'ਆਈਜੀਐਸਟੀ',
									sale_txn_lbl_item_total : 'ਚੀਜ਼ ਕੁੱਲ',
									sale_txn_lbl_discount_percent : '%',
									sale_txn_lbl_discount_amount : '₹',
									error_simple_alert_header : 'ਚਿਤਾਵਨੀ ਸੁਨੇਹਾ',
									error_confirmation_alert_header : 'ਪੁਸ਼ਟੀ  ਸੁਨੇਹਾ',
									alert_btn_ok : 'ਠੀਕ ਹੈ',
									alert_btn_approve : 'ਸਵੀਕਾਰ ਕਰੋ',
									alert_btn_cancel : 'ਰੱਦ ਕਰੋ',
									last_txn_title : 'ਆਖਰੀ ਸਫਲ ਟ੍ਰਾਂਜੈਕਸ਼ਨ',
									alert_btn_logout : 'ਲਾੱਗਆਊਟ',
									error_confirm_logout : 'ਕੀ ਤੁਸੀਂ ਨਿਸ਼ਚਤ ਰੂਪ ਤੋਂ ਲਾੱਗਆਊਟ ਕਰਨਾ ਚਾਹੁੰਦੇ ਹੋ? ਇਹ ਤੁਹਾਡੇ ਮੌਜੂਦਾ ਸੈਸ਼ਨ ਨੂੰ ਖਤਮ ਕਰ ਦੇਵੇਗਾ !',
									error_entered_low : '<b>ਗਿਨੀ ਹੋਈ ਕੁੱਲ ਰਕਮ </b> <b>ਅੰਦਾਜ਼ਨ ਕਲੋਜ਼ਿੰਗ ਰਕਮ </b> ਤੋਂ ਘੱਟ ਹੈ. <br> <br> ਕੁਲ ਦਾਖਲ ਰਕਮ ਨਾਲ ਅੱਗੇ ਵਧਣ ਲਈ <b>ਸਵੀਕਾਰ ਕਰੋ </b> ਜਾਂ ਤਬਦੀਲ ਕਰਨ ਲਈ <b>ਰੱਦ ਕਰੋ </b>.',
									error_entered_high : '<b>ਗਿਨੀ ਹੋਈ ਕੁੱਲ ਰਕਮ </b> <b>ਅੰਦਾਜ਼ਨ ਕਲੋਜ਼ਿੰਗ ਰਕਮ </b> ਤੋਂ ਜਿਆਦਾ ਹੈ. <br><br>ਕੁਲ ਦਾਖਲ ਰਕਮ ਨਾਲ ਅੱਗੇ ਵਧਣ ਲਈ <b>ਸਵੀਕਾਰ ਕਰੋ </b> ਜਾਂ ਤਬਦੀਲ ਕਰਨ ਲਈ <b>ਰੱਦ ਕਰੋ </b>.',
									error_entered_zero : 'ਕਿਰਪਾ ਕਰਕੇ ਬੰਦ ਕਰਨ ਲਈ <b> ਰੁ.0.00 </b> ਤੋਂ ਵੱਧ ਰਕਮ ਦਰਜ ਕਰੋ.',
									error_status_open_register : 'ਰਜਿਸਟਰ ਨੂੰ ਬੰਦ ਨਹੀਂ ਕੀਤਾ ਜਾ ਸਕਦਾ, ਕੀ ਤੁਸੀਂ ਰਜਿਸਟਰ ਨੂੰ ਖੋਲ੍ਹਣਾ ਚਾਹੁੰਦੇ ਹੋ?',
									alert_btn_open_register : 'ਰਜਿਸਟਰ ਖੋਲ੍ਹੋ',
									alert_btn_close_store : 'ਸਟੋਰ ਬੰਦ ਕਰੋ',
									screen_lbl_tender_denomination : 'ਭੁਗਤਾਨ ਸੰਮਤੀਆਂ',
									screen_lbl_tender_denomination_first_option : 'ਸੰਦਰਭ ਚੁਣੋ',
									screen_lbl_media_count : 'ਨਗ ਗਿਣਤੀ',
									screen_lbl_amount : 'ਰਕਮ',
									screen_lbl_media_count_placeholder : 'ਸੰਮਤੀਆਂ ਦੀ ਨਗ ਗਿਣਤੀ ਦਰਜ ਕਰੋ',
									screen_lbl_amount_placeholder : 'ਸੰਮਤੀਆਂ ਦੀ ਰਕਮ ਦਰਜ ਕਰੋ',
									error_store_closure_invalid_status : 'ਸਟੋਰ ਸਥਿਤੀ ਬੰਦ ਕਰਨ ਦੀ ਪ੍ਰਕਿਰਿਆ ਲਈ ਸਹੀ ਨਹੀਂ ਹੈ !!',
									error_store_closure_invalid_bdate : 'ਚੁਣੀ ਹੋਈ ਜਗ੍ਹਾ ਲਈ ਕਾਰੋਬਾਰ ਦੀ ਤਾਰੀਖ ਪ੍ਰਕਿਰਿਆ ਨਹੀਂ ਹੈ !!',
									return_txn_validate_amount_refund : 'ਵਾਪਸੀ ਲਈ ਰਕਮ ਰੁ.0.00 ਤੋਂ ਵੱਧ ਹੋਣੀ ਚਾਹੀਦੀ ਹੈ',
									error_simple_success_header : 'ਸਫਲ ਸੁਨੇਹਾ',
									customer_add_success : 'ਗਾਹਕਾਂ ਦੇ ਵੇਰਵੇ ਟ੍ਰਾਂਜੈਕਸ਼ਨ ਨਾਲ ਸੰਬੰਧਿਤ ਕਰ ਦਿੱਤਾ ਗਿਆ ਹੈ. ਹਾਲਾਂਕਿ, ਵੇਰਵੇ ਟ੍ਰਾਂਜੈਕਸ਼ਨ ਵੇਰਵੇ ਦੇ ਨਾਲ ਸੰਭਾਲੇ ਜਾਣਗੇ',
									screeen_lbl_common_serial : 'S#',
									screeen_lbl_order_item_id : 'Item Id',
									screeen_lbl_order_item_desc : 'Item Description',
									screeen_lbl_order_item_details : 'Item Details',
									screeen_lbl_order_quantity : 'Quantity',
									screeen_lbl_order_unit_cost : 'Unit Cost',
									screeen_lbl_order_item_cost : 'Item Cost',
									screeen_lbl_order_discount : 'Discount',
									screeen_lbl_order_item_tax : 'Item Tax',
									screeen_lbl_order_item_total : 'Item Total',
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

function confirmLogout() {
	var btnLabels = [ i18next.t('alert_btn_logout'), i18next.t('alert_btn_cancel') ];
	var btnActions = [ 'btnLogout', 'btnCancel' ];
	commonUtil.renderAlert('GLOBAL', i18next.t('error_confirmation_alert_header'), i18next.t('error_confirm_logout'), btnLabels, btnActions, 'globalAction');
}

function globalAction(cntl) {
	if (cntl.id.indexOf('Cancel') > 0) {
		$('#alertModal').modal('hide');
	} else if (cntl.id.indexOf('Logout') > 0) {
		window.location.href = logout_url;
	}
}

function hideAlert() {
	$('#alertModal').modal('hide');
}

function showFormSubmit(){
	$('#screenBusyModal').modal({backdrop: 'static', keyboard: false});
}

function resizeModal(reportModalId, reportFrameId){
	$('#'+reportModalId).find('.modal-lg').css({
        height: $(window).height() +'px'});
	
	var modalMargin=$('#'+reportModalId).find('.modal-lg').css("marginTop");
	modalMargin=+modalMargin.replace(/[^0-9]/gi, '');
	modalMargin=modalMargin * 2;
	
	var modalHeight=$(window).height() - modalMargin;
	$('#'+reportModalId).find('.modal-content').css({
        height: modalHeight +'px'});
	
	var pdfHeight=modalHeight-230;
	$('#'+reportFrameId).height(pdfHeight+'px');
	
}