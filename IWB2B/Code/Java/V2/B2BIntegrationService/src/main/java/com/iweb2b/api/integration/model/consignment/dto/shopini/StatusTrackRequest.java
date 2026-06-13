package com.iweb2b.api.integration.model.consignment.dto.shopini;

import lombok.Data;

@Data
public class StatusTrackRequest {

	private String secret_key;
	private String marchantid;
	private String track_number;
}
