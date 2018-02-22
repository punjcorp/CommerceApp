/**
 * 
 */
package com.punj.app.ecommerce.controller.common;

/**
 * @author admin
 *
 */
public final class MVCConstants {

	private MVCConstants() {
		throw new IllegalStateException("MVC Constant class");
	}

	public static final Boolean LOC_PARTIAL= Boolean.TRUE;
	public static final Boolean LOC_FULL= Boolean.FALSE;
	public static final String TNDR_CASH = "CASH";
	public static final String SUCCESS = "success";
	public static final String ALERT = "alert";
	public static final String PAGER= "pager";
	public static final String YES="Y";
	
	public static final String ID_PARAM="id";
	public static final String TENDER_ID_PARAM="tenderId";
	public static final String PAGE_PARAM="page";
	public static final String ITEM_ID_PARAM="itemId";
	public static final String LOCATION_ID_PARAM="locationId";
	public static final String REASON_CODE_ID_PARAM="reasonCodeId";

	public static final String STATUS_RECEIVED="R";
	public static final String STATUS_APPROVED="A";
	public static final String STATUS_CREATED="C";
	public static final String STATUS_ACTIVE="A";

	public static final String ACTION_PARAM="action";
	public static final String ACTION_NEW="NEW";
	public static final String ACTION_NEW_SAVE="NEW_SAVE";
	public static final String ACTION_NEW_APPROVE="NEW_APPROVE";
	public static final String ACTION_EDIT="EDIT";
	public static final String ACTION_EDIT_SAVE="EDIT_SAVE";
	public static final String ACTION_EDIT_APPROVE="EDIT_APPROVE";

	
	public static final String REPORT_OUTPUT_PDF="application/pdf";
	public static final String SUB_REPORT_DIR="SUBREPORT_DIR";
	public static final String REPORT_PERMISSION_PRINTING="PRINTING";
	
	public static final String ADD_ORDER_ITEM_PARAM= "addOrderItem";
	public static final String REMOVE_ORDER_ITEM_PARAM= "removeOrderItem";
	public static final String SAVE_ORDER_PARAM= "saveOrder";
	public static final String SAVE_ORDERS_PARAM= "saveOrders";
	public static final String APPROVE_ORDERS_PARAM= "approveOrders";
	public static final String DELETE_ORDERS_PARAM= "deleteOrders";
	public static final String RECEIVE_ORDER_PARAM= "receiveOrder";
	public static final String RECEIVE_ALL_ORDERS_PARAM="receiveAllOrder";
	public static final String ORDER_ID_PARAM= "orderId";
		
	public static final String ORDERS_REPORT="/reports/order/orders.jrxml";
	public static final String ORDERS_ITEMS_REPORT="/reports/order/order_items.jrxml";
	public static final String ORDER_REPORT="/reports/order/purchase_order.jrxml";
	public static final String ORDER_ITEMS_REPORT="/reports/order/purchase_order_item.jrxml";
	public static final String ORDERS_REPORT_NAME="order_list_report.pdf";


	public static final String ORDER_ITEM_REPORT_PARAM="ORDER_ITEM_REPORT";
	public static final String SUPPLIER_REPORT_PARAM="SUPPLIER_REPORT";
	public static final String SUPPLIER_ADDRESS_REPORT_PARAM="SUPPLIER_ADDRESS_REPORT";
	public static final String DELIVERY_ADDRESS_REPORT_PARAM="DELIVERY_ADDRESS_REPORT";
	public static final String SUPPLIER_DATA_PARAM="SUPPLIER_DATA";
	public static final String SUPPLIER_ADDRESS_DATA_PARAM="SUPPLIER_ADDRESS_DATA";
	public static final String DELIVERY_ADDRESS_DATA_PARAM="DELIVERY_ADDRESS_DATA";
	
	
	
	public static final String PRIMARY_ADDRESS_PARAM="primaryAddress";
	public static final String ADDRESSES_PARAM="addresses";
	
	
	public static final String SUPPLIER_REPORT="/reports/supplier/supplier.jrxml";
	public static final String ADDRESS_REPORT="/reports/address/address.jrxml";
	
