/**
 * 
 */
package com.punj.app.ecommerce.controller.transaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.transaction.TransactionReceipt;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class TransactionPrintController {

	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private TransactionService transactionService;
	private CommerceContext commerceContext;
	private ResourceBundleMessageSource messageSource;

	@Value("${commerce.resource.bundle.base}")
	private String resourceBundleBase;

	@Value("${commerce.txn.receipt.copies}")
	private Integer receiptCopies;

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

	@GetMapping(value = ViewPathConstants.VIEW_RCPT_TXN_NO_URL)
	private void viewLastReceipt(@RequestParam("txnId") String uniqueTxnNo, @RequestParam("rcpt_type") String receiptType, HttpServletResponse response) {
		try {
			if (StringUtils.isNotBlank(uniqueTxnNo) && StringUtils.isNotBlank(receiptType)) {
				TransactionReceipt txnReceipt = this.transactionService.searchTransactionReceipt(uniqueTxnNo, receiptType);
				if (txnReceipt != null) {

					byte pdfBytes[] = txnReceipt.getReceiptData();
					response.setContentType("application/pdf");
					response.setHeader("Content-disposition", "attachment; filename=" + uniqueTxnNo + ".pdf");
					response.setContentLength(pdfBytes.length);
					response.getOutputStream().write(pdfBytes);
					logger.info("The {} txn receipt has been generated successfully", uniqueTxnNo);
					response.getOutputStream().flush();
				} else {
					this.writeBlankPDFForError(response);
				}
			}else {
				this.writeBlankPDFForError(response);
			}

		} catch (IOException e) {
			logger.error("There is an error while generating receipt for last txn", e);
		}
	}

	private void writeBlankPDFForError(HttpServletResponse response) {
		logger.info("There has been an error, hence writing the blank pdf file for the same");
		String outputError = "There was no transaction receipt found!!";
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
