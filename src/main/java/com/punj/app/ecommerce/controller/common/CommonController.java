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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.domains.common.ReasonCode;
import com.punj.app.ecommerce.models.common.AJAXResponseBean;
import com.punj.app.ecommerce.models.common.ConfigBean;
import com.punj.app.ecommerce.models.nosale.ExpenseBean;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ConfigService;
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
	private ConfigService configService;
	private CommerceContext commerceContext;
	private ResourceBundleMessageSource messageSource;

	/**
	 * @param configService the configService to set
	 */
	@Autowired
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	@Autowired
	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @param commonService the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param commerceContext the commerceContext to set
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

		if (reasonDTO != null) {
			reasonCodes = reasonDTO.getReasonCodes();
			logger.info("The expense reason codes has been retrieved successfully");
		}

		return reasonCodes;
	}

	@PostMapping(value = ViewPathConstants.DISABLE_AUT0_TOUR_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AJAXResponseBean disableAutoTour(@RequestBody ConfigBean configBean, BindingResult bindingResult,
			Model model) {
		AJAXResponseBean ajaxResponse = new AJAXResponseBean();
		try {
			Boolean result = Boolean.FALSE;

			if (configBean != null)
				result = this.configService.updateAppConfigByKey(configBean.getConfigKey(),
						configBean.getConfigValue());

			if (result) {
				logger.info("the help tour has been disabled successfully");
				ajaxResponse.setStatus(MVCConstants.AJAX_STATUS_SUCCESS);
			} else {
				logger.info("the help tour updation has failed");
				ajaxResponse.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
			}

		} catch (Exception e) {
			logger.error("There is an error while updating help tour configuration", e);
			ajaxResponse.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
			return null;
		}
		return ajaxResponse;
	}

}
