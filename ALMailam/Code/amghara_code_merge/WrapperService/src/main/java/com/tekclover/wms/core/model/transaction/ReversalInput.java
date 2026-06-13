package com.tekclover.wms.core.model.transaction;

import lombok.Data;

@Data
public class ReversalInput {

	private String companyCodeId;
	private String plantId;
	private String languageId;
	private String warehouseId;

	private String itemCode;
	private String manufacturerName;
	private String refDocNumber;
	private String preOutboundNo;
	private Long lineReference;
	private Long orderTypeId;
}