/**
 * 
 */
package com.punj.app.ecommerce.domains.order;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.order.ids.OrderItemTaxId;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "purchase_order_items_tax")
public class OrderItemTax implements Serializable {

	private static final long serialVersionUID = 6029863757343650795L;

	@EmbeddedId
	private OrderItemTaxId orderItemTaxId;

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
	 * @return the orderItemTaxId
	 */
	public OrderItemTaxId getOrderItemTaxId() {
		return orderItemTaxId;
	}

	/**
	 * @param orderItemTaxId
	 *            the orderItemTaxId to set
	 */
	public void setOrderItemTaxId(OrderItemTaxId orderItemTaxId) {
		this.orderItemTaxId = orderItemTaxId;
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
	 * @param actualTaxableAmt the actualTaxableAmt to set
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
	 * @param actualTaxRuleAmt the actualTaxRuleAmt to set
	 */
	public void setActualTaxRuleAmt(BigDecimal actualTaxRuleAmt) {
		this.actualTaxRuleAmt = actualTaxRuleAmt;
	}

}
