/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.domains.user.Card;
import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.domains.user.Role;
import com.punj.app.ecommerce.domains.user.User;
import com.punj.app.ecommerce.domains.user.UserDTO;
import com.punj.app.ecommerce.domains.user.UserRole;
import com.punj.app.ecommerce.domains.user.ids.CardId;
import com.punj.app.ecommerce.domains.user.ids.UserRoleId;
import com.punj.app.ecommerce.repositories.AddressRepository;
import com.punj.app.ecommerce.repositories.CardRepository;
import com.punj.app.ecommerce.repositories.PasswordRepository;
import com.punj.app.ecommerce.repositories.UserRepository;
import com.punj.app.ecommerce.repositories.user.RoleRepository;
import com.punj.app.ecommerce.repositories.user.UserRoleRepository;
import com.punj.app.ecommerce.repositories.user.UserSearchRepository;
import com.punj.app.ecommerce.services.UserService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.common.SetupService;
import com.punj.app.ecommerce.services.converter.UserConverter;
import com.punj.app.ecommerce.services.dtos.UserPrincipal;
import com.punj.app.ecommerce.utils.Pager;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger();
	private UserRepository userRepository;
	private UserSearchRepository userSearchRepository;
	private PasswordRepository passwordRepository;
	private AddressRepository addressRepository;
	private CardRepository cardRepository;
	private RoleRepository roleRepository;
	private UserRoleRepository userRoleRepository;
	private SetupService setupService;

	@Value("${commerce.list.max.perpage}")
	private Integer maxResultPerPage;

	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

	/**
	 * @param setupService
	 *            the setupService to set
	 */
	@Autowired
	public void setSetupService(SetupService setupService) {
		this.setupService = setupService;
	}

	/**
	 * @param roleRepository
	 *            the roleRepository to set
	 */
	@Autowired
	public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}

	/**
	 * @param roleRepository
	 *            the roleRepository to set
	 */
	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	/**
	 * @param userSearchRepository
	 *            the userSearchRepository to set
	 */
	@Autowired
	public void setUserSearchRepository(UserSearchRepository userSearchRepository) {
		this.userSearchRepository = userSearchRepository;
	}

	/**
	 * @param userRepository
	 *            the userRepository to set
	 */
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * @param passwordRepository
	 *            the passwordRepository to set
	 */
	@Autowired
	public void setPasswordRepository(PasswordRepository passwordRepository) {
		this.passwordRepository = passwordRepository;
	}

	/**
	 * @param addressRepository
	 *            the addressRepository to set
	 */
	@Autowired
	public void setAddressRepository(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	/**
	 * @param cardRepository
	 *            the cardRepository to set
	 */
	@Autowired
	public void setCardRepository(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserByUsername(String username) {
		return userRepository.findOne(username);
	}

	private User getUserDetails(String username) {

		User user = new User();
		user.setUsername(username);
		user.setStatus(ServiceConstants.STATUS_APPROVED);

		Password password = new Password();
		password.setStatus(ServiceConstants.STATUS_APPROVED);

		List<Password> passwordList = new ArrayList<>();

		user.setPasswords(passwordList);

		return userRepository.findOne(Example.of(user));
	}

	public boolean deleteUserByUsername(String username) {
		userRepository.delete(username);
		return true;
	}

	@Transactional
	private void deleteExistingRecords(String username) {

		Password passwordCriteria = new Password();
		passwordCriteria.setStatus(ServiceConstants.STATUS_APPROVED);
		passwordCriteria.setUsername(username);

		List<Password> passwords = this.passwordRepository.findAll(Example.of(passwordCriteria));
		if (passwords != null && !passwords.isEmpty()) {

			this.passwordRepository.delete(passwords);
			logger.info("The user passwords has been cleared successfully");

		}

		UserRole userRoleCriteria = new UserRole();
		UserRoleId userRoleIdCriteria = new UserRoleId();
		userRoleIdCriteria.setUsername(username);
		userRoleCriteria.setUserRoleId(userRoleIdCriteria);

		List<UserRole> userRoles = this.userRoleRepository.findAll(Example.of(userRoleCriteria));
		if (userRoles != null && !userRoles.isEmpty()) {
			this.userRoleRepository.delete(userRoles);
			logger.info("The user roles has been cleared successfully");
		}

	}

	public User saveUser(User user) {

		this.deleteExistingRecords(user.getUsername());

		user = this.userRepository.save(user);
		logger.info("The user has been saved successfully");

		return user;
	}

	public Card saveCard(Card card) {
		return cardRepository.save(card);
	}

	@Override
	public List<Password> getAllPasswordsByUsername(String username) {
		Password newPassword = new Password();
		newPassword.setUsername(username);
		return passwordRepository.findAll(Example.of(newPassword));
	}

	public Password updatePassword(UserDetails userDetails, Password pwd, String newPassword, String changedBy) {

		logger.info("The new details for updating current password are as follows 1 {} 2{} 3{} 4{} 5{}", pwd.getStatus(), pwd.getModifiedBy(), pwd.getUsername(), pwd.getPassword(),
				pwd.getModifiedDate());

		Password pwdSearch = new Password();
		if (userDetails == null || StringUtils.isBlank(userDetails.getUsername()))
			pwdSearch.setUsername("admin");
		else
			pwdSearch.setUsername(userDetails.getUsername());
		pwdSearch.setStatus("A");
		pwdSearch = passwordRepository.findOne(Example.of(pwdSearch));

		pwdSearch.setStatus("E");
		if (StringUtils.isNotEmpty(changedBy))
			pwdSearch.setModifiedBy(changedBy);
		else
			pwdSearch.setModifiedBy(pwd.getUsername());

		pwdSearch = passwordRepository.save(pwdSearch);

		Password changedPassword = new Password();

		changedPassword.setPassword(Utils.getPassEncoder().encode(newPassword));
		changedPassword.setModifiedDate(LocalDateTime.now());
		changedPassword.setUsername(pwdSearch.getUsername());
		if (StringUtils.isNotEmpty(changedBy))
			changedPassword.setModifiedBy(changedBy);
		else
			changedPassword.setModifiedBy(pwdSearch.getUsername());

		changedPassword.setStatus("A");
		logger.info("The new details after the changed password are as follows 1 {} 2{} 3{} 4{} 5{}", changedPassword.getStatus(), changedPassword.getModifiedBy(),
				changedPassword.getUsername(), changedPassword.getPassword(), changedPassword.getModifiedDate());
		changedPassword = passwordRepository.save(changedPassword);
		return changedPassword;
	}

	public void updateAddress(Address address) {

		addressRepository.save(address);

	}

	@Override
	public List<Address> getAddressByUsername(String username) {

		User userData = userRepository.findOne(username);
		List<Address> addresses = userData.getAddresses();
		logger.info("The {} number of addresses retrived for the user {}.", addresses.size(), username);
		return addresses;
	}

	@Override
	public Address getPrimaryAddressByUsername(String username) {

		User userData = userRepository.findOne(username);
		List<Address> addresses = userData.getAddresses();

		Address address = null;

		for (Address addObject : addresses) {
			if (addObject.getPrimary().equals("Y")) {
				address = addObject;
				break;
			}
		}

		logger.info("The primary addresses retrived for the user {}.", addresses.size(), username);
		return address;
	}

	@Override
	public Address getAddress(BigInteger addressId) {
		return this.addressRepository.findOne(addressId);
	}

	@Override
	public void deleteAddress(BigInteger addressId) {
		addressRepository.delete(addressId);
		logger.info("The address is deleted successfully.");
	}

	@Override
	public List<Card> getCardsByUsername(String username) {
		Card card = new Card();
		CardId cardId = new CardId();
		cardId.setUsername(username);
		card.setCardId(cardId);

		List<Card> cards = cardRepository.findAll(Example.of(card));
		logger.info("The card details retrieved successfully.");

		return cards;
	}

	@Override
	public void deleteCard(String username, String cardNo) {
		CardId cardId = new CardId();
		cardId.setUsername(username);
		cardId.setCardNo(cardNo);

		cardRepository.delete(cardId);
		logger.info("The card details has been deleted successfully.");
	}

	@Override
	public Card getCard(String username, String cardNo) {

		CardId cardId = new CardId();
		cardId.setUsername(username);
		cardId.setCardNo(cardNo);

		Card card = cardRepository.findOne(cardId);
		logger.info("The card details has been retrieved successfully.");

		return card;

	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		User user = null;

		if (this.setupService.isSetupGood()) {
			user = this.getUserDetails(username);
			if (user != null) {

				UserPrincipal userDetails = UserConverter.convertUser(user);
				if (userDetails != null) {
					return userDetails;

				} else {
					logger.info("The user password or user roles were not retrieved.");
					throw new UsernameNotFoundException(username);
				}

			} else {
				logger.info("The user basic details were not retrieved.");
				throw new UsernameNotFoundException(username);
			}
		} else {
			logger.info("The user basic details were not retrieved.");
			throw new UsernameNotFoundException(username);
		}

	}

	@Override
	public List<Role> getAllUserRoles() {

		List<Role> roles = this.roleRepository.findAll();
		if (roles != null && !roles.isEmpty())
			logger.info("The user roles has been retrieved successfully.");
		else
			logger.info("There are no user roles existing");
		return roles;
	}

	@Override
	public UserDTO searchUsers(String text, Pager pager) {

		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage;
		pager.setPageSize(maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(maxPageBtns);

		UserDTO userDTO = this.userSearchRepository.search(text, pager);
		if (userDTO != null && userDTO.getUsers() != null && !userDTO.getUsers().isEmpty())
			logger.info("The user details has been retrieved successfully.");
		else
			logger.info("There are no matching users existing");
		return userDTO;
	}

	@Override
	public UserDTO searchApprovedUsers(String text, Pager pager) {
		UserDTO userDTO = this.userSearchRepository.search(text, pager);
		if (userDTO != null && userDTO.getUsers() != null && !userDTO.getUsers().isEmpty())
			logger.info("The user details has been retrieved successfully.");
		else
			logger.info("There are no matching users existing");
		return userDTO;
	}

	@Override
	public User approveUser(String username, String actionBy) {
		User user = this.userRepository.findOne(username);
		if (user != null) {
			user.setModifiedBy(actionBy);
			user.setModifiedDate(LocalDateTime.now());
			user.setStatus(ServiceConstants.STATUS_APPROVED);
			user = this.userRepository.save(user);
			if (user != null)
				logger.info("The approval for '{}' user account was successful", user.getUsername());
		} else {
			logger.info("The user was not found in database based on the provided details");
		}
		return user;
	}

	@Override
	public User disableUser(String username, String actionBy) {
		User user = this.userRepository.findOne(username);
		if (user != null) {
			user.setModifiedBy(actionBy);
			user.setModifiedDate(LocalDateTime.now());
			user.setStatus(ServiceConstants.STATUS_DISABLE);
			user = this.userRepository.save(user);
			if (user != null)
				logger.info("The '{}' user account was successfully disabled", user.getUsername());
		} else {
			logger.info("The user was not found in database based on the provided details");
		}
		return user;
	}

	@Override
	public User activateUser(String username, String actionBy) {
		return this.approveUser(username, actionBy);
	}

	@Override
	public void deleteUser(String username, String actionBy) {
		this.userRepository.delete(username);
		logger.info("The deletion for '{}' user account was successful", username);
	}

	@Override
	@Transactional
	public List<User> approveAllUsers(List<String> usernameList, String actionBy) {
		List<User> userList = new ArrayList<>(usernameList.size());
		User user = null;

		for (String username : usernameList) {
			user = this.approveUser(username, actionBy);
			userList.add(user);
		}
		logger.info("The approval for all the provided user accounts was successful");
		return userList;
	}

	@Override
	@Transactional
	public List<User> disableAllUsers(List<String> usernameList, String actionBy) {
		List<User> userList = new ArrayList<>(usernameList.size());
		User user = null;
		for (String username : usernameList) {
			user = this.disableUser(username, actionBy);
			userList.add(user);
		}
		logger.info("The provided user accounts has been disabled successfully");
		return userList;
	}

	@Override
	@Transactional
	public void deleteAllUsers(List<String> usernameList, String actionBy) {
		for (String username : usernameList) {
			this.deleteUser(username, actionBy);
		}
		logger.info("The deletion for all the provided user accounts was successful");
	}

	@Override
	public User updateUserDetails(User user, String modifiedBy) {
		User retrievedUser = this.userRepository.findOne(user.getUsername());
		if (retrievedUser != null) {
			retrievedUser.setFirstname(retrievedUser.getFirstname());
			retrievedUser.setLastname(retrievedUser.getLastname());
			retrievedUser.setEmail(retrievedUser.getEmail());
			retrievedUser.setPhone1(retrievedUser.getPhone1());
			retrievedUser.setPhone2(retrievedUser.getPhone2());
			retrievedUser.setModifiedBy(modifiedBy);
			retrievedUser.setModifiedDate(LocalDateTime.now());

			retrievedUser = this.userRepository.save(retrievedUser);

			logger.info("The user updation is successful");
		} else {
			logger.error("The {} user was not found in database, hence cannot be updated", user.getUsername());

		}
		return retrievedUser;
	}

	@Override
	public List<UserRole> addUserRoles(List<UserRole> userRoles) {
		userRoles = this.userRoleRepository.save(userRoles);
		logger.info("The user role details has been saved successfully");
		return userRoles;
	}

}
