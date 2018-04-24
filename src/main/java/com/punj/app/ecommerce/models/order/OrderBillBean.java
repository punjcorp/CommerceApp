/**
 * 
 */
package com.punj.app.ecommerce.models.order;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author admin
 *
 */
public class OrderBillBean {

	private BigInteger orderBillId;
	private BigInteger orderId;
	private String billNo;
	private LocalDateTime billDate;
	private String billFileURL;
	private String billFileType;
	private MultipartFile billFile;
	private MultipartFile hiddenBill;

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

	/**
	 * @return the orderId
	 */
	public BigInteger getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(BigInteger orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the orderBillId
	 */
	public BigInteger getOrderBillId() {
		return orderBillId;
	}

	/**
	 * @param orderBillId
	 *            the orderBillId to set
	 */
	public void setOrderBillId(BigInteger orderBillId) {
		this.orderBillId = orderBillId;
	}

	/**
	 * @return the billFileType
	 */
	public String getBillFileType() {
		return billFileType;
	}

	/**
	 * @param billFileType the billFileType to set
	 */
	public void setBillFileType(String billFileType) {
		this.billFileType = billFileType;
	}

	/**
	 * @return the hiddenBill
	 */
	public MultipartFile getHiddenBill() {
		return hiddenBill;
	}

	/**
	 * @param hiddenBill the hiddenBill to set
	 */
	public void setHiddenBill(MultipartFile hiddenBill) {
		this.hiddenBill = hiddenBill;
	}

}
