/**
 * 
 */
package com.punj.app.ecommerce.controller.transaction;

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
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.TransactionTransformer;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.models.transaction.SaleTransaction;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;

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
		logger.info("The txn details has been saved successfully");
		return txnId;
	}

}
