/**
 * 
 */
package com.punj.app.ecommerce.models.supplier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class SupplierBean {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private Integer supplierId;
	@NotNull
	@Size(min = 2, max = 80, message = "{commerce.error.string.size}")
	private String name;
	@NotNull
	@Size(min = 7, max = 10, message = "{commerce.error.int.size}")
	private String phone1;
	private String phone2;
	
	private String gstNo;
	
	@NotNull
	@Size(min = 6, max = 80, message = "{commerce.error.string.size}")
	@Pattern(regexp = EMAIL_PATTERN, message = "{commerce.error.email}")
	private String email;
	@Valid
	private List<AddressBean> addresses;

	private AddressBean primaryAddress;

	private List<SupplierItemBean> items;

	private Pager pager;

	@NotNull(message = "{commerce.error.select.empty}")
	private Integer primaryAddressIndex;
	
	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;

	public SupplierBean() {
		addresses = new ArrayList<AddressBean>();
		addresses.add(new AddressBean());

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
	 * @return the phone1
	 */
	public String getPhone1() {
		return phone1;
	}

	/**
	 * @param phone1
	 *            the phone1 to set
	 */
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
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
	 * @return the supplierId
	 */
	public Integer getSupplierId() {
		return supplierId;
	}

	/**
	 * @param supplierId
	 *            the supplierId to set
	 */
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
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
	 * @return the items
	 */
	public List<SupplierItemBean> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<SupplierItemBean> items) {
		this.items = items;
	}

	/**
	 * @return the primaryAddress
	 */
	public AddressBean getPrimaryAddress() {
		if (this.addresses != null && !this.addresses.isEmpty()) {
			return this.addresses.get(0);
		}
		return this.primaryAddress;
	}

	/**
	 * @param primaryAddress
	 *            the primaryAddress to set
	 */
	public void setPrimaryAddress(AddressBean primaryAddress) {
		this.primaryAddress = primaryAddress;
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
	 * @return the primaryAddressIndex
	 */
	public Integer getPrimaryAddressIndex() {
		return primaryAddressIndex;
	}

	/**
	 * @param primaryAddressIndex the primaryAddressIndex to set
	 */
	public void setPrimaryAddressIndex(Integer primaryAddressIndex) {
		this.primaryAddressIndex = primaryAddressIndex;
	}

	/**
	 * @return the gstNo
	 */
	public String getGstNo() {
		return gstNo;
	}

	/**
	 * @param gstNo the gstNo to set
	 */
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

}
