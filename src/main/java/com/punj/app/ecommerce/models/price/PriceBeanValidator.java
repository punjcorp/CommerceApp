package com.punj.app.ecommerce.models.price;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.punj.app.ecommerce.controller.common.MVCConstants;

@Component
public class PriceBeanValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PriceBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {

		PriceBean priceBean = (PriceBean) obj;
		String priceType = priceBean.getPriceType();
		BigDecimal priceAmount = priceBean.getItemPriceAmt();

		if (StringUtils.isNotBlank(priceType) && (priceType.equals(MVCConstants.PRICE_TYPE_PERMANENT) || priceType.equals(MVCConstants.PRICE_TYPE_CLEARANCE)
				|| priceType.equals(MVCConstants.PRICE_TYPE_PROMOTION))) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemPriceAmt", "commerce.error.amount.empty");
			if (priceAmount != null) {
				if (priceAmount.doubleValue() < 0.01 || priceAmount.doubleValue() > 9999999999.99)
					errors.rejectValue("itemPriceAmt", "commerce.error.amt.range");
			}

			if (StringUtils.isNotBlank(priceType) && priceType.equals(MVCConstants.PRICE_TYPE_PROMOTION)) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "commerce.error.date.empty");
			}

		} else if (StringUtils.isNotBlank(priceType) && priceType.equals(MVCConstants.PRICE_TYPE_CLEARANCE_RESET)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "clearanceResetId", "commerce.error.clearance.not.existing");
		}

	}

}
