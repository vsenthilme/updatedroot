package com.tekclover.wms.core.model.transaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QualityLineV2 extends QualityLine {

	private String companyDescription;
	private String plantDescription;
	private String warehouseDescription;
	private String statusDescription;

	private String middlewareId;
	private String middlewareTable;
	private String middlewareHeaderId;
	private String referenceDocumentType;
	private String manufactureFullName;
	private String salesOrderNumber;
	private String manufacturerName;
	private String supplierInvoiceNo;
	private String salesInvoiceNumber;
	private String pickListNumber;
	private String tokenNumber;
	private String barcodeId;
	private String targetBranchCode;

	private String customerId;
	private String customerName;

	//================================JainCord=============================
	private String sortNo;
	private String meter;
	private String lotNo;
	private String pieceId;
	private String gsm;
	private String grade;
	private String color;
	private String palletId;
}