/**
 * 
 */
package com.punj.app.ecommerce.controller.transaction;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.PrintUtil;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.TransactionTransformer;
import com.punj.app.ecommerce.domains.transaction.TransactionReceipt;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.domains.transaction.ids.TxnIdDTO;
import com.punj.app.ecommerce.models.transaction.SaleTransaction;
import com.punj.app.ecommerce.models.transaction.SaleTransactionReceipt;
import com.punj.app.ecommerce.models.transaction.TransactionHeader;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.dtos.transaction.SaleTransactionReceiptDTO;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

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
	private PrintUtil printUtil;

	@Value("${commerce.resource.bundle.base}")
	private String resourceBundleBase;

	@Value("${commerce.txn.receipt.copies}")
	private Integer receiptCopies;

	@Value("${commerce.printer.name}")
	private String printerName;

	@Value("${commerce.receipt.pdf.output}")
	private String outputDir;

	@Value("${commerce.receipt.export.method}")
	private String reportMethod;

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setPrintUtil(PrintUtil printUtil) {
		this.printUtil = printUtil;
	}

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
	@Transactional
	public TransactionHeader saveTransactionDetails(@RequestBody SaleTransaction saleTxn, Model model, HttpSession session, Locale locale, Authentication authentication) {
		Boolean result = null;
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		TransactionDTO txnDTO = TransactionTransformer.transformSaleTransaction(saleTxn, userDetails.getUsername());
		TxnIdDTO txnIdDTO = this.transactionService.saveSaleTransaction(txnDTO);
		SaleTransactionReceipt txnReceipt = null;
		if (txnIdDTO != null) {
			TransactionId txnId = txnIdDTO.getTransactionId();
			SaleTransactionReceiptDTO receiptDetails = this.transactionService.generateTransactionReceipt(txnId);
			receiptDetails.setInvoiceNo(txnIdDTO.getInvoiceNo());
			txnReceipt = this.generateReceiptPDFs(receiptDetails, session, saleTxn.getTransactionHeader().getCreatedBy(), locale, txnId);

		}

		if (txnReceipt != null) {
			logger.info("The txn details has been saved successfully");
			return txnReceipt.getTransactionHeader();
		} else {
			logger.info("There was some error while saving transaction details");
			return null;
		}

	}

	@PostMapping(value = ViewPathConstants.TXN_EDITED_SAVE_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public TransactionHeader saveModifiedTransactionDetails(@RequestBody SaleTransaction saleTxn, Model model, HttpSession session, Locale locale, Authentication authentication) {
		Boolean result = null;
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		TransactionDTO txnDTO = TransactionTransformer.transformSaleTransaction(saleTxn, userDetails.getUsername());
		TxnIdDTO txnIdDTO = this.transactionService.updateSaleTransaction(txnDTO);
		SaleTransactionReceipt txnReceipt = null;
		if (txnIdDTO != null) {
			TransactionId txnId = txnIdDTO.getTransactionId();
			SaleTransactionReceiptDTO receiptDetails = this.transactionService.generateTransactionReceipt(txnId);
			receiptDetails.setInvoiceNo(saleTxn.getInvoiceNo());
			txnReceipt = this.generateReceiptPDFs(receiptDetails, session, saleTxn.getTransactionHeader().getCreatedBy(), locale, txnId);

		}

		if (txnReceipt != null) {
			logger.info("The txn details has been saved successfully");
			return txnReceipt.getTransactionHeader();
		} else {
			logger.info("There was some error while saving transaction details");
			return null;
		}

	}

	@PostMapping(value = ViewPathConstants.RETURN_TXN_SAVE_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@Transactional
	public TransactionHeader saveReturnTxnDetails(@RequestBody SaleTransaction saleTxn, Model model, HttpSession session, Locale locale, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		TransactionDTO txnDTO = TransactionTransformer.transformReturnTransaction(saleTxn, userDetails.getUsername());
		TxnIdDTO txnIdDTO = this.transactionService.saveSaleTransaction(txnDTO);
		SaleTransactionReceipt txnReceipt = null;
		if (txnIdDTO != null) {
			TransactionId txnId = txnIdDTO.getTransactionId();
			SaleTransactionReceiptDTO receiptDetails = this.transactionService.generateTransactionReceipt(txnId);
			txnReceipt = this.generateReceiptPDF(receiptDetails, session, saleTxn.getTransactionHeader().getCreatedBy(), locale, txnId);

		}

		if (txnReceipt != null) {
			logger.info("The txn details has been saved successfully");
			return txnReceipt.getTransactionHeader();
		} else {
			logger.info("There was some error while saving transaction details");
			return null;
		}

	}

	public List<JRBeanCollectionDataSource> prepareReceiptsArray(SaleTransactionReceiptDTO receiptDetails, HttpSession session, String username, Locale locale,
			TransactionId txnId) {
		List<JRBeanCollectionDataSource> txnReceipts = null;
		if (this.receiptCopies != null && this.receiptCopies > 0) {
			String receiptType = null;
			String receiptMsg = null;
			SaleTransactionReceipt txnReceipt = null;
			txnReceipts = new ArrayList<>(this.receiptCopies);
			for (int i = 1; i <= this.receiptCopies; i++) {
				receiptType = this.messageSource.getMessage("commerce.screen.sale.receipt.copy." + i, new Object[] {}, locale);
				if (i == 1)
					receiptMsg = this.messageSource.getMessage("commerce.screen.sale.receipt.msg.copy." + i, new Object[] {}, locale);
				else
					receiptMsg = null;

				txnReceipt = TransactionTransformer.transformReceiptDetails(receiptDetails, username);
				txnReceipt.setReceiptType(receiptType);
				txnReceipt.setReceiptMsg(receiptMsg);
				List<SaleTransactionReceipt> txnList = new ArrayList<>(1);
				txnList.add(txnReceipt);

				JRBeanCollectionDataSource txnDS = new JRBeanCollectionDataSource(txnList);

				txnReceipts.add(txnDS);
			}
		}
		return txnReceipts;
	}

	public void saveTxnReceipts(List<JasperPrint> jasperPrints, String username, TransactionId txnId) {
		int count = 1;
		Boolean isOriginal = Boolean.FALSE;
		try {
			List<TransactionReceipt> finalTxnReceipts = new ArrayList<>();
			for (JasperPrint jasperPrint : jasperPrints) {
				byte pdfBytes[] = JasperExportManager.exportReportToPdf(jasperPrint);
				if (count == 1)
					isOriginal = Boolean.TRUE;
				else
					isOriginal = Boolean.FALSE;

				// This section will save the receipt in database
				List<TransactionReceipt> txnReceipts = TransactionTransformer.getReceipts(pdfBytes, txnId, username, isOriginal);
				finalTxnReceipts.addAll(txnReceipts);
				count++;
			}
			Boolean result = this.transactionService.saveTransactionReceipt(finalTxnReceipts);
			if (result) {
				logger.info("The transaction receipts has been saved in DB successfully");
			} else {
				logger.info("The transaction receipts were not saved due to unknown reason");
			}

		} catch (JRException e) {
			logger.error("There is an error while generating receipt for txn", e);
		}

	}

	public SaleTransactionReceipt generateReceiptPDFs(SaleTransactionReceiptDTO receiptDetails, HttpSession session, String username, Locale locale, TransactionId txnId) {
		SaleTransactionReceipt txnReceipt = null;
		try {
			txnReceipt = TransactionTransformer.transformReceiptDetails(receiptDetails, username);
			List<JRBeanCollectionDataSource> txnReceiptDSs = this.prepareReceiptsArray(receiptDetails, session, username, locale, txnId);
			if (txnReceiptDSs != null && !txnReceiptDSs.isEmpty() && txnReceipt != null) {
				InputStream txnReceiptReportStream = getClass().getResourceAsStream(MVCConstants.CREATIVE_TXN_RECEIPT_REPORT);
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(txnReceiptReportStream);
				logger.debug("The parent receipt report has been compiled now");

				InputStream txnReceiptStreamChild = getClass().getResourceAsStream(MVCConstants.CREATIVE_TXN_RECEIPT_ITEMS_REPORT);
				JasperReport jasperReportChild = (JasperReport) JRLoader.loadObject(txnReceiptStreamChild);
				logger.debug("The child receipt report has been compiled now");

				ResourceBundle resourceBundle = ResourceBundle.getBundle(this.resourceBundleBase);
				logger.debug("The receipt data source and resource bundle is ready now");

				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put(MVCConstants.SUB_REPORT_DIR, jasperReportChild);
				paramMap.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
				paramMap.put(JRParameter.REPORT_LOCALE, locale);
				logger.debug("The parameter set for setting receipt data is ready");

				String uniqueTxnNo = receiptDetails.getTxn().getTransactionId().toString();

				List<JasperPrint> jasperPrints = new ArrayList<>(txnReceiptDSs.size());
				JasperPrint jasperPrint = null;
				for (JRBeanCollectionDataSource txnDS : txnReceiptDSs) {
					jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, txnDS);
					jasperPrints.add(jasperPrint);
				}

				ByteArrayOutputStream out = null;
				if (StringUtils.isNotBlank(this.reportMethod) && this.reportMethod.equals(MVCConstants.REPORT_METHOD_PDF)) {
					out = this.printUtil.exportAsPDFBytes(jasperPrints);
					byte[] pdfBytes = out.toByteArray();
					session.setAttribute(uniqueTxnNo + MVCConstants.RCPT_PARAM, pdfBytes);

				} else if (StringUtils.isNotBlank(this.reportMethod) && this.reportMethod.equals(MVCConstants.REPORT_METHOD_RTF)) {
					this.printUtil.exportAsRTFFile(uniqueTxnNo, jasperPrints);

				} else if (StringUtils.isNotBlank(this.reportMethod) && this.reportMethod.equals(MVCConstants.REPORT_METHOD_JPG)) {
					this.printUtil.exportAsImage(uniqueTxnNo, jasperPrints);
				}

				// This is to print the receipt when user select the printing option
				session.setAttribute(uniqueTxnNo + MVCConstants.RCPT_JASPER_PARAM, jasperPrints);
				// This is to show the receipt PDF in view screen
				session.setAttribute(MVCConstants.LAST_TXN_NO, uniqueTxnNo);
				session.setAttribute(MVCConstants.RCPT_PRINTER_NAME, this.printerName);

				this.saveTxnReceipts(jasperPrints, username, txnId);

			}
		} catch (JRException e) {
			logger.error("There is an error while generating receipt for txn", e);
		} catch (FileNotFoundException e) {
			logger.error("There is an error while generating receipt for txn", e);
		} catch (IOException e) {
			logger.error("There is an error while generating receipt for txn", e);
		}
		return txnReceipt;

	}

	public SaleTransactionReceipt generateReceiptPDF(SaleTransactionReceiptDTO receiptDetails, HttpSession session, String username, Locale locale, TransactionId txnId) {
		SaleTransactionReceipt txnReceipt = null;
		try {
			txnReceipt = TransactionTransformer.transformReceiptDetails(receiptDetails, username);
			if (txnReceipt != null) {
				TransactionHeader txnHeader = txnReceipt.getTransactionHeader();
				logger.info("The receipt details has been transformed successfully");
				logger.info("Start the receipt PDF generation now");
				List<SaleTransactionReceipt> txnList = new ArrayList<>(1);
				txnList.add(txnReceipt);

				InputStream txnReceiptReportStream = getClass().getResourceAsStream(MVCConstants.TXN_RECEIPT_REPORT);
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(txnReceiptReportStream);
				logger.debug("The parent receipt report has been compiled now");

				InputStream txnReceiptStreamChild = getClass().getResourceAsStream(MVCConstants.TXN_RECEIPT_ITEMS_REPORT);
				JasperReport jasperReportChild = (JasperReport) JRLoader.loadObject(txnReceiptStreamChild);
				logger.debug("The child receipt report has been compiled now");

				ResourceBundle resourceBundle = ResourceBundle.getBundle(this.resourceBundleBase);
				JRBeanCollectionDataSource txnDS = new JRBeanCollectionDataSource(txnList);
				logger.debug("The receipt data source and resource bundle is ready now");

				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put(MVCConstants.SUB_REPORT_DIR, jasperReportChild);
				paramMap.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
				paramMap.put(JRParameter.REPORT_LOCALE, locale);
				logger.debug("The parameter set for setting receipt data is ready");
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, txnDS);
				logger.debug("The receipt report is ready with data to be used");

				String uniqueTxnNo = receiptDetails.getTxn().getTransactionId().toString();
				byte pdfBytes[] = JasperExportManager.exportReportToPdf(jasperPrint);

				// This is to print the receipt when user select the printing option
				session.setAttribute(uniqueTxnNo + MVCConstants.RCPT_JASPER_PARAM, jasperPrint);
				// This is to show the receipt PDF in view screen
				session.setAttribute(MVCConstants.LAST_TXN_NO, uniqueTxnNo);
				session.setAttribute(uniqueTxnNo + MVCConstants.RCPT_PARAM, pdfBytes);
				txnHeader.setPdfbytes(pdfBytes);

				// This section will save the receipt in database
				List<TransactionReceipt> txnReceipts = TransactionTransformer.getReturnReceipts(pdfBytes, txnId, username);
				Boolean result = this.transactionService.saveTransactionReceipt(txnReceipts);
				if (result) {
					logger.info("The transaction receipts has been saved in DB successfully");
				} else {
					logger.info("The transaction receipts were not saved due to unknown reason");
				}

			}

		} catch (JRException e) {
			logger.error("There is an error while generating receipt for txn", e);
		}
		return txnReceipt;
	}

	@PostMapping(value = ViewPathConstants.TXN_RCPT_RETURN_PRINT_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	private TransactionHeader printReturnReceipt(@RequestBody TransactionHeader txnHeader, Model model, HttpSession session, Locale locale) {
		try {
			JasperPrint jasperPrint = (JasperPrint) session.getAttribute(txnHeader.getUniqueTxnNo() + MVCConstants.RCPT_JASPER_PARAM);
			if (jasperPrint != null) {
				JasperPrintManager.printReport(jasperPrint, Boolean.FALSE);
				logger.info("The {} txn receipt has been printed successfully", txnHeader.getUniqueTxnNo());
			} else {
				logger.info("The txn receipt is not found");
			}
		} catch (JRException e) {
			logger.info("There was errror printing receipt for the {} txn", txnHeader.getUniqueTxnNo());
		}
		return txnHeader;
	}

	@PostMapping(value = ViewPathConstants.TXN_RCPT_PRINT_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	private TransactionHeader printReceipt(@RequestBody TransactionHeader txnHeader, Model model, HttpSession session, Locale locale) {
		try {

			
			String printerName = (String) session.getAttribute(MVCConstants.RCPT_PRINTER_NAME);
			List<JasperPrint> jasperPrints = (List) session.getAttribute(txnHeader.getUniqueTxnNo() + MVCConstants.RCPT_JASPER_PARAM);
			if (jasperPrints != null && !jasperPrints.isEmpty()) {
				if(this.printUtil==null)
					this.printUtil=new PrintUtil();
				this.printUtil.printReportUsingJasperPrints(printerName,jasperPrints);

			} else {
				logger.info("The txn receipt is not found");
			}

		} catch (JRException e) {
			logger.error("There is an error while printing receipt for last txn {}",txnHeader.getUniqueTxnNo(), e);
		}
		return txnHeader;
	}

	@GetMapping(value = ViewPathConstants.TXN_RCPT_VIEW_URL)
	private void viewLastReceipt(Model model, HttpSession session, Locale locale, HttpServletResponse response) {
		try {
			String uniqueTxnNo = (String) session.getAttribute(MVCConstants.LAST_TXN_NO);
			byte pdfBytes[] = (byte[]) session.getAttribute(uniqueTxnNo + MVCConstants.RCPT_PARAM);
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=" + uniqueTxnNo + ".pdf");
			response.setContentLength(pdfBytes.length);
			response.getOutputStream().write(pdfBytes);
			logger.info("The {} txn receipt has been generated successfully", uniqueTxnNo);
			response.getOutputStream().flush();
		} catch (IOException e) {
			logger.error("There is an error while generating receipt for last txn", e);
		}
	}

}
