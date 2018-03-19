/**
 * 
 */
package com.punj.app.ecommerce.controller.transaction;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.TransformerException;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.TransactionTransformer;
import com.punj.app.ecommerce.controller.reports.PdfReportGenerator;
import com.punj.app.ecommerce.domains.transaction.TransactionReceipt;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.models.transaction.SaleTransaction;
import com.punj.app.ecommerce.models.transaction.SaleTransactionReceipt;
import com.punj.app.ecommerce.models.transaction.TransactionHeader;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.dtos.transaction.SaleTransactionReceiptDTO;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class TransactionController {

	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private TransactionService transactionService;
	private CommerceContext commerceContext;
	private ResourceBundleMessageSource messageSource;

	@Value("${commerce.receipt.base.path}")
	private String receiptBasePath;

	@Value("${commerce.resource.bundle.base}")
	private String resourceBundleBase;

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param commerceContext
	 *            the commerceContext to set
	 */
	@Autowired
	public void setCommerceContext(CommerceContext commerceContext) {
		this.commerceContext = commerceContext;
	}

	/**
	 * @param transactionService
	 *            the transactionService to set
	 */
	@Autowired
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping(value = ViewPathConstants.TXN_SAVE_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public TransactionHeader saveTransactionDetails(@RequestBody SaleTransaction saleTxn, Model model, HttpSession session, Locale locale) {
		TransactionDTO txnDTO = TransactionTransformer.transformSaleTransaction(saleTxn);
		TransactionId txnId = this.transactionService.saveSaleTransaction(txnDTO);
		SaleTransactionReceipt txnReceipt = null;
		if (txnId != null) {
			SaleTransactionReceiptDTO receiptDetails = this.transactionService.generateTransactionReceipt(txnId);
			txnReceipt = this.generateReceiptPDF(receiptDetails, saleTxn.getTransactionHeader().getCreatedBy(), locale);

		}

		if (txnReceipt != null) {
			logger.info("The txn details has been saved successfully");
			return txnReceipt.getTransactionHeader();
		} else {
			logger.info("There was some error while saving transaction details");
			return null;
		}

	}

	public SaleTransactionReceipt generateReceiptPDF(SaleTransactionReceiptDTO receiptDetails, String username, Locale locale) {
		SaleTransactionReceipt txnReceipt = null;
		try {
			txnReceipt = TransactionTransformer.transformReceiptDetails(receiptDetails, username);
			if (txnReceipt != null) {
				String receiptFileName = this.receiptBasePath + txnReceipt.getTransactionHeader().getUniqueTxnNo() + ".pdf";
				logger.info("The receipt details has been transformed successfully");
				logger.info("Start the receipt PDF generation now");
				List<SaleTransactionReceipt> txnList = new ArrayList<>(1);
				txnList.add(txnReceipt);

				InputStream txnReceiptReportStream = getClass().getResourceAsStream(MVCConstants.TXN_RECEIPT_REPORT);

				JasperReport jasperReport;

				jasperReport = JasperCompileManager.compileReport(txnReceiptReportStream);

				ResourceBundle resourceBundle = ResourceBundle.getBundle(this.resourceBundleBase);

				JRBeanCollectionDataSource txnDS = new JRBeanCollectionDataSource(txnList);

				InputStream txnReceiptStreamChild = getClass().getResourceAsStream(MVCConstants.TXN_RECEIPT_ITEMS_REPORT);
				JasperReport jasperReportChild = JasperCompileManager.compileReport(txnReceiptStreamChild);

				Map<String, Object> paramMap = new HashMap<>();

				paramMap.put(MVCConstants.SUB_REPORT_DIR, jasperReportChild);
				paramMap.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
				paramMap.put(JRParameter.REPORT_LOCALE, locale);

				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, txnDS);

				PdfReportGenerator pdfGenerator = PdfReportGenerator.getInstance();
				// This section will save the receipt in database
				List<TransactionReceipt> txnReceipts = this.getReceipts(jasperPrint, txnReceipt.getTransactionHeader());
				Boolean result = this.transactionService.saveTransactionReceipt(txnReceipts);
				if (result) {
					logger.info("The transaction receipts has been saved in DB successfully");
					JasperPrintManager.printReport(jasperPrint, Boolean.FALSE);
					logger.info("The report has been printed successfully");
					// This is to generate receipt in file format
					pdfGenerator.generatePDFFileReport(jasperPrint, receiptFileName, username);
					logger.info("The transaction receipt was generated successfully");

				} else {
					logger.info("The transaction receipts were not saved due to unknown reason");
				}

			}

		} catch (JRException e) {
			logger.error("There is an error while generating receipt for txn", e);
		} catch (TransformerException e) {
			logger.error("There is an error while transforming receipt for txn", e);
		}
		return txnReceipt;
	}

	private List<TransactionReceipt> getReceipts(JasperPrint jasperPrint, TransactionHeader txnHeader) throws TransformerException {
		List<TransactionReceipt> txnReceipts = new ArrayList<>();
		byte pdfBytes[];
		try {
			pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
			TransactionReceipt txnReceipt = TransactionTransformer.tranformReceiptDetails(txnHeader, MVCConstants.RCPT_SALE_STORE, pdfBytes);
			if (txnReceipt != null)
				txnReceipts.add(txnReceipt);
			logger.info("The transaction receipts has been transformed for saving in DB");
		} catch (JRException e) {
			logger.info("The transaction receipts tranformation has failed");
			throw new TransformerException("The transaction receipts tranformation has failed", e);
		}
		return txnReceipts;
	}

	private void printReceipt() {

		logger.info("The transaction receipt has been successfully sent to printer");
	}

}
