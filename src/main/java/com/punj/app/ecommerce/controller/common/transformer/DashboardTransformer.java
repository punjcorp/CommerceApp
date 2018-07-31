/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.reports.dashboard.DashboardReport;

/**
 * @author admin
 *
 */
public class DashboardTransformer {

	private static final Logger logger = LogManager.getLogger();

	private DashboardTransformer() {
		throw new IllegalStateException("DashboardTransformer class");
	}

	public static Map<String, Object[]> transformDashboardData(Map<String, DashboardReport> dashboardReportMap, List<String> bDates) {

		Map<String, Object[]> dashboardData = new HashMap<>();

		BigDecimal[] revenueArray = new BigDecimal[7];
		BigDecimal[] discountAmountArray = new BigDecimal[7];
		BigDecimal[] discountPercentageArray = new BigDecimal[7];
		BigDecimal[] basketAmountArray = new BigDecimal[7];
		BigDecimal[] basketSizeArray = new BigDecimal[7];
		Integer[] txnCountArray = new Integer[7];
		Integer[] customerCountArray = new Integer[7];

		int counter = 0;
		DashboardReport dashboardReport = null;
		for (String bDate : bDates) {
			dashboardReport = dashboardReportMap.get(bDate);

			revenueArray[counter] = dashboardReport.getRevenueAmount();
			discountAmountArray[counter] = dashboardReport.getDiscountAmount();
			discountPercentageArray[counter] = dashboardReport.getDiscountPercentage();
			basketAmountArray[counter] = dashboardReport.getBasketAmount();
			basketSizeArray[counter] = dashboardReport.getBasketSize();

			txnCountArray[counter] = dashboardReport.getTxnCount();
			customerCountArray[counter] = dashboardReport.getCustomerCount();

			counter++;
		}

		dashboardData.put(MVCConstants.WIDGET_REVENUE, revenueArray);
		dashboardData.put(MVCConstants.WIDGET_DISCOUNT_AMOUNT, discountAmountArray);
		dashboardData.put(MVCConstants.WIDGET_DISCOUNT_PERCENTAGE, discountPercentageArray);
		dashboardData.put(MVCConstants.WIDGET_BASKET_VALUE, basketAmountArray);
		dashboardData.put(MVCConstants.WIDGET_BASKET_SIZE, basketSizeArray);

		dashboardData.put(MVCConstants.WIDGET_SALES_COUNT, txnCountArray);
		dashboardData.put(MVCConstants.WIDGET_CUSTOMER_COUNT, customerCountArray);

		logger.info("The dashboard data has been transformed successfully");
		return dashboardData;
	}

}
