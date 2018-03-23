package com.punj.app.ecommerce.domains.transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.transaction.ids.NoSaleId;

@Entity
@Table(name = "txn_no_sale_tender")
public class NoSaleTender implements Serializable {

	private static final long serialVersionUID = -5961604433963037857L;

	@EmbeddedId
	private NoSaleId noSaleId;

	private BigDecimal amount;

	@Column(name = "to_account_no")
	private String toAccountNo;
	@Column(name = "to_bank_name")
	private String toBankName;
	@Column(name = "to_bank_branch")
	private String toBankBranch;
	@Column(name = "to_payee_name")
	private String toPayeeName;
	@Column(name = "to_payee_phone")
	private String toPayeePhone;

	@Column(name = "created_by")
	private String createdBy;

	/**
	 * @return the noSaleId
	 */
	public NoSaleId getNoSaleId() {
		return noSaleId;
	}

	/**
	 * @param noSaleId
	 *            the noSaleId to set
	 */
	public void setNoSaleId(NoSaleId noSaleId) {
		this.noSaleId = noSaleId;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the toAccountNo
	 */
	public String getToAccountNo() {
		return toAccountNo;
	}

	/**
	 * @param toAccountNo
	 *            the toAccountNo to set
	 */
	public void setToAccountNo(String toAccountNo) {
		this.toAccountNo = toAccountNo;
	}

	/**
	 * @return the toBankName
	 */
	public String getToBankName() {
		return toBankName;
	}

	/**
	 * @param toBankName
	 *            the toBankName to set
	 */
	public void setToBankName(String toBankName) {
		this.toBankName = toBankName;
	}

	/**
	 * @return the toBankBranch
	 */
	public String getToBankBranch() {
		return toBankBranch;
	}

	/**
	 * @param toBankBranch
	 *            the toBankBranch to set
	 */
	public void setToBankBranch(String toBankBranch) {
		this.toBankBranch = toBankBranch;
	}

	/**
	 * @return the toPayeeName
	 */
	public String getToPayeeName() {
		return toPayeeName;
	}

	/**
	 * @param toPayeeName
	 *            the toPayeeName to set
	 */
	public void setToPayeeName(String toPayeeName) {
		this.toPayeeName = toPayeeName;
	}

	/**
	 * @return the toPayeePhone
	 */
	public String getToPayeePhone() {
		return toPayeePhone;
	}

	/**
	 * @param toPayeePhone
	 *            the toPayeePhone to set
	 */
	public void setToPayeePhone(String toPayeePhone) {
		this.toPayeePhone = toPayeePhone;
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


}
