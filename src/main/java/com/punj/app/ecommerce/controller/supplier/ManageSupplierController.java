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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.supplier.SupplierDTO;
import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.common.SearchBean;
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

	@GetMapping(value = ViewPathConstants.ADD_SUPPLIER_URL)
	public String addSupplier(Model model, HttpSession session) {
		logger.info("The add method for new supplier has been called");
		try {
			SupplierBean supplierBean = new SupplierBean();

			model.addAttribute(MVCConstants.SUPPLIER_BEAN, supplierBean);
			logger.info("The empty supplier object bean has been created");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while creating empty supplier.", e);
			return ViewPathConstants.ERROR_PAGE;
		}

		return ViewPathConstants.ADD_SUPPLIER_PAGE;

	}

	@PostMapping(value = ViewPathConstants.ADD_SUPPLIER_URL, params = { MVCConstants.ADD_SUPPLIER_ADDRESS_PARAM })
	public String addRow(@ModelAttribute SupplierBean supplierBean, Model model, final BindingResult bindingResult) {
		supplierBean.getAddresses().add(new AddressBean());
		model.addAttribute(MVCConstants.SUPPLIER_BEAN, supplierBean);
		return ViewPathConstants.ADD_SUPPLIER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_SUPPLIER_URL, params = { MVCConstants.REMOVE_SUPPLIER_ADDRESS_PARAM })
	public String removeRow(@ModelAttribute SupplierBean supplierBean, Model model, final BindingResult bindingResult, final HttpServletRequest req) {
		final Integer rowId = Integer.parseInt(req.getParameter(MVCConstants.ID_PARAM));
		supplierBean.getAddresses().remove(rowId.intValue());
		return ViewPathConstants.ADD_SUPPLIER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_SUPPLIER_URL, params = { MVCConstants.SAVE_SUPPLIER_PARAM })
	public String saveSupplier(@ModelAttribute @Valid SupplierBean supplierBean, BindingResult bindingResult, Model model, HttpSession session, Locale locale) {
		if (bindingResult.hasErrors())
			return ViewPathConstants.ADD_SUPPLIER_PAGE;
		try {
			Supplier supplier = new Supplier();
			this.updateDomain(supplierBean, supplier);

			supplier = this.supplierService.createSupplier(supplier);
			supplierBean.setSupplierId(supplier.getSupplierId());
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.supplier.add.success", new Object[] { supplier.getSupplierId() }, locale));
			model.addAttribute(MVCConstants.SUPPLIER_BEAN, supplierBean);
			logger.info("The supplier details has been created successfully.");
		} catch (Exception e) {
			logger.error("There is an error while updating supplier details", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.ADD_SUPPLIER_PAGE;
	}

	/**
	 * This method is used to set the Domain objects so that the data can be persisted to the database
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

		List<AddressBean> addresses = supplierBean.getAddresses();

		List<Address> supplierAddressList = new ArrayList<>();
		Address address = null;
		for (AddressBean addressBean : addresses) {

			address = new Address();

			address.setPrimary(MVCConstants.YES);
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

	@GetMapping(value = ViewPathConstants.MANAGE_SUPPLIER_URL)
	public String manageSupplier(Model model, HttpSession session, final HttpServletRequest req) {
		logger.info("The manage supplier method for supplier management has been called.");
		model.addAttribute("searchBean", new SearchBean());
		return ViewPathConstants.MANAGE_SUPPLIER_PAGE;

	}

	private void setSupplierList(List<Supplier> supplierList, SupplierBeanDTO suppliers) {

		List<SupplierBean> supplierBeanList = new ArrayList<>();

		for (Supplier supplier : supplierList) {
			SupplierBean supplierBean = new SupplierBean();
			this.updateBean(supplierBean, supplier);
			supplierBeanList.add(supplierBean);
		}
		suppliers.setSuppliers(supplierBeanList);
		logger.info("The supplier details has been added to the DTO object");
	}

	@PostMapping(ViewPathConstants.MANAGE_SUPPLIER_URL)
	public String searchSupplierDetails(@ModelAttribute @Valid SearchBean searchBean, BindingResult bindingResult,
			@RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, Model model, HttpSession session) {
		if (bindingResult.hasErrors() && !model.containsAttribute(MVCConstants.SUCCESS)) {
			return ViewPathConstants.MANAGE_SUPPLIER_PAGE;
		}
		try {
			if (!model.containsAttribute(MVCConstants.SUCCESS)) {

				Pager pager = new Pager();
				if (!page.isPresent()) {
					pager.setCurrentPageNo(1);
				} else {
					pager.setCurrentPageNo(page.get());
				}

				SupplierDTO supplierList = this.supplierService.searchSupplier(searchBean.getSearchText(), pager);
				List<Supplier> suppliersList = supplierList.getSuppliers();

				SupplierBeanDTO suppliers = new SupplierBeanDTO();

				this.setSupplierList(suppliersList, suppliers);

				Pager tmpPager = supplierList.getPager();
				pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(), tmpPager.getMaxDisplayPage(),
						ViewPathConstants.MANAGE_SUPPLIER_URL);

				model.addAttribute(MVCConstants.SUPPLIERS_BEAN, suppliers);
				model.addAttribute(MVCConstants.PAGER, pager);
				model.addAttribute(MVCConstants.SUCCESS, "The {" + pager.getResultSize() + "} supplier record has been retrieved");
				logger.info("The supplier details has been retrieved successfully.");
			} else {
				model.addAttribute("org.springframework.validation.BindingResult.searchBean", null);
			}
			model.addAttribute(MVCConstants.SUPPLIER_BEAN, searchBean);
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, "There is some error while retrieving suppliers");
			logger.error("There is an error while searching for suppliers", e);
		}
		return ViewPathConstants.MANAGE_SUPPLIER_PAGE;
	}

	/**
	 * This method is used to set the details of retrieved suppliers to Bean object so the details can be displayed on the screen
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
		List<AddressBean> supplierAddresses = new ArrayList<>();

		AddressBean addressBean = null;
		if (supplierAddressList != null && !supplierAddressList.isEmpty()) {
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

	@GetMapping(ViewPathConstants.EDIT_SUPPLIER_URL)
	public String editSupplier(Model model, HttpSession session, final HttpServletRequest req) {
		try {
			Integer supplierId = Integer.valueOf(req.getParameter(MVCConstants.SUPPLIER_ID_PARAM));

			SupplierBean supplierBean = new SupplierBean();
			Supplier supplier = new Supplier();
			supplier.setSupplierId(supplierId);

			supplier = this.supplierService.searchSupplier(supplier);
			logger.info("The requested supplier details retrieved successfully");

			this.updateBean(supplierBean, supplier);

			model.addAttribute(MVCConstants.SUPPLIER_BEAN, supplierBean);
			logger.info("The requested supplier for updation has been set in bean object for display");
		} catch (Exception e) {
			logger.error("There is an error while retrieving supplier for editing", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.EDIT_SUPPLIER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_SUPPLIER_URL, params = { MVCConstants.ADD_SUPPLIER_ADDRESS_PARAM })
	public String addRowForEdit(@ModelAttribute SupplierBean supplierBean, Model model, final BindingResult bindingResult) {
		supplierBean.getAddresses().add(new AddressBean());
		model.addAttribute(MVCConstants.SUPPLIER_BEAN, supplierBean);
		logger.info("A new address during for Supplier Edit screen has been added.");
		return ViewPathConstants.EDIT_SUPPLIER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_SUPPLIER_URL, params = { MVCConstants.REMOVE_SUPPLIER_ADDRESS_PARAM })
	public String removeRowForEdit(@ModelAttribute SupplierBean supplierBean, Model model, final BindingResult bindingResult, final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter(MVCConstants.ID_PARAM));

		AddressBean addressBean = supplierBean.getAddresses().get(rowId);
		Address supplierAddress = new Address();
		this.updateSupplierAddressDomain(supplierBean.getSupplierId(), addressBean, supplierAddress);

		this.supplierService.deleteAddress(supplierAddress, supplierBean.getSupplierId());
		logger.info("The selected address for supplier has been deleted.");

		supplierBean.getAddresses().remove(rowId.intValue());
		logger.info("The selected address during for Supplier Edit screen has been deleted.");
		return ViewPathConstants.EDIT_SUPPLIER_PAGE;
	}

	private void updateSupplierAddressDomain(Integer supplierId, AddressBean addressBean, Address address) {

		address.setAddressId(addressBean.getAddressId());

		Supplier supplier = new Supplier();
		supplier.setSupplierId(supplierId);

	}

	@PostMapping(value = ViewPathConstants.EDIT_SUPPLIER_URL, params = { MVCConstants.SAVE_SUPPLIER_PARAM })
	public String saveSupplierAfterEdit(@ModelAttribute @Valid SupplierBean supplierBean, BindingResult bindingResult, Model model, HttpSession session,
			Locale locale) {
		if (bindingResult.hasErrors())
			return ViewPathConstants.EDIT_SUPPLIER_PAGE;
		try {
			Supplier supplier = new Supplier();
			this.updateDomain(supplierBean, supplier);

			supplier = this.supplierService.createSupplier(supplier);
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.supplier.add.success", new Object[] { supplier.getSupplierId(), supplier.getName() }, locale));
			model.addAttribute(MVCConstants.SUPPLIER_BEAN, supplierBean);
			logger.info("The supplier details has been saved after edit successfully.");
		} catch (Exception e) {
			logger.error("There is an error while saving supplier after editing", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.EDIT_SUPPLIER_PAGE;
	}

	@GetMapping(ViewPathConstants.DELETE_SUPPLIER_URL)
	public String deleteSupplier(Model model, HttpSession session, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale) {
		try {
			Integer supplierId = Integer.valueOf(req.getParameter(MVCConstants.SUPPLIER_ID_PARAM));

			this.supplierService.delete(supplierId);

			redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.supplier.delete.success", new Object[] { supplierId }, locale));
			req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);

			logger.info("The requested supplier for deletion has been deleted successfully");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.supplier.delete.failure", null, locale));
			logger.error("There is an error while deleteing supplier", e);
		}
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_SUPPLIER_URL;
	}

	@PostMapping(value = ViewPathConstants.BULK_SUPPLIER_URL, params = { MVCConstants.SAVE_SUPPLIERS_PARAM })
	public String saveSuppliers(@ModelAttribute SupplierBeanDTO suppliers, RedirectAttributes redirectAttrs, final HttpServletRequest req, Model model,
			Locale locale) {
		try {
			if (suppliers != null) {
				List<SupplierBean> supplierBeans = suppliers.getSuppliers();
				List<String> supplierIds = suppliers.getSupplierIds();
				Map<Integer, Integer> idIndex = new HashMap<>(supplierIds.size());
				this.getSelectedIds(supplierIds, idIndex);
				List<SupplierBean> finalSupplierBeans = new ArrayList<>(idIndex.size());
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

				List<Supplier> supplierList = new ArrayList<>(finalSupplierBeans.size());
				Supplier supplier = null;
				for (SupplierBean supplierBean : finalSupplierBeans) {
					supplier = new Supplier();
					this.updateDomain(supplierBean, supplier);
					supplierList.add(supplier);
				}
				logger.info("The supplier details has been updated in Domain objects");

				supplierService.updateSupplier(supplierList);
				redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.supplier.save.all.success", null, locale));
				req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
				logger.info("The selected suppliers basic details has been updated successfully.");
			} else {
				logger.info("There was some error while saving bulk Supplier details.");
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.supplier.save.all.failure", null, locale));
			}

		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.supplier.save.all.failure", null, locale));
			logger.error("There is an error while updating supplier basic details", e);
		}
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_SUPPLIER_URL;
	}

	@PostMapping(value = ViewPathConstants.BULK_SUPPLIER_URL, params = { MVCConstants.DELETE_SUPPLIERS_PARAM })
	public String deleteSuppliers(@ModelAttribute SupplierBeanDTO suppliers, RedirectAttributes redirectAttrs, final HttpServletRequest req, Model model,
			Locale locale) {
		try {
			List<String> supplierIds = suppliers.getSupplierIds();

			Map<Integer, Integer> idIndexMap = new HashMap<>(supplierIds.size());

			this.getSelectedIds(supplierIds, idIndexMap);

			Set<Integer> ids = idIndexMap.keySet();

			supplierService.deleteSuppliers(ids);
			redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.supplier.delete.all.success", null, locale));
			req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
			logger.info("The supplier details has been created successfully.");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.supplier.delete.failure", null, locale));
			logger.error("There is an error while deleting supplier", e);
		}
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_SUPPLIER_URL;
	}

	private void getSelectedIds(List<String> supplierIds, Map<Integer, Integer> idIndex) {
		String[] splittedData = null;
		for (String id : supplierIds) {
			splittedData = id.split("_");
			idIndex.put(new Integer(splittedData[0]), new Integer(splittedData[1]));
		}

		logger.info("The supplier ids and list index data has been seperated");
	}

}
