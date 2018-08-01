package com.punj.app.ecommerce.domains.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Indexed
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1764456297095150169L;

	@Id
	@DocumentId
	private String username;
	@Field
	@Column(name = "first_name")
	private String firstname;
	@Field
	@Column(name = "last_name")
	private String lastname;
	@Field
	private String phone1;
	private String phone2;
	@Field
	private String email;
	@Column(name = "login_count")
	private Integer loginCount = 0;
	@Column(name = "default_location")
	private Integer defaultLocationId;
	@Field
	private String status;
	@Lob
	@Column
	private byte[] photo;

	@Column(name = "photo_url")
	private String photoURL;
	@Column(name = "photo_type")
	private String photoType;

	@Field
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	@OneToMany(mappedBy = "username", cascade = CascadeType.ALL)
	private List<Password> passwords;

	@OneToMany(mappedBy = "cardId.username", cascade = CascadeType.REFRESH)
	private List<Card> cards;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_address", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "address_id"))
	private List<Address> addresses;

	@OneToMany(mappedBy = "userRoleId.username", cascade = CascadeType.ALL)
	private List<UserRole> userRoles;

	public User() {

	}

	public User(String username, String firstname, String lastname, String phone1, String phone2, String email, Integer loginCount, String status, byte[] photo,
			String createdBy, LocalDateTime createdDate) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.email = email;
		this.loginCount = loginCount;
		this.status = status;
		this.photo = photo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;

	}

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
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
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
	 * @return the photo
	 */
	public byte[] getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(byte[] photo) {
		this.photo = photo;
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
	 * @return the passwords
	 */
	public List<Password> getPasswords() {
		return passwords;
	}

	/**
	 * @param passwords
	 *            the passwords to set
	 */
	public void setPasswords(List<Password> passwords) {
		this.passwords = passwords;
	}

	/**
	 * @return the cards
	 */
	public List<Card> getCards() {
		return cards;
	}

	/**
	 * @param cards
	 *            the cards to set
	 */
	public void setCards(List<Card> cards) {
		this.cards = cards;
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
	 * @return the addresses
	 */
	public List<Address> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 *            the addresses to set
	 */
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
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
	 * @return the userRoles
	 */
	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	/**
	 * @param userRoles
	 *            the userRoles to set
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
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
	 * @return the defaultLocationId
	 */
	public Integer getDefaultLocationId() {
		return defaultLocationId;
	}

	/**
	 * @param defaultLocationId
	 *            the defaultLocationId to set
	 */
	public void setDefaultLocationId(Integer defaultLocationId) {
		this.defaultLocationId = defaultLocationId;
	}

}
