/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.customer.CustomerDTO;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.domains.user.Role;
import com.punj.app.ecommerce.domains.user.User;
import com.punj.app.ecommerce.domains.user.UserDTO;
import com.punj.app.ecommerce.domains.user.UserRole;
import com.punj.app.ecommerce.domains.user.ids.UserRoleId;
import com.punj.app.ecommerce.models.account.AccountBean;
import com.punj.app.ecommerce.models.account.AccountBeanDTO;
import com.punj.app.ecommerce.models.account.RoleBean;
import com.punj.app.ecommerce.models.common.CommerceMultipartFile;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.customer.CustomerBean;
import com.punj.app.ecommerce.models.customer.CustomerBeanDTO;
import com.punj.app.ecommerce.models.financials.AccountHeadBean;
import com.punj.app.ecommerce.utils.Pager;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
public class AccountTransformer {

	private static final Logger logger = LogManager.getLogger();

	private AccountTransformer() {
		throw new IllegalStateException("AccountTransformer class");
	}

	public static List<RoleBean> transformRoles(List<Role> roles) {

		List<RoleBean> roleBeans = new ArrayList<>(roles.size());
		RoleBean roleBean;

		for (Role role : roles) {
			roleBean = AccountTransformer.transformRole(role);
			roleBeans.add(roleBean);
		}

		logger.info("The role list has been transformed to role bean list successfully");

		return roleBeans;
	}

	public static RoleBean transformRole(Role role) {

		RoleBean roleBean = new RoleBean();

		roleBean.setRoleId(role.getRoleId());
		roleBean.setName(role.getName());
		roleBean.setDescription(role.getDescription());

		logger.info("The role data has been transformed to role bean successfully");
		return roleBean;
	}

	public static AccountBean transformUser(User user) throws IOException {
		AccountBean accountBean = new AccountBean();

		accountBean.setStatus(user.getStatus());
		accountBean.setDisplayStatus(Utils.showStatus(user.getStatus()));
		accountBean.setUsername(user.getUsername());
		if (user.getUsername() != null)
			accountBean.setOrgUsername(user.getUsername());
		accountBean.setCreatedBy(user.getCreatedBy());
		accountBean.setDefaultLocationId(user.getDefaultLocationId());
		accountBean.setCreatedDate(user.getCreatedDate());
		accountBean.setLoginCount(user.getLoginCount());
		accountBean.setEmail(user.getEmail());
		accountBean.setFirstName(user.getFirstname());
		accountBean.setLastName(user.getLastname());
		accountBean.setPhone(user.getPhone1());
		accountBean.setPhone2(user.getPhone2());

		AccountTransformer.updateAccountPhoto(accountBean, user);

		AccountTransformer.updateAccountRoles(accountBean, user);

		logger.info("The user details has been transformed to account bean successfully");
		return accountBean;
	}

	public static void updateAccountRoles(AccountBean accountBean, User user) {
		List<UserRole> userRoles = user.getUserRoles();
		Integer[] selectedLocations = new Integer[userRoles.size()];
		Integer roleId = null;
		String roleName = null;

		int counter = 0;
		for (UserRole userRole : userRoles) {
			roleId = userRole.getUserRoleId().getRole().getRoleId();
			roleName = userRole.getUserRoleId().getRole().getName();
			selectedLocations[counter] = userRole.getUserRoleId().getLocationId();
			counter++;
		}
		accountBean.setSelectedLocationIds(selectedLocations);
		accountBean.setRoleId(roleId);
		accountBean.setRoleName(roleName);
		logger.info("The user role details has been transformed to account role details successfully");
	}

	public static void updateAccountPhoto(AccountBean accountBean, User user) throws IOException {

		accountBean.setPhotoType(user.getPhotoType());
		accountBean.setPhotoURL(user.getPhotoURL());
		if (StringUtils.isNotBlank(accountBean.getPhotoType())) {
			MultipartFile multipartFile = new CommerceMultipartFile(user.getPhoto(), user.getPhotoURL(), user.getPhotoType());
			accountBean.setImageData(multipartFile);

			byte[] imageData = accountBean.getImageData().getBytes();
			byte[] encodeBase64 = Base64.encodeBase64(imageData);
			String base64Encoded = new String(encodeBase64, "UTF-8");
			accountBean.setBaseEncodedImage(base64Encoded);
			logger.info("The account bean has been updated with encoded photo data to display on screen");

		}
		logger.info("The user photo has been transformed to account bean successfully");
	}

