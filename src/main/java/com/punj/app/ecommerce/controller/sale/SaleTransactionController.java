package com.punj.app.ecommerce.controller.sale;
/**
 * 
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.TransactionTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.common.Register;
import com.punj.app.ecommerce.domains.item.ItemImage;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.TransactionReceipt;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.customer.CustomerBean;
import com.punj.app.ecommerce.models.customer.or.CustomerORBean;
import com.punj.app.ecommerce.models.sale.SaleHeaderBean;
import com.punj.app.ecommerce.models.shipment.ShipmentBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.models.transaction.SaleTransaction;
import com.punj.app.ecommerce.models.transaction.TransactionHeader;
import com.punj.app.ecommerce.services.ItemService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class SaleTransactionController {
	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private TransactionService transactionService;
	private ItemService itemService;
	private CommerceContext commerceContext;
	private MessageSource messageSource;

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @param itemService
	 *            the itemService to set
	 */
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	/**
	 * @param transactionService
	 *            the transactionService to set
	 */
	@Autowired
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param commerceContext
	 *            the commerceContext to set
	 */
	@Autowired
	public void setCommerceContext(CommerceContext commerceContext) {
		this.commerceContext = commerceContext;
	}

	@GetMapping(ViewPathConstants.POS_URL)
	public String showSaleScreen(Model model, final HttpSession session, final HttpServletRequest req, RedirectAttributes redirectAttrs, Locale locale) {
		try {
			String forwardURL = this.updateBeans(model, session, req, redirectAttrs, locale);
			if (StringUtils.isNotEmpty(forwardURL))
				return forwardURL;
			logger.info("The sale screen is ready for display now");
		} catch (Exception e) {
			logger.error("There is an error while showing the new sale screen", e);
			return ViewPathConstants.AUTO_ITEM_PAGE;
		}
		return ViewPathConstants.AUTO_ITEM_PAGE;
	}

	/**
	 * This method is to set all the bean objects in model needed for the return screen functionalities
	 * 
	 */
	private String updateBeans(Model model, final HttpSession session, final HttpServletRequest req, RedirectAttributes redirectAttrs, Locale locale) {
		SearchBean searchBean = new SearchBean();
		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);

		CustomerBean customerBean = new CustomerBean();
		model.addAttribute(MVCConstants.CUSTOMER_BEAN, customerBean);

		Integer openLocId = null;
		SaleHeaderBean saleHeaderBean = new SaleHeaderBean();
		Object openLocationId = commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
		if (openLocationId != null) {
			openLocId = (Integer) openLocationId;
			saleHeaderBean.setLocationId(openLocId);

			String registerIdValue = req.getParameter(MVCConstants.REGISTER_ID_PARAM);
			Integer registerId = null;
			String registerName = null;

			if (StringUtils.isEmpty(registerIdValue)) {
				registerId = (Integer) session.getAttribute(openLocId + MVCConstants.REGISTER_ID_PARAM);
				registerName = (String) session.getAttribute(openLocId + MVCConstants.REG_NAME_PARAM);
			} else {
				registerId = new Integer(registerIdValue);
				registerName = req.getParameter(MVCConstants.REG_NAME_PARAM);
				session.setAttribute(openLocId + MVCConstants.REGISTER_ID_PARAM, registerId);
				session.setAttribute(openLocId + MVCConstants.REG_NAME_PARAM, registerName);
			}

			if (registerId != null) {

				if (this.validateRegister(openLocId, registerId)) {
					Object openLocationName = commerceContext.getStoreSettings(openLocId + "-" + CommerceConstants.OPEN_LOC_NAME);
					Object openLocationGstNo = commerceContext.getStoreSettings(openLocId + "-" + CommerceConstants.OPEN_LOC_GST_NO);
					Object openBusinessDate = commerceContext.getStoreSettings(openLocId + "-" + CommerceConstants.OPEN_BUSINESS_DATE);
					Object defaultTender = commerceContext.getStoreSettings(openLocId + "-" + CommerceConstants.LOC_DEFAULT_TENDER);
					if (openLocationName != null)
						saleHeaderBean.setLocationName((String) openLocationName);
					if (openLocationGstNo != null)
						saleHeaderBean.setGstNo((String) openLocationGstNo);
					if (openBusinessDate != null)
						saleHeaderBean.setBusinessDate((LocalDateTime) openBusinessDate);
					if (defaultTender != null) {
						saleHeaderBean.setDefaultTender((String) defaultTender);
					}

					List<TenderBean> tenderBeans = this.retrieveValidTenders((Integer) openLocationId);
					model.addAttribute(MVCConstants.TENDER_BEANS, tenderBeans);

					saleHeaderBean.setRegisterId(registerId);
					saleHeaderBean.setRegisterName(registerName);
					model.addAttribute(MVCConstants.SALE_HEADER_BEAN, saleHeaderBean);
					model.addAttribute(MVCConstants.SHIPMENT_BEAN, new ShipmentBean());
					model.addAttribute(MVCConstants.CUSTOMER_OR_BEAN, new CustomerORBean());
					logger.info("All the needed beans for return transaction screen has been set in the model");
				} else {
					req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
					redirectAttrs.addFlashAttribute(MVCConstants.REFERRER_URL_PARAM, ViewPathConstants.POS_URL);
					redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.transaction.invalid.store.register", null, locale));
					logger.info("There is no valid store or register provided for the transaction screen");
					return ViewPathConstants.REDIRECT_URL + ViewPathConstants.STORE_OPEN_URL;
				}

			} else {
				req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
				redirectAttrs.addFlashAttribute(MVCConstants.REFERRER_URL_PARAM, ViewPathConstants.POS_URL);
				logger.info("There is no open store existing as per this session, routing to register open screen");
				return ViewPathConstants.REDIRECT_URL + ViewPathConstants.REGISTER_OPEN_URL;
			}
		} else {
			req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
			redirectAttrs.addFlashAttribute(MVCConstants.REFERRER_URL_PARAM, ViewPathConstants.POS_URL);
			logger.info("There is no open store existing as per application context, routing to store open screen");
			return ViewPathConstants.REDIRECT_URL + ViewPathConstants.STORE_OPEN_URL;
		}
		return null;

	}

	private Boolean validateRegister(Integer locationId, Integer registerId) {

		Boolean result = this.transactionService.isTransactionAlllowed(locationId, registerId);
		if (result)
			logger.info("The provided location and registers are in valid status");
		else
			logger.info("The provided location and registers are in not valid");

		return result;
	}

	private List<TenderBean> retrieveValidTenders(Integer locationId) {
		List<Tender> tenders = this.commonService.retrieveAllTenders(locationId);
		List<TenderBean> tenderBeans = CommonMVCTransformer.tranformTenders(tenders);
		return tenderBeans;
	}

	@GetMapping(ViewPathConstants.EDIT_SALE_TXN_URL)
	public String showEditSaleScreen(@RequestParam("txnId") String uniqueTxnNo, Model model, final HttpSession session, final HttpServletRequest req,
			RedirectAttributes redirectAttrs, Locale locale) {
		try {
			if (StringUtils.isNotBlank(uniqueTxnNo)) {
				TransactionDTO txnDTO = this.transactionService.searchTransactionDetails(uniqueTxnNo);
				if (txnDTO != null) {
					Map<BigInteger, List<ItemImage>> itemImagesMap =null;
					if (txnDTO.getSaleLineItems() != null && !txnDTO.getSaleLineItems().isEmpty()) {
						Set<BigInteger> itemIds = TransactionTransformer.retrieveItemIds(txnDTO.getSaleLineItems());
						itemImagesMap = this.itemService.retrieveItems(itemIds);
					}

					Map<Integer, Tender> tenderMap = this.commonService.retrieveAllTendersAsMap(txnDTO.getTxn().getTransactionId().getLocationId());
					SaleTransaction saleTxn = TransactionTransformer.transformSaleTxn(txnDTO, tenderMap, itemImagesMap);

					this.updateBeansForModification(model, session, req, redirectAttrs, locale, saleTxn);
					logger.info("The edit sale screen is ready for display now");
				} else {
					model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.sale.edit.no.txn.found", null, locale));
					logger.error("There is an error while retrieving customer for editing");
				}
			} else {
				model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.sale.edit.no.txn", null, locale));
				logger.error("There is an error while retrieving customer for editing");
			}

		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while showing the edit sale screen", e);
		}
		return ViewPathConstants.EDIT_SALE_TXN_PAGE;
	}

	/**
	 * This method is to set all the bean objects in model needed for the return screen functionalities
	 * 
	 */
	private void updateBeansForModification(Model model, final HttpSession session, final HttpServletRequest req, RedirectAttributes redirectAttrs, Locale locale,
			SaleTransaction saleTxn) {
		SearchBean searchBean = new SearchBean();
		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);
		model.addAttribute(MVCConstants.CUSTOMER_BEAN, saleTxn.getCustomer());

		TransactionHeader txnHeader = saleTxn.getTransactionHeader();
		Integer locationId = txnHeader.getLocationId();
		Integer registerId = txnHeader.getRegisterId();
		SaleHeaderBean saleHeaderBean = new SaleHeaderBean();
		saleHeaderBean.setLocationId(locationId);
		saleHeaderBean.setRegisterId(registerId);
		saleHeaderBean.setBusinessDate(txnHeader.getBusinessDate());

		Location location = this.commonService.retrieveLocationDetails(locationId);
		saleHeaderBean.setLocationName(location.getName());
		saleHeaderBean.setGstNo(location.getGstNo());
		saleHeaderBean.setDefaultTender(location.getDefaultTender());

		List<Register> registers = this.commonService.retrieveRegisters(locationId);
		if (registers != null && !registers.isEmpty()) {
			for (Register register : registers) {
				if (registerId.equals(register.getRegisterId().getRegister())) {
					saleHeaderBean.setRegisterName(register.getName());
					break;
				}
			}
		}

		List<TenderBean> tenderBeans = this.retrieveValidTenders(locationId);
		model.addAttribute(MVCConstants.TENDER_BEANS, tenderBeans);
		model.addAttribute(MVCConstants.SALE_HEADER_BEAN, saleHeaderBean);
		model.addAttribute(MVCConstants.TXN_SALE_DTO, saleTxn);
		model.addAttribute(MVCConstants.SHIPMENT_BEAN, saleTxn.getShipment());
		model.addAttribute(MVCConstants.CUSTOMER_OR_BEAN, saleTxn.getOrderRequest());
		logger.info("All the needed beans for sale transaction modification screen has been set in the model");

	}

	@GetMapping(ViewPathConstants.LAST_TXN_RCPT_URL)
	public void printLastTransaction(Model model, final HttpServletRequest req, Locale locale, HttpSession session, HttpServletResponse response) {
		try {
			byte pdfBytes[] = null;
			String uniqueTxnNo = (String) session.getAttribute(MVCConstants.LAST_TXN_NO);
			if (StringUtils.isNotBlank(uniqueTxnNo)) {
				pdfBytes = (byte[]) session.getAttribute(uniqueTxnNo + MVCConstants.RCPT_PARAM);

			} else {
				String locIdStr = req.getParameter(MVCConstants.LOCATION_ID_PARAM);
				String regIdStr = req.getParameter(MVCConstants.REGISTER_ID_PARAM);
				String bDateStr = req.getParameter(MVCConstants.B_DATE_PARAM);

				String DATE_TIME_FORMAT = "dd-MMM-yy HH:mm:ss";
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

				if (StringUtils.isNotBlank(locIdStr) && StringUtils.isNotBlank(regIdStr) && StringUtils.isNotBlank(bDateStr)) {
					TransactionId txnIdCriteria = new TransactionId();
					txnIdCriteria.setBusinessDate(LocalDateTime.parse(bDateStr, dateTimeFormatter));
					txnIdCriteria.setLocationId(Integer.parseInt(locIdStr));
					txnIdCriteria.setRegister(Integer.parseInt(regIdStr));

					TransactionReceipt txnRcptDetails = this.transactionService.retrieveLastTransaction(txnIdCriteria);
					if (txnRcptDetails != null) {
						pdfBytes = txnRcptDetails.getReceiptData();
					} else {
						logger.info("The transaction receipt was not found!!");
					}
				} else {
					logger.info("The transaction look up details for receipt was not found");
				}
			}
			if (pdfBytes != null) {
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "attachment; filename=" + uniqueTxnNo + ".pdf");
				response.setContentLength(pdfBytes.length);
				response.getOutputStream().write(pdfBytes);
				logger.info("The {} txn receipt has been generated successfully", uniqueTxnNo);
				response.getOutputStream().flush();
			} else {
				String outputError = "There was no transaction receipt found!!";
				try (PDDocument doc = new PDDocument()) {
					PDPage page = new PDPage();
					doc.addPage(page);

					PDFont font = PDType1Font.HELVETICA_BOLD;

					try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
						contents.beginText();
						contents.setFont(font, 12);
						contents.newLineAtOffset(100, 700);
						contents.showText(outputError);
						contents.endText();
					}

					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					doc.save(byteArrayOutputStream);
					doc.close();
					response.setContentType("application/pdf");
					response.setHeader("Content-disposition", "attachment; filename=" + uniqueTxnNo + ".pdf");
					response.getOutputStream().write(byteArrayOutputStream.toByteArray());
					logger.info("The {} txn receipt has been generated successfully", uniqueTxnNo);
					response.getOutputStream().flush();
				}

			}
		} catch (IOException e) {
			logger.error("There is an error while generating receipt for last txn", e);
		}
	}

}
