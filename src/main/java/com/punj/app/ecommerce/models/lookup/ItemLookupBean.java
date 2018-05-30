/**
 * 
 */
package com.punj.app.ecommerce.models.lookup;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.punj.app.ecommerce.models.inventory.ItemInventory;
import com.punj.app.ecommerce.models.item.AttributeBean;
import com.punj.app.ecommerce.models.item.HierarchyBean;
import com.punj.app.ecommerce.models.item.ItemImageBean;
import com.punj.app.ecommerce.models.item.ItemOptionsBean;
import com.punj.app.ecommerce.models.price.PriceBean;

/**
 * @author admin
 *
 */
public class ItemLookupBean implements Serializable {

	private BigInteger itemId;
	private String name;
	private String longDesc;
	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
	private ItemImageBean itemImage;

	private ItemOptionsBean itemOptions;
	private HierarchyBean hierarchy;
	private List<AttributeBean> itemAttributes;
	private ItemInventory itemInventory;
	private List<PriceBean> itemFuturePrices;

	private String currentPriceType;
	private LocalDateTime currentPriceStartDate;
	private LocalDateTime currentPriceEndDate;
	
	
	/**
	 * @return the itemId
	 */
	public BigInteger getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(BigInteger itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the longDesc
	 */
	public String getLongDesc() {
		return longDesc;
	}

	/**
	 * @param longDesc
	 *            the longDesc to set
	 */
	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the itemImage
	 */
	public ItemImageBean getItemImage() {
		return itemImage;
	}

	/**
	 * @param itemImage
	 *            the itemImage to set
	 */
	public void setItemImage(ItemImageBean itemImage) {
		this.itemImage = itemImage;
	}

	/**
	 * @return the itemOptions
	 */
	public ItemOptionsBean getItemOptions() {
		return itemOptions;
	}

	/**
	 * @param itemOptions
	 *            the itemOptions to set
	 */
	public void setItemOptions(ItemOptionsBean itemOptions) {
		this.itemOptions = itemOptions;
	}

	/**
	 * @return the hierarchy
	 */
	public HierarchyBean getHierarchy() {
		return hierarchy;
	}

	/**
	 * @param hierarchy
	 *            the hierarchy to set
	 */
	public void setHierarchy(HierarchyBean hierarchy) {
		this.hierarchy = hierarchy;
	}

	/**
	 * @return the itemAttributes
	 */
	public List<AttributeBean> getItemAttributes() {
		return itemAttributes;
	}

	/**
	 * @param itemAttributes
	 *            the itemAttributes to set
	 */
	public void setItemAttributes(List<AttributeBean> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	/**
	 * @return the itemInventory
	 */
	public ItemInventory getItemInventory() {
		return itemInventory;
	}

	/**
	 * @param itemInventory
	 *            the itemInventory to set
	 */
	public void setItemInventory(ItemInventory itemInventory) {
		this.itemInventory = itemInventory;
	}

	/**
	 * @return the itemFuturePrices
	 */
	public List<PriceBean> getItemFuturePrices() {
		return itemFuturePrices;
	}

	/**
	 * @param itemFuturePrices
	 *            the itemFuturePrices to set
	 */
	public void setItemFuturePrices(List<PriceBean> itemFuturePrices) {
		this.itemFuturePrices = itemFuturePrices;
	}

	/**
	 * @return the currentPriceType
	 */
	public String getCurrentPriceType() {
		return currentPriceType;
	}

	/**
	 * @param currentPriceType the currentPriceType to set
	 */
	public void setCurrentPriceType(String currentPriceType) {
		this.currentPriceType = currentPriceType;
	}

	/**
	 * @return the currentPriceStartDate
	 */
	public LocalDateTime getCurrentPriceStartDate() {
		return currentPriceStartDate;
	}

	/**
	 * @param currentPriceStartDate the currentPriceStartDate to set
	 */
	public void setCurrentPriceStartDate(LocalDateTime currentPriceStartDate) {
		this.currentPriceStartDate = currentPriceStartDate;
	}

	/**
	 * @return the currentPriceEndDate
	 */
	public LocalDateTime getCurrentPriceEndDate() {
		return currentPriceEndDate;
	}

	/**
	 * @param currentPriceEndDate the currentPriceEndDate to set
	 */
	public void setCurrentPriceEndDate(LocalDateTime currentPriceEndDate) {
		this.currentPriceEndDate = currentPriceEndDate;
	}

}
