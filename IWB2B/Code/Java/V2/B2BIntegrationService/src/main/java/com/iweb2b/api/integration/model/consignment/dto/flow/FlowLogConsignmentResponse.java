package com.iweb2b.api.integration.model.consignment.dto.flow;

import java.util.List;

import lombok.Data;

@Data
public class FlowLogConsignmentResponse {

	private String status;
	private List<ConsignmentResponseData> data;
}
