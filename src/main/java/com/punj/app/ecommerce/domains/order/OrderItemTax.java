/**
 * 
 */
package com.punj.app.ecommerce.domains.order;

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
@Table(name = "purchase_order_items_tax")
public class OrderItemTax implements Serializable {

	private static final long serialVersionUID = 6029863757343650795L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_item_tax_id", updatable = false, nullable = false)
	private BigInteger orderItemTaxId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "order_item_id")
	private OrderItem orderItem;

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

	@Column(name = "actual_taxable_amount")
	private BigDecimal actualTaxableAmt;

	@Column(name = "actual_tax_amount")
	private BigDecimal actualTaxRuleAmt;

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
	 * @return the actualTaxableAmt
	 */
	public BigDecimal getActualTaxableAmt() {
		return actualTaxableAmt;
	}

	/**
	 * @param actualTaxableAmt
	 *            the actualTaxableAmt to set
	 */
	public void setActualTaxableAmt(BigDecimal actualTaxableAmt) {
		this.actualTaxableAmt = actualTaxableAmt;
	}

	/**
	 * @return the actualTaxRuleAmt
	 */
	public BigDecimal getActualTaxRuleAmt() {
		return actualTaxRuleAmt;
	}

	/**
	 * @param actualTaxRuleAmt
	 *            the actualTaxRuleAmt to set
	 */
	public void setActualTaxRuleAmt(BigDecimal actualTaxRuleAmt) {
		this.actualTaxRuleAmt = actualTaxRuleAmt;
	}

	/**
	 * @return the orderItemTaxId
	 */
	public BigInteger getOrderItemTaxId() {
		return orderItemTaxId;
	}

	/**
	 * @param orderItemTaxId
	 *            the orderItemTaxId to set
	 */
	public void setOrderItemTaxId(BigInteger orderItemTaxId) {
		this.orderItemTaxId = orderItemTaxId;
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
	 * @return the orderItem
	 */
	public OrderItem getOrderItem() {
		return orderItem;
	}

	/**
	 * @param orderItem the orderItem to set
	 */
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	

}
