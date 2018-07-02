/**
 * 
 */
package com.punj.app.ecommerce.models.common;

import java.io.Serializable;

/**
 * @author admin
 *
 */
public class AJAXResponseBean implements Serializable {

	private static final long serialVersionUID = 1404665795181915401L;

	private String status;
	private String statusMsg;

	private Object resultObj;

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

	/**
	 * @return the statusMsg
	 */
	public String getStatusMsg() {
		return statusMsg;
	}

	/**
	 * @param statusMsg
	 *            the statusMsg to set
	 */
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	/**
	 * @return the resultObj
	 */
	public Object getResultObj() {
		return resultObj;
	}

	/**
	 * @param resultObj
	 *            the resultObj to set
	 */
	public void setResultObj(Object resultObj) {
		this.resultObj = resultObj;
	}

}
