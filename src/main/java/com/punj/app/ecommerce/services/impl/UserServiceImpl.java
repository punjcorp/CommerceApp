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
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.domains.user.Card;
import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.domains.user.Role;
import com.punj.app.ecommerce.domains.user.User;
import com.punj.app.ecommerce.domains.user.ids.CardId;
import com.punj.app.ecommerce.domains.user.ids.PasswordId;
import com.punj.app.ecommerce.repositories.AddressRepository;
import com.punj.app.ecommerce.repositories.CardRepository;
import com.punj.app.ecommerce.repositories.PasswordRepository;
import com.punj.app.ecommerce.repositories.UserRepository;
import com.punj.app.ecommerce.services.UserService;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger();
	private UserRepository userRepository;
	private PasswordRepository passwordRepository;
	private AddressRepository addressRepository;
	private CardRepository cardRepository;

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
		
		User user=new User();
		user.setUsername(username);
		user.setEmail(username);
		
		Password password=new Password();
		password.setStatus("A");
		
		List<Password> passwordList=new ArrayList<Password>();
		
		user.setPasswords(passwordList);
		
		
		return userRepository.findOne(Example.of(user));
	}

	public boolean deleteUserByUsername(String username) {
		userRepository.delete(username);
		return true;
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public Card saveCard(Card card) {
		return cardRepository.save(card);
	}

	@Override
	public List<Password> getAllPasswordsByUsername(String username) {
		Password newPassword = new Password();
		PasswordId newPasswordId = new PasswordId();
		newPasswordId.setUsername(username);
		newPassword.setPasswordId(newPasswordId);
		List<Password> passwords = passwordRepository.findAll(Example.of(newPassword));
		return passwords;
	}

	public Password updatePassword(UserDetails userDetails,Password pwd, String newPassword, String changedBy) {

		logger.info("The new details for updating current password are as follows 1 {} 2{} 3{} 4{} 5{}",
				pwd.getStatus(), pwd.getModifiedBy(), pwd.getPasswordId().getUsername(),
				pwd.getPasswordId().getPassword(), pwd.getPasswordId().getModifiedDate());
		PasswordId passwordId=new PasswordId();
		passwordId.setPassword(userDetails.getPassword());
		passwordId.setUsername(userDetails.getUsername());
		pwd.setStatus("A");
		pwd.setPasswordId(passwordId);
		pwd=passwordRepository.findOne(Example.of(pwd));
		
		
		pwd.setStatus("E");
		if (StringUtils.isNotEmpty(changedBy))
			pwd.setModifiedBy(changedBy);
		else
			pwd.setModifiedBy(pwd.getPasswordId().getUsername());
		
		pwd = passwordRepository.save(pwd);

		Password changedPassword = new Password();
		PasswordId changedPasswordId = new PasswordId();

		changedPasswordId.setPassword(Utils.getPassEncoder().encode(newPassword));
		changedPasswordId.setModifiedDate(LocalDateTime.now());
		changedPasswordId.setUsername(pwd.getPasswordId().getUsername());
		if (StringUtils.isNotEmpty(changedBy))
			changedPassword.setModifiedBy(changedBy);
		else
			changedPassword.setModifiedBy(pwd.getPasswordId().getUsername());
		
		changedPassword.setStatus("A");
		changedPassword.setPasswordId(changedPasswordId);
		logger.info("The new details after the changed password are as follows 1 {} 2{} 3{} 4{} 5{}",
				changedPassword.getStatus(), changedPassword.getModifiedBy(),
				changedPassword.getPasswordId().getUsername(), changedPassword.getPasswordId().getPassword(),
				changedPassword.getPasswordId().getModifiedDate());
		passwordRepository.save(changedPassword);
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
		Address newAddress = addressRepository.findOne(addressId);
		return newAddress;
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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.getUserDetails(username);

		String password =null;
		List<Password> passwords = user.getPasswords();

		for (Password passwd : passwords) {
			if (passwd.getStatus().equals("A")) {
				password = passwd.getPasswordId().getPassword();
				break;
			}
		}

		List<Role> roles = user.getRoles();

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = null;
		for (Role role : roles) {
			authority = new SimpleGrantedAuthority(role.getName());
			authorities.add(authority);
		}

		
		return new org.springframework.security.core.userdetails.User(username, password, authorities); 
	}


}
