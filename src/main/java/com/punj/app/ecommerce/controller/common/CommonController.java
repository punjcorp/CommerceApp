/**
 * 
 */
package com.punj.app.ecommerce.controller.common;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.domains.common.ReasonCode;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.models.nosale.ExpenseBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.dtos.ReasonDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class CommonController {

	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private CommerceContext commerceContext;
	private ResourceBundleMessageSource messageSource;

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

	@PostMapping(value = ViewPathConstants.GET_REASONS_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<ReasonCode> retrieveReasonCodes(@ModelAttribute ExpenseBean expenseBean, Model model) {
		Pager pager = new Pager();
		pager.setCurrentPageNo(1);
		
		ReasonDTO reasonDTO = this.commonService.retrieveReasonCodes(expenseBean.getExpenseType(), pager);

		List<ReasonCode> reasonCodes = null;
		
		if(reasonDTO!=null) {
			reasonCodes = reasonDTO.getReasonCodes();
			logger.info("The expense reason codes has been retrieved successfully");
		}

		return reasonCodes;
	}

}
