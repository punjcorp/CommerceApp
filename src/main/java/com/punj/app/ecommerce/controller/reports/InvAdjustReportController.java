package com.punj.app.ecommerce.controller.reports;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.InventoryBeanTransformer;
import com.punj.app.ecommerce.domains.inventory.StockAdjustment;
import com.punj.app.ecommerce.models.inventory.InvAdjustBean;
import com.punj.app.ecommerce.services.InventoryService;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * @author admin
 *
 */
@Controller
public class InvAdjustReportController {
	private static final Logger logger = LogManager.getLogger();
	private InventoryService inventoryService;

	/**
	 * @param inventoryService
	 *            the inventoryService to set
	 */
	@Autowired
	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@GetMapping(value = ViewPathConstants.PRINT_INV_ADJUSTS_URL)
	public void printInventoryAdjustmentList(Model model, HttpSession session, final HttpServletRequest req,
			HttpServletResponse response, Locale locale) {
		logger.info("The inventory adjustment list report generation has been called.");
		try {
			List<StockAdjustment> stockAdjustmentList = this.inventoryService.listStockAdjustments();

			List<InvAdjustBean> invAdjustBeanList = InventoryBeanTransformer
					.transformStockAdjustmentList(stockAdjustmentList);
			JRBeanCollectionDataSource invAdjustDS = new JRBeanCollectionDataSource(invAdjustBeanList);

			InputStream invAdjustReportStream = getClass().getResourceAsStream(MVCConstants.INV_ADJUSTMENT_REPORT);
			JasperReport jasperReport = JasperCompileManager.compileReport(invAdjustReportStream);

			InputStream invAdjustReportStreamChild = getClass()
					.getResourceAsStream(MVCConstants.INV_ADJUSTMENT_ITEMS_REPORT);
			JasperReport jasperReportChild = JasperCompileManager.compileReport(invAdjustReportStreamChild);

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put(MVCConstants.INV_ADJUSTMENT_ITEMS_REPORT_PARAM, jasperReportChild);

			PdfReportGenerator pdfGenerator = PdfReportGenerator.getInstance();
			pdfGenerator.printPDFReport(response, jasperReport, paramMap, invAdjustDS);

			logger.info("The listing report for inventory adjustment has been generated successfully");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while generating inventory adjustment list.", e);
		}
	}

	@GetMapping(value = ViewPathConstants.PRINT_INV_ADJUST_URL)
	public void printInventoryAdjustment(Model model, HttpSession session, final HttpServletRequest req,
			HttpServletResponse response, Locale locale) {
		logger.info("The single inventory adjustment report generation has been called.");
		try {
			BigInteger invAdjustId = new BigInteger(req.getParameter(MVCConstants.INV_ADJUST_ID_PARAM));
			if (invAdjustId.intValue()>0) {
				
				StockAdjustment stockAdjustment= this.inventoryService.searchStockAdjustment(invAdjustId);
				InvAdjustBean invAdjustBean = InventoryBeanTransformer.transformStockAdjustmentDomainWithItems(stockAdjustment);
				List<InvAdjustBean> invAdjustBeanList=new ArrayList<>(); 
				invAdjustBeanList.add(invAdjustBean);
				
				JRBeanCollectionDataSource invAdjustDS = new JRBeanCollectionDataSource(invAdjustBeanList);

				InputStream invAdjustReportStream = getClass().getResourceAsStream(MVCConstants.INV_ADJUST_REPORT);
				JasperReport jasperReport = JasperCompileManager.compileReport(invAdjustReportStream);

				InputStream invAdjustReportStreamChild = getClass()
						.getResourceAsStream(MVCConstants.INV_ADJUST_ITEMS_REPORT);
				JasperReport jasperReportChild = JasperCompileManager.compileReport(invAdjustReportStreamChild);

				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put(MVCConstants.INV_ADJUST_ITEMS_REPORT_PARAM, jasperReportChild);

				PdfReportGenerator pdfGenerator = PdfReportGenerator.getInstance();
				pdfGenerator.printPDFReport(response, jasperReport, paramMap, invAdjustDS);

				logger.info("The report for provided inventory adjustment has been generated successfully");
			}
		} catch (Exception e) {
			logger.error("An unknown error has occurred while generating inventory adjustment report for provided id.",
					e);
		}
	}

}
