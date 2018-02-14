/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigInteger;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.price.ItemPrice;
import com.punj.app.ecommerce.repositories.item.ItemRepository;
import com.punj.app.ecommerce.repositories.price.ItemPriceHistoryRepository;
import com.punj.app.ecommerce.repositories.price.ItemPriceRepository;
import com.punj.app.ecommerce.services.PriceService;
import com.punj.app.ecommerce.services.common.ServiceConstants;

/**
 * @author admin
 *
 */
@Service
public class PriceServiceImpl implements PriceService {

	private static final Logger logger = LogManager.getLogger();
	private ItemRepository itemRepository;
	private ItemPriceRepository itemPriceRepository;
	private ItemPriceHistoryRepository itemPriceHistoryRepository;

	@Value("${commerce.list.max.perpage}")
	private Integer maxResultPerPage;

	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

	/**
	 * @param itemRepository
	 *            the itemRepository to set
	 */
	@Autowired
	public void setItemRepository(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	/**
	 * 
	 * @param itemPriceRepository
	 */
	@Autowired
	public void setItemPriceRepository(ItemPriceRepository itemPriceRepository) {
		this.itemPriceRepository = itemPriceRepository;
	}

	/**
	 * 
	 * @param itemPriceHistoryRepository
	 */
	@Autowired
	public void setItemPriceHistoryRepository(ItemPriceHistoryRepository itemPriceHistoryRepository) {
		this.itemPriceHistoryRepository = itemPriceHistoryRepository;
	}

	/**
	 * This method will create a new price event based on the provided details
	 * 
	 * @param itemPrice
	 * @return the updated price event with id and other details
	 */
	@Override
	public ItemPrice createItemPrice(ItemPrice itemPrice) {
		itemPrice.setStatus(ServiceConstants.STATUS_CREATED);
		itemPrice = this.itemPriceRepository.save(itemPrice);
		logger.info("The item price for item {} has been created in database as per provided details", itemPrice.getItem().getItemId());
		return itemPrice;
	}

	/**
	 * This method will update new price and approve the price to be applied
	 * 
	 * @param itemPrice
	 * @return the approved price event with id and other details
	 */
	@Override
	public ItemPrice saveAndApproveItemPrice(ItemPrice itemPrice) {
		itemPrice.setStatus(ServiceConstants.STATUS_APPROVED);
		itemPrice = this.itemPriceRepository.save(itemPrice);
		logger.info("The item price for item {} has been approved with updated details", itemPrice.getItem().getItemId());
		return itemPrice;
	}

	@Override
	public List<ItemPrice> searchItemPrice(BigInteger itemId, Integer locationId, String priceType) {
		
		ItemPrice itemPrice=new ItemPrice();
		if(itemId!=null ) {
			Item item=new Item();
			item.setItemId(itemId);
			itemPrice.setItem(item);
		}
		itemPrice.setLocationId(locationId);
		itemPrice.setType(priceType);
		
		List<ItemPrice> itemPriceList=this.itemPriceRepository.findAll(Example.of(itemPrice));
		logger.info("The item prices based on criteria has been successfully retrieved");
		return itemPriceList;
	}

	/**
	 * This method will approve the price list 
	 * 
	 * @param List<ItemPrice>
	 * @return the approved price details
	 */
	@Override
	public List<ItemPrice> approveItemPriceList(List<ItemPrice> itemPriceList) {
		itemPriceList = this.itemPriceRepository.save(itemPriceList);
		logger.info("All the {} item prices has been approved now", itemPriceList.size());
		return itemPriceList;
	}

	@Override
	public void deleteItemPriceList(List<ItemPrice> itemPriceList) {
		this.itemPriceRepository.deleteInBatch(itemPriceList);
		logger.info("All the selected item prices has been deleted now", itemPriceList.size());
	}	
	
}
