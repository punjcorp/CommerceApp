/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.domains.order.OrderItemTax;
import com.punj.app.ecommerce.domains.order.ids.OrderItemId;
import com.punj.app.ecommerce.domains.order.ids.OrderItemTaxId;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.OrderItemBean;
import com.punj.app.ecommerce.models.order.OrderReportBean;
import com.punj.app.ecommerce.models.supplier.SupplierBean;

/**
 * @author admin
 *
 */
public class OrderTransformer {

	private static final Logger logger = LogManager.getLogger();

	private OrderTransformer() {
		throw new IllegalStateException("OrderTransformer class");
	}

	public static OrderBean transformOrder(Order order) {
		OrderBean orderBean = new OrderBean();

		orderBean.setOrderId(order.getOrderId());
		orderBean.setLocationId(order.getLocationId());
		orderBean.setStatus(order.getStatus());

		SupplierBean supplierBean = SupplierTransformer.transformSupplier(order.getSupplier());
		orderBean.setSupplier(supplierBean);
		orderBean.setSupplierId(order.getSupplier().getSupplierId());

		orderBean.setCreatedBy(order.getCreatedBy());
		orderBean.setCreatedDate(order.getCreatedDate());

		orderBean.setEstimatedCost(order.getEstimatedCost());

		orderBean.setDiscountAmount(order.getDiscountAmount());
		orderBean.setTaxAmount(order.getTaxAmount());
		orderBean.setTotalAmount(order.getTotalAmount());
		orderBean.setPaidAmount(order.getPaidAmount());

		List<OrderItemBean> orderItemBeans = OrderTransformer.transformOrderItems(order.getOrderItems(),orderBean);
		orderBean.setOrderItems(orderItemBeans);

		logger.info("The order details has been transformed successfully");

		return orderBean;
	}

	public static List<OrderItemBean> transformOrderItems(List<OrderItem> orderItems, OrderBean orderBean) {

		List<OrderItemBean> orderItemBeans = new ArrayList<>(orderItems.size());
		OrderItemBean orderItemBean;
		
		BigDecimal sgstTaxAmt=BigDecimal.ZERO;
		BigDecimal cgstTaxAmt=BigDecimal.ZERO;
		BigDecimal igstTaxAmt=BigDecimal.ZERO;

		for (OrderItem orderItem : orderItems) {
			orderItemBean = OrderTransformer.transformOrderItem(orderItem);
			
			if(orderItemBean.getSgstTaxAmount()!=null) {
				sgstTaxAmt.add(orderItemBean.getSgstTaxAmount());
			}
			
			if(orderItemBean.getCgstTaxAmount()!=null) {
				cgstTaxAmt.add(orderItemBean.getCgstTaxAmount());
			}
			
			if(orderItemBean.getIgstTaxAmount()!=null) {
				igstTaxAmt.add(orderItemBean.getIgstTaxAmount());
			}
			
			orderItemBeans.add(orderItemBean);
		}
		orderBean.setCgstTaxAmount(cgstTaxAmt);
		orderBean.setSgstTaxAmount(sgstTaxAmt);
		orderBean.setIgstTaxAmount(igstTaxAmt);
		
		logger.info("The order item details list has been transformed successfully");
		return orderItemBeans;
	}

	public static OrderItemBean transformOrderItem(OrderItem orderItem) {
		OrderItemBean orderItemBean = new OrderItemBean();

		orderItemBean.setOrderId(orderItem.getOrderItemId().getOrder().getOrderId());
		orderItemBean.setItemId(orderItem.getOrderItemId().getItemId());
		orderItemBean.setUnitCost(orderItem.getUnitCost());
		orderItemBean.setOrderedQty(orderItem.getOrderedQty());
		orderItemBean.setCostAmount(orderItem.getCostAmount());

		orderItemBean.setDelieveredDate(orderItem.getDelieveredDate());
		orderItemBean.setDelieveredQty(orderItem.getDelieveredQty());
		orderItemBean.setTaxAmount(orderItem.getTaxAmount());
		orderItemBean.setTotalCost(orderItem.getTotalCost());

		orderItemBean.setActualCostAmount(orderItem.getActualCostAmount());
		orderItemBean.setActualDiscountAmount(orderItem.getActualDiscountAmount());
		orderItemBean.setActualTaxAmount(orderItem.getActualTaxAmount());
		orderItemBean.setActualTotalCost(orderItem.getActualTotalCost());
		orderItemBean.setActualUnitCost(orderItem.getActualUnitCost());
		
		OrderTransformer.transformOrderItemTaxes(orderItemBean, orderItem.getOrderItemTaxes());

		logger.info("The order item details has been transformed successfully");
		return orderItemBean;
	}

