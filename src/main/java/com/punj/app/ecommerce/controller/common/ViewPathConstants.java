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
	
	public static final String MANAGE_ITEM_PAGE="item/manage_item";
	public static final String SEARCH_ITEM_URL="/search_item";
	
	public static final String LIST_ITEM_URL="/list_item";
	public static final String DISPLAY_PAGE="common/display";
	
	public static final String HOME_URL="/home";
	public static final String HOME_PAGE="home";
	public static final String ERROR_PAGE="error";

	public static final String ACCESS_DENIED_URL="/403";
	public static final String ACCESS_DENIED_PAGE="access_denied";	
	
	public static final String LOGOUT_URL="/logout";
	public static final String LOGOUT_REDIRECT_LOGIN_PAGE="redirect:/login?logout";		
	
	
	public static final String SEARCH_ORDER_URL="/search_order";
	public static final String MANAGE_ORDER_PAGE="order/manage_order";
	
	public static final String SEARCH_SUPPLIER_URL="/search_supplier";
	public static final String MANAGE_SUPPLIER_PAGE="supplier/manage_supplier";
	
	
	public static final String ITEM_DETAIL_URL="/item_detail";
	public static final String ITEM_DETAIL_PAGE="item/item_detail";
	
}
