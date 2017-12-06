/**
 * 
 */
package com.punj.app.ecommerce.models;

/**
 * @author admin
 *
 */
public class CardBean {

	private String name;
	private String cardNo;
	private String expiryDate;
	private Integer cvvNo;
	private RegisterUserBean address;

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
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo
	 *            the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the expiryDate
	 */
	public String getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate
	 *            the expiryDate to set
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the cvvNo
	 */
	public Integer getCvvNo() {
		return cvvNo;
	}

	/**
	 * @param cvvNo
	 *            the cvvNo to set
	 */
	public void setCvvNo(Integer cvvNo) {
		this.cvvNo = cvvNo;
	}

	/**
	 * @return the address
	 */
	public RegisterUserBean getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(RegisterUserBean address) {
		this.address = address;
	}

	
	
}
