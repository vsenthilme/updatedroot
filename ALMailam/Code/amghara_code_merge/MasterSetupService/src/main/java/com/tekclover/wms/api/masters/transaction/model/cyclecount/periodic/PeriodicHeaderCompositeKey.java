package com.tekclover.wms.api.masters.transaction.model.cyclecount.periodic;

import lombok.Data;

import java.io.Serializable;

@Data
public class PeriodicHeaderCompositeKey implements Serializable {

	private static final long serialVersionUID = -7617672247680004647L;
	
	/*
	 * `C_ID`, `PLANT_ID`, `WH_ID`, `CC_TYP_ID`, `CC_NO`
	 */
	private String languageId;
	private String companyCode;
	private String plantId;
	private String warehouseId;
	private Long cycleCountTypeId;
	private String cycleCountNo;
}