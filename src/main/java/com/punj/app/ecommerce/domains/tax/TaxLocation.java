/**
 * 
 */
package com.punj.app.ecommerce.domains.tax;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.common.Location;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "tax_location")
public class TaxLocation implements Serializable {

	private static final long serialVersionUID = -3696212519525485092L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tax_location_id", updatable = false, nullable = false)
	private Integer taxLocationId;
	private String name;
	private String description;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tax_location_mapping", joinColumns = @JoinColumn(name = "tax_location_id"), inverseJoinColumns = @JoinColumn(name = "location_id"))
	private List<Location> locations;

	/**
	 * @return the taxLocationId
	 */
	public Integer getTaxLocationId() {
		return taxLocationId;
	}

	/**
	 * @param taxLocationId
	 *            the taxLocationId to set
	 */
	public void setTaxLocationId(Integer taxLocationId) {
		this.taxLocationId = taxLocationId;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the locations
	 */
	public List<Location> getLocations() {
		return locations;
	}

	/**
	 * @param locations
	 *            the locations to set
	 */
	public void setLocations(List<Location> locations) {
		this.locations = locations;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((taxLocationId == null) ? 0 : taxLocationId.hashCode());
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
		TaxLocation other = (TaxLocation) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (taxLocationId == null) {
			if (other.taxLocationId != null) {
				return false;
			}
		} else if (!taxLocationId.equals(other.taxLocationId)) {
			return false;
		}
		return true;
	}

}
