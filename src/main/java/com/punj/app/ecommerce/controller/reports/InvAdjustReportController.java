package com.punj.app.ecommerce.controller.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.InventoryBeanTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.inventory.StockAdjustment;
import com.punj.app.ecommerce.domains.inventory.StockReason;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.inventory.InvAdjustBean;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.services.common.CommonService;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * 
 * @author admin
 *
 */
@Controller
public class InvAdjustReportController {
	private static final Logger logger = LogManager.getLogger();
	private InventoryService inventoryService;
	private CommonService commonService;

	@Value("${commerce.resource.bundle.base}")
	private String resourceBundleBase;

	/**
	 * @param inventoryService
	 *            the inventoryService to set
	 */
	@Autowired
	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@GetMapping(value = ViewPathConstants.PRINT_INV_ADJUSTS_URL)
	public void printInventoryAdjustmentList(Model model, HttpSession session, final HttpServletRequest req, HttpServletResponse response, Locale locale) {
		logger.info("The inventory adjustment list report generation has been called.");
		try {
			List<StockAdjustment> stockAdjustmentList = this.inventoryService.listStockAdjustments();

			List<InvAdjustBean> invAdjustBeanList = InventoryBeanTransformer.transformStockAdjustmentList(stockAdjustmentList);
			JRBeanCollectionDataSource invAdjustDS = new JRBeanCollectionDataSource(invAdjustBeanList);

			InputStream invAdjustReportStream = getClass().getResourceAsStream(MVCConstants.INV_ADJUSTMENT_REPORT);
			JasperReport jasperReport = JasperCompileManager.compileReport(invAdjustReportStream);

			InputStream invAdjustReportStreamChild = getClass().getResourceAsStream(MVCConstants.INV_ADJUSTMENT_ITEMS_REPORT);
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
	public void printInventoryAdjustment(Model model, HttpSession session, final HttpServletRequest req, HttpServletResponse response, Locale locale) {
		logger.info("The single inventory adjustment report generation has been called.");
		try {
			BigInteger invAdjustId = new BigInteger(req.getParameter(MVCConstants.INV_ADJUST_ID_PARAM));
			if (invAdjustId.intValue() > 0) {

				StockAdjustment stockAdjustment = this.inventoryService.searchStockAdjustment(invAdjustId);
				InvAdjustBean invAdjustBean = InventoryBeanTransformer.transformStockAdjustmentDomainWithItems(stockAdjustment);
				List<InvAdjustBean> invAdjustBeanList = new ArrayList<>();
				invAdjustBeanList.add(invAdjustBean);

				JRBeanCollectionDataSource invAdjustDS = new JRBeanCollectionDataSource(invAdjustBeanList);

				InputStream invAdjustReportStream = getClass().getResourceAsStream(MVCConstants.INV_ADJUST_REPORT);
				JasperReport jasperReport = JasperCompileManager.compileReport(invAdjustReportStream);

				InputStream invAdjustReportStreamChild = getClass().getResourceAsStream(MVCConstants.INV_ADJUST_ITEMS_REPORT);
				JasperReport jasperReportChild = JasperCompileManager.compileReport(invAdjustReportStreamChild);

				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put(MVCConstants.INV_ADJUST_ITEMS_REPORT_PARAM, jasperReportChild);

				PdfReportGenerator pdfGenerator = PdfReportGenerator.getInstance();
				pdfGenerator.printPDFReport(response, jasperReport, paramMap, invAdjustDS);

				logger.info("The report for provided inventory adjustment has been generated successfully");
			}
		} catch (Exception e) {
			logger.error("An unknown error has occurred while generating inventory adjustment report for provided id.", e);
		}
	}

	@GetMapping(value = ViewPathConstants.VIEW_INV_ADJUST_URL)
	public void viewInventoryAdjustment(Model model, HttpSession session, final HttpServletRequest req, HttpServletResponse response, Locale locale) {
		logger.info("The single inventory adjustment report view has been called.");
		try {
			BigInteger invAdjustId = new BigInteger(req.getParameter(MVCConstants.INV_ADJUST_ID_PARAM));
			if (invAdjustId.intValue() > 0) {

				StockAdjustment stockAdjustment = this.inventoryService.searchStockAdjustment(invAdjustId);
				
				InvAdjustBean invAdjustBean = InventoryBeanTransformer.transformStockAdjustmentDomainWithItems(stockAdjustment);

				Location location = this.commonService.retrieveLocationDetails(invAdjustBean.getLocationId());
				LocationBean locationBean = CommonMVCTransformer.transformLocationDomainPartially(location, Boolean.FALSE);
				invAdjustBean.setLocationDetails(locationBean);

				List<InvAdjustBean> invAdjustBeanList = new ArrayList<>();
				invAdjustBeanList.add(invAdjustBean);

				JRBeanCollectionDataSource invAdjustDS = new JRBeanCollectionDataSource(invAdjustBeanList);

				InputStream invAdjustReportStream = getClass().getResourceAsStream(MVCConstants.INV_ADJUST_REPORT);
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(invAdjustReportStream);
				logger.debug("The inventory adjustment report has been retrieved now");

				InputStream headerStream = getClass().getResourceAsStream(MVCConstants.HEADER_RECEIPT_REPORT);
				JasperReport jasperHeaderReport = (JasperReport) JRLoader.loadObject(headerStream);
				logger.debug("The header receipt report has been compiled now");

				InputStream invItemsReportStream = getClass().getResourceAsStream(MVCConstants.INV_ADJUST_ITEMS_REPORT);
				JasperReport invItemsReport = (JasperReport) JRLoader.loadObject(invItemsReportStream);
				logger.debug("The inventory adjustment items report has been retrieved now");

				ResourceBundle resourceBundle = ResourceBundle.getBundle(this.resourceBundleBase);
				logger.debug("The receipt data source and resource bundle is ready now");

				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put(MVCConstants.HEADER_REPORT_DIR, jasperHeaderReport);
				paramMap.put(MVCConstants.INV_ADJUST_ITEMS_REPORT_PARAM, invItemsReport);
				paramMap.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
				paramMap.put(JRParameter.REPORT_LOCALE, locale);
				logger.debug("The parameter set for setting report is ready");

				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, invAdjustDS);

				byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

				if (pdfBytes != null) {
					response.setContentType(MediaType.APPLICATION_PDF_VALUE);
					response.setHeader("Content-disposition", "attachment; filename=inv_adjust_report.pdf");
					response.setContentLength(pdfBytes.length);
					response.getOutputStream().write(pdfBytes);
					logger.info("The PDF inventory adjustment report has been generated successfully");
					response.getOutputStream().flush();
				} else {
					this.writeBlankPDFForError(response);
				}

				logger.info("The report for provided inventory adjustment has been generated successfully");
			} else {
				this.writeBlankPDFForError(response);
			}
		} catch (Exception e) {
			logger.error("An unknown error has occurred while generating inventory adjustment report for provided id.", e);
			this.writeBlankPDFForError(response);
		}
	}

	private void writeBlankPDFForError(HttpServletResponse response) {
		logger.info("There has been an error, hence writing the blank pdf file for the same");
		String outputError = "There was no inventory adjustment found!!";
		try {
			try (PDDocument doc = new PDDocument()) {
				PDPage page = new PDPage();
				doc.addPage(page);

				PDFont font = PDType1Font.HELVETICA_BOLD;

				try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
					contents.beginText();
					contents.setFont(font, 12);
					contents.newLineAtOffset(100, 700);
					contents.showText(outputError);
					contents.endText();
				}

				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				doc.save(byteArrayOutputStream);
				doc.close();
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "attachment; filename=blank_txn_no.pdf");
				response.getOutputStream().write(byteArrayOutputStream.toByteArray());
				response.getOutputStream().flush();
			}
		} catch (IOException e) {
			logger.error("There is an error while generating receipt for last txn", e);
		}
	}

}
