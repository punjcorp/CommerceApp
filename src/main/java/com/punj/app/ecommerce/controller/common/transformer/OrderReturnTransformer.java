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
import com.punj.app.ecommerce.domains.order.returns.OrderReturn;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnItem;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnItemTax;
import com.punj.app.ecommerce.models.order.OrderItemBean;
import com.punj.app.ecommerce.models.order.returns.OrderReturnBean;
import com.punj.app.ecommerce.models.order.returns.OrderReturnItemBean;

/**
 * @author admin
 *
 */
public class OrderReturnTransformer {

	private static final Logger logger = LogManager.getLogger();

	private OrderReturnTransformer() {
		throw new IllegalStateException("OrderReturnTransformer class");
	}
	
	public static OrderReturn updateReturnBasedOnAction(OrderReturn orderReturn, String username, String action) {
		if(action.equals(MVCConstants.STATUS_CREATED) || (action.equals(MVCConstants.STATUS_APPROVED) && orderReturn.getOrderReturnId()==null)) {
			orderReturn.setCreatedBy(username);
			orderReturn.setCreatedDate(LocalDateTime.now());
		} else if(action.equals(MVCConstants.STATUS_APPROVED) && orderReturn.getOrderReturnId()!=null) {
			orderReturn.setModifiedBy(username);
			orderReturn.setModifiedDate(LocalDateTime.now());
		}
		logger.info("The order return details has been updated based on the action {} ", action);
		return orderReturn;
		
	}

	public static OrderReturn transformOrderReturnBean(OrderReturnBean orderReturnBean, String username, String status) {
		OrderReturn orderReturn = new OrderReturn();

		orderReturn.setOrderReturnId(orderReturnBean.getOrderReturnId());
		orderReturn.setComments(orderReturnBean.getComments());
		orderReturn.setStatus(status);
		orderReturn.setReasonCodeId(orderReturnBean.getReasonCodeId());

		orderReturn.setDiscountAmount(orderReturnBean.getDiscountAmount());
		orderReturn.setSubTotalAmount(orderReturnBean.getEstimatedCost());
		orderReturn.setTaxAmount(orderReturnBean.getTaxAmount());
		orderReturn.setTotalAmount(orderReturnBean.getTotalAmount());
		orderReturn.setRefundAmount(orderReturnBean.getRefundAmount());

		orderReturn.setCreatedBy(orderReturnBean.getCreatedBy());
		orderReturn.setCreatedDate(orderReturnBean.getCreatedDate());

		Order order = new Order();
		order.setOrderId(orderReturnBean.getOrderId());
		orderReturn.setOrder(order);

		List<OrderReturnItemBean> returnItemBeans = orderReturnBean.getOrderReturnItems();
		if (returnItemBeans != null && !returnItemBeans.isEmpty()) {
			List<OrderReturnItem> returnItems = OrderReturnTransformer.transformOrderReturnItemBeans(orderReturn, returnItemBeans, username);
			orderReturn.setOrderReturnItems(returnItems);
		}

		logger.info("The order return details has been transformed successfully for saving in database");
		return orderReturn;

	}

	public static List<OrderReturnItem> transformOrderReturnItemBeans(OrderReturn orderReturn, List<OrderReturnItemBean> returnItemBeans, String username) {
		List<OrderReturnItem> returnItems = new ArrayList<>(returnItemBeans.size());
		OrderReturnItem returnItem=null;
		for(OrderReturnItemBean returnBean: returnItemBeans) {
			returnItem=OrderReturnTransformer.transformOrderReturnItemBean(orderReturn, returnBean, username);
			returnItems.add(returnItem);
		}
		logger.info("The order return items has been transformed successfully to order item objects");
		return returnItems;

	}

