package com.tekclover.wms.api.mfg.model.masterphase;

import lombok.Data;

import java.io.Serializable;

@Data
public class MasterPhaseCompositeKey implements Serializable {

	private static final long serialVersionUID = -7617672247680004647L;

	private String companyCodeId;
	private String plantId;
	private String warehouseId;
	private String languageId;
	private String phaseNumber;
}