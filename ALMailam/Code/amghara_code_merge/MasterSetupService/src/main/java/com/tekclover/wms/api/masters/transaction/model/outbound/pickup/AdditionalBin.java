package com.tekclover.wms.api.masters.transaction.model.outbound.pickup;

import com.tekclover.wms.api.masters.transaction.model.inbound.inventory.Inventory;
import lombok.Data;

import java.util.List;

@Data
public class AdditionalBin {
	private List<Inventory> additionalBins;
}