	public static OrderReturnItem transformOrderReturnItemBean(OrderReturn orderReturn, OrderReturnItemBean returnItemBean, String username) {
		OrderReturnItem returnItem=new OrderReturnItem();
		
		returnItem.setOrderReturn(orderReturn);
		
		returnItem.setItemId(returnItemBean.getItemId());
		returnItem.setItemDesc(returnItemBean.getItemDesc());

		returnItem.setReturnQty(returnItemBean.getReturnedQty());
		
		returnItem.setActualUnitCost(returnItemBean.getUnitCost());
		returnItem.setActualCostAmount(returnItemBean.getCostAmount());
		returnItem.setActualDiscountAmount(returnItemBean.getDiscountAmount());
		returnItem.setActualTaxAmount(returnItemBean.getTaxAmount());
		returnItem.setActualTotalCost(returnItemBean.getTotalCost());
		returnItem.setReasonCodeId(returnItemBean.getReasonCodeId());

		returnItem.setOrderReturnItemId(returnItemBean.getOrderReturnItemId());
		
		List<OrderReturnItemTax> orderReturnItemTaxes=OrderReturnTransformer.transformReturnItemTaxes(returnItem, returnItemBean,username);
		returnItem.setOrderReturnItemTaxes(orderReturnItemTaxes);
		
		
		
		logger.info("The order return item bean has been transformed successfully to order item object");
		return returnItem;
	}
	
	public static List<OrderReturnItemTax> transformReturnItemTaxes(OrderReturnItem returnItem, OrderReturnItemBean returnItemBean, String username) {
		List<OrderReturnItemTax> orderReturnItemTaxes=new ArrayList<>();
	
		Boolean sgstFlag = null;
		Boolean igstFlag = null;
		OrderReturnItemTax returnCGSTTax = null;
		OrderReturnItemTax returnItemTax = new OrderReturnItemTax();
	
		
		if (returnItemBean.getIgstRate() != null && returnItemBean.getIgstTaxAmount() != null
				&& returnItemBean.getIgstTaxAmount().compareTo(BigDecimal.ZERO) > 0) {
			igstFlag = Boolean.TRUE;
		} else if (returnItemBean.getSgstRate() != null && returnItemBean.getSgstTaxAmount() != null
				&& returnItemBean.getSgstTaxAmount().compareTo(BigDecimal.ZERO) > 0) {
			sgstFlag = Boolean.TRUE;
		}
		
		returnItemTax.setOrderReturnItem(returnItem);
		returnItemTax.setTaxGroupId(returnItemBean.getTaxGroupId());

		if (igstFlag != null && igstFlag) {
			returnItemTax.setTaxRateRuleId(returnItemBean.getIgstRateRuleId());

		} else if (sgstFlag != null && sgstFlag) {
			returnItemTax.setTaxRateRuleId(returnItemBean.getSgstRateRuleId());

			returnCGSTTax = new OrderReturnItemTax();
			returnCGSTTax.setOrderReturnItem(returnItem);
			returnCGSTTax.setTaxGroupId(returnItemBean.getTaxGroupId());			
			returnCGSTTax.setTaxRateRuleId(returnItemBean.getCgstRateRuleId());

		}
		
		
		returnItemTax.setTaxableAmt(returnItemBean.getCostAmount());
		returnItemTax.setTaxableAmt(returnItemBean.getCostAmount().subtract(returnItemBean.getDiscountAmount()));

		if (igstFlag != null && igstFlag) {

			returnItemTax.setTaxRuleAmt(returnItemBean.getIgstTaxAmount());
			returnItemTax.setTaxRulePercentage(returnItemBean.getIgstRate());
			returnItemTax.setTaxCode(returnItemBean.getIgstCode());

			logger.info("The IGST taxes has been created successfully");
		} else if (sgstFlag != null && sgstFlag) {

			returnItemTax.setTaxRuleAmt(returnItemBean.getSgstTaxAmount());
			returnItemTax.setTaxRulePercentage(returnItemBean.getSgstRate());
			returnItemTax.setTaxCode(returnItemBean.getSgstCode());

			returnCGSTTax.setTaxableAmt(returnItemBean.getCostAmount().subtract(returnItemBean.getDiscountAmount()));

			returnCGSTTax.setTaxRuleAmt(returnItemBean.getCgstTaxAmount());
			returnCGSTTax.setTaxRulePercentage(returnItemBean.getCgstRate());
			returnCGSTTax.setTaxCode(returnItemBean.getCgstCode());

			
			orderReturnItemTaxes.add(returnCGSTTax);
			logger.info("The SGST and CGST taxes has been created successfully");
		}
		orderReturnItemTaxes.add(returnItemTax);
		
		logger.info("The return item taxes has been transformed successfully");
		return orderReturnItemTaxes;
	}
	
		
				
	
}
