package com.punj.app.ecommerce.domains.item;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@AnalyzerDef(name = "edgeNgram", tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), filters = {
		@TokenFilterDef(factory = LowerCaseFilterFactory.class), // Lowercase all characters
		@TokenFilterDef(factory = EdgeNGramFilterFactory.class, // Generate prefix tokens
				params = { @Parameter(name = "minGramSize", value = "1"), @Parameter(name = "maxGramSize", value = "10") }) })
@Indexed
@Entity
public class Item implements Serializable, Cloneable {

	private static final long serialVersionUID = -1367627372654767177L;

	@Id
	@DocumentId
	@Column(name = "item_id")
	private BigInteger itemId;
	@Field(analyzer=@Analyzer(definition="edgeNgram"))
	private String name;
	@Field
	@Column(name = "long_desc")
	private String description;
	@Field
	@Column(name = "item_level")
	private Integer itemLevel;
	@Column(name = "parent_item_id")
	private BigInteger parentItemId;

	@Column(name = "item_type")
	private String itemType;
	@Column(name = "record_status")
	private String status;
	@Field
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
	private List<ItemImage> images;

	// @IndexedEmbedded
	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "hierarchy_id")
	private Hierarchy hierarchy;

	@OneToMany(mappedBy = "itemAttributeId.item", cascade = CascadeType.ALL)
	private List<ItemAttribute> itemAttributes;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="item_id")
	private ItemOptions itemOptions;

	public Item() {

	}

	public Item(BigInteger itemId, String name, String description, Integer itemLevel, BigInteger parentItemId, String itemType, String status,
			String createdBy, LocalDateTime createdDate, String modifiedBy, LocalDateTime modifiedDate) {

		this.itemId = itemId;
		this.name = name;
		this.description = description;
		this.itemLevel = itemLevel;
		this.parentItemId = parentItemId;
		this.itemType = itemType;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;

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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the images
	 */
	public List<ItemImage> getImages() {
		return images;
	}

	/**
	 * @param images
	 *            the images to set
	 */
	public void setImages(List<ItemImage> images) {
		this.images = images;
	}

	/**
	 * @return the hierarchy
	 */
	public Hierarchy getHierarchy() {
		return hierarchy;
	}

	/**
	 * @param hierarchy
	 *            the hierarchy to set
	 */
	public void setHierarchy(Hierarchy hierarchy) {
		this.hierarchy = hierarchy;
	}

	/**
	 * @return the itemAttributes
	 */
	public List<ItemAttribute> getItemAttributes() {
		return itemAttributes;
	}

	/**
	 * @param itemAttributes
	 *            the itemAttributes to set
	 */
	public void setItemAttributes(List<ItemAttribute> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	/**
	 * @return the itemOptions
	 */
	public ItemOptions getItemOptions() {
		return itemOptions;
	}

	/**
	 * @param itemOptions
	 *            the itemOptions to set
	 */
	public void setItemOptions(ItemOptions itemOptions) {
		this.itemOptions = itemOptions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Item other = (Item) obj;
		if (itemId == null) {
			if (other.itemId != null) {
				return false;
			}
		} else if (!itemId.equals(other.itemId)) {
			return false;
		}
		return true;
	}

}
