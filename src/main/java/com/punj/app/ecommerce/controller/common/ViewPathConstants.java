/**
 * 
 */
package com.punj.app.ecommerce.controller.common;

/**
 * @author admin
 *
 */
public final class ViewPathConstants {

	private ViewPathConstants() {
		throw new IllegalStateException("View Path Constant class");
	}

	public static final String REDIRECT_URL = "redirect:";
	
	/**
	 * The common pages section starts
	 */
	public static final String GET_REASONS_URL="/get_reasons";
	public static final String LIST_ITEM_URL = "/list_item";
	public static final String DISPLAY_PAGE = "common/display";

	public static final String BASE_URL = "/";
	public static final String ECOMMERCE_HOME_URL = "/ecommerce/home";
	public static final String ECOMMERCE_HOME_PAGE = "home";
	public static final String ERROR_PAGE = "error";

	public static final String ACCESS_DENIED_URL = "/403";
	public static final String ACCESS_DENIED_PAGE = "access_denied";

	public static final String LOGOUT_URL = "/logout";
	public static final String LOGOUT_REDIRECT_LOGIN_PAGE = "redirect:/login?logout";
	
	
	public static final String ADMIN_HOME_URL = "/admin/home";
	public static final String ADMIN_DASHBOARD_PAGE = "admin/dashboard";
	
	public static final String CASHIER_HOME_URL = "/pos/home";
	public static final String CASHIER_DASHBOARD_PAGE = "home/dashboard";
	
	/**
	 * The common pages section ends
	 */

	/**
	 * The item pages section starts
	 */
	public static final String MANAGE_ITEM_PAGE = "item/manage_item";
	public static final String SEARCH_ITEM_URL = "/search_item";

	public static final String ITEM_DETAIL_URL = "/item_detail";
	public static final String ITEM_DETAIL_PAGE = "item/item_detail";
	
	public static final String ADD_STYLE_URL = "/admin/add_style";
	public static final String ADD_STYLE_PAGE = "item/add_style";
		
	public static final String EDIT_STYLE_URL = "/admin/edit_style";
	public static final String EDIT_STYLE_PAGE = "item/edit_style";
	
	public static final String ADD_SKU_URL = "/admin/add_sku";
	public static final String ADD_SKU_PAGE = "item/add_sku";
		
	public static final String EDIT_SKU_URL = "/admin/edit_sku";
	public static final String EDIT_SKU_PAGE = "item/sku/edit_sku";	
	
	public static final String GET_HIERARCHY_URL = "/admin/search_hierarchy";
	public static final String SEARCH_HIERARCHIES_URL = "/admin/search_hierarchy";
	
	public static final String SEARCH_ATTRIBUTES_URL = "/admin/search_attributes";
	public static final String SEARCH_ATTRIBUTE_VALUES_URL = "/admin/search_attribute_values";
	
	/**
	 * The item pages section ends
	 */
	/**
	 * The purchase order pages section starts
	 */
	public static final String SEARCH_ORDER_ITEM_URL = "/search_order_item";
	public static final String SEARCH_ORDER_URL = "/admin/search_order";
	public static final String MANAGE_ORDER_PAGE = "order/manage_order";
	public static final String MANAGE_ORDER_URL= "/admin/manage_order";
	
	public static final String ADD_ORDER_URL = "/admin/add_order";
	public static final String ADD_ORDER_PAGE = "order/add_order";	
	
	public static final String PRINT_ORDER_URL = "/admin/print_order_report";
	public static final String VIEW_ORDER_URL = "/admin/view_order_report";
	
	public static final String EDIT_ORDER_URL = "/admin/edit_order";
	public static final String EDIT_ORDER_PAGE= "order/edit_order";
	
	public static final String APPROVE_ORDER_URL = "/admin/approve_order";
	public static final String DELETE_ORDER_URL = "/delete_order";
	
