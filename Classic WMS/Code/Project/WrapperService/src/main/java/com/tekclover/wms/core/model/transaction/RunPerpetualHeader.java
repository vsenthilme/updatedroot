package com.tekclover.wms.core.model.transaction;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class RunPerpetualHeader {

	/*
	 * Pass From and To dates entered in Header screen into INVENOTRYMOVEMENT tables in IM_CTD_BY field 
	 * along with selected MVT_TYP_ID/SUB_MVT_TYP_ID values and fetch the below values
	 */
	private Date dateFrom;
	private Date dateTo;
	private List<Long> movementTypeId;
<<<<<<< HEAD
	private List<String> warehouseId;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
	private List<Long> subMovementTypeId;
}
