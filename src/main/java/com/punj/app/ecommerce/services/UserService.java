/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.domains.user.Card;
import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.domains.user.User;

/**
 * @author admin
 *
 */
public interface UserService extends UserDetailsService {

	public Iterable<User> getAllUsers();

	public User getUserByUsername(String username);

	public User saveUser(User user);

	public Card saveCard(Card card);

	public List<Password> getAllPasswordsByUsername(String username);

	public Password updatePassword(UserDetails userDetails, Password pwd, String newPassword, String changedBy);

	public void updateAddress(Address address);

	public List<Address> getAddressByUsername(String username);

	public Address getPrimaryAddressByUsername(String username);

	public Address getAddress(BigInteger addressId);

	public void deleteAddress(BigInteger addressId);

	public List<Card> getCardsByUsername(String username);

	public void deleteCard(String username, String cardNo);

	public Card getCard(String username, String cardNo);

}
