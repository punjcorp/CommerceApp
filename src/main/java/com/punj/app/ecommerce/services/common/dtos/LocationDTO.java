/**
 * 
 */
package com.punj.app.ecommerce.services.common.dtos;

import java.io.Serializable;
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

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public Map<Integer, Transaction> getLastTxnStatus() {
		return lastTxnStatus;
	}

	public void setLastTxnStatus(Map<Integer, Transaction> lastTxnStatus) {
		this.lastTxnStatus = lastTxnStatus;
	}

}
