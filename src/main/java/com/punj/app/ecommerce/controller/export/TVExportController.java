package com.punj.app.ecommerce.controller.export;
/**
 * 
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.punj.app.ecommerce.models.transaction.TxnBean;
import com.punj.app.ecommerce.models.transaction.TxnSearchBean;
import com.punj.app.ecommerce.services.ItemService;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;
import com.punj.app.ecommerce.tally.TallyVoucherBuilder;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class TVExportController {
	private static final Logger logger = LogManager.getLogger();
	private TransactionService txnService;
	private PaymentAccountService paymentAccountService;
	private CommonService commonService;
	private ItemService itemService;
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
	 * @param itemService
	 *            the itemService to set
	 */
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
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
	 * @param paymentAccountService
	 *            the paymentAccountService to set
	 */
	@Autowired
	public void setPaymentAccountService(PaymentAccountService paymentAccountService) {
		this.paymentAccountService = paymentAccountService;
	}

	/**
	 * @param commerceContext
	 *            the commerceContext to set
	 */
	@Autowired
	public void setCommerceContext(CommerceContext commerceContext) {
		this.commerceContext = commerceContext;
	}

	@GetMapping(value = ViewPathConstants.TALLY_VOUCHER_EXPORT_URL)
	public String showTxnLookupPage(Model model) {
		model.addAttribute(MVCConstants.TXN_SEARCH_BEAN, new TxnSearchBean());

		Map<String, String> txnTypes = new HashMap<>();
		txnTypes.put(MVCConstants.TXN_SALE_PARAM, "Tax Invoice");
		txnTypes.put(MVCConstants.TXN_RETURN_PARAM, "Debit Voucher");
		txnTypes.put(MVCConstants.TXN_NOSALE_PARAM, "Expense Voucher");
		txnTypes.put(MVCConstants.TXN_SUP_PAYMENT_PARAM, "Supplier Payment Voucher");
		txnTypes.put(MVCConstants.TXN_CUST_PAYMENT_PARAM, "Customer Payment Voucher");

		model.addAttribute(MVCConstants.TXN_TYPES_BEAN, txnTypes);

		return ViewPathConstants.TALLY_VOUCHER_EXPORT_PAGE;
	}

	@PostMapping(value = ViewPathConstants.TALLY_VOUCHER_EXPORT_URL)
	public String lookupTransactions(@ModelAttribute @Valid TxnSearchBean txnSearchBean, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors() && !model.containsAttribute(MVCConstants.SUCCESS)) {
			this.setTxnSearchModelBeans(txnSearchBean, model);
			return ViewPathConstants.TALLY_VOUCHER_EXPORT_PAGE;
		}

		List<TxnBean> txnBeanList = null;
		List<TransactionLookup> txnList = this.txnService.searchTxnJournals(txnSearchBean.getTxnType(), txnSearchBean.getStartDateTime(), txnSearchBean.getEndDateTime());
		if (txnList != null && !txnList.isEmpty()) {
			txnBeanList = TransactionTransformer.transformTxnLookupList(txnList);
		} else {
			logger.info("There was no txn found for the provided search");
		}

		this.setTxnSearchModelBeans(txnSearchBean, model);
		model.addAttribute(MVCConstants.TXN_LOOKUP_BEAN_LIST, txnBeanList);

		return ViewPathConstants.TALLY_VOUCHER_EXPORT_PAGE;
	}

	private void setTxnSearchModelBeans(TxnSearchBean txnSearchBean, Model model) {
		Map<String, String> txnTypes = new HashMap<>();
		txnTypes.put(MVCConstants.TXN_SALE_PARAM, "Tax Invoice");
		txnTypes.put(MVCConstants.TXN_RETURN_PARAM, "Debit Voucher");
		txnTypes.put(MVCConstants.TXN_NOSALE, "Expense Voucher");

		model.addAttribute(MVCConstants.TXN_TYPES_BEAN, txnTypes);

		model.addAttribute(MVCConstants.TXN_SEARCH_BEAN, txnSearchBean);
	}

	@GetMapping(value = ViewPathConstants.TXN_VOUCHER_EXPORT_URL, produces = { MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public Object exportTxnVoucher(@RequestParam("txnId") String uniqueTxnId, @RequestParam("txnType") String txnType) {
		Object envelope = null;
		TransactionDTO txnDetails = null;
		if (StringUtils.isNotBlank(uniqueTxnId) && StringUtils.isNotBlank(txnType)) {
			switch (txnType) {
			case MVCConstants.TXN_SALE_PARAM:
			case MVCConstants.TXN_RETURN_PARAM:
			case MVCConstants.TXN_NOSALE_PARAM:
				txnDetails = this.txnService.searchTransactionDetails(uniqueTxnId);
				if (txnDetails != null) {

					envelope = TallyVoucherBuilder.createTallyVoucher(txnDetails, txnType);
					logger.info("The transaction details has been converted to the Tally XML export successfully");

				} else {
					logger.error("The unique transaction is not found in the system");
				}
				break;

			case MVCConstants.TXN_SUP_PAYMENT_PARAM:
			case MVCConstants.TXN_CUST_PAYMENT_PARAM:
				txnDetails = this.paymentAccountService.retrievePaymentWithEntity(uniqueTxnId);
				if (txnDetails != null) {
					envelope = TallyVoucherBuilder.createTallyVoucher(txnDetails, txnType);
					logger.info("The transaction details has been converted to the Tally XML export successfully");

				} else {
					logger.error("The unique transaction is not found in the system");
				}
				break;
			}

		} else {
			logger.error("The unique transaction number for lookup is not provided");
		}

		return envelope;
	}

	@GetMapping(value = ViewPathConstants.TXN_VOUCHERS_EXPORT_URL, produces = { MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public Object exportVouchers(@RequestParam("txnType") String txnType, @RequestParam("startDateTime") String startDate,
			@RequestParam("endDateTime") String endDate) {

		List<Object> envelopes = new ArrayList<>();
		Object envelope = null;
		LocalDateTime stDate=null;
		LocalDateTime edDate=null;
		
		if (StringUtils.isNotBlank(txnType) && StringUtils.isNotBlank(startDate)) {
			
			stDate=Utils.parseDate(startDate);
			if(StringUtils.isNotBlank(endDate))
				edDate=Utils.parseDate(endDate);
			
			List<TransactionLookup> txnList = this.txnService.searchTxnJournals(txnType, stDate, edDate);
			if (txnList != null && !txnList.isEmpty()) {
				for (TransactionLookup txnLookup : txnList) {
					envelope = this.exportTxnVoucher(txnLookup.getUniqueTxnNo(), txnLookup.getTxnType());
					if (envelope != null) {
						envelopes.add(envelope);
						logger.info("The transaction details has been converted to the Tally XML export successfully");
					}

				}
				
				if(!envelopes.isEmpty()) {
					envelope=TallyVoucherBuilder.createTallyVoucherExport(envelopes);
					logger.info("The transaction details has been converted to voucher details successfully");
				}
				
			}

		}

		else {
			logger.error("The transaction lookup details were not provided");
		}

		return envelope;
	}

}
