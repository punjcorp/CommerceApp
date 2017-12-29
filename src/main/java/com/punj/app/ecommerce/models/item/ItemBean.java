/**
 * 
 */
package com.punj.app.ecommerce.models.item;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class ItemBean {

	private BigInteger itemId;
	private String name;
	private String longDesc;
	private Integer itemLevel;
	private BigInteger parentItemId;
	private String itemType;
	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;

	private HierarchyBean hierarchy;
	private ItemOptionsBean itemOptions;

	private Map<String, String> itemImages;

	private String[] itemSizeSelected;
	private String[] itemColorSelected;

	private List<String> imageUrlList;
	private List<String> featureList;

	private Pager pager;

	public ItemBean() {
		itemImages = new HashMap<String, String>();
		itemOptions = new ItemOptionsBean();
		hierarchy = new HierarchyBean();
	}

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
	 * @return the itemLevel
	 */
	public Integer getItemLevel() {
		return itemLevel;
	}

	/**
	 * @param itemLevel
	 *            the itemLevel to set
	 */
	public void setItemLevel(Integer itemLevel) {
		this.itemLevel = itemLevel;
	}

	/**
	 * @return the parentItemId
	 */
	public BigInteger getParentItemId() {
		return parentItemId;
	}

	/**
	 * @param parentItemId
	 *            the parentItemId to set
	 */
	public void setParentItemId(BigInteger parentItemId) {
		this.parentItemId = parentItemId;
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * @param itemType
	 *            the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
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
	 * @return the itemImages
	 */
	public Map<String, String> getItemImages() {
		return itemImages;
	}

	/**
	 * @param itemImages
	 *            the itemImages to set
	 */
	public void setItemImages(Map<String, String> itemImages) {
		this.itemImages = itemImages;
	}

	/**
	 * @return the itemSizeSelected
	 */
	public String[] getItemSizeSelected() {
		return itemSizeSelected;
	}

	/**
	 * @param itemSizeSelected
	 *            the itemSizeSelected to set
	 */
	public void setItemSizeSelected(String[] itemSizeSelected) {
		this.itemSizeSelected = itemSizeSelected;
	}

	/**
	 * @return the itemColorSelected
	 */
	public String[] getItemColorSelected() {
		return itemColorSelected;
	}

	/**
	 * @param itemColorSelected
	 *            the itemColorSelected to set
	 */
	public void setItemColorSelected(String[] itemColorSelected) {
		this.itemColorSelected = itemColorSelected;
	}

	/**
	 * @return the imageUrlList
	 */
	public List<String> getImageUrlList() {
		return imageUrlList;
	}

	/**
	 * @param imageUrlList
	 *            the imageUrlList to set
	 */
	public void setImageUrlList(List<String> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}

	/**
	 * @return the featureList
	 */
	public List<String> getFeatureList() {
		return featureList;
	}

	/**
	 * @param featureList
	 *            the featureList to set
	 */
	public void setFeatureList(List<String> featureList) {
		this.featureList = featureList;
	}

	/**
	 * @return the pager
	 */
	public Pager getPager() {
		return pager;
	}

	/**
	 * @param pager
	 *            the pager to set
	 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}

}
