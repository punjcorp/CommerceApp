/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.inventory.ItemStock;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemImage;
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
	public SaleItem getItem(BigInteger itemId, Integer locationId, String gstFlag) throws UnsupportedEncodingException {
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
				List<LocationTax> taxList = null;
				if (StringUtils.isNotBlank(gstFlag)) {
					if (gstFlag.equals(ServiceConstants.TAX_GST_FLAG_SGST)) {
						taxList = this.retrieveTax(locationId, item.getItemOptions().getTaxGroupId(), ServiceConstants.TAX_WITHIN_STATE);
					} else if (gstFlag.equals(ServiceConstants.TAX_GST_FLAG_IGST)) {
						taxList = this.retrieveTax(locationId, item.getItemOptions().getTaxGroupId(), ServiceConstants.TAX_OTHER_STATE);
					} else if (gstFlag.equals(ServiceConstants.TAX_NO_GST_FLAG)) {
						taxList = null;
					}
				}

				saleItem = this.transformItemDetails(item, itemStock, itemPrice, taxList);
				this.updateItemImage(item, saleItem);
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
		saleItem.setHsnNo(item.getItemOptions().getHsnNo());
		saleItem.setName(item.getName());
		saleItem.setLongDesc(item.getDescription());
		if (item.getImages() != null && !item.getImages().isEmpty())
			saleItem.setImagePath(item.getImages().get(0).getImageURL());

		saleItem.setUnitCostAmt(item.getItemOptions().getUnitCost());
		saleItem.setSuggestedPrice(item.getItemOptions().getSuggestedPrice());
		saleItem.setMaxRetailPrice(item.getItemOptions().getMaxRetailPrice());

		BigDecimal itemPriceAmt = itemPrice.getItemPrice();
		saleItem.setPriceAmt(itemPriceAmt);
		saleItem.setQty(1.0);

		BigDecimal discountAmt = new BigDecimal("0");
		saleItem.setDiscountAmt(discountAmt);

		BigDecimal totalTaxAmt = new BigDecimal("0");
		BigDecimal totalTaxRate = new BigDecimal("0");
		BigDecimal taxPercentage;
		BigDecimal taxAmt;

		if (taxList != null && !taxList.isEmpty()) {
			SaleItemTax saleItemTax = null;

			for (LocationTax locationTax : taxList) {
				saleItemTax = this.tranformItemTax(locationTax, itemPriceAmt);
				if (saleItemTax.getTypeCode().equals(ServiceConstants.TAX_SGST)) {
					saleItem.setSgstTax(saleItemTax);
				} else if (saleItemTax.getTypeCode().equals(ServiceConstants.TAX_CGST)) {
					saleItem.setCgstTax(saleItemTax);
				} else if (saleItemTax.getTypeCode().equals(ServiceConstants.TAX_IGST)) {
					saleItem.setIgstTax(saleItemTax);
				}
				totalTaxRate = totalTaxRate.add(saleItemTax.getPercentage());
			}
		}

		if (totalTaxRate != null && totalTaxRate.compareTo(BigDecimal.ZERO) > 0)
			totalTaxAmt = ((itemPriceAmt.subtract(discountAmt)).multiply(totalTaxRate)).divide(new BigDecimal("100"));

		BigDecimal finalTotalAmt = itemPriceAmt.subtract(discountAmt).add(totalTaxAmt);
		saleItem.setTaxAmt(totalTaxAmt);
		saleItem.setTotalAmt(finalTotalAmt.setScale(0, RoundingMode.HALF_UP));

		return saleItem;
	}

	private SaleItemTax tranformItemTax(LocationTax locationTax, BigDecimal itemPriceAmt) {
		SaleItemTax saleItemTax = new SaleItemTax();

		BigDecimal taxPercentage = locationTax.getPercentage();

		saleItemTax.setTaxGroupId(locationTax.getLocationTaxId().getTaxGroupId());
		saleItemTax.setTaxRuleRateId(locationTax.getRateRuleId());

		saleItemTax.setTaxGroupName(locationTax.getGroupDesc());
		saleItemTax.setTaxGroupRateName(locationTax.getGroupRateName());
		saleItemTax.setTypeCode(locationTax.getTypeCode());

		saleItemTax.setPercentage(taxPercentage);
		if (taxPercentage != null && taxPercentage.doubleValue() > 0) {
			BigDecimal taxAmt = taxPercentage.multiply(itemPriceAmt).divide(new BigDecimal("100"));
			saleItemTax.setAmount(taxAmt);
		} else {
			saleItemTax.setAmount(locationTax.getAmount());
		}

		return saleItemTax;
	}

	private void updateItemImage(Item item, SaleItem saleItem) throws UnsupportedEncodingException {

		if (item.getImages() != null && !item.getImages().isEmpty()) {
			ItemImage itemImage = item.getImages().get(0);
			byte[] imageData = itemImage.getImageData();
			byte[] encodeBase64 = Base64.encodeBase64(imageData);
			String base64Encoded = new String(encodeBase64, "UTF-8");
			saleItem.setImageData(base64Encoded);
			saleItem.setImageType(itemImage.getImageType());
		}
		logger.info("The item image data has been updated successfully");
	}

}
