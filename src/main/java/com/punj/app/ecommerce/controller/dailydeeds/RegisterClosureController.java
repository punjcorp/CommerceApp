package com.punj.app.ecommerce.controller.dailydeeds;
/**
 * 
 */

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.DailyDeedTransformer;
import com.punj.app.ecommerce.controller.common.transformer.TransactionTransformer;
import com.punj.app.ecommerce.domains.common.Denomination;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.finance.DailyTotals;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.TransactionReceipt;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.models.common.AJAXResponseBean;
import com.punj.app.ecommerce.models.common.BaseDenominationBean;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.RegisterBean;
import com.punj.app.ecommerce.models.dailydeeds.ClosingReportBean;
import com.punj.app.ecommerce.models.dailydeeds.ConcilationBean;
import com.punj.app.ecommerce.models.dailydeeds.DailyDeedBean;
import com.punj.app.ecommerce.models.dailydeeds.validator.RegisterOpenValidator;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.models.transaction.SaleTransactionReceipt;
import com.punj.app.ecommerce.models.transaction.TransactionHeader;
import com.punj.app.ecommerce.services.DailyDeedService;
import com.punj.app.ecommerce.services.FinanceService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.dtos.RegisterDTO;
import com.punj.app.ecommerce.services.dtos.DailyTransaction;
import com.punj.app.ecommerce.services.dtos.dailydeeds.DailyDeedDTO;
import com.punj.app.ecommerce.services.dtos.transaction.SaleTransactionReceiptDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class RegisterClosureController {
	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private MessageSource messageSource;
	private DailyDeedService dailyDeedService;
	private CommerceContext commerceContext;
	private TransactionService txnService;
	private FinanceService financeService;
	private RegisterOpenValidator registerOpenValidator;

	@Value("${commerce.resource.bundle.base}")
	private String resourceBundleBase;


	/**
	 * @param registerOpenValidator
	 *            the registerOpenValidator to set
	 */
	@Autowired
	public void setRegisterOpenValidator(RegisterOpenValidator registerOpenValidator) {
		this.registerOpenValidator = registerOpenValidator;
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
	 * @param financeService
	 *            the financeService to set
	 */
	@Autowired
	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	/**
	 * @param txnService
	 *            the txnService to set
	 */
	@Autowired
	public void setTransactionService(TransactionService txnService) {
		this.txnService = txnService;
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
	 * @param dailyDeedService
	 *            the dailyDeedService to set
	 */
	@Autowired
	public void setDailyDeedService(DailyDeedService dailyDeedService) {
		this.dailyDeedService = dailyDeedService;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(value = ViewPathConstants.REGISTER_CLOSE_URL)
	public String showOpenRegisterScreen(final HttpServletRequest req, Model model, Locale locale, HttpSession session) {
		logger.info("The show store open screen method has been called when store is already open");
		try {

			Integer register = null;
			String locationName = null;
			String defaultTender = null;

			Integer locationId = (Integer) commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
			LocalDateTime businessDate = (LocalDateTime) commerceContext.getStoreSettings(locationId + "-" + CommerceConstants.OPEN_BUSINESS_DATE);

			if (locationId != null && businessDate != null) {
				locationName = (String) commerceContext.getStoreSettings(locationId + "-" + CommerceConstants.OPEN_LOC_NAME);
				defaultTender = (String) commerceContext.getStoreSettings(locationId + "-" + CommerceConstants.LOC_DEFAULT_TENDER);

				DailyDeedBean dailyDeedBean = new DailyDeedBean();
				dailyDeedBean.setLocationId(locationId);
				dailyDeedBean.setLocationName(locationName);
				dailyDeedBean.setBusinessDate(businessDate);
				dailyDeedBean.setDefaultTender(defaultTender);

				String registerStr = req.getParameter(MVCConstants.REGISTER_ID_PARAM);
				if (registerStr != null) {
					register = new Integer(registerStr);
					dailyDeedBean.setRegister(register);

					this.updateBeansForSelectedRegister(dailyDeedBean, model, locale);
					logger.info("The bean details for provided register has been updated successfully");

				} else {
					logger.error("There is no register provided for the REGISTER CLOSURE");
				}
				this.updateAllRegisterClosureDetails(dailyDeedBean, locationId, businessDate, MVCConstants.TXN_CLOSE_REGISTER, model, locale);
				logger.info("All the register concilation details has been retrieved and updated");

			} else {
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.close.no.open.location", null, locale));
				logger.error("There was no valid OPEN LOCATION existing, hence the REGISTER CLOSURE is not possible");
			}

		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.close.error", null, locale));
			logger.error("There was some error retrieving store close details for register open", e);
		}

		return ViewPathConstants.REGISTER_CLOSE_PAGE;

	}

	private void updateAllRegisterClosureDetails(DailyDeedBean dailyDeedBean, Integer locationId, LocalDateTime businessDate, String txnType, Model model, Locale locale) {
		
		List<Tender> tenders = this.commonService.retrieveTendersForReconcilation(locationId);
		if (tenders != null && !tenders.isEmpty()) {
			List<TenderBean> tenderBeans = CommonMVCTransformer.tranformTenders(tenders);
			dailyDeedBean.setTenders(tenderBeans);
			logger.info("The Tenders needed for reconcilation has been updated in the daily deed bean");
		}
		

		List<Denomination> denominations = this.commonService.retrieveAllDenominations();
		if (denominations != null && !denominations.isEmpty()) {
			List<BaseDenominationBean> denominationBeans = CommonMVCTransformer.transformDenominations(denominations);
			dailyDeedBean.setDenominationList(denominationBeans);
			logger.info("The Tender Denominations needed for reconcilation has been updated in the daily deed bean");
		}
		
		RegisterDTO registerDTO = this.dailyDeedService.retrieveRegisterConcilationDtls(locationId, businessDate, txnType);
		List<RegisterBean> registers = CommonMVCTransformer.transformRegisterDTO(registerDTO);

		if(registerDTO!=null) {
			if(registerDTO.getAllRegisterClosed()) {
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.close.all.regs.closed", null, locale));
				logger.error("All the registers has been closed for the day, please proceed with store closure");
			}
			dailyDeedBean.setAllRegClosedFlag(registerDTO.getAllRegisterClosed());
		}
		
		model.addAttribute(MVCConstants.DAILY_DEED_BEAN, dailyDeedBean);
		model.addAttribute(MVCConstants.REGISTER_BEANS, registers);
		logger.info("All the beans needs for open store screen has been updated in model");
		
		
		
	}

	private void updateBeansForSelectedRegister(DailyDeedBean dailyDeedBean, Model model, Locale locale) {

		DailyTotals regTotals=null;
		DailyTotals dailyTotalCriteria = new DailyTotals();
		dailyTotalCriteria.setLocationId(dailyDeedBean.getLocationId());
		dailyTotalCriteria.setBusinessDate(dailyDeedBean.getBusinessDate());
		dailyTotalCriteria.setRegisterId(dailyDeedBean.getRegister());
		List<DailyTotals> dailyTotalsList = this.financeService.retrieveDailyTotals(dailyTotalCriteria);
		if (dailyTotalsList != null && !dailyTotalsList.isEmpty()) {
			regTotals=dailyTotalsList.get(0);
			if(regTotals!=null && regTotals.getStartOfDayAmount()!=null) {
				dailyDeedBean.setRegisterId(dailyDeedBean.getRegister());
				ConcilationBean concilationBean = DailyDeedTransformer.transformDailyTotals(regTotals, null);
				dailyDeedBean.setConcilationBean(concilationBean);
				logger.info("The DAILY TOTALS details for register {} has been retrieved successfully", dailyDeedBean.getRegister());
			}else {
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.close.not.open", null, locale));
				logger.error("The provided register {} has not been opened yet so it is not eligible for REGISTER CLOSURE");
			}
			
		} else {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.close.no.register.total", null, locale));
			logger.error("There is no register provided for the REGISTER CLOSURE");
		}

	}

	@PostMapping(value = ViewPathConstants.REGISTER_CLOSE_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AJAXResponseBean processRegisterClosure(@ModelAttribute @Valid DailyDeedBean dailyDeedBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication, HttpSession session) {

		if (bindingResult.hasErrors()) {
			return this.updateAJAXResponseBeanFailure(locale);
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				DailyTransaction registerCloseDetails = DailyDeedTransformer.transformOpenTxnDetails(dailyDeedBean);
				DailyDeedDTO regTotalsDTO= this.dailyDeedService.saveRegisterCloseTxn(registerCloseDetails, userDetails.getUsername());
				ClosingReportBean txnReceipt = null;
				if (regTotalsDTO != null) {
					txnReceipt = this.generateClosingReport(regTotalsDTO, session, userDetails.getUsername(), locale);

				}else{
					return this.updateAJAXResponseBeanFailure(locale);
				}
				session.removeAttribute(dailyDeedBean.getLocationId() + MVCConstants.REGISTER_ID_PARAM);
				session.removeAttribute(dailyDeedBean.getLocationId() + MVCConstants.REG_NAME_PARAM);
				logger.info("The Register close process was successful");
				return this.updateAJAXResponseForSuccess(locale, dailyDeedBean.getRegisterId(), dailyDeedBean.getRegisterName(), regTotalsDTO);
			}
		} catch (Exception e) {
			logger.error("There is an error while retrieving skus for sku lookup screen", e);
			return this.updateAJAXResponseBeanFailure(locale);
		}
		return null;
	}

	private AJAXResponseBean updateAJAXResponseBeanFailure(Locale locale) {
		AJAXResponseBean ajaxResponse = new AJAXResponseBean();
		ajaxResponse.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
		ajaxResponse.setStatusMsg(this.messageSource.getMessage("commerce.screen.register.close.failure.msg", null, locale));

		logger.info("There were some errors while processing the register closure process. The errors has been updated in response now!!");

		return ajaxResponse;
	}

	private AJAXResponseBean updateAJAXResponseForSuccess(Locale locale, Integer registerId, String registerName,DailyDeedDTO regTotalsDTO) {
		AJAXResponseBean ajaxResponse = new AJAXResponseBean();
		ajaxResponse.setResultObj(regTotalsDTO);
		ajaxResponse.setStatus(MVCConstants.AJAX_STATUS_SUCCESS);
		ajaxResponse
				.setStatusMsg(this.messageSource.getMessage("commerce.screen.register.close.success.msg", new Object[] { registerId, registerName }, locale));

		logger.info("The ajax response has been updated successfully based on register closing");

		return ajaxResponse;
	}


	public ClosingReportBean generateClosingReport(DailyDeedDTO regTotalsDTO, HttpSession session, String username, Locale locale) {
		ClosingReportBean txnReceipt = null;
		try {
			TransactionId txnId=regTotalsDTO.getTxnId();
			Location location=this.commonService.retrieveLocationDetails(txnId.getLocationId());
			if(location!=null){
				LocationBean locationBean=CommonMVCTransformer.transformLocationDomainPartially(location, Boolean.FALSE);
				txnReceipt=DailyDeedTransformer.transformDailyTotalsToReportBean(regTotalsDTO, username, locationBean);

				if (txnReceipt != null) {
					logger.info("The receipt details has been transformed successfully");
					logger.info("Start the receipt PDF generation now");
					List<ClosingReportBean> txnList = new ArrayList<>(1);
					txnList.add(txnReceipt);

					InputStream txnClosingReportStream = getClass().getResourceAsStream(MVCConstants.TXN_REG_CLOSE_REPORT);
					JasperReport jasperReport = (JasperReport) JRLoader.loadObject(txnClosingReportStream);
					logger.debug("The closing report has been compiled now");

					ResourceBundle resourceBundle = ResourceBundle.getBundle(this.resourceBundleBase);
					JRBeanCollectionDataSource txnDS = new JRBeanCollectionDataSource(txnList);
					logger.debug("The report data source and resource bundle is ready now");

					Map<String, Object> paramMap = new HashMap<>();
					paramMap.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
					paramMap.put(JRParameter.REPORT_LOCALE, locale);
					logger.debug("The parameter set for setting receipt data is ready");
					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, txnDS);
					logger.debug("The receipt report is ready with data to be used");

					String uniqueTxnNo = txnId.toString();
					byte pdfBytes[] = JasperExportManager.exportReportToPdf(jasperPrint);

					// This is to print the receipt when user select the printing option
					session.setAttribute(uniqueTxnNo + MVCConstants.RCPT_JASPER_PARAM, jasperPrint);
					// This is to show the receipt PDF in view screen
					session.setAttribute(MVCConstants.LAST_TXN_NO, uniqueTxnNo);
					session.setAttribute(uniqueTxnNo + MVCConstants.RCPT_PARAM, pdfBytes);

					// This section will save the receipt in database
					List<TransactionReceipt> txnReceipts = TransactionTransformer.getReceipts(pdfBytes, txnId, username);
					Boolean result = this.txnService.saveTransactionReceipt(txnReceipts);
					if (result) {
						logger.info("The transaction receipts has been saved in DB successfully");
					} else {
						logger.info("The transaction receipts were not saved due to unknown reason");
					}

				}
			}

		} catch (JRException e) {
			logger.error("There is an error while generating receipt for txn", e);
		}
		return txnReceipt;
	}

}
