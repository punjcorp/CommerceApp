package com.punj.app.ecommerce.controller.inventory;
/**
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.InventoryBeanTransformer;
import com.punj.app.ecommerce.domains.inventory.StockAdjustment;
import com.punj.app.ecommerce.domains.inventory.StockDTO;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.inventory.InvAdjustBean;
import com.punj.app.ecommerce.models.inventory.InvAdjustBeanDTO;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class SearchInvAdjustController {
	private static final Logger logger = LogManager.getLogger();
	private InventoryService inventoryService;

	/**
	 * @param inventoryService
	 *            the inventoryService to set
	 */
	@Autowired
	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@GetMapping(ViewPathConstants.SEARCH_INV_ADJUST_URL)
	public String listInventoryAdjustments(@RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, Model model) {
		try {
			Pager pager = new Pager();
			if (!page.isPresent()) {
				pager.setCurrentPageNo(1);
			} else {
				pager.setCurrentPageNo(page.get());
			}

			StockAdjustment searchCriteria = new StockAdjustment();

			StockDTO stockList = this.inventoryService.listStockAdjustments(searchCriteria, pager);
			List<StockAdjustment> stocksList = stockList.getStockAdjustments();

			InvAdjustBeanDTO invAdjusts = new InvAdjustBeanDTO();

			this.setInvAdjustList(stocksList, invAdjusts);

			Pager tmpPager = stockList.getPager();
			pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(), tmpPager.getMaxDisplayPage(),
					ViewPathConstants.SEARCH_INV_ADJUST_URL);

			
			model.addAttribute(MVCConstants.INV_ADJUSTS_BEAN, invAdjusts);
			model.addAttribute(MVCConstants.PAGER, pager);
			model.addAttribute(MVCConstants.SUCCESS, "The {" + pager.getResultSize() + "} inventory adjustment records has been retrieved");
			logger.info("All the request inventory adjustments has been retrieved successfully.");
		} catch (Exception e) {
			logger.error("There is an error while retrieving inventory adjustments", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.MANAGE_INV_ADJUST_PAGE;
	}

	@GetMapping(ViewPathConstants.MANAGE_INV_ADJUST_URL)
	public String searchInventoryAdjustment(Model model, HttpSession session) {
		model.addAttribute("searchBean", new SearchBean());
		logger.info("The inventory adjustment details has been retrieved successfully.");
		return ViewPathConstants.MANAGE_INV_ADJUST_PAGE;
	}

	@PostMapping(ViewPathConstants.SEARCH_INV_ADJUST_URL)
	public String searchInventoryAdjustments(@ModelAttribute @Valid SearchBean searchBean, BindingResult bindingResult,
			@RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, @ModelAttribute(MVCConstants.SUCCESS) String success, @ModelAttribute(MVCConstants.ALERT) String alert, Model model) {
		if (bindingResult.hasErrors())
			return ViewPathConstants.MANAGE_INV_ADJUST_PAGE;
		try {

			Pager pager = new Pager();
			if (!page.isPresent()) {
				if(searchBean.getPage()!=null) {
					pager.setCurrentPageNo(searchBean.getPage());
				}else {
					pager.setCurrentPageNo(1);					
				}
			} else {
				pager.setCurrentPageNo(page.get());
			}

			StockDTO stockList = this.inventoryService.searchStockAdjustments(searchBean.getSearchText(), pager);
			List<StockAdjustment> stocksList = stockList.getStockAdjustments();

			InvAdjustBeanDTO invAdjusts = new InvAdjustBeanDTO();

			this.setInvAdjustList(stocksList, invAdjusts);

			Pager tmpPager = stockList.getPager();
			pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(), tmpPager.getMaxDisplayPage(),
					ViewPathConstants.SEARCH_INV_ADJUST_URL);

			invAdjusts.setSearchBean(searchBean);

			invAdjusts.getSearchBean().setPage(tmpPager.getCurrentPageNo());
			
			model.addAttribute(MVCConstants.INV_ADJUSTS_BEAN, invAdjusts);
			model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);
			model.addAttribute(MVCConstants.PAGER, pager);
			if (StringUtils.isNotEmpty(success)) {
				model.addAttribute(MVCConstants.SUCCESS, success);
				model.addAttribute(MVCConstants.ALERT,null);
			} else if (StringUtils.isNotEmpty(alert)) {
				model.addAttribute(MVCConstants.SUCCESS,null);				
				model.addAttribute(MVCConstants.ALERT, alert);
			} else {
				model.addAttribute(MVCConstants.ALERT,null);
				model.addAttribute(MVCConstants.SUCCESS, "The {" + pager.getResultSize() + "} inventory adjustment records has been retrieved");
			}
			logger.info("All the request inventory adjustments has been retrieved successfully.");
		} catch (Exception e) {
			logger.error("There is an error while retrieving inventory adjustments", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.MANAGE_INV_ADJUST_PAGE;
	}

	private void setInvAdjustList(List<StockAdjustment> stocksList, InvAdjustBeanDTO invAdjusts) {
		List<InvAdjustBean> invAdjustBeanList = new ArrayList<>(stocksList.size());
		InvAdjustBean invAdjustBean = null;

		for (StockAdjustment stockAdjustment : stocksList) {
			invAdjustBean = InventoryBeanTransformer.transformStockAdjustmentDomain(stockAdjustment);
			invAdjustBeanList.add(invAdjustBean);
		}
		invAdjusts.setInvAdjusts(invAdjustBeanList);
		logger.info("The inventory adjustment basic details has been set in bean DTO successfully");
	}

}