	public static final String DAILY_DEED_BEAN= "dailyDeedBean";
	public static final String LOCATION_BEANS= "locations";
	public static final String ITEMS_BEAN= "items";
	public static final String ORDERS_BEAN= "orders";
	public static final String ORDER_BEAN= "orderBean";
	public static final String CARD_BEAN="cardBean";
	public static final String SUPPLIER_BEAN= "supplierBean";
	public static final String ITEM_BEAN= "itemBean";
	public static final String INV_ADJUST_BEAN= "invAdjustBean";
	public static final String INV_ADJUSTS_BEAN= "invAdjusts";
	public static final String SUPPLIERS_BEAN= "suppliers";
	public static final String SEARCH_BEAN= "searchBean";
	public static final String COLOR_LIST_BEAN="colorList";
	public static final String SIZE_LIST_BEAN="sizeList";
	public static final String PRICE_BEAN="priceBean";
	public static final String PRICE_BEAN_DTO="priceBeanDTO";
	
	public static final String ADD_SUPPLIER_ADDRESS_PARAM= "addSupplierAddress";
	public static final String REMOVE_SUPPLIER_ADDRESS_PARAM= "removeSupplierAddress";
	public static final String SAVE_SUPPLIER_PARAM= "saveSupplier";
	public static final String SUPPLIER_ID_PARAM="supplierId";
	public static final String SAVE_SUPPLIERS_PARAM= "saveSuppliers";
	public static final String DELETE_SUPPLIERS_PARAM="deleteSuppliers";
	
	public static final String SEARCH_PARAM= "search";
	public static final String APPROVE_PARAM= "approve";
	public static final String SAVE_PARAM= "save";
	public static final String DELETE_PARAM= "delete";
	public static final String ADD_INV_ADJUST_ITEM_PARAM= "addInvAdjustItem";
	public static final String REMOVE_INV_ADJUST_ITEM_PARAM= "removeInvAdjustItem";
	public static final String SAVE_INV_ADJUST_PARAM= "saveInvAdjust";
	public static final String APPROVE_INV_ADJUST_PARAM= "approveInvAdjust";
	public static final String SAVE_INV_ADJS_PARAM= "saveInvAdjusts";
	public static final String APPROVE_INV_ADJS_PARAM= "approveInvAdjusts";
	public static final String DELETE_INV_ADJS_PARAM= "deleteInvAdjusts";
	public static final String SAVE_EDIT_INV_ADJUST_PARAM= "saveEditedInvAdjust";
	public static final String APPROVE_EDIT_INV_ADJUST_PARAM= "approveEditedInvAdjust";
	public static final String INV_ADJUST_ITEM_INV_PARAM= "getItemInv";
	
	public static final String PRICE_ID_PARAM="itemPriceId";
	public static final String INV_ADJUST_ID_PARAM="invAdjustId";
	public static final String SAVE_INV_ADJUSTS_PARAM= "saveInvAdjustItems";
	public static final String DELETE_INV_ADJUSTS_PARAM="deleteInvAdjustItems";
	
	public static final String INV_ADJUST_REPORT="/reports/inventory/inv_adjust.jrxml";
	public static final String INV_ADJUST_ITEMS_REPORT="/reports/inventory/inv_adjust_items.jrxml";	
	public static final String INV_ADJUST_ITEMS_REPORT_PARAM="INV_ADJUST_ITEMS_REPORT";
	
	public static final String INV_ADJUSTMENT_REPORT="/reports/inventory/list/inv_adjustments.jrxml";
	public static final String INV_ADJUSTMENT_ITEMS_REPORT="/reports/inventory/list/inv_adjustment_items.jrxml";	
	public static final String INV_ADJUSTMENT_ITEMS_REPORT_PARAM="INV_ADJUSTMENT_ITEMS_REPORT";
	
	public static final String OPEN_STORE_PARAM= "openStore";
	public static final String ADD_DENOMINATION_PARAM= "addDenomination";
	public static final String REMOVE_DENOMINATION_PARAM= "removeDenomination";
	
	
	public static final String SAVE_PRICE_PARAM= "savePrice";
	public static final String APPROVE_PRICE_PARAM= "approvePrice";
	public static final String DELETE_PRICE_PARAM= "deletePrice";

	public static final String ADDRESS_PARAM="addressBean";
	
	public static final String ERROR_MSG="commerce.screen.common.error";
}
