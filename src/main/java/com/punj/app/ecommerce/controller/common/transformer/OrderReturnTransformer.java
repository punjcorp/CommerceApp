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
import com.punj.app.ecommerce.domains.order.returns.OrderReturn;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnDTO;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnItem;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnItemTax;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.returns.OrderReturnBean;
import com.punj.app.ecommerce.models.order.returns.OrderReturnItemBean;
import com.punj.app.ecommerce.models.order.returns.ReturnBeanDTO;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
public class OrderReturnTransformer {

	private static final Logger logger = LogManager.getLogger();

	private OrderReturnTransformer() {
		throw new IllegalStateException("OrderReturnTransformer class");
	}

	public static ReturnBeanDTO transformOrderReturnDTO(OrderReturnDTO orderReturnsDTO) {
		ReturnBeanDTO returnBeanDTO = new ReturnBeanDTO();

		returnBeanDTO.setPager(orderReturnsDTO.getPager());
		List<OrderReturn> orderReturns = orderReturnsDTO.getOrderReturns();
		if (orderReturns != null && !orderReturns.isEmpty()) {
			List<OrderReturnBean> orderReturnsBeanList = OrderReturnTransformer.transformOrderReturnList(orderReturns);
			returnBeanDTO.setOrderReturns(orderReturnsBeanList);
		}

		logger.info("The order return DTOs were successfully transformed ");
		return returnBeanDTO;
	}

	public static List<OrderReturnBean> transformOrderReturnList(List<OrderReturn> orderReturns) {
		List<OrderReturnBean> orderReturnBeanList = new ArrayList<>(orderReturns.size());
		OrderReturnBean orderReturnBean = null;

		for (OrderReturn orderReturn : orderReturns) {
			orderReturnBean = OrderReturnTransformer.transformOrderReturn(orderReturn);
			orderReturnBeanList.add(orderReturnBean);
		}

		logger.info("The order return DTOs were successfully transformed ");
		return orderReturnBeanList;
	}

	public static OrderReturnBean transformOrderReturn(OrderReturn orderReturn) {
		OrderReturnBean orderReturnBean = new OrderReturnBean();

		OrderBean orderBean = OrderTransformer.transformOrder(orderReturn.getOrder());
		orderReturnBean.setOrder(orderBean);
		orderReturnBean.setOrderId(orderReturn.getOrder().getOrderId());
		orderReturnBean.setOrderReturnId(orderReturn.getOrderReturnId());

		orderReturnBean.setComments(orderReturn.getComments());
		orderReturnBean.setCreatedBy(orderReturn.getCreatedBy());
		orderReturnBean.setCreatedDate(orderReturn.getCreatedDate());
		orderReturnBean.setDiscountAmount(orderReturn.getDiscountAmount());
		orderReturnBean.setStatus(orderReturn.getStatus());
		orderReturnBean.setDisplayStatus(Utils.showStatus(orderReturn.getStatus()));
		orderReturnBean.setEstimatedCost(orderReturn.getSubTotalAmount());
		// orderReturnBean.setReasonCode();
		orderReturnBean.setReasonCodeId(orderReturn.getReasonCodeId());
		orderReturnBean.setRefundAmount(orderReturn.getRefundAmount());

		orderReturnBean.setTaxAmount(orderReturn.getTaxAmount());
		orderReturnBean.setTotalAmount(orderReturn.getTotalAmount());

		List<OrderReturnItem> orderReturnItems = orderReturn.getOrderReturnItems();
		if (orderReturnItems != null && !orderReturnItems.isEmpty()) {
			List<OrderReturnItemBean> orderReturnItemBeans = OrderReturnTransformer.transformOrderReturnItems(orderReturnItems);
			orderReturnBean.setOrderReturnItems(orderReturnItemBeans);
		}

		logger.info("The order return was successfully transformed ");
		return orderReturnBean;
	}

	public static List<OrderReturnItemBean> transformOrderReturnItems(List<OrderReturnItem> orderReturnItems) {
		List<OrderReturnItemBean> orderRIBs = new ArrayList<>(orderReturnItems.size());
		OrderReturnItemBean orderRIB = null;

		for (OrderReturnItem orderReturnItem : orderReturnItems) {
			orderRIB = OrderReturnTransformer.transformOrderReturnItem(orderReturnItem);
			orderRIBs.add(orderRIB);
		}
		logger.info("The order return items has been transformed successfully");
		return orderRIBs;
	}

