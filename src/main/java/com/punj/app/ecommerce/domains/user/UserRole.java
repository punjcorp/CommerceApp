package com.punj.app.ecommerce.domains.user;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.user.ids.UserRoleId;

@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {

	private static final long serialVersionUID = -6844843078301930086L;

	@EmbeddedId
	private UserRoleId userRoleId;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "begin_date")
	private LocalDateTime beginDate;
	@Column(name = "end_date")
	private LocalDateTime endDate;
	/**
	 * @return the userRoleId
	 */
	public UserRoleId getUserRoleId() {
		return userRoleId;
	}
	/**
	 * @param userRoleId the userRoleId to set
	 */
	public void setUserRoleId(UserRoleId userRoleId) {
		this.userRoleId = userRoleId;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
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
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the beginDate
	 */
	public LocalDateTime getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(LocalDateTime beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return the endDate
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	
	
}
