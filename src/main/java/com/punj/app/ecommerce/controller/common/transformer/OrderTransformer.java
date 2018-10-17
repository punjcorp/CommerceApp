/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderBill;
import com.punj.app.ecommerce.domains.order.OrderDTO;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.domains.order.OrderItemTax;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.common.CommerceMultipartFile;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.OrderBeansDTO;
import com.punj.app.ecommerce.models.order.OrderBillBean;
import com.punj.app.ecommerce.models.order.OrderItemBean;
import com.punj.app.ecommerce.models.order.OrderReportBean;
import com.punj.app.ecommerce.models.supplier.SupplierBean;
import com.punj.app.ecommerce.utils.NumberToWordConverter;
import com.punj.app.ecommerce.utils.Pager;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
public class OrderTransformer {

	private static final Logger logger = LogManager.getLogger();

	private OrderTransformer() {
		throw new IllegalStateException("OrderTransformer class");
	}

	public static Order transformOrderBeanAsReceivedAll(OrderBean orderBean, String username) throws IOException {

		orderBean.setActualCgstTaxAmount(orderBean.getCgstTaxAmount());
		orderBean.setActualIgstTaxAmount(orderBean.getIgstTaxAmount());
		orderBean.setActualSgstTaxAmount(orderBean.getSgstTaxAmount());
		orderBean.setActualSubTotalCost(orderBean.getEstimatedCost());
		orderBean.setActualTaxAmount(orderBean.getTaxAmount());
		orderBean.setActualTotalAmount(orderBean.getTotalAmount());
		orderBean.setActualTotalAmountInWords(NumberToWordConverter.convertBigDecimalToWords(orderBean.getTotalAmount()));

		Order order = OrderTransformer.transformOrderBean(orderBean, username, MVCConstants.STATUS_RECEIVED, Boolean.TRUE);

		List<OrderItem> orderItems = order.getOrderItems();

		OrderTransformer.markOrderItemsAsReceived(orderItems);

		logger.info("The order has been updated to receive all the items");
		return order;
	}

	public static void markOrderItemsAsReceived(List<OrderItem> orderItemList) {
		for (OrderItem orderItem : orderItemList) {
			orderItem.setDelieveredQty(orderItem.getOrderedQty());
			orderItem.setActualUnitCost(orderItem.getUnitCost());
			orderItem.setActualCostAmount(orderItem.getCostAmount());
			orderItem.setActualDiscountAmount(BigDecimal.ZERO);
			orderItem.setActualTaxAmount(orderItem.getTaxAmount());
			orderItem.setActualTotalCost(orderItem.getTotalCost());

			OrderTransformer.markOrderItemTaxesAsReceived(orderItem.getOrderItemTaxes());
		}
		logger.info("The order items has been updated as received in all the item list");
	}

	public static void markOrderItemTaxesAsReceived(List<OrderItemTax> orderItemTaxes) {
		for (OrderItemTax orderItemTax : orderItemTaxes) {
			orderItemTax.setActualTaxableAmt(orderItemTax.getTaxableAmt());
			orderItemTax.setActualTaxRuleAmt(orderItemTax.getTaxRuleAmt());
		}
		logger.info("The order item taxes has been updated as part of received all order operation");
	}

