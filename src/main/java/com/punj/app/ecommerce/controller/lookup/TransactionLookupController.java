package com.punj.app.ecommerce.controller.lookup;
/**
 * 
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.TransactionTransformer;
import com.punj.app.ecommerce.domains.transaction.TransactionLookup;
import com.punj.app.ecommerce.models.transaction.SaleTransaction;
import com.punj.app.ecommerce.models.transaction.TxnBean;
import com.punj.app.ecommerce.models.transaction.TxnSearchBean;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class TransactionLookupController {
	private static final Logger logger = LogManager.getLogger();
	private TransactionService txnService;
	private CommonService commonService;
	private CommerceContext commerceContext;

	/**
	 * @param accountService
	 *            the accountService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param txnService
	 *            the txnService to set
	 */
	@Autowired
	public void setTxnService(TransactionService txnService) {
		this.txnService = txnService;
	}

	/**
	 * @param commerceContext
	 *            the commerceContext to set
	 */
	@Autowired
	public void setCommerceContext(CommerceContext commerceContext) {
		this.commerceContext = commerceContext;
	}

	@GetMapping(value = ViewPathConstants.TXN_LOOKUP_URL)
	public String showTxnLookupPage(Model model) {
		model.addAttribute(MVCConstants.TXN_SEARCH_BEAN, new TxnSearchBean());

		Map<String, String> txnTypes = new HashMap<>();
		txnTypes.put(MVCConstants.TXN_SALE_PARAM, "Tax Invoice");
		txnTypes.put(MVCConstants.TXN_RETURN_PARAM, "Debit Voucher");
		txnTypes.put(MVCConstants.EXPENSE_PARAM, "Expense Voucher");

		model.addAttribute(MVCConstants.TXN_TYPES_BEAN, txnTypes);

		return ViewPathConstants.TXN_LOOKUP_PAGE;
	}

	@PostMapping(value = ViewPathConstants.TXN_LOOKUP_URL)
	public String lookupTransactions(@ModelAttribute @Valid TxnSearchBean txnSearchBean, BindingResult bindingResult, Model model) {
		List<TxnBean> txnBeanList = null;

		List<TransactionLookup> txnList = this.txnService.searchTxnJournals(txnSearchBean.getTxnType(), txnSearchBean.getStartDateTime(), txnSearchBean.getEndDateTime());
		if (txnList != null && !txnList.isEmpty()) {
			txnBeanList = TransactionTransformer.transformTxnLookupList(txnList);
		} else {
			logger.info("There was no txn found for the provided search");
		}

		Map<String, String> txnTypes = new HashMap<>();
		txnTypes.put(MVCConstants.TXN_SALE_PARAM, "Tax Invoice");
		txnTypes.put(MVCConstants.TXN_RETURN_PARAM, "Debit Voucher");
		txnTypes.put(MVCConstants.TXN_NOSALE, "Expense Voucher");

		model.addAttribute(MVCConstants.TXN_TYPES_BEAN, txnTypes);

		model.addAttribute(MVCConstants.TXN_SEARCH_BEAN, txnSearchBean);

		model.addAttribute(MVCConstants.TXN_LOOKUP_BEAN_LIST, txnBeanList);

		return ViewPathConstants.TXN_LOOKUP_PAGE;
	}

	@GetMapping(value = ViewPathConstants.TXN_DETAILS_LOOKUP_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public SaleTransaction lookupTxnDetails(@RequestParam("uniqueTxnNo") String uniqueTxnNo) {
		SaleTransaction saleTxn = null;
		try {

			TransactionDTO txnDTO = this.txnService.searchTransactionDetails(uniqueTxnNo);
			if (txnDTO != null) {
				saleTxn = TransactionTransformer.transformSaleTxn(txnDTO);
				logger.info("The transaction details has been retrieved successfully");
			}else {
				logger.info("The transaction details retreival has failed");
			}
				
		} catch (Exception e) {
			logger.error("There is an error while retrieving transaction details", e);
		}
		return saleTxn;
	}

	/*
	 * @PostMapping(value = ViewPathConstants.TXN_LOOKUP_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseBody public DataTableResponse lookupTransactions(@ModelAttribute @Valid TxnSearchBean txnSearchBean, BindingResult bindingResult, Model model) {
	 * DataTableResponse dtResponse = null;
	 * 
	 * return dtResponse; }
	 */

}
