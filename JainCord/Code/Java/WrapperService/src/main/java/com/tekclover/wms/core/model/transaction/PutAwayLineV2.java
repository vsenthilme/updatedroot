package com.tekclover.wms.core.model.transaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PutAwayLineV2 extends PutAwayLine {

	private Double inventoryQuantity;
	private String barcodeId;
	private Date manufacturerDate;
	private Date expiryDate;
	private String manufacturerCode;
	private String manufacturerName;
	private String origin;
	private String brand;
	private Double orderQty;
	private String cbm;
	private String cbmUnit;
	private Double cbmQuantity;
	private String companyDescription;
	private String plantDescription;
	private String warehouseDescription;
	private String statusDescription;
	private String businessPartnerCode;

	private String middlewareId;
	private String middlewareHeaderId;
	private String middlewareTable;
	private String purchaseOrderNumber;
	private String manufactureFullName;
	private String parentProductionOrderNo;

	private String palletId;
	private String sortNo;
	private String meter;
	private String lotNo;
	private String pieceId;
	private String gsm;
	private String grade;
	private String color;
}