package com.punj.app.ecommerce.controller.supplier;
/**
 * 
 */

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.SupplierTransformer;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.supplier.SupplierDTO;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.models.financials.AccountDTO;
import com.punj.app.ecommerce.models.financials.AccountJournalBean;
import com.punj.app.ecommerce.models.supplier.SupplierBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.SupplierService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class SupplierPaymentController {
	private static final Logger logger = LogManager.getLogger();
	private SupplierService supplierService;
	private CommonService commonService;
	private MessageSource messageSource;
	private CommerceContext commerceContext;
	private PaymentAccountService paymentService;

	/**
	 * @param supplierService
	 *            the supplierService to set
	 */
	@Autowired
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
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
	 * @param paymentService
	 *            the paymentService to set
	 */
	@Autowired
	public void setPaymentService(PaymentAccountService paymentService) {
		this.paymentService = paymentService;
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
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(value = ViewPathConstants.SUPPLIER_PAYMENT_URL)
	public String addSupplier(Model model, HttpSession session) {
		logger.info("The supplier payment screen pre tasks are in progress");
		AccountDTO accountDTO = new AccountDTO();

		Integer openLocationId = (Integer) commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
		LocalDateTime openBusinessDate = (LocalDateTime) commerceContext.getStoreSettings(openLocationId + "-" + CommerceConstants.OPEN_BUSINESS_DATE);
		Integer registerId = (Integer) session.getAttribute(MVCConstants.REGISTER_ID_PARAM);

		accountDTO.setBusinessDate(openBusinessDate);
		accountDTO.setLocationId(openLocationId);
		accountDTO.setRegisterId(registerId);

		List<TenderBean> tenderBeans = this.retrieveValidTenders((Integer) openLocationId);

		String defaultTender = (String) commerceContext.getStoreSettings(openLocationId + "-" + CommerceConstants.LOC_DEFAULT_TENDER);
		accountDTO.setDefaultTender(defaultTender);

		model.addAttribute(MVCConstants.TENDER_BEANS, tenderBeans);

		model.addAttribute(MVCConstants.ACCOUNT_PAYMENT_DTO, accountDTO);
		logger.info("The supplier payment screen is ready for display");

		return ViewPathConstants.SUPPLIER_PAYMENT_PAGE;

	}

	@PostMapping(value = ViewPathConstants.SUPPLIER_PAYMENT_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@Transactional
	public AccountJournalBean saveAccountPayment(@RequestBody AccountJournalBean journalBean, BindingResult bindingResult, Model model,
			Authentication authentication) {

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		AccountJournal accountJournal = SupplierTransformer.transformAccountJournal(journalBean, userDetails.getUsername());
		Integer openLocationId = (Integer) commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
		accountJournal = this.paymentService.savePayment(accountJournal, openLocationId);
		if (accountJournal != null) {
			journalBean.setJournalId(accountJournal.getJournalId());
			logger.info("The payment details for supplier has been saved successfully");
		} else {
			logger.info("The payment details for supplier were not saved due to some issue");
		}
		return journalBean;
	}

	private List<TenderBean> retrieveValidTenders(Integer locationId) {
		List<Tender> tenders = this.commonService.retrieveAllTenders(locationId);
		List<TenderBean> tenderBeans = CommonMVCTransformer.tranformTenders(tenders);
		logger.info("The possible tenders for the location has been retrieved");
		return tenderBeans;
	}

	@PostMapping(value = ViewPathConstants.SEARCH_SUPPLIER_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<SupplierBean> getSupplierDetails(@RequestBody AccountDTO accountDTO, Model model) {
		List<SupplierBean> suppliers = null;
		if (accountDTO != null && accountDTO.getEntityType() != null) {

			Pager pager = new Pager();
			pager.setCurrentPageNo(1);

			SupplierDTO supplierList = this.supplierService.searchSupplier(accountDTO.getEntityType(), pager);
			List<Supplier> suppliersList = supplierList.getSuppliers();

			suppliers = SupplierTransformer.transformSuppliers(suppliersList);

			if (suppliers != null && !suppliers.isEmpty()) {
				logger.info("The suppliers were retrieved successfully based on the criteria");
			} else {
				logger.info("There was no supplier found with the searched text");
			}
		}
		return suppliers;
	}

	@PostMapping(value = ViewPathConstants.PAYMENT_ACCOUNT_DTLS_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@Transactional
	public AccountDTO getPaymentAccountDetails(@RequestBody AccountDTO accountDTO, Model model) {

		if (accountDTO != null && accountDTO.getEntityType() != null) {
			String entityType = accountDTO.getEntityType();
			BigInteger entityId = accountDTO.getEntityId();
			AccountHead accountHead;
			Integer openLocationId = (Integer) commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
			accountHead = this.paymentService.retrievePaymentAccount(entityType, entityId,openLocationId);
			if (accountHead != null) {
				accountDTO = SupplierTransformer.transformAccountHead(accountHead);
				logger.info("The account details for entity type {} and entity id {} has been retrieved", entityType, entityId);
			} else {
				logger.info("The account details for entity type {} and entity id {} was not found", entityType, entityId);
			}
		}
		return accountDTO;
	}

}