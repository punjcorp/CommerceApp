package com.punj.app.ecommerce.controller.order;
/**
 * 
 */

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
import com.punj.app.ecommerce.controller.common.transformer.OrderTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderDTO;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.domains.order.ids.OrderItemId;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.OrderBeansDTO;
import com.punj.app.ecommerce.models.order.OrderItemBean;
import com.punj.app.ecommerce.services.OrderService;
import com.punj.app.ecommerce.utils.Pager;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageOrderController {
	private static final Logger logger = LogManager.getLogger();
	private OrderService orderService;
	private MessageSource messageSource;

	/**
	 * @param orderService
	 *            the orderService to set
	 */
	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(value = ViewPathConstants.MANAGE_ORDER_URL)
	public String showManageOrder(Model model) {
		logger.info("The manage supplier method for supplier management has been called.");
		model.addAttribute("searchBean", new SearchBean());
		return ViewPathConstants.MANAGE_ORDER_PAGE;
	}

	@PostMapping(ViewPathConstants.MANAGE_ORDER_URL)
	public String searchOrder(@ModelAttribute @Valid SearchBean searchBean, BindingResult bindingResult,
			@RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, Model model, HttpSession session, Locale locale) {
		if (bindingResult.hasErrors() && !model.containsAttribute(MVCConstants.SUCCESS))
			return ViewPathConstants.MANAGE_ORDER_PAGE;
		try {
			if (!model.containsAttribute(MVCConstants.SUCCESS)) {
			Pager pager = new Pager();
			if (!page.isPresent()) {
				pager.setCurrentPageNo(1);
			} else {
				pager.setCurrentPageNo(page.get());
			}

			OrderDTO ordersDTO = this.orderService.searchOrder(searchBean.getSearchText(), pager);
			if (ordersDTO != null) {
				OrderBeansDTO orderBeanDTO = OrderTransformer.transformOrderDTO(ordersDTO);
				this.updateModelBeans(model, orderBeanDTO, searchBean);
			}

			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.search.result", new Object[] { pager.getResultSize() }, locale));
			logger.info("The purchase order details has been retrieved successfully.");
			}else {
				model.addAttribute("org.springframework.validation.BindingResult.searchBean", null);
			}
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.search.result.failure", null, locale));
			logger.error("There is an error while retrieving purchase orders", e);
		}
		return ViewPathConstants.MANAGE_ORDER_PAGE;
	}

	private void updateModelBeans(Model model, OrderBeansDTO orderBeanDTO, SearchBean searchBean) {
		model.addAttribute(MVCConstants.ORDERS_BEAN, orderBeanDTO);
		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);
		logger.info("All the needed details has been updated in the model for display.");
	}

	@PostMapping(value = ViewPathConstants.BULK_ORDER_URL, params = { MVCConstants.APPROVE_ORDERS_PARAM })
	public String approveOrders(@ModelAttribute OrderBeansDTO orders, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale,
			Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			List<BigInteger> orderIds = OrderTransformer.retrieveEligibleOrders(orders);
			if(orderIds!=null && !orderIds.isEmpty()) {
				this.orderService.approveAllOrders(orderIds, userDetails.getUsername());
				redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.order.approve.success", null, locale));
				req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
				logger.info("The bulk update operation for purchase order is completed.");
			}else {
				redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.order.approve.no.records", null, locale));
				req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
				logger.info("The bulk update operation for purchase order is completed.");				
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.supplier.delete.failure", null, locale));
			logger.error("There is an error while updating bulk purchase order basic details", e);
		}
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ORDER_URL;
	}

	@PostMapping(value = ViewPathConstants.BULK_ORDER_URL, params = { MVCConstants.DELETE_ORDERS_PARAM })
	public String deleteOrders(@ModelAttribute OrderBeansDTO orders, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale, Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			List<BigInteger> orderIds = OrderTransformer.retrieveEligibleOrders(orders);
			if(orderIds!=null && !orderIds.isEmpty()) {
				this.orderService.deleteAllOrders(orderIds, userDetails.getUsername());
				redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.order.delete.success", null, locale));
				req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
				logger.info("The bulk delete operation for purchase order is completed.");
			}else {
				redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.order.delete.no.records", null, locale));
				logger.info("The bulk delete operation for purchase order is completed.");				
			}
			
			
			
				
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.supplier.delete.failure", null, locale));
			logger.error("There is an error while deleting bulk purchase orders", e);
		}
		req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ORDER_URL;
	}

	private void getSelectedIds(List<String> orderIds, Map<BigInteger, Integer> idIndex) {
		String[] splittedData = null;
		for (String id : orderIds) {
			splittedData = id.split("_");
			idIndex.put(new BigInteger(splittedData[0]), new Integer(splittedData[1]));
		}

		logger.info("The order ids and list index data has been seperated");
	}



}
