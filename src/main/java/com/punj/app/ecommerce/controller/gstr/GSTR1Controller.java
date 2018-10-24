package com.punj.app.ecommerce.controller.gstr;
/**
 * 
 */

import java.io.File;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.gstr.GSTHSN;
import com.punj.app.ecommerce.domains.gstr.GSTInvoice;
import com.punj.app.ecommerce.gst.r1.GSTR1;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.gstr.FinancialMonth;
import com.punj.app.ecommerce.models.gstr.FinancialYear;
import com.punj.app.ecommerce.models.gstr.GSTRSearchBean;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.gst.GSTRService;
import com.punj.app.ecommerce.utils.gst.GSTROneUtil;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class GSTR1Controller {
	private static final Logger logger = LogManager.getLogger();
	private GSTRService gstrService;
	private TransactionService txnService;
	private CommonService commonService;
	private MessageSource messageSource;

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param gstrService
	 *            the gstrService to set
	 */
	@Autowired
	public void setGstrService(GSTRService gstrService) {
		this.gstrService = gstrService;
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
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(value = ViewPathConstants.GSTR1_GENERATION_URL)
	public String showTxnLookupPage(Model model) {

		this.updateBeanDetails(model, new GSTRSearchBean());
		return ViewPathConstants.GSTR1_GENERATION_PAGE;

	}

	@PostMapping(value = ViewPathConstants.GSTR1_GENERATION_URL)
	public String generateGSTR1(@ModelAttribute @Valid GSTRSearchBean gstrSearchBean, BindingResult bindingResult, Model model, Locale locale, Authentication authentication) {
		logger.info("The show store open screen method has been called");
		GSTR1 gstr1Obj = null;
		try {
			if (!bindingResult.hasErrors()) {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				if (userDetails != null) {

					Map<Customer, Map<String, List<GSTInvoice>>> saleTxns = this.gstrService.retrieveGSTRInvoiceData(gstrSearchBean.getLocationId(),
							gstrSearchBean.getFinancialMonth());

					List<GSTHSN> hsnDetails = this.gstrService.retrieveGSTRHSNData(gstrSearchBean.getLocationId(), gstrSearchBean.getFinancialMonth());
					String gstNo = "03AAEFC0339G1Z2";
					gstr1Obj = GSTROneUtil.generateGSTR1(gstrSearchBean.getFinancialYear(), gstrSearchBean.getFinancialMonth(), gstNo, hsnDetails, saleTxns);
					ObjectMapper mapper = new ObjectMapper();
					mapper.writeValue(new File("gstr1.json"), gstr1Obj);
					model.addAttribute(MVCConstants.GSTR1_JSON_PARAM, gstr1Obj);
					model.addAttribute(MVCConstants.SUCCESS, this.messageSource.getMessage("commerce.screen.gstr1.success", null, locale));
				}
			}
		} catch (Exception e) {
			logger.error("There is an error while generating GSTR 1 invoice details", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.gstr1.failure", null, locale));
		}
		this.updateBeanDetails(model, gstrSearchBean);
		return ViewPathConstants.GSTR1_GENERATION_PAGE;
	}

	@GetMapping(value = ViewPathConstants.GSTR1_JSON_GENERATION_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public GSTR1 generateGSTR1JSONFile(@RequestParam("finYear") String finYear, @RequestParam("locationId") Integer locationId, @RequestParam("finMonth") String finMonth) {
		logger.info("The show store open screen method has been called");
		GSTR1 gstr1Obj = null;
		try {
			Map<Customer, Map<String, List<GSTInvoice>>> saleTxns = this.gstrService.retrieveGSTRInvoiceData(locationId, finMonth);

			Location location=this.commonService.retrieveLocationDetails(locationId);
			
			List<GSTHSN> hsnDetails = this.gstrService.retrieveGSTRHSNData(locationId, finMonth);
			String gstNo = location.getGstNo();
			gstr1Obj = GSTROneUtil.generateGSTR1(finYear, finMonth, gstNo, hsnDetails, saleTxns);
		} catch (Exception e) {
			logger.error("There has been an error while generating GSTR1 JSON file");
		}

		return gstr1Obj;
	}

	private void updateBeanDetails(Model model, GSTRSearchBean gstrSearchBean) {

		List<Location> locations = this.commonService.retrieveAllLocations();
		if (locations != null && !locations.isEmpty()) {
			List<LocationBean> locationBeans = CommonMVCTransformer.transformLocationList(locations, Boolean.FALSE);
			model.addAttribute(MVCConstants.LOCATION_BEANS, locationBeans);
		}

		List<FinancialYear> finYears = new ArrayList<>();
		FinancialYear finYear = new FinancialYear();
		finYear.setCode("2018");
		finYear.setDesc("2018-19");
		finYear.setFinYearId(1);
		finYears.add(finYear);
		model.addAttribute(MVCConstants.GSTR_FIN_YEARS_LIST, finYears);

		
		List<FinancialMonth> finMonths = new ArrayList<>();
		YearMonth thisMonth = YearMonth.now();
	
		FinancialMonth finMonth = null;
		int count=1;
		int seqNo=1;
		int month=thisMonth.getMonthValue();
		YearMonth itrMonth=thisMonth;
		if(month<=3) {
			while(month>0) {
				finMonth = new FinancialMonth();
				finMonth.setCode(month+"");
				finMonth.setDesc(itrMonth.getMonth().toString());
				finMonth.setFinMonthId(seqNo);
				finMonths.add(finMonth);
				
				itrMonth=thisMonth.minusMonths(count);
				month=itrMonth.getMonthValue();
				count++;
				seqNo++;
			}
		}
		
		
		count=1;
		
		while(month>3) {
			finMonth = new FinancialMonth();
			finMonth.setCode(month+"");
			finMonth.setDesc(itrMonth.getMonth().toString());
			finMonth.setFinMonthId(seqNo);
			finMonths.add(finMonth);
			
			itrMonth=thisMonth.minusMonths(count);
			month=itrMonth.getMonthValue();
			count++;
			seqNo++;
		}
		
		
		model.addAttribute(MVCConstants.GSTR_FIN_MONTHS_LIST, finMonths);

		model.addAttribute(MVCConstants.GSTR_SEARCH_BEAN, gstrSearchBean);
	}

}
