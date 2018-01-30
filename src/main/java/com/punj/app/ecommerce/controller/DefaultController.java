package com.punj.app.ecommerce.controller;
/**
 * 
 */

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemDTO;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.item.HierarchyBean;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.item.ItemBeanDTO;
import com.punj.app.ecommerce.services.ItemService;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class DefaultController {
	private static final Logger logger = LogManager.getLogger();
	private MessageSource messageSource;
	private ItemService itemService;

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

	@GetMapping(ViewPathConstants.HOME_URL)
	public String login(@ModelAttribute SearchBean searchBean, @RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page,
			Model model, Principal principal) {
		logger.info("========================");
		logger.info("WELCOME TO THE HOME PAGE");
		logger.info("========================");

		try {

			ItemBeanDTO items = null;
			ItemDTO itemList = null;
			List<Item> itemsList = null;

			Pager pager = new Pager();
			if (!page.isPresent()) {
				pager.setCurrentPageNo(1);
			} else {
				pager.setCurrentPageNo(page.get());
			}

			if (searchBean != null && StringUtils.isNotEmpty(searchBean.getSearchText())) {
				itemList = itemService.searchItem(searchBean.getSearchText(), pager);
			} else {
				itemList = itemService.listItems(new Item(), pager);
			}
			
			itemsList = itemList.getItems();
			items = new ItemBeanDTO();
			this.setItemList(itemsList, items);
			
			Pager tmpPager = itemList.getPager();
			pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(),
					tmpPager.getMaxDisplayPage(),ViewPathConstants.HOME_URL);
			
			model.addAttribute("items", items);
			model.addAttribute("searchBean", searchBean);
			model.addAttribute(MVCConstants.PAGER, pager);

			logger.info("The item details for home page has been retrieved successfully.");

		} catch (Exception e) {
			logger.error("There is an error while retrieving items", e);
			return ViewPathConstants.ERROR_PAGE;
		}

		return ViewPathConstants.HOME_PAGE;

	}

	@GetMapping(ViewPathConstants.ACCESS_DENIED_URL)
	public String accessError(Model model) {
		logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		logger.info("!!!!!!!!!ACCESS DENIED!!!!!!!!!!");
		logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return ViewPathConstants.ACCESS_DENIED_PAGE;

	}

	@GetMapping(ViewPathConstants.LOGOUT_URL)
	public String accessError(Model model, HttpServletRequest request, HttpServletResponse response,
			Authentication auth, Locale locale) {

		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.logout.success", null, locale));
		return ViewPathConstants.LOGOUT_REDIRECT_LOGIN_PAGE;
	}

	
	/**
	 * This method is use to convert the item details from 
	 * Domain object to Bean objects
	 * @param itemsList
	 * @param items
	 */
	private void setItemList(List<Item> itemsList, ItemBeanDTO items) {
		List<ItemBean> itemBeanList = new ArrayList<>();
		ItemBean itemBean = null;
		HierarchyBean hierarchyBean = null;
		for (Item item : itemsList) {
			itemBean = new ItemBean();
			/**
			 * Setting the basic information about the style
			 */
			itemBean.setName(item.getName());
			itemBean.setLongDesc(item.getDescription());
			itemBean.setItemId(item.getItemId());
			itemBean.setParentItemId(item.getParentItemId());

			// Setting the hierarchy information
			Hierarchy hierarchy = item.getHierarchy();
			hierarchyBean = new HierarchyBean();
			hierarchyBean.setHierarchyId(hierarchy.getHierarchyId());
			itemBean.setHierarchy(hierarchyBean);
			itemBean.setItemType(item.getItemType());

			itemBeanList.add(itemBean);

		}
		items.setItems(itemBeanList);
		logger.info("The item basic details has been set in bean List successfully");
	}

}
