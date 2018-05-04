/**
 * 
 */
package com.punj.app.ecommerce.models.item;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class ItemBean implements Serializable {

	private BigInteger itemId;

	@NotBlank(message = "{commerce.error.string.empty}", groups = {ValidationGroup.ValidationGroupStyle.class,ValidationGroup.ValidationGroupSKU.class})
	@Size(min = 5, max = 150, message = "{commerce.error.string.size}", groups = {ValidationGroup.ValidationGroupStyle.class,ValidationGroup.ValidationGroupSKU.class})
	private String name;
	@Size(max = 300, message = "{commerce.error.string.max.size}")
	private String longDesc;
	private Integer itemLevel;
	private BigInteger parentItemId;
	@NotBlank(message = "{commerce.error.option.empty}")
	private String itemType;

	private String status;
	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;

	private HierarchyBean hierarchy;

	@Valid
	private ItemOptionsBean itemOptions;

	private List<ItemImageBean> itemImages;

	private String attrName;
	private List<AttributeBean> selectedAttributes;

	private Pager pager;

	public ItemBean() {
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
	public List<ItemImageBean> getItemImages() {
		return itemImages;
	}

	/**
	 * @param itemImages
	 *            the itemImages to set
	 */
	public void setItemImages(List<ItemImageBean> itemImages) {
		this.itemImages = itemImages;
	}

	/**
	 * @return the selectedAttributes
	 */
	public List<AttributeBean> getSelectedAttributes() {
		return selectedAttributes;
	}

	/**
	 * @param selectedAttributes
	 *            the selectedAttributes to set
	 */
	public void setSelectedAttributes(List<AttributeBean> selectedAttributes) {
		this.selectedAttributes = selectedAttributes;
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

	/**
	 * @return the attrName
	 */
	public String getAttrName() {
		return attrName;
	}

	/**
	 * @param attrName
	 *            the attrName to set
	 */
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
