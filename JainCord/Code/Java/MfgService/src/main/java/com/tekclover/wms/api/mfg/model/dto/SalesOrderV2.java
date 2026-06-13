package com.tekclover.wms.api.mfg.model.dto;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class SalesOrderV2 {
	
	@Valid
	private SalesOrderHeaderV2 salesOrderHeader;
	
	@Valid
	private List<SalesOrderLineV2> salesOrderLine;
}
