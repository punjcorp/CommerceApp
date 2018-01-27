package com.punj.app.ecommerce.domains.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.user.ids.PasswordId;

@Entity
@Table(name = "user_password")
public class Password implements Serializable {

	private static final long serialVersionUID = 5614292601323207689L;

	@EmbeddedId
	private PasswordId passwordId;
	@Column(name = "modified_by")
	private String modifiedBy;
	private String status;

	public Password() {
		this.passwordId = new PasswordId();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((passwordId == null) ? 0 : passwordId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Password other = (Password) obj;
		if (passwordId == null) {
			if (other.passwordId != null) {
				return false;
			}
		} else if (!passwordId.equals(other.passwordId)) {
			return false;
		}
		return true;
	}

}