	public static User transformAccountBean(AccountBean accountBean) throws IOException {
		User user = new User();

		user.setCreatedBy(accountBean.getCreatedBy());
		user.setCreatedDate(accountBean.getCreatedDate());
		user.setEmail(accountBean.getEmail());
		user.setFirstname(accountBean.getFirstName());
		user.setLastname(accountBean.getLastName());
		user.setDefaultLocationId(accountBean.getDefaultLocationId());

		user.setPhone1(accountBean.getPhone());

		MultipartFile multipartFile = accountBean.getImageData();

		if (multipartFile != null) {
			user.setPhoto(multipartFile.getBytes());
			user.setPhotoType(multipartFile.getContentType());
			user.setPhotoURL(multipartFile.getOriginalFilename());

		}

		user.setStatus(accountBean.getStatus());
		user.setUsername(accountBean.getUsername());

		List<Password> passwords = AccountTransformer.transformAccountPassword(accountBean);
		user.setPasswords(passwords);

		List<UserRole> userRoles = AccountTransformer.transformAccountRoles(accountBean, user);
		user.setUserRoles(userRoles);

		logger.info("The account bean has been transformed to user object successfully");
		return user;
	}

	public static List<UserRole> transformAccountRoles(AccountBean accountBean, User user) {
		List<UserRole> userRoles = null;
		UserRole userRole = null;
		UserRoleId userRoleId = null;
		Integer[] selectedLocations = accountBean.getSelectedLocationIds();
		if (selectedLocations != null && selectedLocations.length > 0) {
			userRoles = new ArrayList<>(selectedLocations.length);
			for (Integer locationId : selectedLocations) {
				userRole = new UserRole();
				userRoleId = new UserRoleId();
				userRoleId.setLocationId(locationId);
				userRoleId.setUsername(accountBean.getUsername());

				Role role = new Role();
				role.setRoleId(accountBean.getRoleId());
				userRoleId.setRole(role);

				userRole.setUserRoleId(userRoleId);
				userRole.setCreatedBy(accountBean.getCreatedBy());
				userRole.setCreatedDate(accountBean.getCreatedDate());

				// This has to come from screen in case of temp employees
				userRole.setBeginDate(null);
				userRole.setEndDate(null);

				userRoles.add(userRole);

			}
			logger.info("The account roles bean details has been transformed to user roles successfully");
		}
		return userRoles;
	}

	public static List<Password> transformAccountPassword(AccountBean accountBean) {

		List<Password> passwords = new ArrayList<>();

		Password newPassword = new Password();
		newPassword.setModifiedBy(accountBean.getCreatedBy());
		newPassword.setStatus(MVCConstants.STATUS_ACTIVE);
		newPassword.setPasswordId(accountBean.getPasswordId());

		newPassword.setPassword(Utils.encodePassword(accountBean.getPassword()));
		newPassword.setUsername(accountBean.getUsername());
		newPassword.setModifiedDate(LocalDateTime.now());

		passwords.add(newPassword);
		logger.info("The account password bean details has been transformed to user password successfully");
		return passwords;
	}
	
	public static List<Password> transformAccountPasswordWithoutEncoding(AccountBean accountBean) {

		List<Password> passwords = new ArrayList<>();

		Password newPassword = new Password();
		newPassword.setModifiedBy(accountBean.getCreatedBy());
		newPassword.setStatus(MVCConstants.STATUS_ACTIVE);
		newPassword.setPasswordId(accountBean.getPasswordId());

		newPassword.setPassword(accountBean.getPassword());
		newPassword.setUsername(accountBean.getUsername());
		newPassword.setModifiedDate(LocalDateTime.now());

		passwords.add(newPassword);
		logger.info("The account password bean details has been transformed to user password successfully");
		return passwords;
	}
	

