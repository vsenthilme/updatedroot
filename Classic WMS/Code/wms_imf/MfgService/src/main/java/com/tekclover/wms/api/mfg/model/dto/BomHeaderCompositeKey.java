package com.tekclover.wms.api.mfg.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BomHeaderCompositeKey implements Serializable {

	private static final long serialVersionUID = -7617672247680004647L;
	
	/*
	 * `LANG_ID`, `C_ID`, `PLANT_ID`, `WH_ID`, `PAR_ITM_CODE`
	 */
	private String languageId;
	private String companyCode;
	private String plantId;
	private String warehouseId;
	private String parentItemCode;
}