	public static final String ORDER_ITEM_LOOKUP_URL = "/order_item_lookup";
	public static final String CUSTOMER_LOOKUP_URL = "/customer_account_lookup";
	public static final String CUSTOMER_ADDRESS_LOOKUP_URL = "/customer_address_lookup";
	public static final String CUSTOMER_LOOKUP_ADMIN_URL = "/admin/customer_account_lookup";
	public static final String TXN_DETAILS_LOOKUP_URL = "/pos/txn_details_lookup";
	public static final String TXN_LOOKUP_URL = "/admin/transaction_lookup";
	public static final String TXN_LOOKUP_PAGE = "lookup/txn_lookup";
	
	public static final String BULK_ORDER_RETURN_URL = "/admin/bulk_order_return_action";
	
	public static final String BULK_ORDER_URL = "/admin/bulk_order_action";
	public static final String VIEW_ORDER_BILL_URL = "/admin/view_order_bill";
	public static final String RECEIVE_ORDER_URL = "/admin/receive_order";
	public static final String RECEIVE_ORDER_PAGE ="order/receive_order";
	
	public static final String PRINT_ORDERS_URL = "/admin/print_orders";
	
	
	/**
	 * The purchase order pages section ends
	 */
	
	/**
	 * The purchase order returns pages section starts
	 */
	public static final String SEARCH_ORDER_RETURN_URL = "/admin/search_order_return";
	public static final String MANAGE_ORDER_RETURN_PAGE = "order/returns/manage_order_return";
	public static final String MANAGE_ORDER_RETURN_URL= "/admin/manage_order_return";
	
	public static final String ADD_ORDER_RETURN_URL = "/admin/add_order_return";
	public static final String ADD_ORDER_RETURN_PAGE = "order/returns/add_order_return";	
	
	public static final String EDIT_ORDER_RETURN_URL = "/admin/edit_order_return";
	public static final String EDIT_ORDER_RETURN_PAGE = "order/returns/edit_order_return";
	
	public static final String APPROVE_ORDER_RETURN_URL = "/admin/approve_order_return";
	public static final String DELETE_ORDER_RETURN_URL = "/admin/delete_order_return";

	
	public static final String PRINT_ORDER_RETURN_URL = "/admin/print_order_return_report";
	public static final String VIEW_ORDER_RETURN_URL = "/admin/view_order_return_report";
	/**
	 * The purchase order returns pages section ends
	 */	
	
	
	/**
	 * The supplier pages section starts
	 */
	public static final String SEARCH_SUPPLIER_URL = "/admin/search_supplier";
	public static final String MANAGE_SUPPLIER_URL = "/admin/manage_supplier";
	public static final String MANAGE_SUPPLIER_PAGE = "supplier/manage_supplier";
	
	public static final String EDIT_SUPPLIER_URL ="/admin/edit_supplier";
	public static final String EDIT_SUPPLIER_PAGE ="supplier/edit_supplier";
	
	public static final String ADD_SUPPLIER_URL ="/admin/add_supplier";
	public static final String ADD_SUPPLIER_PAGE ="supplier/add_supplier";	
	
	public static final String DELETE_SUPPLIER_URL ="/admin/delete_supplier";
	public static final String BULK_SUPPLIER_URL ="/admin/bulk_supplier_action";
	
	public static final String SUPPLIER_PAYMENT_URL ="/admin/supplier/payment";
	public static final String SUPPLIER_PAYMENT_PAGE ="supplier/supplier_payments";
	
	/**
	 * The supplier pages section ends
	 */
	
	/**
	 * The customer pages section starts
	 */
	
	public static final String SEARCH_CUSTOMER_URL = "/admin/search_customer";
	public static final String MANAGE_CUSTOMER_URL = "/admin/manage_customer";
	public static final String MANAGE_CUSTOMER_PAGE = "customer/manage_customer";
	
	public static final String EDIT_CUSTOMER_URL ="/admin/edit_customer";
	public static final String EDIT_CUSTOMER_PAGE ="customer/edit_customer";
	
