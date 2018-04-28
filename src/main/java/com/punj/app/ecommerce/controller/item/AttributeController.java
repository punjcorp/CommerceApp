package com.punj.app.ecommerce.controller.item;
/**
 * 
 */

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.AttributeTransformer;
import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.AttributeDTO;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.item.AttributeBean;
import com.punj.app.ecommerce.models.item.AttributeBeanDTO;
import com.punj.app.ecommerce.services.ItemService;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class AttributeController {
	private static final Logger logger = LogManager.getLogger();
	private ItemService itemService;

	/**
	 * @param userService
	 *            the userService to set
	 */
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
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

}