	public static AccountBeanDTO transformUserDTO(UserDTO userDTO) throws IOException {
		AccountBeanDTO accountBeanDTO = null;
		List<AccountBean> accountBeans = null;
		if (userDTO != null && userDTO.getUsers() != null && !userDTO.getUsers().isEmpty()) {
			accountBeanDTO = new AccountBeanDTO();
			accountBeans = AccountTransformer.transformUsers(userDTO.getUsers());

			accountBeanDTO.setUsers(accountBeans);

			Pager tmpPager = userDTO.getPager();
			Pager pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(), tmpPager.getMaxDisplayPage(),
					ViewPathConstants.MANAGE_ACCOUNTS_URL);
			accountBeanDTO.setPager(pager);
			logger.info("The user DTO has been transformed to account DTO successfully");
		} else {
			logger.info("The user DTO was not found to be transformed to account DTO");
		}
		return accountBeanDTO;
	}

	public static List<AccountBean> transformUsers(List<User> users) throws IOException {
		List<AccountBean> accountBeans = new ArrayList<>(users.size());
		AccountBean accountBean = null;
		for (User user : users) {
			accountBean = AccountTransformer.transformUser(user);
			accountBeans.add(accountBean);
		}
		logger.info("All the users has been transformed successfully");
		return accountBeans;
	}

	public static List<String> retrieveEligibleUsers(AccountBeanDTO users, String action) {
		List<String> selectedUserNames = users.getUsernames();
		List<String> usernameList = null;
		List<AccountBean> userList = users.getUsers();
		AccountBean userAccount = null;
		String username = null;
		Integer userIndex = null;

		if (selectedUserNames != null && !selectedUserNames.isEmpty()) {
			usernameList = new ArrayList<>(selectedUserNames.size());
			String[] splittedVals = null;
			for (String selectedUserName : selectedUserNames) {
				splittedVals = selectedUserName.split("_");
				username = splittedVals[0];
				userIndex = new Integer(splittedVals[1]);
				userAccount = userList.get(userIndex);
				if (action.equals(MVCConstants.ACTION_APPROVE_ALL) || action.equals(MVCConstants.ACTION_DELETE_ALL)) {
					if (userAccount.getStatus().equals(MVCConstants.STATUS_CREATED)) {
						usernameList.add(username);
					}
				} else if (action.equals(MVCConstants.ACTION_DISABLE_ALL)) {
					if (userAccount.getStatus().equals(MVCConstants.STATUS_APPROVED)) {
						usernameList.add(username);
					}
				}

			}
		}
		logger.info("The select usernames and index for bulk operations has been transformed successfully");
		return usernameList;
	}

	public static void updateSearchCriteria(HttpSession session, RedirectAttributes redirectAttrs) {

		SearchBean searchBean = (SearchBean) session.getAttribute(MVCConstants.SCREEN_MANAGE_ACCOUNTS_SEARCH_CRITERIA);
		Optional<Integer> page = (Optional<Integer>) session.getAttribute(MVCConstants.SCREEN_MANAGE_ACCOUNTS_SEARCH_CRITERIA_PAGE);
		if (searchBean != null) {
			redirectAttrs.addFlashAttribute(MVCConstants.SEARCH_BEAN, searchBean);
			redirectAttrs.addFlashAttribute(MVCConstants.PAGE_PARAM, page);
			logger.info("The search criteria for manage Accounts screen has been updated successfully");
		}

	}

	public static CustomerBeanDTO transformCustomerDTO(CustomerDTO customerDTO) {

		CustomerBeanDTO customerBeanDTO = new CustomerBeanDTO();
		List<Customer> customers = customerDTO.getCustomers();
		List<CustomerBean> customerBeans = null;

		if (customers != null && !customers.isEmpty()) {
			customerBeans = AccountTransformer.transformCustomers(customers);
			customerBeanDTO.setCustomers(customerBeans);
			customerBeanDTO.setPager(customerDTO.getPager());
		}

		logger.info("The customer details has been transformed to customer bean DTO");
		return customerBeanDTO;
	}

	public static List<CustomerBean> transformCustomersWithAccounts(List<Customer> customers, Map<BigInteger, List<AccountHead>> accounts) {
		List<CustomerBean> customerBeanList = new ArrayList<>(customers.size());
		CustomerBean customerBean = null;
		List<AccountHead> customerAccounts = null;
		List<AccountHeadBean> customerAccountBeans = null;
		for (Customer customer : customers) {
			customerBean = AccountTransformer.transformCustomer(customer);
			customerAccounts = accounts.get(customerBean.getCustomerId());
			customerAccountBeans=SupplierTransformer.transformAccountHeads(customerAccounts, customerBean.getName());
			customerBean.setCustomerAccounts(customerAccountBeans);

			customerBeanList.add(customerBean);
		}
		logger.info("The customer details list has been transformed to customer bean list successfully");
		return customerBeanList;
	}

	public static List<CustomerBean> transformCustomers(List<Customer> customers) {
		List<CustomerBean> customerBeanList = new ArrayList<>(customers.size());
		CustomerBean customerBean = null;
		for (Customer customer : customers) {
			customerBean = AccountTransformer.transformCustomer(customer);
			customerBeanList.add(customerBean);
		}
		logger.info("The customer details list has been transformed to customer bean list successfully");
		return customerBeanList;
	}

	public static CustomerBean transformCustomer(Customer customer) {
		CustomerBean customerBean = new CustomerBean();

		customerBean.setCreatedBy(customer.getCreatedBy());
		customerBean.setCreatedDate(customer.getCreatedDate());
		customerBean.setCustomerId(customer.getCustomerId());
		customerBean.setCustomerType("C");
		customerBean.setEmail(customer.getEmail());
		customerBean.setName(customer.getName());
		customerBean.setPhone(customer.getPhone());

		logger.info("The customer details has been transformed to customer bean successfully");
		return customerBean;
	}

}
