/**
 * 
 */
package com.punj.app.ecommerce.domains.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;

import com.punj.app.ecommerce.domains.order.ids.OrderItemId;

/**
 * @author admin
 *
 */
@Entity
@Indexed
@Table(name = "purchase_order_items")
public class OrderItem implements Serializable {
	@EmbeddedId
	@FieldBridge(impl = OrderItemFieldBridge.class)
	@DocumentId
	private OrderItemId orderItemId;

	@Column(name = "ordered_qty")
	private Integer orderedQty;

	// Estimated Amount
	@Column(name = "cost_amount")
	private BigDecimal costAmount = new BigDecimal("0.0");
	@Column(name = "total_cost")
	private BigDecimal totalCost = new BigDecimal("0.0");

	@Column(name = "delievered_qty")
	private Integer delieveredQty = 0;
	@Column(name = "delievered_date")
	private LocalDateTime delieveredDate;

	// Actual Amounts
	@Column(name = "cost_actual_amount")
	private BigDecimal actualUnitCost = new BigDecimal("0.0");
	@Column(name = "total_actual_cost")
	private BigDecimal totalActualCost = new BigDecimal("0.0");
	@Column(name = "discount_amount")
	private BigDecimal discountAmount = new BigDecimal("0.0");
	@Column(name = "tax_amount")
	private BigDecimal taxAmount = new BigDecimal("0.0");
	@Column(name = "total_Actual_amount")
	private BigDecimal actualTotalAmount = new BigDecimal("0.0");

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
	 * @return the totalCost
	 */
	public BigDecimal getTotalCost() {
		return totalCost;
	}

	/**
	 * @param totalCost
	 *            the totalCost to set
	 */
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * @return the costAmount
	 */
	public BigDecimal getCostAmount() {
		return costAmount;
	}

	/**
	 * @param costAmount
	 *            the costAmount to set
	 */
	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	/**
	 * @return the discountAmount
	 */
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	/**
	 * @param discountAmount
	 *            the discountAmount to set
	 */
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	/**
	 * @return the orderedQty
	 */
	public Integer getOrderedQty() {
		return orderedQty;
	}

	/**
	 * @param orderedQty
	 *            the orderedQty to set
	 */
	public void setOrderedQty(Integer orderedQty) {
		this.orderedQty = orderedQty;
	}

	/**
	 * @return the delieveredQty
	 */
	public Integer getDelieveredQty() {
		return delieveredQty;
	}

	/**
	 * @param delieveredQty
	 *            the delieveredQty to set
	 */
	public void setDelieveredQty(Integer delieveredQty) {
		this.delieveredQty = delieveredQty;
	}

	/**
	 * @return the delieveredDate
	 */
	public LocalDateTime getDelieveredDate() {
		return delieveredDate;
	}

	/**
	 * @param delieveredDate
	 *            the delieveredDate to set
	 */
	public void setDelieveredDate(LocalDateTime delieveredDate) {
		this.delieveredDate = delieveredDate;
	}

	/**
	 * @return the actualUnitCost
	 */
	public BigDecimal getActualUnitCost() {
		return actualUnitCost;
	}

	/**
	 * @param actualUnitCost
	 *            the actualUnitCost to set
	 */
	public void setActualUnitCost(BigDecimal actualUnitCost) {
		this.actualUnitCost = actualUnitCost;
	}

	/**
	 * @return the totalActualCost
	 */
	public BigDecimal getTotalActualCost() {
		return totalActualCost;
	}

	/**
	 * @param totalActualCost
	 *            the totalActualCost to set
	 */
	public void setTotalActualCost(BigDecimal totalActualCost) {
		this.totalActualCost = totalActualCost;
	}

	/**
	 * @return the taxAmount
	 */
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount
	 *            the taxAmount to set
	 */
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * @return the actualTotalAmount
	 */
	public BigDecimal getActualTotalAmount() {
		return actualTotalAmount;
	}

	/**
	 * @param actualTotalAmount
	 *            the actualTotalAmount to set
	 */
	public void setActualTotalAmount(BigDecimal actualTotalAmount) {
		this.actualTotalAmount = actualTotalAmount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualTotalAmount == null) ? 0 : actualTotalAmount.hashCode());
		result = prime * result + ((actualUnitCost == null) ? 0 : actualUnitCost.hashCode());
		result = prime * result + ((costAmount == null) ? 0 : costAmount.hashCode());
		result = prime * result + ((delieveredDate == null) ? 0 : delieveredDate.hashCode());
		result = prime * result + ((delieveredQty == null) ? 0 : delieveredQty.hashCode());
		result = prime * result + ((discountAmount == null) ? 0 : discountAmount.hashCode());
		result = prime * result + ((orderItemId == null) ? 0 : orderItemId.hashCode());
		result = prime * result + ((orderedQty == null) ? 0 : orderedQty.hashCode());
		result = prime * result + ((taxAmount == null) ? 0 : taxAmount.hashCode());
		result = prime * result + ((totalActualCost == null) ? 0 : totalActualCost.hashCode());
		result = prime * result + ((totalCost == null) ? 0 : totalCost.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OrderItem other = (OrderItem) obj;
		if (actualTotalAmount == null) {
			if (other.actualTotalAmount != null) {
				return false;
			}
		} else if (!actualTotalAmount.equals(other.actualTotalAmount)) {
			return false;
		}
		if (actualUnitCost == null) {
			if (other.actualUnitCost != null) {
				return false;
			}
		} else if (!actualUnitCost.equals(other.actualUnitCost)) {
			return false;
		}
		if (costAmount == null) {
			if (other.costAmount != null) {
				return false;
			}
		} else if (!costAmount.equals(other.costAmount)) {
			return false;
		}
		if (delieveredDate == null) {
			if (other.delieveredDate != null) {
				return false;
			}
		} else if (!delieveredDate.equals(other.delieveredDate)) {
			return false;
		}
		if (delieveredQty == null) {
			if (other.delieveredQty != null) {
				return false;
			}
		} else if (!delieveredQty.equals(other.delieveredQty)) {
			return false;
		}
		if (discountAmount == null) {
			if (other.discountAmount != null) {
				return false;
			}
		} else if (!discountAmount.equals(other.discountAmount)) {
			return false;
		}
		if (orderItemId == null) {
			if (other.orderItemId != null) {
				return false;
			}
		} else if (!orderItemId.equals(other.orderItemId)) {
			return false;
		}
		if (orderedQty == null) {
			if (other.orderedQty != null) {
				return false;
			}
		} else if (!orderedQty.equals(other.orderedQty)) {
			return false;
		}
		if (taxAmount == null) {
			if (other.taxAmount != null) {
				return false;
			}
		} else if (!taxAmount.equals(other.taxAmount)) {
			return false;
		}
		if (totalActualCost == null) {
			if (other.totalActualCost != null) {
				return false;
			}
		} else if (!totalActualCost.equals(other.totalActualCost)) {
			return false;
		}
		if (totalCost == null) {
			if (other.totalCost != null) {
				return false;
			}
		} else if (!totalCost.equals(other.totalCost)) {
			return false;
		}
		return true;
	}



}