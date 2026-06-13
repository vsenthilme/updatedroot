package com.tekclover.wms.core.model.transaction;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class SearchPerpetualLine {
	
	private List<String> cycleCountNo;
	private List<Long> lineStatusId;
	private String cycleCounterId;
	private String warehouseId;
	private Date startCreatedOn;
	private Date endCreatedOn;
<<<<<<< HEAD

	private List<String> itemCode;
	private List<String> storageBin;
	private List<String> packBarcodes;
	private List<Long> stockTypeId;
	private List<String> manufacturerPartNo;
	private List<String> storageSectionId;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
}
