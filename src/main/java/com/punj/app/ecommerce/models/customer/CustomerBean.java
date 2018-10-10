/**
 * 
 */
package com.punj.app.ecommerce.models.customer;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.models.financials.AccountHeadBean;

/**
 * @author admin
 *
 */
public class CustomerBean {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@NotNull(groups = { ValidationGroup.ValidationGroupOne.class })
	private String customerSearchText;
	private String customerType;

	private BigInteger customerId;
	@NotNull
	@Size(min = 2, max = 80, message = "{commerce.error.string.size}")
	private String name;

	/*@NotNull
	@Size(min = 7, max = 15, message = "{commerce.error.int.size}")*/
	private String phone;

	private String phone2;

	private String gstNo;
	private String panNo;
	private String stateCode;

	//@Size(min = 6, max = 80, message = "{commerce.error.string.size}")
	//@Pattern(regexp = EMAIL_PATTERN, message = "{commerce.error.email}")
	private String email;

	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;

	private Boolean searchAccount;
	private List<AccountHeadBean> customerAccounts;

	@Valid
	@NotEmpty(message = "{commerce.error.no.address}")
	private List<AddressBean> addresses;
	private AddressBean primaryAddress;
	private AddressBean shippingAddress;

	private BigInteger billingAddressId;
	private BigInteger shippingAddressId;

	@NotNull(message = "{commerce.error.select.empty}")
	private Integer primaryAddressIndex;

	/**
	 * @return the customerId
	 */
	public BigInteger getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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

	public List<AccountHeadBean> getCustomerAccounts() {
		return customerAccounts;
	}

	public void setCustomerAccounts(List<AccountHeadBean> customerAccounts) {
		this.customerAccounts = customerAccounts;
	}

	public Boolean getSearchAccount() {
		return searchAccount;
	}

	public void setSearchAccount(Boolean searchAccount) {
		this.searchAccount = searchAccount;
	}

	/**
	 * @return the phone2
	 */
	public String getPhone2() {
		return phone2;
	}

	/**
	 * @param phone2
	 *            the phone2 to set
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	/**
	 * @return the gstNo
	 */
	public String getGstNo() {
		return gstNo;
	}

	/**
	 * @param gstNo
	 *            the gstNo to set
	 */
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	/**
	 * @return the panNo
	 */
	public String getPanNo() {
		return panNo;
	}

	/**
	 * @param panNo
	 *            the panNo to set
	 */
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	/**
	 * @return the addresses
	 */
	public List<AddressBean> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 *            the addresses to set
	 */
	public void setAddresses(List<AddressBean> addresses) {
		this.addresses = addresses;
	}

	/**
	 * @return the primaryAddress
	 */
	public AddressBean getPrimaryAddress() {
		return primaryAddress;
	}

	/**
	 * @param primaryAddress
	 *            the primaryAddress to set
	 */
	public void setPrimaryAddress(AddressBean primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	/**
	 * @return the primaryAddressIndex
	 */
	public Integer getPrimaryAddressIndex() {
		return primaryAddressIndex;
	}

	/**
	 * @param primaryAddressIndex
	 *            the primaryAddressIndex to set
	 */
	public void setPrimaryAddressIndex(Integer primaryAddressIndex) {
		this.primaryAddressIndex = primaryAddressIndex;
	}

	/**
	 * @return the customerSearchText
	 */
	public String getCustomerSearchText() {
		return customerSearchText;
	}

	/**
	 * @param customerSearchText
	 *            the customerSearchText to set
	 */
	public void setCustomerSearchText(String customerSearchText) {
		this.customerSearchText = customerSearchText;
	}

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType
	 *            the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the billingAddressId
	 */
	public BigInteger getBillingAddressId() {
		return billingAddressId;
	}

	/**
	 * @param billingAddressId
	 *            the billingAddressId to set
	 */
	public void setBillingAddressId(BigInteger billingAddressId) {
		this.billingAddressId = billingAddressId;
	}

	/**
	 * @return the shippingAddressId
	 */
	public BigInteger getShippingAddressId() {
		return shippingAddressId;
	}

	/**
	 * @param shippingAddressId
	 *            the shippingAddressId to set
	 */
	public void setShippingAddressId(BigInteger shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	/**
	 * @return the shippingAddress
	 */
	public AddressBean getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress
	 *            the shippingAddress to set
	 */
	public void setShippingAddress(AddressBean shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

}
