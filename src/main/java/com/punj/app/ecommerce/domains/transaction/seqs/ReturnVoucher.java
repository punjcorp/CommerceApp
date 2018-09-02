/**
 * 
 */
package com.punj.app.ecommerce.domains.transaction.seqs;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

import com.punj.app.ecommerce.domains.transaction.Transaction;

/**
 * @author admin
 *
 */
@Entity
@Indexed
@Table(name = "return_txn_vouchers")
public class ReturnVoucher implements Serializable {

	private static final long serialVersionUID = 2480066782267477180L;

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "voucher_no", updatable = false, nullable = false)
	private BigInteger voucherNo;

	@Column(name = "location_id")
	private Integer locationId;

	@Column(name = "business_date")
	private LocalDateTime businessDate;

	@Column(name = "register")
	private Integer register;

	@Column(name = "txn_no")
	private Integer transactionSeq;

	/**
	 * @return the voucherNo
	 */
	public BigInteger getVoucherNo() {
		return voucherNo;
	}

	/**
	 * @param voucherNo
	 *            the voucherNo to set
	 */
	public void setVoucherNo(BigInteger voucherNo) {
		this.voucherNo = voucherNo;
	}

	/**
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the businessDate
	 */
	public LocalDateTime getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate the businessDate to set
	 */
	public void setBusinessDate(LocalDateTime businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return the register
	 */
	public Integer getRegister() {
		return register;
	}

	/**
	 * @param register the register to set
	 */
	public void setRegister(Integer register) {
		this.register = register;
	}

	/**
	 * @return the transactionSeq
	 */
	public Integer getTransactionSeq() {
		return transactionSeq;
	}

	/**
	 * @param transactionSeq the transactionSeq to set
	 */
	public void setTransactionSeq(Integer transactionSeq) {
		this.transactionSeq = transactionSeq;
	}


}