	public static OrderReturnItemBean transformOrderReturnItem(OrderReturnItem orderReturnItem) {
		OrderReturnItemBean orderReturnItemBean = new OrderReturnItemBean();
		orderReturnItemBean.setOrderReturnItemId(orderReturnItem.getOrderReturnItemId());
		orderReturnItemBean.setOrderReturnId(orderReturnItem.getOrderReturn().getOrderReturnId());

		orderReturnItemBean.setItemId(orderReturnItem.getItemId());
		orderReturnItemBean.setItemDesc(orderReturnItem.getItemDesc());

		orderReturnItemBean.setReasonCodeId(orderReturnItem.getReasonCodeId());
		orderReturnItemBean.setReturnedQty(orderReturnItem.getReturnQty());

		orderReturnItemBean.setUnitCost(orderReturnItem.getActualUnitCost());
		orderReturnItemBean.setCostAmount(orderReturnItem.getActualCostAmount());
		orderReturnItemBean.setDiscountAmount(orderReturnItem.getActualDiscountAmount());

		orderReturnItemBean.setTaxAmount(orderReturnItem.getActualTaxAmount());

		orderReturnItemBean.setTotalCost(orderReturnItem.getActualTotalCost());

		List<OrderReturnItemTax> itemTaxes = orderReturnItem.getOrderReturnItemTaxes();
		if (itemTaxes != null && !itemTaxes.isEmpty()) {
			OrderReturnTransformer.updateTaxDetails(orderReturnItemBean, itemTaxes);
		}
		logger.info("The order return item has been transformed successfully");

		return orderReturnItemBean;

	}

	public static OrderReturnItemBean updateTaxDetails(OrderReturnItemBean orderReturnItemBean, List<OrderReturnItemTax> itemTaxes) {

		for (OrderReturnItemTax orderRIT : itemTaxes) {
			orderReturnItemBean.setTaxGroupId(orderRIT.getTaxGroupId());

			String taxCode = orderRIT.getTaxCode();
			if (taxCode != null && taxCode.equals(MVCConstants.TAX_CGST)) {

				orderReturnItemBean.setCgstActualTaxAmount(orderRIT.getTaxRuleAmt());
				orderReturnItemBean.setCgstCode(orderRIT.getTaxCode());
				orderReturnItemBean.setCgstRate(orderRIT.getTaxRulePercentage());
				orderReturnItemBean.setCgstRateRuleId(orderRIT.getTaxRateRuleId());
				orderReturnItemBean.setCgstTaxAmount(orderRIT.getTaxRuleAmt());
				orderReturnItemBean.setCgstTaxId(orderRIT.getReturnItemTaxId());

			} else if (taxCode != null && taxCode.equals(MVCConstants.TAX_SGST)) {
				orderReturnItemBean.setSgstActualTaxAmount(orderRIT.getTaxRuleAmt());
				orderReturnItemBean.setSgstCode(orderRIT.getTaxCode());
				orderReturnItemBean.setSgstRate(orderRIT.getTaxRulePercentage());
				orderReturnItemBean.setSgstRateRuleId(orderRIT.getTaxRateRuleId());
				orderReturnItemBean.setSgstTaxAmount(orderRIT.getTaxRuleAmt());
				orderReturnItemBean.setSgstTaxId(orderRIT.getReturnItemTaxId());
			} else if (taxCode != null && taxCode.equals(MVCConstants.TAX_IGST)) {
				orderReturnItemBean.setIgstActualTaxAmount(orderRIT.getTaxRuleAmt());
				orderReturnItemBean.setIgstCode(orderRIT.getTaxCode());
				orderReturnItemBean.setIgstRate(orderRIT.getTaxRulePercentage());
				orderReturnItemBean.setIgstRateRuleId(orderRIT.getTaxRateRuleId());
				orderReturnItemBean.setIgstTaxAmount(orderRIT.getTaxRuleAmt());
				orderReturnItemBean.setIgstTaxId(orderRIT.getReturnItemTaxId());
			}
		}
		logger.info("The order return item taxes has been updated successfully");
		return orderReturnItemBean;
	}