	public static OrderItemBean transformOrderItemTaxes(OrderItemBean orderItemBean, List<OrderItemTax> orderItemTaxes) {
		
		for(OrderItemTax orderItemTax: orderItemTaxes) {
			if(orderItemTax.getTaxCode().equals(MVCConstants.TAX_SGST)) {
				orderItemBean.setSgstRate(orderItemTax.getTaxRulePercentage());
				orderItemBean.setSgstRateRuleId(orderItemTax.getOrderItemTaxId().getTaxRateRuleId());
				orderItemBean.setSgstTaxAmount(orderItemTax.getTaxRuleAmt());
				orderItemBean.setSgstCode(orderItemTax.getTaxCode());
			} else if(orderItemTax.getTaxCode().equals(MVCConstants.TAX_CGST)) {
				orderItemBean.setCgstRate(orderItemTax.getTaxRulePercentage());
				orderItemBean.setCgstRateRuleId(orderItemTax.getOrderItemTaxId().getTaxRateRuleId());
				orderItemBean.setCgstTaxAmount(orderItemTax.getTaxRuleAmt());
				orderItemBean.setCgstCode(orderItemTax.getTaxCode());
			}else if(orderItemTax.getTaxCode().equals(MVCConstants.TAX_IGST)) {
				orderItemBean.setIgstRate(orderItemTax.getTaxRulePercentage());
				orderItemBean.setIgstRateRuleId(orderItemTax.getOrderItemTaxId().getTaxRateRuleId());
				orderItemBean.setIgstTaxAmount(orderItemTax.getTaxRuleAmt());
				orderItemBean.setIgstCode(orderItemTax.getTaxCode());
			}
			orderItemBean.setTaxGroupId(orderItemTax.getOrderItemTaxId().getTaxGroupId());
		}
		
		
		return orderItemBean;
	}
	
	
	public static List<OrderBean> transformOrders() {
		return null;
	}

	public static Order transformOrderBean(OrderBean orderBean, String username, String status) {
		Order order = new Order();
		order.setOrderId(orderBean.getOrderId());

		Supplier supplier = new Supplier();
		supplier.setSupplierId(orderBean.getSupplierId());
		order.setSupplier(supplier);

		order.setCreatedBy(username);
		order.setCreatedDate(LocalDateTime.now());
		order.setStatus(status);

		order.setLocationId(orderBean.getLocationId());

		order.setEstimatedCost(orderBean.getEstimatedCost());
		order.setDiscountAmount(orderBean.getDiscountAmount());
		order.setTaxAmount(orderBean.getTaxAmount());
		order.setPaidAmount(orderBean.getPaidAmount());
		order.setTotalAmount(orderBean.getTotalAmount());

		List<OrderItem> orderItems = OrderTransformer.transformOrderItemsBean(orderBean.getOrderItems(), order);
		order.setOrderItems(orderItems);
		
		logger.info("All the order details has been transformed successfully");
		return order;

	}

	public static List<OrderItemTax> transformOrderItemTaxBean(OrderItemBean orderItemBean, Order order) {

		List<OrderItemTax> orderItemTaxes=new ArrayList<>();
		
		Boolean sgstFlag = null;
		Boolean cgstFlag = null;
		Boolean igstFlag = null;

		if (orderItemBean.getIgstRate() != null && orderItemBean.getIgstTaxAmount() != null
				&& orderItemBean.getIgstTaxAmount().compareTo(BigDecimal.ZERO) > 0) {
			igstFlag = Boolean.TRUE;
		} else if (orderItemBean.getSgstRate() != null && orderItemBean.getSgstTaxAmount() != null
				&& orderItemBean.getSgstTaxAmount().compareTo(BigDecimal.ZERO) > 0) {
			sgstFlag = Boolean.TRUE;
		}

		OrderItemTax orderCGSTTax=null; 
		OrderItemTaxId orderCGSTTaxId =null;
		OrderItemId orderCGSTItemId = null;
		
		OrderItemTax orderItemTax = new OrderItemTax();
		OrderItemTaxId orderItemTaxId = new OrderItemTaxId();
		OrderItemId orderItemId = new OrderItemId();

		orderItemId.setItemId(orderItemBean.getItemId());
		orderItemId.setOrder(order);
		orderItemTaxId.setOrderItemId(orderItemId);
		orderItemTaxId.setTaxGroupId(orderItemBean.getTaxGroupId());
		
		if (igstFlag!=null && igstFlag) {
			orderItemTaxId.setTaxRateRuleId(orderItemBean.getIgstRateRuleId());
		} else if (sgstFlag!=null && sgstFlag) {
			orderItemTaxId.setTaxRateRuleId(orderItemBean.getSgstRateRuleId());
			
			orderCGSTTax=new OrderItemTax();
			orderCGSTTaxId =new OrderItemTaxId();
			orderCGSTItemId=new OrderItemId();
			orderCGSTItemId.setItemId(orderItemBean.getItemId());
			orderCGSTItemId.setOrder(order);
			orderCGSTTaxId.setOrderItemId(orderCGSTItemId);
			orderCGSTTaxId.setTaxGroupId(orderItemBean.getTaxGroupId());
			orderCGSTTaxId.setTaxRateRuleId(orderItemBean.getCgstRateRuleId());
			
		}

		orderItemTax.setOrderItemTaxId(orderItemTaxId);
		orderItemTax.setTaxableAmt(orderItemBean.getCostAmount());

		if (igstFlag!=null && igstFlag) {
			orderItemTax.setTaxRuleAmt(orderItemBean.getIgstTaxAmount());
			orderItemTax.setTaxRulePercentage(orderItemBean.getIgstRate());
			orderItemTax.setTaxCode(orderItemBean.getIgstCode());
			logger.info("The IGST taxes has been created successfully");
		} else if (sgstFlag!=null && sgstFlag) {
			orderItemTax.setTaxRuleAmt(orderItemBean.getSgstTaxAmount());
			orderItemTax.setTaxRulePercentage(orderItemBean.getSgstRate());
			orderItemTax.setTaxCode(orderItemBean.getSgstCode());
			
			orderCGSTTax.setOrderItemTaxId(orderCGSTTaxId);
			orderCGSTTax.setTaxableAmt(orderItemBean.getCostAmount());
			orderCGSTTax.setTaxRuleAmt(orderItemBean.getCgstTaxAmount());
			orderCGSTTax.setTaxRulePercentage(orderItemBean.getCgstRate());
			orderCGSTTax.setTaxCode(orderItemBean.getCgstCode());
			
			orderItemTaxes.add(orderCGSTTax);
			logger.info("The SGST and CGST taxes has been created successfully");
		} 
		orderItemTaxes.add(orderItemTax);
		
		logger.info("All the order items taxes has been transformed successfully");
		return orderItemTaxes;
	}

