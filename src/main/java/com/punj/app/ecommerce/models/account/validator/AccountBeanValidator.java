/**
 * 
 */
package com.punj.app.ecommerce.models.account.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.models.account.AccountBean;

/**
 * @author admin
 *
 */
@Component
public class AccountBeanValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	@Override
	public boolean supports(Class<?> clazz) {
		return AccountBean.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {

		AccountBean accountBean = (AccountBean) obj;
		String accountPassword = accountBean.getPassword();
		String accountConfirmPassword = accountBean.getConfirmPassword();
		String phone = accountBean.getPhone();
		String email = accountBean.getEmail();

		if (StringUtils.isNotBlank(accountPassword) && StringUtils.isNotBlank(accountConfirmPassword) && !accountPassword.equals(accountConfirmPassword)) {
			errors.rejectValue("password", "commerce.error.confirm.password");
		}

		if (StringUtils.isNotBlank(phone) && phone.length() < 7 && phone.length() > 10) {
			errors.rejectValue("phone", "commerce.error.int.size.args",new Object[] {"7", "10"},"The entered value length should be between 7 and 10 digits");
		}

		if (StringUtils.isNotBlank(phone) && (phone.length() < 7 || phone.length() > 10)) {
			errors.rejectValue("phone", "commerce.error.int.size.args",new Object[] {"7", "10"},"The entered value length should be between 7 and 10 digits");
		}

		if (StringUtils.isNotBlank(email) && (email.length() < 6 || email.length() > 100)) {
			errors.rejectValue("email", "commerce.error.string.size.args",new Object[] {"6", "100"},"The entered value length should be between 6 and 100 characters");
		} else if (StringUtils.isNotBlank(email) && (email.length() >= 6 || email.length() <= 100)) {
			pattern = Pattern.compile(MVCConstants.EMAIL_PATTERN);
			matcher = pattern.matcher(email);
			if (!matcher.matches()) {
				errors.rejectValue("email", "commerce.error.email");
			}
		}
	}

}
