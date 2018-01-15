/**
 * 
 */
package com.punj.app.ecommerce.models.common;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author admin
 *
 */
public class AddressBean {

	private BigInteger addressId;
	@NotNull
	private String addressType;
	private String primary;
	@NotNull
	@Size(min = 5, max = 150, message = "{commerce.error.string.size}")	
	private String address1;
	private String address2;
	@NotNull
	@Size(min = 2, max = 30, message = "{commerce.error.string.size}")		
	private String city;
	@NotNull
	@Size(min = 2, max = 45, message = "{commerce.error.string.size}")		
	private String state;
	@NotNull
	@Size(min = 2, max = 45, message = "{commerce.error.string.size}")		
	private String country;
	@NotNull
	@Size(min = 6, max = 6, message = "{commerce.error.int.size}")		
	private String pincode;

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * @param pincode
	 *            the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the addressType
	 */
	public String getAddressType() {
		return addressType;
	}

	/**
	 * @param addressType
	 *            the addressType to set
	 */
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	/**
	 * @return the addressId
	 */
	public BigInteger getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId
	 *            the addressId to set
	 */
	public void setAddressId(BigInteger addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the primary
	 */
	public String getPrimary() {
		return primary;
	}

	/**
	 * @param primary
	 *            the primary to set
	 */
	public void setPrimary(String primary) {
		this.primary = primary;
	}

}
