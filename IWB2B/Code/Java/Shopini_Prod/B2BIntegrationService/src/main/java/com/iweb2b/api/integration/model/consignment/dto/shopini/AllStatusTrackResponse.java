package com.iweb2b.api.integration.model.consignment.dto.shopini;

import lombok.Data;

@Data
public class AllStatusTrackResponse {

	private Boolean success;
	private String msg;
	private String msg_ar;
	private String status_code;
	private AllStatusData data;
}
