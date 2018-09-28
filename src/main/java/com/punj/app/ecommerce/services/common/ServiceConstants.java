/**
 * 
 */
package com.punj.app.ecommerce.services.common;

/**
 * @author admin
 *
 */
public final class ServiceConstants {

	private ServiceConstants() {
		throw new IllegalStateException("Service Constant class");
	}

	public static final Integer REGISTER_ONE = 1;

	public static final String RCPT_SALE_STORE = "Sale Store Copy";
	public static final String RCPT_SALE_CUSTOMER = "Sale Customer Copy";

	public static final String ACTION_EXPENSE = "Expense";

	public static final String CUSTOMER_TYPE_CLIENT = "CUSTOMER";
	public static final String CUSTOMER_TYPE_SUPPLIER= "SUPPLIER";
	public static final String CUSTOMER_TYPE_FRIEND= "FRIEND";

	public static final String TENDER_CASH = "Cash";
	public static final String TENDER_CREDIT = "Credit";

	public static final String REASON_REPO_TO_REGISTER = "Repository To Register";
	public static final String REASON_REGISTER_TO_REPO = "Register To Repository";
	public static final String REASON_REGISTER_TO_REGISTER = "Register To Register";
	public static final String REASON_EXPENSE_FROM_REGISTER = "Expense From Register";

	public static final String LEDGER_ACTION_ADD_TO_SAFE = "1";
	public static final String LEDGER_ACTION_SUBTRACT_FROM_SAFE = "2";
	public static final String LEDGER_ACTION_RECEIVE_MONEY_TO_REGISTER = "3";
	public static final String LEDGER_ACTION_EXPENSE_FROM_REGISTER = "4";
	public static final String LEDGER_ACTION_SALE_TXN = "5";
	public static final String LEDGER_ACTION_RETURN_TXN = "6";
	public static final String LEDGER_ACTION_PAYMENT_SUPPLIER = "11";
	public static final String LEDGER_ACTION_SUPPLIER_PO_RECEIVED = "12";
	public static final String LEDGER_ACTION_SUPPLIER_PO_RETURN = "13";
	
	public static final String LEDGER_ACTION_PAYMENT_CUSTOMER= "16";

	public static final Integer STYLE_LEVEL = 1;
	public static final Integer ITEM_LEVEL = 2;
	public static final Integer UPC_LEVEL = 3;

	public static final String TXN_SEQ = "TX_SEQ";

	public static final String YES_PARAM = "Y";
	public static final String NO_PARAM = "N";

	public static final String NEXT_LEVEL_CREATED = "C";
	public static final String NEXT_LEVEL_APPROVED = "A";
	public static final String NEXT_LEVEL_NOT_CREATED = "N";

	public static final String TXN_STATUS_STARTED = "STARTED";
	public static final String TXN_STATUS_COMPLETED = "COMPLETED";
	public static final String TXN_STATUS_SUSPEND = "SUSPENDED";
	public static final String TXN_STATUS_CANCEL = "CANCELLED";

	public static final String TXN_OPEN_STORE = "OPEN_STORE";
	public static final String TXN_CLOSE_STORE = "CLOSE_STORE";
	public static final String TXN_OPEN_REGISTER = "OPEN_REGISTER";
	public static final String TXN_CLOSE_REGISTER = "CLOSE_REGISTER";
	public static final String TXN_NOSALE = "NOSALE";
	public static final String TXN_SALE = "SALE";
	public static final String TXN_RETURN= "RETURN";
	public static final String TXN_SUPPLIER_PAYMENT= "SUPPLIER_PAYMENT";
	public static final String TXN_PURCHASE_ORDER_RECEIVED= "SUPPLIER_PO_RECEIVED";
	public static final String TXN_ORDER_RETURN= "SUPPLIER_ORDER_RETURN";
	
	public static final String TXN_CUSTOMER_PAYMENT= "CUSTOMER_PAYMENT";

	public static final String TAX_WITHIN_STATE = "I";
	public static final String TAX_OTHER_STATE = "O";
	public static final String TAX_SGST = "SGST";
	public static final String TAX_CGST = "CGST";
	public static final String TAX_IGST = "IGST";

	public static final String INV_REASON_STKIN = "STKIN";
	public static final String INV_REASON_STKOUT = "STKOUT";

	public static final String INV_BUCKET_NON_SELL = "NON_SELL";
	public static final String INV_BUCKET_RESERVED = "RESERVED";
	public static final String INV_BUCKET_SOH = "SOH";

	public static final String INV_ACTION_ADD = "ADD";
	public static final String INV_ACTION_SUBSTRACT = "SUBTRACT";

	public static final String SKU_CREATION_FUNCTIONALITY = "SKU_CREATION";
	public static final String SALE_TXN_FUNCTIONALITY = "SALE_TXN";
	public static final String RETURN_TXN_FUNCTIONALITY = "RETURN_TXN";
	public static final String RECEIVE_ORDER_FUNCTIONALITY = "RECEIVE_ORDER";
	public static final String SUPPLIER_ORDER_RETURN_FUNCTIONALITY = "SUPPLIER_ORDER_RETURN";
	public static final String STOCK_ADJUSTMENT_FUNCTIONALITY = "STOCK_ADJUSTMENT";

	public static final String OPTION_ALL = "all";

	public static final String STATUS_CREATED = "C";
	public static final String STATUS_APPROVED = "A";
	public static final String STATUS_DISABLE = "I";
	public static final String STATUS_DELETED = "D";
	public static final String STATUS_RESET = "R";

	public static final String STOCK_FROM_BUCKET_IND = "F";
	public static final String STOCK_TO_BUCKET_IND = "T";

	public static final String PAYMENT_FULL = "full";
	public static final String PAYMENT_PART = "part";
	public static final String PAYMENT_ADVANCE = "advance";
	public static final String PAYMENT_DUE = "payment_due";
	public static final String PAYMENT_CREDIT = "payment_credit";
	
	public static final String JOURNAL_CREDIT = "credit";
	public static final String JOURNAL_CREDIT_RETURN = "credit_return";

	public static final String ACCOUNT_TYPE_SUPPLIER = "SUPPLIER";
	public static final String ACCOUNT_TYPE_CUSTOMER = "CUSTOMER";

	public static final String PRICE_TYPE_PERMANENT = "1";
	public static final String PRICE_TYPE_PROMOTION = "2";
	public static final String PRICE_TYPE_CLEARANCE = "3";

}
