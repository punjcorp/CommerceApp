/**
 * 
 */
package com.punj.app.ecommerce.models.item;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author admin
 *
 */
public class ItemImageBean implements Serializable {

	private BigInteger itemImageId;
	private BigInteger itemId;

	private String name;
	private String imageURL;
	private String imageType;
	private MultipartFile imageData;
	private String baseEncodedImage;

	private String createdBy;
	private LocalDateTime createdDate;

	/**
	 * @return the itemImageId
	 */
	public BigInteger getItemImageId() {
		return itemImageId;
	}

	/**
	 * @param itemImageId
	 *            the itemImageId to set
	 */
	public void setItemImageId(BigInteger itemImageId) {
		this.itemImageId = itemImageId;
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
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @param imageURL
	 *            the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * @return the imageType
	 */
	public String getImageType() {
		return imageType;
	}

	/**
	 * @param imageType
	 *            the imageType to set
	 */
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	/**
	 * @return the imageData
	 */
	public MultipartFile getImageData() {
		return imageData;
	}

	/**
	 * @param imageData
	 *            the imageData to set
	 */
	public void setImageData(MultipartFile imageData) {
		this.imageData = imageData;
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
	 * @return the baseEncodedImage
	 */
	public String getBaseEncodedImage() {
		return baseEncodedImage;
	}

	/**
	 * @param baseEncodedImage
	 *            the baseEncodedImage to set
	 */
	public void setBaseEncodedImage(String baseEncodedImage) {
		this.baseEncodedImage = baseEncodedImage;
	}

}
