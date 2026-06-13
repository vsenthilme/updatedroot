package com.tekclover.wms.api.masters.transaction.model.warehouse.outbound.confirmation;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class ReturnPO {
	
	@Valid
	private ReturnPOHeader poHeader;
	
	@Valid
	private List<ReturnPOLine> poLines;
}