/**
 * 
 */
package com.punj.app.ecommerce.domains.user.ids;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author admin
 *
 */
@Embeddable
public class PasswordId implements Serializable {

	private String username;
	private String password;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	public PasswordId() {

	}

	public PasswordId(String username, String password, LocalDateTime modifiedDate) {
		this.username = username;
		this.password = password;
		this.modifiedDate = modifiedDate;
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
		return this.modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
