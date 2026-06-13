package com.tekclover.wms.api.mfg.model.masterreceipe;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchMasterReceipe {

	private List<String> companyCodeId;
	private List<String> plantId;
	private List<String> languageId;
	private List<String> warehouseId;
	private List<String> operationNumber;
	private List<String> receipeId;
	private List<String> itemCode;
	private List<String> bomNumber;
	private List<String> phaseNumber;
	private List<String> childItemCode;
	private List<Long> statusId;
	
	private Date startCreatedOn;
	private Date endCreatedOn;
}