package com.punj.app.ecommerce.models.order.returns.validator;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.models.order.returns.OrderReturnBean;
import com.punj.app.ecommerce.models.order.returns.OrderReturnDTO;
import com.punj.app.ecommerce.models.order.returns.OrderReturnItemBean;
import com.punj.app.ecommerce.services.OrderReturnService;
import com.punj.app.ecommerce.services.OrderService;

@Component
public class OrderRIBValidator implements Validator {

	private OrderService orderService;

	
	@Autowired
    public OrderRIBValidator(OrderService orderService) {
        this.orderService = orderService;
    }

	@Override
	public boolean supports(Class<?> clazz) {
		return OrderReturnItemBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		OrderReturnItemBean orderRIB = (OrderReturnItemBean) obj;
		BigInteger orderId = orderRIB.getOrderId();

		Order order = this.orderService.searchOrder(orderId);
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			if (orderRIB.getItemId().equals(orderItem.getItemId())) {
				this.validateOrderLineItemReturnQty(orderRIB, orderItem, errors);
			}
		}
					
	}

	public void validateOrderLineItemReturnQty(OrderReturnItemBean orderRIB, OrderItem orderItem, Errors errors) {
		
		if(orderRIB.getReturnedQty().compareTo(orderItem.getDelieveredQty().subtract(orderItem.getReturnedQty()))>0)
			errors.rejectValue("returnedQty", "commerce.error.return.qty.exceed");
	}

}