	public static OrderBean transformOrder(Order order) {
		OrderBean orderBean = new OrderBean();

		orderBean.setOrderId(order.getOrderId());

		orderBean.setLocationId(order.getLocation().getLocationId());
		orderBean.setLocationName(order.getLocation().getName());
		orderBean.setStatus(order.getStatus());
		orderBean.setDisplayStatus(Utils.showStatus(order.getStatus()));
		orderBean.setComments(order.getComments());

		SupplierBean supplierBean = SupplierTransformer.transformSupplier(order.getSupplier());
		orderBean.setSupplier(supplierBean);
		orderBean.setSupplierId(order.getSupplier().getSupplierId());
		orderBean.setSupplierName(order.getSupplier().getName());
		
		orderBean.setCreatedBy(order.getCreatedBy());
		orderBean.setCreatedDate(order.getCreatedDate());

		orderBean.setEstimatedCost(order.getEstimatedCost());
		orderBean.setTaxAmount(order.getTaxAmount());
		
		orderBean.setTotalAmount(order.getTotalAmount());
		orderBean.setTotalAmountInWords(NumberToWordConverter.convertBigDecimalToWords(orderBean.getTotalAmount()));

		if(order.getActualSubTotalCost()!=null && order.getActualSubTotalCost().compareTo(BigDecimal.ZERO) > 0) {
			orderBean.setActualSubTotalCost(order.getActualSubTotalCost());	
		}else if (orderBean.getEstimatedCost().compareTo(BigDecimal.ZERO) > 0 && order.getActualSubTotalCost()!=null && order.getActualSubTotalCost().compareTo(BigDecimal.ZERO) == 0) {
			orderBean.setActualSubTotalCost(orderBean.getEstimatedCost());
		}
		
		if(order.getActualTaxAmount()!=null && order.getActualTaxAmount().compareTo(BigDecimal.ZERO) > 0) {
			orderBean.setActualTaxAmount(order.getActualTaxAmount());	
		}else if (orderBean.getTaxAmount().compareTo(BigDecimal.ZERO) > 0 && order.getActualTaxAmount()!=null && order.getActualTaxAmount().compareTo(BigDecimal.ZERO) == 0) {
			orderBean.setActualTaxAmount(orderBean.getTaxAmount());
		}
		
		
		if(order.getActualTotalAmount()!=null && order.getActualTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
			orderBean.setActualTotalAmount(order.getActualTotalAmount());	
			orderBean.setActualTotalAmountInWords(NumberToWordConverter.convertBigDecimalToWords(order.getActualTotalAmount()));
		}else if (orderBean.getTotalAmount().compareTo(BigDecimal.ZERO) > 0 && order.getActualTotalAmount()!=null && order.getActualTotalAmount().compareTo(BigDecimal.ZERO) == 0) {
			orderBean.setActualTotalAmount(orderBean.getTotalAmount());
			orderBean.setActualTotalAmountInWords(NumberToWordConverter.convertBigDecimalToWords(orderBean.getTotalAmount()));
		}

		orderBean.setDiscountAmount(order.getDiscountAmount());
		orderBean.setPaidAmount(order.getPaidAmount());

		if(order.getOrderItems()!=null && !order.getOrderItems().isEmpty()) {
			List<OrderItemBean> orderItemBeans = OrderTransformer.transformOrderItems(order.getOrderItems(), orderBean);
			orderBean.setOrderItems(orderItemBeans);
		}

		if(order.getOrderBills()!=null && !order.getOrderBills().isEmpty()) {
			List<OrderBillBean> orderBillBeanList = OrderTransformer.tranformOrderBills(order.getOrderBills());
			orderBean.setOrderBills(orderBillBeanList);
		}

		logger.info("The order details has been transformed successfully");

		return orderBean;
	}