	public static final String ADD_CUSTOMER_URL ="/admin/add_customer";
	public static final String ADD_CUSTOMER_PAGE ="customer/add_customer";	
	
	public static final String DELETE_CUSTOMER_URL ="/admin/delete_customer";
	public static final String BULK_CUSTOMER_URL ="/admin/bulk_customer_action";
	
	public static final String CUSTOMER_PAYMENT_URL ="/admin/customer/payment";
	public static final String CUSTOMER_PAYMENT_PAGE ="customer/customer_payments";
	
	/**
	 * The customer pages section ends
	 */
	
	
	/**
	 * The card pages section starts
	 */
	public static final String ADD_CARD_URL ="/add_card";
	public static final String ADD_CARD_PAGE ="account/add_card";
	
	public static final String EDIT_CARD_URL ="/manage_card/edit";
	public static final String EDIT_CARD_PAGE ="account/edit_card";
	
	public static final String MANAGE_CARD_URL ="/manage_card";
	public static final String DELETE_CARD_URL="/manage_card/delete";
	
	public static final String CARD_LIST_PAGE="account/card_list";
	
	/**
	 * The card pages section ends
	 */	

	/**
	 * The inventory adjustment pages section starts
	 */
	public static final String SEARCH_INV_ADJUST_URL = "/admin/search_inv_adjust";
	public static final String MANAGE_INV_ADJUST_URL = "/admin/manage_inv_adjust";
	public static final String MANAGE_INV_ADJUST_PAGE = "inventory/manage_inv_adjust";
	
	public static final String EDIT_INV_ADJUST_URL ="/admin/edit_inv_adjust";
	public static final String EDIT_INV_ADJUST_PAGE ="inventory/edit_inv_adjust";
	
	public static final String APPROVE_INV_ADJUST_URL ="/admin/approve_inv_adjust";
	
	public static final String ADD_INV_ADJUST_URL ="/admin/add_inv_adjust";
	public static final String GET_ITEM_INV_URL ="/get_item_inv";
	public static final String ADD_INV_ADJUST_PAGE ="inventory/add_inv_adjust";	
	
	public static final String DELETE_INV_ADJUST_URL ="/admin/delete_inv_adjust";
	public static final String BULK_INV_ADJUST_URL ="/admin/bulk_inv_adjust_action";

	public static final String PRINT_INV_ADJUSTS_URL = "/admin/print_inv_adjusts";
	public static final String PRINT_INV_ADJUST_URL = "/admin/print_inv_adjust";	
	/**
	 * The inventory adjustment pages section ends
	 */
	/**
	 * The lookup pages section starts
	 */
	public static final String INV_LOOKUP_URL = "/inventory_lookup";
	public static final String INV_LOOKUP_PAGE= "lookup/item/inventory_lookup";
	
	public static final String PRICE_LOOKUP_URL = "/price_lookup";
	public static final String PRICE_LOOKUP_PAGE= "lookup/item/price_lookup";
	
	public static final String LOOKUP_ITEM_URL = "/item_lookup";
	public static final String LOOKUP_ITEM_PAGE= "lookup/item/item_lookup";
	public static final String LOOKUP_ITEM_DETAILS_URL= "/item_details_lookup";
	public static final String LOOKUP_ITEM_DETAILS_PAGE= "item/item_details_lookup";
	
	public static final String LOOKUP_ACCOUNT_URL = "/admin/account_lookup";
	public static final String LOOKUP_ACCOUNT_PAGE= "lookup/account/account_lookup";
	public static final String CUSTOMER_LOOKUP_ACCOUNT_URL = "/admin/customer_account_lookup";
	public static final String CUSTOMER_LOOKUP_ACCOUNT_PAGE= "lookup/account/customer_account_lookup";
	
	/**
	 * The lookup pages section ends
	 */	
	/**
	 * The price event pages section starts
	 */
	public static final String SEARCH_PRICE_URL = "/admin/search_price";
	public static final String MANAGE_PRICE_URL = "/admin/manage_price";
	public static final String MANAGE_PRICE_PAGE = "price/manage_price";
	
