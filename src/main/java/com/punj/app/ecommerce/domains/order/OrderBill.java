/**
 * 
 */
package com.punj.app.ecommerce.domains.order;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "purchase_order_bills")
public class OrderBill implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_bill_id", updatable = false, nullable = false)
	private BigInteger orderBillId;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "order_id")
	private Order order;

	@Column(name = "bill_no")
	private String billNo;

	@Column(name = "bill_date")
	private LocalDateTime billDate;

	@Lob
	@Column(name = "bill_data")
	private byte[] billData;

	@Column(name = "bill_file")
	private String billFileName;

	@Column(name = "bill_file_type")
	private String billFileType;

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
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

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
	 * @return the billData
	 */
	public byte[] getBillData() {
		return billData;
	}

	/**
	 * @param billData
	 *            the billData to set
	 */
	public void setBillData(byte[] billData) {
		this.billData = billData;
	}

	/**
	 * @return the billFileName
	 */
	public String getBillFileName() {
		return billFileName;
	}

	/**
	 * @param billFileName
	 *            the billFileName to set
	 */
	public void setBillFileName(String billFileName) {
		this.billFileName = billFileName;
	}

	/**
	 * @return the billFileType
	 */
	public String getBillFileType() {
		return billFileType;
	}

	/**
	 * @param billFileType
	 *            the billFileType to set
	 */
	public void setBillFileType(String billFileType) {
		this.billFileType = billFileType;
	}

}