	public static List<OrderItemBean> transformOrderItems(List<OrderItem> orderItems, OrderBean orderBean) {

		List<OrderItemBean> orderItemBeans = new ArrayList<>(orderItems.size());
		OrderItemBean orderItemBean;

		BigDecimal sgstTaxAmt = BigDecimal.ZERO;
		BigDecimal cgstTaxAmt = BigDecimal.ZERO;
		BigDecimal igstTaxAmt = BigDecimal.ZERO;
		int seqNo=1;
		for (OrderItem orderItem : orderItems) {
			orderItemBean = OrderTransformer.transformOrderItem(orderItem);
			
			orderItemBean.setSeqNo(seqNo);
			seqNo++;

			if (orderItemBean.getSgstTaxAmount() != null) {
				sgstTaxAmt=sgstTaxAmt.add(orderItemBean.getSgstTaxAmount());
			}

			if (orderItemBean.getCgstTaxAmount() != null) {
				cgstTaxAmt=cgstTaxAmt.add(orderItemBean.getCgstTaxAmount());
			}

			if (orderItemBean.getIgstTaxAmount() != null) {
				igstTaxAmt=igstTaxAmt.add(orderItemBean.getIgstTaxAmount());
			}

			if (orderItemBean.getOrderedQty().compareTo(BigDecimal.ZERO) > 0
					&& (orderItemBean.getDelieveredQty() == null || orderItemBean.getDelieveredQty().compareTo(BigDecimal.ZERO) == 0)) {
				orderItemBean.setDelieveredQty(orderItemBean.getOrderedQty());
			}
			if (orderItemBean.getUnitCost().compareTo(BigDecimal.ZERO) > 0 && orderItemBean.getActualUnitCost().compareTo(BigDecimal.ZERO) == 0) {
				orderItemBean.setActualUnitCost(orderItemBean.getUnitCost());
			}
			if (orderItemBean.getCostAmount().compareTo(BigDecimal.ZERO) > 0 && orderItemBean.getActualCostAmount().compareTo(BigDecimal.ZERO) == 0) {
				orderItemBean.setActualCostAmount(orderItemBean.getCostAmount());
			}

			if (orderItemBean.getSgstTaxAmount().compareTo(BigDecimal.ZERO) > 0 && orderItemBean.getSgstActualTaxAmount().compareTo(BigDecimal.ZERO) == 0) {
				orderItemBean.setSgstActualTaxAmount(orderItemBean.getSgstTaxAmount());
			}
			if (orderItemBean.getCgstTaxAmount().compareTo(BigDecimal.ZERO) > 0 && orderItemBean.getCgstActualTaxAmount().compareTo(BigDecimal.ZERO) == 0) {
				orderItemBean.setCgstActualTaxAmount(orderItemBean.getCgstTaxAmount());
			}
			if (orderItemBean.getIgstTaxAmount().compareTo(BigDecimal.ZERO) > 0 && orderItemBean.getIgstActualTaxAmount().compareTo(BigDecimal.ZERO) == 0) {
				orderItemBean.setIgstActualTaxAmount(orderItemBean.getIgstTaxAmount());
			}
			if (orderItemBean.getTaxAmount().compareTo(BigDecimal.ZERO) > 0 && orderItemBean.getActualTaxAmount().compareTo(BigDecimal.ZERO) == 0) {
				orderItemBean.setActualTaxAmount(orderItemBean.getTaxAmount());
			}
			if (orderItemBean.getTotalCost().compareTo(BigDecimal.ZERO) > 0 && orderItemBean.getActualTotalCost().compareTo(BigDecimal.ZERO) == 0) {
				orderItemBean.setActualTotalCost(orderItemBean.getTotalCost());
			}

			orderItemBeans.add(orderItemBean);
		}

		orderBean.setCgstTaxAmount(cgstTaxAmt);
		orderBean.setSgstTaxAmount(sgstTaxAmt);
		orderBean.setIgstTaxAmount(igstTaxAmt);
		
		if((igstTaxAmt !=null && sgstTaxAmt==null)||(igstTaxAmt!=null && igstTaxAmt.compareTo(BigDecimal.ZERO)>0 && sgstTaxAmt!=null && sgstTaxAmt.compareTo(BigDecimal.ZERO)<=0))
			orderBean.setIsIgstApplicable(Boolean.TRUE);
		logger.info("The order item details list has been transformed successfully");
		return orderItemBeans;
	}

	public static List<OrderBillBean> tranformOrderBills(List<OrderBill> orderBillList) {
		List<OrderBillBean> orderBillBeanList = new ArrayList<>(orderBillList.size());
		OrderBillBean orderBillBean;
		for (OrderBill orderBill : orderBillList) {
			orderBillBean = OrderTransformer.tranformOrderBill(orderBill);
			orderBillBeanList.add(orderBillBean);
		}

		logger.info("The order bill list from database has been transformed successfully");
		return orderBillBeanList;

	}

	public static OrderBillBean tranformOrderBill(OrderBill orderBill) {
		OrderBillBean orderBillBean = new OrderBillBean();
		orderBillBean.setBillDate(orderBill.getBillDate());
		orderBillBean.setOrderBillId(orderBill.getOrderBillId());
		orderBillBean.setBillNo(orderBill.getBillNo());

		orderBillBean.setBillFileURL(orderBill.getBillFileName());
		orderBillBean.setBillFileType(orderBill.getBillFileType());

		MultipartFile multipartFile = new CommerceMultipartFile(orderBill.getBillData(), orderBill.getBillFileName(), orderBill.getBillFileType());

		orderBillBean.setBillFile(multipartFile);

		if (orderBill.getOrder() != null)
			orderBillBean.setOrderId(orderBill.getOrder().getOrderId());

		logger.info("The order bill from database has been transformed successfully");
		return orderBillBean;
	}

