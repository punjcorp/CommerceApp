/**
 * 
 */
package com.punj.app.ecommerce.controller.nosale;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.ExpenseTransformer;
import com.punj.app.ecommerce.controller.common.transformer.TransactionTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.NoSaleTransaction;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.TransactionReceipt;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.nosale.ExpenseBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.NoSaleService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ExpenseController {

	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private TransactionService transactionService;
	private NoSaleService noSaleService;
	private CommerceContext commerceContext;
	private ResourceBundleMessageSource messageSource;

	@Value("${commerce.resource.bundle.base}")
	private String resourceBundleBase;

	/**
	 * @param noSaleService
	 *            the noSaleService to set
	 */
	@Autowired
	public void setNoSaleService(NoSaleService noSaleService) {
		this.noSaleService = noSaleService;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
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

	/**
	 * @param transactionService
	 *            the transactionService to set
	 */
	@Autowired
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping(value = ViewPathConstants.TXN_EXPENSE_URL)
	public String processExpenseType(Model model, HttpSession session, RedirectAttributes redirectAttrs) {
		ExpenseBean expenseBean = new ExpenseBean();

		Integer openLocationId = (Integer) commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);

		if (openLocationId != null) {

			Integer registerId = (Integer) session.getAttribute(openLocationId + MVCConstants.REGISTER_ID_PARAM);

			if (registerId != null) {

				List<TenderBean> tenderBeans = this.retrieveValidTenders((Integer) openLocationId);

				Object openLocationName = commerceContext.getStoreSettings(openLocationId + "-" + CommerceConstants.OPEN_LOC_NAME);
				LocalDateTime openBusinessDate = (LocalDateTime) commerceContext.getStoreSettings(openLocationId + "-" + CommerceConstants.OPEN_BUSINESS_DATE);

				String defaultTender = (String) commerceContext.getStoreSettings(openLocationId + "-" + CommerceConstants.LOC_DEFAULT_TENDER);
				String registerName = (String) session.getAttribute(openLocationId + MVCConstants.REG_NAME_PARAM);

				expenseBean.setDefaultTender(defaultTender);
				expenseBean.setBusinessDate(openBusinessDate);
				expenseBean.setLocationId(openLocationId);
				expenseBean.setRegisterId(registerId);
				expenseBean.setRegisterName(registerName);
				expenseBean.setLocationName((String) openLocationName);

				model.addAttribute(MVCConstants.TENDER_BEANS, tenderBeans);
				model.addAttribute(MVCConstants.EXPENSE_PARAM, expenseBean);
				logger.info("The expense screen is ready for the display");
				return ViewPathConstants.TXN_EXPENSE_PAGE;
			} else {
				logger.info("There is no open register existing as per this session, routing to register open screen");
				redirectAttrs.addFlashAttribute(MVCConstants.REFERRER_URL_PARAM, ViewPathConstants.TXN_EXPENSE_URL);
				return ViewPathConstants.REDIRECT_URL + ViewPathConstants.REGISTER_OPEN_URL;
			}

		} else {
			logger.info("There is no open store existing as per application context, routing to store open screen");
			redirectAttrs.addFlashAttribute(MVCConstants.REFERRER_URL_PARAM, ViewPathConstants.TXN_EXPENSE_URL);
			return ViewPathConstants.REDIRECT_URL + ViewPathConstants.STORE_OPEN_URL;
		}

	}

	@PostMapping(value = ViewPathConstants.TXN_EXPENSE_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ExpenseBean saveExpenseType(@RequestBody ExpenseBean expenseBean, Model model, Authentication authentication, Locale locale, HttpSession session) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		Transaction txnHeaderDetails = ExpenseTransformer.transformExpenseToTxn(expenseBean, username);
		NoSaleTransaction noSaleTxn = ExpenseTransformer.transformExpenseDetails(expenseBean, txnHeaderDetails.getTransactionId(), username);
		noSaleTxn = this.noSaleService.saveNoSaleTxn(txnHeaderDetails, noSaleTxn);
		TransactionId txnId = null;
		if (noSaleTxn != null) {
			txnId = noSaleTxn.getTransactionId();

			expenseBean = this.createExpenseReceiptDetails(locale, username, txnId, session, txnHeaderDetails.getComments());

			logger.info("The No Sale transaction details has been save successfully");
		} else {
			logger.info("The No Sale transaction details has been save successfully");
		}
		return expenseBean;
	}

	private List<TenderBean> retrieveValidTenders(Integer locationId) {
		List<Tender> tenders = this.commonService.retrieveAllTenders(locationId);
		List<TenderBean> tenderBeans = CommonMVCTransformer.tranformTenders(tenders);
		logger.info("The possible tenders for the location has been retrieved");
		return tenderBeans;
	}

	private ExpenseBean createExpenseReceiptDetails(Locale locale, String username, TransactionId txnId, HttpSession session, String comments) {
		ExpenseBean expenseBean = null;
		try {
			NoSaleTransaction noSaleTxnFromDB = this.noSaleService.retrieveNoSaleTxn(txnId);
			if (noSaleTxnFromDB != null) {

				Location location = this.commonService.retrieveLocationDetails(txnId.getLocationId());
				LocationBean locationBean = CommonMVCTransformer.transformLocationDomainPartially(location, Boolean.FALSE);

				Map<Integer, Tender> tenderMap = this.commonService.retrieveAllTendersAsMap(txnId.getLocationId());

				expenseBean = ExpenseTransformer.transformExpenseNoSaleTransaction(noSaleTxnFromDB, tenderMap, comments);
				expenseBean.setLocationBean(locationBean);
				expenseBean.setPrintedBy(username);

				this.generateExpenseReceipt(expenseBean, locale, username, txnId, session);
				logger.info("The expense no sale txn receipt has been generated successfully");
			}
		} catch (JRException e) {
			logger.error("There was some error while generating the receipt for expense no sale txn", e);
			expenseBean = null;
		}
		return expenseBean;
	}

	private void generateExpenseReceipt(ExpenseBean expenseBean, Locale locale, String username, TransactionId txnId, HttpSession session) throws JRException {
		List<ExpenseBean> expenseList = new ArrayList<>();
		expenseList.add(expenseBean);

		InputStream txnReceiptReportStream = getClass().getResourceAsStream(MVCConstants.EXPENSE_RECEIPT_REPORT);
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(txnReceiptReportStream);
		logger.debug("The parent expense receipt report has been compiled now");

		InputStream txnReceiptStreamChild = getClass().getResourceAsStream(MVCConstants.EXPENSE_RECEIPT_TENDERS_REPORT);
		JasperReport jasperReportChild = (JasperReport) JRLoader.loadObject(txnReceiptStreamChild);
		logger.debug("The child expense tender receipt report has been compiled now");

		ResourceBundle resourceBundle = ResourceBundle.getBundle(this.resourceBundleBase);
		JRBeanCollectionDataSource expenseDS = new JRBeanCollectionDataSource(expenseList);
		logger.debug("The expense receipt data source and resource bundle is ready now");

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MVCConstants.SUB_REPORT_DIR, jasperReportChild);
		paramMap.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
		paramMap.put(JRParameter.REPORT_LOCALE, locale);
		logger.debug("The parameter set for setting receipt data is ready");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, expenseDS);
		logger.debug("The receipt report is ready with expense data to be used");

		byte pdfBytes[] = JasperExportManager.exportReportToPdf(jasperPrint);

		// This is to print the receipt when user select the printing option
		session.setAttribute(txnId + MVCConstants.RCPT_JASPER_PARAM, jasperPrint);
		// This is to show the receipt PDF in view screen
		session.setAttribute(MVCConstants.LAST_TXN_NO, txnId.toString());
		session.setAttribute(txnId + MVCConstants.RCPT_PARAM, pdfBytes);

		// This section will save the receipt in database
		List<TransactionReceipt> txnReceipts = TransactionTransformer.getReceipts(pdfBytes, txnId, username);
		Boolean result = this.transactionService.saveTransactionReceipt(txnReceipts);
		if (result) {
			logger.info("The expense transaction receipts has been saved in DB successfully");
		} else {
			logger.info("The expense transaction receipts were not saved due to unknown reason");
		}

		logger.info("The receipt for expense transaction has been created");
	}

	@PostMapping(value = ViewPathConstants.TXN_EXPENSE_PRINT_RECEIPT_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public TransactionId printExpenseReceipt(@RequestBody TransactionId txnId, Model model, Authentication authentication, HttpSession session) {
		try {
			JasperPrint jasperPrint = (JasperPrint) session.getAttribute(txnId.toString() + MVCConstants.RCPT_JASPER_PARAM);
			if (jasperPrint != null) {
				JasperPrintManager.printReport(jasperPrint, Boolean.FALSE);
				logger.info("The {} expense receipt has been printed successfully", txnId.toString());
			} else {
				txnId = null;
				logger.info("The expense receipt is not found");
			}
		} catch (JRException e) {
			logger.info("There was errror printing receipt for the {} expense txn", txnId.toString());
		}
		return txnId;
	}

	@GetMapping(value = ViewPathConstants.TXN_EXPENSE_VIEW_RECEIPT_URL)
	private void viewExpenseReceipt(Model model, HttpSession session, Locale locale, HttpServletResponse response, final HttpServletRequest req) {
		try {
			final String txnId = req.getParameter(MVCConstants.TXN_ID_PARAM);
			if (txnId != null) {
				byte pdfBytes[] = (byte[]) session.getAttribute(txnId + MVCConstants.RCPT_PARAM);
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "attachment; filename=" + txnId + ".pdf");
				response.setContentLength(pdfBytes.length);
				response.getOutputStream().write(pdfBytes);
				logger.info("The {} txn receipt has been generated successfully", txnId);
				response.getOutputStream().flush();
			} else {
				String outputError = "There was no transaction receipt found for the provided txn!!";
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
					response.setHeader("Content-disposition", "attachment; filename=expense.pdf");
					response.getOutputStream().write(byteArrayOutputStream.toByteArray());
					logger.info("The txn receipt has been generated successfully");
					response.getOutputStream().flush();
				}
			}
		} catch (IOException e) {
			logger.error("There is an error while generating expense no sale receipt for last txn", e);
		}
	}

}