	public static final String PRICE_DETAIL_PAGE ="price/price_details";	
	public static final String EDIT_PRICE_URL ="/admin/edit_price";
	public static final String EDIT_PRICE_PAGE ="price/edit_price";
	
	public static final String APPROVE_PRICE_URL ="/admin/approve_price";
	
	public static final String ADD_PRICE_URL ="/admin/add_price";
	public static final String GET_OLDEST_CLEARANCE_URL="/admin/search_oldest_clearance";
	public static final String GET_PRICE_URL ="/get_item_price";
	public static final String ADD_PRICE_PAGE ="price/add_price";	
	
	public static final String DELETE_PRICE_URL ="/admin/delete_price";
	public static final String BULK_PRICE_URL ="/admin/bulk_price";

	/**
	 * The price event pages section ends
	 */
	
	public static final String LOGIN_PAGE="login/login";
	public static final String LOGIN_URL="/login";
	public static final String MANAGE_USER_PAGE="account/manage_user";
	public static final String MANAGE_USER_URL="/account/manage_user";
	
	public static final String CHANGE_PASSWORD_URL="/account/change_password";
	public static final String CHANGE_PASSWORD_PAGE="account/manage_password";	
	
	public static final String SEARCH_ACCOUNT_URL="/admin/search_account";
	
	public static final String CREATE_ACCOUNT_URL="/admin/create_account";
	public static final String CREATE_ACCOUNT_PAGE="account/add_user";
	
	public static final String MANAGE_ACCOUNTS_URL="/admin/manage_accounts";
	public static final String MANAGE_ACCOUNTS_PAGE="account/manage_users";
	
	
	public static final String EDIT_ACCOUNT_URL="/admin/edit_account";
	public static final String EDIT_ACCOUNT_PAGE="account/edit_user";
	
	public static final String APPROVE_ACCOUNT_URL="/admin/approve_account";
	public static final String DELETE_ACCOUNT_URL="/admin/delete_account";
	public static final String DISABLE_ACCOUNT_URL="/admin/disable_account";
	public static final String ACTIVATE_ACCOUNT_URL="/admin/activate_account";
	
	
	public static final String BULK_ACCOUNTS_URL="/admin/bulk_account_action";
	
	public static final String REGISTER_USER_URL="/register";
	public static final String REGISTER_USER_PAGE="login/user_register";

	/**
	 * The sale screen section starts
	 */

	public static final String LAST_TXN_RCPT_URL="/pos/last_txn_rcpt";
	public static final String LAST_TXN_RCPT_PAGE="/sale/receipt_print";
	
	public static final String DELETE_SALE_TXN_URL="/admin/delete_sale_txn";
	
	public static final String EDIT_SALE_TXN_URL="/admin/edit_sale_txn";
	public static final String EDIT_SALE_TXN_PAGE="sale/edit_sale_txn";
	public static final String POS_URL="/pos/sale";
	public static final String SALES_URL="/pos/sale";
	public static final String SALES_PAGE="sale/sales";
	public static final String AUTO_ITEM_PAGE="sale/sale_txn";
	
	public static final String RETURN_TXN_URL="/pos/return_txn";
	public static final String RETURN_TXN_PAGE="return/return_txn";
	
	
	public static final String SKU_LOOKUP_URL="/sku_lookup";
	public static final String SALEITEM_LOOKUP_URL="/sale_item_lookup";
	
	/**
	 * The sale screen section ends
	 */
	/**
	 * The daily deed section starts
	 */
	public static final String STORE_STATUS_URL="/pos/store_status";
	public static final String RESET_STORE_STATUS_URL="/pos/reset_store_status";
	
	public static final String STORE_OPEN_URL="/pos/open_store";
	public static final String STORE_OPEN_PAGE="dailydeeds/store_open";
	
	public static final String CREATE_REGISTER_URL="/pos/create_new_register";
	