	public static List<OrderBill> tranformOrderBillBeanList(Order order, List<OrderBillBean> orderBillBeanList) throws IOException {
		List<OrderBill> orderBills = new ArrayList<>();
		OrderBill orderBill;

		for (OrderBillBean orderBillBean : orderBillBeanList) {
			orderBill = OrderTransformer.tranformOrderBillBean(order, orderBillBean);
			orderBills.add(orderBill);
		}

		logger.info("The order bill list has been transformed successfully");
		return orderBills;

	}

	public static OrderBill tranformOrderBillBean(Order order, OrderBillBean orderBillBean) throws IOException {

		OrderBill orderBill = null;
		if (orderBillBean != null) {

			orderBill = new OrderBill();

			orderBill.setOrderBillId(orderBillBean.getOrderBillId());
			orderBill.setOrder(order);
			orderBill.setBillNo(orderBillBean.getBillNo());
			orderBill.setBillDate(orderBillBean.getBillDate());

			MultipartFile billFile = orderBillBean.getBillFile();
			if (billFile != null) {
				orderBill.setBillData(billFile.getBytes());
				orderBill.setBillFileName(billFile.getOriginalFilename());
				orderBill.setBillFileType(billFile.getContentType());
			}

		}

		logger.info("The order item details list has been transformed successfully");
		return orderBill;
	}

	public static Order updateOrderBillsFiles(Order actualOrder, Order updatedOrder) {

		List<OrderBill> orderBills = updatedOrder.getOrderBills();
		List<OrderBill> actualOrderBills = actualOrder.getOrderBills();
		List<OrderBill> finalOrderBills = new ArrayList<>(orderBills.size());

		for (int index = 0; index < orderBills.size(); index++) {
			OrderBill updatedOrderBill = orderBills.get(index);

			if (updatedOrderBill.getOrderBillId() == null && updatedOrderBill.getBillDate() != null && updatedOrderBill.getBillNo() != null) {
				finalOrderBills.add(updatedOrderBill);
			} else {

				for (OrderBill actualOrderBill : actualOrderBills) {
					if (updatedOrderBill.getOrderBillId() != null && updatedOrderBill.getOrderBillId().equals(actualOrderBill.getOrderBillId())) {
						finalOrderBills.add(actualOrderBill);
					}
				}
			}
		}

		updatedOrder.setOrderBills(finalOrderBills);
		logger.info("The order bill files has been updated successfully");
		return updatedOrder;

	}

