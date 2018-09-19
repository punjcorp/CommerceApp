package com.punj.app.ecommerce.controller.utils;
/**
 * 
 */

import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.models.utils.ReceiptBean;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.transaction.receipts.ReceiptService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class UtilsController {
	private static final Logger logger = LogManager.getLogger();
	private TransactionService transactionService;
	private ReceiptService receiptService;
	private MessageSource messageSource;

	@Value("${commerce.txn.receipt.copies}")
	private Integer receiptCopies;

	/**
	 * @param transactionService
	 *            the transactionService to set
	 */
	@Autowired
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/**
	 * @param receiptService
	 *            the receiptService to set
	 */
	@Autowired
	public void setReceiptService(ReceiptService receiptService) {
		this.receiptService = receiptService;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(value = ViewPathConstants.PI_POS_UTILS_URL)
	public String showUtilityScreen(Model model, HttpSession session) {
		logger.info("The add method for new supplier has been called");
		try {
			ReceiptBean receiptBean = new ReceiptBean();

			model.addAttribute(MVCConstants.UTILS_RECEIPT_BEAN, receiptBean);
			logger.info("The empty receipt generation utility object bean has been created");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while displaying utilities page.", e);
		}

		return ViewPathConstants.PI_POS_UTILS_PAGE;

	}

	@PostMapping(value = ViewPathConstants.PI_POS_UTILS_URL)
	public String generateReceipts(@ModelAttribute @Valid ReceiptBean receiptBean, final BindingResult bindingResult, Model model, Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			model.addAttribute(MVCConstants.UTILS_RECEIPT_BEAN, receiptBean);
			return ViewPathConstants.PI_POS_UTILS_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			this.receiptService.regenerateReceipts(receiptBean.getStartingInvoiceNo(), receiptBean.getEndingInvoiceNo(), receiptBean.getNoOfCopies(), locale, userDetails.getUsername());
			model.addAttribute(MVCConstants.SUCCESS, this.messageSource.getMessage("commerce.screen.util.receipt.generator.success", null, locale));

		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.util.receipt.generator.failure", null, locale));
			logger.error("There is an error while updating customer details", e);
		}

		model.addAttribute(MVCConstants.UTILS_RECEIPT_BEAN, receiptBean);
		return ViewPathConstants.PI_POS_UTILS_PAGE;
	}

}
