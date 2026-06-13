package com.iweb2b.api.integration.model.consignment.dto.shopini;

import lombok.Data;

@Data
public class StatusTrackResponse {

	private Boolean success;
	private String msg;
	private String msg_ar;
	private String status_code;
	private StatusData data;
}
