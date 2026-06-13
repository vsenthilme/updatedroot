package com.iweb2b.api.integration.model.consignment.dto.shopini;

import lombok.Data;

@Data
public class CancelRequest {

	private String secret_key;
	private String marchantid;
	private String track_number;
	private String shipment_status;
	private String exception_details;
	private String cancel_note;
}
