package com.punj.app.ecommerce.controller.inventory;
/**
 * 
 */

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.inventory.StockAdjustment;
import com.punj.app.ecommerce.domains.inventory.StockAdjustmentItem;
import com.punj.app.ecommerce.domains.inventory.StockReason;
import com.punj.app.ecommerce.domains.inventory.ids.StockAdjustmentItemId;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.inventory.InvAdjustBean;
import com.punj.app.ecommerce.models.inventory.InvAdjustItemBean;
import com.punj.app.ecommerce.models.inventory.InvReasonBean;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageInvAdjustController {
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

	@GetMapping(value = ViewPathConstants.ADD_INV_ADJUST_URL)
	public String newInventoryAdjustment(Model model, HttpSession session) {
		logger.info("The add method for new inventory adjustment has been called");
		try {
			InvAdjustBean invAdjustBean = new InvAdjustBean();

			this.updateInvAdjustmentBean(invAdjustBean, model);

			logger.info("The empty inventory adjustment object bean has been created");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while creating inventory adjustment.", e);
			return ViewPathConstants.ERROR_PAGE;
		}

		return ViewPathConstants.ADD_INV_ADJUST_PAGE;

	}

	/**
	 * This method avoids the duplicate code for inventory adjustment bean settings
	 * and retrieving locations and reason codes
	 * 
	 * @param invAdjustBean
	 * @param model
	 */
	private void updateInvAdjustmentBean(InvAdjustBean invAdjustBean, Model model) {
		List<Location> locationList = this.commonService.retrieveAllLocations();
		List<StockReason> reasonCodeList = this.inventoryService.listAllReasonCodes();
		this.updateLocations(invAdjustBean, locationList);
		this.updateReasonCodes(invAdjustBean, reasonCodeList);
		SearchBean searchBean = new SearchBean();
		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);
		model.addAttribute(MVCConstants.INV_ADJUST_BEAN, invAdjustBean);

		logger.info("All the needed beans for inventory adjustment has been updated in the model");
	}

	@PostMapping(value = ViewPathConstants.ADD_INV_ADJUST_URL, params = { MVCConstants.ADD_INV_ADJUST_ITEM_PARAM })
	public String addRow(@ModelAttribute InvAdjustBean invAdjustBean, Model model, final BindingResult bindingResult) {
		invAdjustBean.getInvAdjustItems().add(new InvAdjustItemBean());

		this.updateInvAdjustmentBean(invAdjustBean, model);

		return ViewPathConstants.ADD_INV_ADJUST_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_INV_ADJUST_URL, params = { MVCConstants.REMOVE_INV_ADJUST_ITEM_PARAM })
	public String removeRow(@ModelAttribute InvAdjustBean invAdjustBean, Model model, final BindingResult bindingResult,
			final HttpServletRequest req) {
		final Integer rowId = Integer.parseInt(req.getParameter(MVCConstants.ID_PARAM));
		invAdjustBean.getInvAdjustItems().remove(rowId.intValue());

		this.updateInvAdjustmentBean(invAdjustBean, model);

		return ViewPathConstants.ADD_INV_ADJUST_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_INV_ADJUST_URL, params = { MVCConstants.SAVE_INV_ADJUST_PARAM })
	public String saveInventoryAdjustment(@ModelAttribute @Valid InvAdjustBean invAdjustBean,
			BindingResult bindingResult, Model model, Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors())
			return ViewPathConstants.ADD_INV_ADJUST_PAGE;
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			this.updateBeanForNewAdjustment(invAdjustBean, userDetails);

			StockAdjustment stockAdjustment = new StockAdjustment();
			this.updateDomainWithItems(invAdjustBean, stockAdjustment);

			BigInteger invAdjustId = this.inventoryService.createStockAdjustment(stockAdjustment);
			invAdjustBean.setInvAdjustId(invAdjustId);
			model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.inventory.add.success",
					new Object[] { invAdjustId }, locale));

			this.updateInvAdjustmentBean(invAdjustBean, model);

			logger.info("The inventory adjustment has been created successfully.");
		} catch (Exception e) {
			logger.error("There is an error while creating inventory adjustment", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.ADD_INV_ADJUST_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_INV_ADJUST_URL, params = { MVCConstants.APPROVE_INV_ADJUST_PARAM })
	public String saveAndApproveInventoryAdjustment(@ModelAttribute @Valid InvAdjustBean invAdjustBean,
			BindingResult bindingResult, Model model, Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors())
			return ViewPathConstants.ADD_INV_ADJUST_PAGE;
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			this.updateBeanForApprovedAdjustment(invAdjustBean, userDetails);

			StockAdjustment stockAdjustment = new StockAdjustment();
			this.updateDomainWithItems(invAdjustBean, stockAdjustment);

			BigInteger invAdjustId = this.inventoryService.approveStockAdjustment(stockAdjustment);
			invAdjustBean.setInvAdjustId(invAdjustId);
			model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.inventory.approve.success",
					new Object[] { invAdjustId }, locale));

			this.updateInvAdjustmentBean(invAdjustBean, model);

			logger.info("The inventory adjustment has been created successfully.");
		} catch (Exception e) {
			logger.error("There is an error while creating inventory adjustment", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.ADD_INV_ADJUST_PAGE;
	}
	
	
	
	/**
	 * This method is to set values for a New Inventory Adjustment
	 * 
	 * @param invAdjustBean
	 */
	private void updateBeanForNewAdjustment(InvAdjustBean invAdjustBean, UserDetails userDetails) {
		invAdjustBean.setStatus(MVCConstants.STATUS_CREATED);
		invAdjustBean.setCreatedDate(LocalDateTime.now());
		invAdjustBean.setCreatedBy(userDetails.getUsername());
		logger.info("The inventory adjustment bean has been updated with new inventory adjustment values");
	}

	/**
	 * This method is to set values for a New Inventory Adjustment
	 * 
	 * @param invAdjustBean
	 */
	private void updateBeanForApprovedAdjustment(InvAdjustBean invAdjustBean, UserDetails userDetails) {
		invAdjustBean.setStatus(MVCConstants.STATUS_APPROVED);
		//Later on we need to include if this is already existing inv adjustment or not
		invAdjustBean.setCreatedDate(LocalDateTime.now());
		invAdjustBean.setCreatedBy(userDetails.getUsername());
		//This part will be used in case above condition comes as true
		invAdjustBean.setModifiedDate(LocalDateTime.now());
		invAdjustBean.setModifiedBy(userDetails.getUsername());		
		logger.info("The inventory adjustment bean has been updated to approve inventory adjustment values");
	}
	
	
	/**
	 * This method is used to set the Domain objects so that the data can be
	 * persisted to the database
	 * 
	 * @param invAdjustBean
	 * @param stockAdjustment
	 */
	private void updateDomain(InvAdjustBean invAdjustBean, StockAdjustment stockAdjustment) {

		stockAdjustment.setStockAdjustId(invAdjustBean.getInvAdjustId());
		stockAdjustment.setDescription(invAdjustBean.getDescription());

		StockReason stockReason = new StockReason();
		stockReason.setReasonCodeId(invAdjustBean.getReasonCodeId());
		stockAdjustment.setStockReason(stockReason);
		
		stockAdjustment.setLocationId(invAdjustBean.getLocationId());		
		stockAdjustment.setStatus(invAdjustBean.getStatus());

		stockAdjustment.setCreatedBy(invAdjustBean.getCreatedBy());
		stockAdjustment.setCreatedDate(invAdjustBean.getCreatedDate());

		stockAdjustment.setModifiedBy(invAdjustBean.getModifiedBy());
		stockAdjustment.setModifiedDate(invAdjustBean.getModifiedDate());

		logger.info("The stock adjustment details has been updated in domain object");

	}

	/**
	 * This method is used to set the Domain objects so that the data can be
	 * persisted to the database
	 * 
	 * @param invAdjustBean
	 * @param stockAdjustment
	 */
	private void updateDomainWithItems(InvAdjustBean invAdjustBean, StockAdjustment stockAdjustment) {

		this.updateDomain(invAdjustBean, stockAdjustment);
		logger.debug("The header level stock adjustment details has been updated in domain");

		List<InvAdjustItemBean> invAdjustItems = invAdjustBean.getInvAdjustItems();
		List<StockAdjustmentItem> stockAdjustmentItems=new ArrayList<>(invAdjustItems.size());
		StockAdjustmentItem stockAdjItem = null;
		StockAdjustmentItemId stockAdjItemId = null;
		for (InvAdjustItemBean invAdjustItem : invAdjustItems) {
			stockAdjItem = new StockAdjustmentItem();

			stockAdjItemId = new StockAdjustmentItemId();
			stockAdjItemId.setItemId(invAdjustItem.getItemId());
			stockAdjItemId.setReasonCodeId(invAdjustItem.getReasonCodeId());
			stockAdjItemId.setStockAdjustment(stockAdjustment);
			stockAdjItem.setStockAdjustmentItemId(stockAdjItemId);

			stockAdjItem.setQty(invAdjustItem.getQty());
			stockAdjustmentItems.add(stockAdjItem);
		}
		stockAdjustment.setStockAdjustItems(stockAdjustmentItems);

		logger.info("The stock adjustment details wtih items has been updated in domain object");
	}

	/**
	 * This method is used to update the Location details in bean object 
	 * 
	 * @param invAdjustBean
	 * @param locationList
	 */
	private void updateLocations(InvAdjustBean invAdjustBean, List<Location> locationList) {
		LocationBean locationBean = null;
		List<LocationBean> locations = null;
		if (locationList != null && !locationList.isEmpty()) {
			locations = new ArrayList<>(locationList.size());
			for (Location location : locationList) {
				locationBean = new LocationBean();
				locationBean.setLocationId(location.getLocationId());
				locationBean.setLocationType(location.getStatus());
				locationBean.setName(location.getName());
				locations.add(locationBean);
			}
			invAdjustBean.setLocations(locations);
		}
		logger.info("The location details has been set in Inventory Adjustment object");
	}

	/**
	 * This method is used to update the reason code details in bean object
	 * 
	 * @param invAdjustBean
	 * @param reasonCodeList
	 */
	private void updateReasonCodes(InvAdjustBean invAdjustBean, List<StockReason> reasonCodeList) {
		InvReasonBean reasonCodeBean = null;
		List<InvReasonBean> reasonCodes = null;
		if (reasonCodeList != null && !reasonCodeList.isEmpty()) {
			reasonCodes = new ArrayList<>(reasonCodeList.size());
			for (StockReason stockReason : reasonCodeList) {
				reasonCodeBean = new InvReasonBean();
				reasonCodeBean.setReasonCodeId(stockReason.getReasonCodeId());
				reasonCodeBean.setReasonCode(stockReason.getReasonCode());
				reasonCodeBean.setName(stockReason.getName());
				if (stockReason.getFromBucket() != null)
					reasonCodeBean.setFromBucketId(stockReason.getFromBucket().getBucketId());
				if (stockReason.getToBucket() != null)
					reasonCodeBean.setToBucketId(stockReason.getToBucket().getBucketId());
				reasonCodes.add(reasonCodeBean);
			}
			invAdjustBean.setReasonCodes(reasonCodes);
		}
		logger.info("The Reason Code details has been set in Inventory Adjustment object");
	}

}
