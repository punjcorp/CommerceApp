package com.punj.app.ecommerce.controller.supplier;
/**
 * 
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.supplier.SupplierDTO;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.financials.AJReportingBean;
import com.punj.app.ecommerce.models.financials.AccountDTO;
import com.punj.app.ecommerce.models.financials.AccountHeadBean;
import com.punj.app.ecommerce.models.financials.AccountJournalBean;
import com.punj.app.ecommerce.models.supplier.SupplierBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.SupplierService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.utils.Pager;

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
public class SupplierPaymentController {
	private static final Logger logger = LogManager.getLogger();
	private SupplierService supplierService;
	private CommonService commonService;
	private MessageSource messageSource;
	private CommerceContext commerceContext;
	private PaymentAccountService paymentService;

	@Value("${commerce.resource.bundle.base}")
	private String resourceBundleBase;

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
	public String addSupplier(Model model, HttpSession session, RedirectAttributes redirectAttrs, final HttpServletRequest req) {
		logger.info("The supplier payment screen pre tasks are in progress");
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
				logger.info("The supplier payment screen is ready for display");

				return ViewPathConstants.SUPPLIER_PAYMENT_PAGE;
			} else {
				logger.info("There is no open register existing as per this session, routing to register open screen");
				redirectAttrs.addFlashAttribute(MVCConstants.REFERRER_URL_PARAM, ViewPathConstants.SUPPLIER_PAYMENT_URL);
				return ViewPathConstants.REDIRECT_URL + ViewPathConstants.REGISTER_OPEN_URL;
			}

		} else {
			logger.info("There is no open store existing as per application context, routing to store open screen");
			redirectAttrs.addFlashAttribute(MVCConstants.REFERRER_URL_PARAM, ViewPathConstants.SUPPLIER_PAYMENT_URL);
			return ViewPathConstants.REDIRECT_URL + ViewPathConstants.STORE_OPEN_URL;
		}

	}

	@PostMapping(value = ViewPathConstants.SUPPLIER_PAYMENT_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@Transactional
	public AccountJournalBean saveAccountPayment(@RequestBody AccountJournalBean journalBean, BindingResult bindingResult, Model model,
			Authentication authentication, HttpSession session, Locale locale) {
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			AccountJournal accountJournal = SupplierTransformer.transformAccountJournal(journalBean, userDetails.getUsername());
			Integer openLocationId = (Integer) commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
			// The account Id is actually supplier id, this field is used to avoid use of another object
			AccountHead accountHead = this.paymentService.retrievePaymentAccount(ServiceConstants.ACCOUNT_TYPE_SUPPLIER,
					new BigInteger(journalBean.getAccountId().toString()), openLocationId);
			if (accountHead != null) {
				accountJournal.setAccountId(accountHead.getAccountId());

				accountJournal = this.paymentService.savePayment(accountJournal, userDetails.getUsername());
				if (accountJournal != null) {

					Map<Integer, Tender> tenderMap = this.commonService.retrieveAllTendersAsMap(openLocationId);

					journalBean = SupplierTransformer.transformAccountJournalBean(accountJournal, userDetails.getUsername(), tenderMap);
					logger.info("The payment details for supplier has been saved successfully");

					this.preparePaymentReceiptBeans(journalBean, openLocationId, session, locale);
				} else {
					logger.info("The payment details for supplier were not saved due to some issue");
				}

			}
		} catch (Exception e) {
			logger.info("The payment details for supplier were not saved due to some issue", e);
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

	@PostMapping(value = ViewPathConstants.SEARCH_SUPPLIER_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<SupplierBean> getSupplierDetails(@RequestBody AccountDTO accountDTO, Model model) {
		List<SupplierBean> suppliers = null;
		if (accountDTO != null && accountDTO.getEntityType() != null) {

			Pager pager = new Pager();
			pager.setCurrentPageNo(1);

			SupplierDTO supplierList = this.supplierService.searchSupplier(accountDTO.getEntityType(), pager);
			List<Supplier> suppliersList = supplierList.getSuppliers();
			Map<BigInteger, List<AccountHead>> accounts=null;
			
			if (accountDTO.getSearchAccount() != null && accountDTO.getSearchAccount() && suppliersList != null && !suppliersList.isEmpty()) {
				Set<BigInteger> supplierEntityIds = new HashSet<>();
				for (Supplier supplier : suppliersList) {
					supplierEntityIds.add(new BigInteger(supplier.getSupplierId().toString()));
				}

				accounts=this.paymentService.retrieveAccounts(supplierEntityIds, MVCConstants.SUPPLIER);
			}

			suppliers = SupplierTransformer.transformSuppliers(suppliersList, accounts);

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
			accountHead = this.paymentService.retrievePaymentAccount(entityType, entityId, openLocationId);
			if (accountHead != null) {
				accountDTO = SupplierTransformer.transformAccountHead(accountHead);
				logger.info("The account details for entity type {} and entity id {} has been retrieved", entityType, entityId);
			} else {
				logger.info("The account details for entity type {} and entity id {} was not found", entityType, entityId);
			}
		}
		return accountDTO;
	}

	private void preparePaymentReceiptBeans(AccountJournalBean journalBean, Integer locationId, HttpSession session, Locale locale) throws JRException {
		AJReportingBean ajReportingBean = new AJReportingBean();
		String supplierName = null;

		AccountHead accountHead = this.paymentService.retrievePaymentAccount(journalBean.getAccountId());
		if (accountHead != null && accountHead.getEntityType().equals(MVCConstants.SUPPLIER)) {
			Supplier supplier = new Supplier();
			supplier.setSupplierId(new Integer(accountHead.getEntityId().intValue()));
			supplier = this.supplierService.searchSupplier(supplier);
			if (supplier != null)
				supplierName = supplier.getName();

			logger.info("The supplier details has been retrieved successfully for payment receipt");

			AccountHeadBean accountHeadBean = SupplierTransformer.transformAccountHead(accountHead, supplierName);
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
		this.preparePaymentJasperReceipt(ajReportingBean, locale, session);

	}

	private void preparePaymentJasperReceipt(AJReportingBean ajReportingBean, Locale locale, HttpSession session) throws JRException {
		BigInteger journalId = ajReportingBean.getJournalBean().getJournalId();

		List<AJReportingBean> paymentList = new ArrayList<>();
		paymentList.add(ajReportingBean);

		InputStream txnReceiptReportStream = getClass().getResourceAsStream(MVCConstants.PAYMENT_RECEIPT_REPORT);
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(txnReceiptReportStream);
		logger.debug("The parent payment receipt report has been compiled now");

		InputStream txnReceiptStreamChild = getClass().getResourceAsStream(MVCConstants.PAYMENT_RECEIPT_TENDERS_REPORT);
		JasperReport jasperReportChild = (JasperReport) JRLoader.loadObject(txnReceiptStreamChild);
		logger.debug("The child payment tender receipt report has been compiled now");

		ResourceBundle resourceBundle = ResourceBundle.getBundle(this.resourceBundleBase);
		JRBeanCollectionDataSource paymentDS = new JRBeanCollectionDataSource(paymentList);
		logger.debug("The payment receipt data source and resource bundle is ready now");

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MVCConstants.SUB_REPORT_DIR, jasperReportChild);
		paramMap.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
		paramMap.put(JRParameter.REPORT_LOCALE, locale);
		logger.debug("The parameter set for setting receipt data is ready");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, paymentDS);
		logger.debug("The receipt report is ready with payment data to be used");

		byte pdfBytes[] = JasperExportManager.exportReportToPdf(jasperPrint);

		// This is to print the receipt when user select the printing option
		session.setAttribute(journalId + MVCConstants.PAYMENT_JASPER_PARAM, jasperPrint);
		// This is to show the receipt PDF in view screen
		session.setAttribute(MVCConstants.LAST_PAYMENT_NO, journalId);
		session.setAttribute(journalId + MVCConstants.PAYMENT_RCPT_PARAM, pdfBytes);

		logger.info("The receipt for payment transaction has been created");
	}

	@GetMapping(value = ViewPathConstants.PAYMENT_ACCOUNT_VIEW_RECEIPT_URL)
	private void viewPaymentReceipt(Model model, HttpSession session, Locale locale, HttpServletResponse response, final HttpServletRequest req) {
		try {
			final String paymentId = req.getParameter(MVCConstants.PAYMENT_ID_PARAM);
			if (paymentId != null) {
				byte pdfBytes[] = (byte[]) session.getAttribute(paymentId + MVCConstants.PAYMENT_RCPT_PARAM);
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "attachment; filename=" + paymentId + ".pdf");
				response.setContentLength(pdfBytes.length);
				response.getOutputStream().write(pdfBytes);
				logger.info("The {} payment receipt has been generated successfully", paymentId);
				response.getOutputStream().flush();
			} else {
				String outputError = "There was no payment receipt found for the provided txn!!";
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
					response.setHeader("Content-disposition", "attachment; filename=payment.pdf");
					response.getOutputStream().write(byteArrayOutputStream.toByteArray());
					logger.info("The payment receipt has been generated successfully");
					response.getOutputStream().flush();
				}
			}
		} catch (IOException e) {
			logger.error("There is an error while generating payment receipt", e);
		}
	}

	@PostMapping(value = ViewPathConstants.PAYMENT_ACCOUNT_PRINT_RECEIPT_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AccountJournalBean printPaymentReceipt(@RequestBody AccountJournalBean journalBean, Model model, Authentication authentication,
			HttpSession session) {
		BigInteger journalId = journalBean.getJournalId();
		try {
			JasperPrint jasperPrint = (JasperPrint) session.getAttribute(journalBean.getJournalId() + MVCConstants.PAYMENT_JASPER_PARAM);
			if (jasperPrint != null) {
				JasperPrintManager.printReport(jasperPrint, Boolean.FALSE);
				logger.info("The {} payment receipt has been printed successfully", journalId);
			} else {
				journalBean = null;
				logger.info("The payment receipt is not found");
			}
		} catch (JRException e) {
			logger.info("There was errror printing receipt for the {} payment", journalId);
		}
		return journalBean;
	}

}