/**
 * 
 */
package com.punj.app.ecommerce.models.order;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author admin
 *
 */
public class OrderBillBean {

	private String billNo;
	private LocalDateTime billDate;
	private String billFileURL;
	private MultipartFile billFile;

	/**
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * @param billNo
	 *            the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * @return the billDate
	 */
	public LocalDateTime getBillDate() {
		return billDate;
	}

	/**
	 * @param billDate
	 *            the billDate to set
	 */
	public void setBillDate(LocalDateTime billDate) {
		this.billDate = billDate;
	}

	/**
	 * @return the billFileURL
	 */
	public String getBillFileURL() {
		return billFileURL;
	}

	/**
	 * @param billFileURL
	 *            the billFileURL to set
	 */
	public void setBillFileURL(String billFileURL) {
		this.billFileURL = billFileURL;
	}

	/**
	 * @return the billFile
	 */
	public MultipartFile getBillFile() {
		return billFile;
	}

	/**
	 * @param billFile
	 *            the billFile to set
	 */
	public void setBillFile(MultipartFile billFile) {
		this.billFile = billFile;
	}

}
