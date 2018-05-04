/**
 * 
 */
package com.punj.app.ecommerce.models.item.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.punj.app.ecommerce.models.item.ItemBean;

/**
 * @author admin
 *
 */
public class ItemBeanValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return ItemBean.class.equals(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {

	}

}
