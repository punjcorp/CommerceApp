package com.punj.app.ecommerce.controller.returns;
/**
 * 
 */

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.sale.SaleHeaderBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ReturnTransactionController {
	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
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

	@GetMapping(ViewPathConstants.RETURN_TXN_URL)
	public String showSaleScreen(Model model, final HttpSession session, final HttpServletRequest req, RedirectAttributes redirectAttrs) {
		try {
			String forwardURL = this.updateBeans(model, session, req,redirectAttrs);
			if (StringUtils.isNotEmpty(forwardURL))
				return forwardURL;
			logger.info("The sale screen is ready for display now");
		} catch (Exception e) {
			logger.error("There is an error while showing the new sale screen", e);
			return ViewPathConstants.RETURN_TXN_PAGE;
		}
		return ViewPathConstants.RETURN_TXN_PAGE;
	}

	/**
	 * This method is to set all the bean objects in model needed for the return screen functionalities
	 * 
	 */
	private String updateBeans(Model model, final HttpSession session, final HttpServletRequest req, RedirectAttributes redirectAttrs) {
		SearchBean searchBean = new SearchBean();
		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);

		Integer openLocId = null;
		SaleHeaderBean txnHeaderBean = new SaleHeaderBean();
		Object openLocationId = commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
		if (openLocationId != null) {
			openLocId = (Integer) openLocationId;
			txnHeaderBean.setLocationId(openLocId);

			String registerIdValue = req.getParameter(MVCConstants.REGISTER_ID_PARAM);
			Integer registerId = null;
			String registerName = null;

			if (StringUtils.isEmpty(registerIdValue)) {
				registerId = (Integer) session.getAttribute(openLocId+MVCConstants.REGISTER_ID_PARAM);
				registerName = (String) session.getAttribute(openLocId+MVCConstants.REG_NAME_PARAM);
			} else {
				registerId = new Integer(registerIdValue);
				registerName = req.getParameter(MVCConstants.REG_NAME_PARAM);
				session.setAttribute(openLocId+MVCConstants.REGISTER_ID_PARAM, registerId);
				session.setAttribute(openLocId+MVCConstants.REG_NAME_PARAM, registerName);
			}

			if (registerId != null) {
				Object openLocationName = commerceContext.getStoreSettings(openLocId + "-" + CommerceConstants.OPEN_LOC_NAME);
				Object openBusinessDate = commerceContext.getStoreSettings(openLocId + "-" + CommerceConstants.OPEN_BUSINESS_DATE);
				Object defaultTender = commerceContext.getStoreSettings(openLocId + "-" + CommerceConstants.LOC_DEFAULT_TENDER);
				if (openLocationName != null)
					txnHeaderBean.setLocationName((String) openLocationName);
				if (openBusinessDate != null)
					txnHeaderBean.setBusinessDate((LocalDateTime) openBusinessDate);
				if (defaultTender != null) {
					txnHeaderBean.setDefaultTender((String) defaultTender);
				}

				List<TenderBean> tenderBeans = this.retrieveValidTenders((Integer) openLocationId);
				model.addAttribute(MVCConstants.TENDER_BEANS, tenderBeans);

				txnHeaderBean.setRegisterId(registerId);
				txnHeaderBean.setRegisterName(registerName);
				model.addAttribute(MVCConstants.TXN_HEADER_BEAN, txnHeaderBean);
				logger.info("All the needed beans for return transaction screen has been set in the model");

			} else {
				req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
				redirectAttrs.addFlashAttribute(MVCConstants.REFERRER_URL_PARAM,ViewPathConstants.RETURN_TXN_URL);
				logger.info("There is no open store existing as per this session, routing to register open screen");
				return ViewPathConstants.REDIRECT_URL + ViewPathConstants.REGISTER_OPEN_URL;
			}
		} else {
			req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
			redirectAttrs.addFlashAttribute(MVCConstants.REFERRER_URL_PARAM,ViewPathConstants.RETURN_TXN_URL);
			logger.info("There is no open store existing as per application context, routing to store open screen");
			return ViewPathConstants.REDIRECT_URL + ViewPathConstants.STORE_OPEN_URL;
		}
		return null;

	}

	private List<TenderBean> retrieveValidTenders(Integer locationId) {
		List<Tender> tenders = this.commonService.retrieveAllTenders(locationId);
		List<TenderBean> tenderBeans = CommonMVCTransformer.tranformTenders(tenders);
		return tenderBeans;
	}

}
