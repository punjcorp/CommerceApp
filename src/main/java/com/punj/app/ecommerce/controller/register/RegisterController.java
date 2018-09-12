package com.punj.app.ecommerce.controller.register;
/**
 * 
 */

import java.time.LocalDateTime;
import java.util.Locale;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.common.Register;
import com.punj.app.ecommerce.domains.common.ids.RegisterId;
import com.punj.app.ecommerce.models.common.AJAXResponseBean;
import com.punj.app.ecommerce.models.common.RegisterBean;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class RegisterController {
	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private MessageSource messageSource;
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
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @param commerceContext
	 *            the commerceContext to set
	 */
	@Autowired
	public void setCommerceContext(CommerceContext commerceContext) {
		this.commerceContext = commerceContext;
	}

	@PostMapping(value = ViewPathConstants.CREATE_REGISTER_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AJAXResponseBean saveNewRegister(@RequestBody @Valid RegisterBean registerBean, BindingResult bindingResult, Locale locale, Authentication authentication) {
		AJAXResponseBean response = new AJAXResponseBean();
		if (bindingResult.hasErrors()) {
			response.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
			response.setStatusMsg(this.messageSource.getMessage("commerce.screen.btn.register.add.validation.error", null, locale));
			return response;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Register register = new Register();

			RegisterId registerId = new RegisterId();
			registerId.setRegister(registerBean.getRegisterId());
			registerId.setLocationId(registerBean.getLocationId());
			register.setRegisterId(registerId);

			register.setName(registerBean.getName());
			register.setCreatedDate(LocalDateTime.now());
			register.setCreatedBy(userDetails.getUsername());

			register = this.commonService.saveRegister(register);
			if (register != null) {

				response.setStatus(MVCConstants.AJAX_STATUS_SUCCESS);
				response.setStatusMsg(this.messageSource.getMessage("commerce.screen.btn.register.add.success", null, locale));
				response.setResultObj(register);

			} else {
				response.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
				response.setStatusMsg(this.messageSource.getMessage("commerce.screen.btn.register.add.failure", null, locale));
			}

		} catch (Exception e) {
			logger.error("There is an error while saving register details", e);
			response.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
			response.setStatusMsg(this.messageSource.getMessage("commerce.screen.btn.register.add.failure", null, locale));
		}

		return response;
	}

}
