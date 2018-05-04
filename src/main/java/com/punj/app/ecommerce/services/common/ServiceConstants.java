/**
 * 
 */
package com.punj.app.ecommerce.services.common;

import java.math.BigDecimal;

/**
 * @author admin
 *
 */
public final class ServiceConstants {

	private ServiceConstants() {
		throw new IllegalStateException("Service Constant class");
	}

	public static final Integer REGISTER_ONE = 1;

	public static final String TXN_SEQ = "TX_SEQ";

	public static final String TXN_STATUS_STARTED = "STARTED";
	public static final String TXN_STATUS_COMPLETED = "COMPLETED";
	public static final String TXN_STATUS_SUSPEND = "SUSPENDED";
	public static final String TXN_STATUS_CANCEL = "CANCELLED";

	public static final String TXN_OPEN_STORE = "OPEN_STORE";
	public static final String TXN_CLOSE_STORE = "CLOSE_STORE";
	public static final String TXN_OPEN_REGISTER = "OPEN_REGISTER";
	public static final String TXN_CLOSE_REGISTER = "CLOSE_REGISTER";
	public static final String TXN_NOSALE= "NOSALE";

	public static final String TAX_WITHIN_STATE = "I";
	public static final String TAX_OTHER_STATE = "O";
	public static final String TAX_SGST = "SGST";
	public static final String TAX_CGST = "CGST";
	public static final String TAX_IGST= "IGST";
	

	public static final String INV_REASON_STKIN = "STKIN";
	public static final String INV_REASON_STKOUT = "STKOUT";

	public static final String INV_BUCKET_NON_SELL = "NON_SELL";
	public static final String INV_BUCKET_RESERVED = "RESERVED";
	public static final String INV_BUCKET_SOH = "SOH";

	public static final String INV_ACTION_ADD = "ADD";
	public static final String INV_ACTION_SUBSTRACT = "SUBTRACT";

	public static final String SALE_TXN_FUNCTIONALITY = "SALE_TXN";
	public static final String RECEIVE_ORDER_FUNCTIONALITY = "RECEIVE_ORDER";
	public static final String STOCK_ADJUSTMENT_FUNCTIONALITY = "STOCK_ADJUSTMENT";

	public static final String OPTION_ALL = "all";

	public static final String STATUS_CREATED = "C";
	public static final String STATUS_APPROVED = "A";
	public static final String STATUS_DELETED = "D";

	public static final String STOCK_FROM_BUCKET_IND = "F";
	public static final String STOCK_TO_BUCKET_IND = "T";

	public static final String PAYMENT_FULL = "full";
	public static final String PAYMENT_PART = "part";
	public static final String PAYMENT_ADVANCE= "advance";
	
	public static final String ACCOUNT_TYPE_SUPPLIER= "SUPPLIER";
	
	
	public static final String PRICE_TYPE_PERMANENT= "1";
	public static final String PRICE_TYPE_PROMOTION= "2";
	public static final String PRICE_TYPE_CLEARANCE= "3";
	
}
