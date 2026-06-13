package com.tekclover.wms.api.mfg.model.operation;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchOperationHeader {

	private List<String> companyCodeId;
	private List<String> plantId;
	private List<String> languageId;
	private List<String> warehouseId;
	private List<String> productionOrderNo;
	private List<String> productionOrderType;
	private List<Long> statusId;

	private Date startCreatedOn;
	private Date endCreatedOn;

	private Date startConfirmedOn;
	private Date endConfirmedOn;

}