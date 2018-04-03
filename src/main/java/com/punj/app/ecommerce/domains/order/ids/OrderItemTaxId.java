/**
 * 
 */
package com.punj.app.ecommerce.domains.order.ids;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author admin
 *
 */
@Embeddable
public class OrderItemTaxId implements Serializable {

	private static final long serialVersionUID = -1530584370699949136L;

	private OrderItemId orderItemId;

	@Column(name = "tax_group_id")
	private Integer taxGroupId;

	@Column(name = "tax_rate_rule_id")
	private Integer taxRateRuleId;

	/**
	 * @return the orderItemId
	 */
	public OrderItemId getOrderItemId() {
		return orderItemId;
	}

	/**
	 * @param orderItemId
	 *            the orderItemId to set
	 */
	public void setOrderItemId(OrderItemId orderItemId) {
		this.orderItemId = orderItemId;
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

}