	public static OrderReturn updateReturnBasedOnAction(OrderReturn orderReturn, String username, String action) {
		if ((action.equals(MVCConstants.STATUS_CREATED) && orderReturn.getOrderReturnId() == null)
				|| (action.equals(MVCConstants.STATUS_APPROVED) && orderReturn.getOrderReturnId() == null)) {
			orderReturn.setCreatedBy(username);
			orderReturn.setCreatedDate(LocalDateTime.now());
		} else if ((action.equals(MVCConstants.STATUS_CREATED) && orderReturn.getOrderReturnId() != null)
				|| (action.equals(MVCConstants.STATUS_APPROVED) && orderReturn.getOrderReturnId() != null)) {
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
		OrderReturnItem returnItem = null;
		for (OrderReturnItemBean returnBean : returnItemBeans) {
			returnItem = OrderReturnTransformer.transformOrderReturnItemBean(orderReturn, returnBean, username);
			returnItems.add(returnItem);
		}
		logger.info("The order return items has been transformed successfully to order item objects");
		return returnItems;

	}

	public static OrderReturnItem transformOrderReturnItemBean(OrderReturn orderReturn, OrderReturnItemBean returnItemBean, String username) {
		OrderReturnItem returnItem = new OrderReturnItem();

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

		if(returnItemBean.getOrderReturnItemId()!=null && returnItemBean.getOrderReturnItemId().intValue()<0)
			returnItem.setOrderReturnItemId(null);
		else
			returnItem.setOrderReturnItemId(returnItemBean.getOrderReturnItemId());

		List<OrderReturnItemTax> orderReturnItemTaxes = OrderReturnTransformer.transformReturnItemTaxes(returnItem, returnItemBean, username);
		returnItem.setOrderReturnItemTaxes(orderReturnItemTaxes);

		logger.info("The order return item bean has been transformed successfully to order item object");
		return returnItem;
	}

	public static List<OrderReturnItemTax> transformReturnItemTaxes(OrderReturnItem returnItem, OrderReturnItemBean returnItemBean, String username) {
		List<OrderReturnItemTax> orderReturnItemTaxes = new ArrayList<>();

		Boolean sgstFlag = null;
		Boolean igstFlag = null;
		OrderReturnItemTax returnCGSTTax = null;
		OrderReturnItemTax returnItemTax = new OrderReturnItemTax();

		if (returnItemBean.getIgstRate() != null && returnItemBean.getIgstTaxAmount() != null && returnItemBean.getIgstTaxAmount().compareTo(BigDecimal.ZERO) > 0) {
			igstFlag = Boolean.TRUE;
		} else if (returnItemBean.getSgstRate() != null && returnItemBean.getSgstTaxAmount() != null && returnItemBean.getSgstTaxAmount().compareTo(BigDecimal.ZERO) > 0) {
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
			if(returnItemBean.getIgstTaxId()!=null && returnItemBean.getIgstTaxId().intValue()<0)
				returnItemTax.setReturnItemTaxId(null);
			else
			returnItemTax.setReturnItemTaxId(returnItemBean.getIgstTaxId());

			logger.info("The IGST taxes has been created successfully");
		} else if (sgstFlag != null && sgstFlag) {

			returnItemTax.setTaxRuleAmt(returnItemBean.getSgstTaxAmount());
			returnItemTax.setTaxRulePercentage(returnItemBean.getSgstRate());
			returnItemTax.setTaxCode(returnItemBean.getSgstCode());
			if(returnItemBean.getSgstTaxId()!=null && returnItemBean.getSgstTaxId().intValue()<0)
				returnItemTax.setReturnItemTaxId(null);
			else
				returnItemTax.setReturnItemTaxId(returnItemBean.getSgstTaxId());
			
			returnCGSTTax.setTaxableAmt(returnItemBean.getCostAmount().subtract(returnItemBean.getDiscountAmount()));

			returnCGSTTax.setTaxRuleAmt(returnItemBean.getCgstTaxAmount());
			returnCGSTTax.setTaxRulePercentage(returnItemBean.getCgstRate());
			returnCGSTTax.setTaxCode(returnItemBean.getCgstCode());
			if(returnItemBean.getCgstTaxId()!=null && returnItemBean.getCgstTaxId().intValue()<0)
				returnCGSTTax.setReturnItemTaxId(null);
			else
			returnCGSTTax.setReturnItemTaxId(returnItemBean.getCgstTaxId());
			
			orderReturnItemTaxes.add(returnCGSTTax);
			logger.info("The SGST and CGST taxes has been created successfully");
		}
		orderReturnItemTaxes.add(returnItemTax);

		logger.info("The return item taxes has been transformed successfully");
		return orderReturnItemTaxes;
	}

}
