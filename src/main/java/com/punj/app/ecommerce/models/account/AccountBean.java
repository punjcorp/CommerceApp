/**
 * 
 */
package com.punj.app.ecommerce.models.account;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author admin
 *
 */
public class AccountBean implements Serializable {

	@NotNull(message = "{commerce.error.string.empty}")
	@Size(min = 2, max = 50, message = "{commerce.error.string.size}")
	private String username;
	private String orgUsername;

	@NotNull(message = "{commerce.error.string.empty}")
	@Size(min = 2, max = 50, message = "{commerce.error.string.size}")
	private String firstName;

	@NotNull(message = "{commerce.error.string.empty}")
	@Size(min = 2, max = 50, message = "{commerce.error.string.size}")
	private String lastName;

	private BigInteger passwordId;

	@NotNull(message = "{commerce.error.string.empty}")
	@Size(min = 2, max = 30, message = "{commerce.error.string.size}")
	private String password;
	@NotNull(message = "{commerce.error.string.empty}")
	@Size(min = 2, max = 30, message = "{commerce.error.string.size}")
	private String confirmPassword;

	@NotEmpty(message = "{commerce.error.option.empty}")
	private Integer[] selectedLocationIds;
	
	@NotNull(message = "{commerce.error.option.empty}")
	private Integer defaultLocationId;

	private Boolean isUserExisting;

	private String status;
	private String displayStatus;
	private String email;
	private String phone;
	private String phone2;

	private String photoURL;
	private String photoType;
	private MultipartFile imageData;
	private String baseEncodedImage;

	private Integer loginCount;
	private String createdBy;
	private LocalDateTime createdDate;

	private String modifiedBy;
	private LocalDateTime modifiedDate;

	@NotNull(message = "{commerce.error.option.empty}")
	private Integer roleId;
	private String roleName;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
	 * @return the photoURL
	 */
	public String getPhotoURL() {
		return photoURL;
	}

	/**
	 * @param photoURL
	 *            the photoURL to set
	 */
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	/**
	 * @return the photoType
	 */
	public String getPhotoType() {
		return photoType;
	}

	/**
	 * @param photoType
	 *            the photoType to set
	 */
	public void setPhotoType(String photoType) {
		this.photoType = photoType;
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
	 * @return the loginCount
	 */
	public Integer getLoginCount() {
		return loginCount;
	}

	/**
	 * @param loginCount
	 *            the loginCount to set
	 */
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the selectedLocationIds
	 */
	public Integer[] getSelectedLocationIds() {
		return selectedLocationIds;
	}

	/**
	 * @param selectedLocationIds
	 *            the selectedLocationIds to set
	 */
	public void setSelectedLocationIds(Integer[] selectedLocationIds) {
		this.selectedLocationIds = selectedLocationIds;
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
	 * @return the displayStatus
	 */
	public String getDisplayStatus() {
		return displayStatus;
	}

	/**
	 * @param displayStatus
	 *            the displayStatus to set
	 */
	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}

	/**
	 * @return the isUserExisting
	 */
	public Boolean getIsUserExisting() {
		return isUserExisting;
	}

	/**
	 * @param isUserExisting
	 *            the isUserExisting to set
	 */
	public void setIsUserExisting(Boolean isUserExisting) {
		this.isUserExisting = isUserExisting;
	}

	/**
	 * @return the passwordId
	 */
	public BigInteger getPasswordId() {
		return passwordId;
	}

	/**
	 * @param passwordId
	 *            the passwordId to set
	 */
	public void setPasswordId(BigInteger passwordId) {
		this.passwordId = passwordId;
	}

	/**
	 * @return the orgUsername
	 */
	public String getOrgUsername() {
		return orgUsername;
	}

	/**
	 * @param orgUsername
	 *            the orgUsername to set
	 */
	public void setOrgUsername(String orgUsername) {
		this.orgUsername = orgUsername;
	}

	/**
	 * @return the defaultLocationId
	 */
	public Integer getDefaultLocationId() {
		return defaultLocationId;
	}

	/**
	 * @param defaultLocationId the defaultLocationId to set
	 */
	public void setDefaultLocationId(Integer defaultLocationId) {
		this.defaultLocationId = defaultLocationId;
	}

}