	public static OrderItemBean transformOrderItem(OrderItem orderItem) {
		OrderItemBean orderItemBean = new OrderItemBean();

		orderItemBean.setOrderItemId(orderItem.getOrderItemId());
		
		orderItemBean.setOrderId(orderItem.getOrder().getOrderId());
		orderItemBean.setItemId(orderItem.getItemId());
		orderItemBean.setHsnNo(orderItem.getHsnNo());
		orderItemBean.setItemDesc(orderItem.getItemDesc());
		orderItemBean.setUnitCost(orderItem.getUnitCost());
		orderItemBean.setOrderedQty(orderItem.getOrderedQty());
		orderItemBean.setCostAmount(orderItem.getCostAmount());

		orderItemBean.setDelieveredDate(orderItem.getDelieveredDate());
		orderItemBean.setDelieveredQty(orderItem.getDelieveredQty());
		orderItemBean.setReturnedQty(orderItem.getReturnedQty());
				
		if(orderItem.getDelieveredQty()!=null && orderItem.getReturnedQty()!=null && orderItem.getDelieveredQty().compareTo(orderItem.getReturnedQty())>0) {
			orderItemBean.setIsReturnAllowed(Boolean.TRUE);
		}else {
			orderItemBean.setIsReturnAllowed(Boolean.FALSE);
		}
		
		orderItemBean.setTaxAmount(orderItem.getTaxAmount());
		orderItemBean.setTotalCost(orderItem.getTotalCost());

		orderItemBean.setActualSuggestedPrice(orderItem.getActualSuggestedPrice());
		orderItemBean.setActualMaxRetailPrice(orderItem.getActualMaxRetailPrice());
		
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

		for (OrderItemTax orderItemTax : orderItemTaxes) {
			if (orderItemTax.getTaxCode().equals(MVCConstants.TAX_SGST)) {
				orderItemBean.setSgstTaxId(orderItemTax.getOrderItemTaxId());
				
				orderItemBean.setSgstRate(orderItemTax.getTaxRulePercentage());
				orderItemBean.setSgstRateRuleId(orderItemTax.getTaxRateRuleId());
				orderItemBean.setSgstTaxAmount(orderItemTax.getTaxRuleAmt());
				orderItemBean.setSgstCode(orderItemTax.getTaxCode());
				
				orderItemBean.setSgstActualTaxAmount(orderItemTax.getActualTaxRuleAmt());
			} else if (orderItemTax.getTaxCode().equals(MVCConstants.TAX_CGST)) {
				orderItemBean.setCgstTaxId(orderItemTax.getOrderItemTaxId());
				
				orderItemBean.setCgstRate(orderItemTax.getTaxRulePercentage());
				orderItemBean.setCgstRateRuleId(orderItemTax.getTaxRateRuleId());
				orderItemBean.setCgstTaxAmount(orderItemTax.getTaxRuleAmt());
				orderItemBean.setCgstCode(orderItemTax.getTaxCode());

				orderItemBean.setCgstActualTaxAmount(orderItemTax.getActualTaxRuleAmt());				
			} else if (orderItemTax.getTaxCode().equals(MVCConstants.TAX_IGST)) {
				orderItemBean.setIgstTaxId(orderItemTax.getOrderItemTaxId());
				
				orderItemBean.setIgstRate(orderItemTax.getTaxRulePercentage());
				orderItemBean.setIgstRateRuleId(orderItemTax.getTaxRateRuleId());
				orderItemBean.setIgstTaxAmount(orderItemTax.getTaxRuleAmt());
				orderItemBean.setIgstCode(orderItemTax.getTaxCode());
				
				orderItemBean.setIgstActualTaxAmount(orderItemTax.getActualTaxRuleAmt());				
			}
			orderItemBean.setTaxGroupId(orderItemTax.getTaxGroupId());

		}

		return orderItemBean;
	}

	public static OrderBeansDTO transformOrderDTO(OrderDTO orderDTO) {
		OrderBeansDTO orderBeanDTO = new OrderBeansDTO();

		List<OrderBean> orderBeans = OrderTransformer.transformOrders(orderDTO.getOrders());
		orderBeanDTO.setOrders(orderBeans);

		Pager tmpPager = orderDTO.getPager();
		Pager pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(), tmpPager.getMaxDisplayPage(),
				ViewPathConstants.MANAGE_ORDER_URL);
		orderBeanDTO.setPager(pager);

