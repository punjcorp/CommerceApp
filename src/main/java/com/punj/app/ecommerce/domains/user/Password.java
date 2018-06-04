package com.punj.app.ecommerce.domains.user;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_password")
public class Password implements Serializable {

	private static final long serialVersionUID = 5614292601323207689L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "password_id", updatable = false, nullable = false)
	private BigInteger passwordId;

	private String username;
	private String password;

	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;
	@Column(name = "modified_by")
	private String modifiedBy;

	private String status;

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

}
