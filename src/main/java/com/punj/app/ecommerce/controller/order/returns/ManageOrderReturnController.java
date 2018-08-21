package com.punj.app.ecommerce.controller.order.returns;
/**
 * 
 */

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.OrderReturnTransformer;
import com.punj.app.ecommerce.controller.common.transformer.OrderTransformer;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnDTO;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.order.OrderBeansDTO;
import com.punj.app.ecommerce.models.order.returns.ReturnBeanDTO;
import com.punj.app.ecommerce.services.OrderReturnService;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageOrderReturnController {
	private static final Logger logger = LogManager.getLogger();
	private OrderReturnService orderReturnService;
	private MessageSource messageSource;

	/**
	 * @param orderReturnService
	 *            the orderReturnService to set
	 */
	@Autowired
	public void setOrderReturnService(OrderReturnService orderReturnService) {
		this.orderReturnService = orderReturnService;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(value = ViewPathConstants.MANAGE_ORDER_RETURN_URL)
	public String showManageOrderReturn(Model model) {
		logger.info("The manage order return has been called.");
		model.addAttribute(MVCConstants.SEARCH_BEAN, new SearchBean());
		return ViewPathConstants.MANAGE_ORDER_RETURN_PAGE;
	}

	@PostMapping(ViewPathConstants.MANAGE_ORDER_RETURN_URL)
	public String searchOrder(@ModelAttribute @Valid SearchBean searchBean, BindingResult bindingResult, @RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, Model model,
			HttpSession session, Locale locale) {
		if (bindingResult.hasErrors() && !model.containsAttribute(MVCConstants.SUCCESS))
			return ViewPathConstants.MANAGE_ORDER_RETURN_PAGE;
		try {
			if (!model.containsAttribute(MVCConstants.SUCCESS)) {
				Pager pager = new Pager();
				if (!page.isPresent()) {
					pager.setCurrentPageNo(1);
				} else {
					pager.setCurrentPageNo(page.get());
				}

				OrderReturnDTO orderReturnsDTO = this.orderReturnService.searchOrderReturns(searchBean.getSearchText(), pager);
				if (orderReturnsDTO != null) {
					ReturnBeanDTO returnBeanDTO = OrderReturnTransformer.transformOrderReturnDTO(orderReturnsDTO);
					this.updateModelBeans(model, returnBeanDTO, searchBean);
				}

				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.order.return.search.result", new Object[] { pager.getResultSize() }, locale));
				logger.info("The purchase order return details has been retrieved successfully.");
			} else {
				model.addAttribute("org.springframework.validation.BindingResult.searchBean", null);
			}
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.return.search.result.failure", null, locale));
			logger.error("There is an error while retrieving purchase order returns", e);
		}
		return ViewPathConstants.MANAGE_ORDER_RETURN_PAGE;
	}

	private void updateModelBeans(Model model, ReturnBeanDTO returnBeanDTO, SearchBean searchBean) {
		model.addAttribute(MVCConstants.ORDER_RETURNS_BEAN, returnBeanDTO);
		
		Pager tmpPager = returnBeanDTO.getPager();
		Pager pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(), tmpPager.getMaxDisplayPage(),
				ViewPathConstants.MANAGE_ORDER_RETURN_URL);
		
		model.addAttribute(MVCConstants.PAGER, pager);
		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);
		logger.info("All the needed details has been updated in the model for display.");
	}

}
