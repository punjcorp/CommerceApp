package com.punj.app.ecommerce.controller.item;
/**
 * 
 */

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.models.item.HierarchyBean;
import com.punj.app.ecommerce.services.ItemService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageHierarchyController {
	private static final Logger logger = LogManager.getLogger();
	private ItemService itemService;

	/**
	 * @param userService
	 *            the userService to set
	 */
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping("/manage_hierarchy")
	public String showHierarchy(Model model, HttpSession session) {

		List<Hierarchy> deptList = itemService.retrieveAllDepts();

		logger.info("The dept list of all the {} departments has been retrieved successfully.", deptList.size());

		model.addAttribute("depts", deptList);
		model.addAttribute("hierarchyBean", new HierarchyBean());

		return "item/add_hierarchy";
	}

}
