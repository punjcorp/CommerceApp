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
@Table(name = "global_config")
public class AppConfig implements Serializable {

	private static final long serialVersionUID = 389310917558808668L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "conf_id", updatable = false, nullable = false)
	private Integer confId;

	private String name;

	@Column(name = "conf_key")
	private String confKey;

	@Column(name = "conf_value")
	private String confVal;

	/**
	 * @return the confId
	 */
	public Integer getConfId() {
		return confId;
	}

	/**
	 * @param confId
	 *            the confId to set
	 */
	public void setConfId(Integer confId) {
		this.confId = confId;
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
	 * @return the confKey
	 */
	public String getConfKey() {
		return confKey;
	}

	/**
	 * @param confKey
	 *            the confKey to set
	 */
	public void setConfKey(String confKey) {
		this.confKey = confKey;
	}

	/**
	 * @return the confVal
	 */
	public String getConfVal() {
		return confVal;
	}

	/**
	 * @param confVal
	 *            the confVal to set
	 */
	public void setConfVal(String confVal) {
		this.confVal = confVal;
	}

}
