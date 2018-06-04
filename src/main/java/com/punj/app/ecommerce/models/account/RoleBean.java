/**
 * 
 */
package com.punj.app.ecommerce.models.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author admin
 *
 */
public class RoleBean {

	private Integer roleId;

	@NotNull(message = "{commerce.error.string.empty}")
	@Size(min = 2, max = 75, message = "{commerce.error.string.size}")
	private String name;

	@NotNull(message = "{commerce.error.string.empty}")
	@Size(min = 2, max = 200, message = "{commerce.error.string.size}")
	private String description;

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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
