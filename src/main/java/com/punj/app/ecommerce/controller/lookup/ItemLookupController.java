package com.punj.app.ecommerce.controller.lookup;
/**
 * 
 */

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.InventoryBeanTransformer;
import com.punj.app.ecommerce.controller.common.transformer.ItemTransformer;
import com.punj.app.ecommerce.controller.common.transformer.PriceTransformer;
import com.punj.app.ecommerce.domains.inventory.ItemStock;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemDTO;
import com.punj.app.ecommerce.domains.item.ItemImage;
import com.punj.app.ecommerce.domains.price.ItemPrice;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.inventory.ItemInventory;
import com.punj.app.ecommerce.models.item.HierarchyBean;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.item.ItemBeanDTO;
import com.punj.app.ecommerce.models.lookup.ItemLookupBean;
import com.punj.app.ecommerce.models.price.PriceBean;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.services.ItemService;
import com.punj.app.ecommerce.services.PriceService;
import com.punj.app.ecommerce.services.SaleItemService;
import com.punj.app.ecommerce.services.SupplierService;
import com.punj.app.ecommerce.services.dtos.SaleItem;
import com.punj.app.ecommerce.utils.Pager;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ItemLookupController {
	private static final Logger logger = LogManager.getLogger();
	private ItemService itemService;
	private InventoryService inventoryService;
	private SaleItemService saleItemService;
	private PriceService priceService;
	private SupplierService supplierService;
	private MessageSource messageSource;
	private CommerceContext commerceContext;

	/**
	 * @param priceService
	 *            the priceService to set
	 */
	@Autowired
	public void setPriceService(PriceService priceService) {
		this.priceService = priceService;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @param inventoryService
	 *            the inventoryService to set
	 */
	@Autowired
	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

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

	/**
	 * @param commerceContext
	 *            the commerceContext to set
	 */
	@Autowired
	public void setCommerceContext(CommerceContext commerceContext) {
		this.commerceContext = commerceContext;
	}

	@GetMapping(ViewPathConstants.LOOKUP_ITEM_DETAILS_URL)
	public String getItemDetails(Model model, final HttpServletRequest req, Locale locale, RedirectAttributes redirectAttrs) {
		BigInteger itemId = new BigInteger(req.getParameter(MVCConstants.ITEM_ID_PARAM));
		if (itemId.compareTo(BigInteger.ZERO) > 0) {
			Integer locationId = (Integer) this.commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
			if (locationId != null) {

				Item item = this.itemService.getItem(itemId);
				if (item != null) {
					ItemLookupBean itemLookupBean = ItemTransformer.transformItemForLookup(item);

					ItemStock itemStock = this.inventoryService.searchItemStock(itemId, locationId);
					if(itemStock!=null) {
						ItemInventory itemInventory = InventoryBeanTransformer.transformItemStock(itemStock);
						itemLookupBean.setItemInventory(itemInventory);
					}

					List<ItemPrice> itemPriceList = this.priceService.getFutureItemPrices(itemId, locationId, LocalDateTime.now());
					if(itemPriceList!=null && !itemPriceList.isEmpty()) {
						List<PriceBean> priceBeanList = PriceTransformer.transformItemPriceList(itemPriceList);
						itemLookupBean.setItemFuturePrices(priceBeanList);
					}
					

					ItemPrice itemPrice = this.priceService.getCurrentItemPrice(itemId, locationId, LocalDateTime.now());
					if(itemPrice!=null) {
						itemLookupBean.getItemOptions().setCurrentPrice(itemPrice.getItemPriceAmt());
						itemLookupBean.setCurrentPriceStartDate(itemPrice.getStartDate());
						itemLookupBean.setCurrentPriceEndDate(itemPrice.getEndDate());
						itemLookupBean.setCurrentPriceType(Utils.showPriceType(itemPrice.getType()));
					}

					model.addAttribute(MVCConstants.ITEM_BEAN, itemLookupBean);
					model.addAttribute(MVCConstants.SUCCESS,
							this.messageSource.getMessage("commerce.screen.lookup.item.details.success", new Object[] { itemId }, locale));
				} else {
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.lookup.item.details.failure", null, locale));
				}
			} else {
				req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
				redirectAttrs.addFlashAttribute(MVCConstants.REFERRER_URL_PARAM,
						ViewPathConstants.LOOKUP_ITEM_DETAILS_URL + "?" + MVCConstants.ITEM_ID_PARAM + "=" + itemId);
				logger.info("There is no open store existing as per application context, routing to store open screen");
				return ViewPathConstants.REDIRECT_URL + ViewPathConstants.STORE_OPEN_URL;
			}

		} else {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.lookup.item.details.failure", null, locale));
		}
		logger.info("The item lookup screen has been called.");
		return ViewPathConstants.LOOKUP_ITEM_DETAILS_PAGE;
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
		try {
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
				itemBean.setCreatedBy(item.getCreatedBy());
				itemBean.setCreatedDate(item.getCreatedDate());

				// Setting the hierarchy information
				Hierarchy hierarchy = item.getHierarchy();
				hierarchyBean = new HierarchyBean();
				hierarchyBean.setHierarchyId(hierarchy.getHierarchyId());
				itemBean.setHierarchy(hierarchyBean);
				itemBean.setItemType(item.getItemType());

				if (item.getImages() != null && !item.getImages().isEmpty()) {
					ItemImage itemImage = item.getImages().get(0);
					byte[] imageData = itemImage.getImageData();
					byte[] encodeBase64 = Base64.encodeBase64(imageData);
					String base64Encoded = new String(encodeBase64, "UTF-8");
					itemBean.setBaseEncodedImage(base64Encoded);
					itemBean.setImageType(itemImage.getImageType());
				}

				itemBeanList.add(itemBean);

			}
			items.setItems(itemBeanList);
			logger.info("The item basic details has been set in bean List successfully");
		} catch (IOException e) {
			logger.error("There was an error while transforming item images", e);
		}
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

			saleItem = this.saleItemService.getItem(itemId, locationId);

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
