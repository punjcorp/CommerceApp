/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.price.ItemPrice;
import com.punj.app.ecommerce.domains.price.ItemPriceDTO;
import com.punj.app.ecommerce.domains.price.ItemPriceHistory;
import com.punj.app.ecommerce.repositories.price.ItemPriceHistoryRepository;
import com.punj.app.ecommerce.repositories.price.ItemPriceRepository;
import com.punj.app.ecommerce.services.PriceService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Service
public class PriceServiceImpl implements PriceService {

	private static final Logger logger = LogManager.getLogger();
	private ItemPriceRepository itemPriceRepository;
	private ItemPriceHistoryRepository itemPriceHistoryRepository;

	@Value("${commerce.list.max.perpage}")
	private Integer maxResultPerPage;

	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

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
	public ItemPriceDTO searchItemPrice(BigInteger itemId, Integer locationId, String priceType, Integer page) {

		int pageNo = page - 1;
		
		ItemPriceDTO itemPriceDTO = new ItemPriceDTO();
		ItemPrice itemPrice = new ItemPrice();
		if (itemId != null) {
			Item item = new Item();
			item.setItemId(itemId);
			itemPrice.setItem(item);
		}
		if(locationId!=null && locationId==0)
			locationId=null;
		if(priceType!=null && priceType.equals(ServiceConstants.OPTION_ALL))
			priceType=null;
		itemPrice.setLocationId(locationId);
		itemPrice.setType(priceType);
		Pageable pager = new PageRequest(pageNo, maxResultPerPage);

		Page<ItemPrice> itemPricePages = this.itemPriceRepository.findAll(Example.of(itemPrice), pager);

		Pager finalPager = new Pager((int)itemPricePages.getTotalElements(), maxResultPerPage, page, maxPageBtns, null);
		itemPriceDTO.setItemPriceList(itemPricePages.getContent());
		itemPriceDTO.setPager(finalPager);

		logger.info("The item prices based on criteria has been successfully retrieved");
		return itemPriceDTO;
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

	@Override
	public List<ItemPrice> saveItemPriceList(List<ItemPrice> itemPriceList) {
		itemPriceList = this.approveItemPriceList(itemPriceList);
		logger.info("All the {} item prices has been saved now", itemPriceList.size());
		return itemPriceList;
	}

	@Override
	public Boolean approvePrice(BigInteger itemPriceId, String username) {
		Boolean result = Boolean.FALSE;
		ItemPrice itemPrice = this.itemPriceRepository.findOne(itemPriceId);
		itemPrice.setStatus(ServiceConstants.STATUS_APPROVED);
		itemPrice.setModifiedBy(username);
		itemPrice.setModifiedDate(LocalDateTime.now());
		itemPrice = this.itemPriceRepository.save(itemPrice);
		if (itemPrice != null) {
			result = Boolean.TRUE;
			logger.info("The {} item price has been approved now", itemPriceId);
		}
		return result;
	}

	@Override
	public Boolean deletePrice(BigInteger itemPriceId, String username) {
		this.itemPriceRepository.delete(itemPriceId);
		logger.info("The {} item price has been deleted now", itemPriceId);
		return Boolean.TRUE;
	}

	@Override
	public ItemPrice searchPrice(BigInteger itemPriceId) {
		ItemPrice itemPrice = this.itemPriceRepository.findOne(itemPriceId);
		if (itemPrice != null) {
			logger.info("The {} item price has been retrieved", itemPrice.getItemPriceId());
		} else {
			logger.info("The requested item price was not retrieved", itemPriceId);
		}

		return itemPrice;
	}

	@Override
	public ItemPriceHistory createItemPrice(ItemPriceHistory itemPriceHistory) {
		itemPriceHistory = this.itemPriceHistoryRepository.save(itemPriceHistory);
		logger.info("The request {} item price has been archived", itemPriceHistory.getItemPriceHistoryId());
		return itemPriceHistory;
	}

}
