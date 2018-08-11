/**
 * 
 */
package com.punj.app.ecommerce.domains.order.returns;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "po_return_items_tax")
public class OrderReturnItemTax implements Serializable {

	private static final long serialVersionUID = 8359302209684848833L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "po_return_item_tax_id", updatable = false, nullable = false)
	private BigInteger returnItemTaxId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "order_return_item_id")
	private OrderReturnItem orderReturnItem;

	@Column(name = "tax_group_id")
	private Integer taxGroupId;

	@Column(name = "tax_rate_rule_id")
	private Integer taxRateRuleId;

	@Column(name = "taxable_amount")
	private BigDecimal taxableAmt;

	@Column(name = "tax_code")
	private String taxCode;

	@Column(name = "tax_amount")
	private BigDecimal taxRuleAmt;
	@Column(name = "tax_percentage")
	private BigDecimal taxRulePercentage;

	/**
	 * @return the returnItemTaxId
	 */
	public BigInteger getReturnItemTaxId() {
		return returnItemTaxId;
	}

	/**
	 * @param returnItemTaxId
	 *            the returnItemTaxId to set
	 */
	public void setReturnItemTaxId(BigInteger returnItemTaxId) {
		this.returnItemTaxId = returnItemTaxId;
	}

	/**
	 * @return the orderReturnItem
	 */
	public OrderReturnItem getOrderReturnItem() {
		return orderReturnItem;
	}

	/**
	 * @param orderReturnItem
	 *            the orderReturnItem to set
	 */
	public void setOrderReturnItem(OrderReturnItem orderReturnItem) {
		this.orderReturnItem = orderReturnItem;
	}

	/**
	 * @return the taxGroupId
	 */
	public Integer getTaxGroupId() {
		return taxGroupId;
	}

	/**
	 * @param taxGroupId
	 *            the taxGroupId to set
	 */
	public void setTaxGroupId(Integer taxGroupId) {
		this.taxGroupId = taxGroupId;
	}

	/**
	 * @return the taxRateRuleId
	 */
	public Integer getTaxRateRuleId() {
		return taxRateRuleId;
	}

	/**
	 * @param taxRateRuleId
	 *            the taxRateRuleId to set
	 */
	public void setTaxRateRuleId(Integer taxRateRuleId) {
		this.taxRateRuleId = taxRateRuleId;
	}

	/**
	 * @return the taxableAmt
	 */
	public BigDecimal getTaxableAmt() {
		return taxableAmt;
	}

	/**
	 * @param taxableAmt
	 *            the taxableAmt to set
	 */
	public void setTaxableAmt(BigDecimal taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	/**
	 * @return the taxCode
	 */
	public String getTaxCode() {
		return taxCode;
	}

	/**
	 * @param taxCode
	 *            the taxCode to set
	 */
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	/**
	 * @return the taxRuleAmt
	 */
	public BigDecimal getTaxRuleAmt() {
		return taxRuleAmt;
	}

	/**
	 * @param taxRuleAmt
	 *            the taxRuleAmt to set
	 */
	public void setTaxRuleAmt(BigDecimal taxRuleAmt) {
		this.taxRuleAmt = taxRuleAmt;
	}

	/**
	 * @return the taxRulePercentage
	 */
	public BigDecimal getTaxRulePercentage() {
		return taxRulePercentage;
	}

	/**
	 * @param taxRulePercentage
	 *            the taxRulePercentage to set
	 */
	public void setTaxRulePercentage(BigDecimal taxRulePercentage) {
		this.taxRulePercentage = taxRulePercentage;
	}

}
