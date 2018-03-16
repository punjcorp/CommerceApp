/**
 * 
 */
package com.punj.app.ecommerce.controller.transaction;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.TransactionTransformer;
import com.punj.app.ecommerce.controller.reports.PdfReportGenerator;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.models.transaction.SaleTransaction;
import com.punj.app.ecommerce.models.transaction.SaleTransactionReceipt;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.dtos.transaction.SaleTransactionReceiptDTO;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
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
	public TransactionId saveTransactionDetails(@RequestBody SaleTransaction saleTxn, Model model, HttpSession session) {
		TransactionDTO txnDTO = TransactionTransformer.transformSaleTransaction(saleTxn);
		TransactionId txnId = this.transactionService.saveSaleTransaction(txnDTO);
		if (txnId != null) {
			SaleTransactionReceiptDTO receiptDetails = this.transactionService.generateTransactionReceipt(txnId);
			this.generateReceiptPDF(receiptDetails, saleTxn.getTransactionHeader().getCreatedBy());

		}

		logger.info("The txn details has been saved successfully");
		return txnId;
	}

	public void generateReceiptPDF(SaleTransactionReceiptDTO receiptDetails, String username) {
		try {
			SaleTransactionReceipt txnReceipt = TransactionTransformer.transformReceiptDetails(receiptDetails);
			if (txnReceipt != null) {
				String receiptFileName = txnReceipt.getTransactionHeader().getUniqueTxnNo() + ".pdf";
				logger.info("The receipt details has been transformed successfully");
				logger.info("Start the receipt PDF generation now");
				List<SaleTransactionReceipt> txnList = new ArrayList<>(1);
				txnList.add(txnReceipt);

				InputStream txnReceiptReportStream = getClass().getResourceAsStream(MVCConstants.TXN_RECEIPT_REPORT);

				JasperReport jasperReport;

				jasperReport = JasperCompileManager.compileReport(txnReceiptReportStream);

				JRBeanCollectionDataSource txnDS = new JRBeanCollectionDataSource(txnList);

				InputStream txnReceiptStreamChild = getClass().getResourceAsStream(MVCConstants.TXN_RECEIPT_ITEMS_REPORT);
				JasperReport jasperReportChild = JasperCompileManager.compileReport(txnReceiptStreamChild);

				Map<String, Object> paramMap = new HashMap<>();

				paramMap.put(MVCConstants.SUB_REPORT_DIR, jasperReportChild);

				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, txnDS);

				PdfReportGenerator pdfGenerator = PdfReportGenerator.getInstance();
				pdfGenerator.generatePDFFileReport(jasperPrint, receiptFileName, username);
				logger.info("The transaction receipt was generated successfully");
			}

		} catch (JRException e) {
			logger.error("There is an error while generating receipt for txn", e);
		}
	}
}
