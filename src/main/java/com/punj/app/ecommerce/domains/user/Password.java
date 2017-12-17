package com.punj.app.ecommerce.domains.user;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.user.ids.PasswordId;

@Entity
@Table(name = "user_password")
public class Password {
	@EmbeddedId
	private PasswordId passwordId;
	@Column(name = "modified_by")
	private String modifiedBy;
	private String status;


	public Password() {
		this.passwordId=new PasswordId();
	}

	public Password(PasswordId passwordId, String modifiedBy, String status) {
		this.passwordId = passwordId;
		this.modifiedBy = modifiedBy;
		this.status = status;
	}

	/**
	 * @return the passwordId
	 */
	public PasswordId getPasswordId() {
		return passwordId;
	}

	/**
	 * @param passwordId
	 *            the passwordId to set
	 */
	public void setPasswordId(PasswordId passwordId) {
		this.passwordId = passwordId;
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
