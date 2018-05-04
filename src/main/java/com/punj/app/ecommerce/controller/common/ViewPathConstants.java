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
	public static final String HOME_URL = "/home";
	public static final String HOME_PAGE = "home";
	public static final String ERROR_PAGE = "error";

	public static final String ACCESS_DENIED_URL = "/403";
	public static final String ACCESS_DENIED_PAGE = "access_denied";

	public static final String LOGOUT_URL = "/logout";
	public static final String LOGOUT_REDIRECT_LOGIN_PAGE = "redirect:/login?logout";
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
	public static final String EDIT_SKU_PAGE = "item/edit_sku";	
	
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
	
	public static final String BULK_ORDER_URL = "/admin/bulk_order_action";
	public static final String VIEW_ORDER_BILL_URL = "/admin/view_order_bill";
	public static final String RECEIVE_ORDER_URL = "/admin/receive_order";
	public static final String RECEIVE_ORDER_PAGE ="order/receive_order";
	
	public static final String PRINT_ORDERS_URL = "/admin/print_orders";
	
	
	/**
	 * The purchase order pages section ends
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
	public static final String SUPPLIER_PAYMENT_PAGE ="/supplier/supplier_payments";
	
	/**
	 * The supplier pages section ends
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
	public static final String SEARCH_INV_ADJUST_URL = "/search_inv_adjust";
	public static final String MANAGE_INV_ADJUST_URL = "/manage_inv_adjust";
	public static final String MANAGE_INV_ADJUST_PAGE = "inventory/manage_inv_adjust";
	
	public static final String EDIT_INV_ADJUST_URL ="/edit_inv_adjust";
	public static final String EDIT_INV_ADJUST_PAGE ="inventory/edit_inv_adjust";
	
	public static final String APPROVE_INV_ADJUST_URL ="/approve_inv_adjust";
	
	public static final String ADD_INV_ADJUST_URL ="/add_inv_adjust";
	public static final String GET_ITEM_INV_URL ="/get_item_inv";
	public static final String ADD_INV_ADJUST_PAGE ="inventory/add_inv_adjust";	
	
	public static final String DELETE_INV_ADJUST_URL ="/delete_inv_adjust";
	public static final String BULK_INV_ADJUST_URL ="/bulk_inv_adjust_action";

	public static final String PRINT_INV_ADJUSTS_URL = "/print_inv_adjusts";
	public static final String PRINT_INV_ADJUST_URL = "/print_inv_adjust";	
	/**
	 * The inventory adjustment pages section ends
	 */
	/**
	 * The lookup pages section starts
	 */
	public static final String LOOKUP_ITEM_URL = "/item_lookup";
	public static final String LOOKUP_ITEM_PAGE= "/lookup/item_lookup";
	/**
	 * The lookup pages section ends
	 */	
	/**
	 * The price event pages section starts
	 */
	public static final String SEARCH_PRICE_URL = "/search_price";
	public static final String MANAGE_PRICE_URL = "/manage_price";
	public static final String MANAGE_PRICE_PAGE = "price/manage_price";
	
	public static final String PRICE_DETAIL_PAGE ="price/price_details";	
	public static final String EDIT_PRICE_URL ="/edit_price";
	public static final String EDIT_PRICE_PAGE ="price/edit_price";
	
	public static final String APPROVE_PRICE_URL ="/approve_price";
	
	public static final String ADD_PRICE_URL ="/add_price";
	public static final String GET_PRICE_URL ="/get_item_price";
	public static final String ADD_PRICE_PAGE ="price/add_price";	
	
	public static final String DELETE_PRICE_URL ="/delete_price";
	public static final String BULK_PRICE_URL ="/bulk_price";

	/**
	 * The price event pages section ends
	 */
	
	public static final String LOGIN_PAGE="login/login";
	public static final String LOGIN_URL="/login";
	public static final String MANAGE_USER_PAGE="account/manage_user";
	
	public static final String REGISTER_USER_URL="/register";
	public static final String REGISTER_USER_PAGE="login/user_register";

	/**
	 * The sale screen section starts
	 */

	public static final String POS_URL="/pos/sale";
	public static final String SALES_URL="/pos/sales";
	public static final String SALES_PAGE="sale/sales";
	public static final String AUTO_ITEM_PAGE="sale/auto_item";
	
	public static final String SKU_LOOKUP_URL="/sku_lookup";
	public static final String SALEITEM_LOOKUP_URL="/sale_item_lookup";
	
	/**
	 * The sale screen section ends
	 */
	/**
	 * The daily deed section starts
	 */
	public static final String STORE_OPEN_URL="/pos/open_store";
	public static final String STORE_OPEN_PAGE="dailydeeds/store_open";
	
	public static final String REGISTER_OPEN_URL="/pos/open_register";
	public static final String REGISTER_OPEN_PAGE="dailydeeds/register_open";
	
	/**
	 * The daily deed section ends
	 */
	

	/**
	 * The Transction section starts
	 */
	public static final String TXN_SAVE_URL="/pos/save_txn";
	public static final String TXN_RCPT_PRINT_URL="/pos/receipt_print";
	public static final String TXN_RCPT_VIEW_URL="/pos/view_last_receipt";
	public static final String TXN_POST_VOID_URL="/pos/post_void_txn";
	public static final String TXN_PROCESS_EXPENSE_URL="/pos/process_expense";
	public static final String TXN_EXPENSE_URL="/pos/expenses";
	public static final String TXN_EXPENSE_PAGE="/nosale/expenses";
	

	
	/**
	 * The Transaction section ends
	 */	
	
	/**
	 * The account payment pages section starts
	 */
	public static final String PAYMENT_ACCOUNT_DTLS_URL ="/admin/payment_account";
	public static final String SAVE_PAYMENT_DTLS_URL="admin/save_payment";
	
}

