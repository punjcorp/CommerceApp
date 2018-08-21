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
	public static final String FAILURE = "failure";
	public static final String ALERT = "alert";
	public static final String PAGER= "pager";
	public static final String YES="Y";
	public static final String NO="N";
	
	public static final String ROLE_ADMIN="ADMIN";
	public static final String ROLE_CASHIER="CASHIER";
	
	
	
	public static final String WIDGET_REVENUE="Revenue";
	public static final String WIDGET_SALES_COUNT="Sales Count";
	public static final String WIDGET_CUSTOMER_COUNT="Customer Count";
	public static final String WIDGET_PROFIT="Gross Profit";
	public static final String WIDGET_DISCOUNT_AMOUNT="Discount";
	public static final String WIDGET_DISCOUNT_PERCENTAGE="Discount %";
	public static final String WIDGET_BASKET_SIZE="Basket Size";
	public static final String WIDGET_BASKET_VALUE="Basket Value";
	
	
	public static final String PRICE_TYPE_PERMANENT="1";
	public static final String PRICE_TYPE_PROMOTION="2";
	public static final String PRICE_TYPE_CLEARANCE="3";
	public static final String PRICE_TYPE_CLEARANCE_RESET="4";
	
	public static final String PRICE_TYPE_PERMANENT_DESC="Permanent";
	public static final String PRICE_TYPE_PROMOTION_DESC="Promotion";
	public static final String PRICE_TYPE_CLEARANCE_DESC="Clearance";
	public static final String PRICE_TYPE_CLEARANCE_RESET_DESC="Clearance Reset";
	
	public static final String NEXT_LEVEL_CREATED= "C";
	public static final String NEXT_LEVEL_APPROVED= "A";	
	public static final String NEXT_LEVEL_NOT_CREATED= "N";	

	
	public static final String SCREEN_MANAGE_ACCOUNTS_SEARCH_CRITERIA="ManageAccountsSearchCriteria";
	public static final String SCREEN_MANAGE_ACCOUNTS_SEARCH_CRITERIA_PAGE=SCREEN_MANAGE_ACCOUNTS_SEARCH_CRITERIA+"Page";
	
	
	
	
	
	
	public static final String LAST_PAYMENT_NO="LAST_PAYMENT_NO";
	public static final String LAST_TXN_NO="LAST_TXN_NO";
	public static final String TXN_ID_PARAM="txnId";
	public static final String RCPT_PARAM="_RCPT";
	public static final String PAYMENT_RCPT_PARAM="PAYMENT_RCPT";
	public static final String PAYMENT_JASPER_PARAM="PAYMENT_JASPER";
	public static final String RCPT_JASPER_PARAM="_JASPER";
	public static final String RCPT_SALE_STORE="Sale Store Copy";
	public static final String RCPT_SALE_CUSTOMER="Sale Customer Copy";
	public static final String RCPT_SALE_GIFT="Gift Receipt";
	public static final String RCPT_NO_SALE="No Sale Receipt";
	public static final String PAYMENT_ID_PARAM="paymentId";
	
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static final String TXN_SALE_PARAM="SALE";
	public static final String TXN_RETURN_PARAM="RETURN";

	public static final Integer STYLE_LEVEL=1;
	public static final Integer ITEM_LEVEL=2;
	public static final Integer UPC_LEVEL=3;
	
	public static final String FUNCTIONALITY_NEW_STYLE_PARAM="NSTYLE";
	public static final String FUNCTIONALITY_APPROVE_STYLE_PARAM="ASTYLE";
	public static final String FUNCTIONALITY_NEW_ITEM_PARAM="NITEM";
	public static final String FUNCTIONALITY_APPROVE_ITEM_PARAM="AITEM";
	public static final String FUNCTIONALITY_UPC_PARAM="UPC";
	
	
	public static final String SAVE_STYLE_PARAM="saveStyle";
	public static final String APPROVE_STYLE_PARAM="approveStyle";
	public static final String SAVE_APPROVE_STYLE_PARAM="saveApproveStyle";
	public static final String SAVE_SKU_PARAM="saveSKU";
	public static final String SAVE_APPROVE_SKU_PARAM="saveApproveSKU";
	
	
	public static final String REFERRER_URL_PARAM="referrerURL";
	public static final String ID_PARAM="id";
	public static final String TENDER_ID_PARAM="tenderId";
	public static final String PAGE_PARAM="page";
	public static final String STYLE_ID_PARAM="styleId";
	public static final String ITEM_ID_PARAM="itemId";
	public static final String LOCATION_ID_PARAM="locationId";
	
	public static final String LOC_NAME_PARAM="locName";
	public static final String REGISTER_ID_PARAM="registerId";
	public static final String REG_NAME_PARAM="regName";	
	public static final String B_DATE_PARAM="businessDate";
	public static final String DEFAULT_TENDER_PARAM="defaultTender";
	public static final String REASON_CODE_ID_PARAM="reasonCodeId";
	
	public static final String REASON_TYPE_PO_RETURN="POR";

	
	public static final String AJAX_STATUS_FAILURE="F";
	public static final String AJAX_STATUS_SUCCESS="S";
	public static final String AJAX_STATUS_PROGRESS="P";
	
	public static final String STATUS_RECEIVED_DESC="Received";
	public static final String STATUS_APPROVED_DESC="Approved";
	public static final String STATUS_DISABLED_DESC="Disabled";
	public static final String STATUS_CREATED_DESC="Created";
	public static final String STATUS_ACTIVE_DESC="Active";	
	
	public static final String STATUS_RECEIVED="R";
	public static final String STATUS_APPROVED="A";
	public static final String STATUS_DISABLED="I";
	public static final String STATUS_CREATED="C";
	public static final String STATUS_ACTIVE="A";

	public static final String ACTION_PARAM="action";
	public static final String ACTION_NEW="NEW";
	public static final String ACTION_NEW_SAVE="NEW_SAVE";
	public static final String ACTION_NEW_APPROVE="NEW_APPROVE";
	public static final String ACTION_EDIT="EDIT";
	public static final String ACTION_EDIT_SAVE="EDIT_SAVE";
	public static final String ACTION_EDIT_APPROVE="EDIT_APPROVE";
	
	public static final String ACTION_APPROVE_ALL="APPROVEALL";
	public static final String ACTION_DISABLE_ALL="DISABLEALL";
	public static final String ACTION_DELETE_ALL="DELETEALL";

	
	public static final String REPORT_OUTPUT_PDF="application/pdf";
	public static final String SUB_REPORT_DIR="SUBREPORT_DIR";
	public static final String REPORT_PERMISSION_PRINTING="PRINTING";
	
	public static final String EXPENSE_PARAM= "expenseBean";
	
	public static final String ADD_ITEM_IMAGE_PARAM= "addItemImage";
	public static final String REMOVE_ITEM_IMAGE_PARAM= "removeItemImage";
	public static final String SAVE_ITEM_PARAM= "saveItem";
	
	public static final String SAVE_ACCOUNT_PARAM= "saveAccount";
	public static final String APPROVE_ACCOUNT_PARAM= "approveAccount";
	public static final String APPROVE_ACCOUNTS_PARAM= "approveAccounts";
	public static final String DELETE_ACCOUNTS_PARAM= "deleteAccounts";
	public static final String DISABLE_ACCOUNTS_PARAM= "disableAccounts";
	
	public static final String SAVE_ORDER_RETURN_PARAM= "saveOrderReturn";
	public static final String APPROVE_ORDER_RETURN_PARAM= "approveOrderReturn";
	
	public static final String ORDER_BILL_PARAM= "billId";
	public static final String ADD_ORDER_ITEM_PARAM= "addOrderItem";
	public static final String REMOVE_ORDER_ITEM_PARAM= "removeOrderItem";
	public static final String ADD_ORDER_BILL_PARAM= "addOrderBill";
	public static final String REMOVE_ORDER_BILL_PARAM= "removeOrderBill";	
	public static final String SAVE_ORDER_PARAM= "saveOrder";
	public static final String APPROVE_ORDER_PARAM= "approveOrder";
	public static final String SAVE_ORDERS_PARAM= "saveOrders";
	public static final String APPROVE_ORDERS_PARAM= "approveOrders";
	public static final String DELETE_ORDERS_PARAM= "deleteOrders";
	public static final String RECEIVE_ORDER_PARAM= "receiveOrder";
	public static final String RECEIVE_ALL_ORDERS_PARAM="receiveAllOrder";
	public static final String ORDER_ID_PARAM= "orderId";
	public static final String ORDER_RETURN_ID_PARAM= "orderReturnId";
	public static final String USERNAME_PARAM= "username";
	public static final String PASSWORD_BEAN_PARAM= "passwordBean";
	public static final String USER_PASSWORD_BEAN_PARAM= "userDetails";
	public static final String USER_BEAN_PARAM= "registerUserBean";
		
	public static final String ORDERS_REPORT="/reports/order/orders.jrxml";
	public static final String ORDERS_ITEMS_REPORT="/reports/order/order_items.jrxml";
	
	public static final String ORDER_REPORT="/reports/order/purchase_order.jasper";
	public static final String ORDER_HEADER_REPORT="/reports/order/purchase_order_header.jasper";
	public static final String ORDER_FOOTER_REPORT="/reports/order/purchase_order_footer.jasper";
	public static final String ORDER_ITEMS_REPORT="/reports/order/purchase_order_item.jasper";
	public static final String SUPPLIER_REPORT="/reports/supplier/supplier.jasper";
	public static final String DELIEVERY_LOCATION_REPORT="/reports/common/location.jasper";
	
	
	
	public static final String ORDERS_REPORT_NAME="order_list_report.pdf";

	public static final String EXPENSE_RECEIPT_REPORT="/reports/expenses/expense_receipt.jasper";
	public static final String EXPENSE_RECEIPT_TENDERS_REPORT="/reports/expenses/expense_tenders.jasper";
	public static final String EXPENSE_TENDER_REPORT_PARAM="EXPENSE_TENDER_REPORT";	
	
	public static final String PAYMENT_RECEIPT_REPORT="/reports/supplier/supplier_payment_receipt.jasper";
	public static final String PAYMENT_RECEIPT_TENDERS_REPORT="/reports/supplier/supplier_payment_tenders.jasper";
	public static final String PAYMENT_TENDER_REPORT_PARAM="PAYMENT_TENDER_REPORT";
	
	public static final String CUSTOMER_PAYMENT_RECEIPT_REPORT="/reports/customer/customer_payment_receipt.jasper";
	public static final String CUSTOMER_PAYMENT_RECEIPT_TENDERS_REPORT="/reports/customer/customer_payment_tenders.jasper";
	
	public static final String TXN_RECEIPT_REPORT="/reports/transaction/sale_receipt.jasper";
	public static final String TXN_RECEIPT_ITEMS_REPORT="/reports/transaction/sale_receipt_items.jasper";
	public static final String TXN_ITEM_REPORT_PARAM="TXN_ITEM_REPORT";


	public static final String TXN_REG_CLOSE_REPORT="/reports/dailydeeds/register_close_report.jasper";
	public static final String TXN_STORE_CLOSE_REPORT="/reports/dailydeeds/store_close_report.jasper";

	public static final String ORDER_HEADER_REPORT_PARAM="ORDER_HEADER_REPORT";
	public static final String ORDER_FOOTER_REPORT_PARAM="ORDER_FOOTER_REPORT";
	public static final String ORDER_ITEMS_REPORT_PARAM="ORDER_ITEMS_REPORT";
	public static final String SUPPLIER_REPORT_PARAM="SUPPLIER_REPORT";
	public static final String SUPPLIER_ADDRESS_REPORT_PARAM="SUPPLIER_ADDRESS_REPORT";
	public static final String DELIVERY_LOCATION_REPORT_PARAM="DELIVERY_LOCATION_REPORT";
	
	public static final String ORDER_DATA_PARAM="ORDER_DATA";
	public static final String ORDER_ITEMS_DATA_PARAM="ORDER_ITEMS_DATA";
	public static final String SUPPLIER_DATA_PARAM="SUPPLIER_DATA";
	public static final String DELIVERY_LOCATION_DATA_PARAM="DELIVERY_LOCATION_DATA";
	
	
	public static final String TAX_SGST="SGST";
	public static final String TAX_CGST="CGST";
	public static final String TAX_IGST="IGST";
	
	
	public static final String TXN_STATUS_STARTED = "STARTED";
	public static final String TXN_STATUS_COMPLETED = "COMPLETED";
	public static final String TXN_STATUS_SUSPEND = "SUSPENDED";
	public static final String TXN_STATUS_CANCEL = "CANCELLED";	
	
	public static final String TXN_OPEN_STORE = "OPEN_STORE";
	public static final String TXN_CLOSE_STORE = "CLOSE_STORE";
	public static final String TXN_OPEN_REGISTER = "OPEN_REGISTER";
	public static final String TXN_CLOSE_REGISTER = "CLOSE_REGISTER";
	public static final String TXN_NOSALE= "NOSALE";

	
	public static final String SALE_ITEM_PARAM="SALE_ITEM";
	public static final String TAX_ITEM_PARAM="TAX_ITEM";
	public static final String TAX_INCLUSIVE_PARAM="inclusive";
	public static final String TAX_EXCLUSIVE_PARAM="exclusive";
	public static final String TENDER_ITEM_PARAM="TENDER_ITEM";
	public static final String COMPLETED="COMPLETED";
	public static final String MANUAL="MANUAL";
	public static final String ADD="ADD";
	public static final String SUBTRACT="SUBTRACT";
	public static final String SUPPLIER= "SUPPLIER";
	public static final String CUSTOMER= "CUSTOMER";
	
	
	public static final String PRIMARY_ADDRESS_PARAM="primaryAddress";
	public static final String ADDRESSES_PARAM="addresses";
	
	
	public static final String ADDRESS_REPORT="/reports/address/address.jasper";
	
	public static final String DAILY_DEED_BEAN= "dailyDeedBean";
	public static final String DAILY_TOTALS_BEAN= "dailyTotalsBean";
	public static final String SALE_HEADER_BEAN= "saleHeaderBean";
	public static final String CUSTOMER_BEAN= "customerBean";
	public static final String TXN_HEADER_BEAN= "txnHeaderBean";
	public static final String LOCATION_BEANS= "locations";
	public static final String ROLE_BEANS= "roles";
	public static final String TENDER_BEANS= "tenders";
	public static final String REASON_CODES= "reasonCodes";
	public static final String REGISTER_BEANS= "registers";
	public static final String ITEMS_BEAN= "items";
	public static final String ORDERS_BEAN= "orders";
	public static final String ORDER_RETURNS_BEAN= "orderReturns";
	public static final String USERS_BEAN= "users";
	public static final String DASHBOARD_REPORTS_BEAN= "reports";
	
	
	
	public static final String REPORT_ORDER_PRINT= "P";
	public static final String REPORT_ORDER_VIEW= "V";
	public static final String LAST_ORDER_BEAN= "lastOrder";
	public static final String LAST_ORDER_REPORT= "lastOrderReport";
	public static final String LAST_ORDER_ID_REPORT= "lastOrderIdReport";
	public static final String LAST_ORDER_REPORT_JASPER= "lastOrderReportJasper";
	public static final String LAST_ORDER_REPORT_PDF= "lastOrderReportPDF";
	public static final String ORDER_BEAN_DTO= "orderBeanDTO";
	public static final String ORDER_RETURN_BEAN_DTO= "returnDTO";
	public static final String ORDER_BEAN= "orderBean";
	public static final String CARD_BEAN="cardBean";
	public static final String SUPPLIER_BEAN= "supplierBean";
	public static final String ACCOUNT_PAYMENT_DTO= "accountDTO";
	public static final String ITEM_BEAN= "itemBean";
	public static final String INV_ADJUST_BEAN= "invAdjustBean";
	public static final String INV_ADJUSTS_BEAN= "invAdjusts";
	public static final String SUPPLIERS_BEAN= "suppliers";
	public static final String SEARCH_BEAN= "searchBean";
	public static final String COLOR_LIST_BEAN="colorList";
	public static final String SIZE_LIST_BEAN="sizeList";
	public static final String TAX_GROUP_LIST_BEAN="taxGroups";
	public static final String UOM_LIST_BEAN="UOMs";
	public static final String PRICE_BEAN="priceBean";
	public static final String PRICE_BEAN_DTO="priceBeanDTO";
	public static final String ACCOUNT_DTO="accountDTO";
	
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
	public static final String SHOW_OPEN_REGISTER_PARAM= "showOpenRegister";
	public static final String OPEN_REGISTER_PARAM= "openRegister";
	public static final String ADD_DENOMINATION_PARAM= "addDenomination";
	public static final String REMOVE_DENOMINATION_PARAM= "removeDenomination";
	
	
	public static final String SAVE_PRICE_PARAM= "savePrice";
	public static final String APPROVE_PRICE_PARAM= "approvePrice";
	public static final String DELETE_PRICE_PARAM= "deletePrice";

	public static final String ADDRESS_PARAM="addressBean";
	
	public static final String ERROR_MSG="commerce.screen.common.error";
	

	public static final String ACCOUNT_BEAN_PARAM= "accountBean";
	
}
