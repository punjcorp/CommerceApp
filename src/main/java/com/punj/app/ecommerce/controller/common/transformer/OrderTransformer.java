/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.domains.order.ids.OrderItemId;
import com.punj.app.ecommerce.domains.supplier.Supplier;
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

	public static OrderBean transformOrder() {
		return null;
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

		List<OrderItem> orderItems = OrderTransformer.transformOrderItems(orderBean.getOrderItems(), order);
		order.setOrderItems(orderItems);

		logger.info("All the order details has been transformed successfully");
		return order;

	}

	public static List<OrderItem> transformOrderItems(List<OrderItemBean> orderItemBeans, Order order) {
		List<OrderItem> orderItems = new ArrayList<>(orderItemBeans.size());
		OrderItem orderItem;
		for (OrderItemBean orderItemBean : orderItemBeans) {
			orderItem = OrderTransformer.transformOrderItem(orderItemBean, order);
			orderItems.add(orderItem);
		}

		logger.info("All the order items has been transformed successfully");
		return orderItems;

	}

	public static OrderItem transformOrderItem(OrderItemBean orderItemBean, Order order) {
		OrderItem orderItem = new OrderItem();
		OrderItemId orderItemId = new OrderItemId();

		orderItemId.setOrder(order);

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

		logger.info("The order item details has been transformed successfully.");

		return orderItem;
	}

	public static OrderReportBean prepareOrderReportBean(OrderBean orderBean, LocationBean locationBean, String username) {
		OrderReportBean orderReportBean = new OrderReportBean();

		orderReportBean.setOrderId(orderBean.getOrderId());
		
		List<OrderBean> orderList=new ArrayList<>();
		orderList.add(orderBean);
		orderReportBean.setOrder(orderList);
		
		orderReportBean.setOrderItems(orderBean.getOrderItems());

		List<SupplierBean> supplierList=new ArrayList<>();
		supplierList.add(orderBean.getSupplier());
		orderReportBean.setSupplier(supplierList);

		orderReportBean.setLocationDetails(locationBean);
		
		List<LocationBean> locationList=new ArrayList<>();
		locationList.add(locationBean);		
		orderReportBean.setDelieveryLocation(locationList);

		orderReportBean.setUsername(username);
		logger.info("The order report object has been created successfully");
		return orderReportBean;
	}

}
