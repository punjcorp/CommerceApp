package com.punj.app.ecommerce.models.reporting;

import com.punj.app.ecommerce.models.inventory.InvAdjustBean;

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
}
