package com.punj.app.ecommerce.models.reporting;

import com.punj.app.ecommerce.models.inventory.InvAdjustBean;
import com.punj.app.ecommerce.models.transaction.SaleTransactionReceipt;

/**
 * 
 * @author admin
 *
 */
public class ReportingBeanProvider {

	private ReportingBeanProvider() {

	}

	public static InvAdjustBean[] prepareInventoryAdjustmentReport() {

		return new InvAdjustBean[1];
	}

	public static SaleTransactionReceipt[] prepareSaleReceipt() {

		return new SaleTransactionReceipt[1];
	}

}
