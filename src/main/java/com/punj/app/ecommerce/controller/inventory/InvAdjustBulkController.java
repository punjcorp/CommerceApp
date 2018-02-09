package com.punj.app.ecommerce.controller.inventory;
/**
 * 
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.InventoryBeanTransformer;
import com.punj.app.ecommerce.domains.inventory.StockAdjustment;
import com.punj.app.ecommerce.models.inventory.InvAdjustBean;
import com.punj.app.ecommerce.models.inventory.InvAdjustBeanDTO;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class InvAdjustBulkController {
	private static final Logger logger = LogManager.getLogger();
	private InventoryService inventoryService;
	private CommonService commonService;
	private MessageSource messageSource;

	/**
	 * @param inventoryService
	 *            the inventoryService to set
	 */
	@Autowired
	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
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
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@PostMapping(value = ViewPathConstants.BULK_INV_ADJUST_URL, params = { MVCConstants.SAVE_INV_ADJS_PARAM })
	public String saveBulkInventoryAdjustments(@ModelAttribute @Valid InvAdjustBeanDTO invAdjustBeanDTO, Locale locale, Authentication authentication,
			HttpServletRequest request, RedirectAttributes redirectAttrs) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				String username = userDetails.getUsername();
				List<InvAdjustBean> invAdjustBeans = invAdjustBeanDTO.getInvAdjusts();

				List<String> invAdjustIds = invAdjustBeanDTO.getInvAdjustIds();
				Map<BigInteger, Integer> idIndex = InventoryBeanTransformer.transformSelectedIds(invAdjustIds);

				List<InvAdjustBean> finalInvAdjustBeans = new ArrayList<>(idIndex.size());

				Set<BigInteger> ids = idIndex.keySet();
				Integer index = null;
				InvAdjustBean invAdjustBean = null;
				for (BigInteger id : ids) {
					index = idIndex.get(id);
					invAdjustBean = invAdjustBeans.get(index);
					invAdjustBean.setInvAdjustId(id);
					finalInvAdjustBeans.add(invAdjustBean);
				}
				logger.info("The selected list of inventory adjustments has been filtered for processing");

				List<StockAdjustment> stockAdjustmentList = InventoryBeanTransformer.transformInvAdjustmentList(finalInvAdjustBeans, username);

				stockAdjustmentList = this.inventoryService.saveStockAdjustmentsDesc(stockAdjustmentList);

				if (stockAdjustmentList != null && !stockAdjustmentList.isEmpty()) {
					redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
							messageSource.getMessage("commerce.screen.inventory.bulk.save.success", null, locale));
					logger.info("The bulk update operation for selected inventory adjustments is completed.");
				} else {
					redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.inventory.bulk.save.failure", null, locale));
					logger.info("The bulk update operation for selected inventory adjustments has failed.");
				}
				redirectAttrs.addFlashAttribute(MVCConstants.INV_ADJUSTS_BEAN, invAdjustBeanDTO);
				redirectAttrs.addFlashAttribute(MVCConstants.SEARCH_BEAN, invAdjustBeanDTO.getSearchBean());
				redirectAttrs.addFlashAttribute(MVCConstants.PAGE_PARAM, invAdjustBeanDTO.getSearchBean().getPage());

				request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
			}
		} catch (Exception e) {
			logger.error("There is an error while updating selected inventory adjustments", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.SEARCH_INV_ADJUST_URL;
	}

	@PostMapping(value = ViewPathConstants.BULK_INV_ADJUST_URL, params = { MVCConstants.DELETE_INV_ADJS_PARAM })
	public String deleteBulkInventoryAdjustments(@ModelAttribute InvAdjustBeanDTO invAdjustBeanDTO, Locale locale, Authentication authentication,
			HttpServletRequest request, RedirectAttributes redirectAttrs) {
		try {
			List<InvAdjustBean> invAdjustBeans = invAdjustBeanDTO.getInvAdjusts();

			List<BigInteger> finalInvAdjustIds = new ArrayList<>();

			List<String> invAdjustIds = invAdjustBeanDTO.getInvAdjustIds();
			Map<BigInteger, Integer> idIndex = InventoryBeanTransformer.transformSelectedIds(invAdjustIds);
			Set<BigInteger> ids = idIndex.keySet();

			Integer index = null;
			InvAdjustBean invAdjustBean = null;
			for (BigInteger id : ids) {
				index = idIndex.get(id);
				invAdjustBean = invAdjustBeans.get(index);
				if (invAdjustBean.getStatus().equals(MVCConstants.STATUS_CREATED)) {
					finalInvAdjustIds.add(id);
				}
			}
			logger.info("The selected list of inventory adjustments has been filtered for processing");

			this.inventoryService.deleteStockAdjustments(finalInvAdjustIds);

			redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.inventory.bulk.delete.success", null, locale));
			logger.info("The bulk delete operation for selected inventory adjustments is completed.");
			redirectAttrs.addFlashAttribute(MVCConstants.INV_ADJUSTS_BEAN, invAdjustBeanDTO);
			redirectAttrs.addFlashAttribute(MVCConstants.SEARCH_BEAN, invAdjustBeanDTO.getSearchBean());
			redirectAttrs.addFlashAttribute(MVCConstants.PAGE_PARAM, invAdjustBeanDTO.getSearchBean().getPage());

			request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
			logger.info("The selected inventory adjustments has been deleted successfully.");

		} catch (Exception e) {
			logger.error("There is an error while approving selected inventory adjustments", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.SEARCH_INV_ADJUST_URL;

	}

	@PostMapping(value = ViewPathConstants.BULK_INV_ADJUST_URL, params = { MVCConstants.APPROVE_INV_ADJS_PARAM })
	public String approveBulkInventoryAdjustments(@ModelAttribute @Valid InvAdjustBeanDTO invAdjustBeanDTO, Locale locale, Authentication authentication,
			HttpServletRequest request, RedirectAttributes redirectAttrs) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				String username = userDetails.getUsername();
				List<InvAdjustBean> invAdjustBeans = invAdjustBeanDTO.getInvAdjusts();

				List<String> invAdjustIds = invAdjustBeanDTO.getInvAdjustIds();
				Map<BigInteger, Integer> idIndex = InventoryBeanTransformer.transformSelectedIds(invAdjustIds);

				List<InvAdjustBean> finalInvAdjustBeans = new ArrayList<>(idIndex.size());

				Set<BigInteger> ids = idIndex.keySet();
				Integer index = null;
				InvAdjustBean invAdjustBean = null;
				for (BigInteger id : ids) {
					index = idIndex.get(id);
					invAdjustBean = invAdjustBeans.get(index);
					invAdjustBean.setInvAdjustId(id);
					finalInvAdjustBeans.add(invAdjustBean);
				}
				logger.info("The selected list of inventory adjustments has been filtered for processing");

				List<StockAdjustment> stockAdjustmentList = InventoryBeanTransformer.transformInvAdjustmentList(finalInvAdjustBeans, username);

				stockAdjustmentList = this.inventoryService.approveStockAdjustments(stockAdjustmentList);

				if (stockAdjustmentList != null && !stockAdjustmentList.isEmpty()) {
					redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
							messageSource.getMessage("commerce.screen.inventory.bulk.approve.success", null, locale));
					logger.info("The bulk approve operation for selected inventory adjustments is completed.");
				} else {
					redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
							messageSource.getMessage("commerce.screen.inventory.bulk.approve.failure", null, locale));
					logger.info("The bulk approve operation for selected inventory adjustments has failed.");
				}
				redirectAttrs.addFlashAttribute(MVCConstants.INV_ADJUSTS_BEAN, invAdjustBeanDTO);
				redirectAttrs.addFlashAttribute(MVCConstants.SEARCH_BEAN, invAdjustBeanDTO.getSearchBean());
				redirectAttrs.addFlashAttribute(MVCConstants.PAGE_PARAM, invAdjustBeanDTO.getSearchBean().getPage());

				request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
			}
		} catch (Exception e) {
			logger.error("There is an error while updating selected inventory adjustments", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.SEARCH_INV_ADJUST_URL;
	}

}
