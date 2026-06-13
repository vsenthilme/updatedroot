package com.tekclover.wms.core.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.sun.activation.viewers.ImageViewerCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.tekclover.wms.core.config.PropertiesConfig;
import com.tekclover.wms.core.exception.BadRequestException;
import com.tekclover.wms.core.model.auth.AuthToken;
import com.tekclover.wms.core.model.transaction.InboundIntegrationHeader;
import com.tekclover.wms.core.model.transaction.ReceiptConfimationReport;
import com.tekclover.wms.core.model.transaction.ShipmentDeliveryReport;
import com.tekclover.wms.core.model.transaction.ShipmentDeliveryReport;
import com.tekclover.wms.core.model.transaction.ShipmentDeliverySummaryReport;
import com.tekclover.wms.core.model.transaction.ShipmentDispatchSummaryReport;
//import com.tekclover.wms.core.repository.MongoTransactionRepository;
import com.tekclover.wms.core.util.DateUtils;

import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class ReportService {

//	@Autowired
//	MongoTransactionRepository mongoInboundRepository;
	
//	@Autowired
//	MongoOutboundRepository mongoOutboundRepository;
	
	@Autowired
	private MastersService mastersService;
	
	@Autowired
	AuthTokenService authTokenService;
	
	@Autowired
	PropertiesConfig propertiesConfig;
	
	/**
	 * 
	 * @param warehouseID
	 * @param statusId
	 * @param orderDate
	 * @return
	 * @throws ParseException 
	 */
//	public Map<String, Object> getOrderDetails (String warehouseID, Long statusId, String orderDate) throws ParseException {
//		Date localDate = null;
//		List<InboundIntegrationHeader> inboundOrders = null;
//		if (orderDate != null) {
//			try {
//				Date date = DateUtils.convertStringToDate(orderDate);
//				localDate = DateUtils.addTimeToDate(date);
//
//				inboundOrders = mongoInboundRepository.findAllByWarehouseIDAndProcessedStatusIdAndOrderReceivedOn(
//						warehouseID, statusId, localDate);
//				log.info("inboundOrders : " + inboundOrders);
//			} catch (Exception e) {
//				throw new BadRequestException("Date format should be MM-dd-yyyy");
//			}
//		} else {
//			inboundOrders = mongoInboundRepository.findAllByWarehouseIDAndProcessedStatusId(
//					warehouseID, statusId);
//			log.info("inboundOrders : " + inboundOrders);
//		}
//
//		long newOrders = inboundOrders.stream().filter(a -> a.getProcessedStatusId() == 0).count();
//		long processedOrders = inboundOrders.stream().filter(a -> a.getProcessedStatusId() == 10).count();
//
//		Map<String, Object> map = new HashMap <>();
//		map.put("newOrders", newOrders);
//		map.put("processedOrders", processedOrders);
//		map.put("orders", inboundOrders);
//		return map;
//	}
	
	/**
	 * 
	 * @param reportFormat
	 * @return
	 */
	public String exportBom (String reportFormat) {
		try {
			AuthToken authTokenForMasterService = authTokenService.getMastersServiceAuthToken();
			
			com.tekclover.wms.core.model.masters.BomHeader[] bomHeader = 
					mastersService.getBomHeaders(authTokenForMasterService.getAccess_token());
			
			int i = 300;
			for (com.tekclover.wms.core.model.masters.BomHeader bom : bomHeader) {
				List<String> lines = new ArrayList<>();
				lines.add("One Apple 123" + (i+=1000));
				lines.add("One Apple 234" + (i+=1000));
				lines.add("One Apple 345" + (i+=1000));
				bom.setLines(lines);
			}
			
			File file = ResourceUtils.getFile("classpath:bom.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(Arrays.asList(bomHeader));
			
			Map<String, Object> params = new HashMap<>();
			params.put ("createdBy", "Muru");
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, datasource);
			
			if (reportFormat.equalsIgnoreCase("html")) {
				JasperExportManager.exportReportToHtmlFile(jasperPrint, "bom.html");
			}
			
			if (reportFormat.equalsIgnoreCase("pdf")) {
				JasperExportManager.exportReportToPdfFile(jasperPrint, "bom.pdf");
			}
			return reportFormat;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return reportFormat;
	}

	/**
	 * ShipmentDelivery
	 * @param shipmentDeliveryList
	 * @param reportFormat
	 * @return
	 */
	public String exportShipmentDelivery(ShipmentDeliveryReport[] shipmentDeliveryList, String reportFormat) {
		try {
			String reportPath = propertiesConfig.getReportPath();
			String customerRef = shipmentDeliveryList[0].getCustomerRef();
			String fileName = "/shipmentDelivery_" + customerRef + "_" + DateUtils.getCurrentTimestamp();
			
			File file = ResourceUtils.getFile("classpath:shipment_delivery.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(Arrays.asList(shipmentDeliveryList));
			
			Map<String, Object> params = new HashMap<>();
			params.put ("createdBy", "IWExpress");
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, datasource);
			
			if (reportFormat.equalsIgnoreCase("html")) {
				fileName = reportPath + fileName + ".html";
				JasperExportManager.exportReportToHtmlFile(jasperPrint, fileName);
			}
			
			if (reportFormat.equalsIgnoreCase("pdf")) {
				fileName = reportPath + fileName + ".pdf";
				JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
			}
			log.info("Done..........");
			return fileName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
		return reportFormat;
	}

	/**
	 * 
	 * @param shipmentDeliverySummaryReport
	 * @param reportFormat
	 * @return
	 */
	public String exportShipmentDeliverySummary(ShipmentDeliverySummaryReport[] shipmentDeliverySummaryReport,
			String reportFormat) {
		try {
			String reportPath = propertiesConfig.getReportPath();
			String fileName = "/shipmentDeliverySummaryReport_" + DateUtils.getCurrentTimestamp();
			File file = ResourceUtils.getFile("classpath:shipment_delivery_summary.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource datasource = 
					new JRBeanCollectionDataSource(Arrays.asList(shipmentDeliverySummaryReport));
			
			Map<String, Object> params = new HashMap<>();
			params.put ("createdBy", "IWExpress");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, datasource);
			
			if (reportFormat.equalsIgnoreCase("html")) {
				fileName = reportPath + fileName + ".html";
				JasperExportManager.exportReportToHtmlFile(jasperPrint, fileName);
			}
			
			if (reportFormat.equalsIgnoreCase("pdf")) {
				fileName = reportPath + fileName + ".pdf";
				JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
			}
			log.info("Done..........");
			return fileName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
		return reportFormat;
	}

	/**
	 * 
	 * @param shipmentDispatchSummary
	 * @param reportFormat
	 * @return
	 */
	public String exportShipmentDispatchSummary(ShipmentDispatchSummaryReport shipmentDispatchSummary, String reportFormat) {
		try {
			String reportPath = propertiesConfig.getReportPath();
			String fileName = "/shipmentDispatchSummaryReport_" + DateUtils.getCurrentTimestamp();
			File file = ResourceUtils.getFile("classpath:shipment_dispatch_summary.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource datasource = 
					new JRBeanCollectionDataSource(Arrays.asList(shipmentDispatchSummary));
			
			Map<String, Object> params = new HashMap<>();
			params.put ("createdBy", "IWExpress");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, datasource);
			
			if (reportFormat.equalsIgnoreCase("html")) {
				fileName = reportPath + fileName + ".html";
				JasperExportManager.exportReportToHtmlFile(jasperPrint, fileName);
			}
			
			if (reportFormat.equalsIgnoreCase("pdf")) {
				fileName = reportPath + fileName + ".pdf";
				JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
			}
			log.info("Done..........");
			return fileName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
		return reportFormat;
	}

	/**
	 * 
	 * @param receiptConfimationReport
	 * @param reportFormat
	 * @return
	 */
	public String exportReceiptConfimationReport(ReceiptConfimationReport receiptConfimationReport, String reportFormat) {
		try {
			String reportPath = propertiesConfig.getReportPath();
			String fileName = "/shipmentDispatchSummaryReport_" + DateUtils.getCurrentTimestamp();
			File file = ResourceUtils.getFile("classpath:receiptConfimation.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource datasource = 
					new JRBeanCollectionDataSource(Arrays.asList(receiptConfimationReport));
			
			Map<String, Object> params = new HashMap<>();
			params.put ("createdBy", "IWExpress");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, datasource);
			
			if (reportFormat.equalsIgnoreCase("html")) {
				fileName = reportPath + fileName + ".html";
				JasperExportManager.exportReportToHtmlFile(jasperPrint, fileName);
			}
			
			if (reportFormat.equalsIgnoreCase("pdf")) {
				fileName = reportPath + fileName + ".pdf";
				JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
			}
			log.info("Done..........");
			return fileName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
		return reportFormat;
	}

	//PDF Generate
	public void export(HttpServletResponse response) throws IOException, DocumentException {

		Document document = new Document(PageSize.A6);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font fontSubTitle = FontFactory.getFont(FontFactory.TIMES_BOLD);
		fontSubTitle.setSize(8);

		String awbNumber = "BOQ000010213";
		Paragraph paragraph = new Paragraph("AWB - " + awbNumber, fontSubTitle);
		paragraph.setSpacingBefore(3f);
		paragraph.setFirstLineIndent(65f);

		// Creating an ImageData object
		String imageFile = "D:/logo.png";
		String imageFile1 = "D:/bc1.png";
		String imageFile2 = "D:/bc2.png";
		Image image = Image.getInstance(imageFile);
		Image image1 = Image.getInstance(imageFile1);
		Image image2 = Image.getInstance(imageFile2);
		Image image3 = Image.getInstance(imageFile2);

		image.scaleToFit(65,620);
		image1.scaleToFit(120,600);
		image2.scaleToFit(200,1200);
		image3.scaleToFit(140,1100);

		image.setAbsolutePosition(12,390);
		image1.setAbsolutePosition(80,375);
		image2.setAbsolutePosition(45,90);
		image3.setAbsolutePosition(75,50);
		document.add(image);
		document.add(image1);
		document.add(paragraph);
		document.add(image2);
		document.add(image3);

		PdfPTable headerTable = new PdfPTable(4);
		headerTable.setWidthPercentage(125f);
		headerTable.setWidths(new float[] {3.4f, 5.1f, 3.4f, 5.1f});

		PdfPTable pieceTable = new PdfPTable(4);
		pieceTable.setWidthPercentage(125f);
		pieceTable.setWidths(new float[] {3.4f, 5.1f, 3.4f, 5.1f});

		PdfPTable description = new PdfPTable(2);
		description.setWidthPercentage(125f);
		description.setWidths(new float[] {3.4f, 13.6f});

		PdfPTable senderTable = new PdfPTable(4);
		senderTable.setWidthPercentage(125f);
		senderTable.setWidths(new float[] {3.4f, 5.1f, 3.4f, 5.1f});

		PdfPTable sAaddress = new PdfPTable(2);
		sAaddress.setWidthPercentage(125f);
		sAaddress.setWidths(new float[] {3.4f, 13.6f});

		PdfPTable rAaddress = new PdfPTable(2);
		rAaddress.setWidthPercentage(125f);
		rAaddress.setWidths(new float[] {3.4f, 13.6f});

		PdfPTable receiverTable = new PdfPTable(4);
		receiverTable.setWidthPercentage(125f);
		receiverTable.setWidths(new float[] {3.4f, 5.1f, 3.4f, 5.1f});

		PdfPTable barcodelable = new PdfPTable(1);
		barcodelable.setWidthPercentage(45f);
		barcodelable.setWidths(new float[] {14.0f});

//		writeSummaryTableHeader(headerTable);
//		writeSummaryTableHeaderPiece(pieceTable);
//		writeSummaryItemDescription(description);
//		writeSenderTableHeader(senderTable);
//		writeTableSAddress(sAaddress);
//		writeTableRAddress(rAaddress);
//		writeRecipientTableHeader(receiverTable);

		writeSummaryTableHeaderData(headerTable);
		writeSummaryTableHeaderPieceData(pieceTable);
//		writeSummaryItemDescriptionData(description);
		writeSenderTableHeaderData(senderTable);
		writeTableSAddressData(sAaddress);
		writeTableRAddressData(rAaddress);
		writeRecipientTableHeaderData(receiverTable);
		writeTableBarCodeData(barcodelable);

		document.add(headerTable);
//		document.add(description);
		document.add(pieceTable);
		document.add(senderTable);
		document.add(sAaddress);
		document.add(receiverTable);
		document.add(rAaddress);
		document.add(barcodelable);

		document.close();
	}
	private void writeSummaryTableHeader(PdfPTable table) throws DocumentException {

		table.addCell(bottomLine("Label Date",true));
		table.addCell(bottomLine(":",false));

		table.addCell(bottomLine("Cust Ref#",true));
		table.addCell(bottomLine(":",false));

		table.addCell(borderLessCell("Org Country",true));
		table.addCell(borderLessCell(":",false));
		table.addCell(borderLessCell("Dest Country",true));
		table.addCell(borderLessCell(":",false));

		table.addCell(borderLessCell("",true));
		table.addCell(borderLessCell("",false));
		table.addCell(borderLessCell("Dest State",true));
		table.addCell(borderLessCell(":",false));

		table.addCell(borderLessCell("Cust Name",true));
		table.addCell(borderLessCell(":",false));
		table.addCell(borderLessCell("Dest City",true));
		table.addCell(borderLessCell(":",false));

		table.addCell(borderLessCell("Mode",true));
		table.addCell(borderLessCell(":",false));
		table.addCell(borderLessCell("Inco-Terms",true));
		table.addCell(borderLessCell(":",false));

		table.addCell(borderLessCell("Declared Value",true));
		table.addCell(borderLessCell(":",false));
		table.addCell(borderLessCell("Weight",true));
		table.addCell(borderLessCell(":",false));

		table.addCell(borderLessCell("Load Type",true));
		table.addCell(borderLessCell(":",false));
		table.addCell(borderLessCell("Service Type",true));
		table.addCell(borderLessCell(":",false));

		table.addCell(borderLessCell("COD",true));
		table.addCell(borderLessCell(":",false));
		table.addCell(borderLessCellMediumFont("Customs Charge",true));
		table.addCell(borderLessCell(":",false));

		table.addCell(borderLessCell("Insurance",true));
		table.addCell(borderLessCell(":",false));
		table.addCell(borderLessCell("Currency Code",true));
		table.addCell(borderLessCell(":",false));

//		table.addCell(borderLessCell("Piece Count", true));
//		table.addCell(borderLessCell(":",false));

//		table.addCell(borderLessCell("No.of items in piece", true));
//		table.addCell(borderLessCell(":",false));

	}

	private void writeSummaryTableHeaderPiece(PdfPTable table) throws DocumentException {

//		table.addCell(borderLessCellSmallFont("Pieces ID :", true));
//		table.addCell(borderLessCellMediumFont("", false));

		table.addCell(borderLessCell("Piece Count", true));
		table.addCell(borderLessCell(":",false));
 		table.addCell(borderLessCell("No.of items in Piece", true));
		table.addCell(borderLessCell(":",false));
	}

	private void writeSummaryItemDescription(PdfPTable table) throws DocumentException {
		table.addCell(borderLessCellMediumFont("Item Description",true));
		table.addCell(borderLessCell(":",false));
	}

	private void writeSenderTableHeader(PdfPTable table) throws DocumentException {

		//Blank Line
		table.addCell(bottomLine("",false));
		table.addCell(bottomLine("",false));
		table.addCell(bottomLine("",false));
		table.addCell(bottomLine("",false));

		//Sender Details
		table.addCell(borderLessCell("Shipper Name", true));
		table.addCell(borderLessCell(":",false));
		table.addCell(borderLessCell("Org Country", true));
		table.addCell(borderLessCell(":",false));

		table.addCell(borderLessCell("State", true));
		table.addCell(borderLessCell(":",false));
		table.addCell(borderLessCell("City", true));
		table.addCell(borderLessCell(":",false));

		table.addCell(borderLessCell("Phone", true));
		table.addCell(borderLessCell(":",false));
		table.addCell(borderLessCell("Phone 2", true));
		table.addCell(borderLessCell(":",false));
	}

	private void writeTableSAddress(PdfPTable table) throws DocumentException {
		//two column table
		table.addCell(borderLessCell("Address",true));
		table.addCell(borderLessCell(":",false));
	}

	private void writeTableRAddress(PdfPTable table) throws DocumentException {
		//two column table
		table.addCell(borderLessCell("Address",true));
		table.addCell(borderLessCell(":",false));
	}

	private void writeRecipientTableHeader(PdfPTable table) throws DocumentException {

		//four column table
		//Blank Line
		table.addCell(bottomLine("",false));
		table.addCell(bottomLine("",false));
		table.addCell(bottomLine("",false));
		table.addCell(bottomLine("",false));

		//Receiver Details
		table.addCell(borderLessCell("Recipient Name", true));
		table.addCell(borderLessCell(":",false));
		table.addCell(borderLessCell("Dest Country", true));
		table.addCell(borderLessCell(":",false));

		table.addCell(borderLessCell("State", true));
		table.addCell(borderLessCell(":",false));
		table.addCell(borderLessCell("City", true));
		table.addCell(borderLessCell(":",false));

		table.addCell(borderLessCell("Phone", true));
		table.addCell(borderLessCell(":",false));
		table.addCell(borderLessCell("Phone 2", true));
		table.addCell(borderLessCell(":",false));

	}

	private void writeSummaryTableHeaderData(PdfPTable table) throws DocumentException {

		table.addCell(bottomLine("Label Date",true));
		table.addCell(bottomLine(": 05/06/2024",false));

		table.addCell(bottomLine("Cust Ref#",true));
		table.addCell(bottomLine(": BQ70017295",false));

		table.addCell(borderLessCell("Org Country",true));
		table.addCell(borderLessCell(": Kuwait",false));
		table.addCell(borderLessCell("Dest Country",true));
		table.addCell(borderLessCell(": Iraq",false));

		table.addCell(borderLessCell("Cust Name",true));
		table.addCell(borderLessCell(": Mohammed Aslam",false));
		table.addCell(borderLessCell("Dest State",true));
		table.addCell(borderLessCell(": Baghdad",false));

		table.addCell(borderLessCell("Mode",true));
		table.addCell(borderLessCell(": Airways",false));
		table.addCell(borderLessCell("Dest City",true));
		table.addCell(borderLessCell(": Baghdad",false));

		table.addCell(borderLessCell("Declared Value",true));
		table.addCell(borderLessCell(": 10.5",false));
		table.addCell(borderLessCell("Inco-Terms",true));
		table.addCell(borderLessCell(": terms",false));

		table.addCell(borderLessCell("Load Type",true));
		table.addCell(borderLessCell(": Document",false));
		table.addCell(borderLessCell("Weight",true));
		table.addCell(borderLessCell(": 1 kg",false));

		table.addCell(borderLessCell("COD",true));
		table.addCell(borderLessCell(": Yes",false));
		table.addCell(borderLessCell("Service Type",true));
		table.addCell(borderLessCell(": Express",false));

		table.addCell(borderLessCell("Insurance",true));
		table.addCell(borderLessCell(": Yes",false));
		table.addCell(borderLessCellMediumFont("Customs Charge",true));
		table.addCell(borderLessCell(": -NIL-",false));

		table.addCell(borderLessCell("Piece Count", true));
		table.addCell(borderLessCell(": 10",false));
		table.addCell(borderLessCell("Currency Code",true));
		table.addCell(borderLessCell(": KWD",false));

//		table.addCell(borderLessCellMediumFont("No.of items in piece", true));
//		table.addCell(borderLessCell(":",false));
//		table.addCell(borderLessCellMediumFont("Item Description",true));
//		table.addCell(borderLessCellMediumFont(": Description of the item available here, for eg. Gift articles",false));

	}

	private void writeSummaryTableHeaderPieceData(PdfPTable table) throws DocumentException {

//		table.addCell(borderLessCellSmallFont("Pieces ID :", true));
//		table.addCell(borderLessCellMediumFont("", false));

//		table.addCell(borderLessCell("Piece Count", true));
//		table.addCell(borderLessCell(": 10",false));
//		table.addCell(borderLessCell("No.of items in Piece", true));
//		table.addCell(borderLessCell(": 5",false));

		table.addCell(borderLessCellMediumFont("No.of items in piece", true));
		table.addCell(borderLessCell(": 5",false));
		table.addCell(borderLessCellMediumFont("Item Description",true));
		table.addCell(borderLessCellMediumFont(": Item Description available here eg:Gift articles",false));
	}

	private void writeSummaryItemDescriptionData(PdfPTable table) throws DocumentException {
		table.addCell(borderLessCellMediumFont("Item Description",true));
		table.addCell(borderLessCellMediumFont(": Description of the item available here, for eg. Gift articles",false));
	}

	private void writeSenderTableHeaderData(PdfPTable table) throws DocumentException {

		//Blank Line
		table.addCell(bottomLine("",false));
		table.addCell(bottomLine("",false));
		table.addCell(bottomLine("",false));
		table.addCell(bottomLine("",false));

		//Sender Details
		table.addCell(borderLessCell("Shipper Name", true));
		table.addCell(borderLessCell(": The Hut Group",false));
		table.addCell(borderLessCell("Org Country", true));
		table.addCell(borderLessCell(": Kuwait",false));

		table.addCell(borderLessCell("State", true));
		table.addCell(borderLessCell(": Kuwait",false));
		table.addCell(borderLessCell("City", true));
		table.addCell(borderLessCell(": Kuwait",false));

		table.addCell(borderLessCell("Phone", true));
		table.addCell(borderLessCell(": 9717850160",false));
		table.addCell(borderLessCell("Phone 2", true));
		table.addCell(borderLessCell(": 9717850161",false));
	}

	private void writeTableSAddressData(PdfPTable table) throws DocumentException {
		//two column table
		table.addCell(borderLessCell("Address",true));
		table.addCell(borderLessCellMediumFont(": LF Logistics/Mohebi Logistics, Plot, Mohebi Logistics; Plot WT01 &, Kuwait, Kuwait, KUWAIT",false));
	}

	private void writeTableRAddressData(PdfPTable table) throws DocumentException {
		//two column table
		table.addCell(borderLessCell("Address",true));
		table.addCell(borderLessCellMediumFont(": House 61 Block 5, Street 517, Plot, Mohebi Logistics; Plot WT01 &, Baghdad, Baghdad, Iraq",false));
	}

	private void writeTableBarCodeData(PdfPTable table) throws DocumentException {

		table.addCell(borderLessCellFixedHeight("", true));
		table.addCell(borderLessCellFixedHeight("", true));
		table.addCell(borderLessCellFixedHeight("", true));

		table.addCell(borderLessCell("", true));
		table.addCell(borderLessCell("", true));
		table.addCell(borderLessCell("", true));

		table.addCell(borderLessCell("Piece Id - 707787104528",true));

		table.addCell(borderLessCellFixedHeight("", true));
		table.addCell(borderLessCellFixedHeight("", true));

		table.addCell(borderLessCell("", true));
		table.addCell(borderLessCell("", true));

		table.addCell(borderLessCell("Partner AWB - 80716745287",true));
	}

	private void writeRecipientTableHeaderData(PdfPTable table) throws DocumentException {

		//four column table
		//Blank Line
		table.addCell(bottomLine("",false));
		table.addCell(bottomLine("",false));
		table.addCell(bottomLine("",false));
		table.addCell(bottomLine("",false));

		//Receiver Details
		table.addCell(borderLessCell("Recipient Name", true));
		table.addCell(borderLessCell(": Mohammed Aslam",false));
		table.addCell(borderLessCell("Dest Country", true));
		table.addCell(borderLessCell(": Iraq",false));

		table.addCell(borderLessCell("State", true));
		table.addCell(borderLessCell(": Baghdad",false));
		table.addCell(borderLessCell("City", true));
		table.addCell(borderLessCell(": Baghdad",false));

		table.addCell(borderLessCell("Phone", true));
		table.addCell(borderLessCell(": 9787475727",false));
		table.addCell(borderLessCell("Phone 2", true));
		table.addCell(borderLessCell(": 9787475728",false));

	}

	private PdfPCell borderLessCell(String cellPhrase, Boolean bold) {
		//Blank Cell
		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setBorderWidthTop(0);
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthLeft(0);

		Font hFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
		hFont.setSize(7.5f);
		Font lFont = FontFactory.getFont(FontFactory.TIMES);
		lFont.setSize(7.5f);

		if (bold) {
			cell.setPhrase(new Phrase(cellPhrase, hFont));
		}
		if (!bold) {
			cell.setPhrase(new Phrase(cellPhrase, lFont));
		}
		return cell;
	}

	private PdfPCell borderLessCellFixedHeight(String cellPhrase, Boolean bold) {
		//Blank Cell
		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setBorderWidthTop(0);
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthLeft(0);
		cell.setFixedHeight(10f);

		cell.setPhrase(new Phrase(cellPhrase));

		return cell;
	}
	private PdfPCell borderLessCellMediumFont(String cellPhrase, Boolean bold) {
		//Blank Cell
		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setBorderWidthTop(0);
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthLeft(0);

		Font hFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
		hFont.setSize(7.0f);
		Font lFont = FontFactory.getFont(FontFactory.TIMES);
		lFont.setSize(7.0f);

		if (bold) {
			cell.setPhrase(new Phrase(cellPhrase, hFont));
		}
		if (!bold) {
			cell.setPhrase(new Phrase(cellPhrase, lFont));
		}
		return cell;
	}

	private PdfPCell bottomLine(String cellPhrase, Boolean bold) {
		//Cell with bottom border
		PdfPCell bCell = new PdfPCell();
		bCell.setBorder(0);
		bCell.setBorderWidthBottom(1);

		Font hFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
		hFont.setSize(7.5f);
		Font lFont = FontFactory.getFont(FontFactory.TIMES);
		lFont.setSize(7.5f);

		if (bold) {
			bCell.setPhrase(new Phrase(cellPhrase, hFont));
		}
		if (!bold) {
			bCell.setPhrase(new Phrase(cellPhrase, lFont));
		}
		return bCell;
	}
	private void writeTableData(PdfPTable table) {
//		for (User user : listUsers) {
//			table.addCell(String.valueOf(user.getId()));
//			table.addCell(user.getEmail());
//			table.addCell(user.getFullName());
//			table.addCell(user.getRoles().toString());
//			table.addCell(String.valueOf(user.isEnabled()));
//		}
	}


}
