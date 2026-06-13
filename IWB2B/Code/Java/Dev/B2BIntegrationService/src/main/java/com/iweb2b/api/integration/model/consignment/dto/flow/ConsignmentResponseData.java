package com.iweb2b.api.integration.model.consignment.dto.flow;

import lombok.Data;

@Data
public class ConsignmentResponseData {

	private String reason;
	private Boolean success;
	private String message;
	private String reference_number;
	private String customer_reference_number;
}
