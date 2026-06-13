package com.iweb2b.api.integration.model.consignment.dto.flow;

import java.util.List;

import lombok.Data;

@Data
public class FlowCancelShipmentRequest {

	private List<String> ReferenceNumber;
}
