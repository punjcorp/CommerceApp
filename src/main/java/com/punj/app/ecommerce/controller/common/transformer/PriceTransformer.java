/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.Money;

import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.price.ItemPrice;
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
	public static ItemPrice transformPriceBean(PriceBean priceBean) {
		ItemPrice itemPrice = new ItemPrice();

		itemPrice.setItemPriceId(priceBean.getItemPriceId());
		itemPrice.setLocationId(priceBean.getLocationId());
		itemPrice.setStatus(priceBean.getStatus());
		itemPrice.setType(priceBean.getPriceType());

		itemPrice.setItemPrice(BigDecimal.valueOf(priceBean.getItemPriceAmt().getNumber().doubleValueExact()));

		Item item = new Item();
		item.setItemId(priceBean.getItemId());
		itemPrice.setItem(item);

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
	public static PriceBean transformItemPriceDomain(ItemPrice itemPrice) {
		PriceBean priceBean = new PriceBean();

		priceBean.setItemPriceId(itemPrice.getItemPriceId());
		priceBean.setLocationId(itemPrice.getLocationId());
		priceBean.setStatus(itemPrice.getStatus());
		priceBean.setPriceType(itemPrice.getType());

		priceBean.setItemPriceAmt(Money.of(itemPrice.getItemPrice(), Utils.getLocaleCurrency()));

		priceBean.setItemId(itemPrice.getItem().getItemId());

		priceBean.setStartDate(itemPrice.getStartDate());
		priceBean.setEndDate(itemPrice.getEndDate());

		priceBean.setCreatedBy(itemPrice.getCreatedBy());
		priceBean.setCreatedDate(itemPrice.getCreatedDate());
		priceBean.setModifiedBy(itemPrice.getModifiedBy());
		priceBean.setModifiedDate(itemPrice.getModifiedDate());

		logger.info("The item price details has been transformed into price bean successfully");
		return priceBean;
	}

	public static ItemPrice updatePrice(ItemPrice itemPrice, String username) {
		if (itemPrice.getItemPriceId() == null) {
			itemPrice.setCreatedBy(username);
			itemPrice.setCreatedDate(LocalDateTime.now());
			logger.info("The item price is updated with creation time and user successfully");
		} else {
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
			priceBean = PriceTransformer.transformItemPriceDomain(itemPrice);
			priceBeanList.add(priceBean);
		}
		logger.info("The item price list has been transformed into price bean successfully");
		return priceBeanList;
	}
	
	public static List<ItemPrice> transformPriceBeanList(List<PriceBean> priceBeanList) {
		List<ItemPrice> itemPriceList = new ArrayList<>(priceBeanList.size());
		ItemPrice itemPrice;
		for (PriceBean priceBean : priceBeanList) {
			itemPrice = PriceTransformer.transformPriceBean(priceBean);
			itemPriceList.add(itemPrice);
		}
		logger.info("The price bean list has been transformed into item price bean successfully");
		return itemPriceList;
	}	
	

}
