package com.punj.app.ecommerce.domains.user;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "address_master")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "address_id", updatable = false, nullable = false)
	private BigInteger addressId;
	@Column(name = "`primary`")
	private String primary;
	@Column(name = "address_type")
	private String addressType;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String pincode;

	public Address() {
	}

	public Address(String primary, String addressType, String address1, String address2, String city, String state,
			String country, String pincode) {
		this.primary = primary;
		this.addressType = addressType;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;

	}

	/**
	 * @return the addressId
	 */
	public BigInteger getAddressId() {
		return addressId;
	}

	/**
	 * @return the addressId
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

}
