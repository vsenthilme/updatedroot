package com.tekclover.wms.api.enterprise.transaction.model.cyclecount.periodic;

import lombok.Data;

import java.util.List;

@Data
public class PeriodicUpdateResponse {
	private List<PeriodicLine> periodicLines;
	private PeriodicHeader periodicHeader;
}