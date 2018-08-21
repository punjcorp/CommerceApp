package com.punj.app.ecommerce.models.order.returns.validator;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.punj.app.ecommerce.models.order.returns.OrderReturnBean;
import com.punj.app.ecommerce.models.order.returns.OrderReturnDTO;
import com.punj.app.ecommerce.models.order.returns.OrderReturnItemBean;
import com.punj.app.ecommerce.services.OrderReturnService;
import com.punj.app.ecommerce.services.OrderService;

@Component
public class OrderReturnBeanValidator implements Validator {

	private OrderService orderService;

	
	@Autowired
    public OrderReturnBeanValidator(OrderService orderService) {
        this.orderService = orderService;
    }
	
	@Override
	public boolean supports(Class<?> clazz) {
		return OrderReturnBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		OrderReturnDTO orderReturnDTO = (OrderReturnDTO) obj;
		BigInteger orderId = orderReturnDTO.getOrderReturn().getOrderId();
		List<OrderReturnItemBean> orderReturnItems = orderReturnDTO.getOrderReturn().getOrderReturnItems();
		OrderRIBValidator orderRIBValidator = null;
		int idx = 0;
		for (OrderReturnItemBean orderRIB : orderReturnItems) {
			orderRIB.setOrderId(orderId);
			orderRIBValidator = new OrderRIBValidator(this.orderService);
			errors.pushNestedPath("orderReturn.orderReturnItems[" + idx + "]");
			ValidationUtils.invokeValidator(orderRIBValidator, orderRIB, errors);
			errors.popNestedPath();
            idx++;
		}

	}

}
