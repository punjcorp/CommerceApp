/**
 * 
 */
package com.punj.app.ecommerce.services.common.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.transaction.Transaction;

/**
 * @author admin
 *
 */
public class LocationDTO implements Serializable {

	private static final long serialVersionUID = -2933033039640471216L;

	private List<Location> locations;
	private Map<Integer, Transaction> lastTxnStatus;
	private Map<Integer, LocalDateTime> lastSaleBDate;
	private Map<Integer, LocalDateTime> minDateForOpening;

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

	/**
	 * @return the lastTxnStatus
	 */
	public Map<Integer, Transaction> getLastTxnStatus() {
		return lastTxnStatus;
	}

	/**
	 * @param lastTxnStatus
	 *            the lastTxnStatus to set
	 */
	public void setLastTxnStatus(Map<Integer, Transaction> lastTxnStatus) {
		this.lastTxnStatus = lastTxnStatus;
	}

	/**
	 * @return the lastSaleBDate
	 */
	public Map<Integer, LocalDateTime> getLastSaleBDate() {
		return lastSaleBDate;
	}

	/**
	 * @param lastSaleBDate
	 *            the lastSaleBDate to set
	 */
	public void setLastSaleBDate(Map<Integer, LocalDateTime> lastSaleBDate) {
		this.lastSaleBDate = lastSaleBDate;
	}

	/**
	 * @return the minDateForOpening
	 */
	public Map<Integer, LocalDateTime> getMinDateForOpening() {
		return minDateForOpening;
	}

	/**
	 * @param minDateForOpening
	 *            the minDateForOpening to set
	 */
	public void setMinDateForOpening(Map<Integer, LocalDateTime> minDateForOpening) {
		this.minDateForOpening = minDateForOpening;
	}

}
