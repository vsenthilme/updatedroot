package com.tekclover.wms.core.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.tekclover.wms.core.config.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class DocStorageService {
	
	private static final String ACCESS_TOKEN = null;
	@Autowired
	PropertiesConfig propertiesConfig;

	/**
	 * 
	 * @param location
	 * @param file
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public String getQualifiedFilePath (String location, String file) throws Exception {
		String filePath = propertiesConfig.getDocStorageBasePath();
		
		log.info("getQualifiedFilePath---location------>: " + location);
		log.info("getQualifiedFilePath---file------>: " + file);
		
		try {
			if (location != null && location.startsWith("document")) {
				filePath = filePath + propertiesConfig.getDocStorageDocumentPath();

				log.info("filePath------in document------>: " + filePath);
			}
		} catch (Exception e) {
			log.info("getQualifiedFilePath------Error------>: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		filePath = filePath + "/" + file;
		log.info("filePath: " + filePath);
		return filePath;
	}

	//PDF Generate - service
	public void export(HttpServletResponse response) throws IOException, DocumentException {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(15);

		Font fontSubTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontSubTitle.setSize(12);

		Paragraph paragraph = new Paragraph("Delivery Report", fontTitle);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		Paragraph paragraph4 = new Paragraph("Report Summary", fontSubTitle);
		paragraph4.setAlignment(Paragraph.ALIGN_LEFT);

		Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
		fontParagraph.setSize(8);

		Paragraph paragraph2 = new Paragraph("Selection Date: ", fontParagraph);
		paragraph2.setAlignment(Paragraph.ALIGN_RIGHT);
		Paragraph paragraph3 = new Paragraph("Run Date: ", fontParagraph);
		paragraph3.setAlignment(Paragraph.ALIGN_RIGHT);

		// Creating an ImageData object
		String imageFile = "D:/logo.png";
		Image image = Image.getInstance(imageFile);

		image.scaleToFit(100,1000);

		image.setAbsolutePosition(30,780);
		document.add(image);
		document.add(paragraph2);
		document.add(paragraph3);
		document.add(paragraph);

		PdfPTable table = new PdfPTable(11);
		table.setWidthPercentage(105f);
		table.setWidths(new float[] {2.5f, 3.0f, 1.5f, 1.5f, 2.5f, 2.0f, 2.0f, 2.0f, 2.0f, 2.5f, 2.5f});
		table.setSpacingBefore(10);

		PdfPTable table2 = new PdfPTable(8);
		table2.setWidthPercentage(100f);
		table2.setWidths(new float[] {5.5f, 3.0f, 2.5f, 2.5f, 2.5f, 2.5f, 2.5f,2.5f});
		table2.setSpacingBefore(10);

		writeTableHeader(table);
		writeTableData(table);
		writeSummaryTableHeader(table2);

//		PdfReader reader = new PdfReader("OpenPDFExample.pdf");
//		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("File.pdf"));
//		stamper.setRotateContents(false);
//		Phrase t = new Phrase("Total pages " + reader.getNumberOfPages(), new Font(Font.HELVETICA, 14));
//		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
//			float xt = reader.getPageSize(i).getWidth()-50;
//			float yt = reader.getPageSize(i).getBottom(5);
//			ColumnText.showTextAligned(
//					stamper.getOverContent(i), Element.ALIGN_RIGHT,
//					t, xt, yt, 0);
//		}
//		stamper.close();
//		reader.close();

		document.add(table);


		document.newPage();
		document.add(image);
		document.add(paragraph2);
		document.add(paragraph3);
		document.add(paragraph);
		document.add(paragraph4);

		document.add(table2);


		document.close();
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingBottom(5);
		cell.setBorderWidthBottom(1);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setSize(8.5f);
		font.setStyle(Font.BOLD);

		cell.setPhrase(new Phrase("Expected\n" + "Delivery Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Delivery Date/\n" + "Time", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Branch Code", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Order Type", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("# S.O", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("# Lines\n" + "Ordered", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("# Lines\n" + "Shipped", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("# Lines\n" + "Picked", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("# Ordered\n" + "Qty", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Shipped\n" + "Qty", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("%\n" +"Shipped", font));
		table.addCell(cell);

	}
	//Summary Table
	private void writeSummaryTableHeader(PdfPTable table2) {
		PdfPCell cell = new PdfPCell();
		cell.setBorder(1);
//		cell.setPaddingBottom(5);
		cell.setBorderWidthTop(1);
		cell.setBorderWidthBottom(1);
		cell.setBorderWidthLeft(1);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setSize(8.5f);
		font.setStyle(Font.BOLD);

		cell.setPhrase(new Phrase("Location", font));
		table2.addCell(cell);

		cell.setPhrase(new Phrase("Type", font));
		table2.addCell(cell);

		cell.setPhrase(new Phrase("No of\n" + "Orders", font));
		table2.addCell(cell);

		cell.setPhrase(new Phrase("# SKUs\n" + "Shipped", font));
		table2.addCell(cell);

		cell.setPhrase(new Phrase("#Lines\n" + "Picked", font));
		table2.addCell(cell);

		cell.setPhrase(new Phrase("Order Qty", font));
		table2.addCell(cell);

		cell.setPhrase(new Phrase("Shipped Qty", font));
		table2.addCell(cell);

		cell.setPhrase(new Phrase("% Shipped", font));
		cell.setBorderWidthRight(1);
		table2.addCell(cell);

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
