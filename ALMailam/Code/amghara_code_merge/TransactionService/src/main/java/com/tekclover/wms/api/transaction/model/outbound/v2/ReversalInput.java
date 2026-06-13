package com.tekclover.wms.api.transaction.model.outbound.v2;

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