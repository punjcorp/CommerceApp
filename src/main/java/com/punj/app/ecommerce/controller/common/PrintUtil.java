package com.punj.app.ecommerce.controller.common;
/**
 * 
 */

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;

/**
 * @author admin
 *
 */
@Component
public class PrintUtil {

	@Value("${commerce.resource.bundle.base}")
	private String resourceBundleBase;

	@Value("${commerce.txn.receipt.copies}")
	private Integer receiptCopies;

	@Value("${commerce.printer.name}")
	private String printerName;

	@Value("${commerce.receipt.pdf.output}")
	private String pdfOutputDir;

	@Value("${commerce.receipt.rtf.output}")
	private String rtfOutputDir;

	@Value("${commerce.receipt.jpg.output}")
	private String imagesOutputDir;

	public void exportAsRTFFile(String uniqueTxnNo, List<JasperPrint> jasperPrints) throws JRException, IOException {
		JRRtfExporter rtfExporter = new JRRtfExporter();

		FileOutputStream rceiptFile = new FileOutputStream(this.rtfOutputDir + "/" + uniqueTxnNo + ".rtf");
		rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
		rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, rceiptFile);
		rtfExporter.exportReport();
		rceiptFile.flush();
		rceiptFile.close();
	}

	public void exportAsPDFFile(String uniqueTxnNo, List<JasperPrint> jasperPrints) throws JRException, IOException {
		JRRtfExporter rtfExporter = new JRRtfExporter();

		FileOutputStream rceiptFile = new FileOutputStream(this.pdfOutputDir + "/" + uniqueTxnNo + ".rtf");
		rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
		rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, rceiptFile);
		rtfExporter.exportReport();
		rceiptFile.flush();
		rceiptFile.close();
	}

	public ByteArrayOutputStream exportAsPDFBytes(List<JasperPrint> jasperPrints) throws JRException, IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		exporter.exportReport();

		return out;
	}

	public void exportAsImage(String uniqueTxnNo, List<JasperPrint> jasperPrints) throws JRException, IOException {

	}

	public void printReportFromRTFFile(String uniqueTxnNo) throws FileNotFoundException, PrintException {
		PrintService myPrintService = null;
		if (StringUtils.isNotBlank(this.printerName)) {
			PrintService[] ps = PrintServiceLookup.lookupPrintServices(null, null);
			if (ps != null && ps.length > 0) {
				for (PrintService printService : ps) {
					if (printService.getName().equals(this.printerName)) {
						myPrintService = printService;
						break;
					}
				}
			}
		} else {
			myPrintService = PrintServiceLookup.lookupDefaultPrintService();
		}

		FileInputStream fis = new FileInputStream(this.rtfOutputDir + "/" + uniqueTxnNo + ".rtf");
		DocFlavor df = DocFlavor.INPUT_STREAM.AUTOSENSE;
		DocPrintJob job = myPrintService.createPrintJob();
		Doc doc = new SimpleDoc(fis, df, null);
		PrintRequestAttributeSet attrib = new HashPrintRequestAttributeSet();
		attrib.add(new Copies(1));
		job.print(doc, attrib);

	}

	public void printReportFromPDFFile(String uniqueTxnNo) throws FileNotFoundException, PrintException {
		PrintService myPrintService = null;
		if (StringUtils.isNotBlank(this.printerName)) {
			PrintService[] ps = PrintServiceLookup.lookupPrintServices(null, null);
			if (ps != null && ps.length > 0) {
				for (PrintService printService : ps) {
					if (printService.getName().equals(this.printerName)) {
						myPrintService = printService;
						break;
					}
				}
			}
		} else {
			myPrintService = PrintServiceLookup.lookupDefaultPrintService();
		}

		FileInputStream fis = new FileInputStream(this.pdfOutputDir + "/" + uniqueTxnNo + ".rtf");
		DocFlavor df = DocFlavor.INPUT_STREAM.AUTOSENSE;
		DocPrintJob job = myPrintService.createPrintJob();
		Doc doc = new SimpleDoc(fis, df, null);
		PrintRequestAttributeSet attrib = new HashPrintRequestAttributeSet();
		attrib.add(new Copies(1));
		job.print(doc, attrib);

	}

	public void printReportUsingJasperPrints(String printer, List<JasperPrint> jasperPrints) throws JRException {

		for (JasperPrint jasperPrint : jasperPrints) {

			JRPrintServiceExporter exporter = new JRPrintServiceExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

			PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
			printRequestAttributeSet.add(MediaSizeName.ISO_A4);

			PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
			if (StringUtils.isNotBlank(printer))
				printServiceAttributeSet.add(new PrinterName(printer, null));
			else if (StringUtils.isNotBlank(this.printerName))
				printServiceAttributeSet.add(new PrinterName(this.printerName, null));
			else
				printServiceAttributeSet.add(new PrinterName("RICOH SP 210 DDST", null));

			SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
			configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
			configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
			configuration.setDisplayPageDialog(false);
			configuration.setDisplayPrintDialog(false);

			exporter.setConfiguration(configuration);
			exporter.exportReport();

		}
	}

	public void printReportUsingPdfBytes(byte pdfBytes[]) throws PrintException {
		PrintService myPrintService = null;
		if (StringUtils.isNotBlank(this.printerName)) {
			PrintService[] ps = PrintServiceLookup.lookupPrintServices(null, null);
			if (ps != null && ps.length > 0) {
				for (PrintService printService : ps) {
					if (printService.getName().equals(this.printerName)) {
						myPrintService = printService;
						break;
					}
				}
			}
		} else {
			myPrintService = PrintServiceLookup.lookupDefaultPrintService();
		}

		DocPrintJob job = myPrintService.createPrintJob();
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		Doc pdfDoc = new SimpleDoc(pdfBytes, flavor, null);
		PrintRequestAttributeSet attrib = new HashPrintRequestAttributeSet();
		attrib.add(new Copies(1));
		job.print(pdfDoc, attrib);

	}

}
