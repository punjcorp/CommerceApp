package com.punj.app.ecommerce.controller.customer;
/**
 * 
 */

import java.io.InputStream;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.SupplierTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.payment.AJReceipt;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.financials.AJReportingBean;
import com.punj.app.ecommerce.models.financials.AccountDTO;
import com.punj.app.ecommerce.models.financials.AccountHeadBean;
import com.punj.app.ecommerce.models.financials.AccountJournalBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.AccountService;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class CustomerPaymentController {
	private static final Logger logger = LogManager.getLogger();
	private AccountService customerService;
	private CommonService commonService;
	private MessageSource messageSource;
	private CommerceContext commerceContext;
	private PaymentAccountService paymentService;

	@Value("${commerce.resource.bundle.base}")
	private String resourceBundleBase;

	/**
	 * @param customerService
	 *            the customerService to set
	 */
	@Autowired
	public void setAccountService(AccountService customerService) {
		this.customerService = customerService;
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

	@GetMapping(value = ViewPathConstants.CUSTOMER_PAYMENT_URL)
	public String showCustomerPaymentScreen(Model model, HttpSession session, RedirectAttributes redirectAttrs, final HttpServletRequest req) {
		logger.info("The customer payment screen pre tasks are in progress");
		AccountDTO accountDTO = new AccountDTO();

		Integer openLocationId = (Integer) commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);

		if (openLocationId != null && openLocationId > 0) {
			LocalDateTime openBusinessDate = (LocalDateTime) commerceContext.getStoreSettings(openLocationId + "-" + CommerceConstants.OPEN_BUSINESS_DATE);
			String locationName = (String) commerceContext.getStoreSettings(openLocationId + "-" + CommerceConstants.OPEN_LOC_NAME);

			String registerIdValue = req.getParameter(MVCConstants.REGISTER_ID_PARAM);
			Integer registerId = null;
			String registerName = null;

			if (StringUtils.isEmpty(registerIdValue)) {
				registerId = (Integer) session.getAttribute(openLocationId + MVCConstants.REGISTER_ID_PARAM);
				registerName = (String) session.getAttribute(openLocationId + MVCConstants.REG_NAME_PARAM);
			} else {
				registerId = new Integer(registerIdValue);
				registerName = req.getParameter(MVCConstants.REG_NAME_PARAM);
				session.setAttribute(openLocationId + MVCConstants.REGISTER_ID_PARAM, registerId);
				session.setAttribute(openLocationId + MVCConstants.REG_NAME_PARAM, registerName);
			}

			if (registerId != null && registerId > 0) {

				accountDTO.setBusinessDate(openBusinessDate);
				accountDTO.setLocationId(openLocationId);
				accountDTO.setLocationName(locationName);
				accountDTO.setRegisterId(registerId);
				accountDTO.setRegisterName(registerName);

				List<TenderBean> tenderBeans = this.retrieveValidTenders((Integer) openLocationId);

				String defaultTender = (String) commerceContext.getStoreSettings(openLocationId + "-" + CommerceConstants.LOC_DEFAULT_TENDER);
				accountDTO.setDefaultTender(defaultTender);

				model.addAttribute(MVCConstants.TENDER_BEANS, tenderBeans);

				model.addAttribute(MVCConstants.ACCOUNT_PAYMENT_DTO, accountDTO);
				logger.info("The customer payment screen is ready for display");

				return ViewPathConstants.CUSTOMER_PAYMENT_PAGE;
			} else {
				logger.info("There is no open register existing as per this session, routing to register open screen");
				redirectAttrs.addFlashAttribute(MVCConstants.REFERRER_URL_PARAM, ViewPathConstants.CUSTOMER_PAYMENT_URL);
				return ViewPathConstants.REDIRECT_URL + ViewPathConstants.REGISTER_OPEN_URL;
			}

		} else {
			logger.info("There is no open store existing as per application context, routing to store open screen");
			redirectAttrs.addFlashAttribute(MVCConstants.REFERRER_URL_PARAM, ViewPathConstants.CUSTOMER_PAYMENT_URL);
			return ViewPathConstants.REDIRECT_URL + ViewPathConstants.STORE_OPEN_URL;
		}

	}

	@PostMapping(value = ViewPathConstants.CUSTOMER_PAYMENT_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@Transactional
	public AccountJournalBean saveCustomerAccountPayment(@RequestBody AccountJournalBean journalBean, BindingResult bindingResult, Model model, Authentication authentication,
			HttpSession session, Locale locale) {
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			AccountJournal accountJournal = SupplierTransformer.transformAccountJournal(journalBean, userDetails.getUsername());
			Integer openLocationId = (Integer) commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
			// The account Id is actually supplier id, this field is used to avoid use of another object
			AccountHead accountHead = this.paymentService.retrievePaymentAccount(ServiceConstants.ACCOUNT_TYPE_CUSTOMER, new BigInteger(journalBean.getAccountId().toString()),
					openLocationId);
			if (accountHead != null) {
				accountJournal.setAccountId(accountHead.getAccountId());

				accountJournal = this.paymentService.savePayment(accountJournal, userDetails.getUsername());
				if (accountJournal != null) {

					Map<Integer, Tender> tenderMap = this.commonService.retrieveAllTendersAsMap(openLocationId);

					journalBean = SupplierTransformer.transformAccountJournalBean(accountJournal, userDetails.getUsername(), tenderMap);
					logger.info("The payment details for customer has been saved successfully");

					this.preparePaymentReceiptBeans(accountJournal, journalBean, openLocationId, session, locale);
				} else {
					logger.info("The payment details for customer were not saved due to some issue");
				}

			}
		} catch (Exception e) {
			logger.info("The payment details for customer were not saved due to some issue", e);
			journalBean = null;
		}
		return journalBean;
	}

	private List<TenderBean> retrieveValidTenders(Integer locationId) {
		List<Tender> tenders = this.commonService.retrieveAllTenders(locationId);
		List<TenderBean> tenderBeans = CommonMVCTransformer.tranformTenders(tenders);
		logger.info("The possible tenders for the location has been retrieved");
		return tenderBeans;
	}

	private void preparePaymentReceiptBeans(AccountJournal accountJournal, AccountJournalBean journalBean, Integer locationId, HttpSession session, Locale locale) throws JRException {
		AJReportingBean ajReportingBean = new AJReportingBean();
		String customerName = null;

		AccountHead accountHead = this.paymentService.retrievePaymentAccount(journalBean.getAccountId());
		if (accountHead != null && accountHead.getEntityType().equals(MVCConstants.CUSTOMER)) {
			List<Customer> customers = null;
			Customer customer = new Customer();
			customer.setCustomerId(accountHead.getEntityId());
			customers = this.customerService.searchCustomer(customer);
			if (customers != null && !customers.isEmpty()) {
				customer = customers.get(0);
				customerName = customer.getName();
			}
			logger.info("The customer details has been retrieved successfully for payment receipt");

			AccountHeadBean accountHeadBean = SupplierTransformer.transformAccountHead(accountHead, customerName);
			ajReportingBean.setAccountBean(accountHeadBean);
			journalBean.setPrintedBy(journalBean.getCreatedBy());
			ajReportingBean.setJournalBean(journalBean);

			logger.info("The account details has been retrieved successfully for payment receipt");

		}
		Location location = this.commonService.retrieveLocationDetails(locationId);
		LocationBean locationBean = CommonMVCTransformer.transformLocationDomainPartially(location, Boolean.FALSE);
		ajReportingBean.setLocationBean(locationBean);
		logger.info("The location details has been retrieved successfully for payment receipt");

		logger.info("The payment receipt details are ready for receipt generation");
		this.preparePaymentJasperReceipt(accountJournal, ajReportingBean, locale, session);

	}

	private void preparePaymentJasperReceipt(AccountJournal accountJournal, AJReportingBean ajReportingBean, Locale locale, HttpSession session) throws JRException {
		BigInteger journalId = ajReportingBean.getJournalBean().getJournalId();

		List<AJReportingBean> paymentList = new ArrayList<>();
		paymentList.add(ajReportingBean);

		InputStream txnReceiptReportStream = getClass().getResourceAsStream(MVCConstants.CUSTOMER_PAYMENT_RECEIPT_REPORT);
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(txnReceiptReportStream);
		logger.debug("The parent payment receipt report has been compiled now");

		InputStream txnReceiptStreamChild = getClass().getResourceAsStream(MVCConstants.CUSTOMER_PAYMENT_RECEIPT_TENDERS_REPORT);
		JasperReport jasperReportChild = (JasperReport) JRLoader.loadObject(txnReceiptStreamChild);
		logger.debug("The child payment tender receipt report has been compiled now");
		
		InputStream headerStream= getClass().getResourceAsStream(MVCConstants.HEADER_RECEIPT_REPORT);
		JasperReport jasperHeaderReport= (JasperReport) JRLoader.loadObject(headerStream);
		logger.debug("The header receipt report has been compiled now");

		ResourceBundle resourceBundle = ResourceBundle.getBundle(this.resourceBundleBase);
		JRBeanCollectionDataSource paymentDS = new JRBeanCollectionDataSource(paymentList);
		logger.debug("The payment receipt data source and resource bundle is ready now");

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MVCConstants.HEADER_REPORT_DIR, jasperHeaderReport);
		paramMap.put(MVCConstants.SUB_REPORT_DIR, jasperReportChild);
		paramMap.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
		paramMap.put(JRParameter.REPORT_LOCALE, locale);
		logger.debug("The parameter set for setting receipt data is ready");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, paymentDS);
		logger.debug("The receipt report is ready with payment data to be used");

		byte pdfBytes[] = JasperExportManager.exportReportToPdf(jasperPrint);

		AJReceipt ajReceipt = new AJReceipt();
		ajReceipt.setJournalId(accountJournal.getJournalId());
		ajReceipt.setReceiptData(pdfBytes);
		BigInteger accountJournalId=this.paymentService.savePaymentReceipt(ajReceipt);
		if(accountJournalId!=null)
			logger.info("The payment receipt details has been saved successfully");
		

		// This is to print the receipt when user select the printing option
		session.setAttribute(journalId + MVCConstants.PAYMENT_JASPER_PARAM, jasperPrint);
		// This is to show the receipt PDF in view screen
		session.setAttribute(MVCConstants.LAST_PAYMENT_NO, journalId);
		session.setAttribute(journalId + MVCConstants.PAYMENT_RCPT_PARAM, pdfBytes);

		logger.info("The receipt for payment transaction has been created");
	}

}