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
	
	public static final String INV_REASON_STKIN="STKIN";
	public static final String INV_REASON_STKOUT="STKOUT";
	
	public static final String INV_BUCKET_NON_SELL="NON_SELL";
	public static final String INV_BUCKET_RESERVED="RESERVED";
	public static final String INV_BUCKET_SOH="SOH";
	
	public static final String INV_ACTION_ADD="ADD";
	public static final String INV_ACTION_SUBSTRACT="SUBTRACT";
	
	public static final String RECEIVE_ORDER_FUNCTIONALITY="RECEIVE_ORDER";
	public static final String STOCK_ADJUSTMENT_FUNCTIONALITY="STOCK_ADJUSTMENT";
	
	public static final String OPTION_ALL="all";
	
	public static final String STATUS_CREATED="C";
	public static final String STATUS_APPROVED="A";
	
	public static final String STOCK_FROM_BUCKET_IND="F";
	public static final String STOCK_TO_BUCKET_IND="T";

}
