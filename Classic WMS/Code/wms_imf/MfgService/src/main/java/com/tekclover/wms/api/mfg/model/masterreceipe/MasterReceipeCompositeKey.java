package com.tekclover.wms.api.mfg.model.masterreceipe;

import lombok.Data;

import java.io.Serializable;

@Data
public class MasterReceipeCompositeKey implements Serializable {

	private static final long serialVersionUID = -7617672247680004647L;

	private String companyCodeId;
	private String plantId;
	private String warehouseId;
	private String languageId;
	private String receipeId;
	private String itemCode;
	private String bomNumber;
	private String operationNumber;
	private String phaseNumber;
	private String childItemCode;
}