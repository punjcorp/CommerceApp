package com.punj.app.ecommerce.controller.item;
/**
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.AttributeTransformer;
import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.AttributeDTO;
import com.punj.app.ecommerce.models.common.AJAXResponseBean;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.item.AttributeBean;
import com.punj.app.ecommerce.models.item.AttributeBeanDTO;
import com.punj.app.ecommerce.services.ItemService;
import com.punj.app.ecommerce.utils.Pager;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class AttributeController {
	private static final Logger logger = LogManager.getLogger();
	private ItemService itemService;
	private MessageSource messageSource;

	/**
	 * @param userService
	 *            the userService to set
	 */
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@PostMapping(ViewPathConstants.SEARCH_ATTRIBUTES_URL)
	@ResponseBody
	public List<AttributeBean> lookupAttributes(@RequestBody @Valid SearchBean searchBean, BindingResult bindingResult,
			@RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, Model model, HttpSession session) {
		AttributeBeanDTO attributeBeanDTO = null;
		if (bindingResult.hasErrors())
			return null;
		try {

			Pager pager = new Pager();
			if (!page.isPresent()) {
				pager.setCurrentPageNo(1);
			} else {
				pager.setCurrentPageNo(page.get());
			}

			AttributeDTO atributeDTO = this.itemService.searchAttributes(searchBean.getSearchText(), pager);
			attributeBeanDTO = AttributeTransformer.transformAttributeDTO(atributeDTO);

			logger.info("The attributes has been queries successfully based on the requested keywords");

		} catch (Exception e) {
			logger.error("There is an error while retrieving attributes for lookup screen", e);
			return null;
		}

		return attributeBeanDTO.getAttributes();
	}

	@PostMapping(ViewPathConstants.SEARCH_ATTRIBUTE_VALUES_URL)
	@ResponseBody
	public List<AttributeBean> lookupAttributeValues(@RequestBody @Valid SearchBean searchBean, BindingResult bindingResult,
			@RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, Model model, HttpSession session) {
		List<AttributeBean> attributeBeanList = null;
		if (bindingResult.hasErrors())
			return null;
		try {

			List<Attribute> atributeList = this.itemService.retrieveAttributeValues(searchBean.getSearchText());
			attributeBeanList = AttributeTransformer.transformAttributes(atributeList);

			logger.info("The attribute values has been queried successfully based on the requested keywords for attribute code");

		} catch (Exception e) {
			logger.error("There is an error while retrieving attribute values for attribute lookup screen", e);
			return null;
		}

		return attributeBeanList;
	}

	@GetMapping(ViewPathConstants.ATTR_VAL_ACTION_URL)
	public String showAttributes(Model model) {

		Map<String, Attribute> attributeMap = this.itemService.getAllAttributes();
		Attribute attr = null;
		AttributeBean attrBean = null;
		List<AttributeBean> attributeList = new ArrayList<>(attributeMap.size());
		for (String attrCode : attributeMap.keySet()) {
			attr = attributeMap.get(attrCode);
			attrBean = AttributeTransformer.transformAttribute(attr);
			attributeList.add(attrBean);
		}

		model.addAttribute(MVCConstants.ATTR_BEANS_PARAM, attributeList);
		model.addAttribute(MVCConstants.ATTR_BEAN_PARAM, new AttributeBean());
		logger.info("The attribute value screen is ready for display");

		return ViewPathConstants.ATTR_VAL_ACTION_PAGE;
	}

	@PostMapping(ViewPathConstants.SAVE_ATTR_VAL_ORDER_URL)
	@ResponseBody
	public List<AttributeBean> saveAttributeValuesOrder(@RequestBody List<AttributeBean> attrList, Model model) {
		List<AttributeBean> attributeBeanList = null;
		try {

			List<Attribute> attrValList = AttributeTransformer.transformAttributeBeans(attrList);

			List<Attribute> atributeList = this.itemService.updateAttributeOrder(attrValList);
			attributeBeanList = AttributeTransformer.transformAttributes(atributeList);

			logger.info("The attribute values has been queried successfully based on the requested keywords for attribute code");

		} catch (Exception e) {
			logger.error("There is an error while retrieving attribute values for attribute lookup screen", e);
		}

		return attributeBeanList;
	}

	@PostMapping(ViewPathConstants.SAVE_ATTR_DETAILS_URL)
	@ResponseBody
	public AJAXResponseBean saveAttributeDetails(@RequestBody AttributeBean attrDetails, Model model, Locale locale) {
		AJAXResponseBean responseBean = new AJAXResponseBean();
		try {

			if (this.validateAttributeDetails(responseBean, attrDetails, locale)) {
				Attribute attribute = AttributeTransformer.transformAttributeBean(attrDetails);
				this.updateAttributeCodes(attribute);
				
				attribute = this.itemService.saveAttribute(attribute);
				AttributeBean attributeBean = AttributeTransformer.transformAttribute(attribute);

				responseBean.setStatus(MVCConstants.AJAX_STATUS_SUCCESS);
				responseBean.setStatusMsg(this.messageSource.getMessage("commerce.screen.attr.save.success", null, locale));
				responseBean.setResultObj(attributeBean);

				logger.info("The attribute details with value has been saved successfully");
			}

		} catch (Exception e) {
			logger.error("There is an error while saving attribute details and value", e);
		}

		return responseBean;
	}

	private void updateAttributeCodes(Attribute attribute) {
		Set<String> uniqueAttrCodes=this.itemService.findUniqueAttrCodes();
		String newAttrCode=null;
		while(newAttrCode==null) {
			newAttrCode=Utils.generateUniqueCode(uniqueAttrCodes);
		}
		attribute.setCode(newAttrCode);
		
		this.updateAttributeValueCodes(attribute);
		
		logger.info("The attribute code and value details has been updated before save");
	}
	
	private void updateAttributeValueCodes(Attribute attribute) {
		
		Set<String> uniqueAttrValCodes=this.itemService.findUniqueAttrValCodes();
		String newAttrValCode=null;
		while(newAttrValCode==null) {
			newAttrValCode=Utils.generateUniqueCode(uniqueAttrValCodes);
		}
		attribute.setValCode(newAttrValCode);
		
		if(attribute.getValSeqNo()==null)
			attribute.setValSeqNo(1);
		
		logger.info("The attribute value code and details has been updated before save");
	}	

	private Boolean validateAttributeDetails(AJAXResponseBean responseBean, AttributeBean attrDetails, Locale locale) {
		Boolean result = Boolean.TRUE;
		if (attrDetails == null) {
			responseBean.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
			responseBean.setStatusMsg(this.messageSource.getMessage("commerce.screen.attr.save.failure", null, locale));
			result = Boolean.FALSE;
		} else {
			if (StringUtils.isBlank(attrDetails.getName()) || StringUtils.isBlank(attrDetails.getValName())) {
				responseBean.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
				responseBean.setStatusMsg(this.messageSource.getMessage("commerce.screen.attr.save.failure.field", null, locale));
				result = Boolean.FALSE;
			}
		}

		logger.info("The attribute details has been validated successfully");
		return result;
	}
	
	
	@PostMapping(ViewPathConstants.SAVE_ATTR_VAL_DETAILS_URL)
	@ResponseBody
	public AJAXResponseBean saveAttributeValDetails(@RequestBody AttributeBean attrDetails, Model model, Locale locale) {
		AJAXResponseBean responseBean = new AJAXResponseBean();
		try {

			if (this.validateAttributeDetails(responseBean, attrDetails, locale)) {
				Attribute attribute = AttributeTransformer.transformAttributeBean(attrDetails);
				this.updateAttributeValueCodes(attribute);
				
				attribute = this.itemService.saveAttributeValue(attribute);
				AttributeBean attributeBean = AttributeTransformer.transformAttribute(attribute);

				responseBean.setStatus(MVCConstants.AJAX_STATUS_SUCCESS);
				responseBean.setStatusMsg(this.messageSource.getMessage("commerce.screen.attr.val.save.success", null, locale));
				responseBean.setResultObj(attributeBean);

				logger.info("The attribute values has been saved successfully");
			}

		} catch (Exception e) {
			responseBean.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
			responseBean.setStatusMsg(this.messageSource.getMessage("commerce.screen.attr.val.save.failure", null, locale));
			logger.error("There is an error while saving attribute values ", e);
		}

		return responseBean;
	}
	
	
	@PostMapping(ViewPathConstants.DELETE_ATTR_VAL_DETAILS_URL)
	@ResponseBody
	public AJAXResponseBean deleteAttributeVal(@RequestBody AttributeBean attrDetails, Model model, Locale locale) {
		AJAXResponseBean responseBean = new AJAXResponseBean();
		try {

			Attribute attr=AttributeTransformer.transformAttributeBean(attrDetails);
			Boolean result=this.itemService.isAttrValueAssignedToItem(attr);
			if(!result) {
				this.itemService.deleteAttributeValue(attr.getAttributeId());
				responseBean.setStatus(MVCConstants.AJAX_STATUS_SUCCESS);
				responseBean.setStatusMsg(this.messageSource.getMessage("commerce.screen.attr.val.delete.success", null, locale));

				logger.info("The provided attribute value has been deleted successfully");
			}else {
				responseBean.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
				responseBean.setStatusMsg(this.messageSource.getMessage("commerce.screen.attr.val.delete.not.allowed", null, locale));
			}
		} catch (Exception e) {
			responseBean.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
			responseBean.setStatusMsg(this.messageSource.getMessage("commerce.screen.attr.val.delete.failure", null, locale));
			logger.error("There is an error while deleting attribute values", e);
		}

		return responseBean;
	}
	

}