		logger.info("The Order DTO for listing page has been transformed successfully");
		return orderBeanDTO;
	}

	public static List<OrderBean> transformOrders(List<Order> orders) {
		List<OrderBean> orderBeanList = new ArrayList<>(orders.size());
		OrderBean orderBean;
		for (Order order : orders) {
			orderBean = OrderTransformer.transformOrder(order);
			orderBeanList.add(orderBean);
		}
		logger.info("The order list has been transformed to order bean list successfully");
		return orderBeanList;
	}

	public static Order transformOrderBean(OrderBean orderBean, String username, String status, Boolean isUpdate) throws IOException {
		Order order = new Order();
		order.setOrderId(orderBean.getOrderId());

		Supplier supplier = new Supplier();
		supplier.setSupplierId(orderBean.getSupplierId());
		order.setSupplier(supplier);
		if (isUpdate) {
			order.setCreatedBy(orderBean.getCreatedBy());
			order.setCreatedDate(orderBean.getCreatedDate());

			order.setModifiedBy(username);
			order.setModifiedDate(LocalDateTime.now());

		} else {
			order.setCreatedBy(username);
			order.setCreatedDate(LocalDateTime.now());

		}
		order.setStatus(status);
		order.setComments(orderBean.getComments());

		Location location = new Location();
		location.setLocationId(orderBean.getLocationId());
		order.setLocation(location);

		order.setDiscountAmount(orderBean.getDiscountAmount());
		order.setPaidAmount(orderBean.getPaidAmount());

		order.setEstimatedCost(orderBean.getEstimatedCost());
		order.setTaxAmount(orderBean.getTaxAmount());
		order.setTotalAmount(orderBean.getTotalAmount());

		order.setActualSubTotalCost(orderBean.getActualSubTotalCost());
		order.setActualTaxAmount(orderBean.getActualTaxAmount());
		order.setActualTotalAmount(orderBean.getActualTotalAmount());

		if(orderBean.getOrderItems()!=null && !orderBean.getOrderItems().isEmpty()) {
			List<OrderItem> orderItems = OrderTransformer.transformOrderItemsBean(orderBean.getOrderItems(), order);
			order.setOrderItems(orderItems);			
		}

		if (orderBean.getOrderBills() != null && !orderBean.getOrderBills().isEmpty()) {
			List<OrderBill> orderBillList = OrderTransformer.tranformOrderBillBeanList(order, orderBean.getOrderBills());
			order.setOrderBills(orderBillList);
		}

		logger.info("All the order details has been transformed successfully");
		return order;

	}

	public static List<OrderItemTax> transformOrderItemTaxBean(OrderItemBean orderItemBean, Order order, OrderItem orderItem ) {

		List<OrderItemTax> orderItemTaxes = new ArrayList<>();

		Boolean sgstFlag = null;
		Boolean igstFlag = null;
		OrderItemTax orderCGSTTax = null;
		OrderItemTax orderItemTax = new OrderItemTax();
		
		if (orderItemBean.getIgstRate() != null && orderItemBean.getIgstTaxAmount() != null
				&& orderItemBean.getIgstTaxAmount().compareTo(BigDecimal.ZERO) > 0) {
			igstFlag = Boolean.TRUE;
		} else if (orderItemBean.getSgstRate() != null && orderItemBean.getSgstTaxAmount() != null
				&& orderItemBean.getSgstTaxAmount().compareTo(BigDecimal.ZERO) > 0) {
			sgstFlag = Boolean.TRUE;
		}


		orderItemTax.setOrderItem(orderItem);
		orderItemTax.setTaxGroupId(orderItemBean.getTaxGroupId());

		if (igstFlag != null && igstFlag) {
			orderItemTax.setTaxRateRuleId(orderItemBean.getIgstRateRuleId());

		} else if (sgstFlag != null && sgstFlag) {
			orderItemTax.setTaxRateRuleId(orderItemBean.getSgstRateRuleId());

			orderCGSTTax = new OrderItemTax();
			orderCGSTTax.setOrderItem(orderItem);
			orderCGSTTax.setTaxGroupId(orderItemBean.getTaxGroupId());			
			orderCGSTTax.setTaxRateRuleId(orderItemBean.getCgstRateRuleId());

		}

		orderItemTax.setTaxableAmt(orderItemBean.getCostAmount());
		orderItemTax.setActualTaxableAmt(orderItemBean.getActualCostAmount());

		if (igstFlag != null && igstFlag) {
			orderItemTax.setOrderItemTaxId(orderItemBean.getIgstTaxId());
			orderItemTax.setTaxRuleAmt(orderItemBean.getIgstTaxAmount());
			orderItemTax.setTaxRulePercentage(orderItemBean.getIgstRate());
			orderItemTax.setTaxCode(orderItemBean.getIgstCode());
			orderItemTax.setActualTaxRuleAmt(orderItemBean.getIgstActualTaxAmount());

			logger.info("The IGST taxes has been created successfully");
		} else if (sgstFlag != null && sgstFlag) {
			orderItemTax.setOrderItemTaxId(orderItemBean.getSgstTaxId());
			orderItemTax.setTaxRuleAmt(orderItemBean.getSgstTaxAmount());
			orderItemTax.setTaxRulePercentage(orderItemBean.getSgstRate());
			orderItemTax.setTaxCode(orderItemBean.getSgstCode());
			orderItemTax.setActualTaxRuleAmt(orderItemBean.getSgstActualTaxAmount());

			orderCGSTTax.setTaxableAmt(orderItemBean.getCostAmount());
			orderCGSTTax.setActualTaxableAmt(orderItemBean.getActualCostAmount());
			orderCGSTTax.setOrderItemTaxId(orderItemBean.getCgstTaxId());
			orderCGSTTax.setTaxRuleAmt(orderItemBean.getCgstTaxAmount());
			orderCGSTTax.setTaxRulePercentage(orderItemBean.getCgstRate());
			orderCGSTTax.setTaxCode(orderItemBean.getCgstCode());
			orderCGSTTax.setActualTaxRuleAmt(orderItemBean.getCgstActualTaxAmount());
			
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
		if (order != null) {
			orderItem.setOrder(order);
		} else {
			order = new Order();
			order.setOrderId(orderItemBean.getOrderId());
			orderItem.setOrder(order);
		}

		orderItem.setItemId(orderItemBean.getItemId());
		if(orderItemBean.getOrderItemId()!=null)
			orderItem.setOrderItemId(orderItemBean.getOrderItemId());
		else
			orderItem.setOrderItemId(orderItem.getOrderItemId());
		orderItem.setItemDesc(orderItemBean.getItemDesc());
		orderItem.setHsnNo(orderItemBean.getHsnNo());

		orderItem.setOrderedQty(orderItemBean.getOrderedQty());
		orderItem.setUnitCost(orderItemBean.getUnitCost());
		orderItem.setCostAmount(orderItemBean.getCostAmount());
		orderItem.setTaxAmount(orderItemBean.getTaxAmount());
		orderItem.setTotalCost(orderItemBean.getTotalCost());

		orderItem.setDelieveredQty(orderItemBean.getDelieveredQty());
		orderItem.setDelieveredDate(LocalDateTime.now());

		orderItem.setActualSuggestedPrice(orderItemBean.getActualSuggestedPrice());
		orderItem.setActualMaxRetailPrice(orderItemBean.getActualMaxRetailPrice());
		
		orderItem.setActualUnitCost(orderItemBean.getActualUnitCost());
		orderItem.setActualCostAmount(orderItemBean.getActualCostAmount());
		orderItem.setActualDiscountAmount(orderItemBean.getActualDiscountAmount());
		orderItem.setActualTaxAmount(orderItemBean.getActualTaxAmount());
		orderItem.setActualTotalCost(orderItemBean.getActualTotalCost());

		List<OrderItemTax> orderItemTaxes = OrderTransformer.transformOrderItemTaxBean(orderItemBean, order, orderItem);
		orderItem.setOrderItemTaxes(orderItemTaxes);

		logger.info("The order item details has been transformed successfully.");

		return orderItem;
	}

	public static OrderReportBean prepareOrderReportBean(OrderBean orderBean, LocationBean locationBean, String username) {
		OrderReportBean orderReportBean = new OrderReportBean();

		orderReportBean.setOrderId(orderBean.getOrderId());
		
		if(orderBean.getStatus().equals(MVCConstants.STATUS_CREATED) || orderBean.getStatus().equals(MVCConstants.STATUS_CREATED))
			orderReportBean.setReportType(MVCConstants.PO_REPORT_ESTIMATES);
		else if(orderBean.getStatus().equals(MVCConstants.STATUS_APPROVED)) {
			orderReportBean.setReportType(MVCConstants.PO_REPORT_SUPPLIER);
		}else if(orderBean.getStatus().equals(MVCConstants.STATUS_RECEIVED)) {
			orderReportBean.setReportType(MVCConstants.PO_REPORT_RECEIVED);
		}else
			orderReportBean.setReportType(MVCConstants.PO_REPORT_ESTIMATES);

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

	public static List<BigInteger> retrieveEligibleOrders(OrderBeansDTO orders) {
		List<OrderBean> orderList = orders.getOrders();
		List<BigInteger> finalOrderIds = new ArrayList<>();
		List<String> selectedIds = orders.getOrderIds();
		BigInteger orderId = null;
		Integer orderIndex = null;
		OrderBean orderBean = null;
		for (String selectedId : selectedIds) {
			String[] splittedVals = selectedId.split("_");
			orderId = new BigInteger(splittedVals[0]);
			orderIndex = new Integer(splittedVals[1]);
			orderBean = orderList.get(orderIndex);
			if (orderBean.getStatus().equals(MVCConstants.STATUS_CREATED)) {
				finalOrderIds.add(orderId);
			}
		}
		logger.info("The select order ids and index for bulk operations has been transformed successfully");
		return finalOrderIds;
	}

}
