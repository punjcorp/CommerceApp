package com.punj.app.ecommerce.controller.sale;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.dailydeeds.DailyDeedBean;
import com.punj.app.ecommerce.models.sale.SaleHeaderBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class SaleItemSearchController {
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

	@GetMapping(ViewPathConstants.POS_URL)
	public String showSaleScreen(Model model, final HttpSession session, final HttpServletRequest req) {
		try {
			this.updateBeans(model, session, req);
			logger.info("The sale screen is ready for display now");
		} catch (Exception e) {
			logger.error("There is an error while showing the new sale screen", e);
			return ViewPathConstants.AUTO_ITEM_PAGE;
		}
		return ViewPathConstants.AUTO_ITEM_PAGE;
	}

	/**
	 * This method is to set all the bean objects in model needed for the sale screen functionalities
	 * 
	 */
	private void updateBeans(Model model, final HttpSession session, final HttpServletRequest req) {
		SearchBean searchBean = new SearchBean();
		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);

		String registerIdValue=req.getParameter(MVCConstants.REGISTER_ID_PARAM);
		Integer registerId = null;
		String registerName = null;
	
		if(StringUtils.isEmpty(registerIdValue)) {
			registerId=(Integer)session.getAttribute(MVCConstants.REGISTER_ID_PARAM);
			registerName=(String)session.getAttribute(MVCConstants.REG_NAME_PARAM);
		}
		else {
			registerId =new Integer(registerIdValue);
			registerName=req.getParameter(MVCConstants.REG_NAME_PARAM);
			session.setAttribute(MVCConstants.REGISTER_ID_PARAM, registerId);
			session.setAttribute(MVCConstants.REG_NAME_PARAM, registerName);
		}

		Integer openLocId=null;
		SaleHeaderBean saleHeaderBean = new SaleHeaderBean();
		Object openLocationId = commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
		if (openLocationId != null) {
			openLocId=(Integer)openLocationId;
			saleHeaderBean.setLocationId(openLocId);
		}

		Object openLocationName = commerceContext.getStoreSettings(openLocId+"-"+CommerceConstants.OPEN_LOC_NAME);
		Object openBusinessDate = commerceContext.getStoreSettings(openLocId+"-"+CommerceConstants.OPEN_BUSINESS_DATE);
		Object defaultTender = commerceContext.getStoreSettings(openLocId+"-"+CommerceConstants.LOC_DEFAULT_TENDER);
		if (openLocationName != null)
			saleHeaderBean.setLocationName((String) openLocationName);
		if (openBusinessDate != null)
			saleHeaderBean.setBusinessDate((LocalDateTime) openBusinessDate);
		if(defaultTender!=null) {
			saleHeaderBean.setDefaultTender((String)defaultTender);
		}
		
		List<TenderBean> tenderBeans = this.retrieveValidTenders((Integer) openLocationId);
		model.addAttribute(MVCConstants.TENDER_BEANS, tenderBeans);

		saleHeaderBean.setRegisterId(registerId);
		saleHeaderBean.setRegisterName(registerName);
		model.addAttribute(MVCConstants.SALE_HEADER_BEAN, saleHeaderBean);
		logger.info("All the needed beans for sales screen has been set in the model");
	}

	private List<TenderBean> retrieveValidTenders(Integer locationId) {
		List<Tender> tenders = this.commonService.retrieveAllTenders(locationId);
		List<TenderBean> tenderBeans = CommonMVCTransformer.tranformTenders(tenders);
		return tenderBeans;
	}

}
