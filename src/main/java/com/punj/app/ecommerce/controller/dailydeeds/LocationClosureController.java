package com.punj.app.ecommerce.controller.dailydeeds;
/**
 * 
 */

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.punj.app.ecommerce.controller.common.transformer.TransactionTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.transaction.TransactionReceipt;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.models.dailydeeds.ClosingReportBean;
import com.punj.app.ecommerce.services.dtos.dailydeeds.DailyDeedDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.DailyDeedTransformer;
import com.punj.app.ecommerce.controller.common.transformer.EODDeedTransformer;
import com.punj.app.ecommerce.domains.common.Denomination;
import com.punj.app.ecommerce.domains.finance.DailyTotals;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.domains.transaction.tender.TenderDenomination;
import com.punj.app.ecommerce.models.common.AJAXResponseBean;
import com.punj.app.ecommerce.models.common.BaseDenominationBean;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.dailydeeds.ConcilationBean;
import com.punj.app.ecommerce.models.dailydeeds.DailyDeedBean;
import com.punj.app.ecommerce.models.dailydeeds.validator.RegisterOpenValidator;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.DailyDeedService;
import com.punj.app.ecommerce.services.FinanceService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;
import com.punj.app.ecommerce.services.dtos.DailyTransaction;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class LocationClosureController {
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

	@GetMapping(value = ViewPathConstants.STORE_CLOSE_URL)
	public String showOpenRegisterScreen(final HttpServletRequest req, Model model, Locale locale, HttpSession session) {
		logger.info("The show store open screen method has been called when store is already open");
		try {
			Integer locationId = null;
			String locationName = null;
			String defaultTender = null;
			LocalDateTime businessDate = null;

			String locationStr = req.getParameter(MVCConstants.LOCATION_ID_PARAM);
			if (StringUtils.isBlank(locationStr)) {
				locationId = (Integer) commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
			} else {
				locationId = new Integer(locationStr);
			}

			if (locationId != null && locationId > 0) {
				locationName = (String) commerceContext.getStoreSettings(locationId + "-" + CommerceConstants.OPEN_LOC_NAME);
				businessDate = (LocalDateTime) commerceContext.getStoreSettings(locationId + "-" + CommerceConstants.OPEN_BUSINESS_DATE);
				defaultTender = (String) commerceContext.getStoreSettings(locationId + "-" + CommerceConstants.LOC_DEFAULT_TENDER);

				if (businessDate != null) {

					DailyTotals dailyTotalCriteria = new DailyTotals();
					dailyTotalCriteria.setLocationId(locationId);
					dailyTotalCriteria.setBusinessDate(businessDate);

					List<DailyTotals> dailyTotalsList = this.financeService.retrieveDailyTotals(dailyTotalCriteria);
					DailyTotals storeTotals = null;
					BigDecimal expectedTotal = BigDecimal.ZERO;
					Boolean isEligible = Boolean.TRUE;
					if (dailyTotalsList != null && !dailyTotalsList.isEmpty()) {

						for (DailyTotals dailyTotal : dailyTotalsList) {
							if (dailyTotal.getRegisterId() == null) {
								storeTotals = dailyTotal;
							} else {
								if (dailyTotal.getEndOfDayAmount() != null)
									expectedTotal = expectedTotal.add(dailyTotal.getEndOfDayAmount());
								else {
									isEligible = Boolean.FALSE;
									break;
								}
							}
						}

						if (isEligible) {

							ConcilationBean concilationBean = DailyDeedTransformer.transformDailyTotals(storeTotals, expectedTotal);

							DailyDeedBean dailyDeedBean = new DailyDeedBean();
							dailyDeedBean.setRegister(BigInteger.ONE.intValue());
							dailyDeedBean.setLocationId(locationId);
							dailyDeedBean.setLocationName(locationName);
							dailyDeedBean.setBusinessDate(businessDate);
							dailyDeedBean.setDefaultTender(defaultTender);
							dailyDeedBean.setConcilationBean(concilationBean);

							this.updateBeansForCloseProcess(dailyDeedBean, model, session);

							logger.info("The DAILY TOTALS details for store {} has been retrieved successfully", locationStr);
						}else {
							model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.close.register.dtotal.not.found", null, locale));
							logger.error("One or more registers has not been closed yet, please proceed to register closure screen for register closing", locationStr);
						}

					} else {
						model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.close.dtotal.not.found", null, locale));
						logger.error("The DAILY TOTALS details for store {} were not found in database", locationStr);
					}

				} else {
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.close.bdate.not.found", null, locale));
					logger.error("The location {} OPEN business date is not found", locationStr);
				}

			} else {
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.close.no.location", null, locale));
				logger.error("There is no location no provided for the store closing process");
			}

		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.close.error", null, locale));
			logger.error("There was some error retrieving store close details", e);
		}

		return ViewPathConstants.STORE_CLOSE_PAGE;

	}

	private void updateBeansForCloseProcess(DailyDeedBean dailyDeedBean, Model model, HttpSession session) {

		List<TenderCount> tenderCounts = this.dailyDeedService.searchTxnTenderCounts(dailyDeedBean.getLocationId(), null, dailyDeedBean.getBusinessDate(),
				MVCConstants.TXN_CLOSE_REGISTER);

		Map<Integer, Map<Integer, TenderDenomination>> tenderMap = EODDeedTransformer.transformRegisterCloseTenderCounts(tenderCounts);
		logger.info("The tender denominations for register closing txns has been retrieved and transformed successfully");

		List<Tender> tenders = this.commonService.retrieveTendersForReconcilation(dailyDeedBean.getLocationId());
		List<TenderBean> tenderBeans = EODDeedTransformer.tranformTenders(tenders, tenderMap);
		logger.info("The transformed tender beans for reconcilation has been updated successfully");
		dailyDeedBean.setTenders(tenderBeans);

		List<Denomination> denominations = this.commonService.retrieveAllDenominations();
		if (denominations != null) {
			List<BaseDenominationBean> denominationBeans = CommonMVCTransformer.transformDenominations(denominations);
			dailyDeedBean.setDenominationList(denominationBeans);

			LocationDTO locationDTO = this.commonService.retrieveLocationWithDailyStatus();
			List<LocationBean> locations = CommonMVCTransformer.transformLocationDTO(locationDTO);

			String lastLocationStatus = null;
			Integer lastLocationId = null;
			String lastLocationName = null;

			if (locations != null && locations.size() == 1) {
				lastLocationStatus = locations.get(0).getLastStatus();
				lastLocationId = locations.get(0).getLocationId();
				lastLocationName = locations.get(0).getName();

			} else if (locations != null && locations.size() > 1) {

				for (LocationBean locationBean : locations) {
					if (locationBean.getLocationId().equals(dailyDeedBean.getLocationId())) {
						lastLocationStatus = locationBean.getLastStatus();
						lastLocationId = locationBean.getLocationId();
						lastLocationName = locationBean.getName();
						break;
					}
				}

			}
			if (StringUtils.isNotBlank(lastLocationName) && dailyDeedBean.getLocationId() != null) {

				if (StringUtils.isBlank(lastLocationStatus)
						|| (StringUtils.isNotBlank(lastLocationStatus) && MVCConstants.TXN_CLOSE_STORE.equals(lastLocationStatus))) {
					dailyDeedBean.setLocationId(null);
					logger.error("The store is not in OPEN status, hence is not eligible for CLOSING");
				} else if (StringUtils.isNotBlank(lastLocationStatus) && MVCConstants.TXN_OPEN_STORE.equals(lastLocationStatus)) {
					dailyDeedBean.setLocationId(lastLocationId);
					dailyDeedBean.setLocationName(lastLocationName);
					logger.error("The store is setup for CLOSING now");
				}
			}

			model.addAttribute(MVCConstants.DAILY_DEED_BEAN, dailyDeedBean);
			model.addAttribute(MVCConstants.LOCATION_BEANS, locations);
			logger.info("All the beans needs for store closing screen has been updated in model");
		}

	}

	@PostMapping(value = ViewPathConstants.STORE_CLOSE_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AJAXResponseBean processRegisterClosure(@ModelAttribute @Valid DailyDeedBean dailyDeedBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication, HttpSession session) {

		if (bindingResult.hasErrors()) {
			return this.updateAJAXResponseBeanFailure(locale);
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				DailyTransaction storeCloseDetails = DailyDeedTransformer.transformOpenTxnDetails(dailyDeedBean);
				DailyDeedDTO storeTotalsDTO= this.dailyDeedService.saveStoreCloseTxn(storeCloseDetails, userDetails.getUsername());
				ClosingReportBean txnReceipt = null;
				if (storeTotalsDTO==null) {
					return this.updateAJAXResponseBeanFailure(locale);
				} else {

					txnReceipt = this.generateClosingReport(storeTotalsDTO, session, userDetails.getUsername(), locale);

					commerceContext.setStoreSettings(CommerceConstants.OPEN_LOC_ID, null);
					commerceContext.setStoreSettings(CommerceConstants.OPEN_LOC_NAME, null);
					commerceContext.setStoreSettings(CommerceConstants.OPEN_BUSINESS_DATE, null);
					commerceContext.setStoreSettings(CommerceConstants.LOC_DEFAULT_TENDER, null);
					logger.info("The Store close process was successful");

					return this.updateAJAXResponseForSuccess(locale, dailyDeedBean.getLocationId(), dailyDeedBean.getLocationName(), storeTotalsDTO);
				}
			}
		} catch (Exception e) {
			logger.error("There is an error while processing the store closure request", e);
			return this.updateAJAXResponseBeanFailure(locale);
		}
		return null;
	}

	private AJAXResponseBean updateAJAXResponseBeanFailure(Locale locale) {
		AJAXResponseBean ajaxResponse = new AJAXResponseBean();
		ajaxResponse.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
		ajaxResponse.setStatusMsg(this.messageSource.getMessage("commerce.screen.store.close.failure.msg", null, locale));

		logger.info("There were some errors while processing the store closure process. The errors has been updated in response now!!");

		return ajaxResponse;
	}

	private AJAXResponseBean updateAJAXResponseForSuccess(Locale locale, Integer locationId, String locName,DailyDeedDTO storeTotalsDTO) {
		AJAXResponseBean ajaxResponse = new AJAXResponseBean();
		ajaxResponse.setResultObj(storeTotalsDTO);
		ajaxResponse.setStatus(MVCConstants.AJAX_STATUS_SUCCESS);
		ajaxResponse.setStatusMsg(this.messageSource.getMessage("commerce.screen.store.close.success.msg", new Object[] { locationId, locName }, locale));

		logger.info("The ajax response has been updated successfully based on store closing");

		return ajaxResponse;
	}

	public ClosingReportBean generateClosingReport(DailyDeedDTO storeTotalsDTO, HttpSession session, String username, Locale locale) {
		ClosingReportBean txnReceipt = null;
		try {
			TransactionId txnId=storeTotalsDTO.getTxnId();
			Location location=this.commonService.retrieveLocationDetails(txnId.getLocationId());
			if(location!=null){
				LocationBean locationBean=CommonMVCTransformer.transformLocationDomainPartially(location, Boolean.FALSE);
				txnReceipt=DailyDeedTransformer.transformDailyTotalsToReportBean(storeTotalsDTO, username, locationBean);

				if (txnReceipt != null) {
					logger.info("The receipt details has been transformed successfully");
					logger.info("Start the receipt PDF generation now");
					List<ClosingReportBean> txnList = new ArrayList<>(1);
					txnList.add(txnReceipt);

					InputStream txnClosingReportStream = getClass().getResourceAsStream(MVCConstants.TXN_STORE_CLOSE_REPORT);
					JasperReport jasperReport = (JasperReport) JRLoader.loadObject(txnClosingReportStream);
					logger.debug("The closing report has been compiled now");

					InputStream headerStream= getClass().getResourceAsStream(MVCConstants.HEADER_RECEIPT_REPORT);
					JasperReport jasperHeaderReport= (JasperReport) JRLoader.loadObject(headerStream);
					logger.debug("The header receipt report has been compiled now");
					
					ResourceBundle resourceBundle = ResourceBundle.getBundle(this.resourceBundleBase);
					JRBeanCollectionDataSource txnDS = new JRBeanCollectionDataSource(txnList);
					logger.debug("The report data source and resource bundle is ready now");

					Map<String, Object> paramMap = new HashMap<>();
					paramMap.put(MVCConstants.HEADER_REPORT_DIR, jasperHeaderReport);
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
					List<TransactionReceipt> txnReceipts = TransactionTransformer.getReceipts(pdfBytes, txnId, username, Boolean.TRUE, Boolean.FALSE);
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
