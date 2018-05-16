/**
 * 
 */
package com.punj.app.ecommerce.controller.nosale;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.ExpenseTransformer;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.NoSaleTransaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.models.nosale.ExpenseBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.NoSaleService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ExpenseController {

	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private TransactionService transactionService;
	private NoSaleService noSaleService;
	private CommerceContext commerceContext;
	private ResourceBundleMessageSource messageSource;

	/**
	 * @param noSaleService
	 *            the noSaleService to set
	 */
	@Autowired
	public void setNoSaleService(NoSaleService noSaleService) {
		this.noSaleService = noSaleService;
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

	@GetMapping(value = ViewPathConstants.TXN_EXPENSE_URL)
	public String processExpenseType(Model model, HttpSession session) {
		ExpenseBean expenseBean = new ExpenseBean();

		Integer openLocationId = (Integer) commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);

		if (openLocationId!=null) {
			
			Integer registerId = (Integer) session.getAttribute(MVCConstants.REGISTER_ID_PARAM);
			
			if (registerId!=null) {
				
				List<TenderBean> tenderBeans = this.retrieveValidTenders((Integer) openLocationId);
				
				Object openLocationName = commerceContext.getStoreSettings(openLocationId + "-" + CommerceConstants.OPEN_LOC_NAME);
				LocalDateTime openBusinessDate = (LocalDateTime) commerceContext.getStoreSettings(openLocationId + "-" + CommerceConstants.OPEN_BUSINESS_DATE);
				
				String defaultTender = (String) commerceContext.getStoreSettings(openLocationId + "-" + CommerceConstants.LOC_DEFAULT_TENDER);
				String registerName = (String) session.getAttribute(MVCConstants.REG_NAME_PARAM);
				
				expenseBean.setDefaultTender(defaultTender);
				expenseBean.setBusinessDate(openBusinessDate);
				expenseBean.setLocationId(openLocationId);
				expenseBean.setRegisterId(registerId);
				expenseBean.setRegisterName(registerName);
				expenseBean.setLocationName((String) openLocationName);
				
				model.addAttribute(MVCConstants.TENDER_BEANS, tenderBeans);
				model.addAttribute(MVCConstants.EXPENSE_PARAM, expenseBean);
				logger.info("The expense screen is ready for the display");
				return ViewPathConstants.TXN_EXPENSE_PAGE;
			} else {
				logger.info("There is no open register existing as per this session, routing to register open screen");
				return ViewPathConstants.REDIRECT_URL + ViewPathConstants.REGISTER_OPEN_URL;
			}

		} else {
			logger.info("There is no open store existing as per application context, routing to store open screen");
			return ViewPathConstants.REDIRECT_URL + ViewPathConstants.STORE_OPEN_URL;
		}

	}

	@PostMapping(value = ViewPathConstants.TXN_EXPENSE_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@Transactional
	public TransactionId saveExpenseType(@RequestBody ExpenseBean expenseBean, Model model, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		NoSaleTransaction noSaleTxn = ExpenseTransformer.transformExpenseDetails(expenseBean, userDetails.getUsername());
		noSaleTxn = this.noSaleService.saveNoSaleTxn(noSaleTxn);
		TransactionId txnId = null;
		if (noSaleTxn != null) {
			txnId = noSaleTxn.getTransactionId();
			logger.info("The No Sale transaction details has been save successfully");
		} else {
			logger.info("The No Sale transaction details has been save successfully");
		}
		return txnId;
	}

	private List<TenderBean> retrieveValidTenders(Integer locationId) {
		List<Tender> tenders = this.commonService.retrieveAllTenders(locationId);
		List<TenderBean> tenderBeans = CommonMVCTransformer.tranformTenders(tenders);
		logger.info("The possible tenders for the location has been retrieved");
		return tenderBeans;
	}

	public void generateExpenseReceipt(ExpenseBean expenseBean) {

		logger.info("The receipt for expense transaction has been created");
	}

}
