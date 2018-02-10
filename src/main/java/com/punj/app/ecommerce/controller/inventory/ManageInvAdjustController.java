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
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.InventoryBeanTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.inventory.ItemStock;
import com.punj.app.ecommerce.domains.inventory.StockAdjustment;
import com.punj.app.ecommerce.domains.inventory.StockAdjustmentDTO;
import com.punj.app.ecommerce.domains.inventory.StockReason;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.inventory.InvAdjustBean;
import com.punj.app.ecommerce.models.inventory.InvAdjustItemBean;
import com.punj.app.ecommerce.models.inventory.InvAdjustItemInventory;
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
	 * This method avoids the duplicate code for inventory adjustment bean settings and retrieving locations and reason codes
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
	public String removeRow(@ModelAttribute InvAdjustBean invAdjustBean, Model model, final BindingResult bindingResult, final HttpServletRequest req,
			Locale locale) {
		final Integer rowId = Integer.parseInt(req.getParameter(MVCConstants.ID_PARAM));
		List<InvAdjustItemBean> invAdjustItems = invAdjustBean.getInvAdjustItems();

		InvAdjustItemBean invAdjustItemBean = invAdjustItems.get(rowId.intValue());
		BigInteger lineItemId = invAdjustItemBean.getInvAdjustLineItemId();
		Boolean result;
		if (lineItemId != null) {
			result = this.inventoryService.deleteStockAdjustmentItem(lineItemId);
			if (result) {
				invAdjustBean.getInvAdjustItems().remove(rowId.intValue());
				logger.info("The inventory adjustment item with line item id {} was deleted successfully", lineItemId);
			} else {
				model.addAttribute(MVCConstants.ALERT,
						messageSource.getMessage("commerce.screen.inventory.item.delete.failure", new Object[] { invAdjustItemBean.getItemId() }, locale));
				logger.info("The inventory adjustment item was not deleted due to some issue");
			}
		} else {
			invAdjustBean.getInvAdjustItems().remove(rowId.intValue());
			logger.info("The inventory adjustment item was deleted successfully");
		}
		this.updateInvAdjustmentBean(invAdjustBean, model);

		return ViewPathConstants.ADD_INV_ADJUST_PAGE;
	}

	@RequestMapping(value = ViewPathConstants.GET_ITEM_INV_URL, method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InvAdjustItemInventory getItemInventory(final HttpServletRequest req) {
		final BigInteger itemId = new BigInteger(req.getParameter(MVCConstants.ITEM_ID_PARAM));
		final Integer locationId = Integer.parseInt(req.getParameter(MVCConstants.LOCATION_ID_PARAM));
		final Integer reasonCodeId = Integer.parseInt(req.getParameter(MVCConstants.REASON_CODE_ID_PARAM));
		InvAdjustItemInventory invAdjustInventory = null;
		StockReason stockReason = null;

		ItemStock itemStock = this.inventoryService.searchItemStock(itemId, locationId);
		if (itemStock != null) {
			stockReason = this.inventoryService.searchReasonCode(reasonCodeId);
			if (stockReason != null) {
				invAdjustInventory = InventoryBeanTransformer.transformItemStockWithReason(itemStock, stockReason);
				logger.info("The inventory details has been transformed successfully.");
			}
		}

		logger.info("The inventory details has been retrieved successfully.");
		return invAdjustInventory;
	}

	@PostMapping(value = ViewPathConstants.ADD_INV_ADJUST_URL, params = { MVCConstants.SAVE_INV_ADJUST_PARAM })
	public String saveInventoryAdjustment(@ModelAttribute @Valid InvAdjustBean invAdjustBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			updateInvAdjustmentBean(invAdjustBean, model);
			return ViewPathConstants.ADD_INV_ADJUST_PAGE;
		}
		try {
			this.saveInvAdjustment(invAdjustBean, model, locale, authentication, MVCConstants.SAVE_INV_ADJUST_PARAM);
			logger.info("The inventory adjustment has been created successfully.");
		} catch (Exception e) {
			logger.error("There is an error while creating inventory adjustment", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.ADD_INV_ADJUST_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_INV_ADJUST_URL, params = { MVCConstants.APPROVE_INV_ADJUST_PARAM })
	public String saveAndApproveInventoryAdjustment(@ModelAttribute @Valid InvAdjustBean invAdjustBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			updateInvAdjustmentBean(invAdjustBean, model);
			return ViewPathConstants.ADD_INV_ADJUST_PAGE;
		}
		try {
			this.saveInvAdjustment(invAdjustBean, model, locale, authentication, MVCConstants.APPROVE_INV_ADJUST_PARAM);
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
	private void updateBeanForNewAdjustment(InvAdjustBean invAdjustBean, UserDetails userDetails, String action) {
		invAdjustBean.setStatus(MVCConstants.STATUS_CREATED);
		if (action.equals(MVCConstants.SAVE_INV_ADJUST_PARAM) && invAdjustBean.getInvAdjustId()==null) {
			invAdjustBean.setCreatedDate(LocalDateTime.now());
			invAdjustBean.setCreatedBy(userDetails.getUsername());
		} else if (action.equals(MVCConstants.SAVE_EDIT_INV_ADJUST_PARAM) || (action.equals(MVCConstants.SAVE_INV_ADJUST_PARAM) && invAdjustBean.getInvAdjustId()!=null)) {
			invAdjustBean.setModifiedDate(LocalDateTime.now());
			invAdjustBean.setModifiedBy(userDetails.getUsername());
		}
		logger.info("The inventory adjustment bean has been updated with new inventory adjustment values");
	}

	/**
	 * This method is to set values for a New Inventory Adjustment
	 * 
	 * @param invAdjustBean
	 */
	private void updateBeanForApprovedAdjustment(InvAdjustBean invAdjustBean, UserDetails userDetails) {
		invAdjustBean.setStatus(MVCConstants.STATUS_APPROVED);
		// Later on we need to include if this is already existing inv adjustment or not
		if (invAdjustBean.getInvAdjustId() == null) {
			invAdjustBean.setCreatedDate(LocalDateTime.now());
			invAdjustBean.setCreatedBy(userDetails.getUsername());
		} else {
			invAdjustBean.setModifiedDate(LocalDateTime.now());
			invAdjustBean.setModifiedBy(userDetails.getUsername());
		}
		logger.info("The inventory adjustment bean has been updated to approve inventory adjustment values");
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

	@GetMapping(ViewPathConstants.APPROVE_INV_ADJUST_URL)
	public String approveInvAdjust(@ModelAttribute SearchBean searchBean, Model model, final HttpServletRequest req, Locale locale) {
		try {
			this.executeOrderAction(req, MVCConstants.APPROVE_ORDERS_PARAM, model, searchBean, locale);
		} catch (Exception e) {
			logger.error("There is an error while approving inventory adjustment", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.MANAGE_INV_ADJUST_PAGE;
	}

	private void executeOrderAction(final HttpServletRequest req, String action, Model model, SearchBean searchBean, Locale locale) {

		BigInteger invAdjustId = new BigInteger(req.getParameter(MVCConstants.INV_ADJUST_ID_PARAM));
		Boolean result = Boolean.FALSE;
		if (action.equals(MVCConstants.APPROVE_ORDERS_PARAM)) {
			result = this.inventoryService.approveStockAdjustment(invAdjustId);
		} else if (action.equals(MVCConstants.DELETE_ORDERS_PARAM)) {
			result = this.inventoryService.deleteStockAdjustment(invAdjustId);
		}
		if (result) {
			model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);
			if (action.equals(MVCConstants.APPROVE_ORDERS_PARAM)) {
				model.addAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.inventory.approve.success", new Object[] { invAdjustId }, locale));
				logger.info("The selected inventory adjustment has been approved successfully");
			} else if (action.equals(MVCConstants.DELETE_ORDERS_PARAM)) {
				model.addAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.inventory.delete.success", new Object[] { invAdjustId }, locale));
				logger.info("The selected inventory adjustment has been deleted successfully");
			}
		} else {
			if (action.equals(MVCConstants.APPROVE_ORDERS_PARAM)) {
				model.addAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.inventory.approve.failure", new Object[] { invAdjustId }, locale));
				logger.info("The selected inventory adjustment has been approved successfully");
			} else if (action.equals(MVCConstants.DELETE_ORDERS_PARAM)) {
				model.addAttribute(MVCConstants.ALERT,
						messageSource.getMessage("commerce.screen.inventory.delete.failure", new Object[] { invAdjustId }, locale));
				logger.info("The provided inventory adjustment cannot be deleted.");
			}
		}

	}

	@GetMapping(ViewPathConstants.DELETE_INV_ADJUST_URL)
	public String deleteOrder(@ModelAttribute SearchBean searchBean, Model model, final HttpServletRequest req, Locale locale) {
		try {
			this.executeOrderAction(req, MVCConstants.DELETE_ORDERS_PARAM, model, searchBean, locale);
		} catch (Exception e) {
			logger.error("There is an error while deleting inventory adjustment", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.MANAGE_INV_ADJUST_PAGE;
	}

	@GetMapping(value = ViewPathConstants.EDIT_INV_ADJUST_URL)
	public String newInventoryAdjustment(Model model, final HttpServletRequest req, Locale locale) {
		logger.info("The add method for new inventory adjustment has been called");
		try {
			BigInteger invAdjustId = new BigInteger(req.getParameter(MVCConstants.INV_ADJUST_ID_PARAM));
			StockAdjustmentDTO stockAdjustmentDTO = this.inventoryService.searchStockAdjustmentWithInventory(invAdjustId);
			InvAdjustBean invAdjustBean = null;
			if (stockAdjustmentDTO != null) {
				invAdjustBean = InventoryBeanTransformer.transformStockAdjustmentDTO(stockAdjustmentDTO);
			}

			this.updateInvAdjustmentBean(invAdjustBean, model);

			logger.info("The provided inventory adjustment has been retrieved from database");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while retrieving inventory adjustment for editing.", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.EDIT_INV_ADJUST_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_INV_ADJUST_URL, params = { MVCConstants.ADD_INV_ADJUST_ITEM_PARAM })
	public String addRowDuringEdit(@ModelAttribute InvAdjustBean invAdjustBean, Model model) {
		invAdjustBean.getInvAdjustItems().add(new InvAdjustItemBean());

		this.updateInvAdjustmentBean(invAdjustBean, model);

		logger.info("A new item was added for inventory adjustment successfully");
		return ViewPathConstants.EDIT_INV_ADJUST_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_INV_ADJUST_URL, params = { MVCConstants.REMOVE_INV_ADJUST_ITEM_PARAM })
	public String removeRowDuringEdit(@ModelAttribute InvAdjustBean invAdjustBean, Model model, final HttpServletRequest req, Locale locale) {
		final Integer rowId = Integer.parseInt(req.getParameter(MVCConstants.ID_PARAM));
		InvAdjustItemBean invAdjustItem = invAdjustBean.getInvAdjustItems().get(rowId.intValue());
		Boolean result;
		if (invAdjustItem.getInvAdjustLineItemId() != null) {
			result = this.inventoryService.deleteStockAdjustmentItem(invAdjustItem.getInvAdjustLineItemId());
			invAdjustBean.getInvAdjustItems().remove(rowId.intValue());
			if (result) {
				logger.info("The inventory adjustment item was deleted successfully");
			} else {
				model.addAttribute(MVCConstants.ALERT,
						messageSource.getMessage("commerce.screen.inventory.item.delete.failure", new Object[] { invAdjustItem.getInvAdjustId() }, locale));
				logger.info("The inventory adjustment item was not deleted due to some issue");
			}
		} else {
			invAdjustBean.getInvAdjustItems().remove(rowId.intValue());
			logger.info("The inventory adjustment item was deleted successfully");
		}

		this.updateInvAdjustmentBean(invAdjustBean, model);

		return ViewPathConstants.EDIT_INV_ADJUST_PAGE;
	}

	private void saveInvAdjustment(InvAdjustBean invAdjustBean, Model model, Locale locale, Authentication authentication, String action) {

		Boolean newStatus=Boolean.FALSE;
		InvAdjustBean updatedInvAdjustBean;
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		if (action.equals(MVCConstants.SAVE_EDIT_INV_ADJUST_PARAM) || action.equals(MVCConstants.SAVE_INV_ADJUST_PARAM)) {
			if(invAdjustBean.getInvAdjustId()==null) {
				newStatus=Boolean.TRUE;
			}
			
			this.updateBeanForNewAdjustment(invAdjustBean, userDetails, action);
		}
		else if (action.equals(MVCConstants.APPROVE_EDIT_INV_ADJUST_PARAM) || action.equals(MVCConstants.APPROVE_INV_ADJUST_PARAM)) {
			this.updateBeanForApprovedAdjustment(invAdjustBean, userDetails);			
		}


		logger.info("The inventory adjustment values has been updated based on the operation type.");

		StockAdjustment stockAdjustment = InventoryBeanTransformer.transformInvAdjustBeanWithItems(invAdjustBean);

		BigInteger invAdjustId = null;
		if (action.equals(MVCConstants.SAVE_EDIT_INV_ADJUST_PARAM) || action.equals(MVCConstants.SAVE_INV_ADJUST_PARAM)) {
			stockAdjustment = this.inventoryService.createStockAdjustment(stockAdjustment);
		}
		else if (action.equals(MVCConstants.APPROVE_EDIT_INV_ADJUST_PARAM) || action.equals(MVCConstants.APPROVE_INV_ADJUST_PARAM)) {
			stockAdjustment = this.inventoryService.approveStockAdjustment(stockAdjustment);			
		}


		logger.info("The inventory adjustment values has been saved/updated based on the operation in database.");

		updatedInvAdjustBean=InventoryBeanTransformer.transformStockAdjustmentDomainWithItems(stockAdjustment);
		InventoryBeanTransformer.retainInvBucketQty(invAdjustBean, updatedInvAdjustBean);
		invAdjustId=updatedInvAdjustBean.getInvAdjustId();

		if (action.equals(MVCConstants.SAVE_INV_ADJUST_PARAM)) {
			if(newStatus) {
				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.inventory.add.success", new Object[] { invAdjustId }, locale));
			} else {
				model.addAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.inventory.edit.save.success", new Object[] { invAdjustId }, locale));
			}
		}
		else if (action.equals(MVCConstants.SAVE_EDIT_INV_ADJUST_PARAM)) {
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.inventory.edit.save.success", new Object[] { invAdjustId }, locale));			
		}
		else if (action.equals(MVCConstants.APPROVE_EDIT_INV_ADJUST_PARAM) || action.equals(MVCConstants.APPROVE_INV_ADJUST_PARAM)) {
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.inventory.approve.success", new Object[] { invAdjustId }, locale));			
		}
		logger.info("The inventory adjustment success messages are added in model objects based on the operations.");

		this.updateInvAdjustmentBean(updatedInvAdjustBean, model);

	}

	@PostMapping(value = ViewPathConstants.EDIT_INV_ADJUST_URL, params = { MVCConstants.SAVE_EDIT_INV_ADJUST_PARAM })
	public String saveInventoryAdjustmentDuringEdit(@ModelAttribute @Valid InvAdjustBean invAdjustBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			updateInvAdjustmentBean(invAdjustBean, model);
			return ViewPathConstants.EDIT_INV_ADJUST_PAGE;
		}
		try {

			this.saveInvAdjustment(invAdjustBean, model, locale, authentication, MVCConstants.SAVE_EDIT_INV_ADJUST_PARAM);
			logger.info("The inventory adjustment has been updated successfully.");
		} catch (Exception e) {
			logger.error("There is an error while updating modified inventory adjustment", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.EDIT_INV_ADJUST_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_INV_ADJUST_URL, params = { MVCConstants.APPROVE_EDIT_INV_ADJUST_PARAM })
	public String saveAndApproveInventoryAdjustmentAfterEdit(@ModelAttribute @Valid InvAdjustBean invAdjustBean, BindingResult bindingResult, Model model,
			Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			updateInvAdjustmentBean(invAdjustBean, model);
			return ViewPathConstants.EDIT_INV_ADJUST_PAGE;
		}
		try {

			this.saveInvAdjustment(invAdjustBean, model, locale, authentication, MVCConstants.APPROVE_EDIT_INV_ADJUST_PARAM);
			logger.info("The selected inventory adjustment has been approved successfully.");
		} catch (Exception e) {
			logger.error("There is an error while approving selected inventory adjustment", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.EDIT_INV_ADJUST_PAGE;
	}

}
