/**
 * 
 */
package com.punj.app.ecommerce.models.inventory;

/**
 * @author admin
 *
 */
public class InvReasonBean {
	private Integer reasonCodeId;
	private String reasonCode;
	private String name;
	private Integer fromBucketId;
	private Integer toBucketId;

	/**
	 * @return the reasonCodeId
	 */
	public Integer getReasonCodeId() {
		return reasonCodeId;
	}

	/**
	 * @param reasonCodeId
	 *            the reasonCodeId to set
	 */
	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}

	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode
	 *            the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
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
	 * @return the fromBucketId
	 */
	public Integer getFromBucketId() {
		return fromBucketId;
	}

	/**
	 * @param fromBucketId
	 *            the fromBucketId to set
	 */
	public void setFromBucketId(Integer fromBucketId) {
		this.fromBucketId = fromBucketId;
	}

	/**
	 * @return the toBucketId
	 */
	public Integer getToBucketId() {
		return toBucketId;
	}

	/**
	 * @param toBucketId
	 *            the toBucketId to set
	 */
	public void setToBucketId(Integer toBucketId) {
		this.toBucketId = toBucketId;
	}

}