	public static List<OrderItem> transformOrderItemsBean(List<OrderItemBean> orderItemBeans, Order order) {
		List<OrderItem> orderItems = new ArrayList<>(orderItemBeans.size());
		OrderItem orderItem;
		for (OrderItemBean orderItemBean : orderItemBeans) {
			orderItem = OrderTransformer.transformOrderItemBean(orderItemBean, order);
			orderItems.add(orderItem);
		}

		logger.info("All the order items has been transformed successfully");
		return orderItems;

	}

	public static OrderItem transformOrderItemBean(OrderItemBean orderItemBean, Order order) {
		OrderItem orderItem = new OrderItem();
		OrderItemId orderItemId = new OrderItemId();
		if(order!=null) {
			orderItemId.setOrder(order);
		}else {
			order=new Order();
			order.setOrderId(orderItemBean.getOrderId());
			orderItemId.setOrder(order);
		}

		orderItemId.setItemId(orderItemBean.getItemId());
		orderItem.setOrderItemId(orderItemId);

		orderItem.setOrderedQty(orderItemBean.getOrderedQty());
		orderItem.setUnitCost(orderItemBean.getUnitCost());
		orderItem.setCostAmount(orderItemBean.getCostAmount());
		orderItem.setTaxAmount(orderItemBean.getTaxAmount());
		orderItem.setTotalCost(orderItemBean.getTotalCost());

		orderItem.setDelieveredQty(orderItemBean.getDelieveredQty());
		orderItem.setDelieveredDate(LocalDateTime.now());

		orderItem.setActualUnitCost(orderItemBean.getActualUnitCost());
		orderItem.setActualCostAmount(orderItemBean.getActualCostAmount());
		orderItem.setActualDiscountAmount(orderItemBean.getActualDiscountAmount());
		orderItem.setActualTaxAmount(orderItemBean.getActualTaxAmount());
		orderItem.setActualTotalCost(orderItemBean.getActualTotalCost());
		
		List<OrderItemTax> orderItemTaxes = OrderTransformer.transformOrderItemTaxBean(orderItemBean, order);
		orderItem.setOrderItemTaxes(orderItemTaxes);		


		logger.info("The order item details has been transformed successfully.");

		return orderItem;
	}

	public static OrderReportBean prepareOrderReportBean(OrderBean orderBean, LocationBean locationBean, String username) {
		OrderReportBean orderReportBean = new OrderReportBean();

		orderReportBean.setOrderId(orderBean.getOrderId());

		List<OrderBean> orderList = new ArrayList<>();
		orderList.add(orderBean);

		orderReportBean.setOrder(orderList);

		orderReportBean.setOrderItems(orderBean.getOrderItems());

		AddressBean addressBean = orderBean.getSupplier().getPrimaryAddress();
		List<AddressBean> addresses = new ArrayList<>();
		addresses.add(addressBean);
		orderBean.getSupplier().setAddresses(addresses);

		List<SupplierBean> supplierList = new ArrayList<>();
		supplierList.add(orderBean.getSupplier());

		orderReportBean.setSupplier(supplierList);

		orderReportBean.setLocationDetails(locationBean);

		List<LocationBean> locationList = new ArrayList<>();
		locationList.add(locationBean);

		orderReportBean.setDelieveryLocation(locationList);

		orderReportBean.setUsername(username);
		logger.info("The order report object has been created successfully");
		return orderReportBean;
	}

}
