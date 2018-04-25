/**
 * 
 */
package com.punj.app.ecommerce.controller.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderBillValidator implements ConstraintValidator<OrderBillConstraint, String> {

	@Override
	public void initialize(OrderBillConstraint orderBill) {
		//This needs to be initialized
	}

	@Override
	public boolean isValid(String orderBillField, ConstraintValidatorContext cxt) {
		return orderBillField != null && orderBillField.matches("[0-9]+") && (orderBillField.length() > 8) && (orderBillField.length() < 14);
	}

}