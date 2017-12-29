package com.punj.app.ecommerce.controller.supplier;
/**
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.supplier.SupplierDTO;
import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.supplier.SupplierBean;
import com.punj.app.ecommerce.models.supplier.SupplierBeanDTO;
import com.punj.app.ecommerce.services.SupplierService;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageSupplierController {
	private static final Logger logger = LogManager.getLogger();
	private SupplierService supplierService;
	private MessageSource messageSource;

	/**
	 * @param supplierService
	 *            the supplierService to set
	 */
	@Autowired
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(value = "/add_supplier")
	public String addSupplier(Model model, HttpSession session) {
		logger.info("The add method for new supplier has been called");
		try {
			SupplierBean supplierBean = new SupplierBean();

			model.addAttribute("supplierBean", supplierBean);
			logger.info("The empty supplier object bean has been created");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while creating empty supplier.", e);
			return "error";
		}

		return "supplier/add_supplier";

	}

	@PostMapping(value = "/add_supplier", params = { "addSupplierAddress" })
	public String addRow(@ModelAttribute SupplierBean supplierBean, Model model, final BindingResult bindingResult) {
		supplierBean.getAddresses().add(new AddressBean());
		model.addAttribute("supplierBean", supplierBean);
		return "supplier/add_supplier";
	}

	@PostMapping(value = "/add_supplier", params = { "removeSupplierAddress" })
	public String removeRow(@ModelAttribute SupplierBean supplierBean, Model model, final BindingResult bindingResult,
			final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("id"));
		supplierBean.getAddresses().remove(rowId.intValue());
		return "supplier/add_supplier";
	}

	@PostMapping(value = "/add_supplier", params = { "saveSupplier" })
	public String saveSupplier(@ModelAttribute @Valid SupplierBean supplierBean, BindingResult bindingResult,
			Model model, HttpSession session, Locale locale) {
		if (bindingResult.hasErrors())
			return "supplier/add_supplier";
		try {
			Supplier supplier = new Supplier();
			this.updateDomain(supplierBean, supplier);

			supplier = supplierService.createSupplier(supplier);
			model.addAttribute("success", messageSource.getMessage("commerce.screen.supplier.add.success",
					new Object[] { supplier.getSupplierId() }, locale));
			model.addAttribute("supplierBean", supplierBean);
			logger.info("The supplier details has been created successfully.");
		} catch (Exception e) {
			logger.error("There is an error while updating address", e);
			return "error";
		}
		return "supplier/add_supplier";
	}

	/**
	 * This method is used to set the Domain objects so that the data can be
	 * persisted to the database
	 * 
	 * @param supplierBean
	 * @param supplier
	 */
	private void updateDomain(SupplierBean supplierBean, Supplier supplier) {

		supplier.setSupplierId(supplierBean.getSupplierId());
		supplier.setName(supplierBean.getName());
		supplier.setEmail(supplierBean.getEmail());
		supplier.setPhone1(supplierBean.getPhone1());
		supplier.setPhone2(supplierBean.getPhone2());

		List<Address> supplierAddresses = new ArrayList<Address>();
		List<AddressBean> addresses = supplierBean.getAddresses();

		Supplier newSupplier = new Supplier();

		List<Address> supplierAddressList = new ArrayList<Address>();
		Address address = null;
		for (AddressBean addressBean : addresses) {

			address = new Address();

			address.setPrimary("Y");
			address.setAddressId(addressBean.getAddressId());
			address.setAddress1(addressBean.getAddress1());
			address.setAddress2(addressBean.getAddress2());
			address.setCity(addressBean.getCity());
			address.setState(addressBean.getState());
			address.setCountry(addressBean.getCountry());
			address.setPincode(addressBean.getPincode());
			address.setAddressType(addressBean.getAddressType());

			supplierAddressList.add(address);
		}
		supplier.setAddresses(supplierAddressList);

		logger.info("The supplier details has been updated in domain object now");

	}

	@GetMapping(value = "/manage_supplier")
	public String manageSupplier(Model model, HttpSession session) {
		logger.info("The add method for new supplier has been called");
		try {

			SupplierBean supplierBean = new SupplierBean();
			model.addAttribute("supplierBean", supplierBean);

			List<Supplier> supplierList = supplierService.getAll();
			SupplierBeanDTO suppliers = new SupplierBeanDTO();

			this.setSupplierList(supplierList, suppliers);

			model.addAttribute("suppliers", suppliers);

			logger.info("The empty supplier object bean has been created");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while creating empty supplier.", e);
			return "error";
		}

		return "supplier/manage_supplier";

	}

	private void setSupplierList(List<Supplier> supplierList, SupplierBeanDTO suppliers) {

		List<SupplierBean> supplierBeanList = new ArrayList<SupplierBean>();

		for (Supplier supplier : supplierList) {
			SupplierBean supplierBean = new SupplierBean();
			this.updateBean(supplierBean, supplier);
			supplierBeanList.add(supplierBean);
		}
		suppliers.setSuppliers(supplierBeanList);
		logger.info("The supplier details has been added to the DTO object");
	}

	@PostMapping("/search_supplier")
	public String searchSupplier(@ModelAttribute SupplierBean supplierBean,
			@RequestParam("page") Optional<Integer> page, Model model, HttpSession session) {
		try {

			Pager pager = supplierBean.getPager();
			pager = new Pager();
			if (page == null || !page.isPresent()) {
				pager.setCurrentPageNo(1);
			}else {
				pager.setCurrentPageNo(page.get());
			}
			
			

			SupplierDTO supplierList = supplierService.searchSupplier(supplierBean.getName(), pager);
			List<Supplier> suppliersList = supplierList.getSuppliers();

			SupplierBeanDTO suppliers = new SupplierBeanDTO();

			this.setSupplierList(suppliersList, suppliers);

			Pager tmpPager = supplierList.getPager();
			pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(),
					tmpPager.getMaxDisplayPage());

			model.addAttribute("suppliers", suppliers);
			model.addAttribute("supplierBean", supplierBean);
			model.addAttribute("pager", pager);
			model.addAttribute("success", "The {" + pager.getResultSize() + "} supplier record has been retrieved");
			logger.info("The supplier details has been retrieved successfully.");
		} catch (Exception e) {
			logger.error("There is an error while updating address", e);
			return "error";
		}
		return "supplier/manage_supplier";
	}

	/**
	 * This method is used to set the details of retrieved suppliers to Bean object
	 * so the details can be displayed on the screen
	 * 
	 * @param supplierBean
	 * @param supplier
	 */
	private void updateBean(SupplierBean supplierBean, Supplier supplier) {

		supplierBean.setSupplierId(supplier.getSupplierId());
		supplierBean.setName(supplier.getName());
		supplierBean.setEmail(supplier.getEmail());
		supplierBean.setPhone1(supplier.getPhone1());
		supplierBean.setPhone2(supplier.getPhone2());

		List<Address> supplierAddressList = supplier.getAddresses();
		List<AddressBean> supplierAddresses = new ArrayList<AddressBean>();

		AddressBean addressBean = null;
		if (supplierAddressList != null && supplierAddressList.size() > 0) {
			for (Address address : supplierAddressList) {

				addressBean = new AddressBean();

				addressBean.setAddressId(address.getAddressId());
				addressBean.setPrimary(address.getPrimary());
				addressBean.setAddress1(address.getAddress1());
				addressBean.setAddress2(address.getAddress2());
				addressBean.setCity(address.getCity());
				addressBean.setState(address.getState());
				addressBean.setCountry(address.getCountry());
				addressBean.setPincode(address.getPincode());
				addressBean.setAddressType(address.getAddressType());

				supplierAddresses.add(addressBean);
			}
		}
		supplierBean.setAddresses(supplierAddresses);

		logger.info("The supplier details has been updated in bean object now");

	}

	@GetMapping("/edit_supplier")
	public String editSupplier(Model model, HttpSession session, final HttpServletRequest req) {
		try {
			Integer supplierId = Integer.valueOf(req.getParameter("supplierId"));

			SupplierBean supplierBean = new SupplierBean();
			Supplier supplier = new Supplier();
			supplier.setSupplierId(supplierId);

			supplier = supplierService.searchSupplier(supplier);
			logger.info("The requested supplier details retrieved successfully");

			this.updateBean(supplierBean, supplier);

			model.addAttribute("supplierBean", supplierBean);
			logger.info("The requested supplier for updation has been set in bean object for display");
		} catch (Exception e) {
			logger.error("There is an error while updating address", e);
			return "error";
		}
		return "supplier/edit_supplier";
	}

	@PostMapping(value = "/edit_supplier", params = { "addSupplierAddress" })
	public String addRowForEdit(@ModelAttribute SupplierBean supplierBean, Model model,
			final BindingResult bindingResult) {
		supplierBean.getAddresses().add(new AddressBean());
		model.addAttribute("supplierBean", supplierBean);
		logger.info("A new address during for Supplier Edit screen has been added.");
		return "supplier/edit_supplier";
	}

	@PostMapping(value = "/edit_supplier", params = { "removeSupplierAddress" })
	public String removeRowForEdit(@ModelAttribute SupplierBean supplierBean, Model model,
			final BindingResult bindingResult, final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("id"));

		AddressBean addressBean = supplierBean.getAddresses().get(rowId);
		Address supplierAddress = new Address();
		this.updateSupplierAddressDomain(supplierBean.getSupplierId(), addressBean, supplierAddress);

		supplierService.deleteAddress(supplierAddress);
		logger.info("The selected address for supplier has been deleted.");

		supplierBean.getAddresses().remove(rowId.intValue());
		logger.info("The selected address during for Supplier Edit screen has been deleted.");
		return "supplier/edit_supplier";
	}

	private void updateSupplierAddressDomain(Integer supplierId, AddressBean addressBean, Address address) {

		address.setAddressId(addressBean.getAddressId());

		Supplier supplier = new Supplier();
		supplier.setSupplierId(supplierId);

	}

	@PostMapping(value = "/edit_supplier", params = { "saveSupplier" })
	public String saveSupplierAfterEdit(@ModelAttribute @Valid SupplierBean supplierBean, BindingResult bindingResult,
			Model model, HttpSession session, Locale locale) {
		if (bindingResult.hasErrors())
			return "supplier/edit_supplier";
		try {
			Supplier supplier = new Supplier();
			this.updateDomain(supplierBean, supplier);

			supplier = supplierService.createSupplier(supplier);
			model.addAttribute("success", messageSource.getMessage("commerce.screen.supplier.add.success",
					new Object[] { supplier.getSupplierId() }, locale));
			model.addAttribute("supplierBean", supplierBean);
			logger.info("The supplier details has been saved after edit successfully.");
		} catch (Exception e) {
			logger.error("There is an error while updating address", e);
			return "error";
		}
		return "supplier/edit_supplier";
	}

	@GetMapping("/delete_supplier")
	public String deleteSupplier(Model model, HttpSession session, final HttpServletRequest req) {
		try {
			Integer supplierId = Integer.valueOf(req.getParameter("supplierId"));

			supplierService.delete(supplierId);

			logger.info("The requested supplier for deletion has been deleted successfully");
		} catch (Exception e) {
			logger.error("There is an error while deleteing supplier", e);
			return "error";
		}
		return "supplier/manage_supplier";
	}

	@PostMapping(value = "/bulk_supplier_action", params = { "saveSuppliers" })
	public String saveSuppliers(@ModelAttribute SupplierBeanDTO suppliers, Model model, HttpSession session) {
		try {

			List<SupplierBean> supplierBeans = suppliers.getSuppliers();

			List<String> supplierIds = suppliers.getSupplierIds();
			Map<Integer, Integer> idIndex = new HashMap<Integer, Integer>(supplierIds.size());

			this.getSelectedIds(supplierIds, idIndex);

			List<SupplierBean> finalSupplierBeans = new ArrayList<SupplierBean>(idIndex.size());

			Set<Integer> ids = idIndex.keySet();
			Integer index = null;
			SupplierBean tmpSupplierBean = null;
			for (Integer id : ids) {
				index = idIndex.get(id);
				tmpSupplierBean = supplierBeans.get(index);
				tmpSupplierBean.setSupplierId(id);
				finalSupplierBeans.add(tmpSupplierBean);
			}
			logger.info("The modified list of suppliers has been updated in beans");

			List<Supplier> supplierList = new ArrayList<Supplier>(finalSupplierBeans.size());
			Supplier supplier = null;
			for (SupplierBean supplierBean : finalSupplierBeans) {
				supplier = new Supplier();
				this.updateDomain(supplierBean, supplier);
				supplierList.add(supplier);
			}
			logger.info("The supplier details has been updated in Domain objects");

			supplierService.updateSupplier(supplierList);
			model.addAttribute("success", "The selected suppliers basic details has been updated");
			logger.info("The selected suppliers basic details has been updated successfully.");
		} catch (Exception e) {
			logger.error("There is an error while updating supplier basic details", e);
			return "error";
		}
		return "supplier/manage_supplier";
	}

	@PostMapping(value = "/bulk_supplier_action", params = { "deleteSuppliers" })
	public String deleteSuppliers(@ModelAttribute SupplierBeanDTO suppliers, Model model, HttpSession session) {
		try {
			List<String> supplierIds = suppliers.getSupplierIds();

			Map<Integer, Integer> idIndexMap = new HashMap<Integer, Integer>(supplierIds.size());

			this.getSelectedIds(supplierIds, idIndexMap);

			Set<Integer> ids = idIndexMap.keySet();

			supplierService.deleteSuppliers(ids);
			model.addAttribute("success", "The supplier record has been retrieved");
			logger.info("The supplier details has been created successfully.");
		} catch (Exception e) {
			logger.error("There is an error while updating address", e);
			return "error";
		}
		return "supplier/manage_supplier";
	}

	private void getSelectedIds(List<String> supplierIds, Map<Integer, Integer> idIndex) {
		String splittedData[] = null;
		for (String id : supplierIds) {
			splittedData = id.split("_");
			idIndex.put(new Integer(splittedData[0]), new Integer(splittedData[1]));
		}

		logger.info("The supplier ids and list index data has been seperated");
	}

}
