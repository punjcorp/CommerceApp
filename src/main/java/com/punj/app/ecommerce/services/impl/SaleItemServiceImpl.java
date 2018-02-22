/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.inventory.ItemStock;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.price.ItemPrice;
import com.punj.app.ecommerce.domains.tax.LocationTax;
import com.punj.app.ecommerce.domains.tax.ids.LocationTaxId;
import com.punj.app.ecommerce.repositories.tax.LocationTaxRepository;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.services.ItemService;
import com.punj.app.ecommerce.services.PriceService;
import com.punj.app.ecommerce.services.SaleItemService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.dtos.SaleItem;
import com.punj.app.ecommerce.services.dtos.SaleItemTax;

/**
 * @author admin
 *
 */
@Service
public class SaleItemServiceImpl implements SaleItemService {

	private static final Logger logger = LogManager.getLogger();
	private ItemService itemService;
	private PriceService priceService;
	private InventoryService inventoryService;
	private LocationTaxRepository locationTaxRepository;

	/**
	 * @param itemService
	 *            the itemService to set
	 */
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	/**
	 * @param priceService
	 *            the priceService to set
	 */
	@Autowired
	public void setPriceService(PriceService priceService) {
		this.priceService = priceService;
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
	 * @param locationTaxRepository
	 *            the locationTaxRepository to set
	 */
	@Autowired
	public void setLocationTaxRepository(LocationTaxRepository locationTaxRepository) {
		this.locationTaxRepository = locationTaxRepository;
	}

	@Override
	public SaleItem getItem(BigInteger itemId, Integer locationId) {
		SaleItem saleItem = null;
		// Step 1 - Is Item Valid
		// Step 2 - Is Item Valid at the provided location
		Item item = this.itemService.getItem(itemId);
		if (item != null) {
			logger.info("Searching for Item Stock and Prices");
			// Step 3 - does the location have inventory
			ItemStock itemStock = this.inventoryService.searchItemStock(itemId, locationId);
			if (itemStock != null) {
				logger.info("The item stock record was found successfully");
				// Step 4 - does the item has price defined
				ItemPrice itemPrice = this.priceService.getCurrentItemPrice(itemId, locationId, LocalDateTime.now());
				// Step 5 - does the item has tax defined
				List<LocationTax> taxList = this.retrieveTax(locationId, item.getItemOptions().getTaxGroupId(), ServiceConstants.TAX_WITHIN_STATE);

				saleItem = this.transformItemDetails(item, itemStock, itemPrice, taxList);
			}

		}

		return saleItem;
	}

	/**
	 * This method retrieves all the taxes for a tax group
	 * 
	 * @param locationId
	 * @param taxGroupId
	 * @param billingLocation
	 * @return
	 */
	private List<LocationTax> retrieveTax(Integer locationId, Integer taxGroupId, String billingLocation) {
		LocationTax taxCriteria = new LocationTax();

		LocationTaxId taxId = new LocationTaxId();
		taxId.setLocationId(locationId);
		taxId.setTaxGroupId(taxGroupId);
		taxId.setBillingLocationCode(billingLocation);

		taxCriteria.setLocationTaxId(taxId);

		List<LocationTax> taxList = this.locationTaxRepository.findAll(Example.of(taxCriteria));

		logger.info("The taxes for the tax group {} and location {} has been retrieved successfully", taxGroupId, locationId);

		return taxList;
	}

	private SaleItem transformItemDetails(Item item, ItemStock itemStock, ItemPrice itemPrice, List<LocationTax> taxList) {
		SaleItem saleItem = new SaleItem();

		saleItem.setItemId(item.getItemId());
		saleItem.setName(item.getName());
		saleItem.setLongDesc(item.getDescription());

		saleItem.setImagePath(item.getImages().get(0).getImageURL());

		BigDecimal itemPriceAmt = itemPrice.getItemPrice();
		BigDecimal taxPercentage;
		BigDecimal taxAmt;
		saleItem.setPriceAmt(itemPriceAmt);
		saleItem.setQty(1.0);

		BigDecimal discountAmt = new BigDecimal("0");
		saleItem.setDiscountAmt(discountAmt);

		BigDecimal totalTaxAmt = new BigDecimal("0");

		if (taxList != null && !taxList.isEmpty()) {
			SaleItemTax saleItemTax = null;
			LocationTax locationTax = null;
			if (taxList.size() == 2) {
				saleItemTax = new SaleItemTax();
				locationTax = taxList.get(0);

				taxPercentage = locationTax.getPercentage();

				saleItemTax.setPercentage(taxPercentage);
				saleItemTax.setAmount(locationTax.getAmount());
				saleItemTax.setTaxGroupName(locationTax.getGroupDesc());
				saleItemTax.setTaxGroupRateName(locationTax.getGroupRateName());
				saleItemTax.setTypeCode(locationTax.getTypeCode());

				taxAmt = taxPercentage.multiply(itemPriceAmt).divide(new BigDecimal("100"));

				totalTaxAmt = totalTaxAmt.add(taxAmt);

				saleItemTax.setAmount(taxAmt);

				saleItem.setSgstTax(saleItemTax);

				saleItemTax = new SaleItemTax();
				locationTax = taxList.get(1);

				taxPercentage = locationTax.getPercentage();

				saleItemTax.setPercentage(taxPercentage);
				saleItemTax.setAmount(locationTax.getAmount());
				saleItemTax.setTaxGroupName(locationTax.getGroupDesc());
				saleItemTax.setTaxGroupRateName(locationTax.getGroupRateName());
				saleItemTax.setTypeCode(locationTax.getTypeCode());

				taxAmt = taxPercentage.multiply(itemPriceAmt).divide(new BigDecimal("100"));

				totalTaxAmt = totalTaxAmt.add(taxAmt);

				saleItemTax.setAmount(taxAmt);

				saleItem.setCgstTax(saleItemTax);
			} else {
				saleItemTax = new SaleItemTax();
				locationTax = taxList.get(0);

				taxPercentage = locationTax.getPercentage();

				saleItemTax.setPercentage(taxPercentage);
				saleItemTax.setAmount(locationTax.getAmount());
				saleItemTax.setTaxGroupName(locationTax.getGroupDesc());
				saleItemTax.setTaxGroupRateName(locationTax.getGroupRateName());
				saleItemTax.setTypeCode(locationTax.getTypeCode());

				taxAmt = taxPercentage.multiply(itemPriceAmt).divide(new BigDecimal("100"));

				totalTaxAmt = totalTaxAmt.add(taxAmt);

				saleItemTax.setAmount(taxAmt);

				saleItem.setIgstTax(saleItemTax);

			}
		}
		BigDecimal finaTotalAmt=itemPriceAmt.subtract(discountAmt).add(totalTaxAmt);

		saleItem.setTaxAmt(totalTaxAmt);
		saleItem.setTotalAmt(finaTotalAmt);



		
		return saleItem;
	}

}
