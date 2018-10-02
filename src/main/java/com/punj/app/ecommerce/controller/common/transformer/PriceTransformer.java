/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.price.ItemPrice;
import com.punj.app.ecommerce.domains.tax.TaxGroup;
import com.punj.app.ecommerce.models.price.PriceBean;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
public class PriceTransformer {

	private static final Logger logger = LogManager.getLogger();

	private PriceTransformer() {
		throw new IllegalStateException("PriceTransformer class");
	}

	/**
	 * This method is used to set the Domain objects so that the data can be persisted to the database
	 * 
	 * @param priceBean
	 * @return The domain object for entity
	 */
	public static ItemPrice transformPriceBean(PriceBean priceBean, TaxGroup taxGroup) {
		ItemPrice itemPrice = new ItemPrice();

		itemPrice.setItemPriceId(priceBean.getItemPriceId());
		itemPrice.setLocationId(priceBean.getLocationId());
		itemPrice.setStatus(priceBean.getStatus());
		itemPrice.setType(priceBean.getPriceType());
		if(priceBean.getTaxType().equals(MVCConstants.TAX_INCLUSIVE_PARAM))
			itemPrice.setTaxInclusive(Boolean.TRUE);
		else
			itemPrice.setTaxInclusive(Boolean.FALSE);

		if(priceBean.getTaxType().equals(MVCConstants.TAX_INCLUSIVE_PARAM) && taxGroup!=null) {
				BigDecimal itemPriceAmt = priceBean.getItemPriceAmt();
				BigDecimal taxDivident = (taxGroup.getAggregatedRate().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)).add(BigDecimal.ONE);
				itemPriceAmt = itemPriceAmt.divide(taxDivident, 2, RoundingMode.HALF_UP);
				itemPrice.setItemPrice(itemPriceAmt);
		}else {
			itemPrice.setItemPrice(priceBean.getItemPriceAmt());
		}
		
		

		Item item = new Item();
		item.setItemId(priceBean.getItemId());
		itemPrice.setItem(item);

		itemPrice.setClearanceResetId(priceBean.getClearanceResetId());
		itemPrice.setStartDate(priceBean.getStartDate());
		itemPrice.setEndDate(priceBean.getEndDate());

		itemPrice.setCreatedBy(priceBean.getCreatedBy());
		itemPrice.setCreatedDate(priceBean.getCreatedDate());
		itemPrice.setModifiedBy(priceBean.getModifiedBy());
		itemPrice.setModifiedDate(priceBean.getModifiedDate());
		logger.info("The price bean has been transformed to item price domain object");

		return itemPrice;
	}

	/**
	 * This method is used to transform item price domain to price bean object
	 * 
	 * @param itemPrice
	 * @return
	 */
	public static PriceBean transformItemPriceDomain(ItemPrice itemPrice, Boolean convertIncPrice, TaxGroup taxGroup) {
		PriceBean priceBean = new PriceBean();

		priceBean.setItemPriceId(itemPrice.getItemPriceId());
		priceBean.setLocationId(itemPrice.getLocationId());
		priceBean.setStatus(itemPrice.getStatus());
		priceBean.setPriceType(itemPrice.getType());
		priceBean.setPriceTypeDesc(Utils.showPriceType(itemPrice.getType()));
		
		priceBean.setItemPriceAmt(itemPrice.getItemPrice());
		
		if(itemPrice.getTaxInclusive()!=null && itemPrice.getTaxInclusive()) {
			priceBean.setTaxType(MVCConstants.TAX_INCLUSIVE_PARAM);
			if(convertIncPrice && taxGroup!=null) {
				BigDecimal itemPriceAmt = itemPrice.getItemPriceAmt();
				BigDecimal taxMultiplier = (taxGroup.getAggregatedRate().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)).add(BigDecimal.ONE);
				itemPriceAmt = itemPriceAmt.multiply(taxMultiplier);
				priceBean.setItemPriceAmt(itemPriceAmt.setScale(2,BigDecimal.ROUND_HALF_UP));
				
			}
		}
		else {
			priceBean.setTaxType(MVCConstants.TAX_EXCLUSIVE_PARAM);
		}
		
		
		priceBean.setClearanceResetId(itemPrice.getClearanceResetId());

		priceBean.setItemId(itemPrice.getItem().getItemId());
		priceBean.setItemDesc(itemPrice.getItem().getName());

		priceBean.setStartDate(itemPrice.getStartDate());
		priceBean.setEndDate(itemPrice.getEndDate());

		priceBean.setCreatedBy(itemPrice.getCreatedBy());
		priceBean.setCreatedDate(itemPrice.getCreatedDate());
		priceBean.setModifiedBy(itemPrice.getModifiedBy());
		priceBean.setModifiedDate(itemPrice.getModifiedDate());

		logger.info("The item price details has been transformed into price bean successfully");
		return priceBean;
	}

	public static ItemPrice updatePrice(ItemPrice itemPrice, String username, String action) {
		// if it is a save or approve from the new price screen directly
		if ((MVCConstants.ACTION_NEW_SAVE.equals(action) && itemPrice.getItemPriceId() == null)
				|| (MVCConstants.ACTION_NEW_APPROVE.equals(action) && itemPrice.getItemPriceId() == null)) {
			itemPrice.setCreatedBy(username);
			itemPrice.setCreatedDate(LocalDateTime.now());
			logger.info("The item price is updated with creation time and user successfully");
		} else if (MVCConstants.ACTION_EDIT_SAVE.equals(action) || MVCConstants.ACTION_EDIT_APPROVE.equals(action)
				|| (MVCConstants.ACTION_NEW_APPROVE.equals(action) && itemPrice.getItemPriceId() != null)) {
			itemPrice.setModifiedBy(username);
			itemPrice.setModifiedDate(LocalDateTime.now());
			logger.info("The item price is updated with modified time and user successfully");
		}
		return itemPrice;
	}

	public static List<PriceBean> transformItemPriceList(List<ItemPrice> itemPriceList) {
		List<PriceBean> priceBeanList = new ArrayList<>(itemPriceList.size());
		PriceBean priceBean;
		for (ItemPrice itemPrice : itemPriceList) {
			priceBean = PriceTransformer.transformItemPriceDomain(itemPrice, Boolean.FALSE,null);
			priceBeanList.add(priceBean);
		}
		logger.info("The item price list has been transformed into price bean successfully");
		return priceBeanList;
	}

	public static List<ItemPrice> transformPriceBeanList(List<PriceBean> priceBeanList) {
		List<ItemPrice> itemPriceList = new ArrayList<>(priceBeanList.size());
		ItemPrice itemPrice;
		for (PriceBean priceBean : priceBeanList) {
			itemPrice = PriceTransformer.transformPriceBean(priceBean,null);
			itemPriceList.add(itemPrice);
		}
		logger.info("The price bean list has been transformed into item price bean successfully");
		return itemPriceList;
	}

}
