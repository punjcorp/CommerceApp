/**
 * 
 */
package com.punj.app.ecommerce.domains.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "gst_state_codes")
public class State implements Serializable {

	private static final long serialVersionUID = -5932589071281542750L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer stateId;

	@Column(name = "state_code")
	private String stateCode;

	@Column(name = "state_name")
	private String stateName;
	@Column(name = "state_name_short")
	private String stateShortName;

	/**
	 * @return the stateId
	 */
	public Integer getStateId() {
		return stateId;
	}

	/**
	 * @param stateId
	 *            the stateId to set
	 */
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode
	 *            the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName
	 *            the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return the stateShortName
	 */
	public String getStateShortName() {
		return stateShortName;
	}

	/**
	 * @param stateShortName
	 *            the stateShortName to set
	 */
	public void setStateShortName(String stateShortName) {
		this.stateShortName = stateShortName;
	}

}
