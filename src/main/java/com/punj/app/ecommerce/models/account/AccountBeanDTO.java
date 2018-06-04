/**
 * 
 */
package com.punj.app.ecommerce.models.account;

import java.util.List;

import javax.validation.Valid;

import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class AccountBeanDTO {

	private List<AccountBean> users;
	private List<String> usernames;
	private Pager pager;
	/**
	 * @return the users
	 */
	public List<AccountBean> getUsers() {
		return users;
	}
	/**
	 * @param users the users to set
	 */
	public void setUsers(List<AccountBean> users) {
		this.users = users;
	}
	/**
	 * @return the usernames
	 */
	public List<String> getUsernames() {
		return usernames;
	}
	/**
	 * @param usernames the usernames to set
	 */
	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}
	/**
	 * @return the pager
	 */
	public Pager getPager() {
		return pager;
	}
	/**
	 * @param pager the pager to set
	 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}

	

}
