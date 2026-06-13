package com.tekclover.wms.api.mfg.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ASNHeaderV2  {

	@NotBlank(message = "To Branch Code is mandatory")
	private String branchCode;

	@NotBlank(message = "To CompanyCode is mandatory")
	private String companyCode;

	@NotBlank(message = "ASN number is mandatory")
	private String asnNumber;

	private String warehouseId;
	private String languageId;

	//almailem fields
	private String purchaseOrderNumber;
	private String parentProductionOrderNo;
	private Long inboundOrderTypeId;
	private String supplierCode;
}
