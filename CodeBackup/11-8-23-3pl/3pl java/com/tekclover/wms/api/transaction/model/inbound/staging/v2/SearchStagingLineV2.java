package com.tekclover.wms.api.transaction.model.inbound.staging.v2;

import lombok.Data;

import java.util.List;

@Data
public class SearchStagingLineV2 {
	 
	private List<String> warehouseId;
	private List<String> preInboundNo;
	private List<String> refDocNumber;
	private List<String> stagingNo;
	private List<String> palletCode;
	private List<String> caseCode;
	private List<Long> lineNo;
	private List<String> itemCode;
	private List<Long> statusId;
	private List<String> manufacturerCode;
	private List<String> manufacturerName;
	private List<String> origin;
	private List<String> brand;
}
