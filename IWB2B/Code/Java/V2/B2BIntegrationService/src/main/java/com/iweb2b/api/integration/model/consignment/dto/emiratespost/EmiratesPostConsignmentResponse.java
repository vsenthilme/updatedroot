package com.iweb2b.api.integration.model.consignment.dto.emiratespost;

import lombok.Data;

@Data
public class EmiratesPostConsignmentResponse {

	private String awbNumber;
	private String labelUrl;
	private String referenceNumber;
	private String labelBase64;
	private String type;
	private Long status;
	private String title;
	private String detail;
	private String instance;
	private String traceId;
}