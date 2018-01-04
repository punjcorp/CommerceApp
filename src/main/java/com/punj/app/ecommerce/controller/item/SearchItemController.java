package com.punj.app.ecommerce.controller.item;
/**
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemDTO;
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
public class SearchItemController {
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

	@GetMapping("/list_items")
	public String listItems(@RequestParam("page") Optional<Integer> page, Model model, HttpSession session) {
		try {
			Pager pager = new Pager();
			if (page == null || !page.isPresent()) {
				pager.setCurrentPageNo(1);
			} else {
				pager.setCurrentPageNo(page.get());
			}

			Item itemCriteria = new Item();

			ItemDTO itemList =itemService.listItems(itemCriteria, pager);
			List<Item> itemsList = itemList.getItems();

			ItemBeanDTO items = new ItemBeanDTO();

			this.setItemList(itemsList, items);

			Pager tmpPager = itemList.getPager();
			pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(),
					tmpPager.getMaxDisplayPage());

			model.addAttribute("items", items);
			model.addAttribute("pager", pager);
			model.addAttribute("success", "The {" + pager.getResultSize() + "} items record has been retrieved");
			logger.info("The item details has been retrieved successfully.");
		} catch (Exception e) {
			logger.error("There is an error while retrieving items", e);
			return "error";
		}
		return "common/display";
	}

	@GetMapping("/search_item")
	public String searchItem(Model model, HttpSession session) {
		ItemBeanDTO items = new ItemBeanDTO();
		model.addAttribute("items", items);
		model.addAttribute("itemBean", new ItemBean());
		logger.info("The item details has been retrieved successfully.");
		return "item/manage_item";
	}

	@PostMapping("/search_item")
	public String searchItems(@ModelAttribute ItemBean itemBean, @RequestParam("page") Optional<Integer> page,
			Model model, HttpSession session) {
		try {

			Pager pager = itemBean.getPager();
			pager = new Pager();
			if (page == null || !page.isPresent()) {
				pager.setCurrentPageNo(1);
			} else {
				pager.setCurrentPageNo(page.get());
			}

			ItemDTO itemList = itemService.searchItem(itemBean.getName(), pager);
			List<Item> itemsList = itemList.getItems();

			ItemBeanDTO items = new ItemBeanDTO();

			this.setItemList(itemsList, items);

			Pager tmpPager = itemList.getPager();
			pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(),
					tmpPager.getMaxDisplayPage());

			model.addAttribute("items", items);
			model.addAttribute("itemBean", itemBean);
			model.addAttribute("pager", pager);
			model.addAttribute("success", "The {" + pager.getResultSize() + "} items record has been retrieved");
			logger.info("The item details has been retrieved successfully.");
		} catch (Exception e) {
			logger.error("There is an error while retrieving items", e);
			return "error";
		}

		return "item/manage_item";
	}

	private void setItemList(List<Item> itemsList, ItemBeanDTO items) {
		List<ItemBean> itemBeanList = new ArrayList<ItemBean>();
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
