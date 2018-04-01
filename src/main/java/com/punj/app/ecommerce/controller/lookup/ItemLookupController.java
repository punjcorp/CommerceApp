package com.punj.app.ecommerce.controller.lookup;
/**
 * 
 */

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemDTO;
import com.punj.app.ecommerce.domains.supplier.SupplierItem;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.item.HierarchyBean;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.item.ItemBeanDTO;
import com.punj.app.ecommerce.services.ItemService;
import com.punj.app.ecommerce.services.SaleItemService;
import com.punj.app.ecommerce.services.SupplierService;
import com.punj.app.ecommerce.services.dtos.SaleItem;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ItemLookupController {
	private static final Logger logger = LogManager.getLogger();
	private ItemService itemService;
	private SaleItemService saleItemService;
	private SupplierService supplierService;

	/**
	 * @param itemService
	 *            the itemService to set
	 */
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	/**
	 * @param supplierService
	 *            the supplierService to set
	 */
	@Autowired
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	/**
	 * @param saleItemService
	 *            the saleItemService to set
	 */
	@Autowired
	public void setSaleItemService(SaleItemService saleItemService) {
		this.saleItemService = saleItemService;
	}

	@GetMapping(ViewPathConstants.LOOKUP_ITEM_URL)
	public String searchItem(Model model, HttpSession session) {
		model.addAttribute(MVCConstants.SEARCH_BEAN, new SearchBean());
		logger.info("The item lookup screen has been called.");
		return ViewPathConstants.LOOKUP_ITEM_PAGE;
	}

	@PostMapping(value = ViewPathConstants.LOOKUP_ITEM_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ItemBeanDTO lookupItem(@ModelAttribute @Valid SearchBean searchBean, BindingResult bindingResult,
			@RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, Model model, HttpSession session) {
		ItemBeanDTO items = new ItemBeanDTO();
		if (bindingResult.hasErrors())
			return items;
		try {

			Pager pager = new Pager();
			if (!page.isPresent()) {
				pager.setCurrentPageNo(1);
			} else {
				pager.setCurrentPageNo(page.get());
			}

			ItemDTO itemList = itemService.searchItem(searchBean.getSearchText(), pager);
			List<Item> itemsList = itemList.getItems();

			this.setItemList(itemsList, items);
			Pager tmpPager = itemList.getPager();
			pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(), tmpPager.getMaxDisplayPage(),
					ViewPathConstants.LOOKUP_ITEM_URL);
			items.setPager(pager);

		} catch (Exception e) {
			logger.error("There is an error while retrieving items for lookup screen", e);
			return null;
		}

		return items;
	}

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

	@PostMapping(value = ViewPathConstants.SKU_LOOKUP_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<ItemBean> lookupSKU(@ModelAttribute @Valid SearchBean searchBean, BindingResult bindingResult, Model model, HttpSession session) {
		ItemBeanDTO items = new ItemBeanDTO();
		if (bindingResult.hasErrors())
			return items.getItems();
		try {

			Pager pager = new Pager();
			pager.setCurrentPageNo(1);

			ItemDTO itemList = itemService.searchSKUs(searchBean.getSearchText(), pager);
			List<Item> itemsList = itemList.getItems();
			this.setItemList(itemsList, items);
			logger.info("The item list based on the keyword has been retrieved");
		} catch (Exception e) {
			logger.error("There is an error while retrieving skus for sku lookup screen", e);
			return null;
		}
		return items.getItems();
	}

	@PostMapping(value = ViewPathConstants.ORDER_ITEM_LOOKUP_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<ItemBean> lookupOrderItems(@RequestBody @Valid SearchBean searchBean, BindingResult bindingResult, Model model, HttpSession session) {
		return this.lookupSKU(searchBean, bindingResult, model, session);

	}

	@GetMapping(value = ViewPathConstants.SALEITEM_LOOKUP_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public SaleItem lookupSaleItem(@RequestParam("itemId") BigInteger itemId, @RequestParam("locationId") Integer locationId) {
		SaleItem saleItem = null;
		try {

			saleItem = saleItemService.getItem(itemId, locationId);

			logger.info("the {} item details for sale item has been retrieved successfully", itemId);

		} catch (Exception e) {
			logger.error("There is an error while retrieving skus for sku lookup screen", e);
			return null;
		}
		return saleItem;
	}

	@GetMapping(value = ViewPathConstants.SEARCH_ORDER_ITEM_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public SaleItem lookupOrderItem(@RequestParam("itemId") BigInteger itemId, @RequestParam("locationId") Integer locationId,
			@RequestParam("supplierId") Integer supplierId) {
		SaleItem saleItem = this.itemService.retrieveItemDetails(locationId, supplierId, itemId, Boolean.FALSE);
		logger.info("The order line item has been retrieved successfully");
		return saleItem;
	}

}
