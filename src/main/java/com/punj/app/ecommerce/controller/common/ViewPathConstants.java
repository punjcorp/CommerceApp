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

	/**
	 * The common pages section starts
	 */
	public static final String LIST_ITEM_URL = "/list_item";
	public static final String DISPLAY_PAGE = "common/display";

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
	/**
	 * The item pages section ends
	 */
	/**
	 * The purchase order pages section starts
	 */
	public static final String SEARCH_ORDER_URL = "/search_order";
	public static final String MANAGE_ORDER_PAGE = "order/manage_order";
	public static final String MANAGE_ORDER_URL= "/manage_order";
	
	public static final String ADD_ORDER_URL = "/add_order";
	public static final String ADD_ORDER_PAGE = "order/add_order";	
	
	public static final String EDIT_ORDER_URL = "/edit_order";
	public static final String EDIT_ORDER_PAGE= "order/edit_order";
	
	public static final String APPROVE_ORDER_URL = "/approve_order";
	public static final String DELETE_ORDER_URL = "/delete_order";
	
	public static final String BULK_ORDER_URL = "/bulk_order_action";
	public static final String RECEIVE_ORDER_URL = "/receive_order";
	public static final String RECEIVE_ORDER_PAGE ="order/receive_order";
	
	public static final String PRINT_ORDERS_URL = "/print_orders";
	public static final String PRINT_ORDER_URL = "/print_order";
	
	/**
	 * The purchase order pages section ends
	 */
	/**
	 * The supplier pages section starts
	 */
	public static final String SEARCH_SUPPLIER_URL = "/search_supplier";
	public static final String MANAGE_SUPPLIER_URL = "/manage_supplier";
	public static final String MANAGE_SUPPLIER_PAGE = "supplier/manage_supplier";
	
	public static final String EDIT_SUPPLIER_URL ="/edit_supplier";
	public static final String EDIT_SUPPLIER_PAGE ="supplier/edit_supplier";
	
	public static final String ADD_SUPPLIER_URL ="/add_supplier";
	public static final String ADD_SUPPLIER_PAGE ="supplier/add_supplier";	
	
	public static final String DELETE_SUPPLIER_URL ="/delete_supplier";
	public static final String BULK_SUPPLIER_URL ="/bulk_supplier_action";
	
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
	public static final String ADD_INV_ADJUST_PAGE ="inventory/add_inv_adjust";	
	
	public static final String DELETE_INV_ADJUST_URL ="/delete_inv_adjust";
	public static final String BULK_INV_ADJUST_URL ="/bulk_inv_adjust_action";
	
	/**
	 * The inventory adjustment pages section ends
	 */
}

