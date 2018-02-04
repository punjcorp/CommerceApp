/**
 * 
 */
package com.punj.app.ecommerce.controller.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.controller.common.MVCConstants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author admin
 *
 */
public class PdfReportGenerator {

	private static final Logger logger = LogManager.getLogger();
	private static PdfReportGenerator pdfGenerator = null;

	private PdfReportGenerator() {

	}

	public static PdfReportGenerator getInstance() {
		if (pdfGenerator == null) {
			pdfGenerator = new PdfReportGenerator();
		}
		return pdfGenerator;

	}

	public void printPDFReport(HttpServletResponse response, JasperReport jasperReport, Map<String, Object> paramMap,
			JRBeanCollectionDataSource reportDS) {
		try {

			// set header as pdf
			response.setContentType(MVCConstants.REPORT_OUTPUT_PDF);

			// set input and output stream
			ServletOutputStream servletOutputStream = response.getOutputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, reportDS);

			// export to pdf
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			response.setContentLength(baos.size());
			baos.writeTo(servletOutputStream);

			logger.info("The PDF printing has been completed based on provided details successfully");
		} catch (JRException | IOException e) {
			logger.info("The PDF printing has failed due to some issue with report template files and printing");
		}

	}

}
