/**
 * 
 */
package com.punj.app.ecommerce.models.customer;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.models.financials.AccountHeadBean;

/**
 * @author admin
 *
 */
public class CustomerBean {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private BigInteger customerId;
	@NotNull
	@Size(min = 2, max = 80, message = "{commerce.error.string.size}", groups = { ValidationGroup.ValidationGroupTwo.class })
	private String name;

	@NotNull(groups = { ValidationGroup.ValidationGroupOne.class })
	private String customerSearchText;
	private String customerType;
	@NotNull
	@Size(min = 7, max = 10, message = "{commerce.error.int.size}", groups = { ValidationGroup.ValidationGroupTwo.class })
	private String phone;

	@Size(min = 6, max = 80, message = "{commerce.error.string.size}", groups = { ValidationGroup.ValidationGroupTwo.class })
	@Pattern(regexp = EMAIL_PATTERN, message = "{commerce.error.email}", groups = { ValidationGroup.ValidationGroupTwo.class })
	private String email;

	private String gstNo;

	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;

	private Boolean searchAccount;
	private List<AccountHeadBean> customerAccounts;

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

}
