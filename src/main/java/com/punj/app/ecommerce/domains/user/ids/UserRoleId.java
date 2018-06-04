package com.punj.app.ecommerce.domains.user.ids;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.punj.app.ecommerce.domains.user.Role;
import com.punj.app.ecommerce.domains.user.User;

@Embeddable
public class UserRoleId implements Serializable {

	private static final long serialVersionUID = -4051984069455702622L;

	@Column(name = "username")
	private String username;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@Column(name = "location_id")
	private Integer locationId;


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

}