	public static final String REGISTER_OPEN_URL="/pos/open_register";
	public static final String REGISTER_OPEN_PAGE="dailydeeds/register_open";
	
	
	public static final String STORE_CLOSE_URL="/pos/close_store";
	public static final String STORE_CLOSE_PAGE="dailydeeds/store_close";
	
	public static final String REGISTER_CLOSE_URL="/pos/close_register";
	public static final String REGISTER_CLOSE_PAGE="dailydeeds/register_close";
	
	/**
	 * The daily deed section ends
	 */
	

	/**
	 * The Transction section starts
	 */
	public static final String TXN_SAVE_URL="/pos/save_txn";
	public static final String RETURN_TXN_SAVE_URL="/pos/save_return_txn";
	public static final String TXN_EDITED_SAVE_URL="/pos/save_edited_txn";
	public static final String TXN_RCPT_PRINT_URL="/pos/receipt_print";
	public static final String TXN_RCPT_RETURN_PRINT_URL="/pos/return_receipt_print";
	public static final String TXN_RCPT_VIEW_URL="/pos/view_last_receipt";
	public static final String VIEW_RCPT_TXN_NO_URL="/pos/view_txn_receipt";
	public static final String TXN_POST_VOID_URL="/pos/post_void_txn";
	public static final String TXN_PROCESS_EXPENSE_URL="/pos/process_expense";
	public static final String TXN_EXPENSE_URL="/pos/expenses";
	public static final String TXN_EXPENSE_VIEW_RECEIPT_URL="/pos/view_expense_receipt";
	public static final String TXN_EXPENSE_PRINT_RECEIPT_URL="/pos/print_expense_receipt";
	public static final String TXN_EXPENSE_PAGE="nosale/expenses";
	

	
	/**
	 * The Transaction section ends
	 */	
	
	/**
	 * The account payment pages section starts
	 */
	public static final String PAYMENT_ACCOUNT_DTLS_URL ="/admin/payment_account";
	public static final String SAVE_PAYMENT_DTLS_URL="admin/save_payment";
	public static final String PAYMENT_ACCOUNT_VIEW_RECEIPT_URL="/admin/view_payment_receipt";
	public static final String PAYMENT_ACCOUNT_PRINT_RECEIPT_URL="/admin/print_payment_receipt";

	/**
	 * The utility pages section starts
	 */
	public static final String PI_POS_UTILS_URL = "/admin/pi_pos_utils";
	public static final String PI_POS_UTILS_PAGE = "utils/receipt_generator";

	/**
	 * The utility pages section ends
	 */
	
	/**
	 * The GSTR pages section starts
	 */
	public static final String GSTR1_GENERATION_URL = "/admin/generate_gstr1";
	public static final String GSTR1_JSON_GENERATION_URL = "/admin/get_gstr1_json";
	public static final String GSTR1_GENERATION_PAGE = "gstr/gstr1";

	/**
	 * The GSTR pages section ends
	 */
	
	/**
	 * The Attribute pages section starts
	 */
	public static final String ATTR_VAL_ACTION_URL = "/admin/attr_val_action";
	public static final String ATTR_VAL_ACTION_PAGE = "item/attributes/add_attr_vals";

	
	public static final String SAVE_ATTR_VAL_ORDER_URL = "/admin/save_attr_val_order";
	public static final String SAVE_ATTR_DETAILS_URL = "/admin/save_attr_details";
	public static final String SAVE_ATTR_VAL_DETAILS_URL = "/admin/save_attr_val_details";
	public static final String DELETE_ATTR_VAL_DETAILS_URL = "/admin/delete_attr_val_details";
	
	/**
	 * The Attributepages section ends
	 */	
	

	/**
	 * The Setup pages section starts
	 */
	public static final String STORE_SETUP_URL = "/start";
	public static final String SAVE_STORE_SETUP_URL = "/save_pi_pos_setup";
	public static final String STORE_SETUP_PAGE = "setup/initial_setup";

	
	/**
	 * The Setup pages section ends
	 */	
	
	
}

