package com.punj.app.ecommerce.domains.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	private String username;
	@Column(name = "first_name")
	private String firstname;
	@Column(name = "last_name")
	private String lastname;
	private String phone1;
	private String phone2;
	private String email;
	@Column(name = "login_count")
	private Integer loginCount = 0;
	private String status;
	@Lob
	@Column
	private byte[] photo;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@OneToMany( mappedBy = "passwordId.username", cascade = CascadeType.ALL)
	private List<Password> passwords;

	@OneToMany(mappedBy = "cardId.username", cascade = CascadeType.REFRESH)
	private List<Card> cards;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_address", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "address_id"))
	private List<Address> addresses;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	public User() {

	}

	public User(String username, String firstname, String lastname, String phone1, String phone2, String email,
			Integer loginCount, String status, byte[] photo, String createdBy, LocalDateTime createdDate) {
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
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
